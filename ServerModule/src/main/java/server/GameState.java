package server;

import hex.Hex;

import java.util.Arrays;

/**
 * Klasa zawierająca planszę, informacje o stanie rozgrywi
 */
public class GameState {
    private Hex[][] hexes;
    GameState()
    {
        hexes = new Hex[17][17];
        initBoard(2);
    }

    /**
     * TODO: budowanie prawdziwej gwiazdy dawida, teraz jest placeholder
     * @param numberOfPlayers - liczba graczy na podstawie ktorej trzeba zbudowac różne plansze
     */
    public void initBoard(int numberOfPlayers)
    {
        for (int i = 0; i < 17; i++)
        {
            for (int j = 0; j < 17; j++)
            {
                hexes[i][j] = new Hex(Hex.State.EMPTY);
            }
        }
        hexes[0][0] = new Hex(Hex.State.PLAYER1);
    }

    public Hex[][] getHexes() {
        return hexes;
    }
}
