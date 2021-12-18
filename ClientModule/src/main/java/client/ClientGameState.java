package client;

import hex.Hex;

/**
 * Stan planszy
 * TODO:
 */
public class ClientGameState {
    Hex[][] hexes;
    final static int xAxis = 13;
    final static int yAxis = 17;
    public ClientGameState() {
        this.hexes = new Hex[xAxis][yAxis];
    }
    public void setHexes(Hex[][] hexes) {
        this.hexes = hexes;
    }

    public Hex getHexAt(int x, int y) {
        return hexes[x][y];
    }
}
