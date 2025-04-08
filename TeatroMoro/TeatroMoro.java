
import java.util.Scanner;

public class TeatroMoro {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String tipoEntrada;
        String tarifa;
        int precio = 0;

        while (true) {
            System.out.println("Seleccione el tipo de entrada (VIP, Platea Baja, Platea Alta, Palcos): ");
            tipoEntrada = scanner.nextLine().trim().toLowerCase();

            if (tipoEntrada.equals("vip") || tipoEntrada.equals("platea baja")
                    || tipoEntrada.equals("platea alta") || tipoEntrada.equals("palcos")) {
                break;
            } else {
                System.out.println("Error: Tipo de entrada no válido. Intente nuevamente.\n");
            }
        }

        while (true) {
            System.out.println("Seleccione la tarifa (Estudiante o Público General): ");
            tarifa = scanner.nextLine().trim().toLowerCase();

            if (tarifa.equals("estudiante") || tarifa.equals("público general")) {
                break;
            } else {
                System.out.println("Error: Tarifa no válida. Intente nuevamente.\n");
            }
        }

        if (tipoEntrada.equals("vip")) {
            precio = tarifa.equals("estudiante") ? 20000 : 30000;
        } else if (tipoEntrada.equals("platea baja")) {
            precio = tarifa.equals("estudiante") ? 10000 : 15000;
        } else if (tipoEntrada.equals("platea alta")) {
            precio = tarifa.equals("estudiante") ? 9000 : 18000;
        } else if (tipoEntrada.equals("palcos")) {
            precio = tarifa.equals("estudiante") ? 6500 : 13000;
        }

        System.out.println("Total a pagar: $" + precio);
        System.out.println("Gracias por su compra, disfrute la función.");

        scanner.close();
    }
}
