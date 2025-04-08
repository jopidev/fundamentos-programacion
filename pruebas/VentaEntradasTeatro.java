import java.util.Scanner;

public class VentaEntradasTeatro {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Definición de variables
        String tipoEntrada, tarifa;
        int totalPagar = 0;
        
        // Entrada de datos
        System.out.println("Ingrese el tipo de entrada (VIP, Platea Baja, Platea Alta, Palcos):");
        tipoEntrada = scanner.nextLine().trim();

        System.out.println("Ingrese la tarifa (Estudiante o Público General):");
        tarifa = scanner.nextLine().trim();
        
        // Procesamiento de datos
        if (tipoEntrada.equalsIgnoreCase("VIP")) {
            if (tarifa.equalsIgnoreCase("Estudiante")) {
                totalPagar = 20000;
            } else if (tarifa.equalsIgnoreCase("Público General")) {
                totalPagar = 30000;
            }
        } else if (tipoEntrada.equalsIgnoreCase("Platea Baja")) {
            if (tarifa.equalsIgnoreCase("Estudiante")) {
                totalPagar = 10000;
            } else if (tarifa.equalsIgnoreCase("Público General")) {
                totalPagar = 15000;
            }
        } else if (tipoEntrada.equalsIgnoreCase("Platea Alta")) {
            if (tarifa.equalsIgnoreCase("Estudiante")) {
                totalPagar = 9000;
            } else if (tarifa.equalsIgnoreCase("Público General")) {
                totalPagar = 18000;
            }
        } else if (tipoEntrada.equalsIgnoreCase("Palcos")) {
            if (tarifa.equalsIgnoreCase("Estudiante")) {
                totalPagar = 6500;
            } else if (tarifa.equalsIgnoreCase("Público General")) {
                totalPagar = 13000;
            }
        } else {
            System.out.println("Tipo de entrada o tarifa inválida.");
            return;
        }

        // Salida de datos
        System.out.println("Total a pagar: $" + totalPagar);
        System.out.println("Gracias por su compra, disfrute la función.");
    }
}
