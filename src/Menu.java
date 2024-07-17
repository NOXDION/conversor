import java.util.Scanner;

public class Menu {
    private final ClienteTasaDeCambio cliente = new ClienteTasaDeCambio();

    public void iniciarMenu() {
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.println("\nMenú:");
            System.out.println("1. Obtener tasa de cambio");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opción: ");
            input = scanner.nextLine();
            switch (input) {
                case "1":
                    System.out.print("Ingrese la moneda de origen (ejemplo: USD): ");
                    String monedaOrigen = scanner.nextLine();
                    System.out.print("Ingrese la moneda destino (ejemplo: EUR): ");
                    String monedaDestino = scanner.nextLine();
                    try {
                        String tasaDeCambio = cliente.obtenerTasaDeCambio(monedaOrigen, monedaDestino);
                        System.out.println("Tasa de cambio: " + tasaDeCambio);
                    } catch (Exception e) {
                        System.out.println("Error al obtener la tasa de cambio. Por favor, intente de nuevo.");
                    }
                    break;
                case "2":
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
                    break;
            }
        } while (!input.equals("2"));
    }
}