
/**
 * Autora: Sarah Magalhaes
 * Matricula: 705113
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
    public Filme prox;

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
        this.prox = null;
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
            try (// Setando o leitor de arquivos
                    BufferedReader arq = new BufferedReader(new FileReader(s))) {
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
                            // Caso o filme tenha 1h, 2h etc
                            else if (aux.length() == 1) {
                                a += aux.charAt(0);

                                try {
                                    h = Integer.parseInt(a);
                                } catch (Exception e) {
                                }
                                this.duracao = h;
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

class Celula {
    public Filme filme;
    public Celula prox;

    public Celula(Filme filme) {
        this.filme = filme;
        this.prox = null;
    }

    public Celula() {
        this(null);
    }

}

class Lista {
    private Celula primeiro;
    private Celula ultimo;
    private int contador = 0;

    public Lista() {
        primeiro = new Celula();
        ultimo = primeiro;
    }

    /**
     * insere um elemento no comeco da Lista
     * 
     * @param filme
     */
    public void inserirInicio(Filme filme) {
        Celula tmp = new Celula(filme);
        tmp.prox = primeiro.prox;
        primeiro.prox = tmp;
        if (primeiro == ultimo) {
            ultimo = tmp;
        }
        tmp = null;

    }

    /**
     * insere um elemento no final da Lista
     * 
     * @param filme
     * @throws Exception
     */
    public void inserirFim(Filme filme) throws Exception {
        ultimo.prox = new Celula(filme);
        ultimo = ultimo.prox;

    }
    /**
     * insere um elemento numa posicao da Lista
     * @param filme a ser inserido
     * @param pos a ser inserido
     * @throws Exception
     */
    public void inserir(Filme filme, int pos) throws Exception {
        int tam = 0;
        // Calcula tamanho da lista
        for (Celula i = primeiro.prox; i != null; i = i.prox, tam++)
            ;
        if (pos < 0 || pos > tam) {
            throw new Exception("Erro ao inserir posicao (" + pos + " / tamanho = " + tam + ") invalida!");
        } else if (pos == 0) {
            inserirInicio(filme);
        } else if (pos == tam) {
            inserirFim(filme);
        } else {
            // Caminhar ate a posicao anterior a insercao
            Celula i = primeiro;
            for (int j = 0; j < pos; j++, i = i.prox)
                ;

            Celula tmp = new Celula(filme);
            tmp.prox = i.prox;
            i.prox = tmp;
            tmp = i = null;
        }
    }

    /**
     * remove um elemento no inicio da Lista
     * @return filme removido
     * @throws Exception
     */
    public Filme removerInicio() throws Exception {
        if (ultimo == primeiro) {
            throw new Exception("Erro");
        }

        Filme resp = primeiro.prox.filme;
        Celula tmp = primeiro;
        primeiro = primeiro.prox;
        tmp.prox = null;
        tmp = null;

        return resp;
    }

    /**
     * Remove um elemento no final da Lista
     * @return Filme
     * @throws Exception
     */
    public Filme removerFim() throws Exception {
        if (ultimo == primeiro) {
            throw new Exception("Erro");
        }
        Celula i;
        // Ir ate o penultimo para desconectar o penultimo do ultimo
        for (i = primeiro; i.prox != ultimo; i = i.prox)
            ;

        Filme resp = ultimo.filme;
        ultimo = i;
        i = ultimo.prox = null;

        return resp;

    }
    /**
     * remove um elemento numa posicao da Lista
     * @param pos pos a ser removido
     * @return filme removido
     * @throws Exception
     */
    public Filme remover(int pos) throws Exception {
        Filme resp;
        int tam = 0;
        // Calcula tamanho da lista
        for (Celula i = primeiro.prox; i != null; i = i.prox, tam++) ;

        if (primeiro == ultimo) {
            throw new Exception("Erro ao remover (vazia)!");

        } else if (pos < 0 || pos >= tam) {
            throw new Exception("Erro ao remover (posicao " + pos + " / " + tam + " invalida!");
        } else if (pos == 0) {
            resp = removerInicio();
        } else if (pos == tam - 1) {
            resp = removerFim();
        } else {
            // Caminha ate a celula anterior a posicao desejada
            Celula i = primeiro;
            for (int j = 0; j < pos; i = i.prox, j++);
            Celula tmp = i.prox;
            resp = tmp.filme;
            i.prox = tmp.prox;
            tmp.prox = null;
            i = tmp = null;

        }
        return resp;
    }

    public void imprimirListaIterativo() {
        for (Celula i = primeiro.prox; i != null; i = i.prox) {
            System.out.println("[" + contador + "] " + i.filme.imprimir2());
            contador++;
        }
    }

    public void imprimirListaRec() {
        imprimirListaRec(this.primeiro.prox);
    }

    private void imprimirListaRec(Celula i) {
        if (i != null) {
            System.out.println("[" + contador + "] " + i.filme.imprimir2());
            contador++;
            imprimirListaRec(i.prox);

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
        int numEntrada = 0;
        String metodos[] = new String[1000];
        int numMetodos = 0;
        int qtdeMetodos = 0;
        Lista lista = new Lista();
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

            if (Split[0].equals("IF") == true) {
                metodos[i] = metodos[i].replace("IF ", "");
                Filme atual = new Filme("/tmp/filmes/" + metodos[i]);
                lista.inserirFim(atual);

            } else if (Split[0].equals("II") == true) {
                metodos[i] = metodos[i].replace("II ", "");
                Filme atual = new Filme("/tmp/filmes/" + metodos[i]);
                lista.inserirInicio(atual);
            } else if (Split[0].equals("I*") == true) {
                int pos = Integer.parseInt(Split[1]);
                metodos[i] = metodos[i].replace("I* ", "");
                metodos[i] = metodos[i].replace(Split[1], "");
                metodos[i] = metodos[i].replaceFirst(" ", "");
                Filme atual = new Filme("/tmp/filmes/" + metodos[i]);
                lista.inserir(atual, pos);
            } else if (Split[0].equals("RI")) {
                Filme remover = new Filme();
                remover = lista.removerInicio();
                System.out.println("(R) " + remover.getNome());
            } else if (Split[0].equals("RF")) {
                Filme remover = new Filme();
                remover = lista.removerFim();
                System.out.println("(R) " + remover.getNome());
            } else if (Split[0].equals("R*") == true) {
                int pos = Integer.parseInt(Split[1]);
                Filme remover = new Filme();
                remover = lista.remover(pos);
                System.out.println("(R) " + remover.getNome());
            }

        }
        lista.imprimirListaRec();

    }
}
