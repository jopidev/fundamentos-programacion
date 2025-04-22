
import java.util.*;

public class TeatroMoro4 {

    static Scanner scanner = new Scanner(System.in);

    static final String nombreTeatro = "Teatro Moro";
    static final int capacidadSala = 30;
    static final int filas = 5;
    static final int columnas = 6;
    static final int precioEntrada = 5000;

    static String[][] asientos = new String[filas][columnas];
    static List<String> boletas = new ArrayList<>();
    static List<String> reservas = new ArrayList<>();

    static int totalIngresos = 0;
    static int cantidadEntradasVendidas = 0;
    static int cantidadReservas = 0;

    static String generarCodigo(int fila, int columna) {
        return "F" + (fila + 1) + "C" + (columna + 1);
    }

    static void inicializarAsientos() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                asientos[i][j] = "L";
            }
        }
    }

    static void mostrarAsientos() {
        System.out.println("Estado de los asientos (L=Libre, R=Reservado, V=Vendido):");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(asientos[i][j] + " ");
            }
            System.out.println();
        }
    }

    static int[] seleccionarAsiento() {
        while (true) {
            try {
                System.out.print("Ingrese fila (1-" + filas + ", 0 para volver): ");
                int fila = Integer.parseInt(scanner.nextLine());
                if (fila == 0) {
                    return null;
                }
                System.out.print("Ingrese columna (1-" + columnas + ", 0 para volver): ");
                int columna = Integer.parseInt(scanner.nextLine());
                if (columna == 0) {
                    return null;
                }
                fila -= 1;
                columna -= 1;
                if (fila < 0 || fila >= filas || columna < 0 || columna >= columnas) {
                    System.out.println("Ubicación fuera de rango. Intente nuevamente.");
                    continue;
                }
                return new int[]{fila, columna};
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Intente nuevamente.");
            }
        }
    }

    static void reservarAsiento() {
        while (true) {
            mostrarAsientos();
            int[] asiento = seleccionarAsiento();
            if (asiento == null) {
                return;
            }
            int fila = asiento[0];
            int columna = asiento[1];
            if (asientos[fila][columna].equals("L")) {
                asientos[fila][columna] = "R";
                String codigo = generarCodigo(fila, columna);
                reservas.add(codigo);
                cantidadReservas++;
                System.out.println("Asiento " + codigo + " reservado exitosamente.");
                return;
            } else if (asientos[fila][columna].equals("R")) {
                System.out.println("Ese asiento ya está reservado.");
            } else {
                System.out.println("Ese asiento ya está vendido.");
            }
        }
    }

    static void comprarAsiento() {
        while (true) {
            mostrarAsientos();
            int[] asiento = seleccionarAsiento();
            if (asiento == null) {
                return;
            }
            int fila = asiento[0];
            int columna = asiento[1];
            if (asientos[fila][columna].equals("L")) {
                asientos[fila][columna] = "V";
                String codigo = generarCodigo(fila, columna);
                cantidadEntradasVendidas++;
                totalIngresos += precioEntrada;
                boletas.add("Asiento: " + codigo + ", Precio: $" + precioEntrada);
                System.out.println("Compra realizada exitosamente para el asiento " + codigo);
                String opcion = "";
                while (true) {
                    System.out.println("\n¿Qué desea hacer ahora?");
                    System.out.println("1. Realizar otra compra");
                    System.out.println("2. Realizar una reserva");
                    System.out.println("3. Generar boleta");
                    System.out.println("4. Volver al menú principal");
                    System.out.print("Seleccione una opción: ");
                    opcion = scanner.nextLine();
                    if (opcion.matches("[1-4]")) {
                        break;
                    } else {
                        System.out.println("Opción inválida. Intente nuevamente.");
                    }
                }
                switch (opcion) {
                    case "1":
                        comprarAsiento();
                        break;
                    case "2":
                        reservarAsiento();
                        break;
                    case "3":
                        generarBoleta();
                        break;
                    case "4":
                        return;
                }
            } else if (asientos[fila][columna].equals("R")) {
                System.out.println("Ese asiento está reservado. No se puede comprar directamente.");
            } else {
                System.out.println("Ese asiento ya ha sido vendido.");
            }
        }
    }

    static void modificarVenta() {
        while (true) {
            mostrarAsientos();
            System.out.println("Seleccione el asiento que desea cambiar (0 para volver):");
            int[] asientoViejo = seleccionarAsiento();
            if (asientoViejo == null) {
                return;
            }
            int filaViejo = asientoViejo[0];
            int columnaViejo = asientoViejo[1];
            if (!asientos[filaViejo][columnaViejo].equals("V")) {
                System.out.println("Ese asiento no ha sido vendido.");
                continue;
            }
            asientos[filaViejo][columnaViejo] = "L";
            System.out.println("Seleccione el nuevo asiento (0 para volver):");
            int[] asientoNuevo = seleccionarAsiento();
            if (asientoNuevo == null) {
                asientos[filaViejo][columnaViejo] = "V";
                return;
            }
            int filaNuevo = asientoNuevo[0];
            int columnaNuevo = asientoNuevo[1];
            if (asientos[filaNuevo][columnaNuevo].equals("L")) {
                asientos[filaNuevo][columnaNuevo] = "V";
                String codigoNuevo = generarCodigo(filaNuevo, columnaNuevo);
                boletas.add("(Modificada) Asiento: " + codigoNuevo + ", Precio: $" + precioEntrada);
                System.out.println("Venta modificada exitosamente.");
                return;
            } else {
                System.out.println("Nuevo asiento no disponible. Modificación cancelada.");
                asientos[filaViejo][columnaViejo] = "V";
            }
        }
    }

    static void modificarReservaACompra() {
        while (true) {
            mostrarAsientos();
            System.out.println("Seleccione el asiento reservado que desea convertir a comprado (0 para volver):");
            int[] asientoReserva = seleccionarAsiento();
            if (asientoReserva == null) {
                return;
            }
            int fila = asientoReserva[0];
            int columna = asientoReserva[1];
            if (asientos[fila][columna].equals("R")) {
                asientos[fila][columna] = "V";
                String codigo = generarCodigo(fila, columna);
                cantidadEntradasVendidas++;
                cantidadReservas--;
                totalIngresos += precioEntrada;
                boletas.add("Asiento: " + codigo + " (Reservado convertido a Comprado), Precio: $" + precioEntrada);
                System.out.println("Reserva convertida a compra exitosamente para el asiento " + codigo);
                return;
            } else if (asientos[fila][columna].equals("V")) {
                System.out.println("Ese asiento ya ha sido vendido.");
            } else {
                System.out.println("Ese asiento no está reservado.");
            }
        }
    }

    static void generarBoleta() {
        if (boletas.isEmpty()) {
            System.out.println("No hay entradas compradas.");
            return;
        }
        int cantidadCompras = boletas.size();
        int totalPrecio = cantidadCompras * precioEntrada;
        System.out.println("Cantidad de entradas compradas: " + cantidadCompras);
        System.out.println("Total: $" + totalPrecio);
        System.out.println("Detalle de las compras:");
        for (String boleta : boletas) {
            System.out.println(boleta);
        }
        System.out.println("\nGracias por su compra. ¡Que disfrute la función!");
        boletas.clear();
        String opcion = "";
        while (true) {
            System.out.println("\n¿Qué desea hacer ahora?");
            System.out.println("1. Realizar otra compra");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextLine();
            if (opcion.matches("[1-2]")) {
                break;
            } else {
                System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
        switch (opcion) {
            case "1":
                comprarAsiento();
                break;
            case "2":
                System.out.println("Saliendo...");
                System.exit(0);
                break;
        }
    }

    public static void main(String[] args) {
        inicializarAsientos();
        while (true) {
            String opcion = "";
            while (true) {
                System.out.println("\nBienvenido al sistema de ventas del " + nombreTeatro);
                System.out.println("1. Reservar entradas");
                System.out.println("2. Modificar Reserva");
                System.out.println("3. Comprar entradas");
                System.out.println("4. Modificar una venta");
                System.out.println("5. Generar boleta");
                System.out.println("6. Salir");
                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextLine();
                if (opcion.matches("[1-6]")) {
                    break;
                } else {
                    System.out.println("Opción inválida. Intente nuevamente.");
                }
            }
            switch (opcion) {
                case "1":
                    reservarAsiento();
                    break;
                case "2":
                    modificarReservaACompra();
                    break;
                case "3":
                    comprarAsiento();
                    break;
                case "4":
                    modificarVenta();
                    break;
                case "5":
                    generarBoleta();
                    break;
                case "6":
                    System.out.println("Saliendo...");
                    System.exit(0);
                    break;
            }
        }
    }
}
