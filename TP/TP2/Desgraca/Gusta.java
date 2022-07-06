import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        
        // Exemplo de Strip
        String s = " Nome do filme     ";
        System.out.println("->" + s + "<-");
        System.out.println("->" +s.strip()+ "<-");

        // Exemplo de parse para float
        String orcamento = "$250,000,000.00";

        orcamento = orcamento.replaceAll(",","");
        orcamento = orcamento.replaceAll("\\$","");

        float f = 0f;
        try {
            f = Float.parseFloat(orcamento);
        } catch(Exception e) {
            System.out.println("Erro");
        }

        System.out.println(f);

        // Exemplo de pattern para retirar multiplos padroes
        String generos = "<a href=\"/genre/12-aventura/movie\">Aventura</a>,&nbsp;<a href=\"/genre/28-acao/movie\">Ação</a>,&nbsp;<a href=\"/genre/53-thriller/movie\">Thriller</a>";

        Pattern p3 = Pattern.compile("<a href=(.+?)</a>");
        Matcher m3 = p3.matcher(generos);

        String generos_separados = "";
        
        while (m3.find()) {
            
            System.out.println(m3.group(1));
            String[] padroes = m3.group(1).split(">");
            for(String ss : padroes) {
                System.out.println(ss);
            }

            generos_separados += m3.group(1).split(">")[1].strip() + ", ";
        }
        
        System.out.println(generos_separados);
        
        String palavras_chaves_seccao = "<section class=\"keywords right_column\">" + 
        "<h4><bdi>Palavras-chave</bdi></h4>"+
          "<ul>" +
              "<li><a href=\"/keyword/470-spy/movie?language=pt-BR\">spy</a></li>" +
             " <li><a href=\"/keyword/156095-british-secret-service/movie?language=pt-BR\">british secret service</a></li>"+
              "<li><a href=\"/keyword/219965-nanobots/movie?language=pt-BR\">nanobots</a></li>"+
              "<li><a href=\"/keyword/244739-007/movie?language=pt-BR\">007</a></li>"+
          "</ul>"+
      "</section>";

      Pattern p = Pattern.compile("<section class=\"keywords right_column\">(.+?)</section>");
      Matcher m = p.matcher(palavras_chaves_seccao);
      m.find();
      String palavras_chaves = m.group(1);
    
      Pattern p2 = Pattern.compile("<li><a href=(.+?)</a>");
      Matcher m2 = p2.matcher(palavras_chaves); 

      String palavras_chaves_separadas = "";

      while(m2.find()) {
        palavras_chaves_separadas += m2.group(1).split(">")[1].strip() + ", ";
      }

      System.out.println(palavras_chaves_separadas);
    }
}