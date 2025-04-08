
import java.util.Scanner;

public class TeatroMoro2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        // Ciclo principal para mostrar el menú
        for (int i = 0; i < 1; i++) {  // Solo un ciclo para mostrar el menú
            while (continuar) {
                // Menú principal
                System.out.println("\nBienvenido al Teatro Moro");
                System.out.println("1. Comprar entrada");
                System.out.println("2. Salir");
                System.out.print("Selecciona una opción: ");

                // Validación de la opción seleccionada
                int opcion = scanner.nextInt();
                if (opcion == 1) {
                    comprarEntrada(scanner);
                } else if (opcion == 2) {
                    continuar = false;
                    System.out.println("Gracias por visitar el Teatro Moro.");
                    break;  // Salir del ciclo
                } else {
                    System.out.println("Opción no válida, intenta de nuevo.");
                }
            }
        }
    }

    // Función para comprar entradas
    public static void comprarEntrada(Scanner scanner) {
        // Paso 2: Solicitar la ubicación del asiento con validación
        System.out.println("\nSelecciona la ubicación del asiento:");
        System.out.println("1. Zona A");
        System.out.println("2. Zona B");
        System.out.println("3. Zona C");
        System.out.print("Opción: ");

        int zona = scanner.nextInt();
        if (zona < 1 || zona > 3) {
            System.out.println("Opción no válida. Por favor elige una zona entre 1 y 3.");
            return; // Salir de la función si la opción es inválida
        }

        // Definir la zona seleccionada como texto
        String zonaSeleccionada = "";
        switch (zona) {
            case 1:
                zonaSeleccionada = "Zona A";
                break;
            case 2:
                zonaSeleccionada = "Zona B";
                break;
            case 3:
                zonaSeleccionada = "Zona C";
                break;
        }

        // Paso 2: Solicitar la edad del usuario con validación
        System.out.print("Ingrese su edad: ");
        int edad = 0;
        try {
            edad = scanner.nextInt();
            if (edad < 0) {
                System.out.println("Edad no válida.");
                return;  // Salir si la edad es inválida
            }
        } catch (Exception e) {
            System.out.println("Entrada inválida. Por favor ingresa un número para la edad.");
            scanner.next();  // Limpiar el buffer del scanner
            return;
        }

        // Paso 2: Aplicar descuentos según la edad
        double descuento = 0;
        if (edad < 18) {
            descuento = 0;  // Sin descuento para menores de 18
        } else if (edad >= 18 && edad <= 25) {
            descuento = 0.10;  // 10% para estudiantes
        } else if (edad > 65) {
            descuento = 0.15;  // 15% para personas de la tercera edad
        }

        // Paso 2: Definir precio base de la entrada según la zona
        double precioBase = 0;
        switch (zona) {
            case 1:
                precioBase = 50;  // Precio para Zona A
                break;
            case 2:
                precioBase = 40;  // Precio para Zona B
                break;
            case 3:
                precioBase = 30;  // Precio para Zona C
                break;
        }

        // Calcular el precio final con descuento
        double precioFinal = precioBase * (1 - descuento);

        // Paso 3: Mostrar el resumen de la compra
        System.out.println("\nResumen de la compra:");
        System.out.println("Ubicación del asiento: " + zonaSeleccionada);
        System.out.println("Precio base: $" + precioBase);
        System.out.println("Descuento aplicado: " + (descuento * 100) + "%");
        System.out.println("Precio final a pagar: $" + precioFinal);

        // Paso 4: Preguntar si desea realizar otra compra
        System.out.print("\n¿Desea realizar otra compra? (sí/no): ");
        String respuesta = scanner.next();
        if (respuesta.equalsIgnoreCase("no")) {
            System.out.println("Gracias por tu compra.");
            return;  // Salir de la función y volver al menú
        }
    }
}
