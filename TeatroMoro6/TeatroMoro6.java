import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TeatroMoro6 {
    static final int TOTAL_ASIENTOS = 100;
    static final int PRECIO_ENTRADA = 5000;

    static class Reserva {
        String nombre;
        String tipo;
        int asiento;

        public Reserva(String nombre, String tipo, int asiento) {
            this.nombre = nombre;
            this.tipo = tipo;
            this.asiento = asiento;
        }

        public int calcularPrecio() {
            if (tipo.equalsIgnoreCase("ESTUDIANTE")) return (int)(PRECIO_ENTRADA * 0.5);
            if (tipo.equalsIgnoreCase("TERCERA_EDAD")) return (int)(PRECIO_ENTRADA * 0.3);
            return PRECIO_ENTRADA;
        }

        public String toString() {
            return "Nombre: " + nombre + ", Tipo: " + tipo + ", Asiento: " + asiento + ", Precio: $" + calcularPrecio();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean[] asientos = new boolean[TOTAL_ASIENTOS];
        ArrayList<Reserva> reservas = new ArrayList<>();

        while (true) {
            System.out.println("\n--- Teatro Moro ---");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Cancelar entrada");
            System.out.println("3. Actualizar reserva");
            System.out.println("4. Ver resumen");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = -1;
            try {
                opcion = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Por favor, ingrese un número válido.");
                sc.nextLine();
                continue;
            }

            if (opcion == 1) {
                System.out.print("Ingrese su nombre: ");
                String nombre = sc.nextLine().trim();

                String tipo = "";
                while (true) {
                    System.out.print("Tipo de cliente (NORMAL, ESTUDIANTE, TERCERA_EDAD): ");
                    tipo = sc.nextLine().trim().toUpperCase();
                    if (tipo.equals("NORMAL") || tipo.equals("ESTUDIANTE") || tipo.equals("TERCERA_EDAD")) break;
                    System.out.println("Tipo inválido. Intente de nuevo.");
                }

                int asiento = -1;
                while (true) {
                    System.out.print("Seleccione número de asiento (0-99): ");
                    try {
                        asiento = sc.nextInt();
                        sc.nextLine();
                        if (asiento >= 0 && asiento < TOTAL_ASIENTOS) {
                            if (!asientos[asiento]) break;
                            else System.out.println("Ese asiento ya está ocupado.");
                        } else {
                            System.out.println("Número fuera de rango.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Debe ingresar un número.");
                        sc.nextLine();
                    }
                }

                reservas.add(new Reserva(nombre, tipo, asiento));
                asientos[asiento] = true;
                System.out.println("Entrada comprada con éxito.");

            } else if (opcion == 2) {
                System.out.print("Ingrese su nombre para cancelar: ");
                String nombre = sc.nextLine().trim();
                boolean encontrado = false;

                for (int i = 0; i < reservas.size(); i++) {
                    Reserva r = reservas.get(i);
                    if (r != null && r.nombre.equalsIgnoreCase(nombre)) {
                        asientos[r.asiento] = false;
                        reservas.set(i, null);
                        System.out.println("Reserva cancelada.");
                        encontrado = true;
                        break;
                    }
                }

                if (!encontrado) {
                    System.out.println("Reserva no encontrada.");
                }

            } else if (opcion == 3) {
                System.out.print("Ingrese su nombre para actualizar: ");
                String nombre = sc.nextLine().trim();
                boolean encontrado = false;

                for (int i = 0; i < reservas.size(); i++) {
                    Reserva r = reservas.get(i);
                    if (r != null && r.nombre.equalsIgnoreCase(nombre)) {
                        asientos[r.asiento] = false;

                        System.out.print("Nuevo nombre: ");
                        String nuevoNombre = sc.nextLine().trim();

                        String nuevoTipo = "";
                        while (true) {
                            System.out.print("Nuevo tipo (NORMAL, ESTUDIANTE, TERCERA_EDAD): ");
                            nuevoTipo = sc.nextLine().trim().toUpperCase();
                            if (nuevoTipo.equals("NORMAL") || nuevoTipo.equals("ESTUDIANTE") || nuevoTipo.equals("TERCERA_EDAD")) break;
                            System.out.println("Tipo inválido.");
                        }

                        int nuevoAsiento = -1;
                        while (true) {
                            System.out.print("Nuevo asiento (0-99): ");
                            try {
                                nuevoAsiento = sc.nextInt();
                                sc.nextLine();
                                if (nuevoAsiento >= 0 && nuevoAsiento < TOTAL_ASIENTOS) {
                                    if (!asientos[nuevoAsiento]) break;
                                    else System.out.println("Asiento ocupado.");
                                } else {
                                    System.out.println("Número fuera de rango.");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Debe ser un número.");
                                sc.nextLine();
                            }
                        }

                        reservas.set(i, new Reserva(nuevoNombre, nuevoTipo, nuevoAsiento));
                        asientos[nuevoAsiento] = true;
                        System.out.println("Reserva actualizada.");
                        encontrado = true;
                        break;
                    }
                }

                if (!encontrado) {
                    System.out.println("Reserva no encontrada.");
                }

            } else if (opcion == 4) {
                int total = 0;
                System.out.println("\n--- Resumen de Compras ---");
                for (Reserva r : reservas) {
                    if (r != null) {
                        System.out.println(r.toString());
                        total += r.calcularPrecio();
                    }
                }
                System.out.println("Total recaudado: $" + total);

            } else if (opcion == 5) {
                System.out.println("Gracias por usar Teatro Moro.");
                break;

            } else {
                System.out.println("Opción no válida.");
            }
        }

        sc.close();
    }
}
