package algoritmoapriori;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Regla {
    List <String> antecedentes = new ArrayList<String>();
    List <String> consecuentes = new ArrayList<String>();
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
//            if(k<2){
//                   
//                    String [] auxAnt = new String[antecedente.size()];
//                    for (int i=antecedente.size()-1;i>-1;i--){
//                        for (int j=0;j<antecedente.size();j++){
//                            auxAnt[j]=antecedente.get(antecedente.size()-j-1);
//                        }
//                    }
//                    String auxAntSt = String.join(" ", auxAnt);
//                    for (int y=0;y<fAnterior.size();y++){
//                         //System.out.println("conf------------"+fAnterior.get(y).Descripcion+xyCount+" "+xCount);
//                        if(fAnterior.get(y).Descripcion.equals(auxAntSt)){
//                                 xCount = fAnterior.get(y).Cantidad;
//                                
//                        }
//                    }
//                    
//                    auxConf = xyCount/xCount;
//                    this.setConfianza(auxConf);
//             }else{
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
                            //System.out.println("***--------descrip------ "+f.get(x).get(t).getDescripcion()+", "+auxAntSt);
                              if(f.get(x).get(t).Descripcion.equals(auxAntSt)){
                                  
                                  xCount = f.get(x).get(t).Cantidad;
                                  if (xCount ==0)
                                  System.out.println("--------------X COOUNT ES CEROOOOOOOOOOOO--------");
                              }
                        }
                    }
                    
                           System.out.println("******-------------- "+xyCount+" "+xCount);
                           if (xCount!=0.0) {
            auxConf = xyCount/xCount;
                    this.setConfianza(auxConf);
        }else{
                  this.setConfianza(xCount);         }
                    
                
            auxSup = cantidad/canTrans;
            this.setSoporte(auxSup);
    }
    
}

// --------------------------parte del metodo CALCULAR CONFIANZA Y SOPORTE
//                    String [] auxAnt = new String[antecedente.size()];
//
//                    for (int i=antecedente.size()-1;i>-1;i--){
//                        for (int j=0;j<antecedente.size();j++){
//                            auxAnt[j]=antecedente.get(antecedente.size()-j-1);
//                        }
//                    }
//                    String auxAntSt = String.join(" ", auxAnt);
//
//                        Auxiliar aux = new Auxiliar (auxAntSt,descripcion);
//                        System.out.println("F: "+descripcion+" H: "+auxAntSt);
//                        if(aux.contieneElemento()){
//
//                            xyCount = cantidad;
//                            for (int m=0;m<fAnterior.size();m++){
//                                if (fAnterior.get(m).getDescripcion().equals(auxAntSt)){
//                                    System.out.println("Son iguales, y su cantidad es :"+fAnterior.get(m).getCantidad());
//                                    xCount = fAnterior.get(m).getCantidad();
//                                }
//                            }
//                            if(xCount!=0){
//                                System.out.println("XY Count: "+xyCount);
//                                System.out.println("X Count: "+xCount);
//                                auxConf = xyCount/xCount;
//                                System.out.println("auxConf: "+auxConf);
//                                auxSup = xyCount/canTrans;
//
//                            }
//                        }  
//                    //}
//                    this.setConfianza(auxConf);
//                    this.setSoporte(auxSup);
                    
