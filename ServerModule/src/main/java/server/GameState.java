package server;

import hex.Hex;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Klasa zawierająca planszę, informacje o stanie rozgrywi
 */
public class GameState {
    private Hex[][] hexes;
    private ArrayList<PlayerHandler> players;
    static final int xAxis = 13;
    static final int yAxis = 17;
    GameState()
    {
        hexes = new Hex[xAxis][yAxis];
        players = new ArrayList<PlayerHandler>();
        initBoard(2);
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
                hexes[i][j] = new Hex(Hex.State.EMPTY);
            }
        }
        hexes[0][0] = new Hex(Hex.State.PLAYER2);
        hexes[12][16] = new Hex(Hex.State.PLAYER2);
        hexes[6][0] = new Hex(Hex.State.PLAYER1);
        hexes[6][1] = new Hex(Hex.State.PLAYER1);
        hexes[7][1] = new Hex(Hex.State.PLAYER1);
        hexes[5][2] = new Hex(Hex.State.PLAYER1);
        hexes[6][2] = new Hex(Hex.State.PLAYER1);
        hexes[7][2] = new Hex(Hex.State.PLAYER1);
        hexes[5][3] = new Hex(Hex.State.PLAYER1);
        hexes[6][3] = new Hex(Hex.State.PLAYER1);
        hexes[7][3] = new Hex(Hex.State.PLAYER1);
        hexes[8][3] = new Hex(Hex.State.PLAYER1);
        hexes[0][4] = new Hex(Hex.State.PLAYER1);
        hexes[1][4] = new Hex(Hex.State.PLAYER1);
        hexes[2][4] = new Hex(Hex.State.PLAYER1);
        hexes[3][4] = new Hex(Hex.State.PLAYER1);
        hexes[4][4] = new Hex(Hex.State.PLAYER1);
        hexes[5][4] = new Hex(Hex.State.PLAYER1);
        hexes[6][4] = new Hex(Hex.State.PLAYER1);
        hexes[7][4] = new Hex(Hex.State.PLAYER1);
        hexes[8][4] = new Hex(Hex.State.PLAYER1);
        hexes[9][4] = new Hex(Hex.State.PLAYER1);
        hexes[10][4] = new Hex(Hex.State.PLAYER1);
        hexes[11][4] = new Hex(Hex.State.PLAYER1);
        hexes[12][4] = new Hex(Hex.State.PLAYER1);
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
        return true;
    }

    /**
     * TODO: Pause turę gracza socket
     * @param socket
     */
    public void passTurn(Socket socket)
    {

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
    public void addPlayer(PlayerHandler p)
    {
        players.add(p);
    }

}
