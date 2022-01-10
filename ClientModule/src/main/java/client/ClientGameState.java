package client;

import hex.Hex;

/**
 * Stan planszy na kliencie
 */
public class ClientGameState {
    /**
     * Stan planszy
     */
    Hex[][] hexes;
    final static int xAxis = 13;
    final static int yAxis = 17;

    /**
     * Konstruktor
     */
    public ClientGameState() {
        this.hexes = new Hex[xAxis][yAxis];
    }

    /**
     * Ustawia stan planszy na zadany
     * @param hexes zadany stan planszy
     */
    public void setHexes(Hex[][] hexes) {
        this.hexes = hexes;
    }

    /**
     * Zwraca Hex z danego adresu
     * @param x kolumna
     * @param y wiersz
     * @return Hex z adresu (x,y)
     */
    public Hex getHexAt(int x, int y) {
        return hexes[x][y];
    }
}
