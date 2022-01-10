package clientTests;

import client.Client;
import hex.Hex;
import org.junit.Assert;
import org.junit.Test;


public class TestClientChannels {
    @Test
    public void testUpdateBoard() {
        Hex[][] array = new Hex[13][17];
        array[0][0] = new Hex(Hex.State.BLUE);
        Client client = new Client();
        client.updateBoard(array);
        Assert.assertEquals(Hex.State.BLUE,client.gameState.getHexAt(0,0).getState());
    }

}
