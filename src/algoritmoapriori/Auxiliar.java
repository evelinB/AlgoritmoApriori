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
        if(this.auxFrec.contains(this.auxHfrec.get(k))){
        contiene = true;
        }else{
        contiene = false;
        break;
        }            
    }
        
    return contiene;
}

    public List<String> diferencia() {
          auxFrec.removeAll(auxHfrec);
          return auxFrec;
    }

}
