import java.nio.charset.Charset;// correcao para o charset

class Palindromo {

    public static boolean isFim(String s) {
        return (s.length() >= 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    // metodo para verificar se a palavra e palindromo
    public static boolean isPalindromo(String s) {
        // Declaracao de variaveis
        boolean resp = true;
        // Correcao em erros do charset
        final Charset fromCharset = Charset.forName("windows-1252");
        final Charset toCharset = Charset.forName("UTF-8");
        s = new String(s.getBytes(fromCharset), toCharset);
        // Metodo iterativo
        for (int i = 0; i < s.length() / 2; i++) {

            if (s.charAt(i) != s.charAt(s.length() - (i + 1))) {

                resp = false;
                break;
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

        // Para cada linha de entrada, gerando uma de saida contendo o numero de SIM ou
        // NAO
        for (int i = 0; i < numEntrada; i++) {

            if (isPalindromo(entrada[i]) == false) {
                System.out.println("NAO");
            } else {
                System.out.println("SIM");
            }
        }
    }
}