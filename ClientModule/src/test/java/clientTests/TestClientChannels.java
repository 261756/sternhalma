package clientTests;

import client.Client;
import hex.Hex;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

public class TestClientChannels {
    @Test
    public void testUpdateBoard() {
        Hex[][] array = new Hex[25][17];
        array[0][0] = new Hex(Hex.State.PLAYER1);
        Client client = new Client();
        client.updateBoard(array);
        Assert.assertEquals(Hex.State.PLAYER1,client.gameState.getHexAt(0,0).getState());
    }
    @Test
    public void testSetBoard() throws IOException {

        var socket = new Socket("localhost", 59898);
        Client client = new Client();
        client.setCommunication(socket);
        client.commandWriter.requestBoardState();
        client.commandReader.fetchInstruction();
        Hex unit1 = client.gameState.getHexAt(0,0);
        Hex unit2 = client.gameState.getHexAt(24,15);;
        Assert.assertEquals(Hex.State.PLAYER1,unit1.getState());
        Assert.assertEquals(Hex.State.PLAYER2,unit2.getState());
    }
}
