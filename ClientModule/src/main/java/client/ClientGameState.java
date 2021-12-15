package client;

import hex.Hex;

/**
 * Stan planszy
 * TODO:
 */
public class ClientGameState {
    Hex[][] hexes;
    public void setHexes(Hex[][] hexes) {
        this.hexes = hexes;
    }
    public Hex getHexAt(int x, int y) {
        return hexes[x][y];
    }
}
