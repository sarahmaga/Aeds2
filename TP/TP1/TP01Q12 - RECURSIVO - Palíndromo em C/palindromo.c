#include <stdio.h>   // para entradas e saida
#include <stddef.h>  // para definicoes basicas
#include <stdlib.h>  // para a biblioteca padrao
#include <string.h>  // para cadeias de caracteres
#include <stdarg.h>  // para tratar argumentos
#include <stdbool.h> // para definicoes logicas
#include <ctype.h>   // para tipos padroes
#include <math.h>    // para definicoes matematicas
#include <time.h>    // para medir tempo

bool isFim(char *s)
{
    return (strlen(s) >= 3 && s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}

bool isPalindromo(char *s, int i)
{
    // Declaracao de variaveis
    bool resp = false;
    // Metodo recursivo
    if (i < strlen(s)/2)
    {
        if (s[i] != s[strlen(s) - (i + 1)])
        { // condição é falsa (compara o caracter da esquerda com o da direita sucessivamente)
            resp = false;
        }
        else {
            resp= isPalindromo - (i+1);
            resp=true;
        }
    }
    return resp;
}

int main()
{
    // Declaracao de variaveis
    char entrada[1000][100];
    int numEntrada = 0;
    bool resp = false;
    do
    {
        scanf(" %[^\n]s", entrada[numEntrada]); // le cada linha
    } while (isFim(entrada[numEntrada++]) == false);
    // Desconsiderar ultima linha contendo a palavra FIM
    numEntrada--;
    // Para cada linha de entrada, gerando uma de saida verdadeira (SIM) ou falsa
    // (NAO)
    for (int i = 0; i < numEntrada; i++)
    {
        resp = isPalindromo(entrada[i], 0);
        if (resp == true)
        {
            printf("SIM\n");
        }
        else if (resp == false)
        {
            printf("NAO\n");
        }
    }
}
