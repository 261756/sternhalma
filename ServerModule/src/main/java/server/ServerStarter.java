package server;

import server.gui.ServerLogDisplay;
import server.gui.StartPopup;

public class ServerStarter {


    public static void main(String[] args) throws Exception {
        ServerLogDisplay serverLogDisplay = new ServerLogDisplay();
        Server S = new Server(serverLogDisplay);
        StartPopup startPopup = new StartPopup(S);
    }



}
