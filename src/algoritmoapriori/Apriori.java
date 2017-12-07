package algoritmoapriori;

import UI.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Apriori {   
    float minSupAp;
    float minConfAp;
    String directorio="";
    Frecuente frec;
    public List<Regla> reglasFinales = new ArrayList<Regla>();
    String [] data;
    ArrayList<String> aux = new ArrayList<>();
    boolean numerico;
    Principal principal;
    
    public Apriori(float minSup, float minConf , String directorio){
        this.minSupAp = minSup/100;
        this.minConfAp = minConf/100;
        this.directorio = directorio;
    }

    public void correrAlgoritmo(){    
        LeeFichero r = new LeeFichero(directorio);        
        r.generarListas();
        numerico = r.numerico;
        List<Elemento> elementosPri  = pasadaInicial(r.getTrans(), r.getUnicos());        
        frec = new Frecuente();        
        List<Elemento> elementosFrecuentes  = frec.genFrecuentesInicial(elementosPri, minSupAp, r.CanTrans);       
        paraKveces(elementosFrecuentes,r.getTrans(),r.CanTrans); 
        generarReglas(frec, r.CanTrans, numerico);        
     }
    
    public List<Elemento> pasadaInicial(List<List<String>> transacciones, List<String> unicos  ){
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
        //6 - Devolver la lista de candidatos
        return candidatosIniciales;
    }   
   
    public boolean poda(String[] stEntrada,List<Elemento> listaDeFrecuentes, int k ){
        printCombination(stEntrada, stEntrada.length, k-1);
        aux.clear();
        data = null;
        data = aux.toArray(new String[aux.size()]);
        boolean bandera = true;
        for(int i=0; i<data.length;i++){            
            if(!validarExiste(listaDeFrecuentes,data[i])){
                bandera = false;
            }
        }
        return bandera;
    }         
    
    public boolean validarExiste (List<Elemento> elementos, String e){
        for (Elemento elemento:elementos)
            {
              
                if(elemento.getDescripcion().equalsIgnoreCase(e))
                {                    
                    return true;
                }
            }
        return false;
    }
    
    public List<Elemento> genCandidatosFk(List<Elemento> listaDeFrecuentes, int k, List<List<String>> transacciones) {
        List <Elemento> candidatosK = new ArrayList<Elemento>();
        String [] stSalida = new String [k];        
//            1 - Tomar primer elemento de freceuente k-1
        for (int i=0;i<listaDeFrecuentes.size();i++){
//            2 - Tomar segundo elemento freceuente k-1        
            String [] str1 = listaDeFrecuentes.get(i).getDescripcion().split(" ");                 
            for (int j=i+1;j<listaDeFrecuentes.size();j++){                    
                String [] str2 = listaDeFrecuentes.get(j).Descripcion.split(" ");               
                if (k==2 ){                        
                    stSalida [k-2] = str1[0];
                    stSalida [k-1] = str2[0];
                                         
                    Elemento elemento = new Elemento(String.join(" ", stSalida));  
                    elemento.setCantidad(0);
                    elemento.obtenerFrecuencia(transacciones,stSalida);                        
                    candidatosK.add(elemento);
                    
                }else{
                    boolean bandera = true;
        //          3 - Comparar los primernos k-1 elementos
                    int n=0;                          
                    while (bandera && n<=(str1.length-2)){
        //           4 - Si son iguales, crear nuevo elemento y agregarlo
                        if(str1[n].equals(str2[n])){
                            stSalida [n] = str1[n];                                
                           
                        }                        
        //            5 - Si no son iguales, obtener siguiente
                        else {bandera = false;}
                        n++;
                        }
                        if(bandera){                                
                            stSalida [k-2] = str1 [str1.length-1];                                
                            stSalida [k-1] = str2 [str2.length-1];                            
                            String salida = String.join(" ", stSalida);
                            if (poda(stSalida,listaDeFrecuentes,k)){
                                Elemento elemento = new Elemento(salida);
                                elemento.obtenerFrecuencia(transacciones,stSalida);
                                candidatosK.add(elemento);                                
                            }
                        }                       
                    } 
                }                                
            }              
        return candidatosK;
    }    
    
    public List<Elemento> paraKveces(List<Elemento> listaDeFrecuentes, List<List<String>> transacciones, int canTrans){
        boolean bandera = true;
        List<Elemento> listaFrecuentes;
        int k=2;
        List <Elemento> elementosCandidatosk = null;
        listaFrecuentes = listaDeFrecuentes;
        while(bandera){  
            elementosCandidatosk = genCandidatosFk(listaFrecuentes, k, transacciones);            
            listaFrecuentes = frec.genFrencuentesK(elementosCandidatosk,minSupAp, canTrans);                
                       
            if (frec.frecuentes.get(k-1).isEmpty()){
                bandera=false;
            }                
            k++;
        }          
        return elementosCandidatosk;    
    }
    
    public void combinationUtil(String arr[], int n, int r, int index,String data1[], int i){           
        // Current combination is ready to be printed, print it
        if (index == r){
            for (int j=0; j<r; j++)
                aux.add(String.join(" ", data1));
        return ;
        }
        if (i >= n)
        return;
        
        data1[index] = arr[i];
        combinationUtil(arr, n, r, index+1, data1, i+1);
        combinationUtil(arr, n, r, index, data1, i+1);
    }
	
    public void printCombination(String arr[], int n, int r){
       String data1[] = new String[r];
       combinationUtil(arr, n, r, 0, data1, 0);
        return;
    }
    
    public void generarReglas (Frecuente frec, int canTrans, boolean flagNum){ 
       
         System.out.println(reglasFinales.size()+"size--------------------------------------");
        List <Regla> reglasUnConsecuente = new ArrayList<Regla>();
          List <String> unicos = new ArrayList<String>(); //lista UNICOS es H1 
    
        
        for (int i=1;i<frec.getFrecuentes().size()-1;i++){      //i=1 porque tengo que tener en cuenta a partir de k=2         
             
            for (int j=0;j<frec.getFrecuentes().get(i).size();j++){                
                String [] aux = frec.getFrecuentes().get(i).get(j).getDescripcion().split(" ");   
                for (int u=aux.length-1;u>-1;u--){
                    Regla regla = new Regla();
                    regla.consecuentes.add(aux[u]);                                    
                    for (int s=aux.length-1;s>-1;s--){
                        if (!aux[u].equalsIgnoreCase(aux[s])){
                            regla.antecedentes.add(aux[s]);
                        }                                        
                    }
                        // Método que calcula la confianza y soporte
                    Collections.sort(regla.antecedentes);
                    
                    regla.calcularConfSup(String.join(" ", aux),frec.getFrecuentes().get(i).get(j).getCantidad(),frec.getFrecuentes().get(i-1),regla.antecedentes,canTrans,frec.frecuentes,i);                    
                    
                    if(regla.getConfianza()>=minConfAp){
                        reglasUnConsecuente.add(regla);
                        reglasFinales.add(regla); 
                        for (int y=0;y<regla.getConsecuentes().size();y++){
                          
                            if (!unicos.contains(regla.getConsecuentes().get(y))){                                
                                unicos.add(regla.getConsecuentes().get(y));  

                            }
                        }
                    }                   
                }                            
            } 
            
             if(flagNum){    
                List <Integer> unAux = new ArrayList<Integer>();
                for (int p=0;p<unicos.size();p++){                    
                    int unNumAux = Integer.parseInt(unicos.get(p));
                    unAux.add(unNumAux);                    
                }
                Collections.sort(unAux);
                unicos.clear();
                for (int p=0;p<unAux.size();p++){                    
                    List <String> listAux = new ArrayList<String>();                    
                    String auxStr = unAux.get(p).toString();
                    listAux.add(auxStr);
                    unicos.add(auxStr);
                }
            }        
            int m=1;
            apGenRules(frec.getFrecuentes().get(i),unicos, i,canTrans, m,frec.frecuentes);
           unicos.clear();
        }                  
    }
       
    public void apGenRules(List<Elemento> frecuentes, List<String> h1, int i, int CanTrans, int m, List<List<Elemento>> f) {                                                      
        Collections.sort(h1);
        
        int k = i+1;
        //1 - Comprobar que k>m+1, y Hm <> 0
        if ((k>m+1)&&!h1.isEmpty()) {               
            List<String> hNuevo = new ArrayList<String>();
            hNuevo = genCandidatosHm(h1);
                for (int o=0;o<hNuevo.size();o++){  
                    for (int y=0;y<frecuentes.size();y++){                    
                        Auxiliar aux = new Auxiliar (hNuevo.get(o),frecuentes.get(y).getDescripcion());
                        if(aux.contieneElemento()){
                            Regla regla = new Regla();
                            regla.consecuentes = aux.auxHfrec;
                            regla.antecedentes = aux.diferencia();
                            regla.calcularConfSup(frecuentes.get(y).getDescripcion(),frecuentes.get(y).getCantidad(),frec.getFrecuentes().get(i-1),regla.antecedentes,CanTrans,f,k);                    

                            if(regla.getConfianza()>=minConfAp){
                                reglasFinales.add(regla);
                            }                                  
                        }                       
                    }
                }  
                apGenRules(frec.getFrecuentes().get(i),hNuevo, i,CanTrans,m+1,frec.frecuentes);
        }
    }    

    public List<String> genCandidatosHm(List<String> hm) {
        List<String> hmMasUno = new ArrayList<String>();
        //Hacer un producto cartesiano de hm x hm                      
        String [] auxSt = hm.get(0).split(" ");
        if (auxSt.length == 1){
            for (int x=0;x<hm.size();x++){
                for (int y=x+1;y<hm.size();y++){
                    String [] auxAnt = new String[2];
                    auxAnt[0] = hm.get(x); auxAnt[1] = hm.get(y);                    
                    String auxAntSt = String.join(" ", auxAnt);
                    if(!hmMasUno.contains(auxAntSt)){hmMasUno.add(auxAntSt);}
                }
            }                                                
        }else{            
            int t = auxSt.length+1; 
            String [] stSalida = new String[t];
            for (int x=0;x<hm.size();x++){
               String [] auxSt1 = hm.get(x).split(" ");
                for (int y=x+1;y<hm.size();y++){
                   String [] auxSt2 = hm.get(y).split(" ");
                   boolean bandera = true;        
                    int n=0;                          
                    while (bandera && n<=(auxSt1.length-2)){        
                        if(auxSt1[n].equals(auxSt2[n])){
                            stSalida [n] = auxSt1[n];  
                        }                        
                        else {bandera = false;}
                        n++;
                        }
                        if(bandera){  
                            stSalida [t-2] = auxSt1[auxSt1.length-1];                                
                            stSalida [t-1] = auxSt2[auxSt2.length-1]; 
                            
                            String salida = String.join(" ", stSalida);
                            if(podaHm(stSalida,hm,t)){
                                if(!hmMasUno.contains(salida)){hmMasUno.add(salida);}
                            }
                            
                            
                        }                                                                                                                                                                                                                 
                }
            }            
        }
       return hmMasUno;   
    }
    
    public boolean podaHm(String[] stEntrada,List<String> hm, int k ){
        printCombination(stEntrada, stEntrada.length, k-1);
        aux.clear();
        data = null;
        data = aux.toArray(new String[aux.size()]);
        boolean bandera = true;
        for(int i=0; i<data.length;i++){            
            if(!validarExisteHm(hm,data[i])){
                bandera = false;
            }
        }
        return bandera;
    }
    
    public boolean validarExisteHm (List<String> elementos, String e){
        for (String palabra:elementos)
            {
                if(palabra.equalsIgnoreCase(e))
                {                    
                    return true;
                }
            }
        return false;
    }
    
    
}