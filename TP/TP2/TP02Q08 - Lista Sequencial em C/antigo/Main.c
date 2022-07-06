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
void inserirFim(Lista *lista, Filme *filme);

void inserirInicio(Lista *lista, Filme *filme)
{
    if (lista->tamanhoLista <= lista->n)
    {
        printf("erro");
    }

    // levar elementos para o fim do array
    for (int i = lista->n; i > 0; i--)
    {
        lista->listaDeFilmes[i] = *clone(&lista->listaDeFilmes[i - 1]);
    }

    lista->listaDeFilmes[0] = *filme;
    lista->n++;
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

void inserir(Lista *lista, Filme *filme, int pos)
{
    if (lista->tamanhoLista <= lista->n)
    {
        printf("erro");
    }

    // levar elementos para o fim do array
    for (int i = lista->n; i > pos; i--)
    {
        lista->listaDeFilmes[i] = *clone(&lista->listaDeFilmes[i - 1]);
    }

    lista->listaDeFilmes[pos] = *filme;
    lista->n++;
}

Filme removerInicio(Lista *lista)
{
    if (lista->tamanhoLista == 0)
    {
        printf("erro");
    }

    Filme resp;
    lista->listaDeFilmes[0] = resp;
    lista->n--;
    // levar elementos para o fim do array
    for (int i = 0; i < lista->n; i++)
    {
        lista->listaDeFilmes[i] = *clone(&lista->listaDeFilmes[i + 1]);
    }

    return resp;
}
Filme removerFim(Lista *lista)
{
    int aux = lista->n;
    return lista->listaDeFilmes[--aux];
}
Filme *remover(Lista *lista, int pos)
{
    Filme *resp = (Filme *)malloc(sizeof(Filme) * 200);

    if (lista->n == 0 || pos < 0 || pos >= lista->n)
    {
        printf("erro");
    }

    for (int i = pos; i < lista->n; i++)
    {
        lista->listaDeFilmes[i] = *clone(&lista->listaDeFilmes[i + 1]);
    }

    lista->listaDeFilmes[pos] = *resp;
    lista->n++;
    return resp;
}

void imprimirLista(Lista *lista)
{

    for (int i = 0; i < 37; i++)
    {
        printf("[");
        printf("%d", i);
        printf("] ");
        print_Filme(&lista->listaDeFilmes[i]);
    }
    lista->contador++;
}
int main()
{
    Lista *listaDeFilmes = (Lista *)malloc(sizeof(Lista));
    listaDeFilmes->tamanhoLista = 100;
    listaDeFilmes->n = 0;
    Filme *vetorDeFilmes = (Filme *)malloc(sizeof(Filme) * 200);
    // listaDeFilmes->listaDeFilmes = (Filme *)malloc(sizeof(Filme)*listaDeFilmes->tamanhoLista);

    while (true)
    {
        string *input = read_line();

        if (equals(input, "FIM"))
            break;

        char token[MAX_STRING_LENGTH];

        // Adiciona o endereco com o .html
        strcpy(token, "/tmp/filmes/");
        strncat(token, input->chars, input->length);

        Filme *f = new_Filme(token);
        inserirFim(listaDeFilmes, f);

        free(f);
    }

    // Metodo para leitura dos metodos
    int numMetodos = 0;
    int qtdeMetodos = 0;

    scanf("%d", &qtdeMetodos);
    do
    {
        string *metodos = read_line();
        string *aux = new_string();
        char leitorAux[100];
        //   scanf(" %[^\n]s", leitorAux);

        if (contains_char_array(metodos, "II"))
        {
            metodos = substring(metodos, 3, metodos->length);
            char token[MAX_STRING_LENGTH];
            // Adiciona o endereco com o .html
            strcpy(token, "/tmp/filmes/");
            strncat(token, metodos->chars, metodos->length);

            Filme *f = new_Filme(token);
            inserirInicio(listaDeFilmes, f);
        }
        else if (contains_char_array(metodos, "IF"))
        {
            metodos = substring(metodos, 3, metodos->length);
            char token[MAX_STRING_LENGTH];
            // Adiciona o endereco com o .html
            strcpy(token, "/tmp/filmes/");
            strncat(token, metodos->chars, metodos->length);

            Filme *f = new_Filme(token);
            inserirFim(listaDeFilmes, f);
            free(f);
        }
        else if (contains_char_array(metodos, "I*"))
        {
            // Posicao
            aux = substring(metodos, 3, 5);
            // Nome do filme.html
            metodos = substring(metodos, 6, metodos->length);
            int pos = 0;
            pos = atoi(aux->chars);

            char token[MAX_STRING_LENGTH];
            // Adiciona o endereco com o .html
            strcpy(token, "/tmp/filmes/");
            strncat(token, metodos->chars, metodos->length);
            Filme *f = new_Filme(token);
            inserir(listaDeFilmes, f, pos);
        }
        else if (contains_char_array(metodos, "RI"))
        {
            Filme removido;
            //   removido = removerInicio(listaDeFilmes);
            printf("(R) ");
            //    print_string(removido.nome);
            printf("\n");
        }
        else if (contains_char_array(metodos, "RF"))
        {
            Filme removido;
            removido = removerFim(listaDeFilmes);
            printf("(R) ");
            print_string(removido.nome);
            printf("\n");
        }
        else if (contains_char_array(metodos, "R*"))
        {
            // Posicao
            aux = substring(metodos, 3, 5);
            int pos = 0;
            pos = atoi(aux->chars);
            Filme *removido = (Filme *)malloc(sizeof(Filme) * 200);
            //        removido = remover(listaDeFilmes, pos);
            printf("(R) ");
            //  print_string(removido->nome);
            printf("\n");
            free(removido);
        }
        numMetodos++;
    } while (numMetodos < qtdeMetodos);

    imprimirLista(listaDeFilmes);

    free(vetorDeFilmes);
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
    s->chars = (char *)malloc(sizeof(char) * MAX_STRING_LENGTH);
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