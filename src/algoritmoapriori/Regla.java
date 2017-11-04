package algoritmoapriori;

import algoritmoapriori.Elemento;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Regla {
    public List <String> antecedentes = new ArrayList<String>();
    public List <String> consecuentes = new ArrayList<String>();
    float soporte;
    float confianza;
    
    public List<String> getAntecedentes(){
            return antecedentes;
    }

    public List<String> getConsecuentes(){
            return consecuentes;
    }

    public float getSoporte(){
        return soporte; 
    }

    public float getConfianza(){
        return confianza; 
    }

    public void setSoporte (Float unSoporte){
        this.soporte = unSoporte;
    }

    public void setConfianza (Float unaConfianza){
        this.confianza = unaConfianza;
    }

    public void calcularConfSup(List<Elemento> fAnt,String antecedente, int canTrans, int xyCount) {
        float auxConf = 0;
        float auxSup = 0;
        float xCount = 0;
        
        for (int n=0;n<fAnt.size();n++){
            if(antecedente.equals(fAnt.get(n).getDescripcion())){
                xCount = fAnt.get(n).getCantidad();
            }  
        }
        auxConf = xyCount/xCount;
        auxSup = xyCount/canTrans;
        this.setConfianza(auxConf);
        this.setSoporte(auxSup);
    }                
}
