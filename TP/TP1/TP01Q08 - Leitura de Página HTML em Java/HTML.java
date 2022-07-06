import java.io.*;
import java.net.*;

class HTML {

    public static boolean isFim(String s) {
        return (s.length() >= 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static String getHtml(String endereco) {
        MyIO.setCharset("UTF-8");

        URL url;
        InputStream is = null;
        BufferedReader br;
        String resp = "", line;

        int x1 = 0, x2 = 0, x3 = 0, x4 = 0, x5 = 0, x6 = 0, x7 = 0, x8 = 0, x9 = 0, x10 = 0, x11 = 0, x12 = 0, x13 = 0,
                x14 = 0, x15 = 0,
                x16 = 0, x17 = 0, x18 = 0, x19 = 0, x20 = 0, x21 = 0, x22 = 0, x23 = 0, x24 = 0, x25 = 0;
        try {
            url = new URL(endereco);
            is = url.openStream(); // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                resp += line + "\n";

                for (int i = 0; i < line.length(); i++) {
                    int p = i;
                    if (line.charAt(i) == 'a') {
                        x1++;
                    } else if (line.charAt(i) == 'e') {
                        x2++;
                    } else if (line.charAt(i) == 'i') {
                        x3++;
                    } else if (line.charAt(i) == 'o') {
                        x4++;
                    } else if (line.charAt(i) == 'u') {
                        x5++;
                    } else if (line.charAt(i) == 'á') {
                        x6++;
                    } else if (line.charAt(i) == 'é') {
                        x7++;
                    } else if (line.charAt(i) == 'í') {
                        x8++;
                    } else if (line.charAt(i) == 'ó') {
                        x9++;
                    } else if (line.charAt(i) == 'ú') {
                        x10++;
                    }
                    if (line.charAt(i) == 'à') {
                        x11++;
                    } else if (line.charAt(i) == 'è') {
                        x12++;
                    } else if (line.charAt(i) == 'ì') {
                        x13++;
                    } else if (line.charAt(i) == 'ò') {
                        x14++;
                    } else if (line.charAt(i) == 'ù') {
                        x15++;
                    } else if (line.charAt(i) == 'ã') {
                        x16++;
                    } else if (line.charAt(i) == 'õ') {
                        x17++;
                    } else if (line.charAt(i) == 'â') {
                        x18++;
                    } else if (line.charAt(i) == 'ê') {
                        x19++;
                    } else if (line.charAt(i) == 'î') {
                        x20++;
                    } else if (line.charAt(i) == 'ô') {
                        x21++;
                    } else if (line.charAt(i) == 'û') {
                        x22++;

                    } else if (!(line.charAt(i) == 65 || line.charAt(i) == 'E' || line.charAt(i) == 'I'
                            || line.charAt(i) == 'O' || line.charAt(i) == 'U' || line.charAt(i) == 'a'
                            || line.charAt(i) == 'e' || line.charAt(i) == 'i'
                            || line.charAt(i) == 'o' || line.charAt(i) == 'u')
                            && ((line.charAt(i) >= 'A' && line.charAt(i) <= 'Z')
                                    || (line.charAt(i) >= 'a' && line.charAt(i) <= 'z'))) {

                        x23++;
                    } else if (line.charAt(i) == '<' && line.charAt(p + 1) == 'b' && line.charAt(p + 2) == 'r'
                            && line.charAt(p + 3) == '>') {
                        x24++;

                    } else if (line.charAt(p) == '<' && line.charAt(p + 1) == 't' && line.charAt(p + 2) == 'a'
                            && line.charAt(p + 3) == 'b' && line.charAt(p + 4) == 'l' && line.charAt(p + 5) == 'e'
                            && line.charAt(p + 6) == '>') {
                        x25++;
                    }
                }

            }
            System.out.print("a(" + x1 + ") ");
            System.out.print("e(" + x2 + ") ");
            System.out.print("i(" + x3 + ") ");
            System.out.print("o(" + x4 + ") ");
            System.out.print("u(" + x5 + ") ");
            System.out.print("á(" + x6 + ") ");
            System.out.print("é(" + x7 + ") ");
            System.out.print("í(" + x8 + ") ");
            System.out.print("ó(" + x9 + ") ");
            System.out.print("ú(" + x10 + ") ");
            System.out.print("à(" + x11 + ") ");
            System.out.print("è(" + x12 + ") ");
            System.out.print("ì(" + x13 + ") ");
            System.out.print("ò(" + x14 + ") ");
            System.out.print("ù(" + x15 + ") ");
            System.out.print("ã(" + x16 + ") ");
            System.out.print("õ(" + x17 + ") ");
            System.out.print("â(" + x18 + ") ");
            System.out.print("ê(" + x19 + ") ");
            System.out.print("î(" + x20 + ") ");
            System.out.print("ô(" + x21 + ") ");
            System.out.print("û(" + x22 + ") ");
            System.out.print("consoante(" + x23 + ") ");
            System.out.print("<br>(" + x24 + ") ");
            System.out.print("<table>(" + x25 + ") ");

        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        try {
            is.close();
        } catch (IOException ioe) {
            // nothing to see here

        }

        return resp;
    }

    public static void main(String[] args) {
        // Declaracao de variaveis
        String entrada[] = new String[1000];
        String html;
        int numEntrada = 0, j = 1, k = 0;

        // Leitura da entrada padrao
        do {
            entrada[numEntrada] = MyIO.readLine();

        } while (isFim(entrada[numEntrada++]) == false);

        // Desconsiderar ultima linha contendo a palavra FIM
        numEntrada--;

        // Para cada linha de entrada, gerando uma de saida contendo o numero de SIM ou
        // NAO

        for (int i = 0; i < (numEntrada / 2); i++) {

            html = getHtml(entrada[j]);

            j += 2;
            System.out.println(entrada[k]);
            k += 2;

        }
        // System.out.print("a(" + x1 + ") ");

    }

}