
/**
 * Autora: Sarah Magalhaes
 * Matricula: 705113
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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

}

class CelulaDupla {
    public Filme filme;
    public CelulaDupla prox;
    public CelulaDupla ant;

    public CelulaDupla(Filme filme) {
        this.filme = filme;
        this.prox = this.ant = null;
    }

    public CelulaDupla() {
        this(null);
    }

    public CelulaDupla cloneCelula() {
        CelulaDupla clonado = new CelulaDupla();
        try {
            clonado.filme = this.filme.cloneFilme();
        } catch (ParseException e) {
        }
        return clonado;
    }

}

class ListaDupla {
    public CelulaDupla primeiro;
    public CelulaDupla ultimo;
    private int contador = 0;

    public ListaDupla() {
        primeiro = new CelulaDupla();
        ultimo = primeiro;
    }

    /**
     * insere um elemento no final da ListaDupla
     * 
     * @param filme
     * @throws Exception
     */
    public void inserirFim(Filme filme) throws Exception {
        ultimo.prox = new CelulaDupla(filme);
        ultimo.prox.ant = ultimo;
        ultimo = ultimo.prox;

    }

    /**
     * remove um elemento no inicio da ListaDupla sem perder a cabeca kk
     * 
     * @return filme removido
     * @throws Exception
     */
    public Filme removerInicio() throws Exception {
        if (ultimo == primeiro) {
            throw new Exception("Erro");
        }

        Filme resp = primeiro.prox.filme;
        CelulaDupla tmp = primeiro.prox;
        primeiro.prox = primeiro.prox.prox;
        tmp.prox = null;
        tmp.ant = null;
        tmp = null;

        return resp;
    }

    /**
     * Remove um elemento no final da ListaDupla
     * 
     * @return Filme
     * @throws Exception
     */
    public Filme removerFim() throws Exception {
        if (ultimo == primeiro) {
            throw new Exception("Erro");
        }
        CelulaDupla i;
        // Ir ate o penultimo para desconectar o penultimo do ultimo
        for (i = primeiro; i.prox != ultimo; i = i.prox)
            ;

        Filme resp = ultimo.filme;
        ultimo = i;
        i = ultimo.prox = null;

        return resp;

    }

    public void imprimirListaDuplaIterativo() {
        for (CelulaDupla i = primeiro.prox; i != null; i = i.prox) {
            System.out.println(i.filme.imprimir2());
            contador++;
        }
    }

    public void imprimirListaDuplaRec() {
        imprimirListaDuplaRec(this.primeiro.prox);
    }

    private void imprimirListaDuplaRec(CelulaDupla i) {
        if (i != null) {
            System.out.println("[" + contador + "] " + i.filme.imprimir2());
            contador++;
            imprimirListaDuplaRec(i.prox);

        }

    }

    public void quicksort(int n) {
        quicksort(primeiro.prox, ultimo, 0, n - 1);
    }

    public void quicksort(CelulaDupla esq, CelulaDupla dir, int esqIndex, int dirIndex) {
        CelulaDupla i = esq, j = dir;
        int iIndex = esqIndex, jIndex = dirIndex;
        int tamanho = (esqIndex + dirIndex) / 2;

        CelulaDupla pivo = primeiro.prox;
        // Caminha o pivo ate o meio do array
        for (int k = 0; k < tamanho; k++, pivo = pivo.prox);

        while (iIndex <= jIndex) {

            while (i.filme.getNome().compareTo(pivo.filme.getNome()) < 0) {
                Filme.numeroDeComparacoes += 1;
                if (i.filme.getSituacao().compareTo(pivo.filme.getSituacao()) < 0) {
                    i = i.prox;
                    iIndex++;
                } else {
                    i = i.prox;
                    iIndex++;
                }
            }
            while (j.filme.getNome().compareTo(pivo.filme.getNome()) > 0) {

                Filme.numeroDeComparacoes += 1;
                if (j.filme.getSituacao().compareTo(pivo.filme.getSituacao()) > 0) {
                    j = j.ant;
                    jIndex--;
                } else {
                    j = j.ant;
                    jIndex--;
                }
            }
            if (esqIndex <= dirIndex) {
                swap(i, j);
                iIndex++;
                i = i.prox;
                jIndex--;
                j = j.ant;
                Filme.numeroDeMovimentacoes += 3;
            }

        }
        if (esqIndex < jIndex) {
            quicksort(esq, j, esqIndex, jIndex);
        }
        if (iIndex < dirIndex) {
            quicksort(i, dir, iIndex, dirIndex);
        }

    }

    public static void swap(CelulaDupla m, CelulaDupla l) {
        CelulaDupla tmp = m.cloneCelula();
        m.filme = l.filme;
        l.filme = tmp.filme;
    }

    /**
     * Metodo que retorna tamanho da lista
     * 
     * @return tamanho da lista
     */
    public int tamanhoListaDupla() {
        int tamanho = 0;
        for (CelulaDupla i = primeiro.prox; i != null; i = i.prox, tamanho++);

        return tamanho;
    }

    /**
     * Metodo que retorna tamanho da lista
     * 
     * @return tamanho da lista
     */
    public int tamanhoListaDuplaQuick(CelulaDupla esq, CelulaDupla dir) {
        int tamanho = 0;
        if (esq.filme == null) {
            for (CelulaDupla i = esq.prox; i != dir.prox; i = i.prox, tamanho++) ;
        } else {
            for (CelulaDupla i = esq; i != dir.prox; i = i.prox, tamanho++);
        }
        return tamanho;
    }

    // Metodo para calcular tempo de execucao
    public static long now() {
        return new Date().getTime();
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
        ListaDupla listaDupla = new ListaDupla();

        // Leitura da entrada padrao
        do {
            entrada[numEntrada] = MyIO.readLine();
        } while (isFim(entrada[numEntrada++]) == false);
        numEntrada--;
        // Metodo para leitura e criacao de objetos
        for (int i = 0; i < numEntrada; i++) {
            File arqFilme = new File(entrada[i]);
            Filme atual = new Filme("/tmp/filmes/" + arqFilme.getName());
            listaDupla.inserirFim(atual);
        }
        double inicio, fim;
        File log = new File("705113_quicksort2.txt");
        log.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(log));
        writer.append("705113\t");

        // Execucao do algoritmo de ordenacao
        inicio = ListaDupla.now();
        listaDupla.quicksort(numEntrada);
        fim = ListaDupla.now();
        listaDupla.imprimirListaDuplaIterativo();

        writer.write((fim - inicio) / 1000.0 + " s.\t");
        writer.write(Filme.numeroDeComparacoes + "\t");
        writer.write(Filme.numeroDeMovimentacoes + "\t");

        writer.close();
    }
}
