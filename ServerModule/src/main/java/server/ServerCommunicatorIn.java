package server;

import server.gui.ServerLog;
import server.gui.ServerLogDisplay;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * Odpowiada za przyjmowanie Stringów z danego inputStream
 */
public class ServerCommunicatorIn {

    Scanner in;
    ServerLog serverLog;
    PlayerHandler playerHandler;
    ServerCommunicatorIn(InputStream inputStream, ServerLog serverLog, PlayerHandler playerHandler)
    {
        in = new Scanner(inputStream);
        this.serverLog = serverLog;
        this.playerHandler = playerHandler;
    }
    Boolean availableCommandFromClient()
    {
        return in.hasNextLine();
    }
    String getCommandFromClient()
    {
        String ret = in.nextLine();
        serverLog.log("Recieved from Game " + playerHandler.getGameId() + ", " + playerHandler.getColorname() + ": "+ ret);
        return ret;

    }


}
