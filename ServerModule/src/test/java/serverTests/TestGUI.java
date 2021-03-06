package serverTests;

import org.junit.Test;
import server.NoGUIInitializer;
import server.Server;
import server.gui.AcceptButton;
import server.gui.ServerLogDisplay;
import server.gui.StartPopup;

import static org.mockito.Mockito.*;

public class TestGUI {


    @Test
    public void testAccept() {
        StartPopup startPopup = mock(StartPopup.class);
        AcceptButton acceptButton = new AcceptButton(startPopup);
        acceptButton.doClick();
        verify(startPopup,times(1)).sendInput();
    }
    @Test
    public void testStartPopup() throws Exception {
        Server S = mock(Server.class);
        StartPopup startPopup = new StartPopup(S);
        startPopup.sendInputNoGUI("999","2");
        verify(S,timeout(2000)).startServer(999,2);
    }
    @Test
    public void testNoGUI() throws Exception {
        Server S = mock(Server.class);
        NoGUIInitializer.startServerNoGUI(S,"999","2");
        verify(S,times(1)).startServer(999,2);
    }
    @Test
    public void testLogDisplay() throws Exception {
        ServerLogDisplay serverLogDisplay = new ServerLogDisplay();
        Server S = new Server(serverLogDisplay);
        Thread serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    S.startServer(999, 2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        serverThread.start();
        serverLogDisplay.log("test");
        serverThread.stop();
    }
}
