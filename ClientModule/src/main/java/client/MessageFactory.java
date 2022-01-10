package client;

/**
 * Klasa tworzy stringi służące do komunikacji z użytkownikiem
 */
public class MessageFactory {
    public static String myTurnMsg() {
        return "<html><div style='text-align: center;'>Twoja tura!</div><br></html>";
    }

    public static String opponentTurnMsg(String color) {
        return "<html><div style='text-align: center;'>Tura gracza:<font color = '" + color + "'><br>" + color + "</div></html>";
    }

    public static String winnerMsg(String prevmsg, String color, int place) {
        StringBuilder builder = new StringBuilder(prevmsg);
        String n = "<div style='text-align: center;'>" + place + ". miejsce zajął:<font color = '" + color + "'><br>" + color + "</div><br>";
        builder.insert(6,n);
        System.out.println(builder.toString());
        return  builder.toString();
    }
    public static String leftMsg(String prevmsg, String color) {
        StringBuilder builder = new StringBuilder(prevmsg);
        String n = "<div style='text-align: center;'><font color = '" + color + "'>" + color + "<br><font color = 'BLACK'> wyszedł z gry.</div><br>";
        builder.insert(6,n);
        System.out.println(builder.toString());
        return  builder.toString();
    }
}
