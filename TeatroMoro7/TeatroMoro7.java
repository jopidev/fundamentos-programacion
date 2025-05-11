
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TeatroMoro7 {

    static Scanner scanner = new Scanner(System.in);
    static List<Venta> ventas = Collections.synchronizedList(new ArrayList<>());
    static Map<String, List<Asiento>> asientos = Collections.synchronizedMap(new HashMap<>());
    static String archivoDatos = "teatro_datos.dat";
    static int ultimoIdVenta = 0;

    enum TriState {
        TRUE, FALSE, VOLVER;
    }

    public static void main(String[] args) {
        inicializarSistema();
        int opcion;

        do {
            opcion = menuPrincipal();
            switch (opcion) {
                case 1:
                    procesarCompra();
                    break;
                case 2:
                    verDetalleVentas();
                    break;
                case 3:
                    modificarVenta();
                    break;
                case 4:
                    cancelarVenta();
                    break;
                case 5:
                    estadisticasVentas();
                    break;
                case 6:
                    guardarDatos();
                    System.out.println("Gracias por utilizar nuestro sistema. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 6);

        scanner.close();
    }

    public static void inicializarSistema() {
        try {
            cargarDatos();
            System.out.println("Datos cargados correctamente.");
        } catch (Exception e) {
            System.out.println("Iniciando sistema con datos por defecto.");
            inicializarAsientos();
        }
    }

    public static void inicializarAsientos() {
        String[] categorias = {"VIP", "PALCO", "PLATEA BAJA", "PLATEA ALTA", "GALERIA"};
        int[] cantidades = {10, 15, 20, 25, 30};
        double[] precios = {30000, 25000, 20000, 15000, 10000};

        for (int i = 0; i < categorias.length; i++) {
            List<Asiento> asientosCategoria = Collections.synchronizedList(new ArrayList<>());
            for (int j = 1; j <= cantidades[i]; j++) {
                asientosCategoria.add(new Asiento(j, categorias[i], precios[i]));
            }
            asientos.put(categorias[i], asientosCategoria);
        }
    }

    public static int menuPrincipal() {
        System.out.println("\n========== TEATRO MORO - SISTEMA DE VENTAS ==========");
        System.out.println("1. Comprar entradas");
        System.out.println("2. Ver detalle de ventas");
        System.out.println("3. Modificar una venta");
        System.out.println("4. Cancelar una venta");
        System.out.println("5. Estadísticas de ventas");
        System.out.println("6. Salir");
        System.out.print("Ingrese una opción: ");

        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static void procesarCompra() {
        Cliente cliente = recopilarDatosCliente();
        if (cliente == null) {
            return;
        }

        mostrarAsientosDisponibles();

        String tipoAsiento = pedirTipoAsiento();
        if (tipoAsiento.equals("volver") || tipoAsiento.equals("salir")) {
            return;
        }

        Asiento asientoSeleccionado = seleccionarAsiento(tipoAsiento);
        if (asientoSeleccionado == null) {
            return;
        }

        double descuento = calcularDescuento(asientoSeleccionado.getPrecio(), cliente);
        double total = asientoSeleccionado.getPrecio() - descuento;

        System.out.println("\n==== CONFIRMAR COMPRA ====");
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Asiento: " + tipoAsiento + " #" + asientoSeleccionado.getNumero());
        System.out.println("Precio: $" + (int) asientoSeleccionado.getPrecio());
        System.out.println("Descuento: " + (int) ((descuento / asientoSeleccionado.getPrecio()) * 100) + "% ($" + (int) descuento + ")");
        System.out.println("Total: $" + (int) total);
        System.out.print("¿Confirmar compra? (s/n): ");

        String confirmar = scanner.nextLine().trim().toLowerCase();
        if (confirmar.equals("s")) {
            ultimoIdVenta++;
            Venta venta = new Venta(
                    ultimoIdVenta,
                    cliente,
                    asientoSeleccionado,
                    descuento,
                    total,
                    LocalDateTime.now()
            );

            ventas.add(venta);
            asientoSeleccionado.setDisponible(false);

            imprimirBoleta(venta);
            guardarDatos();
        } else {
            System.out.println("Compra cancelada.");
        }
    }

    public static Cliente recopilarDatosCliente() {
        System.out.println("\n==== DATOS DEL CLIENTE ====");

        String nombre = pedirNombre();
        if (nombre.equals("salir")) {
            return null;
        }

        int edad = pedirEdad();
        if (edad == -1) {
            return null;
        }

        String genero = pedirGenero();
        if (genero.equals("salir")) {
            return null;
        }

        TriState esEstudiante = pedirEstudiante();
        if (esEstudiante == TriState.VOLVER) {
            return null;
        }

        return new Cliente(nombre, edad, genero, esEstudiante == TriState.TRUE);
    }

    public static String pedirNombre() {
        while (true) {
            System.out.print("Ingrese nombre del cliente (escriba 'salir' para cancelar): ");
            String nombre = scanner.nextLine().trim();

            if (nombre.equalsIgnoreCase("salir")) {
                return "salir";
            } else if (!nombre.isEmpty()) {
                return nombre;
            }

            System.out.println("Error: El nombre no puede estar vacío.");
        }
    }

    public static int pedirEdad() {
        while (true) {
            System.out.print("Ingrese edad (escriba 'salir' para cancelar): ");
            String entrada = scanner.nextLine().trim();

            if (entrada.equalsIgnoreCase("salir")) {
                return -1;
            }

            try {
                int edad = Integer.parseInt(entrada);
                if (edad > 0 && edad < 120) {
                    return edad;
                } else {
                    System.out.println("Error: La edad debe estar entre 1 y 120 años.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido.");
            }
        }
    }

    public static String pedirGenero() {
        while (true) {
            System.out.print("Ingrese género (M/F) (escriba 'salir' para cancelar): ");
            String genero = scanner.nextLine().trim().toUpperCase();

            if (genero.equalsIgnoreCase("salir")) {
                return "salir";
            } else if (genero.equals("M") || genero.equals("F")) {
                return genero;
            }

            System.out.println("Error: El género debe ser 'M' o 'F'.");
        }
    }

    public static TriState pedirEstudiante() {
        while (true) {
            System.out.print("¿Es estudiante? (s/n/volver) (escriba 'salir' para cancelar): ");
            String respuesta = scanner.nextLine().trim().toLowerCase();

            if (respuesta.equals("salir")) {
                return null;
            } else if (respuesta.equals("s")) {
                return TriState.TRUE;
            } else if (respuesta.equals("n")) {
                return TriState.FALSE;
            } else if (respuesta.equals("volver")) {
                return TriState.VOLVER;
            }

            System.out.println("Error: Debe responder 's', 'n' o 'volver'.");
        }
    }

    public static void mostrarAsientosDisponibles() {
        System.out.println("\n========== ASIENTOS DISPONIBLES ==========");
        for (String categoria : asientos.keySet()) {
            int disponibles = contarAsientosDisponibles(categoria);
            System.out.println(categoria + ": " + disponibles + " disponibles, Precio: $"
                    + (int) getPrecioCategoria(categoria));
        }
        System.out.println("=========================================");
    }

    public static int contarAsientosDisponibles(String categoria) {
        int count = 0;
        for (Asiento asiento : asientos.get(categoria)) {
            if (asiento.isDisponible()) {
                count++;
            }
        }
        return count;
    }

    public static double getPrecioCategoria(String categoria) {
        if (!asientos.get(categoria).isEmpty()) {
            return asientos.get(categoria).get(0).getPrecio();
        }
        return 0;
    }

    public static String pedirTipoAsiento() {
        while (true) {
            System.out.print("Seleccione tipo de asiento (VIP, PALCO, PLATEA BAJA, PLATEA ALTA, GALERIA) "
                    + "(escriba 'salir' para cancelar): ");
            String tipoAsiento = scanner.nextLine().trim().toUpperCase();

            if (tipoAsiento.equalsIgnoreCase("salir")) {
                return "salir";
            } else if (asientos.containsKey(tipoAsiento)) {
                if (contarAsientosDisponibles(tipoAsiento) > 0) {
                    return tipoAsiento;
                } else {
                    System.out.println("Error: No hay asientos disponibles en esa categoría.");
                }
            } else {
                System.out.println("Error: Categoría no válida.");
            }
        }
    }

    public static Asiento seleccionarAsiento(String categoria) {
        while (true) {
            System.out.println("\n==== ASIENTOS " + categoria + " DISPONIBLES ====");
            List<Asiento> asientosCategoria = asientos.get(categoria);
            List<Asiento> disponibles = Collections.synchronizedList(new ArrayList<>());

            for (Asiento asiento : asientosCategoria) {
                if (asiento.isDisponible()) {
                    disponibles.add(asiento);
                    System.out.println("Asiento #" + asiento.getNumero());
                }
            }

            System.out.print("Seleccione número de asiento (escriba 'salir' para cancelar): ");
            String entrada = scanner.nextLine().trim();

            if (entrada.equalsIgnoreCase("salir")) {
                return null;
            }

            try {
                int numeroAsiento = Integer.parseInt(entrada);
                for (Asiento asiento : disponibles) {
                    if (asiento.getNumero() == numeroAsiento) {
                        return asiento;
                    }
                }
                System.out.println("Error: Número de asiento no disponible.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido.");
            }
        }
    }

    public static double calcularDescuento(double precio, Cliente cliente) {
        double descuento = 0;

        if (cliente.getEdad() >= 60) {
            descuento = 0.25;
        } else if (cliente.getGenero().equals("F")) {
            descuento = 0.20;
        } else if (cliente.isEstudiante() && cliente.getEdad() >= 13 && cliente.getEdad() <= 25) {
            descuento = 0.15;
        } else if (cliente.getEdad() < 13) {
            descuento = 0.10;
        }

        return precio * descuento;
    }

    public static void imprimirBoleta(Venta venta) {
        Cliente cliente = venta.getCliente();
        Asiento asiento = venta.getAsiento();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        double porcentajeDescuento = (venta.getDescuento() / asiento.getPrecio()) * 100;

        System.out.println("\n================ BOLETA DE VENTA ================");
        System.out.println("ID Venta: " + venta.getId());
        System.out.println("Fecha: " + venta.getFechaHora().format(formatter));
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Edad: " + cliente.getEdad());
        System.out.println("Género: " + cliente.getGenero());
        System.out.println("Estudiante: " + (cliente.isEstudiante() ? "Sí" : "No"));
        System.out.println("Categoría: " + asiento.getCategoria());
        System.out.println("Número de asiento: " + asiento.getNumero());
        System.out.println("Precio base: $" + (int) asiento.getPrecio());
        System.out.println("Descuento aplicado: " + (int) porcentajeDescuento + "% ($" + (int) venta.getDescuento() + ")");
        System.out.println("Total pagado: $" + (int) venta.getTotal());
        System.out.println("=================================================");
    }

    public static void verDetalleVentas() {
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        System.out.println("\n================ REGISTRO DE VENTAS ================");
        System.out.printf("%-5s %-15s %-12s %-10s %-8s %-10s %-8s\n",
                "ID", "Cliente", "Categoría", "Asiento", "Precio", "Descuento", "Total");
        System.out.println("------------------------------------------------------------");

        for (Venta venta : ventas) {
            Cliente cliente = venta.getCliente();
            Asiento asiento = venta.getAsiento();
            System.out.printf("%-5d %-15s %-12s %-10d $%-8d $%-10d $%-8d\n",
                    venta.getId(),
                    cliente.getNombre(),
                    asiento.getCategoria(),
                    asiento.getNumero(),
                    (int) asiento.getPrecio(),
                    (int) venta.getDescuento(),
                    (int) venta.getTotal());
        }

        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    public static void modificarVenta() {
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas para modificar.");
            return;
        }

        System.out.println("\n================ MODIFICAR VENTA ================");
        System.out.print("Ingrese ID de venta a modificar (escriba 'salir' para cancelar): ");
        String entrada = scanner.nextLine().trim();

        if (entrada.equalsIgnoreCase("salir")) {
            return;
        }

        try {
            int idVenta = Integer.parseInt(entrada);
            Venta venta = buscarVentaPorId(idVenta);

            if (venta == null) {
                System.out.println("Error: No se encontró una venta con ese ID.");
                return;
            }

            Cliente cliente = venta.getCliente();
            Asiento asientoAnterior = venta.getAsiento();

            System.out.println("\nVenta actual:");
            imprimirBoleta(venta);

            System.out.println("\n¿Qué desea modificar?");
            System.out.println("1. Datos del cliente");
            System.out.println("2. Asiento");
            System.out.println("3. Cancelar");
            System.out.print("Ingrese opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine().trim());

                switch (opcion) {
                    case 1:
                        Cliente nuevoCliente = recopilarDatosCliente();
                        if (nuevoCliente == null) {
                            return;
                        }

                        venta.setCliente(nuevoCliente);
                        double nuevoDescuento = calcularDescuento(asientoAnterior.getPrecio(), nuevoCliente);
                        venta.setDescuento(nuevoDescuento);
                        venta.setTotal(asientoAnterior.getPrecio() - nuevoDescuento);
                        System.out.println("Datos del cliente actualizados correctamente.");
                        break;

                    case 2:
                        mostrarAsientosDisponibles();
                        String nuevoTipoAsiento = pedirTipoAsiento();
                        if (nuevoTipoAsiento.equals("salir") || nuevoTipoAsiento.equals("volver")) {
                            return;
                        }

                        Asiento nuevoAsiento = seleccionarAsiento(nuevoTipoAsiento);
                        if (nuevoAsiento == null) {
                            return;
                        }

                        if (!nuevoAsiento.isDisponible()) {
                            System.out.println("Error: El asiento seleccionado no está disponible");
                            asientoAnterior.setDisponible(false);
                            return;
                        }

                        asientoAnterior.setDisponible(true);
                        nuevoAsiento.setDisponible(false);
                        venta.setAsiento(nuevoAsiento);

                        nuevoDescuento = calcularDescuento(nuevoAsiento.getPrecio(), cliente);
                        venta.setDescuento(nuevoDescuento);
                        venta.setTotal(nuevoAsiento.getPrecio() - nuevoDescuento);

                        System.out.println("Asiento actualizado correctamente.");
                        break;

                    case 3:
                        return;

                    default:
                        System.out.println("Opción no válida.");
                        return;
                }

                System.out.println("\nVenta modificada:");
                imprimirBoleta(venta);
                guardarDatos();

            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: El ID debe ser un número.");
        }
    }

    public static Venta buscarVentaPorId(int id) {
        for (Venta venta : ventas) {
            if (venta.getId() == id) {
                return venta;
            }
        }
        return null;
    }

    public static void cancelarVenta() {
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas para cancelar.");
            return;
        }

        System.out.println("\n================ CANCELAR VENTA ================");
        System.out.print("Ingrese ID de venta a cancelar (escriba 'salir' para cancelar): ");
        String entrada = scanner.nextLine().trim();

        if (entrada.equalsIgnoreCase("salir")) {
            return;
        }

        try {
            int idVenta = Integer.parseInt(entrada);
            Venta venta = buscarVentaPorId(idVenta);

            if (venta == null) {
                System.out.println("Error: No se encontró una venta con ese ID.");
                return;
            }

            System.out.println("\nVenta a cancelar:");
            imprimirBoleta(venta);

            System.out.print("\n¿Está seguro que desea cancelar esta venta? (s/n): ");
            String confirmar = scanner.nextLine().trim().toLowerCase();

            if (confirmar.equals("s")) {
                venta.getAsiento().setDisponible(true);
                ventas.remove(venta);
                System.out.println("Venta cancelada correctamente.");
                guardarDatos();
            } else {
                System.out.println("Operación cancelada.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: El ID debe ser un número.");
        }
    }

    public static void estadisticasVentas() {
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas para mostrar estadísticas.");
            return;
        }

        System.out.println("\n================ ESTADÍSTICAS DE VENTAS ================");

        int totalVentas = ventas.size();
        double recaudacionTotal = 0;

        Map<String, Integer> ventasPorCategoria = new HashMap<>();
        Map<String, Double> recaudacionPorCategoria = new HashMap<>();

        for (String categoria : asientos.keySet()) {
            ventasPorCategoria.put(categoria, 0);
            recaudacionPorCategoria.put(categoria, 0.0);
        }

        for (Venta venta : ventas) {
            String categoria = venta.getAsiento().getCategoria();
            recaudacionTotal += venta.getTotal();

            ventasPorCategoria.put(categoria, ventasPorCategoria.get(categoria) + 1);
            recaudacionPorCategoria.put(categoria, recaudacionPorCategoria.get(categoria) + venta.getTotal());
        }

        System.out.println("Total de entradas vendidas: " + totalVentas);
        System.out.println("Recaudación total: $" + (int) recaudacionTotal);

        System.out.println("\nVentas por categoría:");
        System.out.printf("%-15s %-12s %-15s %-15s %-15s\n",
                "Categoría", "Vendidos", "Disponibles", "Recaudación", "% Ocupación");
        System.out.println("---------------------------------------------------------------------");

        for (String categoria : asientos.keySet()) {
            int vendidos = ventasPorCategoria.get(categoria);
            int disponibles = contarAsientosDisponibles(categoria);
            int total = asientos.get(categoria).size();
            double recaudacion = recaudacionPorCategoria.get(categoria);
            double porcentajeOcupacion = ((double) (total - disponibles) / total) * 100;

            System.out.printf("%-15s %-12d %-15d $%-14d %.1f%%\n",
                    categoria, vendidos, disponibles, (int) recaudacion, porcentajeOcupacion);
        }

        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    public static void guardarDatos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivoDatos))) {
            oos.writeInt(ultimoIdVenta);
            oos.writeObject(ventas);
            oos.writeObject(asientos);
            System.out.println("Datos guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar datos: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static void cargarDatos() throws IOException, ClassNotFoundException {
        if (!new File(archivoDatos).exists()) {
            inicializarAsientos();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivoDatos))) {
            ultimoIdVenta = ois.readInt();
            ventas = (List<Venta>) ois.readObject();
            asientos = (Map<String, List<Asiento>>) ois.readObject();
        }
    }
}

class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nombre;
    private int edad;
    private String genero;
    private boolean estudiante;

    public Cliente(String nombre, int edad, String genero, boolean estudiante) {
        this.nombre = nombre;
        this.edad = edad;
        this.genero = genero;
        this.estudiante = estudiante;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getGenero() {
        return genero;
    }

    public boolean isEstudiante() {
        return estudiante;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setEstudiante(boolean estudiante) {
        this.estudiante = estudiante;
    }
}

class Asiento implements Serializable {

    private static final long serialVersionUID = 1L;
    private int numero;
    private String categoria;
    private double precio;
    private boolean disponible;

    public Asiento(int numero, String categoria, double precio) {
        this.numero = numero;
        this.categoria = categoria;
        this.precio = precio;
        this.disponible = true;
    }

    public int getNumero() {
        return numero;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}

class Venta implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private Cliente cliente;
    private Asiento asiento;
    private double descuento;
    private double total;
    private transient LocalDateTime fechaHora;

    public Venta(int id, Cliente cliente, Asiento asiento, double descuento, double total, LocalDateTime fechaHora) {
        this.id = id;
        this.cliente = cliente;
        this.asiento = asiento;
        this.descuento = descuento;
        this.total = total;
        this.fechaHora = fechaHora;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeObject(fechaHora.toString());
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        this.fechaHora = LocalDateTime.parse((String) ois.readObject());
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public double getDescuento() {
        return descuento;
    }

    public double getTotal() {
        return total;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
}
