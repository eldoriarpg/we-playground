package de.chojo.wetest;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.util.SideEffectSet;
import com.sk89q.worldedit.world.block.BlockStateHolder;
import org.bukkit.World;

public class ImmutableWorld extends BukkitWorld {
    /**
     * Construct the object.
     *
     * @param world the world
     */
    public ImmutableWorld(World world) {
        super(world);
    }

    @Override
    public <B extends BlockStateHolder<B>> boolean setBlock(BlockVector3 position, B block, SideEffectSet sideEffects) {
        return true;
    }
}
