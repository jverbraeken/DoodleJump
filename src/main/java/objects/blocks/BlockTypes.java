package objects.blocks;

import java.util.ArrayList;
import java.util.List;

/**
 * An enumerator describing the different blocktypes and their weighted chance of spawning.
 */
public enum BlockTypes {

    /**
     * A standard block.
     */
    standardBlock(10),

    /**
     * Normal platforms only.
     */
    normalOnlyBlock(2),

    /**
     * Vertical platforms only.
     */
    verticalOnlyBlock(2),

    /**
     * Horizontal platforms only.
     */
    horizontalOnlyBlock(2);

    /**
     * The weight of the block type.
     */
    private int weight;

    /**
     * Initialize the weight.
     *
     * @param w the weight
     */
    /* package */BlockTypes(final int w) {
        weight = w;
    }

    /**
     * Get the weight of the block type.
     *
     * @return the weight
     */
    private int getWeight() {
        return weight;
    }


    /**
     * Get a random block type.
     *
     * @return the type.
     */
    public static BlockTypes randomType() {
        List<BlockTypes> types = new ArrayList<>();

        types.add(standardBlock);
        types.add(normalOnlyBlock);
        types.add(verticalOnlyBlock);
        types.add(horizontalOnlyBlock);

        double total = 0d;
        for (BlockTypes type : types) {
            total += type.getWeight();
        }

        double r = Math.random() * total;

        double current = 0d;
        for (BlockTypes type : types) {
            current += type.getWeight();
            if (current >= r) {
                return type;
            }
        }
        throw new RuntimeException("We exceeded the list in blockTypes somehow.");
    }
}
