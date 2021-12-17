import hex.BoardAndString;
import hex.Hex;
import org.junit.Assert;
import org.junit.Test;

public class TestBoardAndString {
    @Test
    public void testConversion() {
        Hex[][] array = new Hex[25][17];
        for (int i = 0; i < 17; i++) {
            for(int j = 0; j<25; j++) {
                array[j][i] = new Hex(Hex.State.NULL);
            }
        }
        array[0][0] = new Hex(Hex.State.PLAYER1);
        array[1][0] = new Hex(Hex.State.PLAYER1);
        array[2][0] = new Hex(Hex.State.PLAYER2);
        array[3][0] = new Hex(Hex.State.EMPTY);
        array[24][16] = new Hex(Hex.State.PLAYER2);
        String value = new BoardAndString(array).getStringValue();
        System.out.println(value);
        Hex[][] converted = new BoardAndString(value).getBoardValue();
        for (int i = 0; i < 17; i++) {
            for(int j = 0; j<25; j++) {
                System.out.print(converted[j][i].state.name() + " ");
            }
        }
        System.out.println();
        Assert.assertEquals(array[0][0].getState(),converted[0][0].getState());
        Assert.assertEquals(array[24][16].getState(),converted[24][16].getState());
    }
}