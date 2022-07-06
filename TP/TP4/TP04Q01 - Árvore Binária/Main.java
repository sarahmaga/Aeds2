
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
    public static int numeroDeComparacoes;
    public static int numeroDeMovimentacoes;

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

class No {

    Filme elemento;
    No esq;
    No dir;
    String chave;

    public No() {
        this(null);
    }

    public No(Filme elemento) {
        this.elemento = elemento;
        this.chave = elemento.getTituloOriginal();

    }

    public No(Filme elemento, No esq, No dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
        this.chave = elemento.getTituloOriginal();
    }
}

class ArvoreBinaria {

    No raiz;

    public ArvoreBinaria() {

        raiz = null;
    }

    public void inserir(Filme elemento) {
        raiz = inserir(elemento, raiz);
    }

    private No inserir(Filme elemento, No i) {

        if (i == null) {
            i = new No(elemento);
            Filme.numeroDeComparacoes++;
        } else if (elemento.getTituloOriginal().compareTo(i.chave) < 0) {
            i.esq = inserir(elemento, i.esq);
            Filme.numeroDeComparacoes++;
        } else if (elemento.getTituloOriginal().compareTo(i.chave) > 0) {
            i.dir = inserir(elemento, i.dir);
            Filme.numeroDeComparacoes++;
        } else {
            System.out.println("Erro ao inserir");
        }

        return i;
    }

    // Filme removido = new Filme();
    // removido.setTituloOriginal(chaveFilme);
    // System.out.println("removido " + removido.getTituloOriginal());
    public void remover(Filme elemento) {
        raiz = remover(elemento, raiz);
    }

    private No remover(Filme elemento, No i) {
        if (i == null) {
            System.out.println("Erro ao remover");
        } else if (elemento.getTituloOriginal().compareTo(i.chave) < 0) {
            i.dir = remover(elemento, i.esq);
        } else if (elemento.getTituloOriginal().compareTo(i.chave) > 0) {
            i.dir = remover(elemento, i.dir);
        } else if (i.dir == null) {
            i = i.esq;
        } else if (i.esq == null) {
            i = i.dir;
        } else {
            i.esq = maiorEsq(i, i.esq);
        }

        return i;
    }

    public void remover2(String elemento) {
        raiz = remover2(elemento, raiz);
    }

    private No remover2(String chave, No i) {
        if (i == null) {
       //     System.out.println("Erro ao remover");
        } else if (chave.compareTo(i.chave) < 0) {
            i.esq = remover2(chave, i.esq);
        } else if (chave.compareTo(i.chave) > 0) {
            i.dir = remover2(chave, i.dir);
        } else if (i.dir == null) {
            i = i.esq;
        } else if (i.esq == null) {
            i = i.dir;
        } else {
            i.esq = maiorEsq(i, i.esq);
        }

        return i;
    }

    private No maiorEsq(No i, No j) {

        if (j.dir == null) {
            i.chave = j.chave;
            j = j.esq;
        } else {
            j.dir = maiorEsq(i, j.dir);
        }

        return j;
    }

    public boolean pesquisar(String chave) {
        return pesquisar(chave, raiz);
    }

    private boolean pesquisar(String chave, No i) {
        boolean resp;

        if (i == null) {
            resp = false;
            Filme.numeroDeComparacoes++;
        } else if (chave.compareTo(i.chave) == 0) {
            resp = true;

        } else if (chave.compareTo(i.chave) < 0) {
            System.out.print("esq ");
            Filme.numeroDeComparacoes++;
            resp = pesquisar(chave, i.esq);
        } else {
            System.out.print("dir ");
            Filme.numeroDeComparacoes++;
            resp = pesquisar(chave, i.dir);
        }

        return resp;
    }

    public void caminharCentral() {
        caminharCentral(raiz);

    }

    public void caminharCentral(No i) {
        if (i != null) {
            caminharCentral(i.esq);
            System.out.println(i.chave);
            caminharCentral(i.dir);
        }
    }

    public void caminharPre() {
        caminharPre(raiz);
    }

    private void caminharPre(No i) {
        if (i != null) {
            System.out.println(i.chave);
            caminharPre(i.esq); // Elementos da esquerda.
            caminharPre(i.dir); // Elementos da direita.
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
        int qtdeMetodos = 0;
        String metodo = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        ArvoreBinaria arvoreBinaria = new ArvoreBinaria();

        // Leitura da entrada padrao
        entrada = in.readLine();
        while (!entrada.equals("FIM")) {
            File arqFilme = new File(entrada);
            Filme atual = new Filme("/tmp/filmes/" + arqFilme.getName());
            arvoreBinaria.inserir(atual);
            entrada = in.readLine();
        }
        // Leitura da segunda parte da entrada
        qtdeMetodos = Integer.parseInt(in.readLine()) ;
        String[] split;
        // Metodos chamamento das funcoes de insercao e remocao
        for (int i = 0; i < qtdeMetodos; i++) {
            metodo = in.readLine();
            split = metodo.split(" ");
            split = metodo.split(" ");
           
            if (split[0].equals("I") == true) {
                metodo = metodo.replace("I ", "");
                File arqFilme = new File(metodo);
                Filme novo = new Filme("/tmp/filmes/" + arqFilme.getName());
                arvoreBinaria.inserir(novo);

            } else if (split[0].equals("R")) {
                String chaveFilme = metodo.replace("R ", "");
                arvoreBinaria.remover2(chaveFilme);

            }

        }

        // arvoreBinaria.caminharCentral();
        double inicio, fim;

        // Execucao do algoritmo de pesquisa na arvore
        inicio = Filme.now();
        // Leitura da entrada padrao
        entrada = in.readLine();
        while (!entrada.equals("FIM")) {
            Boolean resp = false;
            System.out.println(entrada);
            System.out.print("=>raiz ");
            resp = arvoreBinaria.pesquisar(entrada);
            if (resp == true) {
                System.out.println("SIM");
            } else {
                System.out.println("NAO");
            }
            entrada = in.readLine();
        }

        fim = Filme.now();

        // Criacao e preenchimento do log
        File log = new File("705113_arvoreBinaria.txt");
        log.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(log));
        writer.append("705113\t");
        writer.write((fim - inicio) / 1000.0 + " s.\t");
        writer.write(Filme.numeroDeComparacoes + "\t");

        writer.close();
    }
}
