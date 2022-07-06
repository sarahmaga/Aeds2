#include <stdio.h>   // para entradas e saida
#include <stddef.h>  // para definicoes basicas
#include <stdlib.h>  // para a biblioteca padrao
#include <string.h>  // para cadeias de caracteres
#include <stdarg.h>  // para tratar argumentos
#include <stdbool.h> // para definicoes logicas
#include <ctype.h>   // para tipos padroes
#include <math.h>    // para definicoes matematicas
#include <time.h>    // para medir tempo

size_t string_lengh(const char *s)
{
    size_t count = 0;
    while (*s)
    {
        count += (*s++ & 0xC0) != 0x80;
    }
    return count;
}

bool isFim(char *s)
{
    return (string_lengh(s) >= 3 && s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}

// Testa se a string eh composta somente por vogais
bool isVogal(char *s)
{
    bool resp = true;

    for (int i = 0; i < strlen(s); i++)
    {

        char d = s[i];
        if ((d == 65 || d == 'E' || d == 'I' || d == 'O' || d == 'U' || d == 'a' || d == 'e' || d == 'i' || d == 'o' || d == 'u') == false)
        {
            resp = false;
        }
    }

    return resp;
}

// Testa se a string eh composta somente por consoantes
bool isConso(char *s)
{
    bool resp = true;

    for (int i = 0; i < strlen(s); i++)
    {

        char d = s[i];
        if ((!(d == 65 || d == 'E' || d == 'I' || d == 'O' || d == 'U' || d == 'a' || d == 'e' || d == 'i' || d == 'o' || d == 'u') && ((d >= 'A' && d <= 'Z') || (d >= 'a' && d <= 'z'))) == false)
        {
            resp = false;
        }
    }

    return resp;
}

// Testa se a string eh composta somente por inteiros
bool isInt(char *s)
{
    bool resp = true;

    for (int i = 0; i < strlen(s); i++)
    {

        char d = s[i];
        if ((d >= '0' && d <= '9') == false)
        {
            resp = false;
        }
    }

    return resp;
}

// Testa se a string eh composta somente por reais
bool isReal(char *s)
{
    bool resp = true;
    int numeroSinais = 0;

    for (int i = 0; i < strlen(s); i++)
    {

        char d = s[i];
        if (d == ',' || d == '.')
        {

            numeroSinais++; // conta o numero de sinais
        }
        else if (!(d >= '0' && d <= '9'))
        {
            resp = false;
            i = strlen(s);
        }
    }
    if (resp == true)
    {
        if (numeroSinais > 1)
        { // se a string possuir mais de um sinal nao e real
            resp = false;
        }
    }

    return resp;
}

int main()
{
    // Declaracao de variaveis
    char entrada[1000][100];
    int numEntrada = 0;
    
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
        if (isVogal(entrada[i]) == false) 
        {
            printf("NAO ");
        }
        else
        {
            printf("SIM ");
        }
        if (isConso(entrada[i]) == false)
        {
            printf("NAO ");
        }
        else
        {
            printf("SIM ");
        }
        if (isInt(entrada[i]) == false)
        {
            printf("NAO ");
        }
        else
        {
            printf("SIM ");
        }
        if (isReal(entrada[i]) == false)
        {
            printf("NAO\n");
        }
        else
        {
            printf("SIM\n");
        }
    }
}