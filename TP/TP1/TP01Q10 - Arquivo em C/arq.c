#include <stdio.h>   // para entradas e saida
#include <stddef.h>  // para definicoes basicas
#include <stdlib.h>  // para a biblioteca padrao
#include <string.h>  // para cadeias de caracteres
#include <stdarg.h>  // para tratar argumentos
#include <stdbool.h> // para definicoes logicas
#include <ctype.h>   // para tipos padroes
#include <math.h>    // para definicoes matematicas
#include <time.h>    // para medir tempo
#include <locale.h>  // para localizacoes diferentes

int freadSize(char* fileName) // le o tamanho do vetor de um arq
{
    // definir dados locais
    int n = 0;
    FILE *arq = fopen(fileName, "r");
    // ler a quantidade de dados
    do
    {
        scanf("%d", &n);

    } while (n <= 0);
    // retornar dado lido
    return (n);
} // freadArraySize ( )
void freadDouble(char* fileName, int n, float numeros) // le os valores de um arq
{
    // definir dados locais
    int x = 0, k = 0, i = 0;
    FILE *arq = fopen(fileName, "w");

    // ler e guardar valores
    while (!feof(arq) && x < n)
    {

        scanf("%g", &numeros);
        fprintf(arq, "%g\n", numeros);

        x++;
    }
    fclose(arq);

    // abrir o arquivo novamente para imprimir os valores em ordem reversa
    arq = fopen(fileName, "r");
    for (int i = 0; i < n; i++)
    {
        // seek começando do começo do arquivo
        fseek(arq, 0, SEEK_SET);
        
        for (int j = 1; j <= n - i; j++)
        {
            fscanf(arq, "%g\n", &numeros);

            if (j == (n - i))
            {
                printf("%g\n", numeros);
            }
        }
    }

    fclose(arq);
}
int main()
{
    // Para colocar pontos em vez de virgula nos numeros reais
    setlocale(LC_ALL, "English");

    // Declaracao de variaveis
    int n = 0;

    n = freadSize("test.txt");
    double numeros = 0.0;
    freadDouble("test.txt", n, numeros);

    return 0;
}