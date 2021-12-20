package server;

import server.gui.ServerLogDisplay;
import server.gui.StartPopup;

/**
 * Klasa tworząca instancję serwera i dwa okienka.
 */
public class ServerStarter {


    public static void main(String[] args) throws Exception {
        ServerLogDisplay serverLogDisplay = new ServerLogDisplay();
        Server S = new Server(serverLogDisplay);
        StartPopup startPopup = new StartPopup(S);
    }



}
