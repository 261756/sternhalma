package server;


import hex.Hex;
import server.gui.ServerLogDisplay;


import javax.swing.*;
import java.net.ServerSocket;
import java.util.concurrent.Executors;


public class Server {

    ServerLogDisplay serverLogDisplay;
    public Server(ServerLogDisplay serverLogDisplay) throws Exception {
        this.serverLogDisplay = serverLogDisplay;
        this.serverLogDisplay.log("lol");
    }

    /**
     * Główna pętla serwera, jeśli ktoś próbuje się połączyć z portem, to jest mu nadawany PlayerHandler
     * @throws Exception
     * @param serverPort - port servera
     */
    public void startServer(int serverPort, int numberOfPlayers) throws Exception {
        try (var listener = new ServerSocket(serverPort)) {
            serverLogDisplay.log("Serwer wystartował na porcie " + serverPort +", hostuje gry dla " + numberOfPlayers + " graczy.");
            int id_count = 0;
            var pool = Executors.newFixedThreadPool(20);
            while (true) {
                GameState GS = new GameState(numberOfPlayers,id_count,serverLogDisplay);
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
