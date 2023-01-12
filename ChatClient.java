package ie.atu.dip;

import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) throws IOException {
        try {
            // Accessing PORT from Server class
            Socket socket = new Socket("localhost", ChatServer.PORT);
            System.out.println("Connecting to localhost:" + ChatServer.PORT);

            InputStreamReader inputStreamReader = new InputStreamReader(System.in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            // Read users name
            System.out.print("Enter your name: ");
            String name = bufferedReader.readLine();
            dataOutputStream.writeUTF(name + " has connected");

            // Client Communication Thread
            new Thread(() -> {
                try {
                    while (true) {
                        handleClientComms(socket, bufferedReader, dataOutputStream, name);
                    }
                } catch (IOException e) {
                    System.out.println("Client Shutdown, Goodbye");
                }
            }).start();

            // Server Communication Thread
            new Thread(() -> {
                try {
                    while (true) {
                        handleServerComms(socket, dataOutputStream);
                    }
                } catch (IOException e) {
                    System.out.println("Connection lost to server");
                }
            }).start();
        } catch (SocketException socketException) {
            System.out.println("Server cannot be reached");
        }
    }

    private static void handleClientComms(Socket socket, BufferedReader bufferedReader, DataOutputStream dataOutputStream, String name) throws IOException {
        String message = bufferedReader.readLine();
        dataOutputStream.writeUTF(name + ": " + message);

        if (message.equals("\\q")) {
            shutDownClient(socket, bufferedReader, dataOutputStream);
        }
    }

    private static void handleServerComms(Socket socket, DataOutputStream dataOutputStream) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

        String message = dataInputStream.readUTF();
        System.out.print("Server: " + message + "\n");
        dataOutputStream.flush();
    }

    private static void shutDownClient(Socket socket, BufferedReader bufferedReader, DataOutputStream dataOutputStream) throws IOException {
        //Closing everything
        socket.close();
        bufferedReader.close();
        dataOutputStream.close();
    }
}
