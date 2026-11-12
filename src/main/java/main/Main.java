package main;

import java.util.Scanner;
import model.core.Cinema;

public class Main {

    public static void main(String[] args) {
        Scanner entradaTeclado = new Scanner(System.in);
        Cinema cinemaPrincipal = new Cinema("CinemaUQ");
        int opcionSeleccionada;

        do {
            System.out.println("\n---- Menú CINEMAUQ ----");
            System.out.println("=== MÓDULO DE OPERADOR ===");
            System.out.println("1. Vender entrada");
            System.out.println("2. Cancelar venta");
            System.out.println("3. Consultar películas disponibles");
            System.out.println("4. Consultar funciones programadas");
            System.out.println("5. Consultar disponibilidad de una función");
            System.out.println("------------------------------------");
            System.out.println("=== MÓDULO DE ADMINISTRADOR ===");
            System.out.println("6. Administrar películas (Registrar, consultar, modificar, deshabilitar)");
            System.out.println("7. Administrar salas (Registrar, consultar, modificar, deshabilitar)");
            System.out.println("8. Administrar funciones (Programar, finalizar, cancelar)");
            System.out.println("9. Gestionar tarifas por tipo de cliente");
            System.out.println("------------------------------------");
            System.out.println("=== MÓDULO DE REPORTES ===");
            System.out.println("10. Reporte: Total de entradas vendidas");
            System.out.println("11. Reporte: Total de ingresos");
            System.out.println("12. Reporte: Película con mayor cantidad de entradas vendidas");
            System.out.println("13. Reporte: Ocupación de una función");
            System.out.println("14. Reporte: Funciones programadas por fecha");
            System.out.println("------------------------------------");
            System.out.println("15. Salir");
            System.out.print("\nIngrese una opción: ");
            opcionSeleccionada = entradaTeclado.nextInt();
            entradaTeclado.nextLine();

            switch (opcionSeleccionada) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    break;
                case 11:
                    break;
                case 12:
                    break;
                case 13:
                    break;
                case 14:
                    break;
                case 15:
                    System.out.println("Saliendo del sistema CinemaUQ...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, ingrese una opción del menú.");
                    break;
            }
        } while (opcionSeleccionada != 15);
        entradaTeclado.close();
    }
}