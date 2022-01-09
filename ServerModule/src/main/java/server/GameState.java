package server;

import hex.Hex;
import server.gui.ServerLogDisplay;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Klasa zawierająca planszę, informacje o stanie rozgrywki.
 */
public class GameState {
    private MoveValidator validator;
    private Hex[][] hexes;
    private int gameId;
    private int place; // które miejsce zostało ostatnio zajęte (ilu graczy już wygrało)
    private boolean gameStarted; // czy do gry podłączyli się wszyscy gracze
    private final int numberOfPlayers;
    private int currentPlayer; // numer gracza ktory ma teraz ture
    private ArrayList<PlayerHandler> players;
    static final int xAxis = 13;
    static final int yAxis = 17;
    ServerLogDisplay serverLogDisplay;

    /**
     * Klasa przechowująca koordynat tabeli hexes
     *
    public class Cord{
        public int x;
        public int y;
        Cord(int x,int y)
        {
            this.x = x;
            this.y = y;
        }
    }*/
    // koordynaty wchodzące w skład poszczególnych trójkątów
    private ArrayList<Cord> Ncords;
    private ArrayList<Cord> Scords;
    private ArrayList<Cord> NWcords;

    public ArrayList<Cord> getNcords() {
        return Ncords;
    }

    public ArrayList<Cord> getScords() {
        return Scords;
    }

    public ArrayList<Cord> getNWcords() {
        return NWcords;
    }

    public ArrayList<Cord> getNEcords() {
        return NEcords;
    }

    public ArrayList<Cord> getSWcords() {
        return SWcords;
    }

    public ArrayList<Cord> getSEcords() {
        return SEcords;
    }

    private ArrayList<Cord> NEcords;
    private ArrayList<Cord> SWcords;
    private ArrayList<Cord> SEcords;
    public GameState(int numberOfPlayers, int id, ServerLogDisplay serverLogDisplay)
    {
        this.validator = new MoveValidator(this);
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
                hexes[i][j] = new Hex(Hex.State.NULL);
            }
        }
        fillNcords();
        fillNEcords();
        fillNWcords();
        fillScords();
        fillSEcords();
        fillSWcords();
        /*hexes[0][0] = new Hex(Hex.State.BLUE);
        hexes[12][16] = new Hex(Hex.State.BLUE);*/
        initNE(Hex.State.EMPTY);
        initNW(Hex.State.EMPTY);
        initSW(Hex.State.EMPTY);
        initSE(Hex.State.EMPTY);
        initCenter(Hex.State.EMPTY);
        initS(Hex.State.EMPTY);
        initN(Hex.State.RED);
        if (numberOfPlayers == 2) {
            initS(Hex.State.BLUE);

        }

        if (numberOfPlayers == 3) {
            initSE(Hex.State.GREEN);
            initSW(Hex.State.BLACK);
        }

        if (numberOfPlayers == 4) {
            initNW(Hex.State.YELLOW);
            initS(Hex.State.BLUE);
            initSE(Hex.State.GREEN);
        }

        if (numberOfPlayers >= 5) {
            initNW(Hex.State.YELLOW);
            initS(Hex.State.BLUE);
            initSE(Hex.State.GREEN);
            initNE(Hex.State.WHITE);
        }

        if (numberOfPlayers == 6) {
            initSW(Hex.State.BLACK);
        }


    }

    /**
     * Uzupełnia tablicę koordynatów północnego trójkąta
     */
    public void fillNcords()
    {
        Ncords = new ArrayList<Cord>();
        Ncords.add(new Cord(6,0));
        Ncords.add(new Cord(5,1));
        Ncords.add(new Cord(6,1));
        Ncords.add(new Cord(5,2));
        Ncords.add(new Cord(6,2));
        Ncords.add(new Cord(7,2));
        Ncords.add(new Cord(4,3));
        Ncords.add(new Cord(5,3));
        Ncords.add(new Cord(6,3));
        Ncords.add(new Cord(7,3));
    }
    public void fillNEcords()
    {
        NEcords = new ArrayList<Cord>();
        NEcords.add(new Cord(9,4));
        NEcords.add(new Cord(10,4));
        NEcords.add(new Cord(11,4));
        NEcords.add(new Cord(12,4));
        NEcords.add(new Cord(9,5));
        NEcords.add(new Cord(10,5));
        NEcords.add(new Cord(11,5));
        NEcords.add(new Cord(10,6));
        NEcords.add(new Cord(11,6));
        NEcords.add(new Cord(10,7));
    }
    public void fillNWcords()
    {
        NWcords = new ArrayList<Cord>();
        NWcords.add(new Cord(0,4));
        NWcords.add(new Cord(1,4));
        NWcords.add(new Cord(2,4));
        NWcords.add(new Cord(3,4));
        NWcords.add(new Cord(0,5));
        NWcords.add(new Cord(1,5));
        NWcords.add(new Cord(2,5));
        NWcords.add(new Cord(1,6));
        NWcords.add(new Cord(2,6));
        NWcords.add(new Cord(1,7));
    }
    public void fillSEcords()
    {

        SEcords = new ArrayList<Cord>();
        SEcords.add(new Cord(9,12));
        SEcords.add(new Cord(10,12));
        SEcords.add(new Cord(11,12));
        SEcords.add(new Cord(12,12));
        SEcords.add(new Cord(9,11));
        SEcords.add(new Cord(10,11));
        SEcords.add(new Cord(11,11));
        SEcords.add(new Cord(10,10));
        SEcords.add(new Cord(11,10));
        SEcords.add(new Cord(10,9));
    }
    public void fillSWcords()
    {
        SWcords = new ArrayList<Cord>();
        SWcords.add(new Cord(0,12));
        SWcords.add(new Cord(1,12));
        SWcords.add(new Cord(2,12));
        SWcords.add(new Cord(3,12));
        SWcords.add(new Cord(0,11));
        SWcords.add(new Cord(1,11));
        SWcords.add(new Cord(2,11));
        SWcords.add(new Cord(1,10));
        SWcords.add(new Cord(2,10));
        SWcords.add(new Cord(1,9));
    }

    public void fillScords()
    {
        Scords = new ArrayList<Cord>();
        Scords.add(new Cord(6,16));
        Scords.add(new Cord(5,15));
        Scords.add(new Cord(6,15));
        Scords.add(new Cord(5,14));
        Scords.add(new Cord(6,14));
        Scords.add(new Cord(7,14));
        Scords.add(new Cord(4,13));
        Scords.add(new Cord(5,13));
        Scords.add(new Cord(6,13));
        Scords.add(new Cord(7,13));
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
        do {
            n++;
            if (n == numberOfPlayers) {
                n = 0;
            }
        }
        while (players.get(n).checkIfWinner());
        currentPlayer=n;
        this.validator.newTurn(Hex.State.valueOf(getCurrentPlayerColorName()));
    }
    public int getCurrentPlayer()
    {
        return currentPlayer;
    }
    public String getCurrentPlayerColorName()
    {
        return players.get(currentPlayer).getColorname();
    }

    public boolean move(int a, int b, int c ,int d)
    {
        if (moveIsLegal(a,b,c,d))
        {
            hexes[c][d].setState(hexes[a][b].getState());
            hexes[a][b].setState(Hex.State.EMPTY);
            return true;
        }
        return false;
    }

    /**
     * TODO: Używa zasad gry aby sprawdzić czy ruch jest legalny
     * @return
     */
    public Boolean moveIsLegal(int a, int b, int c ,int d) {
        return validator.moveIsLegal(a, b, c, d);
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
            writeToAllPlayers("turnChanged" + players.get(0).getColorname());
            this.validator.newTurn(Hex.State.valueOf(getCurrentPlayerColorName()));
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
        for(int i =0; i< Ncords.size(); i++)
        {
            hexes[Ncords.get(i).x][Ncords.get(i).y] = new Hex(state);
        }
    }

    /**
     * init north-east
     * @param state Hex.State
     */
    public void initNE(Hex.State state) {
        for(int i =0; i< NEcords.size(); i++)
        {
            hexes[NEcords.get(i).x][NEcords.get(i).y] = new Hex(state);
        }
    }

    /**
     * init south-east
     * @param state Hex.State
     */
    public void initSE(Hex.State state) {
        for(int i =0; i< SEcords.size(); i++)
        {
            hexes[SEcords.get(i).x][SEcords.get(i).y] = new Hex(state);
        }
    }
    /**
     * init south
     * @param state Hex.State
     */
    public void initS(Hex.State state) {
        for(int i =0; i< Scords.size(); i++)
        {
            hexes[Scords.get(i).x][Scords.get(i).y] = new Hex(state);
        }
    }
    /**
     * init south-west
     * @param state Hex.State
     */
    public void initSW(Hex.State state) {
        for(int i =0; i< SWcords.size(); i++)
        {
            hexes[SWcords.get(i).x][SWcords.get(i).y] = new Hex(state);
        }
    }
    /**
     * init north-west
     * @param state Hex.State
     */
    public void initNW(Hex.State state) {
        for(int i =0; i< NWcords.size(); i++)
        {
            hexes[NWcords.get(i).x][NWcords.get(i).y] = new Hex(state);
        }
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
    boolean checkIfWon(Hex.State player) throws IOException {

        // jeśli player wygrał już wcześniej
        for (int i = 0; i < players.size(); i++)
        {
            if (Objects.equals(players.get(i).getColorname(), player.name()) && players.get(i).checkIfWinner())
            {
                return true;
            }
        }

        boolean verdict = false;
        if (player == Hex.State.RED)
        {
            // czerwony wygrał jeśli South(BLUE) trójkąt jest wypełniony czerwonymi
            for(int i =0; i< Scords.size(); i++)
            {
                if (hexes[Scords.get(i).x][Scords.get(i).y].getState() != Hex.State.RED)
                {
                    return false;
                }
            }
            verdict = true;
        }

        if (player == Hex.State.BLUE)
        {
            // niebieski wygrał jeśli North(RED) trójkąt jest wypełniony niebieskimi
            for(int i =0; i< Ncords.size(); i++)
            {
                if (hexes[Ncords.get(i).x][Ncords.get(i).y].getState() != Hex.State.BLUE)
                {
                    return false;
                }
            }
            verdict = true;
        }
        if (player == Hex.State.YELLOW)
        {
            for(int i =0; i< SEcords.size(); i++)
            {
                if (hexes[SEcords.get(i).x][SEcords.get(i).y].getState() != Hex.State.YELLOW)
                {
                    return false;
                }
            }
            verdict = true;
        }
        if (player == Hex.State.WHITE)
        {
            for(int i =0; i< SWcords.size(); i++)
            {
                if (hexes[SWcords.get(i).x][SWcords.get(i).y].getState() != Hex.State.WHITE)
                {
                    return false;
                }
            }
            verdict = true;
        }
        if (player == Hex.State.BLACK)
        {
            for(int i =0; i< NEcords.size(); i++)
            {
                if (hexes[NEcords.get(i).x][NEcords.get(i).y].getState() != Hex.State.BLACK)
                {
                    return false;
                }
            }
            verdict = true;
        }
        if (player == Hex.State.GREEN)
        {
            for(int i =0; i< NWcords.size(); i++)
            {
                if (hexes[NWcords.get(i).x][NWcords.get(i).y].getState() != Hex.State.GREEN)
                {
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
    public boolean checkIfGameEnded()
    {
        int counter = 0;
        for (int i = 0; i < players.size(); i++)
        {
            if (players.get(i).checkIfWinner())
            {
                counter++;
            }
        }
        if (counter >= numberOfPlayers-1)
        {
            return true;
        }
        return false;
    }

    public ServerLogDisplay getServerLogDisplay() {
        return serverLogDisplay;
    }
}
