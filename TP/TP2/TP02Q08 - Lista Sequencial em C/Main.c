// -------------------------------------------------------------------------------- //
// @author Pedro Henrique Lopes Costa
// 1/2022
//
// -------------------------------------------------------------------------------- //
// Includes
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>
#include <err.h>

// -------------------------------------------------------------------------------- //
// Definitions
#define MAX_MOVIES 100
#define MAX_FIELD_SIZE 100
#define MAX_KEYWORDS 20
#define MAX_LINE_SIZE 250
#define FDR_PREFIX "tmp/filmes/"

// -------------------------------------------------------------------------------- //

// Structs
typedef struct
{
    int year,
        month,
        day;
} Date;

typedef struct
{
    char name[MAX_FIELD_SIZE],
        original_title[MAX_FIELD_SIZE],
        genre[MAX_FIELD_SIZE],
        original_language[MAX_FIELD_SIZE],
        situation[MAX_FIELD_SIZE],
        keywords[MAX_KEYWORDS][MAX_FIELD_SIZE];
    Date release_date;
    int duration, count_keywords;
    float budget;

} Movie;

// Global variables
Movie movies[MAX_MOVIES];
int count_movies = 0;

Date clone_date(Date src)
{

    Date clonado;

    clonado.day = src.day;
    clonado.month = src.month;
    clonado.year = src.year;

    return clonado;
}
Movie *clone(Movie *src)
{

    Movie *clonado = (Movie *)malloc(sizeof(Movie));
    strncpy(clonado->name, src->name, MAX_FIELD_SIZE);
    strncpy(clonado->original_title, src->original_title, MAX_FIELD_SIZE);
    clonado->release_date = clone_date(src->release_date);
    clonado->duration = src->duration;
    strncpy(clonado->genre, src->genre, MAX_FIELD_SIZE);
    clonado->budget = src->budget;
    // strncpy(clonado->keywords, src->keywords, MAX_FIELD_SIZE);
    strncpy(clonado->situation, src->situation, MAX_FIELD_SIZE);
    strncpy(clonado->original_language, src->original_language, MAX_FIELD_SIZE);
    strncpy(clonado->name, src->name, MAX_FIELD_SIZE);
    return clonado;
}

// -------------------------------------------------------------------------------- //

// -------------------------------------------------------------------------------- //
// Functions
bool isFim(char *str) { return str[0] == 'F' && str[1] == 'I' && str[2] == 'M'; }

char *remove_line_break(char *line)
{
    while (*line != '\r' && *line != '\n')
        line++;
    *line = '\0';
    return line;
}

char *freadline(char *line, int max_size, FILE *file) { return remove_line_break(fgets(line, max_size, file)); }
char *readline(char *line, int max_size) { return freadline(line, max_size, stdin); }

long int indexOf(char *str, char *search)
{
    long int pos = strcspn(str, search);
    return pos == strlen(str) ? -1 : pos;
}

char *substring(char *string, int position, int length)
{
    char *p;
    int c;
    p = (char *)malloc(sizeof(char) * (length + 1));
    if (p == NULL)
    {
        printf("Unable to allocate memory.\n");
        exit(1);
    }
    for (c = 0; c < length; c++)
    {
        *(p + c) = *(string + position - 1);
        string++;
    }
    *(p + c) = '\0';
    return p;
}

void str_replace(char *target, const char *needle, const char *replacement)
{
    char buffer[1024] = {0};
    char *insert_point = &buffer[0];
    const char *tmp = target;
    size_t needle_len = strlen(needle);
    size_t repl_len = strlen(replacement);

    while (1)
    {
        const char *p = strstr(tmp, needle);
        if (p == NULL)
        {
            strcpy(insert_point, tmp);
            break;
        }
        memcpy(insert_point, tmp, p - tmp);
        insert_point += p - tmp;
        memcpy(insert_point, replacement, repl_len);
        insert_point += repl_len;
        tmp = p + needle_len;
    }
    strcpy(target, buffer);
}

int firstDigit(const char *str, int start)
{
    for (int i = start; i != strlen(str); i++)
        if (str[i] >= '0' && str[i] <= '9')
            return i;
    return -1;
}

// Remove tags
char *extractOnlyText(char *html, char *text)
{
    char *start = text;
    int contagem = 0;
    while (*html != '\0')
    {
        if (*html == '<')
        {
            if (
                (*(html + 1) == 'p') ||
                (*(html + 1) == 'b' && *(html + 2) == 'r') ||
                (*(html + 1) == '/' && *(html + 2) == 'h' && *(html + 3) == '1') ||
                (*(html + 1) == '/' && *(html + 2) == 't' && *(html + 3) == 'h') ||
                (*(html + 1) == '/' && *(html + 2) == 't' && *(html + 3) == 'd'))
                break;
            else
                contagem++;
        }
        else if (*html == '>')
            contagem--;
        else if (contagem == 0 && *html != '"')
        {
            if (*html == '&')
                html = strchr(html, ';');
            else if (*html != '\r' && *html != '\n')
                *text++ = *html;
        }
        html++;
    }
    *text = '\0';
    return *start == ' ' ? start + 1 : start;
}

// -------------------------------------------------------------------------------- //
// Class movie functions
void movie_print(Movie *movie)
{
    printf("%s %s %02i/%02i/%04i %i %s %s %s %g [",
           movie->name,
           movie->original_title,
           movie->release_date.day, movie->release_date.month, movie->release_date.year,
           movie->duration,
           movie->genre,
           movie->original_language,
           movie->situation,
           movie->budget);
    for (int i = 0; i < movie->count_keywords; i++)
    {
        if (i == movie->count_keywords - 1)
            printf("%s]\n", movie->keywords[i]);
        else
            printf("%s, ", movie->keywords[i]);
    }
    if (movie->count_keywords == 0)
        printf("]\n");
}

void movie_readHtml(char *filename)
{
    FILE *html_file;
    char *line_html = NULL;
    size_t len = 0;
    ssize_t read;

    html_file = fopen(filename, "r");

    if (html_file == NULL)
    {
        printf("error could not open file %s", filename);
        exit(EXIT_FAILURE);
    }
    // ------------------------------------ //

    // Creating movie variables
    char *name = NULL,
         *original_title = NULL,
         *genre = NULL,
         *original_language = NULL,
         *situation = NULL,
         *keywords = NULL;

    Date release_date;

    release_date.day = 0;
    int duration = -1;
    float budget = -1;

    // ------------------------------------ //

    // Read HTML line by line
    while ((read = getline(&line_html, &len, html_file)) != -1)
    {

        // --------------------------- //
        // Find movie name
        if (name == NULL)
        {
            if (strstr(line_html, "<title>") != NULL)
            {
                name = strstr(line_html, "<title>") + 7;
                strcpy(movies[count_movies].name, name);
                str_replace(movies[count_movies].name, "&#8212;", "—");
                movies[count_movies].name[strlen(movies[count_movies].name) - 46] = '\0';
            }
        }

        // --------------------------- //
        // Find movie original title
        if (original_title == NULL)
        {
            if (strstr(line_html, "<p class=\"wrap\">") != NULL)
            {
                original_title = strstr(line_html, "</strong> ") + 10;
                original_title[strlen(original_title) - 5] = '\0';
                strcpy(movies[count_movies].original_title, original_title);
            }
        }

        // --------------------------- //
        // Find movie release date
        if (release_date.day == 0)
        {
            if (strstr(line_html, "<span class=\"release\">") != NULL)
            {
                // Skip one line
                read = getline(&line_html, &len, html_file);
                char *day, *month, *year;
                day = substring(line_html, 9, 2);
                month = substring(line_html, 12, 2);
                year = substring(line_html, 15, 4);
                movies[count_movies].release_date.day = atoi(day);
                movies[count_movies].release_date.month = atoi(month);
                movies[count_movies].release_date.year = atoi(year);
                free(day);
                free(month);
                free(year);
            }
        }

        // --------------------------- //
        // Find movie duration
        if (duration == -1)
        {
            if (strstr(line_html, "<span class=\"runtime\">") != NULL)
            {
                // Skip two lines
                read = getline(&line_html, &len, html_file);
                read = getline(&line_html, &len, html_file);
                int h_pos = indexOf(line_html, "h"),
                    hours = 0,
                    minutes = 0;
                if (h_pos != -1)
                {
                    char *hours_string = substring(line_html, firstDigit(line_html, 0), h_pos);
                    hours = atoi(hours_string);
                    free(hours_string);
                }
                char *minutes_string = substring(line_html, firstDigit(line_html, h_pos == -1 ? 0 : h_pos), strlen(line_html) - 1);
                minutes = atoi(minutes_string);
                duration = (hours * 60) + minutes;
                movies[count_movies].duration = duration;
                free(minutes_string);
            }
        }

        // -------------------------- //
        // Find movie genres
        if (genre == NULL)
        {
            if (strstr(line_html, "<span class=\"genres\">") != NULL)
            {
                // Skip two lines
                read = getline(&line_html, &len, html_file);
                read = getline(&line_html, &len, html_file);
                extractOnlyText(line_html, movies[count_movies].genre);
                genre = substring(movies[count_movies].genre, 7, strlen(movies[count_movies].genre));
                strcpy(movies[count_movies].genre, genre);
                free(genre);
            }
        }

        // --------------------------- //
        // Find movie original language
        if (original_language == NULL)
        {
            if (strstr(line_html, "<bdi>Idioma original</bdi>") != NULL)
            {
                strcpy(movies[count_movies].original_language, line_html);
                original_language = substring(movies[count_movies].original_language, 50, strlen(line_html) - 54);
                strcpy(movies[count_movies].original_language, original_language);
                free(original_language);
            }
        }

        // --------------------------- //
        // Find movie situation
        if (situation == NULL)
        {
            if (strstr(line_html, "<bdi>Situação</bdi>") != NULL)
            {
                strcpy(movies[count_movies].situation, line_html);
                situation = substring(movies[count_movies].situation, 44, strlen(line_html) - 44);
                strcpy(movies[count_movies].situation, situation);
                free(situation);
            }
        }

        // --------------------------- //
        // Find movie budget
        if (budget == -1)
        {
            if (strstr(line_html, "<bdi>Orçamento</bdi>") != NULL)
            {
                char *p_budget, e_budget[strlen(line_html)];
                strcpy(e_budget, line_html);
                p_budget = substring(e_budget, 45, strlen(line_html) - 49);
                if (!strcmp(p_budget, "-"))
                    movies[count_movies].budget = 0;
                else
                {
                    strcpy(e_budget, p_budget);
                    str_replace(e_budget, "$", "");
                    str_replace(e_budget, ",", "");
                    movies[count_movies].budget = atof(e_budget);
                }
                free(p_budget);
            }
        }

        // --------------------------- //
        // Find movie keywords
        if (keywords == NULL)
        {
            if (strstr(line_html, "<h4><bdi>Palavras-chave</bdi></h4>") != NULL)
            {
                // Skip two lines until keywords starts
                for (int i = 0; i < 2; i++)
                    read = getline(&line_html, &len, html_file);
                char tmp_line[strlen(line_html)];
                strcpy(tmp_line, line_html);
                keywords = substring(tmp_line, 5, strlen(line_html) - 5);

                if (strcmp(keywords, "<p><bdi>Nenhuma palavra-chave foi adicionada.</bdi></p>"))
                {
                    // Skip more two lines until keywords starts
                    for (int x = 0; x < 2; x++)
                        read = getline(&line_html, &len, html_file);
                    while (true)
                    {
                        if (strstr(line_html, "</ul>") != NULL)
                            break;
                        if (strstr(line_html, "<li>") != NULL)
                        {
                            extractOnlyText(line_html, tmp_line);
                            keywords = substring(tmp_line, 9, strlen(line_html) - 8);
                            strcpy(movies[count_movies].keywords[movies[count_movies].count_keywords++], keywords);
                        }
                        read = getline(&line_html, &len, html_file);
                    }
                }
                free(keywords);
            }
        }

        // ------------------------------------ //
        // Verify variables still "null"
        if (original_title == NULL)
            strcpy(movies[count_movies].original_title, movies[count_movies].name);
    }

    // ------------------------------------ //
    fclose(html_file);
    if (line_html)
        free(line_html);
}
bool contains_char_array(const char *str1, const char *str2)
{

    if (str1 == NULL || str2 == NULL)
        return false;

    return strstr(str1, str2) != NULL;
}
char *read_line()
{
    char *resp = (char *)calloc(MAX_LINE_SIZE, sizeof(char));
    size_t length = 0;
    fgets(resp, MAX_LINE_SIZE, stdin);

    length = strlen(resp);
    resp[length - 1] = '\0';

    return resp;
}
/**
 * Lista estatica
 * @author Max do Val Machado
 * @version 2 01/2015
 */

Movie array[MAX_MOVIES]; // Elementos da pilha
int n;                   // Quantidade de array.

/**
 * Inicializacoes
 */
void start()
{
    n = 0;
}

/**
 * Insere um elemento na primeira posicao da lista e move os demais
 * elementos para o fim da
 * @param x int elemento a ser inserido.
 */
void inserirInicio(Movie x)
{
    int i;

    // validar insercao
    if (n >= MAX_MOVIES)
    {
        printf("Erro ao inserir!");
        exit(1);
    }

    // levar elementos para o fim do array
    for (i = n; i > 0; i--)
    {
        array[i] = array[i - 1];
    }

    array[0] = x;
    n++;
}

/**
 * Insere um elemento na ultima posicao da
 * @param x int elemento a ser inserido.
 */
void inserirFim(Movie x)
{

    // validar insercao
    if (n >= MAX_MOVIES)
    {
        printf("Erro ao inserir!");
        exit(1);
    }

    array[n] = x;
    n++;
}

/**
 * Insere um elemento em uma posicao especifica e move os demais
 * elementos para o fim da
 * @param x int elemento a ser inserido.
 * @param pos Posicao de insercao.
 */
void inserir(Movie x, int pos)
{
    int i;

    // validar insercao
    if (n >= MAX_MOVIES || pos < 0 || pos > n)
    {
        printf("Erro ao inserir!");
        exit(1);
    }

    // levar elementos para o fim do array
    for (i = n; i > pos; i--)
    {
        array[i] = array[i - 1];
    }

    array[pos] = x;
    n++;
}

/**
 * Remove um elemento da primeira posicao da lista e movimenta
 * os demais elementos para o inicio da mesma.
 * @return resp int elemento a ser removido.
 */
Movie removerInicio()
{
    int i;
    Movie resp;

    // validar remocao
    if (n == 0)
    {
        printf("Erro ao remover!");
        exit(1);
    }

    resp = array[0];
    n--;

    for (i = 0; i < n; i++)
    {
        array[i] = array[i + 1];
    }

    return resp;
}

/**
 * Remove um elemento da ultima posicao da
 * @return resp int elemento a ser removido.
 */
Movie removerFim()
{

    // validar remocao
    if (n == 0)
    {
        printf("Erro ao remover!");
        exit(1);
    }

    return array[--n];
}

/**
 * Remove um elemento de uma posicao especifica da lista e
 * movimenta os demais elementos para o inicio da mesma.
 * @param pos Posicao de remocao.
 * @return resp Movie elemento a ser removido.
 */
Movie remover(int pos)
{
    int i;
    Movie resp;

    // validar remocao
    if (n == 0 || pos < 0 || pos >= n)
    {
        printf("Erro ao remover!");
        exit(1);
    }

    resp = array[pos];
    n--;

    for (i = pos; i < n; i++)
    {
        array[i] = array[i + 1];
    }

    return resp;
}

/**
 * Mostra os array separados por espacos.
 */
void mostrar()
{
    int i;

    for (i = 0; i < n; i++)
    {
        printf("[");
        printf("%d", i);
        printf("] ");
        movie_print(&array[i]);
    }
}
// -------------------------------------------------------------------------------- //
int main()
{
    int qtdeMetodos = 0;
    size_t prefix_size = strlen(FDR_PREFIX);
    char line[MAX_LINE_SIZE];
    strcpy(line, FDR_PREFIX);
    readline(line + prefix_size, MAX_LINE_SIZE);

    start();
    while (!isFim(line + prefix_size))
    {
        movie_readHtml(line);
        readline(line + prefix_size, MAX_LINE_SIZE);
        inserirFim(movies[count_movies]);
        scanf("%d", &qtdeMetodos);
        count_movies++;
    }
    int num = 0;
    scanf("%d", &qtdeMetodos);
    while (num < qtdeMetodos)
    {

        char *aux = read_line();

        if (contains_char_array(aux, "II"))
        {
            char movie_path[MAX_LINE_SIZE];
            strcpy(movie_path, FDR_PREFIX);
            aux = substring(aux, 4, strlen(aux));
            strncat(movie_path, aux, strlen(aux));
            movie_readHtml(movie_path);
            inserirInicio(movies[count_movies]);
        }
        else if (contains_char_array(aux, "IF"))
        {
            char movie_path[MAX_LINE_SIZE];
            strcpy(movie_path, FDR_PREFIX);
            aux = substring(aux, 4, strlen(aux));
            strncat(movie_path, aux, strlen(aux));
            movie_readHtml(movie_path);
            inserirFim(movies[count_movies]);
        }
        else if (contains_char_array(aux, "I* "))
        {
            char movie_path[MAX_LINE_SIZE];
            strcpy(movie_path, FDR_PREFIX);
            aux = substring(aux, 7, strlen(aux));
            strncat(movie_path, aux, strlen(aux));
            movie_readHtml(movie_path);
            // Posicao
            aux = substring(aux, 3, 5);
            int pos = 0;
            pos = atoi(aux);
            inserir(movies[count_movies], pos);
        }
        else if (contains_char_array(aux, "R"))
        {
            Movie *removido;
            //  removido = remover(&pilha);
            printf("(R) ");
            printf("%s", removido->name);
            printf("\n");
        }

        num++;
    }
    mostrar();
    // imprimirPilhaRec(&pilha);
    // free_pilha(&pilha);
    return EXIT_SUCCESS;
}