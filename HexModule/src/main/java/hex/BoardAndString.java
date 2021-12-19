package hex;


/**
 * Umożliwia konwersję Hex[25][17] na String i String na Hex[25][17]. Nie dodaje nagłówka
 */
public class BoardAndString {
    Hex[][] hexes;
    String value;
    final static int xAxis = 13;
    final static int yAxis = 17;
    /**
     * Konstrukor dla tablicy
     * @param hexes tablica
     */
    public BoardAndString(Hex[][] hexes) {
        this.hexes = hexes;
        this.value = "";
        setStringValue();
    }

    /**
     * Konstrukor dla String
     * @param value string
     */
    public BoardAndString(String value) {
        this.value = value;
        this.hexes = new Hex[xAxis][yAxis];
        setHexValue();
    }

    /**
     * Zwraca zapis String
     * @return String
     */
    public String getStringValue() {
        return value;
    }

    /**
     * Tworzy zapis String
     */
    private void setStringValue() {
        for (int i = 0; i < yAxis; i++) {
            for(int j = 0; j < xAxis; j++) {
                value = value + hexes[j][i].state.name() + " ";
            }
        }
    }

    /**
     * Zwraca tablicę
     * @return Hex[13][17]
     */
    public Hex[][] getBoardValue() {
        return hexes;
    }

    /**
     * Tworzy tablicę
     */
    private void setHexValue() {
        String[] array = value.split(" ");
        for (int i = 0; i < yAxis; i++) {
            for(int j = 0; j < xAxis; j++) {
                hexes[j][i] = new Hex(Hex.State.valueOf(array[i*xAxis+j]));
            }
        }
    }
}
