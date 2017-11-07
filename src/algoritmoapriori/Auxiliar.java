package algoritmoapriori;

import java.util.ArrayList;
import java.util.List;

public class Auxiliar {
    List <String> auxFrec = new ArrayList<String>();
    List <String> auxHfrec = new ArrayList<String>();

public Auxiliar(String auxSthfrec,String auxStFrec){
    String [] auxHfrecAux = auxSthfrec.split(" ");        
    List <String> lisAuxHm = new ArrayList <String>();
    for (int j=0;j<auxHfrecAux.length;j++){
        String auxHm = auxHfrecAux[j];
        lisAuxHm.add(auxHm);
    }
    this.auxHfrec = lisAuxHm;
    String [] stDescAux = auxStFrec.split(" ");        
    List <String> lisAuxDesc = new ArrayList <String>();
    for (int j=0;j<stDescAux.length;j++){
        String auxDesc = stDescAux[j];
        lisAuxDesc.add(auxDesc);
    }
    this.auxFrec = lisAuxDesc;    
    }

public boolean contieneElemento (){
    boolean contiene = false;
    for (int k=0;k<this.auxHfrec.size();k++){
        System.out.println("El elto frec  contiene al elementeo "+this.auxHfrec.get(k)); 
    if(this.auxFrec.contains(this.auxHfrec.get(k))){
        contiene = true;
        
    }
    else{
        contiene = false;
        break;
    }            
    }
    System.out.println("Contiene "+contiene);
    if(contiene){
        System.out.println("El elto frec "+auxFrec.toString()+" contiene al elementeo "+auxHfrec.toString());        
    }    
    return contiene;
}

/*
public List<String> diferencia() {
        List <String> diferencia = new ArrayList<String>();        
        boolean bandera =false;
        for(int i=0;i<this.auxFrec.size();i++){
            for(int j=0;j<this.auxHfrec.size();j++){
                 
                if(auxFrec.get(i)==auxHfrec.get(j)){
                    bandera = true;
                }
            }
            if(!bandera){
                System.out.println("Diferencia----------"+auxFrec.get(i)); 
                diferencia.add(auxFrec.get(i));                
            }
        }
        return diferencia;
    }*/

public List<String> diferencia() {
      auxFrec.removeAll(auxHfrec);
      System.out.println("Diferencia----------"+auxFrec);
    return auxFrec;
}



}
