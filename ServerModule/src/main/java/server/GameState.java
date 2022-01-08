package server;

import hex.Hex;
import server.gui.ServerLogDisplay;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Klasa zawierająca planszę, informacje o stanie rozgrywki.
 */
public class GameState {
    private Hex[][] hexes;
    private int gameId;
    private boolean gameStarted; // czy do gry podłączyli się wszyscy gracze
    private final int numberOfPlayers;
    private int currentPlayer; // numer gracza ktory ma teraz ture
    private ArrayList<PlayerHandler> players;
    static final int xAxis = 13;
    static final int yAxis = 17;
    ServerLogDisplay serverLogDisplay;
    GameState(int numberOfPlayers, int id, ServerLogDisplay serverLogDisplay)
    {
        this.serverLogDisplay = serverLogDisplay;
        this.numberOfPlayers = numberOfPlayers;
        this.gameId = id;
        hexes = new Hex[xAxis][yAxis];
        players = new ArrayList<PlayerHandler>();
        this.currentPlayer=0;
        initBoard(this.numberOfPlayers);
        gameStarted = false;
    }

    /**
     * TODO: budowanie prawdziwej gwiazdy dawida, teraz jest placeholder
     * @param numberOfPlayers - liczba graczy na podstawie ktorej trzeba zbudowac różne plansze
     */
    public void initBoard(int numberOfPlayers)
    {
        for (int i = 0; i < xAxis; i++)
        {
            for (int j = 0; j < yAxis; j++)
            {
                //hexes[i][j] = new Hex(Hex.State.EMPTY);
                hexes[i][j] = new Hex(Hex.State.NULL);
            }
        }
        /*hexes[0][0] = new Hex(Hex.State.BLUE);
        hexes[12][16] = new Hex(Hex.State.BLUE);*/
        initN(Hex.State.RED);
        initS(Hex.State.BLUE);

        if (numberOfPlayers >= 3)
            initNE(Hex.State.WHITE);
        else
            initNE(Hex.State.EMPTY);
        if (numberOfPlayers >= 4)
            initSW(Hex.State.BLACK);
        else
            initSW(Hex.State.EMPTY);
        if (numberOfPlayers >= 5)
            initSE(Hex.State.GREEN);
        else
            initSE(Hex.State.EMPTY);
        if (numberOfPlayers >= 6)
            initNW(Hex.State.YELLOW);
        else
            initNW(Hex.State.EMPTY);
        initCenter(Hex.State.EMPTY);

    }

    public Hex[][] getHexes() {
        return hexes;
    }

    /**
     * TODO: Sprawdza czy teraz jest tura gracza który używa danego socket
     * @param socket
     * @return
     */
    public Boolean checkTurn(Socket socket)
    {
        if (currentPlayer == getPlayerNumber(socket)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param socket
     * @return return number of player with socket
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
     * TODO: Pause turę gracza socket
     * @param socket
     */
    public void passTurn(Socket socket)
    {
        int n = getPlayerNumber(socket);
        n++;
        if (n == numberOfPlayers)
        {
            n=0;
        }
        currentPlayer=n;
    }
    public int getCurrentPlayer()
    {
        return currentPlayer;
    }
    public String getCurrentPlayerColorName()
    {
        return players.get(currentPlayer).getColorname();
    }

    public void move(int a, int b, int c ,int d)
    {
        if (moveIsLegal(a,b,c,d))
        {

            hexes[c][d].setState(hexes[a][b].getState());
            hexes[a][b].setState(Hex.State.EMPTY);

        }
    }

    /**
     * TODO: Używa zasad gry aby sprawdzić czy ruch jest legalny
     * @return
     */
    public Boolean moveIsLegal(int a, int b, int c ,int d) {
        return true;
    }

    public void writeToAllPlayers(String s) throws IOException {
        for (int i = 0; i < players.size(); i++)
        {
            players.get(i).write(s);
        }
    }

    /**
     * Dodaje gracza do tablicy graczy w gamestate
     * @param p
     */
    public void addPlayer(PlayerHandler p) throws IOException {
        players.add(p);
        if (players.size() == numberOfPlayers)
        {
            gameStarted = true;
            log("All players are connected. The game started.");
            writeToAllPlayers("turnChanged");
            writeToAllPlayers(players.get(0).getColorname());
        }
    }
    public boolean getGameStarted()
    {
        return gameStarted;
    }

    /**
     * init north
     * @param state Hex.State
     */
    public void initN(Hex.State state) {
        hexes[6][0] = new Hex(state);
        hexes[5][1] = new Hex(state);
        hexes[6][1] = new Hex(state);
        hexes[5][2] = new Hex(state);
        hexes[6][2] = new Hex(state);
        hexes[7][2] = new Hex(state);
        hexes[4][3] = new Hex(state);
        hexes[5][3] = new Hex(state);
        hexes[6][3] = new Hex(state);
        hexes[7][3] = new Hex(state);
    }

    /**
     * init north-east
     * @param state Hex.State
     */
    public void initNE(Hex.State state) {
        hexes[9][4] = new Hex(state);
        hexes[10][4] = new Hex(state);
        hexes[11][4] = new Hex(state);
        hexes[12][4] = new Hex(state);
        hexes[9][5] = new Hex(state);
        hexes[10][5] = new Hex(state);
        hexes[11][5] = new Hex(state);
        hexes[10][6] = new Hex(state);
        hexes[11][6] = new Hex(state);
        hexes[10][7] = new Hex(state);
    }

    /**
     * init south-east
     * @param state Hex.State
     */
    public void initSE(Hex.State state) {
        hexes[9][12] = new Hex(state);
        hexes[10][12] = new Hex(state);
        hexes[11][12] = new Hex(state);
        hexes[12][12] = new Hex(state);
        hexes[9][11] = new Hex(state);
        hexes[10][11] = new Hex(state);
        hexes[11][11] = new Hex(state);
        hexes[10][10] = new Hex(state);
        hexes[11][10] = new Hex(state);
        hexes[10][9] = new Hex(state);
    }
    /**
     * init south
     * @param state Hex.State
     */
    public void initS(Hex.State state) {
        hexes[6][16] = new Hex(state);
        hexes[5][15] = new Hex(state);
        hexes[6][15] = new Hex(state);
        hexes[5][14] = new Hex(state);
        hexes[6][14] = new Hex(state);
        hexes[7][14] = new Hex(state);
        hexes[4][13] = new Hex(state);
        hexes[5][13] = new Hex(state);
        hexes[6][13] = new Hex(state);
        hexes[7][13] = new Hex(state);
    }
    /**
     * init south-west
     * @param state Hex.State
     */
    public void initSW(Hex.State state) {
        hexes[0][12] = new Hex(state);
        hexes[1][12] = new Hex(state);
        hexes[2][12] = new Hex(state);
        hexes[3][12] = new Hex(state);
        hexes[0][11] = new Hex(state);
        hexes[1][11] = new Hex(state);
        hexes[2][11] = new Hex(state);
        hexes[1][10] = new Hex(state);
        hexes[2][10] = new Hex(state);
        hexes[1][9] = new Hex(state);
    }
    /**
     * init north-west
     * @param state Hex.State
     */
    public void initNW(Hex.State state) {
        hexes[0][4] = new Hex(state);
        hexes[1][4] = new Hex(state);
        hexes[2][4] = new Hex(state);
        hexes[3][4] = new Hex(state);
        hexes[0][5] = new Hex(state);
        hexes[1][5] = new Hex(state);
        hexes[2][5] = new Hex(state);
        hexes[1][6] = new Hex(state);
        hexes[2][6] = new Hex(state);
        hexes[1][7] = new Hex(state);
    }

    /**
     * init center
     * @param state Hex.State
     */
    public void initCenter(Hex.State state) {
        for (int i = 0; i < 5; i++) {
            hexes[4+i][4] = new Hex(state);
            hexes[4+i][12] = new Hex(state);
        }
        for (int i = 0; i < 6; i++) {
            hexes[3+i][5] = new Hex(state);
            hexes[3+i][11] = new Hex(state);
        }
        for (int i = 0; i < 7; i++) {
            hexes[3+i][6] = new Hex(state);
            hexes[3+i][10] = new Hex(state);
        }
        for (int i = 0; i < 8; i++) {
            hexes[2+i][7] = new Hex(state);
            hexes[2+i][9] = new Hex(state);
        }
        for (int i = 0; i < 9; i++) {
            hexes[2+i][8] = new Hex(state);
        }
    }
    public int getGameId()
    {
        return gameId;
    }

    public void log(String m)
    {
        serverLogDisplay.log(m);
    }

    public ServerLogDisplay getServerLogDisplay() {
        return serverLogDisplay;
    }
}
