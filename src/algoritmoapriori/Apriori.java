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
        
        frecuentesK(elementosFrecuentes,r.getTrans(),r.CanTrans);
        
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
//                //2 - Obtener primer elemento
//                for (int j=0;j<transaccionI.size();j++){
//                    String elementoEnTransaccion = transaccionI.get(j);
                    
                    //3 - Ver si ya existe en la lista de candidatos

//                    if(!validarExistEInc(candidatosIniciales,elementoEnTransaccion)){
//                        Elemento elemento = new Elemento(elementoEnTransaccion);
//                        //4 - Si no existe, agregarlo
//                        candidatosIniciales.add(elemento);
//                    }
                //}
            }
             candidatosIniciales.add(elemento);
        }
        for (int i = 0;i< candidatosIniciales.size(); i++) {
            System.out.println("elem candidato inicial"+" "+candidatosIniciales.get(i).getDescripcion()+" cant ocurrencia "+candidatosIniciales.get(i).getCantidad());
        }
        //6 - Devolver la lista de candidatos
        return candidatosIniciales;
   }   
    
    
    private List<Elemento> generarCandidatosK(List<Elemento> listaDeFrecuentes, int k , List<List<String>> transacciones)
    {           
        
        if(k == 2) {
            return crearParaKIgualDos(listaDeFrecuentes, k, transacciones);
        } else {
            //return crearParaKIgualN(listaDeFrecuentes, k, transacciones);
            return null;
        }
    }
    /*
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
    private boolean poda(String[] stEntrada,List<Elemento> listaDeFrecuentes, int k ){
        
//        1 - Obtener el tamaño de srEntrada
        //["1 2", "3 3"]
        // [null, null]
//        2 - Armar los subconjuntos temporales a partir de stEntrada
//        3 - Comprobar que todos los subconjutos temporales estén en la listaDeFrecuentes
        
        
        boolean bandera = true;
        System.out.println("Candidad Entrada :"+k);
        String [] aux = new String [k];
        int pos = 0;
        int i=0;
        while ((i<k)&&(bandera)){
            for (int j=i+1;j<k;j++)
            {         
                aux[pos] = stEntrada[j-1];                
                if(!validarExiste(listaDeFrecuentes,Arrays.toString(stEntrada))){
                    bandera = false;
                }
                pos++;
            }
            System.out.println("Auxiliar "+Arrays.toString(aux));
            i++;
        }              
        return bandera;
        
        //return false;
    }
    
    
    
    private void pruebaDePasada(List<Elemento> elementos)
    {    
      for (int i=0;i<elementos.size();i++)
        {
            System.out.println("Elemento :" + elementos.get(i).getDescripcion());
            System.out.println("Cantidad :" + elementos.get(i).getCantidad());   
        }  
    }

// ver si este metodo queda!!   
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
    } 
    
    private boolean validarExiste (List<Elemento> elementos, String e)
    {
        for (Elemento elemento:elementos)
            {
                System.out.println("Existe "+ elemento.getDescripcion().equalsIgnoreCase(e));
                if(elemento.getDescripcion().equalsIgnoreCase(e))
                {                    
                    return true;
                }
            }
        return false;
    } 

    private List<Elemento> crearParaKIgualDos(List<Elemento> listaDeFrecuentes, int k , List<List<String>> transacciones) {
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
    }

    private List<Elemento> crearParaKIgualN(List<Elemento> listaDeFrecuentes, int k, List<List<String>> transacciones) {
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
                            int n=0;
                        while (bandera && n<(str1.length-2)) {
        //            4 - Si son iguales, crear nuevo elemento y agregarlo
                             if(str1[n]==str2[n]){
                                stSalida [n] = str1[n];
                                   }
        //            5 - Si no son iguales, obtener siguiente
                                   else {bandera = false;}
                            n++;
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
                    if (poda(stSalida,listaDeFrecuentes,k)){
                        Elemento elemento = new Elemento(salida);
                        elemento.obtenerFrecuencia(transacciones,stSalida);
                        candidatosK.add(elemento);
                    }                               
                }                                
            }                                                    
        return candidatosK;
     }
    
    private List <Elemento> frecuentesK(List<Elemento> listaDeFrecuentes, List<List<String>> transacciones, int canTrans){
       boolean bandera = true;
       int k=2;
       List <Elemento> elementosCandidatosk = null;
        while(bandera){          
                elementosCandidatosk = crearParaKIgualN(listaDeFrecuentes, k, transacciones);
                frec.genFrencuentesK(elementosCandidatosk,minSupAp, canTrans);
                pruebaDePasada(elementosCandidatosk);
                
                if (frec.frecuentes.get(k).size()==1){
                    bandera=false;
                }
                k++;
                    }
           
        return elementosCandidatosk;    
    }
}
