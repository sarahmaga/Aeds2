
class Is {

    public static boolean isFim(String s) {
        return (s.length() >= 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    // Testa se a string e composta somente por vogais
    public static boolean isVogal(String s, int i) {
        boolean resp = true;
        // Metodo recursivo
        if (i < s.length()) {

            char d = s.charAt(i);
            if ((d == 65 || d == 'E' || d == 'I' || d == 'O' || d == 'U' || d == 'a' || d == 'e' || d == 'i' || d == 'o'
                    || d == 'u') == false) {

                resp = false; // caso a funcao encontre um caractere "falso" ela se encerra
            } else {
                resp = true;
                resp = isVogal(s, i + 1);

            }

        }

        return resp;
    }

    // Testa se a string eh composta somente por consoantes
    public static boolean isConso(String s, int i) {
        boolean resp = true;
        // Metodo recursivo
        if (i < s.length()) {
            char d = s.charAt(i);
            if ((!(d == 65 || d == 'E' || d == 'I' || d == 'O' || d == 'U' || d == 'a' || d == 'e' || d == 'i'
                    || d == 'o' || d == 'u') && ((d >= 'A' && d <= 'Z') || (d >= 'a' && d <= 'z'))) == false) {
                resp = false; // caso a funcao encontre um caractere "falso" ela se encerra

            } else {
                resp = true;
                resp = isConso(s, i + 1);

            }

        }

        return resp;
    }

    // Testa se a string eh composta somente por inteiros
    public static boolean isInt(String s, int i) {
        boolean resp = true;
        // Metodo recursivo
        if (i < s.length()) {
            char d = s.charAt(i);
            if ((d >= '0' && d <= '9') == false) {
                resp = false; // caso a funcao encontre um caractere "falso" ela se encerra

            } else {
                resp = true;
                resp = isInt(s, i + 1);

            }

        }

        return resp;
    }

    // Testa se a string eh composta somente por reais
    public static boolean isReal(String s, int i) {
        boolean resp = true;
        int numeroSinais = 0;
        // Metodo recursivo
        if (i < s.length()) {
            char d = s.charAt(i);
            if (d == ',' || d == '.') {

                numeroSinais++; // conta o numero de sinais

            } else if (!(d >= '0' && d <= '9')) {
                resp = false; // caso a funcao encontre um caractere "falso" ela se encerra
                i = s.length();

            } else {
                resp = true;
                resp = isReal(s, i + 1);

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
        // Setando charset
        MyIO.setCharset("UTF-8");
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

            if (isVogal(entrada[i], 0) == false) {
                System.out.print("NAO ");
            } else {
                System.out.print("SIM ");
            }

            if (isConso(entrada[i], 0) == false) {
                System.out.print("NAO ");
            } else {
                System.out.print("SIM ");
            }
            if (isInt(entrada[i], 0) == false) {
                System.out.print("NAO ");
            } else {
                System.out.print("SIM ");
            }
            if (isReal(entrada[i], 0) == false) {
                System.out.println("NAO");
            } else {
                System.out.println("SIM");
            }
        }
    }
}