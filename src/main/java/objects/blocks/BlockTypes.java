package objects.blocks;

import system.Game;

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
     * Horizontal platforms only.
     */
    horizontalOnlyBlock(2),

    /**
     * Vertical platforms only.
     */
    verticalOnlyBlock(1);

    /**
     * The weight of the block type.
     */
    private int weight;

    /**
     * Initialize the weight.
     *
     * @param w
     */
    BlockTypes(final int w) {
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
     * Set the weight of the block type.
     *
     * @param w the new weight.
     */
    private void setWeight(final int w) {
        weight = w;
    }

    /**
     * Get a random block type.
     *
     * @return the type.
     */
    public static BlockTypes randomType() {
        List<BlockTypes> types = getAllTypes();

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

    /**
     * Set all types to a certain weight.
     *
     * @param w the new weight.
     */
    private static void setAllWeights(int w) {
        for (BlockTypes type : getAllTypes()) {
            type.setWeight(w);
        }
    }

    /**
     * Get all the block types.
     *
     * @return a list of block types.
     */
    private static List<BlockTypes> getAllTypes() {
        List<BlockTypes> types = new ArrayList<>();

        types.add(standardBlock);
        types.add(normalOnlyBlock);
        types.add(horizontalOnlyBlock);
        types.add(verticalOnlyBlock);

        return types;
    }

    /**
     * Reset the standard weights.
     */
    public static void setRegularWeights() {
        setAllWeights(0);
        standardBlock.setWeight(10);
        normalOnlyBlock.setWeight(2);
        horizontalOnlyBlock.setWeight(2);
        verticalOnlyBlock.setWeight(1);
    }

    /**
     * Set the weights of the blocktypes.
     *
     * @param w the new weights.
     */
    public static void setAllWeights(List<Integer> w) {
        List<Integer> weights = w;
        List<BlockTypes> types = getAllTypes();

        while (weights.size() < types.size()) {
            weights.add(0);
        }

        int i = 0;

        for (BlockTypes type : types) {
            type.setWeight(weights.get(i++));
        }
    }

    public static void setMode(Game.Modes m) {
        setRegularWeights();
        switch (m) {
            case regular:
                break;
            case space:
                break;
            case underwater:
                break;
            case invert:
                List<Integer> verticalOnlyWeights = new ArrayList<>();
                //regular blocks.
                verticalOnlyWeights.add(0);
                //normal only blocks.
                verticalOnlyWeights.add(0);
                //horizontal only blocks.
                verticalOnlyWeights.add(0);
                //vertical only blocks.
                verticalOnlyWeights.add(1);
                setAllWeights(verticalOnlyWeights);
                break;
            case darkness:
                List<Integer> darknessWeights = new ArrayList<>();
                //regular blocks.
                darknessWeights.add(0);
                //normal only blocks.
                darknessWeights.add(1);
                setAllWeights(darknessWeights);
                break;
            case story:
                List<Integer> horizontalOnlyWeigths = new ArrayList<>();
                //regular blocks.
                horizontalOnlyWeigths.add(0);
                //normal only blocks.
                horizontalOnlyWeigths.add(0);
                //horizontal only blocks.
                horizontalOnlyWeigths.add(1);
                //vertical only blocks.
                horizontalOnlyWeigths.add(0);
                setAllWeights(horizontalOnlyWeigths);
                break;
            default:
                throw new RuntimeException("No such mode (" + m + ") in modes");
        }
    }
}
