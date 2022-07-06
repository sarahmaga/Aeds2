import java.text.ParseException;

public class test {
    public void startHeapsort(Filme [] filmes, int n) throws Exception {

        int i = 0, j = 1;
        heapsortAltura(filmes, n);
        while (i != (filmes.length - 1) && j != (filmes.length - 1)) {
            while (filmes[j].getGenero().compareTo(filmes[i].getGenero()) <= 0) {
                j++;
            }
            if (j - i != 0)
                insertionNome(filmes, i, j);
            i = j;
        }
       
    }
    public void constroiAltura(int tamHeap, Filme []filmes) throws ParseException {
        for (int i = tamHeap; i > 1 && filmes[i].getGenero().compareTo(filmes[i / 2].getGenero()) > 0; i /= 2) {
            swap(filmes,i, i / 2);
        }
    }
    public void reconstroiAltura(int tamHeap, Filme []filmes) throws ParseException {
        int i = 1, filho;
        while (i <= (tamHeap / 2)) {
            if (filmes[2 * i].getGenero().compareTo(filmes[2 * i + 1].getGenero()) > 0  || 2 * i == tamHeap) {
                filho = 2 * i;
            } else {
                filho = 2 * i + 1;
            }
            if (filmes[i].getGenero().compareTo(filmes[filho].getGenero()) < 0 ) {
                swap(filmes, i, filho);
                i = filho;
            } else {
                i = tamHeap;
            }
        }
    }
    /**
     * Algoritmo de ordenacao Heapsort.
     * @throws ParseException
     */
    public void heapsortAltura(Filme[] filmes, int n) throws ParseException {
        // Alterar o vetor ignorando a posicao zero
        Filme[] tmp = new Filme[n + 1];
        for (int i = 0; i < n; i++) {
            tmp[i + 1] = filmes[i].cloneFilme();
        }
        filmes = tmp;
        // Contrucao do heap
        for (int tamHeap = 2; tamHeap <= n; tamHeap++) {
            constroiAltura(tamHeap, filmes);
        }
        /*
         * for(int i = 1; i < tmp.length; i++){
         * System.out.println(tmp[i]);
         * }
         */
        // Ordenacao propriamente dita
        int tamHeap = n;
        while (tamHeap > 1) {
            swap(filmes, 1, tamHeap--);
            reconstroiAltura(tamHeap, filmes);
        }
        // Alterar o vetor para voltar a posicao zero
        tmp = filmes.clone();
        filmes = new Filme[n];
        for (int i = 0; i < n; i++) {
            filmes[i] = tmp[i + 1].cloneFilme();
        }
    }
    public void swap(Filme[] filmes,int i, int j) throws ParseException {
        Filme temp = filmes[i].cloneFilme();
        filmes[i] = filmes[j].cloneFilme();
        filmes[j] = temp.cloneFilme();
    }
    public void insertionNome(Filme [] filmes, int inicio, int fim) throws Exception {
        for (int i = inicio + 1; i < fim; i++) {
           Filme tmp = filmes[i].cloneFilme();
           int j = i - 1;
           while ((j >= 0) && (filmes[j].getNome().compareTo(tmp.getNome()) > 0)) {
              numDeMovimentacoes++;
              numDeComparacoes++;
              filmes[j + 1] = filmes[j].cloneFilme();
              j--;
           }
           numDeMovimentacoes++;
           filmes[j + 1] = tmp.cloneFilme();
        }
    }
}
