/**
 * Autora: Sarah Magalhaes
 * Matricula: 705113
 */

class Celula {
    public int elemento;
    public Celula inf, sup, esq, dir;

    public Celula() {
        this(0);
    }

    public Celula(int elemento) {
        this(elemento, null, null, null, null);
    }

    public Celula(int elemento, Celula inf, Celula sup, Celula esq, Celula dir) {
        this.elemento = elemento;
        this.inf = inf;
        this.sup = sup;
        this.esq = esq;
        this.dir = dir;
    }
}

class Matriz {
    int linha;
    int coluna;
    Celula inicio;
    Celula inserir;

    public Matriz() {

        this(3);
    }

    // Para criacao de matrizes quadraticas
    public Matriz(int tamanho) {
        this.linha = this.coluna = tamanho;
        this.inicio = new Celula();
        inserir = inicio;
        contruirMatriz();
    }

    public Matriz(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
        this.inicio = new Celula();
        inserir = inicio;
        contruirMatriz();
    }

    private void contruirMatriz() {

        Celula tmp = inicio;
        Celula tmp2 = new Celula();
        // Construindo a primeira linha
        for (int i = 0; i < linha - 1; i++) {
            tmp.dir = tmp2;
            tmp2.esq = tmp;
            tmp = tmp2;
            tmp2 = null;
            tmp2 = new Celula();
        }
        tmp2 = null;
        tmp = inicio;
        Celula tmp3 = null;
        // Construindo as seguintes linhas
        for (int i = 0; i < coluna - 1; i++) {
            tmp2 = new Celula();
            tmp.inf = tmp2;
            tmp2.sup = tmp;
            tmp = tmp2;
            tmp3 = tmp;
            tmp2 = null;
            for (int j = 0; j < linha - 1; j++) {
                tmp2 = new Celula();
                tmp3.dir = tmp2;
                tmp2.esq = tmp3;
                tmp3.sup.dir.inf = tmp2;
                tmp2.sup = tmp3.sup.dir;
                tmp3 = tmp2;
                tmp2 = null;
            }
            tmp3 = null;
        }

        tmp2 = null;

    }

    public Celula inserirPirata(int x) {
        if (inserir == null) {
            System.err.println("Erro");
            return inserir;
        }

        inserir.elemento = x;
        if (inserir.dir != null) {
            inserir = inserir.dir;
        } else {
            if (inserir.inf != null) {
                inserir = inserir.inf;
                while (inserir.esq != null) {
                    inserir = inserir.esq;
                }
            } else {
                inserir = null;
            }
        }

        return inserir;
    }

    public void inserir(int x) {
        inicio = inserir(inicio, x);

    }

    private Celula inserir(Celula i, int x) {
        if (i == null) {
            System.out.println("Matriz vazia");
        } else if (i.elemento == 0) { // Preencher celula inicio
            i.elemento = x;
        } else if (i.dir != null) { // Preencher primeira linha
            i.dir = inserir(i.dir, x);
        } else if (i.esq != null) {
            i.esq = inserir(i.esq, x);
        } else if (i.inf != null) {
            i.inf = inserir(i.inf, x);
        }
        return i;
    }

    public void imprimir() {

        for (Celula i = inicio; i != null; i = i.inf) {
            for (Celula j = i; j != null; j = j.dir) {
                System.out.print(j.elemento + " ");
            }
            System.out.println();
        }
    }

    public Matriz soma(Matriz i, Matriz j) {
        Matriz soma = new Matriz(i.linha, i.coluna);
        for (Celula k = i.inicio, m = j.inicio, s = soma.inicio; k != null; k = k.inf, m = m.inf, s = s.inf) {
            for (Celula l = k, p = m, q = s; l != null; l = l.dir, p = p.dir, q = q.dir) {
                q.elemento = l.elemento + p.elemento;
            }
        }
        return soma;
    }

    public Matriz multiplicacao(Matriz i, Matriz j) {
        Matriz mult = new Matriz(i.linha, i.coluna);
        for (Celula k = i.inicio, s = mult.inicio; k != null; k = k.inf, s = s.inf) {
            for (Celula p = j.inicio, q = s; q != null; p = p.dir, q = q.dir) {
                Celula aux = p;
                Celula aux2 = k;

                while (aux != null) {
                    q.elemento += aux.elemento * aux2.elemento;
                    aux = aux.inf;
                    aux2 = aux2.dir;
                }

            }

        }
        return mult;
    }

    public void imprimirDiagonalPrincipal() {
        Celula j = inicio;

        for (; j.dir != null; j = j.inf.dir) {
            System.out.print(j.elemento + " ");
        }
        System.out.print(j.elemento + " ");
        System.out.println();
    }

    public void imprimirDiagonalSecundaria() {
        Celula i = inicio;

        for (; i.dir != null; i = i.dir)
            ;

        for (; i.esq != null; i = i.inf.esq) {
            System.out.print(i.elemento + " ");
        }
        System.out.print(i.elemento + " ");
        System.out.println();
    }

}

class Main {

    public static boolean isFim(String s) {
        return (s.length() >= 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static void main(String[] args) throws Exception {
        MyIO.setCharset("UTF-8");
        // Declaraco de variaveis
        int numEntrada = 0;
        int numCasos = 0;
        int linha = 0, coluna = 0;

        // Leitura da entrada
        numCasos = MyIO.readInt();
        while (numEntrada < numCasos) {
            linha = MyIO.readInt();
            coluna = MyIO.readInt();
            Matriz matriz = new Matriz(linha, coluna);
            for (int i = 0; i < linha * coluna; i++) {
                int x = 0;
                x = MyIO.readInt();
                matriz.inserirPirata(x);
            }
            linha = MyIO.readInt();
            coluna = MyIO.readInt();
            Matriz matriz2 = new Matriz(linha, coluna);
            for (int i = 0; i < linha * coluna; i++) {
                int x = 0;
                x = MyIO.readInt();
                matriz2.inserirPirata(x);
            }
            matriz.imprimirDiagonalPrincipal();
            matriz.imprimirDiagonalSecundaria();
            Matriz matriz3 = new Matriz(linha, coluna);
            matriz3 = matriz3.soma(matriz, matriz2);
            matriz3.imprimir();
            matriz3 = matriz3.multiplicacao(matriz, matriz2);
            matriz3.imprimir();
            numEntrada++;
        }
    

    }
}

