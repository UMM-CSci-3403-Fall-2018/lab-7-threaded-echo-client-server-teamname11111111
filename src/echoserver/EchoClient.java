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
        Thread t1 = new Thread(new keyboardReader());
        Thread t2 = new Thread(new serverWriter());
        t1.start();
        t2.start();
    }        System.out.flush();
}

public class keyboardReader implements Runnable {
    public void run() {
        try {
            int readByte;
            while((readByte = System.in.read()) != -1) {
                socketOutputStream.write(readByte);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class serverWriter implements Runnable {
    public void run() {
        try {
            int socketByte = socketInputStream.read();
            System.out.write(socketByte);
            } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
