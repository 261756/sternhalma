package client;

/**
 * Klasa tworzy stringi służące do komunikacji z użytkownikiem
 */
public class MessageFactory {
    /**
     * Zwraca powiadomienie o turze gracza
     * @return wiadomość
     */
    public static String myTurnMsg() {
        return "<html><div style='text-align: center;'>Twoja tura!</div><br></html>";
    }

    /**
     * Zwraca powiadomienie o turze innego gracza o danym kolorze
     * @param color kolor innego gracza
     * @return wiadomość
     */
    public static String opponentTurnMsg(String color) {
        return "<html><div style='text-align: center;'>Tura gracza:<font color = '" + color + "'><br>" + color + "</div></html>";
    }

    /**
     * Do poprzedniego powiadomienia dołącza nowe powiadomienie o zwycięzcy gry
     * @param prevmsg przednie powiadomienie
     * @param color kolor gracza, który teraz wygrał
     * @param place miejsce, które zajął gracz
     * @return nowe powiadomienie + stare powiadomienie
     */
    public static String winnerMsg(String prevmsg, String color, int place) {
        StringBuilder builder = new StringBuilder(prevmsg);
        String n = "<div style='text-align: center;'>" + place + ". miejsce zajął:<font color = '" + color + "'><br>" + color + "</div><br>";
        builder.insert(6,n);
        //System.out.println(builder.toString());
        return  builder.toString();
    }

    /**
     * Do poprzedniego powiadomienia dołącza nowe powiadomienie o graczu, który opuścił grę
     * @param prevmsg poprzednie powiadomienie
     * @param color kolor gracza, który wyszedł
     * @return nowe powiadomienie + stare powiadomienie
     */
    public static String leftMsg(String prevmsg, String color) {
        StringBuilder builder = new StringBuilder(prevmsg);
        String n = "<div style='text-align: center;'><font color = '" + color + "'>" + color + "<br><font color = 'BLACK'> wyszedł z gry.</div><br>";
        builder.insert(6,n);
        //System.out.println(builder.toString());
        return  builder.toString();
    }
    public static String simpleMsg(String message) {
        return "<html><div style='text-align: center;'>" + message + "</div><br></html>";
    }
}
