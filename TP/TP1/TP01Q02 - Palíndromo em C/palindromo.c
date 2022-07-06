#include <stdio.h>   // para entradas e saida
#include <stddef.h>  // para definicoes basicas
#include <stdlib.h>  // para a biblioteca padrao
#include <string.h>  // para cadeias de caracteres
#include <stdarg.h>  // para tratar argumentos
#include <stdbool.h> // para definicoes logicas
#include <ctype.h>   // para tipos padroes
#include <math.h>    // para definicoes matematicas
#include <time.h>    // para medir tempo

size_t string_lengh(const char *s) {
size_t count = 0;
while (*s) {
count += (*s++ & 0xC0) != 0x80;
}
return count;
}


bool isFim(char *s)
{
    return (string_lengh(s) >= 3 && s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}
// metodo para verificar se a palavra e palindromo
bool isPalindromo(char *s)
{
    // Declaracao de variaveis
    bool resp = true;
    // Metodo iterativo
    for (int i = 0; i < string_lengh(s)/2; i++)
    {
        if (s[i] != s[string_lengh(s) - (i + 1)])// condição é falsa (compara o caracter da esquerda com o da direita sucessivamente)
        { 
            resp = false;
            break;
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
        resp = isPalindromo(entrada[i]);
        if (resp == true)
        {
            printf("SIM\n");
        }
        else
        {
            printf("NAO\n");
        }
    }
}
