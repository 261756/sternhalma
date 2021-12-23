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
        // jeśli podano poprawne argumenty port, liczba graczy to ich używamy
        if (args.length != 0) {
            startPopup.sendInputNoGUI(args[0],args[1]);
        }
    }
}
