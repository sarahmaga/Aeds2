
import java.io.RandomAccessFile;
import java.util.Locale;

class arq {

    public static void main(String[] args) {
        // Para colocar pontos em vez de virgula nos numeros reais
        Locale.setDefault(new Locale("en", "US"));
        // Declaracao de variaveis
        double numeros = 0.0;
        int n = 0;
        // le numero de linhas
        n = MyIO.readInt();
        // le e salva as linhas do arquivo
        try {
            RandomAccessFile arq = new RandomAccessFile("test.txt", "rw");
            for (int i = 0; i < n; i++) {
                numeros = MyIO.readDouble();
                arq.writeDouble(numeros);
            }
            arq.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // le e salva as linhas do arquivo de tras pra frente
        try {
            RandomAccessFile arq = new RandomAccessFile("test.txt", "rw");
            for (int i = 1; i <= n; i++) {
                arq.seek(arq.length() - (i * 8));

                numeros = arq.readDouble();
                // testa se um valor e inteiro ou double
                if (numeros == (int) numeros) {
                    MyIO.println((int) numeros);
                } else {
                    MyIO.println(numeros);
                }

            }
            arq.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}