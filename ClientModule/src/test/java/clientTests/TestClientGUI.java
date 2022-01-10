package clientTests;
import client.Client;
import client.ClientStarter;
import client.gui.BoardHex;
import hex.Hex;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestClientGUI {

    @Test
    public void testBoardHex() {
        BoardHex boardHex = new BoardHex();
        boardHex.setColor(Hex.State.GREEN);
        boardHex.setSelected(true);
        Assert.assertTrue(boardHex.getSelected());
    }
    @Test
    public void testCommunication() throws IOException {
        Socket mockedSocket = mock(Socket.class);
        OutputStream mockedOutputStream = mock(OutputStream.class);
        InputStream mockedInputStream = mock(InputStream.class);
        when(mockedSocket.getOutputStream()).thenReturn(mockedOutputStream);
        when(mockedSocket.getInputStream()).thenReturn(mockedInputStream);
        Client client = new Client();
        client.setCommunication(mockedSocket);
    }
    @Test
    public void testRobotClicking() throws AWTException, IOException {

        final String[] EMPTY_ARRAY = new String[0];
        Thread serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ClientStarter.main(EMPTY_ARRAY);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        serverThread.start();
        Robot bot = new Robot();
        Point savedPosition = MouseInfo.getPointerInfo().getLocation();
        try{Thread.sleep(100);}catch(InterruptedException e){}

        bot.mouseMove(140,160); // kliknij "ok"
        bot.mousePress(InputEvent.getMaskForButton(MouseEvent.BUTTON1));
        try{Thread.sleep(100);}catch(InterruptedException e){}
        bot.mouseRelease(InputEvent.getMaskForButton(MouseEvent.BUTTON1));


        bot.mouseMove(savedPosition.x,savedPosition.y);
    }
}
