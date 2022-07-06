
/**
 * Autora: Sarah Magalhaes
 * Matricula: 705113
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
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

    // Metodo para calcular tempo de execucao
    public static long now() {
        return new Date().getTime();
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
    public static int numeroDeComparacoes;

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
    public void inserirFim(Filme filme) {
        ultimo.prox = new Celula(filme);
        ultimo = ultimo.prox;

    }

    /**
     * insere um elemento numa posicao da Lista
     * 
     * @param filme a ser inserido
     * @param pos   a ser inserido
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
     * 
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
     * 
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
     * 
     * @param pos pos a ser removido
     * @return filme removido
     * @throws Exception
     */
    public Filme remover(int pos) throws Exception {
        Filme resp;
        int tam = 0;
        // Calcula tamanho da lista
        for (Celula i = primeiro.prox; i != null; i = i.prox, tam++)
            ;

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
            for (int j = 0; j < pos; i = i.prox, j++)
                ;
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
            System.out.println(i.filme.imprimir2());
            // System.out.println("[" + contador + "] " + i.filme.imprimir2());
            contador++;
            imprimirListaRec(i.prox);

        }

    }

    public boolean pesquisar(String chave) {
        boolean resp = false;
        for (Celula i = primeiro.prox; i != null; i = i.prox) {
            numeroDeComparacoes++;
            if (i.filme.getTituloOriginal().equals(chave)) {
                resp = true;
                break;
            }
        }
        return resp;
    }

}

class Hash {

    private int tamTabela;
    private Lista[] tabela;

    public Hash() {
        this(0);
    }

    public Hash(int tamTabela) {
        this.tamTabela = tamTabela;
        tabela = new Lista[tamTabela];
        for (int i = 0; i < tamTabela; i++) {
            tabela[i] = new Lista();
        }
    }

    private int hash(String chave) {
        int resp = 0;
        for (int i = 0; i < chave.length(); i++) {
            resp += chave.charAt(i);
        }
        return (resp % tamTabela);
    }

    public void inserir(Filme elemento) {
        int indice = hash(elemento.getTituloOriginal());
        tabela[indice].inserirFim(elemento);
    }

    public boolean pesquisar(String chave) {
        boolean resp = false;
        int indice = hash(chave);

        if (tabela[indice] != null) {
            Lista.numeroDeComparacoes++;
            resp = tabela[indice].pesquisar(chave);
            if (resp == true) {
                System.out.println("Posicao: " + indice);
            }
        }

        return resp;
    }

    public void imprimir() {

        for (int i = 0; i < tamTabela; i++) {
            if (tabela[i] != null) {
                System.out.print("[" + i + "]");
                tabela[i].imprimirListaRec();
            }
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
        String entrada = new String();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Hash tabelaHash = new Hash(21);

        // Leitura da entrada padrao
        entrada = in.readLine();
        while (!entrada.equals("FIM")) {
            File arqFilme = new File(entrada);
            Filme atual = new Filme("/tmp/filmes/" + arqFilme.getName());
            tabelaHash.inserir(atual);
            entrada = in.readLine();
        }
        // tabelaHash.imprimir();

        double inicio, fim;

        // Execucao do algoritmo de pesquisa na arvore
        inicio = Filme.now();
        // Leitura da entrada padrao
        entrada = in.readLine();
        while (!entrada.equals("FIM")) {
            Boolean resp = false;
            System.out.println("=> " + entrada);
            resp = tabelaHash.pesquisar(entrada);
            if (resp == false) {
                System.out.println("NAO");
            }
            entrada = in.readLine();
        }

        fim = Filme.now();

        // Criacao e preenchimento do log
        File log = new File("705113_hashIndireta.txt");
        log.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(log));
        writer.append("705113\t");
        writer.write((fim - inicio) / 1000.0 + " s.\t");
        writer.write(Lista.numeroDeComparacoes + "\t");

        writer.close();
    }
}
