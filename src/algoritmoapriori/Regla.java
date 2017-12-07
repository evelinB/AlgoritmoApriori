package algoritmoapriori;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    public void calcularConfSup(String descripcion,int cantidad, List<Elemento> fAnterior, List<String> antecedente, int canTrans, List<List<Elemento>> f,int k) {
        float auxConf = 0;
        float auxSup = 0;
        float xCount = 0;
        float xyCount = 0;
        
        xyCount = cantidad;
        String [] auxAnt = new String[antecedente.size()];
                    for (int i=antecedente.size()-1;i>-1;i--){
                            for (int j=0;j<antecedente.size();j++){
                                auxAnt[j]=antecedente.get(antecedente.size()-j-1);
                            }
                    }
                    Arrays.sort(auxAnt);
                    String auxAntSt = (String.join(" ", auxAnt));
                                     
                    for(int x=0; x<f.size();x++){
                        for (int t=0;t<f.get(x).size();t++){
                            if(f.get(x).get(t).Descripcion.equals(auxAntSt)){
                                xCount = f.get(x).get(t).Cantidad;
                            }
                        }
                    }
                    
                    if (xCount!=0.0) {
                        auxConf = xyCount/xCount;
                        this.setConfianza(auxConf);
                    }else{
                            this.setConfianza(xCount);
                    }
                    System.out.println("Cantidad: "+cantidad);
                    System.out.println("CanTans: "+canTrans);
                    auxSup = xyCount/canTrans;
                    System.out.println("resul: "+auxSup);
                    this.setSoporte(auxSup);
    }
    
}
