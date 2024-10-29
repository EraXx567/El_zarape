import java.util.Scanner;

public class CalculadoraMedia {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Pedir cantidad de números
        System.out.println("¿Cuántos números quieres ingresar? (debe ser mayor que 5 y menor o igual a 15)");
        int n = scanner.nextInt();
        
        // Validar cantidad de números
        while (n <= 5 || n > 15) {
            System.out.println("Error: El número debe ser mayor que 5 y menor o igual a 15");
            System.out.println("Ingresa nuevamente la cantidad:");
            n = scanner.nextInt();
        }
        
        // Array para almacenar los números
        double[] numeros = new double[n];
        double suma = 0;
        
        // Pedir los números uno a uno
        for (int i = 0; i < n; i++) {
            System.out.println("Ingresa el número " + (i+1) + " (debe estar entre 10 y 100):");
            double numero = scanner.nextDouble();
            
            // Validar que el número esté en el rango correcto
            while (numero < 10 || numero > 100) {
                System.out.println("Error: El número debe estar entre 10 y 100");
                System.out.println("Ingresa nuevamente el número " + (i+1) + ":");
                numero = scanner.nextDouble();
            }
            
            numeros[i] = numero;
            suma += numero;
        }
        
        // Calcular la media
        double media = suma / n;
        
        // Mostrar el resultado
        System.out.println("\nLa media de los números es: " + media);
        
        scanner.close();
    }
}