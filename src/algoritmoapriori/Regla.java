package algoritmoapriori;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Regla implements Comparable<Regla>{
    public List <String> antecedentes = new ArrayList<String>();
    public List <String> consecuentes = new ArrayList<String>();
    float soporte;
    float confianza;    
    float  lift;   
    
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
    
    public float getLlift(){
        return lift; 
    }
    
    public void setSoporte (Float unSoporte){
        this.soporte = unSoporte;
    }

    public void setConfianza (Float unaConfianza){
        this.confianza = unaConfianza;
    }
    
    public void setLift (Float unLift){
        this.lift = unLift;
    }
  // metodo que calcula soporte, confianza y lift de una regla
    public void calcularConfSup(String descripcion,int cantidad, List<Elemento> fAnterior, List<String> antecedente,List<String> consecuente, int canTrans, List<List<Elemento>> f,int k) {
        float auxConf = 0;
        float auxSup = 0;
        float xCount = 0;
        float yCount = 0;
        float xyCount = 0;        
        xyCount = cantidad;
        
        //Invertir antecedente
        String [] auxAnt = new String[antecedente.size()];
        for (int i=antecedente.size()-1;i>-1;i--){
                for (int j=0;j<antecedente.size();j++){
                    auxAnt[j]=antecedente.get(antecedente.size()-j-1);
                }
        }
        Arrays.sort(auxAnt);
        
        //Obtener la frecuencia del elemento antecedente
        String auxAntSt = (String.join(" ", auxAnt));                                    
        for(int x=0; x<f.size();x++){
            for (int t=0;t<f.get(x).size();t++){
                if(f.get(x).get(t).Descripcion.equals(auxAntSt)){
                    xCount = f.get(x).get(t).Cantidad;
                }
            }
        }
        //----------------------------------------------------------------------        
        //Invertir consecuente
        String [] auxCons = new String[consecuente.size()];
        for (int i=consecuente.size()-1;i>-1;i--){
                for (int j=0;j<consecuente.size();j++){
                    auxCons[j]=consecuente.get(consecuente.size()-j-1);
                }
        }
        Arrays.sort(auxCons);                
        
        //Obtener la frecuencia del elemento consecuente
        String auxConSt = (String.join(" ", auxCons));                                    
        for(int x=0; x<f.size();x++){
            for (int t=0;t<f.get(x).size();t++){
                if(f.get(x).get(t).Descripcion.equals(auxConSt)){
                    yCount = f.get(x).get(t).Cantidad;
                }
            }
        }
        
        if (xCount!=0.0) {
            auxConf = xyCount/xCount;
            this.setConfianza(auxConf);
        }else{
                this.setConfianza(xCount);
        }                    
        auxSup = (float)(xyCount/canTrans);        
        float auxLift = auxSup/(float)((xCount/canTrans)*(xCount/canTrans));        
        this.setLift(auxLift);
        this.setSoporte(auxSup);
    }

     @Override
        public int compareTo(Regla r) {
            if (lift > r.lift) {
                return -1;
            }
            if (lift < r.lift) {
                return 1;
            }
            return 0;
        }
    
}