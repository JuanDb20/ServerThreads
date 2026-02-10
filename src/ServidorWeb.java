import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public final class ServidorWeb {

    public static void main(String[] args) throws IOException {

        // Se crea el socket del servidor en el puerto 5000
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Esperando la conexion...");

        // El servidor queda ejecutándose indefinidamente 
        while (true) {

            // Espera a que un cliente se conecte
            Socket socket = serverSocket.accept();
            System.out.println("Coonectado al servidor");

            // Se crea la solicitud HTTP asociada al cliente
            SolicitudHttp solicitud = new SolicitudHttp(socket);

            // Se crea un hilo para manejar la solicitud
            Thread hilo = new Thread(solicitud);

            // Se inicia el hilo (multihilo)
            hilo.start();
        }
    }
}
