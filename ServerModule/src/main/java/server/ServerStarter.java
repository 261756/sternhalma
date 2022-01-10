package server;

import server.gui.ServerLog;
import server.gui.ServerLogDisplay;
import server.gui.ServerLogTerminal;
import server.gui.StartPopup;

/**
 * Klasa tworząca instancję serwera i dwa okienka.
 */
public class ServerStarter {

    public static void main(String[] args) throws Exception {

        // jeśli nie podano argumentów, to używamy GUI
        if (args.length == 0) {
            ServerLog serverLog = new ServerLogDisplay();
            Server S = new Server(serverLog);
            StartPopup startPopup = new StartPopup(S);
        }
        // jeśli podano poprawne argumenty port, liczba graczy to ich używamy
        else {
            ServerLog serverLog = new ServerLogTerminal();
            Server S = new Server(serverLog);
            NoGUIInitializer.startServerNoGUI(S,args[0],args[1]);
        }
    }

}
