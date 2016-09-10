package objects.blocks;

import system.Factory;

public interface IBlockFactory extends Factory {
    IBlock createBlock();
}
