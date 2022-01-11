package server;


import hex.Hex;
import server.boardTools.RegionFactory;
import server.gui.ServerLog;


import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

import static java.lang.System.exit;

/**
 * Klasa główna serwera
 */
public class Server {

    ServerLog serverLog;

    /**
     * Konstruktor
     * @param serverLog logger serwera
     */
    public Server(ServerLog serverLog) {
        this.serverLog = serverLog;
    }

    /**
     * Główna pętla serwera, jeśli ktoś próbuje się połączyć z portem, to jest mu nadawany PlayerHandler
     * @throws Exception exceptions
     * @param serverPort port servera
     * @param numberOfPlayers ilość graczy
     */
    public boolean startServer(int serverPort, int numberOfPlayers) throws Exception {
        ServerSocket listener = null;
        try  {
            listener = new ServerSocket(serverPort);
            serverLog.log("Serwer wystartował na porcie " + serverPort +", hostuje gry dla " + numberOfPlayers + " graczy.");
            int id_count = 0;
            Socket socket = null;
            var pool = Executors.newFixedThreadPool(20);
            while (true) {
                id_count++;
                if (socket == null) {
                    socket = listener.accept();
                }
                GameState GS = new GameState(numberOfPlayers,id_count,serverLog, new MoveValidator(), new RegionFactory());
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


                else if (numberOfPlayers == 4) {
                    socket =listener.accept();
                    if (GS.checkIfGameEnded()) {continue;}
                    pool.execute(new PlayerHandler(socket, GS, Hex.State.GREEN));
                    socket =listener.accept();
                    if (GS.checkIfGameEnded()) {continue;}
                    pool.execute(new PlayerHandler(socket,GS, Hex.State.BLUE));
                    socket =listener.accept();
                    if (GS.checkIfGameEnded()) {continue;}
                    pool.execute(new PlayerHandler(socket,GS, Hex.State.YELLOW));
                    socket = listener.accept();
                }

                if (numberOfPlayers == 6) {
                    socket =listener.accept();
                    if (GS.checkIfGameEnded()) {continue;}
                    pool.execute(new PlayerHandler(socket,GS, Hex.State.WHITE));
                    socket =listener.accept();
                    if (GS.checkIfGameEnded()) {continue;}
                    pool.execute(new PlayerHandler(socket,GS, Hex.State.GREEN));
                    socket =listener.accept();
                    if (GS.checkIfGameEnded()) {continue;}
                    pool.execute(new PlayerHandler(socket,GS, Hex.State.BLUE));
                    socket =listener.accept();
                    if (GS.checkIfGameEnded()) {continue;}
                    pool.execute(new PlayerHandler(socket,GS, Hex.State.BLACK));
                    socket =listener.accept();
                    if (GS.checkIfGameEnded()) {continue;}
                    pool.execute(new PlayerHandler(socket,GS, Hex.State.YELLOW));
                    socket =listener.accept();

                }

            }
        }
        catch (BindException e)
        {
            serverLog.log("Adres jest już zajęty!");
            //exit(0);
        }
        catch (Exception e){
            if (listener != null) {
                listener.close();
            }
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Wysyła String na log serwera
     * @param msg wpis do przesłania
     */
    public void log(String msg)
    {
        serverLog.log(msg);
    }





}
