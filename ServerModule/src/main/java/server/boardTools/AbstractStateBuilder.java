package server.boardTools;

import hex.Hex;

public abstract class AbstractStateBuilder {
    public abstract Hex[][] getHexes();
    public abstract void createEmptyBoard();
    public abstract void initRegion(Hex.State state, Region region);
}

