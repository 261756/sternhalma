package server;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;

/**
 * OBSŁUGIWANE KOMENDY:
 * requestHexes: odpowiada "sendingHexes", wysyła tablicę Hexów
 */
public class Server {
    private static GameState GS;
    public Server() throws Exception {
        GS = new GameState();

    }
    public void startServer() throws Exception {
        try (var listener = new ServerSocket(59898)) {
            System.out.println("The checkers server is running...");
            var pool = Executors.newFixedThreadPool(20);
            while (true) {
                pool.execute(new PlayerHandler(listener.accept(),GS));
            }
        }
    }



}
