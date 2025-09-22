package network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class MyServer {
    public static void main(String[] args) throws IOException {
        int port = 6666;
        ServerSocket ss = new ServerSocket(port);
        System.out.println("server is running...");
        while (true) {
            Socket sock = ss.accept();
            Handler handler = new Handler(sock);
            handler.start();
            System.out.println("connected from " + sock.getRemoteSocketAddress());
        }
    }
}
class Handler extends Thread {
    private final Socket socket;
    Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (InputStream input = this.socket.getInputStream()) {
            try(OutputStream output = this.socket.getOutputStream()){
                handle(input, output);
            }
        } catch (Exception E) {
            try {
                this.socket.close();
            } catch (IOException e) {

            }
            System.out.println("client disconnected.");
        }
    }

    private void handle(InputStream input, OutputStream output) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
        BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        writer.write("hello\n");
        writer.flush();
        while (true) {
            String s = reader.readLine();
            if (s.equals("bye")) {
                writer.write("bye\n");
                writer.flush();
                break;
            }
            writer.write("ok: " + s + "\n");
            writer.flush();
        }
    }
}
