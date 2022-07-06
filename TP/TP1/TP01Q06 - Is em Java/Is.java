class Is {

    public static boolean isFim(String s) {
        return (s.length() >= 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    // Testa se a string eh composta somente por vogais
    public static boolean isVogal(String s) {
        boolean resp = true;

        for (int i = 0; i < s.length(); i++) {

            char d = s.charAt(i);
            if ((d == 65 || d == 'E' || d == 'I' || d == 'O' || d == 'U' || d == 'a' || d == 'e' || d == 'i' || d == 'o'
                    || d == 'u') == false) {
                resp = false;

            }

        }

        return resp;
    }

    // Testa se a string eh composta somente por consoantes
    public static boolean isConso(String s) {
        boolean resp = true;

        for (int i = 0; i < s.length(); i++) {

            char d = s.charAt(i);
            if ((!(d == 65 || d == 'E' || d == 'I' || d == 'O' || d == 'U' || d == 'a' || d == 'e' || d == 'i'
                    || d == 'o' || d == 'u') && ((d >= 'A' && d <= 'Z') || (d >= 'a' && d <= 'z'))) == false) {
                resp = false;

            }

        }

        return resp;
    }

    // Testa se a string eh composta somente por inteiros
    public static boolean isInt(String s) {
        boolean resp = true;

        for (int i = 0; i < s.length(); i++) {

            char d = s.charAt(i);
            if ((d >= '0' && d <= '9') == false) {
                resp = false;

            }

        }

        return resp;
    }

    // Testa se a string eh composta somente por reais
    public static boolean isReal(String s) {
        boolean resp = true;
        int numeroSinais = 0;

        for (int i = 0; i < s.length(); i++) {

            char d = s.charAt(i);
            if (d == ',' || d == '.') {

                numeroSinais++; // conta o numero de sinais

            } else if (!(d >= '0' && d <= '9')) {
                resp = false;
                i = s.length();

            }

        }
        if (resp == true) {
            if (numeroSinais > 1) { // se a string possuir mais de um sinal nao e real
                resp = false;
            }
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

        // Para cada linha de entrada, gerando uma de saida contendo SIM ou
        // NAO
        for (int i = 0; i < numEntrada; i++) {

            if (isVogal(entrada[i]) == false) {
                System.out.print("NAO ");
            } else {
                System.out.print("SIM ");
            }

            if (isConso(entrada[i]) == false) {
                System.out.print("NAO ");
            } else {
                System.out.print("SIM ");
            }
            if (isInt(entrada[i]) == false) {
                System.out.print("NAO ");
            } else {
                System.out.print("SIM ");
            }
            if (isReal(entrada[i]) == false) {
                System.out.println("NAO");
            } else {
                System.out.println("SIM");
            }
        }
    }
}