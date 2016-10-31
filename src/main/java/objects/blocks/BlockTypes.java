package objects.blocks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 31-10-2016.
 */
public enum BlockTypes {

    standardBlock(10),

    normalOnlyBlock(2),

    verticalOnlyBlock(2),

    horizontalOnlyBlock(2);


    private int weight;

    BlockTypes(int w) {
        weight = w;
    }

    private int getWeight() {
        return weight;
    }

    public static BlockTypes randomType() {
        List<BlockTypes> types = new ArrayList<>();

        types.add(standardBlock);
        types.add(normalOnlyBlock);
        types.add(verticalOnlyBlock);
        types.add(horizontalOnlyBlock);

        double total = 0d;
        for (BlockTypes type : types)
            total += type.getWeight();

        double r = Math.random() * total;

        double current = 0d;
        for (BlockTypes type : types) {
            current += type.getWeight();
            if (current >= r)
                return type;
        }
        throw new RuntimeException("We exceeded the list in blockTypes somehow.");
    }
}
