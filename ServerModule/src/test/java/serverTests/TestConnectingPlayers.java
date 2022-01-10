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
import server.Server;
import server.gui.ServerLogDisplay;

import java.io.IOException;
import java.net.Socket;

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
    @Test
    public void testConnectingSockets() throws Exception {
        ServerLogDisplay serverLogDisplay = new ServerLogDisplay();
        Server server = new Server(serverLogDisplay);
        Thread serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    server.startServer(9999, 6);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        serverThread.start();
        Socket socket1 = new Socket("localhost",9999);
        Socket socket2 = new Socket("localhost",9999);
        Socket socket3 = new Socket("localhost",9999);
        Socket socket4 = new Socket("localhost",9999);
        Socket socket5 = new Socket("localhost",9999);
        Socket socket6 = new Socket("localhost",9999);
        server.log("test");
    }

}
