package server;

import hex.Hex;
import server.boardTools.*;
import server.gui.ServerLog;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * Klasa zawierająca planszę, informacje o stanie rozgrywki.
 */
public class GameState {
    private final AbstractMoveValidator moveValidator;
    private final AbstractRegionFactory regionFactory;
    private Hex[][] hexes;
    private final int gameId;
    private int place; // które miejsce zostało ostatnio zajęte (ilu graczy już wygrało)
    private boolean gameStarted; // czy do gry podłączyli się wszyscy gracze
    private final int numberOfPlayers;
    private int currentPlayer; // numer gracza ktory ma teraz ture
    private final ArrayList<PlayerHandler> players;
    private ArrayList<ArrayList<String >> writeBuffer; // tutaj lądują wiadomości wysyłane do klientów, którzy jeszcze nie połączyli sie z serwerem
    static final int xAxis = 13;
    static final int yAxis = 17;
    ServerLog serverLog;

    /**
     * Standardowy konstruktor
     * @param numberOfPlayers ilość graczy
     * @param id identyfikator gry
     * @param serverLog log serwera
     * @param validator sprawdzacz ruchów
     * @param factory definicje regionów planszy
     */
    public GameState(int numberOfPlayers, int id, ServerLog serverLog, AbstractMoveValidator validator, AbstractRegionFactory factory)
    {
        this.moveValidator = validator;
        this.moveValidator.setGameState(this);
        this.regionFactory = factory;
        this.moveValidator.setRegionFactory(this.regionFactory);
        this.serverLog = serverLog;
        this.numberOfPlayers = numberOfPlayers;
        this.gameId = id;
        players = new ArrayList<>();
        this.currentPlayer=0;
        initBoard(this.numberOfPlayers, new InitialStateBuilder(xAxis, yAxis, regionFactory), regionFactory);
        gameStarted = false;
        initBuffer();
    }

    /**
     * Klasa tworząca stan początkowy planszy dla zadanej liczby graczy
     * @param numberOfPlayers - liczba graczy, na podstawie której trzeba zbudować różne plansze
     */
    public void initBoard(int numberOfPlayers, AbstractStateBuilder boardBuilder, AbstractRegionFactory regionFactory)
    {
        boardBuilder.createEmptyBoard();
        for (Region region : Region.values()) {
            boardBuilder.initRegion(Hex.State.EMPTY, region);
        }
        switch(numberOfPlayers) {
            case 2 -> {
                boardBuilder.initRegion(Hex.State.RED, Region.NORTH);
                boardBuilder.initRegion(Hex.State.BLUE, Region.SOUTH);
            }
            case 3 -> {
                boardBuilder.initRegion(Hex.State.RED, Region.NORTH);
                boardBuilder.initRegion(Hex.State.GREEN, Region.SOUTHEAST);
                boardBuilder.initRegion(Hex.State.BLACK, Region.SOUTHWEST);
            }
            case 4 -> {
                boardBuilder.initRegion(Hex.State.RED, Region.NORTH);
                boardBuilder.initRegion(Hex.State.BLUE, Region.SOUTH);
                boardBuilder.initRegion(Hex.State.YELLOW, Region.NORTHWEST);
                boardBuilder.initRegion(Hex.State.GREEN, Region.SOUTHEAST);
            }
            case 6 -> {
                boardBuilder.initRegion(Hex.State.RED, Region.NORTH);
                boardBuilder.initRegion(Hex.State.BLUE, Region.SOUTH);
                boardBuilder.initRegion(Hex.State.YELLOW, Region.NORTHWEST);
                boardBuilder.initRegion(Hex.State.GREEN, Region.SOUTHEAST);
                boardBuilder.initRegion(Hex.State.BLACK, Region.SOUTHWEST);
                boardBuilder.initRegion(Hex.State.WHITE, Region.NORTHEAST);
            }
        }
        hexes = boardBuilder.getHexes();
    }

    /**
     * Zwraca tablicę zawierającą stan planszy
     * @return stan planszy
     */
    public Hex[][] getHexes() {
        return hexes;
    }

    /**
     * Sprawdza, czy teraz jest tura gracza, który używa danego socket
     * @param socket Socket gracza
     * @return true- jeśli jest tura danego gracza, false- w p. wypadku
     */
    public Boolean checkTurn(Socket socket)
    {
        return currentPlayer == getPlayerNumber(socket);
    }

    /**
     * Zwraca numer gracza o danym Socket
     * @param socket Socket gracza
     * @return number gracza
     */
    public int getPlayerNumber(Socket socket)
    {
        for (int i =0; i < players.size(); i++) {
            if (players.get(i).getSocket() == socket)
            {
                return i;
            }
        }
        return -1;
    }

    /**
     * Funkcja ustawiająca currentPlayer na następnego gracza zgodnie z ruchem wskazówek zegara
     * @param socket socket currentPlayer'a
     */
    public void passTurn(Socket socket)
    {
        int n = getPlayerNumber(socket);
        do {
            n++;
            if (n == numberOfPlayers) {
                n = 0;
            }
        }
        while (players.get(n).checkIfWinner() || players.get(n).checkIfLeft());
        currentPlayer=n;
        this.moveValidator.newTurn(Hex.State.valueOf(getCurrentPlayerColorName()));
    }

    /**
     * Zwraca numer gracza, którego teraz jest tura
     * @return numer gracza, którego teraz jest tura
     */
    public int getCurrentPlayer()
    {
        return currentPlayer;
    }

    /**
     * Zwarca nazwę koloru gracza, którego teraz jest tura
     * @return nazwa koloru pionka gracza, którego teraz jest tura
     */
    public String getCurrentPlayerColorName()
    {
        return players.get(currentPlayer).getColorname();
    }

    /**
     * Metoda przesuwająca pionek (a,b) -> (c,d), jeśli ruch ten jest zgodny z zasadami
     * @param a x początkowe
     * @param b y początkowe
     * @param c x końcowe
     * @param d y końcowe
     * @return true -> gdy ruch wykonany, false -> w przeciwnym wypadku
     */
    public boolean move(int a, int b, int c ,int d)
    {
        if (moveValidator.moveIsLegal(a, b, c, d))
        {
            hexes[c][d].setState(hexes[a][b].getState());
            hexes[a][b].setState(Hex.State.EMPTY);
            return true;
        }
        return false;
    }

    /**
     * Metoda wysyłająca komendy do klientów. Przechowuje komendy, jeśli jeszcze nie wszyscy gracze się połączyli
     * @param s komenda
     * @throws IOException input/output exception przy uzywaniu write
     */
    public void writeToAllPlayers(String s) throws IOException {

        for (int i = 0; i < players.size(); i++)
        {
            /*for (int j = 0; j < writeBuffer.get(i).size(); j++)
            {
                players.get(i).write(writeBuffer.get(i).get(j));
            }*/
            players.get(i).write(s);
        }
        for (int i = players.size(); i < numberOfPlayers; i++)
        {
            writeBuffer.get(i).add(s);
        }
    }

    /**
     * Wyślij wiadomości z buffer do gracza o kolorze pegs
     * @param pegs - kolor dla którego trzeba aktywować writeBuffer
     */
    public void activateWriteBuffer(Hex.State pegs) throws IOException {
        for (int i = 0; i < players.size(); i++)
        {
            if (Objects.equals(players.get(i).getColorname(), pegs.name())) {
            for (int j = 0; j < writeBuffer.get(i).size(); j++) {
                players.get(i).write(writeBuffer.get(i).get(j));
            }
        }

        }
    }

    /**
     * Dodaje gracza do tablicy graczy w gamestate
     * @param p PlayerHandler do dodania
     */
    public void addPlayer(PlayerHandler p) throws IOException {
        players.add(p);
        if (players.size() == numberOfPlayers)
        {
            gameStarted = true;
            ArrayList<Integer> activePlayers = new ArrayList<Integer>();
            log("All players are connected. The game started.");
            // szukamy pierwszego gracza który jeszcze nie wyszedł
            for (int i = 0; i< players.size(); i++)
            {
                if (!players.get(i).checkIfLeft()) {
                    activePlayers.add(i);
                }
            }
            currentPlayer= activePlayers.get(new Random().nextInt(activePlayers.size()));
            writeToAllPlayers("turnChanged" + players.get(currentPlayer).getColorname());
            this.moveValidator.newTurn(Hex.State.valueOf(getCurrentPlayerColorName()));
        }
    }

    /**
     * Zwraca stan rozpoczęcia gry
     * @return true jeśli gra się zaczęła, false- w przeciwnym wypadku
     */
    public boolean getGameStarted()
    {
        return gameStarted;
    }

    /**
     * Zwraca id gry
     * @return id gry
     */
    public int getGameId()
    {
        return gameId;
    }

    /**
     * Dodaj wpis do loggera
     * @param m wpis
     */
    public void log(String m)
    {
        serverLog.log(m);
    }

    /**
     * Sprawdza, czy dany gracz zwyciężył
     * @param player gracz, o którego pytamy
     * @return true-gracz jest zwycięzcą, false-gracz nie jest zwycięzcą
     * @throws IOException I/O Exception
     */
    boolean checkIfWon(Hex.State player) throws IOException {

        // jeśli player wygrał już wcześniej
        for (PlayerHandler playerHandler : players) {
            if (Objects.equals(playerHandler.getColorname(), player.name()) && playerHandler.checkIfWinner()) {
                return true;
            }
        }

        boolean verdict = false;
        if (player == Hex.State.RED)
        {
            // czerwony wygrał jeśli South(BLUE) trójkąt jest wypełniony czerwonymi
            for (Cord scord : regionFactory.getRegion(Region.SOUTH)) {
                if (hexes[scord.x][scord.y].getState() != Hex.State.RED) {
                    return false;
                }
            }
            verdict = true;
        }

        if (player == Hex.State.BLUE)
        {
            // niebieski wygrał jeśli North(RED) trójkąt jest wypełniony niebieskimi
            for (Cord ncord : regionFactory.getRegion(Region.NORTH)) {
                if (hexes[ncord.x][ncord.y].getState() != Hex.State.BLUE) {
                    return false;
                }
            }
            verdict = true;
        }
        if (player == Hex.State.YELLOW)
        {
            for (Cord sEcord : regionFactory.getRegion(Region.SOUTHEAST)) {
                if (hexes[sEcord.x][sEcord.y].getState() != Hex.State.YELLOW) {
                    return false;
                }
            }
            verdict = true;
        }
        if (player == Hex.State.WHITE)
        {
            for (Cord sWcord : regionFactory.getRegion(Region.SOUTHWEST)) {
                if (hexes[sWcord.x][sWcord.y].getState() != Hex.State.WHITE) {
                    return false;
                }
            }
            verdict = true;
        }
        if (player == Hex.State.BLACK)
        {
            for (Cord nEcord : regionFactory.getRegion(Region.NORTHEAST)) {
                if (hexes[nEcord.x][nEcord.y].getState() != Hex.State.BLACK) {
                    return false;
                }
            }
            verdict = true;
        }
        if (player == Hex.State.GREEN)
        {
            for (Cord nWcord : regionFactory.getRegion(Region.NORTHWEST)) {
                if (hexes[nWcord.x][nWcord.y].getState() != Hex.State.GREEN) {
                    return false;
                }
            }
            verdict = true;
        }
        if (verdict)
        {
            place++;
            writeToAllPlayers("won" + player.name() + place);
            return true;
        }
        else
            return false;
    }

    /**
     * Zlicza graczy, którzy opuścili grę lub wygrali. Jeśli tylko jeden gracz gra dalej, można zakończyć grę.
     * @return true- jeśli można zakończyć grę, false- w przeciwnym wypadku
     */
    public boolean checkIfGameEnded()
    {
        int counter = 0;
        for (PlayerHandler player : players) {
            if (player.checkIfWinner() || player.checkIfLeft()) {
                counter++;
            }
        }
        System.out.println(counter + " " + numberOfPlayers);
        return counter >= numberOfPlayers - 1;
    }

    /**
     * Inicjuje buffer do przechowywania komend dla graczy, którzy jeszcze się nie połączyli
     */
    public void initBuffer()
    {
        writeBuffer = new ArrayList<>();
        for (int i=0; i < numberOfPlayers; i++)
        {
            writeBuffer.add(new ArrayList<String>());
        }
    }

    /**
     * Zwraca element, na którym wypisywane są logi serwera
     * @return element, na którym wypisywane są logi serwera
     */
    public ServerLog getServerLog() {
        return serverLog;
    }
}
