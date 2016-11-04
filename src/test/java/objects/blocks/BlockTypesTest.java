package objects.blocks;

import org.junit.Test;
import org.powermock.reflect.Whitebox;
import system.Game;

import java.util.EnumMap;

import static objects.blocks.BlockTypes.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BlockTypesTest {

    @Test
    public void crashTest() {
        EnumMap<BlockTypes, Integer> map = (Whitebox.getInternalState(BlockTypes.class, "map"));
        assertNotNull(map);
    }

    @Test
    public void testGetWeight() throws Exception {
        EnumMap<BlockTypes, Integer> newMap = new EnumMap<>(BlockTypes.class);
        int[] weights = {(int) (Math.random() * 1000), (int) (Math.random() * 1000), (int) (Math.random() * 1000), (int) (Math.random() * 1000)};
        newMap.put(standardBlock, weights[0]);
        newMap.put(normalOnlyBlock, weights[1]);
        newMap.put(horizontalOnlyBlock, weights[2]);
        newMap.put(verticalOnlyBlock, weights[3]);
        Whitebox.setInternalState(BlockTypes.class, "map", newMap);

        int w0 = Whitebox.invokeMethod(standardBlock, "getWeight");
        assertEquals(weights[0], w0);

        int w1 = Whitebox.invokeMethod(normalOnlyBlock, "getWeight");
        assertEquals(weights[1], w1);

        int w2 = Whitebox.invokeMethod(horizontalOnlyBlock, "getWeight");
        assertEquals(weights[2], w2);

        int w3 = Whitebox.invokeMethod(verticalOnlyBlock, "getWeight");
        assertEquals(weights[3], w3);
    }

    @Test
    public void testSetWeight() throws Exception {
        int[] weights = {(int) (Math.random() * 1000), (int) (Math.random() * 1000), (int) (Math.random() * 1000), (int) (Math.random() * 1000)};
        Whitebox.invokeMethod(standardBlock, "setWeight", weights[0]);
        Whitebox.invokeMethod(normalOnlyBlock, "setWeight", weights[1]);
        Whitebox.invokeMethod(horizontalOnlyBlock, "setWeight", weights[2]);
        Whitebox.invokeMethod(verticalOnlyBlock, "setWeight", weights[3]);

        EnumMap<BlockTypes, Integer> map = (Whitebox.getInternalState(BlockTypes.class, "map"));

        assertEquals(weights[0], (int) map.get(standardBlock));
        assertEquals(weights[1], (int) map.get(normalOnlyBlock));
        assertEquals(weights[2], (int) map.get(horizontalOnlyBlock));
        assertEquals(weights[3], (int) map.get(verticalOnlyBlock));

    }

    @Test
    /**
     * Note: there is a very very very small chance this test might randomly fail.
     */
    public void testgetRandomElement() throws Exception {
        Whitebox.invokeMethod(standardBlock, "setWeight", 1);
        Whitebox.invokeMethod(normalOnlyBlock, "setWeight", 2);
        Whitebox.invokeMethod(horizontalOnlyBlock, "setWeight", 3);
        Whitebox.invokeMethod(verticalOnlyBlock, "setWeight", 4);

        int[] count = new int[4];
        for (int i = 0; i < 100000; i++) {
            BlockTypes type = randomType();
            if (type.equals(standardBlock)) {
                count[0]++;
            } else if (type.equals(normalOnlyBlock)) {
                count[1]++;
            } else if (type.equals(horizontalOnlyBlock)) {
                count[2]++;
            } else if (type.equals(verticalOnlyBlock)) {
                count[3]++;
            }
        }

        assertTrue(count[0] > 9000);
        assertTrue(count[0] < 11000);

        assertTrue(count[1] > 19000);
        assertTrue(count[1] < 21000);

        assertTrue(count[2] > 29000);
        assertTrue(count[2] < 31000);

        assertTrue(count[3] > 39000);
        assertTrue(count[3] < 41000);
    }

    @Test
    public void testSetRegularMode() throws Exception {
        EnumMap<BlockTypes, Integer> expectedMap = new EnumMap<>(BlockTypes.class);
        expectedMap.put(standardBlock, 10);
        expectedMap.put(normalOnlyBlock, 2);
        expectedMap.put(horizontalOnlyBlock, 2);
        expectedMap.put(verticalOnlyBlock, 1);


        Whitebox.invokeMethod(BlockTypes.class, "setMode", Game.Modes.regular);
        EnumMap<BlockTypes, Integer> map = (Whitebox.getInternalState(BlockTypes.class, "map"));
        assertEquals(expectedMap, map);
    }

    @Test
    public void testSetSpaceMode() throws Exception {
        EnumMap<BlockTypes, Integer> expectedMap = new EnumMap<>(BlockTypes.class);
        expectedMap.put(standardBlock, 10);
        expectedMap.put(normalOnlyBlock, 2);
        expectedMap.put(horizontalOnlyBlock, 2);
        expectedMap.put(verticalOnlyBlock, 1);


        Whitebox.invokeMethod(BlockTypes.class, "setMode", Game.Modes.space);
        EnumMap<BlockTypes, Integer> map = (Whitebox.getInternalState(BlockTypes.class, "map"));
        assertEquals(expectedMap, map);
    }

    @Test
    public void testSetUnderwaterMode() throws Exception {
        EnumMap<BlockTypes, Integer> expectedMap = new EnumMap<>(BlockTypes.class);
        expectedMap.put(standardBlock, 10);
        expectedMap.put(normalOnlyBlock, 2);
        expectedMap.put(horizontalOnlyBlock, 2);
        expectedMap.put(verticalOnlyBlock, 1);


        Whitebox.invokeMethod(BlockTypes.class, "setMode", Game.Modes.underwater);
        EnumMap<BlockTypes, Integer> map = (Whitebox.getInternalState(BlockTypes.class, "map"));
        assertEquals(expectedMap, map);
    }

    @Test
    public void testSetHorizontalOnlyMode() throws Exception {
        EnumMap<BlockTypes, Integer> expectedMap = new EnumMap<>(BlockTypes.class);
        expectedMap.put(standardBlock, 0);
        expectedMap.put(normalOnlyBlock, 0);
        expectedMap.put(horizontalOnlyBlock, 1);
        expectedMap.put(verticalOnlyBlock, 0);


        Whitebox.invokeMethod(BlockTypes.class, "setMode", Game.Modes.horizontalOnly);
        EnumMap<BlockTypes, Integer> map = (Whitebox.getInternalState(BlockTypes.class, "map"));
        assertEquals(expectedMap, map);
    }

    @Test
    public void testSetVerticalOnlyMode() throws Exception {
        EnumMap<BlockTypes, Integer> expectedMap = new EnumMap<>(BlockTypes.class);
        expectedMap.put(standardBlock, 0);
        expectedMap.put(normalOnlyBlock, 0);
        expectedMap.put(horizontalOnlyBlock, 0);
        expectedMap.put(verticalOnlyBlock, 1);


        Whitebox.invokeMethod(BlockTypes.class, "setMode", Game.Modes.verticalOnly);
        EnumMap<BlockTypes, Integer> map = (Whitebox.getInternalState(BlockTypes.class, "map"));
        assertEquals(expectedMap, map);
    }

    @Test
    public void testSetDarknessMode() throws Exception {
        EnumMap<BlockTypes, Integer> expectedMap = new EnumMap<>(BlockTypes.class);
        expectedMap.put(standardBlock, 0);
        expectedMap.put(normalOnlyBlock, 1);
        expectedMap.put(horizontalOnlyBlock, 0);
        expectedMap.put(verticalOnlyBlock, 0);


        Whitebox.invokeMethod(BlockTypes.class, "setMode", Game.Modes.darkness);
        EnumMap<BlockTypes, Integer> map = (Whitebox.getInternalState(BlockTypes.class, "map"));
        assertEquals(expectedMap, map);
    }


}
