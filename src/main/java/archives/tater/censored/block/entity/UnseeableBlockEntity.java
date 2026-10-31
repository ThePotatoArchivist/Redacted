package archives.tater.censored.block.entity;

import archives.tater.censored.registry.CensoredModBlockEntities;
import archives.tater.censored.registry.CensoredModBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class UnseeableBlockEntity extends BlockEntity {
    public UnseeableBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(CensoredModBlockEntities.UNSEEABLE_BLOCK, worldPosition, blockState);
    }

    public boolean shouldRenderFace(Direction direction) {
        if (level == null) return true;
        var blockState = level.getBlockState(getBlockPos().relative(direction));
        return !blockState.is(CensoredModBlocks.UNSEEABLE_BLOCK) && Block.shouldRenderFace(getBlockState(), blockState, direction);
    }
}
