Proceso Gestion_Ventas_Simple
    Definir carrito Como Cadena  // Variable para almacenar los productos en el carrito
    Definir total Como Real      // Total de la compra
    Definir opcion, precio Como Entero  // Opci�n seleccionada y precio del producto
    Definir producto Como Cadena  // Definir la variable producto como cadena
    carrito <- ""  // Inicializar el carrito vac�o
    total <- 0      // Inicializar el total
	
    Repetir
        Escribir "Seleccione una opci�n:"
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
                    Escribir "El carrito est� vac�o."
                Sino
                    Escribir carrito  // Mostrar los productos en el carrito
                    Escribir "Total: $", total
                FinSi
            3:
                Escribir "Procesando pago..."
                Si total > 0 Entonces
                    Escribir "Pago exitoso. Gracias por su compra!"
                    // Limpiar el carrito despu�s del pago
                    carrito <- ""
                    total <- 0
                Sino
                    Escribir "El carrito est� vac�o. No se puede procesar el pago."
                FinSi
            4:
                Escribir "Saliendo del sistema..."
            De Otro Modo:
                Escribir "Opci�n inv�lida. Intente nuevamente."
        FinSegun
    Hasta Que opcion = 4
FinProceso






