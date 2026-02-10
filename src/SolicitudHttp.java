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

        try {
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            br = new BufferedReader(new InputStreamReader(is));
            bw = new BufferedWriter(new OutputStreamWriter(os));

            String linea;
            while ((linea = br.readLine()) != null && !linea.isEmpty()) {
                System.out.println(linea);
            }

            String payload = "<html><body><h1>Hello, World!</h1></body></html>";

            bw.write("HTTP/1.1 200 OK\r\n");
            bw.write("Content-Type: text/html\r\n");
            bw.write("Content-Length: " + payload.length() + "\r\n");
            bw.write("\r\n");
            bw.write(payload);
            bw.flush();

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
