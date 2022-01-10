package server.boardTools;

import hex.Hex;

/**
 * Builder początkowego stanu gry
 */
public abstract class AbstractStateBuilder {
    /**
     * Zwraca stworzoną planszę
     * @return stworzona plansza
     */
    public abstract Hex[][] getHexes();

    /**
     * Tworzy pustą planszę
     */
    public abstract void createEmptyBoard();

    /**
     * Wypełnia region Hexami danego stanu
     * @param state stan, jaki mają mieć pola w tym regionie
     * @param region region, który ma być wypełniony
     */
    public abstract void initRegion(Hex.State state, Region region);
}

