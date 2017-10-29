package algoritmoapriori;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Apriori {   
    float minSupAp;
    float minConfAp;
    String directorio;
    Frecuente frec;
    String [] data;
    ArrayList<String> aux= new ArrayList<>();
    
   public Apriori(float minSup, float minConf , String directorio) {
        this.minSupAp = minSup/100;
        this.minConfAp = minConf/100;
        this.directorio = directorio;
    }

    public void correrAlgoritmo()    
    {    
       
        LeeFichero r = new LeeFichero(directorio);       
        r.generarListas();
        for (int i=0;i<r.getUnicos().size();i++){
            System.out.println(r.getUnicos().get(i));
        }
        
        List<Elemento> elementosPri = pasadaInicial(r.getTrans(), r.getUnicos());
        //pruebaDePasada(elementosPri);
        frec = new Frecuente();        
        List<Elemento> elementosFrecuentes = frec.genFrecuentesInicial(elementosPri, minSupAp, r.CanTrans);
        frec.frecuentes.add(elementosFrecuentes);
        paraKveces(elementosFrecuentes,r.getTrans(),r.CanTrans);
        
       // 1 - Generar Candidatos k
       // 2 - Generar Frecuentes k
    }
    
    private List<Elemento> pasadaInicial(List<List<String>> transacciones, List<String> unicos  )
    {
        ArrayList<Elemento> candidatosIniciales = new ArrayList<Elemento>();
        //1 - Tomar primer transaccion
        for (int x=0; x<unicos.size();x++){  
            //2 - Obtener primer elemento de la lista ordenada Unicos
            String unUnico = unicos.get(x);
            Elemento elemento = new Elemento(unUnico);
            for(int i=0;i<transacciones.size();i++){            
                List<String> transaccionI = transacciones.get(i); 
                //3 - buscar en cada trans si aparece el primer elem unico
                //4 - Si existe en la trans aumenta cantidad de ocurrencia del elto.
                if(transaccionI.contains(unUnico)){
                    elemento.setCantidad(elemento.getCantidad()+1);
                }
            }
             candidatosIniciales.add(elemento);
        }
        for (int i = 0;i< candidatosIniciales.size(); i++) {
            System.out.println("elem candidato inicial"+" "+candidatosIniciales.get(i).getDescripcion()+" cant ocurrencia "+candidatosIniciales.get(i).getCantidad());
        }
        //6 - Devolver la lista de candidatos
        return candidatosIniciales;
   }   
    /*---------ESTO ESTABA DESPUES DE CERRAR for QUE RECORRE TRANSACCIONES DE pasadaInicial
                //2 - Obtener primer elemento
               for (int j=0;j<transaccionI.size();j++){
                    String elementoEnTransaccion = transaccionI.get(j);
                    
                    //3 - Ver si ya existe en la lista de candidatos

                    if(!validarExistEInc(candidatosIniciales,elementoEnTransaccion)){
                        Elemento elemento = new Elemento(elementoEnTransaccion);
                        //4 - Si no existe, agregarlo
                        candidatosIniciales.add(elemento);
                    }
                }*/
    
    
    /*--------------- VER SI SE UTILIZARA ESTE METODO
    private List<Elemento> generarCandidatosK(List<Elemento> listaDeFrecuentes, int k , List<List<String>> transacciones)
    {           
        
        if(k == 2) {
            return crearParaKIgualDos(listaDeFrecuentes, k, transacciones);
        } else {
            //return crearParaKIgualN(listaDeFrecuentes, k, transacciones);
            return null;
        }
    }
    
        List <Elemento> candidatosK = new ArrayList<Elemento>();
        String [] stSalida = new String [k];        
//            1 - Tomar primer elemento de freceuente k-1
            for (int i=0;i<listaDeFrecuentes.size();i++){
//            2 - Tomar segundo elemento freceuente k-1                
                String [] str1 = listaDeFrecuentes.get(i).Descripcion.split(" ");
                for (int j=i+1;j<listaDeFrecuentes.size();j++){                    
                    String [] str2 = listaDeFrecuentes.get(j).Descripcion.split(" ");
                    System.out.println("String de str1: "+Arrays.toString(str1));
                    System.out.println("String de str2: "+Arrays.toString(str2));
                    if (k==2 ){
                        
                        stSalida [k-2] = str1[0];
                        stSalida [k-1] = str2[0];
                        System.out.println("String de salida: "+Arrays.toString(stSalida));
                        Elemento elemento = new Elemento(Arrays.toString(stSalida));
                        elemento.setCantidad(0);
                        elemento.obtenerFrecuencia(transacciones,stSalida);
                        
                        candidatosK.add(elemento);
                    }else{
                            boolean bandera = true;
        //            3 - Comparar los primernos k-1 elementos
                            for (int n=0;(bandera) && n<(str1.length-1);n++)
                            {
        //            4 - Si son iguales, crear nuevo elemento y agregarlo
                                if(str1[n]==str2[n]){
                                    stSalida [n] = str1[n];
                                }
        //            5 - Si no son iguales, obtener siguiente
                                else {bandera = false;}
                            }
                            if(bandera){
                                stSalida [k-1] = str1 [str1.length-1];
                                stSalida [k] = str2 [str2.length-1];
                            }
                            PruebaPoda.pruebapodas(poda(stSalida,listaDeFrecuentes,k));
                        }
                    String salida = stSalida.toString();
                    //System.out.println("St Salida "+Arrays.toString(stSalida));
                    //PruebaPoda.pruebapodas(poda(stSalida,listaDeFrecuentes,k));
                    /*if (poda(stSalida,listaDeFrecuentes,k)){
                        Elemento elemento = new Elemento(salida);
                        elemento.obtenerFrecuencia(transacciones,stSalida);
                        candidatosK.add(elemento);
                    }                               
                }                                
            }                                                    
        return candidatosK;
    }   
*/
    /**
     * A partir de un conjunto de tamaño k, armar todos los subconjuntos k - 1 posibles
     * y comparar que todos esos subconjuntos esten en k-1 de frecuentes
     * @param stEntrada contiene el par ordenado generado en k
     * @param listaDeFrecuentes lista de frecuentes f(k-1)
     * @param k pasada actual y tamaño de la tupla
     * @return 
     */
    private boolean poda(String[] stEntrada,List<Elemento> listaDeFrecuentes, int k )
    {
       //new SubConjunto(stEntrada, k-1);
        printCombination(stEntrada, stEntrada.length, k-1);
        aux.clear();
        data = null;
        data = aux.toArray(new String[aux.size()]);
        for (int i = 0; i < data.length; i++) {
            System.out.println(data[i]+" aux");
        }
        boolean bandera = true;
        for(int i=0; i<data.length;i++)
        {
            
            if(!validarExiste(listaDeFrecuentes,data[i]))
            {
                    bandera = false;
            }
        }
        return bandera;
    }
        
       

    
    private boolean validarExiste (List<Elemento> elementos, String e)
    {
        for (Elemento elemento:elementos)
            {
                System.out.println("ValidarExiste "+ elemento.getDescripcion()+"/"+e);
                if(elemento.getDescripcion().equalsIgnoreCase(e))
                {                    
                    return true;
                }
            }
        return false;
    } 


    private List<Elemento> crearParaKIgualN(List<Elemento> listaDeFrecuentes, int k, List<List<String>> transacciones) {
        List <Elemento> candidatosK = new ArrayList<Elemento>();
        String [] stSalida = new String [k];        
//            1 - Tomar primer elemento de freceuente k-1
            for (int i=0;i<listaDeFrecuentes.size();i++){
//            2 - Tomar segundo elemento freceuente k-1        
                String [] str1 = listaDeFrecuentes.get(i).getDescripcion().split(" ");
                 
                for (int j=i+1;j<listaDeFrecuentes.size();j++){                    
                    String [] str2 = listaDeFrecuentes.get(j).Descripcion.split(" ");
                    System.out.println("-----------------");
                    System.out.println("String de str1: "+String.join(" ", str1));
                    System.out.println("String de str2: "+String.join(" ", str2));
                    if (k==2 ){
                        
                        stSalida [k-2] = str1[0];
                        stSalida [k-1] = str2[0];
                        //System.out.println("String de salida: "+Arrays.toString(stSalida));                        
                        Elemento elemento = new Elemento(String.join(" ", stSalida));  
                        elemento.setCantidad(0);
                        elemento.obtenerFrecuencia(transacciones,stSalida);
                        
                        candidatosK.add(elemento);
                        System.out.println("prueba eve - elemento de salida: "+String.join(" ", stSalida)); 
                    }else{
                            boolean bandera = true;
        //            3 - Comparar los primernos k-1 elementos
                            int n=0;
                            
                        while (bandera && n<=(str1.length-2)) {
        //            4 - Si son iguales, crear nuevo elemento y agregarlo
                             if(str1[n].equals(str2[n])){
                                stSalida [n] = str1[n];
                                
                                System.out.println("compara igual "+str1[n]+" "+(str2[n])+"stSalida:"+stSalida[n]);
                              }
        //            5 - Si no son iguales, obtener siguiente
                                   else {bandera = false;}
                            n++;
                           }
                            if(bandera){
                                //System.out.println("stSalida "+stSalida [0]);
                                                      stSalida [k-2] = str1 [str1.length-1];
                                //System.out.println("stSalida "+stSalida [k-2]);
                                                     stSalida [k-1] = str2 [str2.length-1];
                                System.out.println("st Salida "+String.join(" ", stSalida));
                                                    String salida = String.join(" ", stSalida);
                                        if (poda(stSalida,listaDeFrecuentes,k)){
                                            Elemento elemento = new Elemento(salida);
                                            elemento.obtenerFrecuencia(transacciones,stSalida);
                                            candidatosK.add(elemento);
                                            System.out.println("prueba eve2 - elemento de salida: "+String.join(" ", stSalida)); 
                                        }
                            }
                            //PruebaPoda.pruebapodas(poda(stSalida,listaDeFrecuentes,k));
                        
//                    String salida = stSalida.toString();
//                    System.out.println("St Salida k mayor 2"+Arrays.toString(stSalida));
//                    //PruebaPoda.pruebapodas(poda(stSalida,listaDeFrecuentes,k));
//                    System.out.println("poda"+poda(stSalida,listaDeFrecuentes,k)+" "+k);
//                    if (poda(stSalida,listaDeFrecuentes,k)){
//                        Elemento elemento = new Elemento(salida);
//                        elemento.obtenerFrecuencia(transacciones,stSalida);
//                        candidatosK.add(elemento);
//                    }  
                  } 
                }                                
            }    
           
        return candidatosK;
     }
    
    private List <Elemento> paraKveces(List<Elemento> listaDeFrecuentes, List<List<String>> transacciones, int canTrans){
       boolean bandera = true;
       List<Elemento> listaFrecuentes;
       int k=2;
       List <Elemento> elementosCandidatosk = null;
       listaFrecuentes = listaDeFrecuentes;
        while(bandera)
        {  
            System.out.println("muestra k  "+k);
                elementosCandidatosk = crearParaKIgualN(listaFrecuentes, k, transacciones);
                    //System.out.println("muestra frecuentes en K=2  "+elementosCandidatosk.get(k).getDescripcion());
                    //System.out.println("muestra frecuentes  "+elementosCandidatosk.get(k).getCantidad());
                listaFrecuentes = frec.genFrencuentesK(elementosCandidatosk,minSupAp, canTrans);
                
                frec.frecuentes.add(listaFrecuentes);
                    
               
                //pruebaDePasada(elementosCandidatosk);
                
                if (frec.frecuentes.get(k-1).isEmpty())
                {
                    bandera=false;
                }
                
                k++;
        }
                    for(int i=0; i<frec.frecuentes.size();i++){
                        for(int j=0;j<frec.frecuentes.get(i).size();j++){   
                        System.out.println("cant de frec frecuetnes "+frec.frecuentes.get(i).get(j).getDescripcion());
                        }
                    }
           
        return elementosCandidatosk;    
    }
    
    public void combinationUtil(String arr[], int n, int r, int index,
								String data1[], int i)
	{
           
		// Current combination is ready to be printed, print it
		if (index == r)
		{
		for (int j=0; j<r; j++)
			System.out.print(data1[j]+"");
                       aux.add(String.join(" ", data1));
			System.out.println("");
		return ;
		}

		// When no more elements are there to put in data[]
		if (i >= n)
		return;

		// current is included, put next at next location
		data1[index] = arr[i];
		combinationUtil(arr, n, r, index+1, data1, i+1);

		// current is excluded, replace it with next (Note that
		// i+1 is passed, but index is not changed)
		combinationUtil(arr, n, r, index, data1, i+1);
	}

	// The main function that prints all combinations of size r
	// in arr[] of size n. This function mainly uses combinationUtil()
	public void printCombination(String arr[], int n, int r)
	{
		// A temporary array to store all combination one by one
		String data1[]=new String[r];

		// Print all combination using temprary array 'data[]'
		combinationUtil(arr, n, r, 0, data1, 0);
                return;
	}
}



	/* arr[] ---> Input Array
	data[] ---> Temporary array to store current combination
	start & end ---> Staring and Ending indexes in arr[]
	index ---> Current index in data[]
	r ---> Size of a combination to be printed */
	

	/*Driver function to check for above function*/
	

        
//        1 - Obtener el tamaño de srEntrada
        //["1 2", "3 3"]
        // [null, null]
//        2 - Armar los subconjuntos temporales a partir de stEntrada
//        3 - Comprobar que todos los subconjutos temporales estén en la listaDeFrecuentes
      
//    private void pruebaDePasada(List<Elemento> elementos)
//    {    
//      for (int i=0;i<elementos.size();i++)
//        {
//            System.out.println("Elemento :" + elementos.get(i).getDescripcion());
//            System.out.println("Cantidad :" + elementos.get(i).getCantidad());   
//        }  
//    }

/*  
    private boolean validarExistEInc (List<Elemento> elementos, String e)
    {
        for (Elemento elemento:elementos)
            {
                if(elemento.getDescripcion().equalsIgnoreCase(e))
                {
                    elemento.setCantidad(elemento.getCantidad()+1);
                    return true;
                }
            }
        return false;
    }*/ 


    /*private List<Elemento> crearParaKIgualDos(List<Elemento> listaDeFrecuentes, int k , List<List<String>> transacciones) {
        List <Elemento> candidatosK = new ArrayList<Elemento>();
        String [] stSalida = new String [k];
        for (int i=0; i<listaDeFrecuentes.size(); i++){
        // 2 - Tomar segundo elemento freceuente k-1                
            String [] str1 = listaDeFrecuentes.get(i).Descripcion.split(" ");
            for (int j=i+1; j<listaDeFrecuentes.size(); j++){                    
                String [] str2 = listaDeFrecuentes.get(j).Descripcion.split(" ");
                System.out.println("String de str1: "+Arrays.toString(str1));
                System.out.println("String de str2: "+Arrays.toString(str2));
                System.out.println("String de j: "+j);
                stSalida [k-2] = str1[0];
                stSalida [k-1] = str2[0];
                System.out.println("String de salida: "+Arrays.toString(stSalida));
                Elemento elemento = new Elemento(Arrays.toString(stSalida));
                elemento.setCantidad(0);
                elemento.obtenerFrecuencia(transacciones,stSalida);

                candidatosK.add(elemento);

            }
        }
                    
        return candidatosK;
    }*/