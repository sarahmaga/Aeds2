public class test {

    public void inserir(int elemento){
     int chave = hash(elemento);

     if(tabela[chave] == null){
         tabela[chave] = elemento;
     }
     else if (chave % 3 == 0) {
        int chave2 = hash(elemento); 

         if(tabela[chave2] == null){
            tabela[chave2] = elemento;
         }
         else if(tabela[chave2] != null){
             int rehashing = rehash(elemento);
             tabela[rehashing] = elemento;
         }
         else{
             inserirArvore(elemento);
         }
     }else if( == 1){
         inserirComecoFIla(elemento);
     }
     else {

        inserirArvore(elemento);
     }

    }

}

/**
 * 2/3
 * 
 * 
 */
