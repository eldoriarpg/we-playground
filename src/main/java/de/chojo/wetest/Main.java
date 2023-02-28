package de.chojo.wetest;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;

public class Main extends JavaPlugin {
    WorldEdit worldEdit;

    @Override
    public void onEnable() {
        worldEdit = WorldEdit.getInstance();
        try {
            Path data = getDataFolder().toPath();
            Files.createDirectories(data);
            Files.copy(getResource("cube.schem"), data.resolve("cube.schem"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Nope", e);
        }
        getServer().getPluginCommand("wetest").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        EditSession session;
        Extent extent;
        if (args.length > 0 && args[0].equalsIgnoreCase("mutable")) {
            // Case 1
            // session with mutable world and paste in session
            sender.sendMessage("Pasting in real extent");
            session = worldEdit.newEditSessionBuilder().actor(BukkitAdapter.adapt(player)).world(BukkitAdapter.adapt(player.getWorld())).build();
            extent = session;
        }else if (args.length > 0 && args[0].equalsIgnoreCase("explicit")) {
            // session with immutable world and paste in immutable world
            ImmutableWorld immutableWorld = new ImmutableWorld(player.getWorld());
            session = worldEdit.newEditSessionBuilder().actor(BukkitAdapter.adapt(player)).world(immutableWorld).build();
            extent = immutableWorld;
            sender.sendMessage("session with immutable world and paste in immutable world");
        } else {
            // session with immutable world and paste in session
            sender.sendMessage("Pasting in fake extent");
            ImmutableWorld immutableWorld = new ImmutableWorld(player.getWorld());
            session = worldEdit.newEditSessionBuilder().actor(BukkitAdapter.adapt(player)).world(immutableWorld).build();
            extent = session;
        }
        try (session) {
            ClipboardHolder clipboardHolder = new ClipboardHolder(loadSchematic());
            Operation paste = clipboardHolder.createPaste(extent)
                    .to(BukkitAdapter.adapt(player.getLocation()).toVector().toBlockPoint())
                    .build();
            Operations.complete(paste);
        } catch (IOException | WorldEditException e) {
            getLogger().log(Level.SEVERE, "Nope", e);
        }
        return true;
    }

    public Clipboard loadSchematic() throws IOException {
        File cube = getDataFolder().toPath().resolve("cube.schem").toFile();
        ClipboardFormat format = ClipboardFormats.findByFile(cube);
        try (var in = new FileInputStream(cube); var reader = format.getReader(in)) {
            Clipboard clipboard = reader.read();
            var dimensions = clipboard.getDimensions();
            var centerZ = clipboard.getMinimumPoint().getBlockZ() + dimensions.getBlockZ() / 2;
            var centerX = clipboard.getMinimumPoint().getBlockX() + dimensions.getBlockX() / 2;
            var centerY = clipboard.getMinimumPoint().getBlockY();
            clipboard.setOrigin(BlockVector3.at(centerX, centerY, centerZ));
            return clipboard;
        }
    }
}
