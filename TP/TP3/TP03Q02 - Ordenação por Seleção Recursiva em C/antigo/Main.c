#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
#include <ctype.h>

const size_t MAX_STRING_LENGTH = 500;

typedef struct date
{
    int dia;
    int mes;
    int ano;
} date;

typedef struct string
{
    char *chars;
    size_t length;
} string;

typedef struct Filme
{
    string *nome;
    string *tituloOriginal;
    date *dataLancamento;
    int duracao;
    string *genero;
    string *idiomaOriginal;
    string *situacao;
    float orcamento;
    string *palavrasChave;
} Filme;

typedef struct Lista
{
    // Declaracao de variaveis
    int tamanhoLista;
    int n;
    Filme listaDeFilmes[100];
    int contador;
    int numComparacoes;
    int numMovimentacoes;
} Lista;

// String
string *new_string();
string *read_line();
string *clone_string(string *s);
void print_string(string *s);
void append_char(string *dest, const char src);
void append_char_array(string *dest, const char *src);
bool contains_char_array(const string *str1, const char *str2);
string *strip(string *s);
string *remove_tags(string *s);
float string_to_float(string *s);
string *substring(const string *s, int startIndex, int endIndex);
bool equals(const string *s, const char *arr);
void free_string(string *s);

// Date
date *new_date(string *s);
date *clone_date(date *src);
void print_date(date *d);

// Filme
Filme *clone_filme(Filme *src);
Filme *new_Filme(const char *file_src);
Filme *read_file(const char *file_src);
void print_Filme(Filme *f);
int movie_time_to_int(const string *s);
Filme *clone(Filme *src);

// Lista
Lista *new_lista(){
    Lista *listaDeFilmes = (Lista *)calloc(1, sizeof(Lista));
    listaDeFilmes->tamanhoLista = 50;
    listaDeFilmes->n = 0;
    return listaDeFilmes;
}

void inserirFim(Lista *lista, Filme *filme)
{
    if (lista->tamanhoLista <= lista->n)
    {
        printf("erro");
    }

    lista->listaDeFilmes[lista->n] = *clone(filme);
    lista->n++;
}
void imprimirLista(Lista *lista)
{

    for (int i = 0; i < lista->n; i++)
    {
        print_Filme(&lista->listaDeFilmes[i]);
    }
    lista->contador++;
}

void swap(Lista *lista, int m, int l)
{
    Filme tmp = lista->listaDeFilmes[m];
    lista->listaDeFilmes[m] = lista->listaDeFilmes[l];
    lista->listaDeFilmes[l] = tmp;
    lista->numMovimentacoes += 3;
}

int minIndex(Lista *lista, int i, int j)
{

    if (i == j)
        return i;

    int k = minIndex(lista, i + 1, j);
    lista->numComparacoes++;
    return (strcmp(lista->listaDeFilmes[i].tituloOriginal->chars, lista->listaDeFilmes[j].tituloOriginal->chars) < 0) ? i : k;
}

void selecaoRec(Lista *lista, int n, int index)
{
    if (index == n)
        return;

    int k = minIndex(lista, index, n - 1);

    if (k != index)
        swap(lista, k, index);

    selecaoRec(lista, n, ++index);
}


int main()
{
    Lista *listaDeFilmes = new_lista();

    int n = 0;
    while (true)
    {
        string *input = read_line();

        if (equals(input, "FIM"))
            break;

        char token[MAX_STRING_LENGTH];

        // Adiciona o endereco com o .html
        strcpy(token, "tmp/filmes/");
        strncat(token, input->chars, input->length);
       // printf("%s\n", token);
        Filme *f = new_Filme(token);
        inserirFim(listaDeFilmes, f);
        n++;
      //  print_Filme(f);
        free(f);
        free_string(input);
    }
    //  selecaoRec(listaDeFilmes, n, 0);

      imprimirLista(listaDeFilmes);

    // FILE *log;
    // log = fopen("705113_selecaoRecursiva.txt", "w");
    // fprintf(log, "%s", "705113   ");
    // fprintf(log, "%d", listaDeFilmes->numComparacoes);
    // fprintf(log, "%s", "   ");
    // fprintf(log, "%d", listaDeFilmes->numMovimentacoes);
    // fclose(log);
    free(listaDeFilmes);
    return 0;
     
}

void print_Filme(Filme *f)
{

    print_string(f->nome);
    printf(" ");
    print_string(f->tituloOriginal);
    printf(" ");
    print_date(f->dataLancamento);
    printf(" ");
    printf("%d ", f->duracao);
    print_string(f->genero);
    printf(" ");
    print_string(f->idiomaOriginal);
    printf(" ");
    print_string(f->situacao);
    printf(" ");
    printf("%g [", f->orcamento);
    print_string(f->palavrasChave);
    printf("] \n");
}

void print_Filmes(Filme *f, int n)
{
    for (int i = 0; i < n; i++)
    {
        print_string(f->nome);
        printf(" ");
        print_string(f->tituloOriginal);
        printf(" ");
        print_date(f->dataLancamento);
        printf(" ");
        printf("%d ", f->duracao);
        print_string(f->genero);
        printf(" ");
        print_string(f->idiomaOriginal);
        printf(" ");
        print_string(f->situacao);
        printf(" ");
        printf("%g [", f->orcamento);
        print_string(f->palavrasChave);
        printf("] \n");
    }
}

Filme *new_Filme(const char *file_src)
{

    Filme *f = read_file(file_src);
    return f;
}

Filme *read_file(const char *file_src)
{

    bool contains_keywords = false;
    bool contains_original_title = false;
    Filme *f = (Filme *)malloc(sizeof(Filme));
    FILE *file;

    string *curr_line = new_string();

    file = fopen(file_src, "r");

    if (file == NULL)
    {
        f->nome = new_string();
        return f;
    }

    // Achar o titulo
    while (fgets(curr_line->chars, 255, file))
    {

        if (contains_char_array(curr_line, "<title>"))
            break;
    }

    f->nome = clone_string(remove_tags(strip(curr_line)));

    char *endString = strstr(f->nome->chars, "(");
    int position = endString - f->nome->chars;
    f->nome = substring(f->nome, 0, position - 1);

    // Data de lacamento
    while (fgets(curr_line->chars, 255, file))
    {

        if (contains_char_array(curr_line, "<span class=\"release\""))
            break;
    }

    fgets(curr_line->chars, 255, file);
    string *release = clone_string(remove_tags(strip(curr_line)));
    release = substring(release, 0, 10);

    f->dataLancamento = new_date(release);
    free(release);

    // Generos
    while (fgets(curr_line->chars, 255, file))
    {

        if (contains_char_array(curr_line, "<span class=\"genres\">"))
            break;
    }

    fgets(curr_line->chars, 255, file);
    fgets(curr_line->chars, 255, file);
    f->genero = remove_tags(strip(curr_line));

    // Duracao
    while (fgets(curr_line->chars, 255, file))
    {

        if (contains_char_array(curr_line, "<span class=\"runtime\">"))
            break;
    }

    fgets(curr_line->chars, 255, file);
    fgets(curr_line->chars, 255, file);
    string *tempo = clone_string(strip(curr_line));
    f->duracao = movie_time_to_int(tempo);
    free(tempo);

    // Titulo Original
    while (fgets(curr_line->chars, 255, file))
    {

        if (contains_char_array(curr_line, "Título original"))
        {
            contains_original_title = true;
            break;
        }

        if (contains_char_array(curr_line, "<strong><bdi>Situação</bdi></strong>"))
        {

            break;
        }
    }

    if (contains_original_title)
    {
        f->tituloOriginal = clone_string(remove_tags(strip(curr_line)));
        f->tituloOriginal = substring(f->tituloOriginal, 17, f->tituloOriginal->length);
    }
    else
    {
        f->tituloOriginal = clone_string(f->nome);
    }

    // Situacao
    if (contains_original_title)
        while (fgets(curr_line->chars, 255, file))
        {

            if (contains_char_array(curr_line, "<strong><bdi>Situação</bdi></strong>"))
                break;
        }

    f->situacao = clone_string(remove_tags(strip(curr_line)));
    f->situacao = substring(f->situacao, 11, f->situacao->length);

    // Idioma original
    while (fgets(curr_line->chars, 255, file))
    {

        if (contains_char_array(curr_line, "Idioma original"))
            break;
    }

    f->idiomaOriginal = clone_string(remove_tags(strip(curr_line)));
    f->idiomaOriginal = substring(f->idiomaOriginal, 16, f->idiomaOriginal->length);

    // Orcamento
    while (fgets(curr_line->chars, 255, file))
    {
        if (contains_char_array(curr_line, "Orçamento"))
            break;
    }

    string *orcamento = clone_string(remove_tags(strip(curr_line)));
    f->orcamento = string_to_float(orcamento);
    free(orcamento);

    // Palavras chave
    while (fgets(curr_line->chars, 255, file))
    {

        if (contains_char_array(curr_line, "\"keywords right_column\""))
            break;
    }

    while (fgets(curr_line->chars, 255, file))
    {

        if (contains_char_array(curr_line, "ul"))
        {
            contains_keywords = true;
            break;
        }
        else if (contains_char_array(curr_line, "Nenhuma palavra-chave foi adicionada."))
        {
            break;
        }
    }
    fgets(curr_line->chars, 255, file);
    f->palavrasChave = new_string();
    string *tmp = new_string();
    if (contains_keywords)
    {
        while (fgets(curr_line->chars, 255, file))
        {

            if (contains_char_array(curr_line, "</ul>"))
                break;

            tmp = strip(remove_tags(curr_line));
            append_char_array(f->palavrasChave, tmp->chars);
            append_char_array(f->palavrasChave, ", ");
            fgets(curr_line->chars, 255, file);
        }
        f->palavrasChave = substring(f->palavrasChave, 0, f->palavrasChave->length - 2);
    }
    else
    {
        f->palavrasChave = new_string();
    }
    free(tmp);
    free(curr_line);
    fclose(file);
    return f;
}

Filme *clone(Filme *src)
{

    Filme *clonado = (Filme *)malloc(sizeof(Filme));
    clonado->nome = clone_string(src->nome);
    clonado->tituloOriginal = clone_string(src->tituloOriginal);
    clonado->dataLancamento = clone_date(src->dataLancamento);
    clonado->duracao = src->duracao;
    clonado->genero = clone_string(src->genero);
    clonado->orcamento = src->orcamento;
    clonado->palavrasChave = clone_string(src->palavrasChave);
    clonado->situacao = clone_string(src->situacao);
    clonado->idiomaOriginal = clone_string(src->idiomaOriginal);

    return clonado;
}

string *new_string()
{
    string *s = (string *)malloc(sizeof(string));
    s->chars = (char *)calloc(MAX_STRING_LENGTH, sizeof(char));
    s->length = 0;

    return s;
}

string *read_line()
{
    string *resp = new_string();

    fgets(resp->chars, MAX_STRING_LENGTH, stdin);

    resp->length = strlen(resp->chars);
    resp->chars[resp->length - 1] = '\0';

    return resp;
}

string *clone_string(string *s)
{

    string *clone_string = new_string();

    strncpy(clone_string->chars, s->chars, strlen(s->chars));
    clone_string->length = s->length;

    return clone_string;
}

inline void print_string(string *s)
{
    if (s != NULL)
        printf("%s", s->chars);
}

void append_char(string *dest, const char src)
{

    if (dest == NULL || &src == NULL)
        return;

    if (dest->length + 1 >= MAX_STRING_LENGTH - 1)
        return;

    strncat(dest->chars, &src, 1);
    dest->length += 1;
}

void append_char_array(string *dest, const char *src)
{

    if (dest == NULL || src == NULL)
        return;

    size_t src_length = strlen(src);

    if (dest->length + src_length >= MAX_STRING_LENGTH - 1)
        return;

    strcat(dest->chars, src);
    dest->length += src_length;
}

bool contains_char_array(const string *str1, const char *str2)
{

    if (str1->chars == NULL || str2 == NULL)
        return false;

    return strstr(str1->chars, str2) != NULL;
}

string *strip(string *s)
{
    int i;

    while (isspace(*s->chars))
        s->chars++;
    for (i = strlen(s->chars) - 1; (isspace(s->chars[i])); i--)
        ;
    s->chars[i + 1] = '\0';
    s->length = strlen(s->chars);
    return s;
}

string *remove_tags(string *s)
{
    string *result = new_string();

    int i = 0;

    while (i < strlen(s->chars))
    {
        if (s->chars[i] == '<')
        {
            while (s->chars[i] != '>')
            {
                i++;
            }
            i++;
            continue;
        }
        if (s->chars[i] == '&')
        {
            while (s->chars[i] != ';')
                i++;
        }
        if (s->chars[i] == ';')
        {
            i++;
            continue;
        }
        append_char(result, s->chars[i]);
        i++;
    }

    return result;
}

int movie_time_to_int(const string *s)
{

    char s_time[10];
    strncpy(s_time, s->chars, 9);

    int time = 0;

    char *token;

    token = strtok(s_time, "h");

    if (contains_char_array(s, "h"))
    {
        time += atoi(token) * 60;

        token = strtok(NULL, "m");
        if (token != NULL)
        {
            time += atoi(token);
        }
    }
    else
    {
        if (token != NULL)
        {
            time += atoi(token);
        }
    }

    return time;
}

float string_to_float(string *s)
{
    if (contains_char_array(s, "-"))
        return 0.0;

    char *token = strstr(s->chars, "$");

    string *float_string = new_string();
    for (int i = 0; i < strlen(token); i++)
    {
        if (token[i] != ',' && token[i] != '$')
            append_char(float_string, token[i]);
    }
    return (float)atof(float_string->chars);
}

string *substring(const string *s, int startIndex, int endIndex)
{

    string *result = new_string();

    strncpy(result->chars, s->chars + startIndex, endIndex);
    result->length = strlen(result->chars);

    return result;
}

void free_string(string *s)
{
    free(s->chars);
    free(s);
}

date *new_date(string *s)
{

    date *d = (date *)malloc(sizeof(date));

    char *token;

    char string[MAX_STRING_LENGTH];
    memset(string, 0, MAX_STRING_LENGTH);
    strncpy(string, s->chars, strlen(s->chars));

    token = strtok(string, "/");
    d->dia = atoi(token);

    token = strtok(NULL, "/");
    d->mes = atoi(token);

    token = strtok(NULL, "/");
    if (token != NULL)
        d->ano = atoi(token);

    return d;
}

date *clone_date(date *src)
{

    date *clonado = (date *)malloc(sizeof(date));

    clonado->dia = src->dia;
    clonado->mes = src->mes;
    clonado->ano = src->ano;

    return clonado;
}

void print_date(date *d)
{
    printf("%.2d/%.2d/%.4d", d->dia, d->mes, d->ano);
}

bool equals(const string *s, const char *arr)
{
    return strcmp(s->chars, arr) == 0;
}