package server.boardTools;

import hex.Hex;
import server.Cord;

/**
 * Klasa budująca początkową planszę
 */
public class InitialStateBuilder extends AbstractStateBuilder{
    private int xAxis;
    private int yAxis;
    private AbstractRegionFactory regionFactory;
    private Hex[][] hexes;

    /**
     * Konstruktor
     * @param xAxis liczba kolumn
     * @param yAxis liczba wierszy
     * @param regionFactory fabryka regionów
     */
    public InitialStateBuilder(int xAxis, int yAxis, AbstractRegionFactory regionFactory) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.regionFactory = regionFactory;
    }

    /**
     * Zwraca stworzoną planszę
     * @return plansza o stanie początkowym
     */
    public Hex[][] getHexes() {
        return hexes;
    }

    /**
     * Tworzy pustą planszę
     */
    public void createEmptyBoard(){
        hexes = new Hex[xAxis][yAxis];
        for (int i = 0; i < xAxis; i++)
        {
            for (int j = 0; j < yAxis; j++)
            {
                hexes[i][j] = new Hex(Hex.State.NULL);
            }
        }
    }

    /**
     * Wypełnia zadanym stanem, wybrany region zgodnie z definicją z fabryki regionów
     * @param state stan, którym należy wypełnić region
     * @param region region do wypełnienia
     */
    public void initRegion(Hex.State state, Region region) {
        for (Cord cord : regionFactory.getRegion(region)) {
            hexes[cord.x][cord.y] = new Hex(state);
        }
    }
}
