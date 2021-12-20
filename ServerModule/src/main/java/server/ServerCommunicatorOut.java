package server;

import server.gui.ServerLogDisplay;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Odpowiada za wysyłanie stringów do danego outputstream
 */
public class ServerCommunicatorOut {
    PrintWriter out;
    ServerLogDisplay serverLogDisplay;
    PlayerHandler playerHandler;
    ServerCommunicatorOut(OutputStream outputStream, ServerLogDisplay serverLogDisplay, PlayerHandler playerHandler) throws IOException
    {
        this.serverLogDisplay = serverLogDisplay;
        this.playerHandler = playerHandler;
        out = new PrintWriter(outputStream,true);
    }
    public void writeString(String string) throws IOException {
        out.println(string);
        serverLogDisplay.log("Sent to Game " + playerHandler.getGameId() + ", " + playerHandler.getColorname() + ": " + string);
    }

}
