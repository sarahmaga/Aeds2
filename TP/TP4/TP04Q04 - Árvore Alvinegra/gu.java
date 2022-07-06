import java.io.*;
import java.util.*;

class StarWars
{
	private String nome,corDoCabelo,corDaPele,corDosOlhos,anoNascimento,genero,homeworld;
	private int altura;
	private double peso;

	public StarWars ( ) 
	{

		nome = corDoCabelo = corDaPele = corDosOlhos = anoNascimento = genero = homeworld = "";
		altura = 0;
		peso = 0.0;

	}

	public StarWars (String diretorio) 
	{

		configPersonagem(diretorio);

	}

	public StarWars clone()
	{

		StarWars tmp = new StarWars();
		tmp.nome = this.nome;
		tmp.corDoCabelo = this.corDoCabelo;
		tmp.corDaPele = this.corDaPele;
		tmp.corDosOlhos = this.corDosOlhos;
		tmp.anoNascimento = this.anoNascimento;
		tmp.genero = this.genero;
		tmp.homeworld = this.homeworld;
		tmp.altura = this.altura;
		tmp.peso = this.peso;

		return tmp;	

	}

	public String getNome()
	{

		return nome;

	}

	public String getCorDoCabelo()
	{

		return corDoCabelo;

	}

	public String getCorDaPele()
	{

		return corDaPele;

	}

	public String getCorDosOlhos()
	{

		return corDosOlhos;

	}

	public String getAnoNascimento()
	{

		return anoNascimento;

	}

	public String getGenero()
	{

		return genero;

	}
	
	public String getHomeworld()
	{

		return homeworld;

	}

	public int getAltura()
	{	

		return altura;

	}

	public double getPeso()
	{	

		return peso;

	}

	public void setNome(String novoNome)
	{

		nome = novoNome;

	}

	public void setCorDoCabelo(String novaCorDoCabelo)
	{

		corDoCabelo = novaCorDoCabelo;

	}

	public void setCorDaPele(String novaCorDaPele)
	{

		corDaPele = novaCorDaPele;

	}
	
	public void setCorDosOlhos(String novaCorDosOlhos)
	{

		corDosOlhos = novaCorDosOlhos;

	}

	public void setAnoNascimento(String novoAnoNascimento)
	{

		anoNascimento = novoAnoNascimento;

	}

	public void setGenero(String novoGenero)
	{

		genero = novoGenero;

	}

	public void setHomeworld(String novoHomeworld)
	{

		homeworld = novoHomeworld;

	}

	public void setAltura(int novaAltura)
	{

		altura = novaAltura;

	}

	public void setPeso(double novoPeso)
	{

		peso = novoPeso;

	}
	
	public String imprimir()
	{

		
		String resp = " ## " + getNome() + " ## " + getAltura() + " ## 0";

		/*if( getPeso() == (int)getPeso() )
			resp += (int)getPeso();
		else
			resp += getPeso();
         */
		resp += " ## " + getCorDoCabelo() + " ## " +  getCorDaPele() + " ## " + getCorDosOlhos() + " ## " + getAnoNascimento() + " ## " + getGenero() + " ## " + getHomeworld() + " ## ";
		return resp;

	}

	public String pegarTexto(String diretorio)
	{
		String texto = "",linha = "";
		try {
			RandomAccessFile arq = new RandomAccessFile(diretorio,"r");
			linha = arq.readLine();

			while (linha != null) 
			{

				texto += linha;
				linha = arq.readLine();

			}

			arq.close();
		} 

		catch (IOException excecao) 
		{

			excecao.printStackTrace();

		}

		return texto;
	}

	public void configPersonagem(String diretorio)
	{

		int numInteiro;
		double numDouble;

		String texto = pegarTexto(diretorio),Split[];

		Split = texto.split("\'");

		setNome(Split[3]);
		
		if(Split[7].contains("unknown") == true)
		{

			setAltura(0);
		
		}		
		else
		{

			numInteiro = Integer.parseInt(Split[7]);
			setAltura(numInteiro);

		}
		if(Split[11].contains("unknown") == true)
		{

			setPeso(0);

		}
		else
		{
			
			numDouble = Double.parseDouble(Split[11]);
			setPeso(numDouble);

		}

		setCorDoCabelo(Split[15]);
		setCorDaPele(Split[19]);
		setCorDosOlhos(Split[23]);
		setAnoNascimento(Split[27]);
		setGenero(Split[31]);
		setHomeworld(Split[35]);

	}

} 

/**
 * No da arvore binaria
 * @author Max do Val Machado
 */
class No{
  public boolean cor;
  public StarWars elemento;
  public No esq, dir;

  public No (StarWars elemento){
    this.cor = false;
    this.elemento = elemento;
    this.esq = null;
    this.dir = null;
  }
  public No (StarWars elemento, boolean cor){
    this.cor = cor;
    this.elemento = elemento.clone();
    this.esq = null;
    this.dir = null;
  }
  public No (StarWars elemento, boolean cor, No esq, No dir){
    this.cor = cor;
    this.elemento = elemento.clone();
    this.esq = esq;
    this.dir = dir;
  }
}

/**
 * Arvore binaria de pesquisa
 * @author Max do Val Machado
 */
class Alvinegra {
	private No raiz; // Raiz da arvore.

	/**
	 * Construtor da classe.
	 */
	public Alvinegra() {
		raiz = null;
	}

	/**
	 * Metodo publico iterativo para pesquisar elemento.
	 * @param elemento Elemento que sera procurado.
	 * @return <code>true</code> se o elemento existir,
	 * <code>false</code> em caso contrario.
	 */
	public boolean pesquisar(String elemento) {
		System.out.print(elemento + " raiz ");
		return pesquisar(elemento, raiz);
	}

	/**
	 * Metodo privado recursivo para pesquisar elemento.
	 * @param elemento Elemento que sera procurado.
	 * @param i No em analise.
	 * @return <code>true</code> se o elemento existir,
	 * <code>false</code> em caso contrario.
	 */
	private boolean pesquisar(String x, No i) {
      boolean resp;
		if (i == null) {
         resp = false;

      } else if (x.compareTo(i.elemento.getNome()) == 0 ) {
         resp = true;

      } else if (x.compareTo(i.elemento.getNome()) < 0 ) {
		 System.out.print("esq ");
         resp = pesquisar(x, i.esq);

      } else{
		 System.out.print("dir ");
         resp = pesquisar(x, i.dir);
      }
      return resp;
	}

	/**
	 * Metodo publico iterativo para inserir elemento.
	 * @param elemento Elemento a ser inserido.
	 * @throws Exception Se o elemento existir.
	 */
	public void inserir(StarWars elemento) throws Exception {
   
      //Se a arvore estiver vazia
      if(raiz == null){
         raiz = new No(elemento, false);
         //System.out.println("Antes, zero elementos. Agora, raiz(" + raiz.elemento + ").");

      //Senao, se a arvore tiver um elemento 
      } else if (raiz.esq == null && raiz.dir == null){
         if (raiz.elemento.getNome().compareTo(elemento.getNome()) > 0){
            raiz.esq = new No(elemento, true);
            //System.out.println("Antes, um elemento. Agora, raiz(" + raiz.elemento + ") e esq(" + raiz.esq.elemento +").");
         } else {
            raiz.dir = new No(elemento, true);
            //System.out.println("Antes, um elemento. Agora, raiz(" + raiz.elemento + ") e dir(" + raiz.dir.elemento +").");
         }

      //Senao, se a arvore tiver dois elementos (raiz e dir)
      } else if (raiz.esq == null){

         if(raiz.elemento.getNome().compareTo(elemento.getNome()) > 0){
            raiz.esq = new No(elemento);
            //System.out.println("Antes, dois elementos(A). Agora, raiz(" + raiz.elemento + "), esq (" + raiz.esq.elemento +") e dir(" + raiz.dir.elemento +").");

         } else if (raiz.dir.elemento.getNome().compareTo(elemento.getNome()) > 0){
            raiz.esq = new No(raiz.elemento);
            raiz.elemento = elemento.clone();
            //System.out.println("Antes, dois elementos(B). Agora, raiz(" + raiz.elemento + "), esq (" + raiz.esq.elemento +") e dir(" + raiz.dir.elemento +").");

         } else {
            raiz.esq = new No(raiz.elemento);
            raiz.elemento = raiz.dir.elemento.clone();
            raiz.dir.elemento = elemento.clone();
            //System.out.println("Antes, dois elementos(C). Agora, raiz(" + raiz.elemento + "), esq (" + raiz.esq.elemento +") e dir(" + raiz.dir.elemento +").");
         }

         raiz.esq.cor = raiz.dir.cor = false;
         
      //Senao, se a arvore tiver dois elementos (raiz e esq)
      } else if (raiz.dir == null){
         
         if(raiz.elemento.getNome().compareTo(elemento.getNome()) < 0){
            raiz.dir = new No(elemento);
            //System.out.println("Antes, dois elementos(D). Agora, raiz(" + raiz.elemento + "), esq (" + raiz.esq.elemento +") e dir(" + raiz.dir.elemento +").");
         } else if (raiz.esq.elemento.getNome().compareTo(elemento.getNome()) < 0){
            raiz.dir = new No(raiz.elemento);
            raiz.elemento = elemento.clone();
            //System.out.println("Antes, dois elementos(E). Agora, raiz(" + raiz.elemento + "), esq (" + raiz.esq.elemento +") e dir(" + raiz.dir.elemento +").");
         } else {
            raiz.dir = new No(raiz.elemento);
            raiz.elemento = raiz.esq.elemento;
            raiz.esq.elemento = elemento;
            //System.out.println("Antes, dois elementos(F). Agora, raiz(" + raiz.elemento + "), esq (" + raiz.esq.elemento +") e dir(" + raiz.dir.elemento +").");
         }

         raiz.esq.cor = raiz.dir.cor = false;

      //Senao, a arvore tem tres ou mais elementos
      } else {
         //System.out.println("Arvore com tres ou mais elementos...");
		   inserir(elemento, null, null, null, raiz);
      }

      raiz.cor = false;
   }

   private void balancear(No bisavo, No avo, No pai, No i){

      //Se o pai tambem e preto, reequilibrar a arvore, rotacionando o avo
      if(pai.cor == true){

         //4 tipos de reequilibrios e acoplamento
         if(pai.elemento.getNome().compareTo(avo.elemento.getNome()) > 0){ // rotacao a esquerda ou direita-esquerda
            if(i.elemento.getNome().compareTo(pai.elemento.getNome()) > 0){
               avo = rotacaoEsq(avo);
            } else {
               avo = rotacaoDirEsq(avo);
            }

         } else { // rotacao a direita ou esquerda-direita
            if(i.elemento.getNome().compareTo(pai.elemento.getNome()) < 0){
               avo = rotacaoDir(avo);
            } else {
               avo = rotacaoEsqDir(avo);
            }
         }

         if (bisavo == null){
            raiz = avo;
         } else {
            if(avo.elemento.getNome().compareTo(bisavo.elemento.getNome()) < 0){
               bisavo.esq = avo;
            } else {
               bisavo.dir = avo;
            }
         }

         //reestabelecer as cores apos a rotacao
         avo.cor = false;
         avo.esq.cor = avo.dir.cor = true;
         //System.out.println("Reestabeler cores: avo(" + avo.elemento + "->branco) e avo.esq / avo.dir(" + avo.esq.elemento + "," + avo.dir.elemento + "-> pretos)");
      } //if(pai.cor == true)
   }

	/**
	 * Metodo privado recursivo para inserir elemento.
	 * @param elemento Elemento a ser inserido.
	 * @param avo No em analise.
	 * @param pai No em analise.
	 * @param i No em analise.
	 * @throws Exception Se o elemento existir.
	 */
	private void inserir(StarWars elemento, No bisavo, No avo, No pai, No i) throws Exception {
		if (i == null) {
         if(elemento.getNome().compareTo(pai.elemento.getNome()) < 0){
            i = pai.esq = new No(elemento, true);
         } else {
            i = pai.dir = new No(elemento, true);
         }
         if(pai.cor == true){
            balancear(bisavo, avo, pai, i);
         }
      } else {
        //Achou um 4-no: eh preciso fragmeta-lo e reequilibrar a arvore
         if(i.esq != null && i.dir != null && i.esq.cor == true && i.dir.cor == true){
            i.cor = true;
            i.esq.cor = i.dir.cor = false;
            if(i == raiz){
               i.cor = false;
            }else if(pai.cor == true){
               balancear(bisavo, avo, pai, i);
            }
         }
         if(elemento.getNome().compareTo(i.elemento.getNome()) < 0) {
            inserir(elemento, avo, pai, i, i.esq);
         } else if(elemento.getNome().compareTo(i.elemento.getNome()) > 0) {
            inserir(elemento, avo, pai, i, i.dir);
         } else {
            throw new Exception("Erro inserir (elemento repetido)!");
         }
      }
	}

   private No rotacaoDir(No no) {
      //System.out.println("Rotacao DIR(" + no.elemento + ")");
      No noEsq = no.esq;
      No noEsqDir = noEsq.dir;

      noEsq.dir = no;
      no.esq = noEsqDir;

      return noEsq;
   }

   private No rotacaoEsq(No no) {
      //System.out.println("Rotacao ESQ(" + no.elemento + ")");
      No noDir = no.dir;
      No noDirEsq = noDir.esq;

      noDir.esq = no;
      no.dir = noDirEsq;
      return noDir;
   }

   private No rotacaoDirEsq(No no) {
      no.dir = rotacaoDir(no.dir);
      return rotacaoEsq(no);
   }

   private No rotacaoEsqDir(No no) {
      no.esq = rotacaoEsq(no.esq);
      return rotacaoDir(no);
   }
}

public class Questao4 {

	public static void main(String[] args) throws Exception{

		Alvinegra a = new Alvinegra();
		StarWars tmp = new StarWars();
		boolean resp;

		String entrada = MyIO.readLine();

		while( entrada.contains("FIM") != true )
		{

			tmp = new StarWars(ISO88591toUTF8(entrada));
			a.inserir(tmp);

			entrada = MyIO.readLine();

		}

		entrada = MyIO.readLine();

		while( entrada.contains("FIM") != true )
		{

			resp = a.pesquisar(ISO88591toUTF8(entrada));

			if ( resp == true)
				MyIO.println("SIM");
			else
				MyIO.println ("NÃO");	

			entrada = MyIO.readLine();	
		
		}

	}
	public static String ISO88591toUTF8(String strISO) throws Exception 
	{

		byte[] isoBytes = strISO.getBytes("ISO-8859-1");
		return new String(isoBytes, "UTF-8");

	}
}