package client;

/**
 * Klasa tworzy stringi służące do komunikacji z użytkownikiem
 */
public class MessageFactory {
    public String myTurnMsg() {
        return "Twoja tura!";
    }

    public String opponentTurnMsg(String color) {
        return "<html><div style='text-align: center;'>Tura gracza:<font color = '" + color + "'><br>" + color + "</div></html>";
    }

    public String winnerMsg(String prevmsg, String color, int place) {
        StringBuilder builder = new StringBuilder(prevmsg);
        String n = "<div style='text-align: center;'>" + place + ". miejsce zajął:<font color = '" + color + "'><br>" + color + "</div>";
        builder.insert(6,n);
        System.out.println(builder.toString());
        return  builder.toString();
    }
}
