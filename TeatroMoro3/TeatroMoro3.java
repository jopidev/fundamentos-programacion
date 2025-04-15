
import java.util.ArrayList;
import java.util.Scanner;

public class TeatroMoro3 {

    static String nombreTeatro = "Teatro Moro";
    static int capacidadSala = 100;
    static int entradasDisponibles = 100;
    static double precioBase = 10000;

    static int totalEntradasVendidas = 0;
    static double ingresosTotales = 0;
    static int totalDescuentosAplicados = 0;

    static ArrayList<Entrada> entradasVendidas = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== Bienvenido al " + nombreTeatro + " ===");
            System.out.println("1. Venta de entradas");
            System.out.println("2. Ver promociones");
            System.out.println("3. Buscar entradas");
            System.out.println("4. Eliminar entrada");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                System.out.print("Seleccione una opción: ");
                scanner.next();
            }
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    menuVenta(scanner);
                    break;
                case 2:
                    verPromociones(scanner);
                    break;
                case 3:
                    if (entradasVendidas.isEmpty()) {
                        System.out.println("No hay entradas compradas aún. Realice una compra primero.");
                    } else {
                        buscarEntradas(scanner);
                    }
                    break;
                case 4:
                    if (entradasVendidas.isEmpty()) {
                        System.out.println("No hay entradas compradas aún. No hay nada que eliminar.");
                    } else {
                        eliminarEntrada(scanner);
                    }
                    break;
                case 5:
                    System.out.println("Gracias por usar el sistema del " + nombreTeatro + ".");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
    }

    static void menuVenta(Scanner scanner) {
        int opcion;
        do {
            System.out.println("--- Menú de Venta ---");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.next();
            }

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    venderEntrada(scanner);
                    break;
                case 2:
                    break;
                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 2);
    }

    static void venderEntrada(Scanner scanner) {
        System.out.println("--- Venta de Entradas ---");
        System.out.print("Ingrese ubicación (VIP, Platea, General): ");
        String ubicacion = scanner.nextLine();

        System.out.print("¿Es estudiante? (s/n): ");
        String esEstudiante = scanner.nextLine();

        System.out.print("¿Es de la tercera edad? (s/n): ");
        String esTerceraEdad = scanner.nextLine();

        double descuento = 0;
        if (esEstudiante.equalsIgnoreCase("s")) {
            descuento = 0.10;
        } else if (esTerceraEdad.equalsIgnoreCase("s")) {
            descuento = 0.15;
        }

        double precioFinal = precioBase - (precioBase * descuento);
        entradasDisponibles--;
        totalEntradasVendidas++;
        ingresosTotales += precioFinal;
        if (descuento > 0) {
            totalDescuentosAplicados++;
        }

        Entrada nueva = new Entrada(totalEntradasVendidas, ubicacion, precioFinal,
                esEstudiante.equalsIgnoreCase("s"), esTerceraEdad.equalsIgnoreCase("s"));
        entradasVendidas.add(nueva);

        System.out.println("Entrada vendida. Precio final: $" + precioFinal);
    }

    static void verPromociones(Scanner scanner) {
        int opcion;
        do {
            System.out.println("--- Promociones ---");
            System.out.println("1. 10% de descuento para estudiantes.");
            System.out.println("2. 15% de descuento para tercera edad.");
            System.out.println("3. Descuento por compras múltiples no disponible aún.");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.next();
            }

            opcion = scanner.nextInt();
        } while (opcion != 4);
    }

    static void buscarEntradas(Scanner scanner) {
        int opcion;
        do {
            System.out.println("--- Buscar Entradas ---");
            System.out.println("1. Buscar por número");
            System.out.println("2. Buscar por ubicación");
            System.out.println("3. Buscar por tipo de cliente");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.next();
            }

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese número de entrada: ");
                    int numero = scanner.nextInt();
                    scanner.nextLine();
                    boolean encontrado = false;
                    for (Entrada e : entradasVendidas) {
                        if (e.numero == numero) {
                            System.out.println(e);
                            encontrado = true;
                        }
                    }
                    if (!encontrado) {
                        System.out.println("No se encontró una entrada con ese número.");
                    }
                    break;
                case 2:
                    System.out.print("Ingrese ubicación (VIP, Platea, General): ");
                    String ubicacion = scanner.nextLine();
                    for (Entrada e : entradasVendidas) {
                        if (e.ubicacion.equalsIgnoreCase(ubicacion)) {
                            System.out.println(e);
                        }
                    }
                    break;
                case 3:
                    System.out.print("Tipo (estudiante/tercera edad): ");
                    String tipo = scanner.nextLine();
                    for (Entrada e : entradasVendidas) {
                        if ((tipo.equalsIgnoreCase("estudiante") && e.estudiante)
                                || (tipo.equalsIgnoreCase("tercera edad") && e.terceraEdad)) {
                            System.out.println(e);
                        }
                    }
                    break;
                case 4:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 4);
    }

    static void eliminarEntrada(Scanner scanner) {
        int opcion;
        do {
            System.out.println("--- Eliminar Entrada ---");
            System.out.println("1. Eliminar por número");
            System.out.println("2. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.next();
            }

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese número de entrada a eliminar: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Entrada inválida. Por favor, ingrese un número.");
                        scanner.next();
                    }
                    int numero = scanner.nextInt();
                    Entrada aEliminar = null;
                    for (Entrada e : entradasVendidas) {
                        if (e.numero == numero) {
                            aEliminar = e;
                        }
                    }

                    if (aEliminar != null) {
                        entradasVendidas.remove(aEliminar);
                        System.out.println("Entrada eliminada correctamente.");
                    } else {
                        System.out.println("Entrada no encontrada.");
                    }
                    break;
                case 2:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 2);
    }
}

class Entrada {

    int numero;
    String ubicacion;
    double precioFinal;
    boolean estudiante;
    boolean terceraEdad;

    public Entrada(int numero, String ubicacion, double precioFinal, boolean estudiante, boolean terceraEdad) {
        this.numero = numero;
        this.ubicacion = ubicacion;
        this.precioFinal = precioFinal;
        this.estudiante = estudiante;
        this.terceraEdad = terceraEdad;
    }

    public String toString() {
        return "Entrada #" + numero + " | Ubicación: " + ubicacion + " | Precio: $" + precioFinal
                + " | Estudiante: " + estudiante + " | Tercera Edad: " + terceraEdad;
    }
}
