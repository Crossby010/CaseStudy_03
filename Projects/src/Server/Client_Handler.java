package Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client_Handler implements Runnable {

    public static ArrayList<Client_Handler> clienthandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientusername;

    public Client_Handler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientusername = bufferedReader.readLine();
            clienthandlers.add(this);
            broadcastMessage("SERVER: " + clientusername + "Has entered the chat");
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run() {
        String messageFromClientl;

        while (socket.isConnected()) {
            try {
                messageFromClientl = bufferedReader.readLine();
                broadcastMessage(messageFromClientl);
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public void broadcastMessage(String messageToSend) {
        for (Client_Handler clienthandler : clienthandlers) {
            try {
                if (clienthandler.clientusername.equals(clientusername)) {
                    clienthandler.bufferedWriter.write(messageToSend);
                    clienthandler.bufferedWriter.newLine();
                    clienthandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void removeClientHandler() {
        clienthandlers.remove(this);
        broadcastMessage("SERVER: " + clientusername + "has left the chat");
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

