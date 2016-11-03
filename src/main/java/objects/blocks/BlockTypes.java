package objects.blocks;

import system.Game;

import java.util.EnumMap;

/**
 * An enumerator describing the different blocktypes and their weighted chance of spawning.
 */
public enum BlockTypes {

    /**
     * A standard block.
     */
    standardBlock,

    /**
     * Normal platforms only.
     */
    normalOnlyBlock,

    /**
     * Horizontal platforms only.
     */
    horizontalOnlyBlock,

    /**
     * Vertical platforms only.
     */
    verticalOnlyBlock;

    /**
     * A map containing the weight of each block type.
     */
    private static EnumMap<BlockTypes, Integer> map;

    /**
     * Initialize the weight.
     */
    static {
        map = new EnumMap<>(BlockTypes.class);
        setMode(Game.Modes.regular);
    }

    /**
     * Get a random block type.
     *
     * @return the type.
     */
    public static BlockTypes randomType() {

        int total = 0;
        for (BlockTypes type : BlockTypes.values()) {
            total += type.getWeight();
        }

        int select = (int) (Math.ceil(Math.random() * total));
        int current = 0;

        for (BlockTypes type : BlockTypes.values()) {
            current += type.getWeight();
            if (current >= select) {
                return type;
            }
        }
        throw new RuntimeException("We exceeded the list in blockTypes somehow.");
    }

    /**
     * Set the weights of the blocktypes.
     *
     * @param newMap the new weights.
     */
    private static void setAllWeights(final EnumMap<BlockTypes, Integer> newMap) {

        for (BlockTypes type : BlockTypes.values()) {
            if (newMap.containsKey(type)) {
                type.setWeight(newMap.get(type));
            } else {
                type.setWeight(0);
            }
        }
    }

    /**
     * Set the weigths to match the game mode.
     *
     * @param m the new game mode.
     */
    public static void setMode(final Game.Modes m) {
        EnumMap<BlockTypes, Integer> newMap = new EnumMap<>(BlockTypes.class);

        switch (m) {
            case regular:
                newMap.put(standardBlock, 10);
                newMap.put(normalOnlyBlock, 2);
                newMap.put(horizontalOnlyBlock, 2);
                newMap.put(verticalOnlyBlock, 1);
                break;
            case space:
                newMap.put(standardBlock, 10);
                newMap.put(normalOnlyBlock, 2);
                newMap.put(horizontalOnlyBlock, 2);
                newMap.put(verticalOnlyBlock, 1);
                break;
            case underwater:
                newMap.put(standardBlock, 10);
                newMap.put(normalOnlyBlock, 2);
                newMap.put(horizontalOnlyBlock, 2);
                newMap.put(verticalOnlyBlock, 1);
                break;
            case verticalOnly:
                newMap.put(verticalOnlyBlock, 1);
                break;
            case darkness:
                newMap.put(normalOnlyBlock, 1);
                break;
            case horizontalOnly:
                newMap.put(horizontalOnlyBlock, 1);
                break;
            default:
                throw new RuntimeException("No such mode (" + m + ") in modes");
        }

        setAllWeights(newMap);
    }

    /**
     * Get the weight of the block type.
     *
     * @return the weight
     */
    private int getWeight() {
        return map.get(this);
    }

    /**
     * Set the weight of the block type.
     *
     * @param w the new weight.
     */
    private void setWeight(final int w) {
        map.put(this, w);
    }
}
