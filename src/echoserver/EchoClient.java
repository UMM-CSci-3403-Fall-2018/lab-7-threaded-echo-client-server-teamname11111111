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

        Thread t1 = new Thread(new KeyboardReader(socketOutputStream, socket));
        Thread t2 = new Thread(new ServerWriter(socketInputStream, socket));

        t1.start();
        t2.start();



        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.flush();
    }
}

 class KeyboardReader implements Runnable {
    private OutputStream oStream;
    private Socket socket;

    public KeyboardReader(OutputStream oStream, Socket socket) {
        this.socket = socket;
        this.oStream = oStream;
    }

    public void run() {
        try {
            int readByte;
            while ((readByte = System.in.read()) != -1) {
                oStream.write(readByte);
            }

            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

 class ServerWriter implements Runnable {
    private InputStream iStream;
    private Socket iSocket;

    public ServerWriter(InputStream iStream, Socket iSocket) {
        this.iStream = iStream;
        this.iSocket = iSocket;
    }
    public void run() {
        try {
            int socketByte;
            while ((socketByte = iStream.read()) != -1){
                System.out.write(socketByte);
            }

            iSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
