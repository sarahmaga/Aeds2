
/**
 * Autora: Sarah Magalhaes
 * Matricula: 705113
 */

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
    public static int numeroDeComparacoes;

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

    // Metodo de clonagem
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
        for (int i = 0; i < original.length(); i++) { // tags de abertura e fechamento <>

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
                    linha = arq.readLine().trim();
                    this.nome = removeTags(linha);

                    this.nome = this.nome.replace(
                            "Inclui filmes como Homem-Aranha: De Volta ao Lar, Homem-Aranha: Longe de Casa, ", "");

                }
                // Metodo para pegar a duracao
                else if (linha.contains("<span class=\"runtime\">")) {
                    // Declaraco de variaveis, aux sao Strings auxiliares
                    arq.readLine();
                    int h = 0, m = 0;
                    String aux = arq.readLine();
                    String aux2 = aux;
                    String a = "";
                    String b = "";
                    aux = aux.replace("h", "").replace("m", "").replace(" ", "").trim();
                    aux2 = aux2.replace(" ", "").trim();

                    // Caso o filme tenha xh 10 minutos pra cima
                    if (aux2.contains("h")) {
                        if (aux.length() == 3) {
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
                        // Caso o filme tenha 10m ate 59m
                        if (aux.length() == 2) {
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
                    linha = linha.replace("Título original", "");
                    this.tituloOriginal = removeTags(linha).trim();
                }

                // Metodo para pegar a data
                else if (linha.contains("<span class=\"release\">")) {
                    // Declaraco de variaveis, aux sao Strings auxiliares
                    String aux = "";

                    aux = arq.readLine();
                    aux = aux.trim();

                    aux = aux.replace("(BR)", "");

                    aux = aux.replace("(AU)", "");

                    aux = aux.replace("(US)", "");

                    aux = aux.replace("(RU)", "");

                    // Formatacao da data
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    this.data = sdf.parse(aux);
                }
                // Metodo para pegar os generos
                else if (linha.contains("<span class=\"genres\">")) {
                    arq.readLine();
                    linha = arq.readLine();

                    this.genero = removeTags(linha).trim();

                    this.genero = this.genero.replace("&nbsp;", "");

                }
                // Metodo para pegar o idioma original
                else if (linha.contains("<p><strong><bdi>Idioma original</bdi></strong>")) {
                    linha = linha.replace("Idioma original", "");
                    this.idiomaOriginal = removeTags(linha).trim();
                }
                // Metodo para pegar a situação
                else if (linha.contains("Situação</bdi></strong> ")) {

                    linha = linha.replace("Situação", "").replace(" ", "");
                    this.situacao = removeTags(linha);
                }
                // Metodo para pegar o orcamento
                else if (linha.contains("<p><strong><bdi>Orçamento</bdi></strong>")) {
                    linha = linha.replace("Orçamento", "").replace(" ", "");
                    linha = removeTags(linha);

                    linha = linha.replace(",", "");

                    linha = linha.replace("$", "");
                    // Parsing string to float
                    try {
                        this.orcamento = Float.parseFloat(linha);
                    } catch (Exception e) {
                    }
                }
                // Metodo para pegar as palavras chave
                else if (linha.contains("<section class=\"keywords right_column\">")) {
                    // Declaraco de variaveis, aux sao Strings auxiliares
                    String aux = "";
                    // As palavras chave serao primeiramente armazenadas num ArrayList
                    ArrayList<String> al = new ArrayList<String>();

                    linha = arq.readLine();
                    linha = arq.readLine();
                    linha = arq.readLine();
                    if (linha.contains("<ul>")) {
                        while (!linha.contains("</ul>")) {

                            linha = arq.readLine();
                            if (!linha.isEmpty()) {
                                linha = arq.readLine();
                            }
                            linha.trim();
                            aux = removeTags(linha).trim();
                            al.add(aux);

                        }
                        al.remove(al.size() - 1);
                        // Parse de ArrayList to String[]
                        this.palavrasChave = al.toArray(new String[al.size()]);
                    }
                }
            }
            // Caso nao tenha titulo original, ele sera o nome do filme
            if (this.tituloOriginal == null) {
                this.tituloOriginal = this.nome;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo 2 do tipo void para impressão
     */
    public void imprimir() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.printf(
                this.nome + " " + this.tituloOriginal + " " + sdf.format(this.data) + " " + this.duracao + " "
                        + this.genero + " " + this.idiomaOriginal + " " + this.situacao + " " + this.orcamento
                        + " " + "[");
        // Caso existam palavras chave o for e executado
        if (this.palavrasChave != null) {
            for (int i = 0; i < this.palavrasChave.length; i++) {
                System.out.printf(this.palavrasChave[i]);
                if (i != this.palavrasChave.length - 1) {
                    System.out.printf(", ");
                }

            }

        }
        System.out.println("]");
    }

    public String imprimir2() {

        String resp = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        resp = this.nome + " " + this.tituloOriginal + " " + sdf.format(this.data) + " " + this.duracao + " "
                + this.genero + " " + this.idiomaOriginal + " " + this.situacao + " " + this.orcamento
                + " " + "[";
        // Caso existam palavras chave o for e executado
        if (this.palavrasChave != null) {
            for (int i = 0; i < this.palavrasChave.length; i++) {
                resp += this.palavrasChave[i];
                if (i != this.palavrasChave.length - 1) {
                    resp += ", ";
                }

            }

        }
        resp += "]";

        return resp;
    }

    public static boolean isFim(String s) {
        return (s.length() >= 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

}

class Lista {

    public int tamanhoLista;
    public int n;
    public Filme[] listaDeFilmes;

    public Lista() {
        this.tamanhoLista = 30;
    }

    public Lista(int tamanhoLista) throws ParseException {
        this.listaDeFilmes = new Filme[tamanhoLista];
        this.n = 0;
        this.tamanhoLista = tamanhoLista;

    }

    public int getTamanhoLista() {
        return tamanhoLista;
    }

    /**
     * Insere um elemento no final da lista
     * 
     * @param filme
     * @throws Exception
     */
    public void inserirInicio(Filme filme) throws Exception {
        if (this.n >= this.tamanhoLista) {
            throw new Exception("Erro!");
        }
        // levar elementos para o fim do array
        for (int i = this.n; i > 0; i--) {
            this.listaDeFilmes[i] = this.listaDeFilmes[i - 1].cloneFilme();

        }

        this.listaDeFilmes[0] = filme.cloneFilme();
        n++;

    }

    /**
     * insere um elemento numa posicao especifica
     * 
     * @param filme
     * @param pos
     * @throws Exception
     */
    public void inserir(Filme filme, int pos) throws Exception {
        if (this.n >= this.tamanhoLista) {
            throw new Exception("Erro!");
        }

        // levar elementos para o fim do array
        for (int i = this.n; i > pos; i--) {
            this.listaDeFilmes[i] = this.listaDeFilmes[i - 1].cloneFilme();

        }

        this.listaDeFilmes[pos] = filme.cloneFilme();
        n++;

    }

    /**
     * Insere um elemento no final da lista
     * 
     * @param filme
     * @throws Exception
     */
    public void inserirFim(Filme filme) throws Exception {
        if (this.n >= this.tamanhoLista) {
            throw new Exception("Erro!");
        }

        this.listaDeFilmes[n] = filme.cloneFilme();
        n++;
    }

    /**
     * Remove um elemento no inicio da lista
     * 
     * @param filme
     * @return
     * @throws Exception
     */
    public Filme removerInicio() throws Exception {
        if (this.n == 0) {
            throw new Exception("Erro!");
        }
        Filme resp = listaDeFilmes[0].cloneFilme();
        n--;

        for (int i = 0; i < n; i++) {
            listaDeFilmes[i] = listaDeFilmes[i + 1].cloneFilme();
        }
        return resp;

    }

    /**
     * Remove um elemento na posicao escolhida
     * 
     * @param pos
     * @return
     * @throws Exception
     */
    public Filme remover(int pos) throws Exception {
        if (this.n == 0 || pos < 0 || pos >= n) {
            throw new Exception("Erro!");
        }
        Filme resp = listaDeFilmes[pos].cloneFilme();
        n--;

        for (int i = pos; i < n; i++) {
            listaDeFilmes[i] = listaDeFilmes[i + 1].cloneFilme();
        }
        return resp;

    }

    /**
     * Remove um elemento na ultima posicao
     * 
     * @param filme
     * @return
     * @throws Exception
     */
    public Filme removerFim() throws Exception {
        if (this.n == 0) {
            throw new Exception("Erro!");
        }

        return listaDeFilmes[--n];

    }

    public void imprimirLista() {
        for (int i = 0; i < n; i++) {
            System.out.println("[" + i + "] " + listaDeFilmes[i].imprimir2());
        }
    }

}

class Main {

    public static boolean isFim(String s) {
        return (s.length() >= 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static void main(String[] args) throws Exception {
        MyIO.setCharset("UTF-8");
        // Declaraco de variaveis
        String entrada[] = new String[1000];
        Filme[] filmes = new Filme[30];
        int numEntrada = 0;
        String metodos[] = new String[1000];
        int numMetodos = 0;
        int qtdeMetodos = 0;
        Lista lista = new Lista(50);
        String[] Split;

        // Leitura da entrada padrao
        do {
            entrada[numEntrada] = MyIO.readLine();
        } while (isFim(entrada[numEntrada++]) == false);
        numEntrada--;
        // Metodo para leitura e criacao de objetos
        for (int i = 0; i < numEntrada; i++) {
            File arqFilme = new File(entrada[i]);
            Filme atual = new Filme("/tmp/filmes/" + arqFilme.getName());
            filmes[i] = atual;
            lista.inserirFim(atual);
        }
        // Leitura da segunda parte da entrada
        qtdeMetodos = MyIO.readInt();
        do {
            metodos[numMetodos] = MyIO.readLine();
            numMetodos++;
        } while (numMetodos < qtdeMetodos);
        // Metodos chamamento das funcoes de insercao e remocao
        for (int i = 0; i < qtdeMetodos; i++) {
            Split = metodos[i].split(" ");

            if (Split[0].equals("II") == true) {
                metodos[i] = metodos[i].replace("II ", "");
                File arqFilme = new File(metodos[i]);
                Filme novo = new Filme("/tmp/filmes/" + arqFilme.getName());
                lista.inserirInicio(novo);

            } else if (Split[0].equals("IF")) {
                metodos[i] = metodos[i].replace("IF ", "");
                File arqFilme = new File(metodos[i]);
                Filme novo = new Filme("/tmp/filmes/" + arqFilme.getName());
                lista.inserirFim(novo);
            } else if (Split[0].equals("I*")) {
                int pos = Integer.parseInt(Split[1]);
                metodos[i] = metodos[i].replace("I* ", "");
                metodos[i] = metodos[i].replace(Split[1], "");
                File arqFilme = new File(metodos[i].replaceFirst(" ", ""));
                Filme novo = new Filme("/tmp/filmes/" + arqFilme.getName());
                lista.inserir(novo, pos);
            } else if (Split[0].equals("RI")) {
                Filme remover = new Filme();
                remover = lista.removerInicio();
                System.out.println("(R) " + remover.getNome());
            } else if (Split[0].equals("RF")) {
                Filme remover = new Filme();
                remover = lista.removerFim();
                System.out.println("(R) " + remover.getNome());
            } else if (Split[0].equals("R*")) {

                int pos = Integer.parseInt(Split[1]);
                metodos[i] = metodos[i].replace("R* ", "");
                metodos[i] = metodos[i].replace(Split[1], "");
                Filme remover = new Filme();
                remover = lista.remover(pos);
                System.out.println("(R) " + remover.getNome());
            }

        }
        lista.imprimirLista();

    }
}