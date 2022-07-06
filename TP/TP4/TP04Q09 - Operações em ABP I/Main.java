
import java.util.Scanner;

class No {
    No esq;
    No dir;
    char elemento;

    public No() {
        this(' ', null, null);
    }

    public No(char elemento) {
        this.elemento = elemento;

    }

    public No(char elemento, No esq, No dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }

}

class ArvoreBB {
    private No raiz;

    public ArvoreBB() {
        raiz = null;
    }

    public void inserir(char elemento) {
        raiz = inserir(elemento, raiz);
    }

    private No inserir(char elemento, No i) {
        if (i == null) {
            i = new No(elemento);
        } else if (elemento < i.elemento) {
            i.esq = inserir(elemento, i.esq);
        } else if (elemento > i.elemento) {
            i.dir = inserir(elemento, i.dir);
        } else {
            System.out.println("Erro ao inserir");
        }

        return i;
    }

    public void mostrarPre() {
        mostrarPre(raiz);
        System.out.println();
    }

    private void mostrarPre(No i) {
        if (i != null) {
            System.out.print(i.elemento + " ");
            mostrarPre(i.esq);
            mostrarPre(i.dir);
        }
    }

    public void mostrarCentral() {
        mostrarCentral(raiz);
        System.out.println();
    }

    private void mostrarCentral(No i) {
        if (i != null) {
            mostrarCentral(i.esq);
            System.out.print(i.elemento + " ");
            mostrarCentral(i.dir);
        }
    }

    public void mostrarPos() {
        mostrarPos(raiz);
        System.out.println();
    }

    private void mostrarPos(No i) {
        if (i != null) {
            mostrarPos(i.esq);
            mostrarPos(i.dir);
            System.out.print(i.elemento + " ");
        }
    }

    public boolean pesquisar(char x) {
        return pesquisar(x, raiz);
    }

    private boolean pesquisar(char x, No i) {
        boolean resp = false;
        if (i == null) {
            resp = false;
        } else if (x < i.elemento) {
            resp = pesquisar(x, i.esq);
        } else if (x > i.elemento) {
            resp = pesquisar(x, i.dir);
        } else {
            resp = true;
        }
        return resp;
    }
}

class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        ArvoreBB arvoreBB = new ArvoreBB();
        String linha = in.nextLine();
        String split[] = new String[100];
        while (in.hasNext()) {
            split = linha.split(" ");
            if (split[0].equals("I")) {
                arvoreBB.inserir(linha.charAt(2));
            } else if (linha.equals("INFIXA")) {
                arvoreBB.mostrarCentral();
            } else if (linha.equals("PREFIXA")) {
                arvoreBB.mostrarPre();
            } else if (linha.equals("POSFIXA")) {
                arvoreBB.mostrarPos();
            } else if (split[0].equals("P")) {
                boolean resp = arvoreBB.pesquisar(linha.charAt(2));
                if (resp == true) {
                    System.out.println(linha.charAt(2) + " existe");
                } else {
                    System.out.println("nao existe");
                }
            }
            linha = in.nextLine();
        }
        if (linha.equals("INFIXA")) {
            arvoreBB.mostrarCentral();
        } else if (linha.equals("PREFIXA")) {
            arvoreBB.mostrarPre();
        } else if (linha.equals("POSFIXA")) {
            arvoreBB.mostrarPos();
        }
        in.close();
    }
}