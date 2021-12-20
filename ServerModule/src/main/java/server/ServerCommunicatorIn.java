package server;

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
    ServerLogDisplay serverLogDisplay;
    PlayerHandler playerHandler;
    ServerCommunicatorIn(InputStream inputStream, ServerLogDisplay serverLogDisplay, PlayerHandler playerHandler)
    {
        in = new Scanner(inputStream);
        this.serverLogDisplay = serverLogDisplay;
        this.playerHandler = playerHandler;
    }
    Boolean availableCommandFromClient()
    {
        return in.hasNextLine();
    }
    String getCommandFromClient()
    {
        String ret = in.nextLine();
        serverLogDisplay.log("Recieved from Game " + playerHandler.getGameId() + ", " + playerHandler.getColorname() + ": "+ ret);
        return ret;

    }


}
