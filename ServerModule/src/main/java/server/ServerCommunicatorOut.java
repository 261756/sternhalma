package server;

import server.gui.ServerLog;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Odpowiada za wysyłanie stringów do danego outputstream
 */
public class ServerCommunicatorOut {
    /**
     * PrintWriter piszący do klienta
     */
    PrintWriter out;
    /**
     * Log serwera
     */
    ServerLog serverLog;
    /**
     * Handler klienta
     */
    PlayerHandler playerHandler;

    /**
     * Konstruktor
     * @param outputStream wyjście
     * @param serverLog log serwera
     * @param playerHandler handler klienta
     * @throws IOException błąd PrintWriter
     */
    ServerCommunicatorOut(OutputStream outputStream, ServerLog serverLog, PlayerHandler playerHandler) throws IOException
    {
        this.serverLog = serverLog;
        this.playerHandler = playerHandler;
        out = new PrintWriter(outputStream,true);
    }

    /**
     * Wysyła komendę do klienta
     * @param string komenda do wysłania
     * @throws IOException błąd PrintWriter
     */
    public void writeString(String string) throws IOException {
        out.println(string);
        serverLog.log("Sent to Game " + playerHandler.getGameId() + ", " + playerHandler.getColorname() + ": " + string);
    }

}
