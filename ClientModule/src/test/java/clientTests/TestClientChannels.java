package clientTests;

import client.Client;
import hex.Hex;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class TestClientChannels {
    @Test
    public void testSetBoard() {
        Socket socket = new Socket();
        Hex[][] array = new Hex[17][17];
        array[0][0] = new Hex(Hex.State.PLAYER1);
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(socket.getInputStream());
            Client client = new Client();
            client.setCommunication(socket);
            client.commandWriter.requestBoardState();
            Assert.assertEquals("requestHexes",scanner.nextLine());
            outputStream.writeObject("sendingHexes");
            outputStream.writeObject(array);
            Assert.assertEquals(Hex.State.PLAYER1,client.gameState.getHexAt(0,0));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
