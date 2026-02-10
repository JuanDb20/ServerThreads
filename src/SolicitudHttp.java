import java.io.*;
import java.net.Socket;

final class SolicitudHttp implements Runnable {

    private Socket socket;

    public SolicitudHttp(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        BufferedReader br = null;
        BufferedWriter bw = null;
        OutputStream os = null;

        try {
            InputStream is = socket.getInputStream();
            os = socket.getOutputStream();

            br = new BufferedReader(new InputStreamReader(is));
            bw = new BufferedWriter(new OutputStreamWriter(os));

            // Línea de solicitud
            String lineaSolicitud = br.readLine();
            System.out.println("Linea de solicitud:");
            System.out.println(lineaSolicitud);

            String[] partes = lineaSolicitud.split(" ");
            String metodo = partes[0];
            String recurso = partes[1];

            // Headers
            String linea;
            while ((linea = br.readLine()) != null && !linea.isEmpty()) {
                System.out.println(linea);
            }

            // Recurso por defecto
            if (recurso.equals("/")) {
                recurso = "/index.html";
            }

            String rutaArchivo = "www" + recurso;
            File archivo = new File(rutaArchivo);

            // ------------------ 404 ------------------
            if (!archivo.exists() || !archivo.isFile()) {

                File archivo404 = new File("www/404.html");

                StringBuilder payload404 = new StringBuilder();

                if (archivo404.exists()) {
                    BufferedReader fr = new BufferedReader(new FileReader(archivo404));
                    String l;
                    while ((l = fr.readLine()) != null) {
                        payload404.append(l);
                    }
                    fr.close();
                } else {
                    payload404.append("<html><body><h1>404 - Not Found</h1></body></html>");
                }

                bw.write("HTTP/1.1 404 Not Found\r\n");
                bw.write("Content-Type: text/html\r\n");
                bw.write("Content-Length: " + payload404.length() + "\r\n");
                bw.write("\r\n");
                bw.write(payload404.toString());
                bw.flush();
                return;
            }

            // ------------------ HTML ------------------
            if (recurso.endsWith(".html")) {

                BufferedReader fr = new BufferedReader(new FileReader(archivo));
                StringBuilder payload = new StringBuilder();
                String l;

                while ((l = fr.readLine()) != null) {
                    payload.append(l);
                }
                fr.close();

                bw.write("HTTP/1.1 200 OK\r\n");
                bw.write("Content-Type: text/html\r\n");
                bw.write("Content-Length: " + payload.length() + "\r\n");
                bw.write("\r\n");
                bw.write(payload.toString());
                bw.flush();

            }
            // ------------------ IMÁGENES ------------------
            else if (recurso.endsWith(".jpg") || recurso.endsWith(".jpeg") || recurso.endsWith(".gif")) {

                String mimeType = recurso.endsWith(".gif") ? "image/gif" : "image/jpeg";

                bw.write("HTTP/1.1 200 OK\r\n");
                bw.write("Content-Type: " + mimeType + "\r\n");
                bw.write("Content-Length: " + archivo.length() + "\r\n");
                bw.write("\r\n");
                bw.flush();

                FileInputStream fis = new FileInputStream(archivo);
                byte[] buffer = new byte[1024];
                int n;

                while ((n = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, n);
                }

                os.flush();
                fis.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
                if (bw != null) bw.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
