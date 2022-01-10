package serverTests;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import server.Server;
import server.gui.AcceptButton;
import server.gui.ServerLogDisplay;
import server.gui.StartPopup;

import java.util.concurrent.ExecutorService;

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
