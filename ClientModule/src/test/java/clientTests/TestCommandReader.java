package clientTests;

import client.Client;
import client.CommandReader;
import hex.BoardAndString;
import hex.Hex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestCommandReader {
    @Mock
    Client client;
    @InjectMocks
    CommandReader reader;

    @Test
    public void testReaderSending() {
        Hex[][] array = new Hex[13][17];
        for (int i = 0; i < 17; i++) {
            for(int j = 0; j<13; j++) {
                array[j][i] = new Hex(Hex.State.NULL);
            }
        }
        String command = "sendingHexes" + BoardAndString.getStringValue(array);
        reader.processInstruction(command);
        verify(client).updateFrameBoard();
    }
    @Test
    public void testReaderAssignRed() {
        String command = "assignColor" + "RED";
        reader.processInstruction(command);
        verify(client).setPegsColor(Hex.State.RED);
    }
    @Test
    public void testReaderAssignBlack() {
        String command = "assignColor" + "BLACK";
        reader.processInstruction(command);
        verify(client).setPegsColor(Hex.State.BLACK);
    }
    @Test
    public void testReaderMoveMade() {
        String command = "moveMade";
        reader.processInstruction(command);
        verify(client).requestHexes();
    }
    @Test
    public void testReaderTurnChanged() {
        String command = "turnChanged" + "RED";
        reader.processInstruction(command);
        verify(client).setCurrentPlayer("RED");
    }
    @Test
    public void testWon() {
        String command = "won" + "RED";
        reader.processInstruction(command);
        verify(client).updateWinners("RED");
    }
    @Test
    public void testGameEnded() {
        String command = "gameEnded";
        reader.processInstruction(command);
        verify(client).setCurrentPlayer("gameEnded");
    }
    @Test
    public void testLeft() {
        String command = "left" + "WHITE";
        reader.processInstruction(command);
        verify(client).updateLeavers("WHITE");
    }
}
