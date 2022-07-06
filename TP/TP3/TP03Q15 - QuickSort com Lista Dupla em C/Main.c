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
#define FDR_PREFIX "/tmp/filmes/"

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
// Struct de celulaDupla
typedef struct CelulaDupla
{
    Movie *elemento;
    struct CelulaDupla *prox;
    struct CelulaDupla *ant;
} CelulaDupla;

// Struct de ListaDupla
typedef struct ListaDupla
{
    CelulaDupla *inicio;
    CelulaDupla *fim;
    int tamanho;
} ListaDupla;

// Global variables
Movie movies[MAX_MOVIES];
int count_movies = 0;
int numMovimentacoes;
int numComparacoes;
int contador;

CelulaDupla *new_celulaDupla(Movie *elemento)
{
    CelulaDupla *tmp = (CelulaDupla *)malloc(sizeof(CelulaDupla));
    tmp->elemento = elemento;
    tmp->ant = tmp->prox = NULL;
    return tmp;
}

ListaDupla new_ListaDupla()
{
    ListaDupla tmp;
    tmp.inicio = new_celulaDupla(NULL);
    tmp.fim = tmp.inicio;
    tmp.tamanho = 0;
    return tmp;
}

Movie *clone(Movie *movie);

void inserirFim(ListaDupla *listaDupla, Movie *elemento)
{
    // CelulaDupla temporaria que armazena o elemento a ser inserido
    // CelulaDupla *tmp = new_celulaDupla(elemento);

    // tmp->prox = NULL;
    // tmp->ant = listaDupla->fim; // Conecta a tmp.ant no final da lista
    // listaDupla->fim = tmp;

    listaDupla->fim->prox = new_celulaDupla(elemento);
    listaDupla->fim->prox->ant = listaDupla->fim;
    listaDupla->fim = listaDupla->fim->prox;

    count_movies++;
    // free(tmp);
}

// Movie *remover(ListaDupla *ListaDupla)
// {
//     if (ListaDupla->topo == NULL)
//     {
//         errx(1, "erro ao inserir");
//     }

//     Movie *removido = clone(ListaDupla->topo->elemento);
//     CelulaDupla *tmp = ListaDupla->topo;
//     ListaDupla->topo = ListaDupla->topo->prox;
//     ListaDupla->tamanho--;
//     free(tmp);
//     count_movies--;
//     return removido;
// }
void imprimirListaDupla(CelulaDupla *i);
void movie_print(Movie *movie);
void imprimirListaDuplaRec(ListaDupla *ListaDupla)
{
    imprimirListaDupla(ListaDupla->inicio);
}
int contador = 0;
void imprimirListaDupla(CelulaDupla *i)
{

    if (i != NULL)
    {

        imprimirListaDupla(i->prox);
        printf("[");
        printf("%d", contador++);
        printf("] ");
        movie_print(i->elemento);
    }
}
void free_elemento(ListaDupla *listaDupla, CelulaDupla *i)
{
    int j;
    while (j < listaDupla->tamanho)
    {
        free(i->elemento);
        j++;
    }
}
void imprimirListaDuplaIterativo(ListaDupla *p)
{
    CelulaDupla *i;
    for (i = p->inicio->prox; i != NULL; i = i->prox)
    {
        movie_print(i->elemento);
    }
    free_elemento(p, i);
}

void free_ListaDupla(ListaDupla *ListaDupla)
{
    while (ListaDupla->tamanho > 0)
        ;
    //  remover(ListaDupla);
}
void swap(Movie lista[], int m, int l)
{
    Movie tmp = lista[m];
    lista[m] = lista[l];
    lista[l] = tmp;
}

void swapCelula(CelulaDupla *m, CelulaDupla *l)
{
    Movie *tmp = m->elemento;
    m->elemento = l->elemento;
    l->elemento = tmp;
    numMovimentacoes += 3;
}
void quicksortInsert(ListaDupla *listaDupla, int esqIndex, int dirIndex);

void quicksort(ListaDupla *listaDupla, int n)
{
        quicksortInsert(listaDupla, 0, n - 1);   
}
CelulaDupla *getCelulaPos(int pos, ListaDupla *x)
{
    CelulaDupla *resp;
    int i = 0;
    for (resp = x->inicio->prox; i < pos; resp = resp->prox, i++);
    return resp;
}
void quicksortInsert(ListaDupla *listaDupla, int esqIndex, int dirIndex)
{

    int i = esqIndex, j = dirIndex;
    int tamanho = (esqIndex + dirIndex) / 2;

    CelulaDupla *pivo = listaDupla->inicio->prox;
    // Caminha o pivo ate o meio do array
    for (int k = 0; k < tamanho; k++, pivo = pivo->prox);

    while (i <= j)
    {
        while (strcmp(getCelulaPos(i, listaDupla)->elemento->name, pivo->elemento->name) < 0)
        {
            if (strcmp(getCelulaPos(i, listaDupla)->elemento->situation, pivo->elemento->situation) < 0)
            {
                i++;
                numComparacoes++;
            }
            else
            {
                numComparacoes++;
                i++;
            }
        }
        while (strcmp(getCelulaPos(j, listaDupla)->elemento->name, pivo->elemento->name) > 0)
        {
            if (strcmp(getCelulaPos(j, listaDupla)->elemento->situation, pivo->elemento->situation) > 0)
            {
                numComparacoes++;
                j--;
            }
            else
            {
                numComparacoes++;
                j--;
            }
        }
        if (i <= j)
        {
            swapCelula(getCelulaPos(i, listaDupla), getCelulaPos(j, listaDupla));
            
            i++;
            j--;
        }
    }
    if (esqIndex < j)
    {
        quicksortInsert(listaDupla, esqIndex, j);
    }
    if (i < dirIndex)
    {
        quicksortInsert(listaDupla, i, dirIndex);
    }
}
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
// -------------------------------------------------------------------------------- //
int main()
{
    ListaDupla listaDupla = new_ListaDupla();
    size_t prefix_size = strlen(FDR_PREFIX);
    char line[MAX_LINE_SIZE];
    strcpy(line, FDR_PREFIX);
    readline(line + prefix_size, MAX_LINE_SIZE);
    while (!isFim(line + prefix_size))
    {
        movie_readHtml(line);
        readline(line + prefix_size, MAX_LINE_SIZE);
        inserirFim(&listaDupla, &movies[count_movies]);
        count_movies++;
        contador++;
    }
    clock_t tempo = clock();
    quicksort(&listaDupla, contador);
    tempo = clock() - tempo;
    imprimirListaDuplaIterativo(&listaDupla);
    FILE *log;
    log = fopen("705113_quicksortEmC.txt", "w");
    fprintf(log, "%s", "705113   ");
    fprintf(log, "%d", numComparacoes);
    fprintf(log, "%s", "    ");
    fprintf(log, "%d", numMovimentacoes);
    fprintf(log, "%s", "    ");
    fprintf(log, "%lf", ((double)tempo) / ((CLOCKS_PER_SEC / 1000)));
    fclose(log);

    free_ListaDupla(&listaDupla);

    return EXIT_SUCCESS;
}