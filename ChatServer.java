package ie.atu.dip;

import java.io.*;
import java.net.*;

public class ChatServer {
    public static int PORT = 8888;

    public static void main(String[] args) throws IOException {
        // Create a ServerSocket to listen for incoming connections
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Waiting for client to connect...");

        // Accept the incoming connection
        while (true) {
            Socket socket = serverSocket.accept();

            InputStreamReader inputStreamReader = new InputStreamReader(System.in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            // Client Communication Thread
            new Thread(() -> {
                try {
                    while (true) {
                        handleClientComms(socket);
                    }
                } catch (IOException e) {
                    System.out.println("Client disconnected");
                }
            }).start();

            // Server Communication Thread
            new Thread(() -> {
                try {
                    while (true) {
                        handleServerComms(socket, dataOutputStream, bufferedReader);
                    }
                } catch (IOException e) {
                    System.out.println("Server Shutdown, Goodbye");
                }
            }).start();
        }
    }

    private static void handleClientComms(Socket socket) throws IOException {
        // Display message from clients
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        String message = dataInputStream.readUTF();
        System.out.print(message + "\n");
    }

    private static void handleServerComms(Socket socket, DataOutputStream dataOutputStream, BufferedReader bufferedReader) throws IOException {
        String message = bufferedReader.readLine();

        // Write message to clients
        dataOutputStream.writeUTF(message);
        dataOutputStream.flush();

        if (message.equals("\\q")) {
            shutDownServer(socket, bufferedReader, dataOutputStream);
        }
    }

    private static void shutDownServer(Socket socket, BufferedReader bufferedReader, DataOutputStream dataOutputStream) throws IOException {
        //Closing everything
        socket.close();
        bufferedReader.close();
        dataOutputStream.close();
    }
}
