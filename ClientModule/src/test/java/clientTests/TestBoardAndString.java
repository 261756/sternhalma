package clientTests;

import hex.BoardAndString;
import hex.Hex;
import org.junit.Assert;
import org.junit.Test;

public class TestBoardAndString {
    @Test
    public void testConversion() {
        Hex[][] array = new Hex[13][17];
        for (int i = 0; i < 17; i++) {
            for(int j = 0; j<13; j++) {
                array[j][i] = new Hex(Hex.State.NULL);
            }
        }
        array[0][0] = new Hex(Hex.State.RED);
        array[1][0] = new Hex(Hex.State.RED);
        array[2][0] = new Hex(Hex.State.BLUE);
        array[3][0] = new Hex(Hex.State.EMPTY);
        array[12][16] = new Hex(Hex.State.BLUE);
        String value = new BoardAndString(array).getStringValue();
        Hex[][] converted = new BoardAndString(value).getBoardValue();
        for (int i = 0; i < 17; i++) {
            for(int j = 0; j<13; j++) {
                Assert.assertEquals(array[j][i].getState(),converted[j][i].getState());
            }
        }
    }
}
