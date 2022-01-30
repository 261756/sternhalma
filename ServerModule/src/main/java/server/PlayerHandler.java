package server;

import hex.BoardAndString;
import hex.Hex;
import server.boardTools.InitialStateBuilder;
import server.boardTools.RegionFactory;
import server.boardTools.ReplayStateCreator;
import server.sql.QuerySQL;

import java.io.IOException;
import java.net.Socket;


/**
 * każdy klient dostanie swój własny playerHandler (testowane connectionCount)
 * playerHandelr umiera kiedy jego klient się rozłączy także lepiej tu nie przechowywać informacji
 *
 * OBSŁUGIWANE KOMENDY:
 * requestHexes: odpowiada "sendingHexes", wysyła tablicę Hexów
 * requestMove: wykonuje zadany przez gracza ruch, zwraca każdemu graczowi moveMade,
 * wysyła graczom powiadomienie o zwycięstwie któregoś z graczy
 * passTurn: pasuje turę gracza, wysyła Turn changed do graczy
 * quit: wysyła do wszystkich graczy informacje o opuszczeniu gry przez danego graca
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
    private ReplayStateCreator creator;
    /**
     * Konstruktor wątku handlara gracza
     * @param socket socket gracza
     * @param gs stan gry
     * @param pegs kolor pionków gracza
     * @throws IOException błąd połączenia
     */
    PlayerHandler(Socket socket, GameState gs, Hex.State pegs) throws IOException {
        winner = false;
        left = false;
        addedToGS=false;
        this.socket = socket;
        this.GS = gs;
        pegsColor = pegs;
        SCO = new ServerCommunicatorOut(socket.getOutputStream(),GS.getServerLog(), this);
        SCI = new ServerCommunicatorIn(socket.getInputStream(), GS.getServerLog(),this);
    }

    /**
     * Główna metoda wątku. Przyjmuje i wysyła odpowiednie komendy
     */
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
                if (command.startsWith("requestPastGame"))
                {
                    creator = new ReplayStateCreator(new InitialStateBuilder(13,17, new RegionFactory()),
                            Integer.parseInt(command.substring(15)));
                }
                else if (command.startsWith("requestPastHexes"))
                {
                    if (creator.setState(Integer.parseInt(command.substring(16)))) {
                        SCO.writeString("sendingHexes" + BoardAndString.getStringValue(creator.getHexes()));
                    } else {
                        SCO.writeString("message" + "Koniec powtórki");
                    }

                }
                else if (command.equals("requestGames")) {
                    writeToAllPlayers("gameList" + QuerySQL.getGameList());
                }
                else if (GS.checkIfGameEnded())
                {
                    continue;
                }
                else if (command.equals("requestHexes"))
                {
                    SCO.writeString("sendingHexes" + BoardAndString.getStringValue(GS.getHexes()));
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
                            //writeToAllPlayers("gameEnded" + QuerySQL.getGameList());
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
                        GS.serverLog.log("Turn of player: " + GS.getCurrentPlayer() + ", " + GS.getCurrentPlayerColorName());
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
                    GS.serverLog.log("Turn of player: " + GS.getCurrentPlayer() + ", " + GS.getCurrentPlayerColorName());
                    writeToAllPlayers("turnChanged" + GS.getCurrentPlayerColorName());
                }
                if (GS.checkIfGameEnded())
                {
                    writeToAllPlayers("gameEnded");
                    //writeToAllPlayers("gameEnded" + QuerySQL.getGameList());
                }
                writeToAllPlayers("left" + pegsColor.name());
            } catch (IOException e) {
                System.out.println("I/O Exception");
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

    /**
     * Wysyła wiadomość do klienta
     * @param s wiadomość
     * @throws IOException błąd połączenia
     */
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

    /**
     * zwraca id gry
     * @return id gry
     */
    public int getGameId()
    {
        return GS.getGameId();
    }

    /**
     * Zwraca kolor gracza
     * @return nazwa koloru gracza
     */
    public String getColorname()
    {
        return pegsColor.name();
    }

    /**
     * Zwraca socket gracza
     * @return socket gracza
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * Zwraca czy gracz wygrał
     * @return true, jeśli gracz wygrał
     */
    public boolean checkIfWinner()
    {
        return winner;
    }

    /**
     * Zwraca czy gracz wyszedł z gry, zanim wygrał
     * @return true, jeśli gracz wyszedł z gry, zanim wygrał
     */
    public boolean checkIfLeft()
    {
        return left;
    }



}
