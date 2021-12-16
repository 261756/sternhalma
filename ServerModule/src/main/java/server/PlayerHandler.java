package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * każdy klient dostanie swój własny playerHandler (testowane connectionCount)
 * playerHandelr umiera kiedy jego klient się rozłączy także lepiej tu nie przechowywać informacji
 *
 * OBSŁUGIWANE KOMENDY:
 * requestHexes: odpowiada "sendingHexes", wysyła tablicę Hexów
 *
 */
public class PlayerHandler implements Runnable {
    private Socket socket;
    private GameState GS;
    private ServerCommunicatorOut SCO;
    private ServerCommunicatorIn SCI;
    /**
     * ile klientow jest polaczylo się z playerHandler podczas jego życia
     */
    int connectionCount;

    PlayerHandler(Socket socket, GameState gs) throws IOException {
        this.socket = socket;
        this.GS = gs;
        SCO = new ServerCommunicatorOut(socket.getOutputStream());
        SCI = new ServerCommunicatorIn(socket.getInputStream());
        connectionCount = 0;
    }

    @Override
    public void run() {
        System.out.println("Connected: " + socket);
        connectionCount++;
        System.out.println("Connection count: " + connectionCount);
        try {
            while (SCI.availableCommandFromClient()) {
                //System.out.println(in.nextLine());
                if (SCI.getCommandFromClient().equals("requestHexes"))
                {
                    SCO.writeObject("sendingHexes");
                    SCO.writeObject(GS.getHexes());
                }
                else
                {
                    SCO.writeObject("unknownCommand");
                }
            }
        } catch (Exception e) {
            System.out.println("Error:" + socket);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
            //connectionCount--;
            System.out.println("Closed: " + socket);
        }
    }
}
