package algoritmoapriori;

import Controlador.ControladorPanelSecundario;
import UI.Principal;
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
    boolean numerico;
    public List<Regla>lista1;
    public List<String>lista2;
    Principal principal;
    
    public Apriori(float minSup, float minConf , String directorio, Principal principal){
        this.minSupAp = minSup/100;
        this.minConfAp = minConf/100;
        this.directorio = directorio;
    }

    public void correrAlgoritmo(){           
        LeeFichero r = new LeeFichero(directorio); 
        List<Regla> reglas;
        r.generarListas();
        numerico = r.numerico;
        List<Elemento> elementosPri = pasadaInicial(r.getTrans(), r.getUnicos());        
        frec = new Frecuente();        
        List<Elemento> elementosFrecuentes = frec.genFrecuentesInicial(elementosPri, minSupAp, r.CanTrans);       
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
        for (int i = 0;i< candidatosIniciales.size(); i++) {
            System.out.println("elem candidato inicial"+" "+candidatosIniciales.get(i).getDescripcion()+" cant ocurrencia "+candidatosIniciales.get(i).getCantidad());
        }
        //6 - Devolver la lista de candidatos
        return candidatosIniciales;
    }   
   
    public boolean poda(String[] stEntrada,List<Elemento> listaDeFrecuentes, int k ){
       //new SubConjunto(stEntrada, k-1);
        printCombination(stEntrada, stEntrada.length, k-1);
        aux.clear();
        data = null;
        data = aux.toArray(new String[aux.size()]);
        for (int i = 0; i < data.length; i++){
            System.out.println(data[i]+" aux");
        }
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
                System.out.println("ValidarExiste "+ elemento.getDescripcion()+"/"+e);
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
                }else{
                    boolean bandera = true;
        //          3 - Comparar los primernos k-1 elementos
                    int n=0;                          
                    while (bandera && n<=(str1.length-2)){
        //           4 - Si son iguales, crear nuevo elemento y agregarlo
                        if(str1[n].equals(str2[n])){
                            stSalida [n] = str1[n];                                
                            System.out.println("compara igual "+str1[n]+" "+(str2[n])+"stSalida:"+stSalida[n]);
                        }                        
        //            5 - Si no son iguales, obtener siguiente
                        else {bandera = false;}
                        n++;
                        }
                        if(bandera){                                
                            stSalida [k-2] = str1 [str1.length-1];                                
                            stSalida [k-1] = str2 [str2.length-1];
                            System.out.println("st Salida "+String.join(" ", stSalida));
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
    
    public List <Elemento> paraKveces(List<Elemento> listaDeFrecuentes, List<List<String>> transacciones, int canTrans){
        boolean bandera = true;
        List<Elemento> listaFrecuentes;
        int k=2;
        List <Elemento> elementosCandidatosk = null;
        listaFrecuentes = listaDeFrecuentes;
        while(bandera){  
            //System.out.println("muestra k  "+k);
            elementosCandidatosk = genCandidatosFk(listaFrecuentes, k, transacciones);            
            listaFrecuentes = frec.genFrencuentesK(elementosCandidatosk,minSupAp, canTrans);                
            //frec.frecuentes.add(listaFrecuentes);                                               
            if (frec.frecuentes.get(k-1).isEmpty()){
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
    
    public void combinationUtil(String arr[], int n, int r, int index,String data1[], int i){           
        // Current combination is ready to be printed, print it
        if (index == r){
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
    
    public void printCombination(String arr[], int n, int r){
        // A temporary array to store all combination one by one
        String data1[] = new String[r];
        // Print all combination using temprary array 'data[]'
        combinationUtil(arr, n, r, 0, data1, 0);
        return;
    }
    
    public List <Regla> generarReglas (Frecuente frec, int canTrans, boolean flagNum){        
        List <Regla> reglasUnConsecuente = new ArrayList<>();
        List <List<String>> h1 = new ArrayList<>();
        List <String> unicos = new ArrayList<>();
        
        for (int i=1;i<frec.getFrecuentes().size();i++){      //i=1 porque tengo que tener en cuenta a partir de k=2            
         
            for (int j=0;j<frec.getFrecuentes().get(i).size();j++){                
                String [] aux = frec.getFrecuentes().get(i).get(j).getDescripcion().split(" ");                             
                
                for (int k=0;k<aux.length;k++){
                    Regla regla = new Regla();
                    regla.consecuentes.add(aux[k]);
                    System.out.println("cons:"+ aux[k]);                    
                    
                    for (int m=0;m<aux.length;m++){
                        if (!aux[k].equalsIgnoreCase(aux[m])){
                            regla.antecedentes.add(aux[m]);
                            System.out.println("ant:"+ aux[m]);
                        }                                        
                    }
                        // Método que calcula la confianza y soporte
                    regla.calcularConfSup(frec.getFrecuentes().get(i-1),regla.antecedentes.get(0),canTrans, frec.getFrecuentes().get(i).get(j).getCantidad());
                    if(regla.getConfianza()>=minConfAp){
                        System.out.println("regla.getConfianza() "+regla.getConfianza() );
                        reglasUnConsecuente.add(regla);
                        /*if (!h1.contains(regla.getConsecuentes())){
                             h1.add(regla.getConsecuentes());                             
                        }*/
                        for (int y=0;y<regla.getConsecuentes().size();y++){
                            if (!unicos.contains(regla.getConsecuentes().get(y))){                                
                                unicos.add(regla.getConsecuentes().get(y));                                
                            }
                        }
                    }                   
                }                      
            }
            for (int p=0;p<reglasUnConsecuente.size();p++){  
                System.out.println(reglasUnConsecuente.get(p).antecedentes+" -> "+            
                                   reglasUnConsecuente.get(p).consecuentes);
            lista1 = reglasUnConsecuente;
            
            }
            
            if(flagNum){    
                List <Integer> unAux = new ArrayList<Integer>();
                for (int p=0;p<unicos.size();p++){                    
                    int unNumAux = Integer.parseInt(unicos.get(p));
                    unAux.add(unNumAux);                    
                }
                Collections.sort(unAux);
                for (int p=0;p<unAux.size();p++){                    
                    List <String> listAux = new ArrayList<String>();                    
                    String auxStr = unAux.get(p).toString();
                    listAux.add(auxStr);
                    h1.add(listAux);
                }
            }        
            
            apGenRules(frec.frecuentes.get(i),h1, i);  
        }       
        
        return reglasUnConsecuente;
    }
       
    public void apGenRules(List<Elemento> frecuentes, List<List<String>> hm, int i) {                                                      
        for (int r=0;r<hm.size();r++){
            System.out.println("Hm "+r+" : "+hm.get(r));
        }
        int k = i+1;
        int m = 1;        
        List<List<String>> hmSig = new ArrayList<List<String>>();        
        //hm = h;
        if ((k>m+1)&&!hm.isEmpty()) {
             hmSig = genCandidatosHm(hm);
             for(int z=0;z<hmSig.size();z++){
                 //calcular conf
                 //controlar si conf es mayor a minConfAp
                 //si se cumple genero la regla
                        //calculo sup y confianza le asigno conf
                 //sino elimino el subset de hmSig
                 //volver a llamar a apGenRule
                }
        }
        apGenRules(frecuentes, hmSig, i);
    }
    
    public List<List<String>> genCandidatosHm(List<List<String>> hm) {
        List<List<String>> hmMasUno = new ArrayList<List<String>>();
        //Hacer un producto cartesiano de hm x hm                
        System.out.println("tamaño de hm: "+hm.size());
        for (int x=0;x<hm.size();x++){
            
            if (hm.get(x).size()==1){
                for (int y=x+1;y<hm.size();y++){                                                           
                    List<String> stSalida = new ArrayList<String>();                    
                    stSalida.add(hm.get(x).get(0));
                    stSalida.add(hm.get(y).get(0));
                    //System.out.println("Salida: "+stSalida);
                    if(!hmMasUno.contains(stSalida)){ 
                         hmMasUno.add(stSalida); }                   
                }                        
            }
            else{System.out.println("deberia generar x -> xx");}
        }
        
                
        /*for (int o=0;o<hmMasUno.size();o++){                        
            System.out.println("{"+hmMasUno.get(o)+"}");            
        }*/ 
        return hmMasUno;
    }
}