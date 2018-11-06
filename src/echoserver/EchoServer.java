package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException, InterruptedException {
		EchoServer server = new EchoServer();
		server.start();
	}

	private void start() throws IOException, InterruptedException {
		ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
		while (true) {
			Socket socket = serverSocket.accept();
			InputStream iStream = socket.getInputStream();
			OutputStream oStream = socket.getOutputStream();

			Thread a = new Thread(new ServerThreader(oStream, socket, iStream));
			a.start();
			}
		}
	}

	class ServerThreader implements Runnable {
		private OutputStream oStream;
		private Socket socket;
		private InputStream iStream;

		public ServerThreader(OutputStream oStream, Socket socket, InputStream iStream) {
			this.socket = socket;
			this.iStream = iStream;
			this.oStream = oStream;
		}

		public void run() {
			try {
				int b;
				while ((b = iStream.read()) != -1) {
					oStream.write(b);
				}
				socket.shutdownOutput();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}