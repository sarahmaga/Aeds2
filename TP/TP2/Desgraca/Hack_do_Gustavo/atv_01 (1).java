import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

class Filme implements Cloneable {
    // Declaracao de variaveis
    private String nome;
    private String tituloOriginal;
    private Date data;
    private int duracao;
    private String genero;
    private String idiomaOriginal;
    private String situacao;
    private float orcamento;
    private String[] palavrasChave;

    // Construtor
    public Filme() {

    }

    public Filme(String arqFilmeNome) { // Construtor que recebe o nome do arquivo.html
        lerArq(arqFilmeNome);
    }

    // Comeco dos metodos get e set
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTituloOriginal() {
        return tituloOriginal;
    }

    public void setTituloOriginal(String tituloOriginal) {
        this.tituloOriginal = tituloOriginal;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
        // SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getIdiomaOriginal() {
        return idiomaOriginal;
    }

    public void setIdiomaOriginal(String idiomaOriginal) {
        this.idiomaOriginal = idiomaOriginal;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public float getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(float orcamento) {
        this.orcamento = orcamento;
    }

    public String[] getPalavrasChave() {
        return palavrasChave;
    }

    public void setPalavrasChave(String[] palavrasChave) {
        this.palavrasChave = palavrasChave;
    }
    // Fim dos metodos get e set

    // Metodo de clonagem
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    // Metodo para leitura de arquivo
    public void lerArq(String s) {
        try {
            BufferedReader arq = new BufferedReader(new FileReader(s));
            for (String linha; (linha = arq.readLine()) != null;) {
                if (linha.contains("<title>")) {
                    // System.out.println(linha);
                    this.nome = linha;
                    // Pattern padrao = Pattern.compile("<title>(.+?))");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String imprimir() {
        return this.nome;

        // + ", " + this.tituloOriginal + ", " + this.data + ", " + this.duracao + ", "
        // + this.genero
        // + ", " + this.idiomaOriginal + ", " + this.situacao + ", " + this.orcamento +
        // this.palavrasChave + ", ";
    }

}

class atv_01 {
    
    public static boolean isFim(String s) {
        return (s.length() >= 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }
    public static void main(String[] args) throws CloneNotSupportedException, ParseException, IOException {
        String entrada[] = new String[1000];
        int numEntrada = 0;
        MyIO.setCharset("UTF-8");
        // Leitura da entrada padrao
        do {
            entrada[numEntrada] = MyIO.readLine();
            System.out.println(entrada[numEntrada]);
            if(entrada[numEntrada].contains("Witch"))
            break;

        } while (isFim(entrada[numEntrada++]) == false);
        // Desconsiderar ultima linha contendo a palavra FIM
        numEntrada--;
        String currentPath = new java.io.File(".").getCanonicalPath();
        System.out.println("Current dir:" + currentPath);
        File diretoAtual = new File (currentPath);
        // for (int i = 0; i < numEntrada; i++) {
        // File diretorioFilmes = new File(entrada[i]);
        // if(diretorioFilmes.exists() ){
        //     System.out.println("existo"+diretorioFilmes.getName());
        // }
        // else{
        //     System.out.println("nao existo"+diretorioFilmes.getName());
        // }

        // }

        // Declaraco de variaveis
        // Locale.setDefault(new Locale("pt", "BR"));
        // Filme bee = new Filme();
        // Filme Oz = new Filme();
        // // String[] s = { "lala", "papa" };
        // File diretorioFilmes = new File("tmp/filmes");
        if(diretoAtual==null){
            System.out.println("diretoAtual Nulo");
            return;
        }
        if(diretoAtual.listFiles()==null){
            System.out.println("diretoAtual Arq Nulo");
            return;
        }
        for (File arqFilme : diretoAtual.listFiles()) {
            // Filme atual = new Filme("tmp/filmes/" + arqFilme.getName());
            // // System.out.println(atual.imprimir());
            // // String linha;
            // // linha = MyIO.readLine();
            // // System.out.println(linha);
            System.out.println(arqFilme.getName());
        }

        // bee.lerArq("tmp/filmes/007_ Sem Tempo para Morrer.html");
        // Oz.lerArq("tmp/filmes/A Era do Gelo_ As Aventuras de Buck.html");
        // System.out.println("Imprimir: "+Batman.imprimir());h
        // System.out.println("Imprimir: "+Oz.imprimir());

        // System.out.println("System: "+bee.getNome());
        // System.out.println("System: "+Oz.getNome());
        // SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        // Date data = sdf.parse("30/09/2021");
        // System.out.println(sdf.format(bee.getData()));
        // for (String ss : s) {
        // System.out.println(ss);
        // }

    }
}

/**
 * import java.text.SimpleDateFormat;
 * 
 * import java.util.Date;
 * 
 * import java.util.regex.Matcher;
 * 
 * import java.util.regex.Pattern;
 * 
 * 
 * 
 * 
 * public class Main {
 * 
 * public static void main(String[] args) throws Exception {
 * 
 * 
 * 
 * String s = "
 * <p>
 * <strong><bdi>Orçamento</bdi></strong> $250,000,000.00
 * </p>
 * ";
 * 
 * Pattern p = Pattern.compile("Orçamento</bdi></strong>(.+?)
 * </p>
 * ", Pattern.DOTALL);
 * 
 * Matcher m = p.matcher(s);
 * 
 * m.find();
 * 
 * 
 * 
 * System.out.println(m.group(1).strip());
 * 
 * 
 * 
 * }
 * 
 * }
 */

/**
 * if (line.contains("<section class=\"content_score\">")) {
 * 
 * break;
 * 
 * }
 */