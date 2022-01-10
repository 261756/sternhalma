package hex;


/**
 * Umożliwia konwersję Hex[13][17] na String i String na Hex[13][17]. Nie dodaje nagłówka
 */
public class BoardAndString {
    final static int xAxis = 13;
    final static int yAxis = 17;
    /**
     * Zwraca zapis String
     * @return String
     */
    public static String getStringValue(Hex[][] hexes) {
        String value = "";
        for (int i = 0; i < yAxis; i++) {
            for(int j = 0; j < xAxis; j++) {
                value = value + hexes[j][i].state.name() + " ";
            }
        }
        return value;
    }

    /**
     * Zwraca tablicę
     * @return Hex[13][17]
     */
    public static Hex[][] getBoardValue(String value) {
        Hex[][] hexes = new Hex[xAxis][yAxis];
        String[] array = value.split(" ");
        for (int i = 0; i < yAxis; i++) {
            for(int j = 0; j < xAxis; j++) {
                hexes[j][i] = new Hex(Hex.State.valueOf(array[i*xAxis+j]));
            }
        }
        return hexes;
    }

}
