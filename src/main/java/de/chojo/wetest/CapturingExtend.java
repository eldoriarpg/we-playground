package de.chojo.wetest;

import com.fastasyncworldedit.core.history.changeset.AbstractChangeSet;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.entity.BaseEntity;
import com.sk89q.worldedit.entity.Entity;
import com.sk89q.worldedit.extent.AbstractDelegateExtent;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.world.block.BlockStateHolder;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

public class CapturingExtend extends AbstractDelegateExtent {
    /**
     * Create a new instance.
     *
     * @param extent the extent
     */
    public CapturingExtend(Extent extent) {
        super(extent);
    }

    @Nullable
    @Override
    public Entity createEntity(Location location, BaseEntity entity) {
        Bukkit.getLogger().info("Blocked entity");
        return null;
    }

    @Nullable
    @Override
    public Entity createEntity(Location location, BaseEntity entity, UUID uuid) {
        Bukkit.getLogger().info("Blocked entity");
        return null;
    }

//    @Override
//    public void setChangeSet(AbstractChangeSet changeSet) {
//        changeSet.getIterator(false)
//    }

    @Override
    public <T extends BlockStateHolder<T>> boolean setBlock(BlockVector3 position, T block) throws WorldEditException {
        Bukkit.getLogger().info("Blocked block at " + position);
        return false;
    }

    @Override
    public <T extends BlockStateHolder<T>> boolean setBlock(int x, int y, int z, T block) throws WorldEditException {
                Bukkit.getLogger().info("Blocked block at %s %s %s".formatted(x,y,z));

        return false;
    }

    @Override
    public int setBlocks(Set<BlockVector3> vset, Pattern pattern) {
        Bukkit.getLogger().info("Blocked pattern for " +vset.size() + " blocks");
        return 0;
    }
}
