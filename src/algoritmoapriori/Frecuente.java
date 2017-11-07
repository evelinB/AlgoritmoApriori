package algoritmoapriori;

import java.util.ArrayList;
import java.util.List;

public class Frecuente {
    
    List<List<Elemento>> frecuentes = new ArrayList<List<Elemento>>();
    
    public List<List<Elemento>> getFrecuentes(){
        return frecuentes;
    }
    
   //-----------Devuelve F1, primer lista de elementos frecuentes que cumplen con minsup--------------//    
    public List<Elemento> genFrecuentesInicial(List<Elemento> listaElementos, float minSup, int cantTrans){
        //1 - Obtener el primer elemento de la lista de candidatos
        List<Elemento> elementosFrecuentesInicial = new ArrayList<Elemento>();
        for (int i=0;i<listaElementos.size();i++){
            Elemento unElemento = listaElementos.get(i); 
            //System.out.println("Frec Inicial"+unElemento.getDescripcion());
            //2 - De ese elemento, obtener el soporte                 
            double soporte = (double)unElemento.getCantidad()/(double)cantTrans;
            System.out.println(soporte);           
            System.out.println(minSup);
            //3 - Comparar el soporte con minSup
            if (soporte>=minSup) {
                //4 - Si es mayor o igual lo agrega a frecuentes1                
                elementosFrecuentesInicial.add(unElemento);        
                //System.out.println("ver"+elementosFrecuentesInicial);
            }
            //5 - Sino, obtener siguiente elemento            
        }
        frecuentes.add(elementosFrecuentesInicial);             
        return elementosFrecuentesInicial;
    }
    
    public List<Elemento> genFrencuentesK(List<Elemento> listaElementos, float minSup, int cantTrans){
        //1 - Obtener el primer elemento de la lista de candidatos        
        List<Elemento> elementosFrecuentesInicial = new ArrayList<Elemento>();
        for (int i=0;i<listaElementos.size();i++){
            Elemento unElemento = listaElementos.get(i);    
            //2 - De ese elemento, obtener el soporte           
            double soporte = (double)unElemento.getCantidad()/(double)cantTrans;
            //3 - Comparar el soporte con minSup
            if (soporte>=minSup) {
                //4 - Si es mayor o igual lo agrega a frecuentes1                
                elementosFrecuentesInicial.add(unElemento);                 
                //System.out.println("cumplen minsup:"+unElemento.getDescripcion());
            }
            //5 - Sino, obtener siguiente elemento            
        }
        frecuentes.add(elementosFrecuentesInicial);
        return elementosFrecuentesInicial;
    }
}