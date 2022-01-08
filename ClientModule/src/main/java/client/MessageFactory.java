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
}
