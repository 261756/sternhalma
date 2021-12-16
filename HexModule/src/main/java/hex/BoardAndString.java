package hex;


/**
 * Umożliwia konwersję Hex[25][17] na String i String na Hex[25][17]. Nie dodaje nagłówka
 */
public class BoardAndString {
    Hex[][] hexes;
    String value;

    /**
     * Konstrukor dla tablicy
     * @param hexes tablica
     */
    public BoardAndString(Hex[][] hexes) {
        this.hexes = hexes;
        this.value = "";
    }

    /**
     * Konstrukor dla String
     * @param value string
     */
    public BoardAndString(String value) {
        this.value = value;
        this.hexes = new Hex[25][17];
    }

    /**
     * Zwraca zapis String
     * @return String
     */
    public String getStringValue() {
        for (int i = 0; i < 17; i++) {
            for(int j = 0; j<25; j++) {
                value = value + hexes[j][i].state.name() + " ";
            }
        }
        return value;
    }

    /**
     * Zwraca tablicę
     * @return Hex[25][17]
     */
    public Hex[][] getBoardValue() {
        String[] array = value.split(" ");
        for (int i = 0; i < 17; i++) {
            for(int j = 0; j<25; j++) {
                hexes[j][i] = new Hex(Hex.State.valueOf(array[i*25+j]));
            }
        }
        return hexes;
    }
}
