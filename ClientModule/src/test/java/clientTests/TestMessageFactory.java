package clientTests;

import client.MessageFactory;
import client.gui.ClientFrame;
import org.junit.Assert;
import org.junit.Test;

import org.mockito.junit.MockitoJUnitRunner;

import java.util.Locale;
public class TestMessageFactory {
    MessageFactory factory = new MessageFactory();
    @Test
    public void testMyTurnMsg() {
        Assert.assertTrue(factory.myTurnMsg().toLowerCase().contains("twoja tura"));
    }
    @Test
    public void testOpponentTurnMsg() {
        String expected = "<html><div style='text-align: center;'>Tura gracza:<font color = 'WHITE'><br>WHITE</div></html>";
        Assert.assertEquals(expected, factory.opponentTurnMsg("WHITE"));
    }
    @Test
    public void testWinnerMsg() {
        String dummy = "<html>Dummy</html>";
        String expected = "<html><div style='text-align: center;'>1. miejsce zajął:<font color = 'RED'><br>RED</div><br>Dummy</html>";
        Assert.assertEquals(expected, factory.winnerMsg(dummy, "RED", 1));
    }
    @Test
    public void testLeftMsg() {
        String dummy = "<html>Dummy</html>";
        String expected = "<html><div style='text-align: center;'><font color = 'RED'>RED<br><font color = 'BLACK'> wyszedł z gry.</div><br>Dummy</html>";
        Assert.assertEquals(expected, factory.leftMsg(dummy, "RED"));
    }
}
