#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include <stdbool.h>

// Struct de data
typedef struct Data
{
    int dia;
    int mes;
    int ano;
} Data;
// Struct para filmes
typedef struct Filme
{
    // Declaracao de variaveis
    char nome[100];
    char tituloOriginal[100];
    Data data;
    int duracao;
    char genero[100];
    char idiomaOriginal[100];
    char situacao[100];
    float orcamento;
    char palavrasChave[20][30];
    int numPalavrasChave;
} Filme;

// Metodo para remover tags
char *removeTags(char *original)
{

    char *remover = (char *)malloc(sizeof(char) * strlen(original));
    int j = 0;
    int i = 0;
    while (i < strlen(original))
    {

        if (original[i] == '<')
        {
            i++;
            while (original[i] != '>')
            {
                i++;
            }
        }
        else
        {
            remover[j] = original[i];
            j++;
        }
        i++;
    }
    remover[j] = '\0';
    return remover;
}

// Metodo para remover nbsp
char *removeNbsp(char *original)
{

    char *remover = (char *)malloc(sizeof(char) * strlen(original));
    int j = 0;
    int i = 0;
    while (i < strlen(original))
    {

        if (original[i] == '&')
        {
            i++;
            while (original[i] != ';')
            {
                i++;
            }
        }
        else
        {
            remover[j] = original[i];
            j++;
        }
        i++;
    }
    remover[j] = '\0';
    return remover;
}

// Metodo para tirar espaços a mais nas extremidades de uma cadeia de caracteres
#include <ctype.h>
char *trim(char *s)
{
    int i;
    while (isspace(*s))
        s++; // skip left side white spaces
    for (i = strlen(s) - 1; (isspace(s[i])); i--)
        ; // skip right side white spaces
    s[i + 1] = '\0';
    return s;
}
char *trim2(char *s)
{
    int i;
    while (isspace(*s))
        s += 2; // skip left side white spaces
    for (i = strlen(s) - 1; (isspace(s[i])); i--)
        ; // skip right side white spaces
    s[i + 2] = '\0';
    return s;
}

// Metodo de parse de string to float
float toFloat(char *s)
{
    return (float)atof(s);
}

void ler(char *nomeFilme, Filme *a)
{
    char buf[3000];
    char *aux;
    FILE *nomeArq = fopen(nomeFilme, "r");
    while (!feof(nomeArq))
    {
        fgets(buf, 3000, nomeArq);

        if (strstr(buf, "<h2 class") != NULL)
        {
            // Metodo para pegar o nome
            fgets(buf, 3000, nomeArq);
            aux = removeTags(buf);
            aux = trim(aux);
            strcpy(a->nome, aux);
        }
        // Metodo para pegar a duracao
        else if (strstr(buf, "<span class=\"runtime\">") != NULL)
        {

            char *token = (char *)malloc(sizeof(char) * 200);
            char aux2[2];
            int h = 0, m = 0;
            fgets(buf, 200, nomeArq);
            aux = fgets(buf, 200, nomeArq);
            aux = removeTags(buf);
            aux = trim(aux);
            // Metodo que remove h, m e espaços
            for (int i = 0; i < strlen(aux); i++)
            {
                if (aux[i] != 'h' && aux[i] != 'm' && aux[i] != ' ')
                {
                    strncat(token, &aux[i], 1);
                }
            }
            strcpy(aux, token);
            free(token);
            // Metodos que testam se o filme tem duracao acima de 1h, abaixo etc.
            if (strlen(aux) == 3)
            {
                h = 0;
                m = 0;
                h += ((int)aux[0] - 48) * 60;
                for (int i = 1; i <= 2; i++)
                {
                    strncat(aux2, &aux[i], 1);
                }
                m += atoi(aux2);
            }
            else if (strlen(aux) == 2)
            {
                h = 0;
                m = 0;
                for (int i = 0; i < 1; i++)
                {
                    strncat(aux2, &aux[i], 1);
                }
                m += atoi(aux2);
            }
            else if (strlen(aux) == 1)
            {
                h = 0;
                m = 0;
                m += atoi(aux);
            }

            a->duracao = h + m;
            // printf("%d\n", a->duracao);
        }
        // Metodo para pegar a data
        else if (strstr(buf, "<span class=\"release\">") != NULL)
        {
            int dia = 0, mes = 0, ano = 0;
            char *token = (char *)malloc(sizeof(char) * 200);
            aux = fgets(buf, 200, nomeArq);
            aux = removeTags(buf);
            aux = trim(aux);
            // Metodo que remove as /
            for (int i = 0; i < strlen(aux) - 4; i++)
            {
                if (aux[i] != '/')
                {
                    strncat(token, &aux[i], 1);
                }
            }
            strcpy(aux, token);
            free(token);
            dia = (((int)aux[0] - 48) * 10) + ((int)aux[1] - 48);
            mes = (((int)aux[2] - 48) * 10) + ((int)aux[3] - 48);
            ano = (((int)aux[4] - 48) * 1000) + (((int)aux[5] - 48) * 100) + (((int)aux[6] - 48) * 10) + (((int)aux[7] - 48));
            a->data.dia = dia;
            a->data.mes = mes;
            a->data.ano = ano;
        }
        // Metodo para pegar o titulo original
        else if (strstr(buf, "<strong>Título original</strong>") != NULL)
        {
            char *token = (char *)malloc(sizeof(char) * 200);
            aux = removeTags(buf);
            aux = trim(aux);
            // Metodo que remove caracteres desnessarios
            for (int i = 17; i < strlen(aux); i++)
            {
                strncat(token, &aux[i], 1);
            }
            strcpy(a->tituloOriginal, token);
            free(token);
        }
        // Metodo para pegar os generos
        else if (strstr(buf, "<span class=\"genres\">") != NULL)
        {
            char *token = (char *)malloc(sizeof(char) * 200);

            fgets(buf, 200, nomeArq);
            aux = fgets(buf, 200, nomeArq);
            aux = removeTags(buf);
            aux = removeNbsp(aux);
            aux = trim(aux);
            // Metodo que remove caracteres desnessarios
            for (int i = 0; i < strlen(aux); i++)
            {

                strncat(token, &aux[i], 1);
            }
            strncpy(a->genero, token, strlen(buf));
            free(token);
        }
        // Metodo para pegar o idioma original
        else if (strstr(buf, "<p><strong><bdi>Idioma original</bdi></strong>") != NULL)
        {
            char *token = (char *)malloc(sizeof(char) * 200);
            aux = removeTags(buf);
            aux = trim(aux);
            // Metodo que remove caracteres desnessarios
            for (int i = 16; i < strlen(aux); i++)
            {

                strncat(token, &aux[i], 1);
            }
            strcpy(a->idiomaOriginal, token);
            free(token);
        }
        // Metodo para pegar a situacao
        else if (strstr(buf, "Situação</bdi></strong> ") != NULL)
        {
            char *token = (char *)malloc(sizeof(char) * 200);
            aux = removeTags(buf);
            aux = trim(aux);
            // Metodo que remove caracteres desnessarios
            for (int i = 11; i < strlen(aux); i++)
            {

                strncat(token, &aux[i], 1);
            }
            strcpy(a->situacao, token);
            free(token);
        }
        // Metodo para pegar o orcamento
        else if (strstr(buf, "<p><strong><bdi>Orçamento</bdi></strong>") != NULL)
        {
            char *token = (char *)malloc(sizeof(char) * 200);
            aux = removeTags(buf);
            aux = trim(aux);
            // Metodo que remove , e .
            for (int i = 12; i < strlen(aux); i++)
            {
                if (aux[i] != ',' && aux[i] != '.')
                {
                    strncat(token, &aux[i], 1);
                }
            }
            strcpy(aux, token);
            a->orcamento = toFloat(aux);
            free(token);
        }
        // Metodo para pegar as palavras chave
        else if (strstr(buf, "<section class=\"keywords right_column\">") != NULL)
        {
            char *token = (char *)malloc(sizeof(char) * 200);
            char *aux2;
            fgets(buf, 200, nomeArq);
            fgets(buf, 200, nomeArq);
            fgets(buf, 200, nomeArq);

            a->numPalavrasChave = 0;

            if (strstr(buf, "<ul>"))
            {
                while (!strstr(buf, "</ul>"))
                {
                    // fgets(buf, 200, nomeArq);
                    aux = fgets(buf, 200, nomeArq);
                    if (aux != NULL)
                    {
                        aux = fgets(buf, 200, nomeArq);
                    }
                    aux = trim(removeTags(aux));

                    strncpy(a->palavrasChave[a->numPalavrasChave++], aux, strlen(aux));
                }
            }
            a->numPalavrasChave--;
            free(token);
        }
    }

    fclose(nomeArq);

    // Caso nao tenha titulo original, ele sera o nome do filme
    if (a->tituloOriginal == NULL)
    {
        strcpy(a->tituloOriginal, a->nome);
    }
}
// Metodo para impressao de data
void imprimirData(Data *d)
{
    printf("%.2d/%.2d/%.4d ", d->dia, d->mes, d->ano);
}
// Metodo para impressao da struct
void imprimir(Filme *a)
{
    printf("%s %s ", a->nome, a->tituloOriginal);
    imprimirData(&a->data);
    printf("%d %s %s %s %g ", a->duracao, a->genero, a->idiomaOriginal, a->situacao, a->orcamento);
    printf("[");
    for (int i = 0; i < a->numPalavrasChave; i++)
    {
        printf("%s", a->palavrasChave[i]);
        if (i != a->numPalavrasChave - 1)
        {
            printf(", ");
        }
    }
    printf("]\n");
}
// Metodo de clonagem
Filme cloneFilme(Filme *filme)
{
    Filme clone;
    char *aux;
    strncpy(clone.nome, filme->nome, strlen(filme->nome));
    strncpy(clone.tituloOriginal, filme->tituloOriginal, strlen(filme->tituloOriginal));
    // strncpy(aux, filme->data, strlen(filme->nome));
    strncpy(clone.genero, filme->genero, strlen(filme->genero));
    strncpy(clone.idiomaOriginal, filme->idiomaOriginal, strlen(filme->idiomaOriginal));
    strncpy(clone.situacao, filme->situacao, strlen(filme->situacao));
    //    strncpy(clone.palavrasChave, filme->palavrasChave, strlen(filme->palavrasChave));
    // strcpy(clone.orcamento, aux);

    // filme->orcamento = atoi(aux);
    return clone;
}
typedef struct Lista
{
    // Declaracao de variaveis
    int tamanhoLista;
    int n;
    Filme listaDeFilmes[30];
} Lista;

void inserirNoInicio(Lista *lista, Filme filme)
{

    // levar elementos para o fim do array
    for (int i = lista->n; i > 0; i--)
    {
        lista->listaDeFilmes[i] = lista->listaDeFilmes[i - 1];
    }

    lista->listaDeFilmes[0] = filme;
    lista->n++;
}

bool isFim(char *s)
{
    return (strlen(s) == 3 && s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}
int main(int argc, char *argv[])
{
    // Declaracao de variaveis
    char entrada[100][100];
    int numEntrada = 0;
    Filme filmes[30];
    char nomeFilme[50];
    char metodos[100][100];
    int numMetodos = 0;
    int qtdeMetodos = 0;
    do
    {
        scanf(" %[^\n]s", entrada[numEntrada]);

    } while (isFim(entrada[numEntrada++]) == false);
    numEntrada--;
    // printf(" ");
    for (int i = 0; i < numEntrada; i++)
    {
        Filme atual;
        strcat(strcpy(nomeFilme, "tmp/filmes/"), entrada[i]);
        ler(nomeFilme, &filmes[i]);
        // imprimir(&filmes[i]);
    }
    scanf("%d", &qtdeMetodos);
    printf("%d", qtdeMetodos);
    do
    {
        scanf(" %[^\n]s", metodos[numMetodos]);
        numMetodos++;
    } while (numMetodos < qtdeMetodos);

    // for (int i = 0; i < qtdeMetodos; i++)
    // {
    //     char *token = (char *)malloc(sizeof(char) * 200);
    //     if (strstr(metodos, "II") != NULL)
    //     {
    //         for (int i = 0; i < strlen(metodos); i++)
    //         {
    //             if (metodos[i] != 'I' && metodos[i] != ' ')
    //             {
    //                 strncat(token, &metodos[i], 1);
    //             }
    //         }
    //         printf("%s", token);
    //         free(token);
    //     }
    // }
    return 0;
}
