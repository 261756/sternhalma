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
    private Hex.State pegsColor;
    private boolean winner;
    private boolean left; // czy gracz wyszedł z gry zanim wygrał
    private boolean addedToGS;

    PlayerHandler(Socket socket, GameState gs, Hex.State pegs) throws IOException {
        winner = false;
        left = false;
        addedToGS=false;
        this.socket = socket;
        this.GS = gs;
        pegsColor = pegs;
        SCO = new ServerCommunicatorOut(socket.getOutputStream(),GS.getServerLogDisplay(), this);
        SCI = new ServerCommunicatorIn(socket.getInputStream(), GS.getServerLogDisplay(),this);
    }

    @Override
    public void run() {
        GS.log("Connected " + pegsColor.name() + " to game " + GS.getGameId() + ": " + socket );
        try {
            SCO.writeString("assignColor"+pegsColor.name());

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while (SCI.availableCommandFromClient()) {
                String command = SCI.getCommandFromClient();
                if (GS.checkIfGameEnded())
                {
                    continue;
                }
                else if (command.equals("requestHexes"))
                {
                    SCO.writeString("sendingHexes" + new BoardAndString(GS.getHexes()).getStringValue());
                    if (!addedToGS) {
                        GS.addPlayer(this);
                        addedToGS=true;
                        GS.activateWriteBuffer(this.pegsColor);
                    }
                    if (!winner && GS.checkIfWon(this.pegsColor))
                    {
                        winner = true;
                        GS.log(pegsColor + " won!");
                        if (GS.checkIfGameEnded())
                        {
                            writeToAllPlayers("gameEnded");
                        }
                    }
                }
                else if (command.startsWith("requestMove"))
                {
                    if (GS.checkTurn(socket) && GS.getGameStarted() == true) {
                        if (handleMove(command)) {
                            writeToAllPlayers("moveMade");
                        }
                    }
                }
                else if (command.equals("passTurn"))
                {
                    if (GS.checkTurn(socket) && GS.getGameStarted() == true) {
                        GS.passTurn(socket);
                        GS.serverLogDisplay.log("Turn of player: " + GS.getCurrentPlayer() + ", " + GS.getCurrentPlayerColorName());
                        writeToAllPlayers("turnChanged" + GS.getCurrentPlayerColorName());
                    }
                }
                else if (command.equals("quit"))
                {
                    GS.log(GS.getCurrentPlayerColorName() +" quit.");

                }
                else
                {
                    SCO.writeString("unknownCommand");
                    //logOut("unknownCommand");
                }
            }
        } catch (Exception e) {
            GS.log("Error:" + socket);
        } finally {
            try {
                socket.close();
                left=true;
                if (GS.checkTurn(socket) && GS.getGameStarted() == true) {
                    GS.passTurn(socket);
                    GS.serverLogDisplay.log("Turn of player: " + GS.getCurrentPlayer() + ", " + GS.getCurrentPlayerColorName());
                    writeToAllPlayers("turnChanged" + GS.getCurrentPlayerColorName());
                }
                if (GS.checkIfGameEnded())
                {
                    writeToAllPlayers("gameEnded");
                }
                writeToAllPlayers("left" + pegsColor.name());
            } catch (IOException e) {
            }

            GS.log("Disconnected "+ pegsColor.name() + " from game " + GS.getGameId() + ": " + socket);
        }
    }
    private boolean handleMove(String command)
    {
        String[] parts = command.substring(11).split(" ");
        int x1 = Integer.parseInt(parts[0]);
        int y1 = Integer.parseInt(parts[1]);
        int x2 = Integer.parseInt(parts[2]);
        int y2 = Integer.parseInt(parts[3]);
        return GS.move(x1,y1,x2,y2);
    }
    private void writeToAllPlayers(String command) throws IOException {
        GS.writeToAllPlayers(command);
    }
    public void write(String s) throws IOException {
        SCO.writeString(s);
    }
    /*public void logIn(String msg)
    {
        GS.log("Received from Game " + GS.getGameId() + ", " + pegsColor.name() + ": "+ msg);
    }
    public void logOut(String msg){
        GS.log("Sent to Game " + GS.getGameId() + ", " + pegsColor.name() + ": " + msg);
    }
    public void logOutAll(String msg)
    {

        GS.log("Sent to Game " + GS.getGameId() + ", [ALL COLORS]: " + msg);

    }*/
    public int getGameId()
    {
        return GS.getGameId();
    }
    public String getColorname()
    {
        return pegsColor.name();
    }
    public Socket getSocket() {
        return socket;
    }
    public boolean checkIfWinner()
    {
        return winner;
    }
    public boolean checkIfLeft()
    {
        return left;
    }



}
