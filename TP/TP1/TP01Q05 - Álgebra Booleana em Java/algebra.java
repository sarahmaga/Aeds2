class algebra {

    public static boolean isFim(String s) {
        return (s.length() >= 1 && s.charAt(0) == '0');
    }

    public static boolean algebra(String s) {
        boolean resp = false;

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

        // Para cada linha de entrada, gerando uma de saida contendo SIM ou
        // NAO

        // Para cada linha de entrada, gerando uma de saida contendo o numero de SIM ou
        // NAO
        for (int i = 0; i < numEntrada; i++) {

            if (algebra(entrada[i]) == false) {
                System.out.println("NAO");
            } else {
                System.out.println("SIM");
            }
        }
    }

}