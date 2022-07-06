import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    // Construtor
    public Filme() throws ParseException {
        this.nome = "nome";
        this.tituloOriginal = null;
        // this.data;
        this.duracao = 0;
        this.genero = "genero";
        this.idiomaOriginal = "idiomaOriginal";
        this.situacao = "situacao";
        this.orcamento = 0;

    }

    // Construtor que recebe o nome do arquivo.html
    public Filme(String arqFilmeNome) throws ParseException {
        this.nome = "nome";
        this.tituloOriginal = null;
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
    public void lerArq(String s) throws ParseException {
        try {
            // Setando o leitor de arquivos
            BufferedReader arq = new BufferedReader(new FileReader(s));
            String linha = "";
            while (!linha.contains("<section class=\"content_score\">")) {
                linha = arq.readLine();
                // Metodo para pegar o nome

                if (linha.contains("<h2")) {
                    // while (!linha.contains("</h2")) {
                    linha = arq.readLine().trim();
                    this.nome = removeTags(linha);

                    this.nome = this.nome.replace("&amp;", "");

                    this.nome = this.nome.replace(
                            "Inclui filmes como Homem-Aranha: De Volta ao Lar, Homem-Aranha: Longe de Casa, ", "");
                    // }
                    // System.out.println(nome);

                }
                // Metodo para pegar a duracao
                else if (linha.contains("<span class=\"runtime\">")) {
                    arq.readLine();
                    int h = 0, m = 0;
                    String aux = arq.readLine();
                    String aux2 = aux;
                    String a = "";
                    String b = "";
                    aux = aux.replace("h", "").replace("m", "").replace(" ", "").trim();
                    aux2 = aux2.replace(" ", "").trim();
                    // System.out.println(aux);
                    // System.out.println(aux2);
                    // Caso o filme tenha xh 10 minutos pra cima
                    if (aux2.contains("h")) {
                        if (aux.length() == 3) {
                            // System.out.println("1");
                            a += aux.charAt(0);

                            try {
                                h = Integer.parseInt(a);
                                h = h * 60;
                            } catch (Exception e) {
                            }

                            b += aux.charAt(1);
                            b += aux.charAt(2);
                            try {
                                m = Integer.parseInt(b);
                            } catch (Exception e) {
                            }
                            this.duracao = h + m;

                        }
                        // Caso o filme tenha ate xh 9 minutos
                        else if (aux.length() == 2) {
                            // System.out.println(aux);
                            // System.out.println("2");
                            a += aux.charAt(0);

                            try {
                                h = Integer.parseInt(a);
                                h = h * 60;
                            } catch (Exception e) {
                            }
                            b += aux.charAt(1);
                            try {
                                m = Integer.parseInt(b);
                            } catch (Exception e) {
                            }
                            this.duracao = h + m;
                        }
                    } else {
                        // Caso o filme tenha ate 59m
                        if (aux.length() == 2) {
                            // System.out.println("3");
                            a += aux.charAt(0);
                            a += aux.charAt(1);
                            try {
                                m = Integer.parseInt(a);
                            } catch (Exception e) {
                            }
                            this.duracao = m;
                        }
                        // Caso o filme tenha duracao de ate 9 minutos
                        else if (aux.length() == 1) {
                            // System.out.println("4");
                            a += aux.charAt(0);
                            try {
                                this.duracao = Integer.parseInt(a);
                            } catch (Exception e) {
                            }
                        }
                    }
                }

                // Metodo para pegar o titulo original
                else if (linha.contains("<strong>Título original</strong>")) {
                    // linha = arq.readLine().trim();
                    linha = linha.replace("Título original", "");
                    this.tituloOriginal = removeTags(linha).trim();
                    // System.out.println(tituloOriginal);
                }

                // Metodo para pegar a data
                else if (linha.contains("<span class=\"release\">")) {
                    String aux = "";
                    aux = arq.readLine();
                    aux = aux.trim();

                    aux = aux.replace("(BR)", "");

                    aux = aux.replace("(AU)", "");

                    aux = aux.replace("(US)", "");

                    aux = aux.replace("(RU)", "");

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // problema
                    this.data = sdf.parse(aux);
                    // System.out.println(aux);
                    // System.out.println(sdf.format(this.data));
                }
                // Metodo para pegar os generos
                else if (linha.contains("<span class=\"genres\">")) {
                    arq.readLine();
                    linha = arq.readLine();

                    this.genero = removeTags(linha).trim();

                    this.genero = this.genero.replace("&nbsp;", "");

                    // System.out.println(this.genero);
                }
                // Metodo para pegar o idioma original
                else if (linha.contains("<p><strong><bdi>Idioma original</bdi></strong>")) {
                    linha = linha.replace("Idioma original", "");
                    this.idiomaOriginal = removeTags(linha).trim();
                    // System.out.println(this.idiomaOriginal);
                }
                // Metodo para pegar a situação
                else if (linha.contains("Situação</bdi></strong> ")) {

                    linha = linha.replace("Situação", "").replace(" ", "");
                    this.situacao = removeTags(linha);
                    // System.out.println(this.situacao);
                }
                // Metodo para pegar o orcamento
                else if (linha.contains("<p><strong><bdi>Orçamento</bdi></strong>")) {
                    linha = linha.replace("Orçamento", "").replace(" ", "");
                    linha = removeTags(linha);
                    // System.out.println(aux);

                    linha = linha.replace(",", "");

                    linha = linha.replace("$", "");

                    // System.out.println(linha);
                    try {
                        this.orcamento = Float.parseFloat(linha);
                    } catch (Exception e) {
                    }
                    // System.out.println(this.orcamento);
                }
                // Metodo para pegar as palavras chave
                else if (linha.contains("<section class=\"keywords right_column\">")) {
                    String aux = "";
                    ArrayList<String> al = new ArrayList<String>();
                    linha = arq.readLine();
                    linha = arq.readLine();
                    linha = arq.readLine();
                    // System.out.println(linha);
                    if (linha.contains("<ul>")) {
                        while (!linha.contains("</ul>")) {
                            // System.out.println(linha);

                            linha = arq.readLine();
                            // linha = arq.readLine();
                            if (!linha.isEmpty()) {
                                linha = arq.readLine();
                            }
                            // System.out.println(linha);
                            // if (!linha.isEmpty()) {
                            linha.trim();
                            aux = removeTags(linha).trim();
                            al.add(aux);
                            // aux2= aux + aux2;
                            // System.out.println(aux);
                            // for (int i=0; i< aux.length();i++){
                            // this.palavrasChave[i] += aux;
                            // System.out.println(this.palavrasChave[i]);
                            // }
                            // }

                        }
                        al.remove(al.size() - 1);
                        this.palavrasChave = al.toArray(new String[al.size()]);
                        // for (int i = 0; i < test.length; i++) {
                        // System.out.println(test[i]);
                        // }
                    }
                }
            }
            if (this.tituloOriginal == null) {
                this.tituloOriginal = this.nome;
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
        // this.palavrasChave;
    }

    /**
     * Metodo 2 do tipo void para impressão
     */
    public void imprimir_2() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.printf(
                this.nome + " " + this.tituloOriginal + " " + sdf.format(this.data) + " " + this.duracao + " "
                        + this.genero + " " + this.idiomaOriginal + " " + this.situacao + " " + this.orcamento
                        + " " + "[");
        if (this.palavrasChave != null) {
            for (int i = 0; i < this.palavrasChave.length; i++) {
                System.out.printf(this.palavrasChave[i]);
                if (i != this.palavrasChave.length - 1) {
                    System.out.printf(", ");
                }

            }

        }
        System.out.println("]");
        // + this.palavrasChave);
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

        } while (isFim(entrada[numEntrada++]) == false);
        // // Desconsiderar ultima linha contendo a palavra FIM
        numEntrada--;

        for (int i = 0; i < numEntrada; i++) {
            File arqFilme = new File(entrada[i]);
            Filme atual = new Filme("/tmp/filmes/" + arqFilme.getName());
            atual.imprimir_2();
        }

    }
}
