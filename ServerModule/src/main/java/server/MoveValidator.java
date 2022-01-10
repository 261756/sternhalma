package server;

import hex.Hex;
import server.boardTools.AbstractRegionFactory;
import server.boardTools.Region;

import java.util.ArrayList;

/**
 * Klasa zawierająca zasady poruszania się po planszy
 * Ruch polega na przesunięciu jednego piona na turę na jeden z dwóch sposobów:
 * 1) przesunięcie na sąsiednie pole, po takim ruchu nie można już się ruszyć;
 * 2) przeskakiwanie na wprost innego piona, tyle razy ile jest to możliwe.
 * W jednej turze nie można użyć obu sposobów.
 * Ponadto, jeśli pionek znajdzie się w obszarze docelowym (róg naprzeciw rogu startowego),
 * wówczas ten pionek może wykonywać ruchy tylko w obrębie tego obszaru
 */
public class MoveValidator extends AbstractMoveValidator {
    private GameState gameState;
    private AbstractRegionFactory regionFactory;
    private ArrayList<Cord> objective;
    private boolean firstMove;
    private boolean turnContinue;
    private int lastX;
    private int lastY;

    /**
     * Metoda zmieniająca konfigurację sprawdzacza dla następnego gracza, na początku jego tury
     * @param color kolor gracza
     */
    @Override
    public void newTurn(Hex.State color) {
        this.turnContinue = true;
        this.firstMove = true;
        setObjective(color);
    }

    @Override
    public void setGameState(GameState game) {
        this.gameState = game;
    }

    @Override
    public void setRegionFactory(AbstractRegionFactory factory) {
        this.regionFactory = factory;
    }

    /**
     * Metoda sprawdzająca, czy ruch (a,b) -> (c,d) jest zgodny z zasadami
     * @param a x początkowe
     * @param b y początkowe
     * @param c x końcowe
     * @param d y końcowe
     * @return true -> gdy ruch poprawny, false -> w przeciwnym wypadku
     */
    @Override
    public boolean moveIsLegal(int a, int b, int c, int d) {
        if (isInsideObjective(a,b) && !isInsideObjective(c,d)) {
            return false;
        }

        if (firstMove && walkHex(a, b, c, d)) {

            firstMove = false;
            turnContinue = false;
            return true;
        }
        if (turnContinue && jumpHex(a, b, c, d)) {
            if (firstMove) {
                lastX = c;
                lastY = d;
                firstMove = false;
                return true;
            } else {
                if (lastX == a && lastY == b) {
                    lastX = c;
                    lastY = d;
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    /**
     * Metoda pozwalająca otrzymać informację, czy wykonano skok, czy krok
     * @return zwraca true, jeśli gracz wykonał skok, false, gdy wykonał krok o 1 pole
     */
    public boolean isTurnContinue() {
        return turnContinue;
    }
    //Zadada poruszania się o jedno pole (a,b) -> (c,d)
    private boolean walkHex(int a, int b, int c, int d) {
        if (isEmpty(c,d)) {
            if (((c == a + 1) && (d == b))
                    || ((c == a - 1) && (d == b))
                    || ((c == a) && (d == b + 1))
                    || ((c == a) && (d == b - 1))) {
                return true;
            }
            if (b % 2 == 0) {
                return ((c == a - 1) && (d == b - 1)) || ((c == a - 1) && (d == b + 1));
            } else {
                return ((c == a + 1) && (d == b - 1)) || ((c == a + 1) && (d == b + 1));
            }
        }
        return false;
    }
    //Zasada skakania przez pionek (a,b) --> (c,d)
    private boolean jumpHex(int a, int b, int c, int d) {
        if (isEmpty(c,d)) {
            if ((c == a - 2) && (d == b) && hasPeg(c + 1, d)) {
                return true;
            }
            if ((c == a + 2) && (d == b) && hasPeg(c - 1, d)) {
                return true;
            }
            if ((c == a + 1) && (d == b - 2)) {
                if (b % 2 == 0) {
                    return hasPeg(c - 1, d + 1);
                } else {
                    return hasPeg(c, d + 1);
                }
            }
            if ((c == a - 1) && (d == b - 2)) {
                if (b % 2 == 0) {
                    return hasPeg(c, d + 1);
                } else {
                    return hasPeg(c + 1, d + 1);
                }
            }
            if ((c == a + 1) && (d == b + 2)) {
                if (b % 2 == 0) {
                    return hasPeg(c - 1, d - 1);
                } else {
                    return hasPeg(c, d - 1);
                }
            }
            if ((c == a - 1) && (d == b + 2)) {
                if (b % 2 == 0) {
                    return hasPeg(c, d - 1);
                } else {
                    return hasPeg(c + 1, d - 1);
                }
            }
        }
        return false;
    }
    //Metoda ustawiająca odpowiedni narożnik jako cel
    private void setObjective(Hex.State color) {
        switch (color) {
            case RED -> this.objective = regionFactory.getRegion(Region.SOUTH);
            case WHITE -> this.objective = regionFactory.getRegion(Region.SOUTHWEST);
            case GREEN -> this.objective = regionFactory.getRegion(Region.NORTHWEST);
            case BLUE -> this.objective = regionFactory.getRegion(Region.NORTH);
            case BLACK -> this.objective = regionFactory.getRegion(Region.NORTHEAST);
            case YELLOW -> this.objective = regionFactory.getRegion(Region.SOUTHEAST);
            default -> this.objective = null;
        }
    }
    //Metoda sprawdzająca czy pionek jest w docelowym narożniku
    private boolean isInsideObjective(int a, int b) {
        for (Cord cord : this.objective) {
            if (cord.x == a && cord.y == b) {
                return true;
            }
        }
        return false;
    }
    private boolean isEmpty(int x, int y) {
        return this.gameState.getHexes()[x][y].getState() == Hex.State.EMPTY;
    }
    private boolean hasPeg(int x, int y) {
        return this.gameState.getHexes()[x][y].getState() != Hex.State.NULL && this.gameState.getHexes()[x][y].getState() != Hex.State.EMPTY;
    }
}
