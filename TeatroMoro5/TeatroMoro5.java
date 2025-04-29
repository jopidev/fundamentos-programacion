
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TeatroMoro5 {

    private static final String NOMBRE_TEATRO = "Teatro Moro";
    private static final int CAPACIDAD_SALA = 100;
    private static int entradasDisponibles = CAPACIDAD_SALA;
    private static final int PRECIO_VIP = 20000;
    private static final int PRECIO_PLATEA = 15000;
    private static final int PRECIO_BALCON = 10000;

    private static List<String> ubicaciones = new ArrayList<>();
    private static List<Integer> costosFinales = new ArrayList<>();
    private static List<Integer> descuentosAplicados = new ArrayList<>();

    private static int totalIngresos = 0;
    private static int totalEntradasVendidas = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        do {
            do {
                System.out.println("\n--- Bienvenido al " + NOMBRE_TEATRO + " ---");
                System.out.println("1. Venta de entradas");
                System.out.println("2. Visualizar resumen de ventas");
                System.out.println("3. Generar boleta");
                System.out.println("4. Calcular ingresos totales");
                System.out.println("5. Salir");
                System.out.print("Seleccione una opción: ");

                try {
                    opcion = scanner.nextInt();
                    scanner.nextLine();
                    if (opcion < 1 || opcion > 5) {
                        System.out.println("Opción inválida. Vuelva a intentarlo.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Debe ingresar un número válido. Vuelva a intentarlo.");
                    scanner.nextLine();
                    opcion = 0;
                }
            } while (opcion < 1 || opcion > 5);

            switch (opcion) {
                case 1:
                    venderEntrada(scanner);
                    break;
                case 2:
                    mostrarResumen();
                    break;
                case 3:
                    generarBoletas();
                    break;
                case 4:
                    calcularIngresosTotales();
                    break;
                case 5:
                    System.out.println("Gracias por su compra.");
                    break;
            }
        } while (opcion != 5);

        scanner.close();
    }

    private static void venderEntrada(Scanner scanner) {
        if (entradasDisponibles <= 0) {
            System.out.println("No hay entradas disponibles.");
            return;
        }

        String ubicacion = "";
        int precioBase = 0;
        boolean ubicacionValida = false;

        do {
            System.out.println("\nUbicaciones disponibles:");
            System.out.println("- VIP ($20,000)");
            System.out.println("- Platea ($15,000)");
            System.out.println("- Balcón ($10,000)");
            System.out.print("Seleccione la ubicación: ");
            ubicacion = scanner.nextLine().toLowerCase().trim();

            switch (ubicacion) {
                case "vip":
                    precioBase = PRECIO_VIP;
                    ubicacionValida = true;
                    break;
                case "platea":
                    precioBase = PRECIO_PLATEA;
                    ubicacionValida = true;
                    break;
                case "balcón":
                case "balcon":
                    precioBase = PRECIO_BALCON;
                    ubicacionValida = true;
                    break;
                default:
                    System.out.println("Ubicación no válida. Vuelva a intentarlo.");
                    break;
            }
        } while (!ubicacionValida);

        int descuento = 0;
        String respuesta = "";

        do {
            System.out.print("¿Es estudiante? (s/n): ");
            respuesta = scanner.nextLine().trim().toLowerCase();
            if (respuesta.equals("s")) {
                descuento = (int) (precioBase * 0.10);
            } else if (respuesta.equals("n")) {
                do {
                    System.out.print("¿Es persona de la tercera edad? (s/n): ");
                    respuesta = scanner.nextLine().trim().toLowerCase();
                    if (respuesta.equals("s")) {
                        descuento = (int) (precioBase * 0.15);
                    } else if (!respuesta.equals("n")) {
                        System.out.println("Respuesta inválida. Vuelva a intentarlo.");
                    }
                } while (!respuesta.equals("s") && !respuesta.equals("n"));
            } else {
                System.out.println("Respuesta inválida. Vuelva a intentarlo.");
            }
        } while (!respuesta.equals("s") && !respuesta.equals("n"));

        int precioFinal = precioBase - descuento;

        ubicaciones.add(ubicacion);
        costosFinales.add(precioFinal);
        descuentosAplicados.add(descuento);

        entradasDisponibles--;
        totalEntradasVendidas++;
        totalIngresos += precioFinal;

        System.out.println("Venta realizada con éxito.");
    }

    private static void mostrarResumen() {
        if (ubicaciones.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        System.out.println("\nResumen de Ventas:");
        for (int i = 0; i < ubicaciones.size(); i++) {
            System.out.println("Venta " + (i + 1) + ":");
            System.out.println("Ubicación: " + ubicaciones.get(i));
            System.out.println("Costo Final: $" + costosFinales.get(i));
            System.out.println("Descuento Aplicado: $" + descuentosAplicados.get(i));
            System.out.println("----------------------");
        }
    }

    private static void generarBoletas() {
        if (ubicaciones.isEmpty()) {
            System.out.println("No hay ventas para generar boletas.");
            return;
        }

        System.out.println("\n--- Boletas Generadas ---");
        for (int i = 0; i < ubicaciones.size(); i++) {
            String ubicacion = ubicaciones.get(i);
            int descuento = descuentosAplicados.get(i);
            int precioFinal = costosFinales.get(i);
            int precioBase = 0;

            switch (ubicacion) {
                case "vip":
                    precioBase = PRECIO_VIP;
                    break;
                case "platea":
                    precioBase = PRECIO_PLATEA;
                    break;
                case "balcón":
                case "balcon":
                    precioBase = PRECIO_BALCON;
                    break;
            }

            System.out.println("Boleta N°" + (i + 1));
            System.out.println("Teatro: " + NOMBRE_TEATRO);
            System.out.println("Ubicación: " + ubicacion);
            System.out.println("Precio Base: $" + precioBase);
            System.out.println("Descuento Aplicado: $" + descuento);
            System.out.println("Costo Final: $" + precioFinal);
            System.out.println("¡Gracias por su compra!");
            System.out.println("-----------------------------");
        }
    }

    private static void calcularIngresosTotales() {
        System.out.println("\nIngresos Totales: $" + totalIngresos);
        System.out.println("Total de Entradas Vendidas: " + totalEntradasVendidas);
    }
}
