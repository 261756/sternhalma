package server;


import hex.Hex;
import server.gui.ServerLogDisplay;


import javax.swing.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * Klasa główna serwera
 */
public class Server {

    ServerLogDisplay serverLogDisplay;
    public Server(ServerLogDisplay serverLogDisplay) throws Exception {
        this.serverLogDisplay = serverLogDisplay;
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
            Socket socket = null;
            var pool = Executors.newFixedThreadPool(20);
            while (true) {
                id_count++;
                if (socket == null) {
                    socket = listener.accept();
                }
                GameState GS = new GameState(numberOfPlayers,id_count,serverLogDisplay);
                pool.execute(new PlayerHandler(socket, GS, Hex.State.RED));
                if (numberOfPlayers == 2) {
                    socket =listener.accept();
                    if (GS.checkIfGameEnded()) {continue;}
                    pool.execute(new PlayerHandler(socket, GS, Hex.State.BLUE));
                    socket = listener.accept();
                }

                else if (numberOfPlayers == 3) {
                    socket =listener.accept();
                    if (GS.checkIfGameEnded()) {continue;}
                    pool.execute(new PlayerHandler(socket,GS, Hex.State.GREEN));
                    socket =listener.accept();
                    if (GS.checkIfGameEnded()) {continue;}
                    pool.execute(new PlayerHandler(socket,GS, Hex.State.BLACK));
                    socket = listener.accept();
                }

                if (numberOfPlayers >= 5) {
                    socket =listener.accept();
                    if (GS.checkIfGameEnded()) {continue;}
                    pool.execute(new PlayerHandler(socket,GS, Hex.State.WHITE));
                    if (numberOfPlayers == 5)
                        socket = listener.accept();
                }
                if (numberOfPlayers >= 4) {
                    socket =listener.accept();
                    if (GS.checkIfGameEnded()) {continue;}
                    pool.execute(new PlayerHandler(socket, GS, Hex.State.GREEN));
                    socket =listener.accept();
                    if (GS.checkIfGameEnded()) {continue;}
                    pool.execute(new PlayerHandler(socket,GS, Hex.State.BLUE));
                    socket =listener.accept();
                    if (GS.checkIfGameEnded()) {continue;}
                    pool.execute(new PlayerHandler(socket,GS, Hex.State.BLACK));
                    if (numberOfPlayers == 4)
                        socket = listener.accept();
                }

                if (numberOfPlayers == 6) {
                    socket =listener.accept();
                    if (GS.checkIfGameEnded()) {continue;}
                    pool.execute(new PlayerHandler(socket,GS, Hex.State.YELLOW));
                    socket =listener.accept();

                }

            }
        }
    }
    public void log(String msg)
    {
        serverLogDisplay.log(msg);
    }





}
