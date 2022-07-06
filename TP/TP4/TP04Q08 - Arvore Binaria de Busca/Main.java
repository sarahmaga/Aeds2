import java.io.BufferedReader;
import java.util.Scanner;

class No {
    No esq;
    No dir;
    int elemento;

    public No() {
        this(0, null, null);
    }

    public No(int elemento) {
        this.elemento = elemento;

    }

    public No(int elemento, No esq, No dir) {
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

    public void inserir(int elemento) {
        raiz = inserir(elemento, raiz);
    }

    private No inserir(int elemento, No i) {
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
        System.out.print("Pre.: ");
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
        System.out.print("In..: ");
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
        System.out.print("Post: ");
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
}

class Main {
    public static void main(String[] args) {
        int numCase = 1;
        Scanner in = new Scanner(System.in);
        int numCasos = in.nextInt();
        //BufferedReader entrada = new BufferedReader(System.in,);
        for (int i = 0; i < numCasos; i++) {
            int numElementos = in.nextInt();
            ArvoreBB arvoreBB = new ArvoreBB();
            for (int j = 0; j < numElementos; j++) {
                int inserido = in.nextInt();
                arvoreBB.inserir(inserido);
            }
            System.out.println("Case " + numCase + ":");
            numCase++;
            arvoreBB.mostrarPre();
            arvoreBB.mostrarCentral();
            arvoreBB.mostrarPos();
            System.out.println();
        }
        in.close();
    }
}