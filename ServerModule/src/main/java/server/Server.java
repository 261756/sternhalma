package server;


import hex.Hex;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;


public class Server {
    public Server() throws Exception {

    }

    /**
     * Główna pętla serwera, jeśli ktoś próbuje się połączyć z portem, to jest mu nadawany PlayerHandler
     * @throws Exception
     * @param serverPort - port servera
     */
    public void startServer(int serverPort, int numberOfPlayers) throws Exception {
        try (var listener = new ServerSocket(serverPort)) {
            System.out.println("The checkers server is running...");
            int id_count = 0;
            var pool = Executors.newFixedThreadPool(20);
            while (true) {
                GameState GS = new GameState(numberOfPlayers,id_count);
                pool.execute(new PlayerHandler(listener.accept(),GS, Hex.State.RED));
                pool.execute(new PlayerHandler(listener.accept(),GS, Hex.State.BLUE));
                if (numberOfPlayers >= 3)
                    pool.execute(new PlayerHandler(listener.accept(),GS, Hex.State.WHITE));
                if (numberOfPlayers >= 4)
                    pool.execute(new PlayerHandler(listener.accept(),GS, Hex.State.BLACK));
                if (numberOfPlayers >= 5)
                    pool.execute(new PlayerHandler(listener.accept(),GS, Hex.State.GREEN));
                if (numberOfPlayers >= 6)
                    pool.execute(new PlayerHandler(listener.accept(),GS, Hex.State.YELLOW));
                id_count++;
            }
        }
    }



}
