package net.ldoin.jmines.mine.block.type;

import lombok.EqualsAndHashCode;
import lombok.Value;
import net.ldoin.jmines.mine.block.MineBlock;

@Value
@EqualsAndHashCode(callSuper = true)
public class RegenerateBlock extends net.ldoin.jmines.mine.block.type.MineBlock {

    MineBlock replace;

    int ticks;

    public RegenerateBlock(MineBlock block, MineBlock replace, int ticks) {

        super(block);

        this.replace = replace;

        this.ticks = ticks;

    }
}