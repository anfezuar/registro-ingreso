/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.util.Scanner;

/**
 *
 * @author Rec Tecnologicos
 */
public class Vectores {
    
    public static Scanner entrada=new Scanner(System.in);
    
    public static void main(String[] args){
        int tamanoVector;
        do{
        System.out.println("Por favor ingrese la cantidad (solo numeros) de numeros que tendra su vector");
                   
        tamanoVector = entrada.nextInt();
        
        } while (tamanoVector <= 0 );
        
        int[] vector = new int[tamanoVector];
        for (int i = 0; i < tamanoVector; i++) {
            System.out.println("Por favor ingrese el numero # "+i+" del vector");
            vector[i] = entrada.nextInt();
        }
        int sumPares = 0;
        int sumImpares = 0;
        int cantPares = 0;
        int cantImpares = 0;
        int promPares = 0;
        int promImpares = 0;
        int mayor = 0;
        int menor = 0;
        
        System.out.println("Impresión del vector");
        for (int j = 0; j < tamanoVector; j++) {
            System.out.println(vector[j]);
            
            if (vector[j]%2 == 0) {
                sumPares += vector[j];
                cantPares++;
            }
            else{
                sumImpares += vector[j];
                cantImpares++;
            }
            if (j==0) {
                mayor = vector[j];
                menor = vector[j];
            }
            else{
                if (vector[j]>mayor) {
                    mayor = vector[j];
                }
                if (vector[j]<menor) {
                    menor = vector[j];
                }
            }
        }
        
        promPares = sumPares/cantPares;
        promImpares = sumImpares/cantImpares;
        
        System.out.println("1. Suma de Pares: "+sumPares);
        System.out.println("2. Suma de Impares: "+sumImpares);
        System.out.println("3. Promedio de Pares: "+promPares);
        System.out.println("4. Promedio de Impares: "+promImpares);
        System.out.println("5. Número Mayor: "+mayor);
        System.out.println("6. Número Menor: "+menor);

    }
}
