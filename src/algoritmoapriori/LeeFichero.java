package algoritmoapriori;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static java.util.Collections.list;

class LeeFichero {        
    public static List<List<String>> transacciones = new ArrayList<List<String>>();
    public List<String> unicos = new ArrayList<String>();
    List<List<Integer>> transacNum = new ArrayList<List<Integer>>();    
    List<Integer> unicosNum = new ArrayList<Integer>();
    public int CanTrans = 0;  
    boolean numerico = false;
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
                List <Integer> itemSetNum = new ArrayList<Integer>();                
                String [] arreglo = linea.split(" ");
                //Si el primer elemento que lee es un número hace esto...
                if(isNumeric(arreglo[0])){
                    numerico = true;
                    for(int k=0;k<arreglo.length;k++) {                   
                        int auxNum  = Integer.parseInt(arreglo[k]);
                        itemSetNum.add(auxNum);
                        if(!unicosNum.contains(auxNum)){
                            unicosNum.add(auxNum);
                        }                    
                    }                    
                //Acá trata si es String    
                }else{
                    for(int k=0;k<arreglo.length;k++) {                   
                        itemSet.add(arreglo[k]);
                        if(!unicos.contains(arreglo[k])){
                            unicos.add(arreglo[k]);
                        }
                    }
                }
                Collections.sort(itemSetNum);
                transacNum.add(itemSetNum);                
                Collections.sort(itemSet);
                transacciones.add(itemSet);
            }
            Collections.sort(unicos);            
            System.out.println("Cantidad de transacciones: "+CanTrans);
            
            if(numerico){
                transacciones.clear();
                for (int i=0;i<transacNum.size();i++){
                    List <String> itemSet = new ArrayList<String>();
                    for (int j=0;j<transacNum.get(i).size();j++){
                        String auxStr = transacNum.get(i).get(j).toString();
                        itemSet.add(auxStr);
                        if(!unicos.contains(auxStr)){
                                unicos.add(auxStr);
                            }                    
                    }
                    transacciones.add(itemSet);
                }
            }
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
    
    private static boolean isNumeric(String cadena){
	try {
		Integer.parseInt(cadena);
		return true;
	} catch (NumberFormatException nfe){
		return false;
	}
    }
    
    public boolean getNumerico(){
        return numerico;
    }
    
} 
 