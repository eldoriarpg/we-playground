package de.chojo.wetest;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.entity.BaseEntity;
import com.sk89q.worldedit.entity.Entity;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.world.block.BaseBlock;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockStateHolder;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ImmutableExtent implements Extent {
    EditSession session;

    public ImmutableExtent(EditSession session) {
        this.session = session;
    }

    @Override
    public BlockVector3 getMinimumPoint() {
        return session.getMinimumPoint();
    }

    @Override
    public BlockVector3 getMaximumPoint() {
        return session.getMaximumPoint();
    }

    @Override
    public List<? extends Entity> getEntities(Region region) {
        return session.getEntities();
    }

    @Override
    public List<? extends Entity> getEntities() {
        return session.getEntities();
    }

    @Nullable
    @Override
    public Entity createEntity(Location location, BaseEntity entity) {
        return null;
    }

    @Override
    public BlockState getBlock(BlockVector3 position) {
        return session.getBlock(position);
    }

    @Override
    public BaseBlock getFullBlock(BlockVector3 position) {
        return session.getFullBlock(position);
    }

    @Override
    public <T extends BlockStateHolder<T>> boolean setBlock(BlockVector3 position, T block) throws WorldEditException {
        return false;
    }

    @Nullable
    @Override
    public Operation commit() {
        return null;
    }
}
