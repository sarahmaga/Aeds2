import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Filme {
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
    private String dataFormatada;
     
    // Construtor
    public Filme() throws ParseException {
        this.nome = "nome";
        this.tituloOriginal = "tituloOriginal";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.data = null;
        this.duracao = 0;
        this.genero = "genero";
        this.idiomaOriginal = "idiomaOriginal";
        this.situacao = "situacao";
        this.orcamento = 0;
        
        // this.palavrasChave[];
        // for (int i = 0; i < 2; i++)
        // this.palavrasChave[i] += "";

    }

    // Construtor que recebe o nome do arquivo.html
    public Filme(String arqFilmeNome) throws ParseException {
        this.nome = "nome";
        this.tituloOriginal = "tituloOriginal";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        data = null;
        this.duracao = 0;
        this.genero = "genero";
        this.idiomaOriginal = "idiomaOriginal";
        this.situacao = "situacao";
        this.orcamento = 0;
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

    public Filme cloneFilme() throws ParseException {
        Filme clonado = new Filme();
        clonado.setNome(nome);
        clonado.setTituloOriginal(tituloOriginal);
        clonado.setData((data));
        clonado.setDuracao(duracao);
        clonado.setGenero(genero);
        clonado.setIdiomaOriginal(idiomaOriginal);
        clonado.setSituacao(situacao);
        clonado.setOrcamento(orcamento);
        clonado.setPalavrasChave(palavrasChave);
        return clonado;
    }

    public String removeTags(String original) {
        String remover = "";
        for (int i = 0; i < original.length(); i++) { // tags de abertuda e fechamento <>

            if (original.charAt(i) == '<') {
                while (original.charAt(i) != '>')
                    i++;
            } else {
                remover += original.charAt(i);
            }
        }
        return remover;
    }

    /**
     * Metodo para leitura de arquivo
     * 
     * @param s nome do arquivo
     */
    public void lerArq(String s) {
        try {
            // Setando o leitor de arquivos
            BufferedReader arq = new BufferedReader(new FileReader(s));
            for (String linha; (linha = arq.readLine()) != null;) {
                // Condiçcoes que verificam padrões usando metodos da classe String, Pattern e
                // Matcher
                // Metodo para pegar o nome
                if (linha.contains("<title>")) { // Nome
                    Pattern padrao = Pattern.compile("<title>(.+?)\\(", Pattern.DOTALL);
                    Matcher match = padrao.matcher(linha);
                    match.find();
                    
                    this.nome = match.group(1).strip().replaceAll("&amp;", "");
                    
                    // Metodo para pegar o titulo original
                } else if (linha.contains("<strong>Título original</strong>")) {
                    Pattern padrao = Pattern.compile("<strong>Título original</strong> (.+?)\\</p>", Pattern.DOTALL);
                    Matcher match = padrao.matcher(linha);
                    match.find();
                    this.tituloOriginal = match.group(1).strip();
                }
                // Metodo para pegar a data de lançameto
                else if (linha.contains("<span class=\"release\">")) {
                linha =arq.readLine();
                // System.out.println(linha);
                this.dataFormatada = linha.replaceAll("<(.+?)>\\(","").strip().replaceAll(" \\((.+?)\\)", "".strip());
                // System.out.println("Cheguei");
                //System.out.println(dataFormatada);


                }
                // Metodo para pegar a duracao
                else if (linha.contains("<span class=\"runtime\">")) {
                    arq.readLine();
                    int h, m;
                    String aux = arq.readLine();
                     //while (!linha.contains("h")){
                    // System.out.println(aux);
                    // aux = aux.replaceAll("h", "");
                    // h = Integer.parseInt(aux);
                    
                     //}
                     aux = aux.replaceAll("h", "");
                    aux = aux.replaceAll(" ", "");
                    aux = aux.replaceAll("m", "");
                    try {
                        this.duracao = Integer.parseInt(aux);
                    } catch (Exception e) {
                    }
                    String[] digits = aux.split("(?<=.)");
                    for (int i=0 ; i < digits.length; i++){
                    if(digits.length > 2){
                        h = Integer.parseInt(digits[0])*60;
                        m = Integer.parseInt(digits[1])*10 + Integer.parseInt(digits[2]) ;
                        this.duracao = h + m;
                        
                    }    
                    //System.out.println(digits[i]);
                    }
                    //System.out.println(duracao);
                    

                }
                // Metodo para pegar os generos
                else if (linha.contains("<span class=\"genres\">")) {
                    arq.readLine();
                    linha = arq.readLine();
                    this.genero = linha.replaceAll("<(.+?)>","").replaceAll("\\&nbsp;","").strip();

                }
                // Metodo para pegar o idioma original
                else if (linha.contains("<p><strong><bdi>Idioma original</bdi></strong>")) {
                    Pattern padrao = Pattern.compile("<p><strong><bdi>Idioma original</bdi></strong> (.+?)\\</p>",Pattern.DOTALL);
                    Matcher match = padrao.matcher(linha);
                    match.find();
                    this.idiomaOriginal = match.group(1).strip();

                }
                // Metodo para pegar a situação
                else if (linha.contains("Situação</bdi></strong>")) {
                    this.situacao = linha.split("</strong>")[1].strip();
                    
                }

                // Metodo para pegar o orcamento
                else if (linha.contains("<p><strong><bdi>Orçamento</bdi></strong>")) {

                    Pattern padrao = Pattern.compile("<p><strong><bdi>Orçamento</bdi></strong> (.+?)</p>",Pattern.DOTALL);
                    Matcher match = padrao.matcher(linha);
                    match.find();
                    String aux = "";
                    aux = match.group(1).strip();
                    aux = aux.replaceAll(",", "");
                    aux = aux.replaceAll("\\$", "");
                    try {
                        this.orcamento = Float.parseFloat(aux);
                    } catch (Exception e) {
                    }
                }
                // // Metodo para pegar as palavras chave
                else if (linha.contains("<section class=\"keywords right_column\">")) {
                    String aux= "";
                 while(!linha.contains("<ul>")){
                     linha = arq.readLine();
            
                 }
               
                 while (!linha.contains("</ul>")){
                 //System.out.println(arq==null);
                 //System.out.println(linha);
                 
                 
                 linha = arq.readLine();
                 
                 linha= arq.readLine();
                 if(!linha.isEmpty()){
                 
                 aux += linha.replaceAll("<(.+?)>", "").trim() +",";

                 }
                 
                 
                  //System.out.println(linha);
                
                  }

                  this.palavrasChave = aux.split(",");
                for (int i= 0; i <palavrasChave.length;i++){
                 System.out.printf(palavrasChave[i]);
                 if(i!= palavrasChave.length - 1){
                     System.out.printf(", ");
                 }
                }
                  //System.out.println(aux);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo 1 para impressão
     * 
     * @return instâncias
     */
    public String imprimir_1() {
        return this.nome + ", " + this.tituloOriginal + ", " + this.data + ", " + this.duracao + ", "
                + this.genero + ", " + this.idiomaOriginal + ", " + this.situacao + ", " + this.orcamento + ", ";
                //this.palavrasChave;
    }

    /**
     * Metodo 2 do tipo void para impressão
     */
    public void imprimir_2() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println(
                this.nome + ", " + this.tituloOriginal + ", " + (this.dataFormatada) + ", " + this.duracao + ", "
                        + this.genero + ", " + this.idiomaOriginal + ", " + this.situacao + ", " + this.orcamento + ", ");
                        //+ this.palavrasChave);
    }

}

class Main {

    public static boolean isFim(String s) {
        return (s.length() >= 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static void main(String[] args) throws ParseException, IOException {
        MyIO.setCharset("UTF-8");
        // Declaraco de variaveis
        String entrada[] = new String[1000];
        int numEntrada = 0;
        // Leitura da entrada padrao
        do {

            entrada[numEntrada] = MyIO.readLine();
            //System.out.println(entrada[numEntrada]);
            // if (isFim(entrada[numEntrada]) == true) {
            //     break;
            // }
            // if(entrada[numEntrada].contains("Witch")){
            //     break;
            // }

        } while (isFim(entrada[numEntrada++]) == false);
        // // Desconsiderar ultima linha contendo a palavra FIM
        numEntrada--;

        for (int i = 0; i < numEntrada; i++) {
            File arqFilme = new File(entrada[i]);
            Filme atual = new Filme("tmp/filmes/" + arqFilme.getName());
            // System.out.println(atual.imprimir_1());
           // atual.imprimir_2();
        }

        // for (File arqFilme : diretorioFilmes.listFiles()) {

        // }

    }
}
