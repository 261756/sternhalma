package serverTests;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.mockito.junit.MockitoJUnitRunner;
import server.GameState;
import server.PlayerHandler;
import server.gui.ServerLogDisplay;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class TestConnectingPlayers {

    @Mock
    PlayerHandler playerHandler;
    @Mock
    ServerLogDisplay serverLogDisplay;
    @Test
    public void testGameStarting() throws IOException {
        Assert.assertNotNull(serverLogDisplay);
        GameState GS = new GameState(3,0,serverLogDisplay);
        GS.addPlayer(playerHandler);
        Assert.assertEquals(GS.getGameStarted(),false);
        GS.addPlayer(playerHandler);
        Assert.assertEquals(GS.getGameStarted(),false);
        GS.addPlayer(playerHandler);
        Assert.assertEquals(GS.getGameStarted(),true);
    }

}
