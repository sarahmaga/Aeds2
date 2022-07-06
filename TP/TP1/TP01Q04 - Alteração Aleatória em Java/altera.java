import java.util.Random;

class altera {

    public static boolean isFim(String s) {
        return (s.length() >= 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static String altera_a(String s, Random gerador) {

        // Declaracao de variaveis
        char primeira, segunda;
        String resp = "";

        // Setando o gerador
        primeira = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));
        segunda = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));

        // Percorrendo e trocando os caracteres
        for (int i = 0; i < s.length(); i++) {

            if (s.charAt(i) == primeira) {
                resp += segunda;
            } else {
                resp += s.charAt(i);
            }
        }
        return resp;
    }

    public static void main(String[] args) {
        // Criando o gerador para servir de paramentro para a funcao altera_a
        Random gerador = new Random();
        gerador.setSeed(4);
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

        // Para cada linha de entrada, gerando a String alterada
        for (int i = 0; i < numEntrada; i++) {
            MyIO.println(altera_a(entrada[i], gerador));

        }
    }
}