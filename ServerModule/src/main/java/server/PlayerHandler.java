package server;

import hex.BoardAndString;
import hex.Hex;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * każdy klient dostanie swój własny playerHandler (testowane connectionCount)
 * playerHandelr umiera kiedy jego klient się rozłączy także lepiej tu nie przechowywać informacji
 *
 * OBSŁUGIWANE KOMENDY:
 * requestHexes: odpowiada "sendingHexes", wysyła tablicę Hexów
 * requestMove: wykonuje zadany przez gracza ruch, zwraca każdemu graczowi moveMade
 * passTurn: pasuje turę gracza
 *
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
    private int connectionCount;
    private Hex.State pegsColor;

    PlayerHandler(Socket socket, GameState gs, Hex.State pegs) throws IOException {

        this.socket = socket;
        this.GS = gs;
        pegsColor = pegs;
        gs.addPlayer(this);
        SCO = new ServerCommunicatorOut(socket.getOutputStream());
        SCI = new ServerCommunicatorIn(socket.getInputStream());
        connectionCount = 0;
    }

    @Override
    public void run() {
        GS.log("Connected " + pegsColor.name() + " to game " + GS.getGameId() + ": " + socket );
        try {
            SCO.writeString("assignColor"+pegsColor.name());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //connectionCount++;
        //System.out.println("Connection count: " + connectionCount);
        try {
            while (SCI.availableCommandFromClient()) {
                String command = SCI.getCommandFromClient();
                GS.log(command);
                if (command.equals("requestHexes"))
                {
                    SCO.writeString("sendingHexes");
                    SCO.writeString(new BoardAndString(GS.getHexes()).getStringValue());
                }
                else if (command.startsWith("requestMove"))
                {
                    handleMove(command);
                    writeToAllPlayers("moveMade");
                    GS.log("moved");
                }
                else if (command.equals("passTurn"))
                {
                    if (GS.checkTurn(socket))
                        GS.passTurn(socket);
                }
                else if (command.equals("quit"))
                {
                    GS.log(socket + "quit");
                }
                else
                {
                    SCO.writeString("unknownCommand");
                }
            }
        } catch (Exception e) {
            GS.log("Error:" + socket);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
            //connectionCount--;
            GS.log("Disconnected "+ pegsColor.name() + " from game " + GS.getGameId() + ": " + socket);
        }
    }
    private void handleMove(String command)
    {
        String[] parts = command.substring(11).split(" ");
        int x1 = Integer.parseInt(parts[0]);
        int y1 = Integer.parseInt(parts[1]);
        int x2 = Integer.parseInt(parts[2]);
        int y2 = Integer.parseInt(parts[3]);
        GS.move(x1,y1,x2,y2);
    }
    private void writeToAllPlayers(String command) throws IOException {
        GS.writeToAllPlayers(command);
    }
    public void write(String s) throws IOException {
        SCO.writeString(s);
    }

}
