package serverTests;
import hex.Hex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.mockito.junit.MockitoJUnitRunner;
import server.GameState;
import server.PlayerHandler;
import server.gui.ServerLogDisplay;

import java.io.IOException;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestConnectingPlayers {

    @Mock
    PlayerHandler playerHandler;
    @Mock
    ServerLogDisplay serverLogDisplay;

    @Before
    public void setup() {
        when(playerHandler.getColorname()).thenReturn("RED");
    }
    @Test
    public void testGameStarting() throws IOException {
        Assert.assertNotNull(serverLogDisplay);
        GameState GS = new GameState(3,0,serverLogDisplay);
        GS.addPlayer(playerHandler);
        Assert.assertFalse(GS.getGameStarted());
        GS.addPlayer(playerHandler);
        Assert.assertFalse(GS.getGameStarted());
        GS.addPlayer(playerHandler);
        Assert.assertTrue(GS.getGameStarted());
    }

}
