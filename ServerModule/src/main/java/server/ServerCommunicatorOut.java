package server;

import server.gui.ServerLog;
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
    ServerLog serverLog;
    PlayerHandler playerHandler;
    ServerCommunicatorOut(OutputStream outputStream, ServerLog serverLog, PlayerHandler playerHandler) throws IOException
    {
        this.serverLog = serverLog;
        this.playerHandler = playerHandler;
        out = new PrintWriter(outputStream,true);
    }
    public void writeString(String string) throws IOException {
        out.println(string);
        serverLog.log("Sent to Game " + playerHandler.getGameId() + ", " + playerHandler.getColorname() + ": " + string);
    }

}
