package algoritmoapriori;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

    public class Elemento {
        //List<Integer> Descripcion = new ArrayList<Integer>();
        String Descripcion;
        int Cantidad = 0;
                   
    Elemento(String unDescripcion){
        Descripcion = unDescripcion;
        Cantidad = 0;
    }

    public void setDescripcion(String unDescripcion){
        Descripcion = unDescripcion;
    }

    public String getDescripcion(){
        return Descripcion;
    }

    public void setCantidad(int unaCantidad){
        Cantidad = unaCantidad;
    }

    public int getCantidad (){
        return Cantidad;
    }
    
    public void obtenerFrecuencia(List<List<String>> transacciones, String[] stSalida) {
        //1 - Obtener la primer transaccion                
        for (int i=0;i<transacciones.size();i++){
            int pos = 0;
            if (transacciones.get(i).size()>=stSalida.length){//Si el tamaño de la transaccion n es menor al del elemento, descarta la transaccion n                
                int j=0;
                while ((j<transacciones.get(i).size())&&(pos  < stSalida.length)){
                    //2 - Verificar si el elemento está en esa transaccion
                    if (transacciones.get(i).get(j).equalsIgnoreCase(stSalida[pos])){
                        pos++;
                    }
                    j++;
                }             
                //3 - Si está en la transacción, cpontar+
                if (pos==stSalida.length){//Comprueba que encuentra TODOS los eltos. en la transaccion n
                    this.setCantidad(this.getCantidad()+1);
                }
            }
        //4 - Sino, obtener la siguiente transaccion     
        }                          
    }
    
    
}
