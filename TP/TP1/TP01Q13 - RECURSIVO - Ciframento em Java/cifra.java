
class cifra {

    public static boolean isFim(String s) {
        return (s.length() >= 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static String cifra_m(String s, int n) {

        // Declaracao de variaveis
        String resp = "";
        int chave = 3, cifra = 0;
        char letra;
        // Percorrer a String para retornar outra
        if (n < s.length()) {

            cifra = ((int) s.charAt(n)) + chave; // transforma o char na posicao (i) no tipo int, e soma com a chave
            letra = (char) cifra; // devolve o tipo int para char novamente
            resp = resp + "" + letra; // concatena os resultados e forma a String
            resp += "" + cifra_m(s, n + 1); // chama a funcao cifra_m novamente para dar continuidade ao programa

        }

        return resp;
    }

    public static void main(String[] args) {
        // Declaracao de variaveis
        String entrada[] = new String[1000];
        int numEntrada = 0;
        // Leitura da entrada padrao
        do {
            entrada[numEntrada] = MyIO.readLine();

        } while (isFim(entrada[numEntrada++]) == false);
        // Desconsiderar ultima linha contendo a palavra FIM
        numEntrada--;

        // Para cada linha de entrada, gerando uma String cifrada
        for (int i = 0; i < numEntrada; i++) {
            MyIO.println(cifra_m(entrada[i], 0));
        }
    }
}