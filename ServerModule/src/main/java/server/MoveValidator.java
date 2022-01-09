package server;

import hex.BoardAndString;
import hex.Hex;

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
public class MoveValidator {
    /*private static final ArrayList<int[]> cornerN = new ArrayList<>();
    static {
        cornerN.add(new int[] {6,0});
        cornerN.add(new int[] {5,1});
        cornerN.add(new int[] {6,1});
        cornerN.add(new int[] {5,2});
        cornerN.add(new int[] {6,2});
        cornerN.add(new int[] {7,2});
        cornerN.add(new int[] {4,3});
        cornerN.add(new int[] {5,3});
        cornerN.add(new int[] {6,3});
        cornerN.add(new int[] {7,3});
    }
    private static final ArrayList<int[]> cornerNE = new ArrayList<>();
    static {
        cornerN.add(new int[] {9,4});
        cornerN.add(new int[] {10,4});
        cornerN.add(new int[] {11,4});
        cornerN.add(new int[] {12,4});
        cornerN.add(new int[] {9,5});
        cornerN.add(new int[] {10,5});
        cornerN.add(new int[] {11,5});
        cornerN.add(new int[] {10,6});
        cornerN.add(new int[] {11,6});
        cornerN.add(new int[] {10,7});
    }
    private static final ArrayList<int[]> cornerSE = new ArrayList<>();
    static {
        cornerN.add(new int[] {9,12});
        cornerN.add(new int[] {10,12});
        cornerN.add(new int[] {11,12});
        cornerN.add(new int[] {12,12});
        cornerN.add(new int[] {9,11});
        cornerN.add(new int[] {10,11});
        cornerN.add(new int[] {11,11});
        cornerN.add(new int[] {10,10});
        cornerN.add(new int[] {11,10});
        cornerN.add(new int[] {10,9});
    }
    private static final ArrayList<int[]> cornerS = new ArrayList<>();
    static {
        cornerN.add(new int[] {6,16});
        cornerN.add(new int[] {5,15});
        cornerN.add(new int[] {6,15});
        cornerN.add(new int[] {5,14});
        cornerN.add(new int[] {6,14});
        cornerN.add(new int[] {7,14});
        cornerN.add(new int[] {4,13});
        cornerN.add(new int[] {5,13});
        cornerN.add(new int[] {6,13});
        cornerN.add(new int[] {7,13});
    }
    private static final ArrayList<int[]> cornerSW = new ArrayList<>();
    static {
        cornerN.add(new int[] {0,12});
        cornerN.add(new int[] {1,12});
        cornerN.add(new int[] {2,12});
        cornerN.add(new int[] {3,12});
        cornerN.add(new int[] {0,11});
        cornerN.add(new int[] {1,11});
        cornerN.add(new int[] {2,11});
        cornerN.add(new int[] {1,10});
        cornerN.add(new int[] {2,10});
        cornerN.add(new int[] {1,9});
    }
    private static final ArrayList<int[]> cornerNW = new ArrayList<>();
    static {
        cornerN.add(new int[] {0,4});
        cornerN.add(new int[] {1,4});
        cornerN.add(new int[] {2,4});
        cornerN.add(new int[] {3,4});
        cornerN.add(new int[] {0,5});
        cornerN.add(new int[] {1,5});
        cornerN.add(new int[] {2,5});
        cornerN.add(new int[] {1,6});
        cornerN.add(new int[] {2,6});
        cornerN.add(new int[] {1,7});
    }*/
    private GameState gameState;
    private ArrayList<Cord> objective;
    private boolean firstMove;
    private boolean turnContinue;
    private int lastX;
    private int lastY;
    public MoveValidator(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Metoda zmieniająca konfigurację sprawdzacza dla następnego gracza, na początku jego tury
     * @param color kolor gracza
     */
    public void newTurn(Hex.State color) {
        this.turnContinue = true;
        this.firstMove = true;
        setObjective(color);
    }

    /**
     * Metoda sprawdzająca czy ruch (a,b) -> (c,d) jest zgodny z zasadami
     * @param a x początkowe
     * @param b y początkowe
     * @param c x końcowe
     * @param d y końcowe
     * @return true -> gdy ruch poprawny, false -> w przeciwnym wypadku
     */
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
            case RED -> this.objective = gameState.getScords();
            case WHITE -> this.objective = gameState.getSWcords();
            case GREEN -> this.objective = gameState.getNWcords();
            case BLUE -> this.objective = gameState.getNcords();
            case BLACK -> this.objective = gameState.getNEcords();
            case YELLOW -> this.objective = gameState.getSEcords();
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
