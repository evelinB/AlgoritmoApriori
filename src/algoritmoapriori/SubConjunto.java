/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmoapriori;


import java.util.Arrays;

public class SubConjunto {
    
    public  SubConjunto(String [] arr,int k){
        int tamaño = arr.length-1;
        combinations2(arr, k, 0, new String[tamaño]);
    }

    static void combinations2(String[] arr, int len, int startPosition, String[] result){
        if (len == 0){
            return;
        }  
        
        for (int i = startPosition; i <= arr.length-len; i++){
            result[result.length - len] = arr[i];
            combinations2(arr, len-1, i+1, result);
        }
    }       
}