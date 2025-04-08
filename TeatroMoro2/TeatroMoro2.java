
import java.util.Scanner;

public class TeatroMoro2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        System.out.println("Bienvenido al sistema de venta de entradas del Teatro Moro");

        while (continuar) {
            System.out.println("\nMenú principal:");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opción: ");
            String opcionInput = scanner.nextLine();

            if (opcionInput.equals("1")) {
                comprarEntrada(scanner);
            } else if (opcionInput.equals("2")) {
                System.out.println("Gracias por usar el sistema del Teatro Moro.");
                continuar = false;
            } else {
                System.out.println("Opción inválida. Intente nuevamente.");
            }
        }

        scanner.close();
    }

    public static void comprarEntrada(Scanner scanner) {
        String zona = "";
        boolean zonaValida = false;

        while (!zonaValida) {
            System.out.println("\nSeleccione zona:");
            System.out.println("1. Zona A - Preferencial");
            System.out.println("2. Zona B - General");
            System.out.println("3. Zona C - Económica");
            System.out.println("4. Volver al menú principal");
            System.out.println("5. Salir");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    zona = "A";
                    zonaValida = true;
                    break;
                case "2":
                    zona = "B";
                    zonaValida = true;
                    break;
                case "3":
                    zona = "C";
                    zonaValida = true;
                    break;
                case "4":
                    return;
                case "5":
                    System.out.println("Gracias por su compra.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }

        double precioBase = 0;
        switch (zona) {
            case "A":
                precioBase = 15000;
                break;
            case "B":
                precioBase = 10000;
                break;
            case "C":
                precioBase = 7000;
                break;
        }

        int edad = 0;
        boolean edadValida = false;

        while (!edadValida) {
            System.out.print("\nIngrese su edad (o escriba 0 para volver al menú, -1 para salir): ");
            String edadInput = scanner.nextLine();

            try {
                edad = Integer.parseInt(edadInput);
                if (edad == 0) {
                    return;
                } else if (edad == -1) {
                    System.out.println("Gracias por su compra.");
                    System.exit(0);
                } else if (edad > 0) {
                    edadValida = true;
                } else {
                    System.out.println("La edad debe ser un número positivo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Debe ingresar un número.");
            }
        }

        double descuento = 0;
        if (edad >= 60) {
            descuento = 0.15;
        } else if (edad >= 18 && edad <= 25) {
            descuento = 0.10;
        }

        double precioFinal = precioBase * (1 - descuento);

        System.out.println("\nResumen de la compra:");
        System.out.println("Zona seleccionada: " + zona);
        System.out.println("Precio base: $" + (int) precioBase);
        System.out.println("Descuento: " + (int) (descuento * 100) + "%");
        System.out.println("Total a pagar: $" + (int) precioFinal);

        boolean respuestaValida = false;
        while (!respuestaValida) {
            System.out.print("\n¿Desea realizar otra compra? (1 = Sí, 2 = No, 3 = Salir del sistema): ");
            String respuesta = scanner.nextLine();

            if (respuesta.equals("1")) {
                respuestaValida = true;
            } else if (respuesta.equals("2")) {
                return;
            } else if (respuesta.equals("3")) {
                System.out.println("Gracias por su compra.");
                System.exit(0);
            } else {
                System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }
}
