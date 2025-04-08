Proceso Gestion_Ventas_Simple
    Definir carrito Como Cadena  // Variable para almacenar los productos en el carrito
    Definir total Como Real      // Total de la compra
    Definir opcion, precio Como Entero  // Opción seleccionada y precio del producto
    Definir producto Como Cadena  // Definir la variable producto como cadena
    carrito <- ""  // Inicializar el carrito vacío
    total <- 0      // Inicializar el total
	
    Repetir
        Escribir "Seleccione una opción:"
        Escribir "1. Agregar producto al carrito"
        Escribir "2. Ver carrito"
        Escribir "3. Procesar pago"
        Escribir "4. Salir"
        Leer opcion
        
        Segun opcion Hacer
            1:
                Escribir "Ingrese nombre del producto:"
                Leer producto  // Captura el nombre del producto
                Escribir "Ingrese el precio del producto:"
                Leer precio
                
                // Agregar el producto al carrito
                Si carrito = "" Entonces
                    carrito <- producto
                Sino
                    carrito <- carrito + ", " + producto 
                FinSi
                total <- total + precio
                Escribir "Producto agregado al carrito."
            2:
                Escribir "Carrito de compras:"
                Si carrito = "" Entonces
                    Escribir "El carrito está vacío."
                Sino
                    Escribir carrito  // Mostrar los productos en el carrito
                    Escribir "Total: $", total
                FinSi
            3:
                Escribir "Procesando pago..."
                Si total > 0 Entonces
                    Escribir "Pago exitoso. Gracias por su compra!"
                    // Limpiar el carrito después del pago
                    carrito <- ""
                    total <- 0
                Sino
                    Escribir "El carrito está vacío. No se puede procesar el pago."
                FinSi
            4:
                Escribir "Saliendo del sistema..."
            De Otro Modo:
                Escribir "Opción inválida. Intente nuevamente."
        FinSegun
    Hasta Que opcion = 4
FinProceso






