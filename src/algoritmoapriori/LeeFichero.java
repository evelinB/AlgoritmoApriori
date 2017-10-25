package algoritmoapriori;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static java.util.Collections.list;

class LeeFichero {        
    public static List<List<String>> transacciones = new ArrayList<List<String>>();
    public List<String> unicos = new ArrayList<String>();
    public int CanTrans = 0;  
    String directorio;

    public LeeFichero(String directorio) {
             this.directorio = directorio;
    }
    
    
    public void generarListas(){
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;        
        try {
        // Apertura del fichero y creacion de BufferedReader para poder hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File (directorio);
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);          
            // Lectura y recorrido del fichero
            String linea;
            while((linea = br.readLine())!=null) {
                this.CanTrans++;
                List <String> itemSet = new ArrayList<String>();                
                String [] arreglo = linea.split(" ");                
                for(int k=0;k<arreglo.length;k++) {                   
                    itemSet.add(arreglo[k]);
                    if(!unicos.contains(arreglo[k])){
                        unicos.add(arreglo[k]);
                    }
                }
                System.out.println("itemSet" + itemSet);
                Collections.sort(itemSet);
                transacciones.add(itemSet);
            }
            Collections.sort(unicos);
            System.out.println("Cantidad de transacciones: "+CanTrans);
        }
        catch(Exception e){
        e.printStackTrace();}
        finally{// En el finally cerramos el fichero, para asegurarnos que se cierra tanto si todo va bien como si salta una excepcion.
            try{                    
                if( null != fr ){   
                fr.close();}                  
            }catch (Exception e2){ 
                e2.printStackTrace();}
        }
    }
    public List<List<String>> getTrans(){
        return transacciones;
    }
    
    public List<String> getUnicos(){
        return unicos;
    }
} 