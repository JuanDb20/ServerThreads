import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public final class ServidorWeb {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        int puerto = 0;

        // Se pide el puerto hasta que sea válido
        while (puerto <= 1024) {
            System.out.print("Ingrese el puerto del servidor (mayor a 1024): ");

            if (scanner.hasNextInt()) {
                puerto = scanner.nextInt();

                if (puerto <= 1024) {
                    System.out.println("El puerto debe ser mayor a 1024.");
                }
            } else {
                System.out.println("Debe ingresar un número.");
                scanner.next(); 
            }
        }

        // Se crea el socket del servidor con el puerto válido
        ServerSocket serverSocket = new ServerSocket(puerto);
        System.out.println("Servidor escuchando en el puerto " + puerto);

        // El servidor queda ejecutándose indefinidamente
        while (true) {

            // Espera a que un cliente se conecte
            Socket socket = serverSocket.accept();
            System.out.println("Cliente conectado");

            SolicitudHttp solicitud = new SolicitudHttp(socket);

            // Se crea un hilo para manejar la solicitud
            Thread hilo = new Thread(solicitud);

            hilo.start();
        }
    }
}
