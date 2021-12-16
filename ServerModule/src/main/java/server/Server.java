package server;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;


public class Server {
    private static GameState GS;
    public Server() throws Exception {
        GS = new GameState();
    }

    /**
     * Główna pętla serwera, jeśli ktoś próbuje się połączyć z portem, to jest mu nadawany PlayerHandler
     * @throws Exception
     * @param serverPort - port servera
     */
    public void startServer(int serverPort) throws Exception {
        try (var listener = new ServerSocket(serverPort)) {
            System.out.println("The checkers server is running...");
            var pool = Executors.newFixedThreadPool(20);
            while (true) {
                pool.execute(new PlayerHandler(listener.accept(),GS));
            }
        }
    }



}
