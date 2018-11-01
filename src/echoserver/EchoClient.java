package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {
    public static final int PORT_NUMBER = 6013;

    public static void main(String[] args) throws IOException {
        EchoClient client = new EchoClient();
        client.start();
    }

    private void start() throws IOException {
        Socket socket = new Socket("localhost", PORT_NUMBER);
        InputStream socketInputStream = socket.getInputStream();
        OutputStream socketOutputStream = socket.getOutputStream();

        Thread t1 = new Thread(new KeyboardReader(socketOutputStream));
        Thread t2 = new Thread(new ServerWriter(socketInputStream));
        t1.start();
        t2.start();

    }
}

 class KeyboardReader implements Runnable {
    private OutputStream oStream;

    public KeyboardReader(OutputStream oStream) {
        this.oStream = oStream;
    }

    public void run() {
        try {
            int readByte;
            while((readByte = System.in.read()) != -1) {
                oStream.write(readByte);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

 class ServerWriter implements Runnable {
    private InputStream iStream;

    public ServerWriter(InputStream iStream) {
        this.iStream = iStream;
    }
    public void run() {
        try {
            int socketByte = iStream.read();
            System.out.write(socketByte);
            } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
