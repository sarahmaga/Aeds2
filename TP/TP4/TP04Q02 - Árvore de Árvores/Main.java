
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
import java.nio.charset.Charset;
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
    public static String ISO88591toUTF8(String strISO) throws Exception
    {
        byte[] isoBytes = strISO.getBytes("ISO-8859-1");
        return new String(isoBytes, "UTF-8");
    }
    /**
     * Metodo para leitura de arquivo
     * 
     * @param s nome do arquivo
     */
    public void lerArq(String s) throws ParseException {
        try {
            try (// Setando o leitor de arquivos
                    BufferedReader arq = new BufferedReader(new FileReader(s)) ) {
                String linha = "";
                while (!linha.contains("<section class=\"content_score\">")) {
                    linha = arq.readLine();
                    // Metodo para pegar o nome
                   // System.out.println(linha);
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
                    else if (linha.contains("<strong>T�tulo original</strong>")) {
                        linha = linha.replace("T�tulo original", "");
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
                    // Metodo para pegar a situa??o
                    else if (linha.contains("Situa??o</bdi></strong> ")) {

                        linha = linha.replace("Situa??o", "").replace(" ", "");
                        this.situacao = removeTags(linha);
                    }
                    // Metodo para pegar o orcamento
                    else if (linha.contains("<p><strong><bdi>Or?amento</bdi></strong>")) {
                        linha = linha.replace("Or?amento", "").replace(" ", "");
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
     * Metodo 2 do tipo void para impress?o
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

    char caracter;
    No esq;
    No dir;
    No2 outro;

    public No() {
        this(' ');
    }

    public No(char caracter) {
        this.caracter = caracter;

    }

    public No(char caracter, No esq, No dir) {
        this.caracter = caracter;
        this.esq = esq;
        this.dir = dir;
    }
}

class No2 {
    No2 esq;
    No2 dir;
    Filme elemento;
    String chave;

    public No2() {
        this(null);
    }

    public No2(Filme elemento) {
        this.elemento = elemento;
        this.chave = elemento.getTituloOriginal();
    }

    public No2(Filme elemento, No2 esq, No2 dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }

}

class ArvoreArvore {

    No raiz;

    public ArvoreArvore() {

        raiz = null;
        inserir('D');
        inserir('R');
        inserir('Z');
        inserir('X');
        inserir('V');
        inserir('B');
        inserir('F');
        inserir('P');
        inserir('U');
        inserir('I');
        inserir('G');
        inserir('E');
        inserir('J');
        inserir('L');
        inserir('H');
        inserir('T');
        inserir('A');
        inserir('W');
        inserir('S');
        inserir('O');
        inserir('M');
        inserir('N');
        inserir('K');
        inserir('C');
        inserir('Y');
        inserir('Q');
    }

    public void inserir(char caracter) {
        raiz = inserir(caracter, raiz);
    }

    /**
     * Inserir um caracter na primeira arvore
     * 
     * @param caracter
     * @param i
     * @return
     */
    private No inserir(char caracter, No i) {
        if (i == null) {
            i = new No(caracter);
        } else if (caracter < i.caracter) {
            i.esq = inserir(caracter, i.esq);
        } else if (caracter > i.caracter) {
            i.dir = inserir(caracter, i.dir);
        } else {
            System.out.println("Erro ao inserir: " + caracter);
        }

        return i;
    }

    public void remover(char caracter) {
        raiz = remover(caracter, raiz);
    }

    /**
     * Remover um caracter na primeira arvore
     * 
     * @param caracter
     * @param i
     * @return
     */
    private No remover(char caracter, No i) {
        if (i == null) {
            System.out.println("Erro ao remover");
        } else if (caracter < i.caracter) {
            i.esq = remover(caracter, i.esq);
        } else if (caracter > i.caracter) {
            i.dir = remover(caracter, i.dir);
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
            i.caracter = j.caracter;
            j = j.esq;
        } else {
            j.dir = maiorEsq(i, j.dir);
        }

        return j;
    }

    // ------------------------------------------------------------
    public void inserir(Filme elemento) {
        inserir(elemento, raiz);
    }

    private void inserir(Filme elemento, No i) {
        if (i == null) {
         System.out.println("Erro ao inserir: Filme invalido! " + elemento.getTituloOriginal());

        } else if (elemento.getTituloOriginal().charAt(0) < i.caracter) {
            inserir(elemento, i.esq);

        } else if (elemento.getTituloOriginal().charAt(0) > i.caracter) {
            inserir(elemento, i.dir);

        } else {
            i.outro = inserir(elemento, i.outro);
        }
    }

    private No2 inserir(Filme elemento, No2 i) {
        if (i == null) {
            i = new No2(elemento);

        } else if (elemento.getTituloOriginal().compareTo(i.chave) < 0) {
            i.esq = inserir(elemento, i.esq);

        } else if (elemento.getTituloOriginal().compareTo(i.chave) > 0) {
            i.dir = inserir(elemento, i.dir);

        } else {
            System.out.println("Erro ao inserir: elemento existente!");
        }

        return i;
    }

    // public boolean pesquisar(String chave) {
    // return pesquisar(chave, raiz);
    // }

    // private boolean pesquisar(String chave, No i) {
    // boolean resp;
    // if (i == null) {
    // resp = false;
    // } else if (chave.charAt(0) == i.caracter) {
    // resp = true;

    // } else if (chave.charAt(0) < i.caracter) {
    // resp = pesquisar(chave, i.esq);

    // } else {
    // resp = pesquisar(chave, i.dir);
    // }

    // return resp;
    // }

    public void imprimirCaracteres() {
        imprimirCaracteres(raiz);

    }

    public void imprimirCaracteres(No i) {
        if (i != null) {
            imprimirCaracteres(i.esq);
            System.out.println(i.caracter);
            imprimirCaracteres(i.dir);
        }
    }

    public void imprimir() {
        imprimir(raiz);
    }

    public void imprimir(No i) {
        if (i != null) {
            imprimir(i.esq);
            imprimir(i.outro);
            imprimir(i.dir);
        }
    }

    public void imprimir(No2 i) {
        if (i != null) {
            imprimir(i.esq);
            System.out.println(i.elemento.getTituloOriginal());
            imprimir(i.dir);
        }
    }

    public boolean pesquisar(String elemento) {
        return pesquisar(raiz, elemento);
    }

    private boolean pesquisar(No no, String x) {
        boolean resp;
        if (no == null) {
            resp = false;

        } else if (x.charAt(0) < no.caracter) {
            System.out.print("esq ");
            resp = pesquisar(no.esq, x);

        } else if (x.charAt(0) > no.caracter) {
            System.out.print("dir ");
            resp = pesquisar(no.dir, x);

        } else {
            resp = pesquisarSegundaArvore(no.outro, x);
        }
        return resp;
    }

    private boolean pesquisarSegundaArvore(No2 no, String x) {
        boolean resp;
        if (no == null) {
            resp = false;

        } else if (x.compareTo(no.chave) < 0) {
            System.out.print("ESQ ");
            resp = pesquisarSegundaArvore(no.esq, x);

        } else if (x.compareTo(no.chave) > 0) {
            System.out.print("DIR ");
            resp = pesquisarSegundaArvore(no.dir, x);

        } else {
            resp = true;

        }
        return resp;
    }

    public boolean mostrarPre(String x) {
        return mostrarPre(raiz, x);
    }

    private boolean mostrarPre(No i, String x) {
        boolean resp = false;
        if (i != null) {
            resp = mostrarPreOutro(i.outro, x);
            if (resp == false) {
                System.out.print(" ESQ ");
                Filme.numeroDeComparacoes++;
                resp = mostrarPre(i.esq, x); // Elementos da esquerda.
            }
            if (resp == false) {
                System.out.print(" DIR ");
                Filme.numeroDeComparacoes++;
                resp = mostrarPre(i.dir, x); // Elementos da direita.
            }
        }
        return resp;
    }

    private boolean mostrarPreOutro(No2 i, String x) {
        boolean resp = false;
        if (i == null) {
            resp = false;

        } else {
            if (x.compareTo(i.chave) < 0) {
                System.out.print("esq ");
                Filme.numeroDeComparacoes++;
                resp = mostrarPreOutro(i.esq, x);

            } else if (x.compareTo(i.chave) > 0) {
                System.out.print("dir ");
                Filme.numeroDeComparacoes++;
                resp = mostrarPreOutro(i.dir, x);

            } else {
                resp = true;

            }
        }
        return resp;
    }
}

class Main {

    public static boolean isFim(String s) {
        return (s.length() >= 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static void main(String[] args) throws Exception {
        //  MyIO.setCharset("UTF-8");
        // Declaraco de variaveis
        String entrada = new String();
        int qtdeMetodos = 0;
        String metodo = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        ArvoreArvore arvoreArvore = new ArvoreArvore();

        // Leitura da entrada padrao
        entrada = in.readLine();
        while (!entrada.equals("FIM")) {
            File arqFilme = new File(entrada);
            Filme atual = new Filme("/tmp/filmes/" + arqFilme.getName());
            arvoreArvore.inserir(atual);
            //System.out.println(entrada);
            entrada = in.readLine();
        }
        
        // Leitura da segunda parte da entrada
        qtdeMetodos = Integer.parseInt(in.readLine()) ;
        String[] split;
        // Metodos chamamento das funcoes de insercao e remocao
        for (int i = 0; i < qtdeMetodos; i++) {
            metodo = in.readLine();
            split = metodo.split(" ");
           
            if (split[0].equals("I") == true) {
                metodo = metodo.replace("I ", "");
                File arqFilme = new File(metodo);
                Filme novo = new Filme("/tmp/filmes/" + arqFilme.getName());
                arvoreArvore.inserir(novo);

            }
        }
        // // arvoreArvore.imprimir();

         double inicio, fim;

        // // Execucao do algoritmo de pesquisa na arvore
         inicio = Filme.now();
        // Leitura da entrada padrao
        entrada = in.readLine();
        while (!entrada.equals("FIM")) {
            Boolean resp = false;
            System.out.println("=> " + entrada);
            System.out.print("raiz ");
            resp = arvoreArvore.mostrarPre(entrada);
            if (resp == true) {
                System.out.println(" SIM");
            } else {
                System.out.println(" NAO");
            }
            entrada = in.readLine();
        }

        fim = Filme.now();

        // Criacao e preenchimento do log
        File log = new File("705113_arvoreArvore.txt");
        log.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(log));
        writer.append("705113\t");
        writer.write((fim - inicio) / 1000.0 + " s.\t");
        writer.write(Filme.numeroDeComparacoes + "\t");

        writer.close();
    }
}

/**
 * entrada = MyIO.readLine();
        while (!entrada.equals("FIM")) {
            File arqFilme = new File(entrada);
            Filme atual = new Filme("/tmp/filmes/" + arqFilme.getName());
            arvoreArvore.inserir(atual);
            entrada = MyIO.readLine();
        }
        // Leitura da segunda parte da entrada
        qtdeMetodos = MyIO.readInt();
        String[] split;
        // Metodos chamamento das funcoes de insercao e remocao
        for (int i = 0; i < qtdeMetodos; i++) {
            metodo = MyIO.readLine();
            split = metodo.split(" ");

            if (split[0].equals("I") == true) {
                metodo = metodo.replace("I ", "");
                File arqFilme = new File(metodo);
                Filme novo = new Filme("/tmp/filmes/" + arqFilme.getName());
                arvoreArvore.inserir(novo);

            }
        }
        // arvoreArvore.imprimir();

        double inicio, fim;

        // Execucao do algoritmo de pesquisa na arvore
        inicio = Filme.now();
        // Leitura da entrada padrao
        entrada = MyIO.readLine();
        while (!entrada.equals("FIM")) {
            Boolean resp = false;
            System.out.println("=> " + entrada);
            System.out.print("raiz ");
            resp = arvoreArvore.mostrarPre(entrada);
            if (resp == true) {
                System.out.println(" SIM");
            } else {
                System.out.println(" NAO");
            }
            entrada = MyIO.readLine();
        }
 */