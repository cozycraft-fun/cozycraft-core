package fun.cozycraft.cozycore.commands;

import fun.cozycraft.cozycore.Cozycore;
import fun.cozycraft.cozycore.commands.back.PlayerBackCommandExecutor;
import fun.cozycraft.cozycore.commands.home.PlayerHomeCommandExecutor;
import fun.cozycraft.cozycore.commands.home.PlayerHomeSetCommandExecutor;
import fun.cozycraft.cozycore.commands.home.PlayerHomeUnsetCommandExecutor;
import fun.cozycraft.cozycore.commands.home.PlayerHomesCommandExecutor;
import fun.cozycraft.cozycore.commands.refer.ReferCommandExecutor;
import org.bukkit.command.PluginCommand;

import java.util.Objects;

public class CozycoreCommandExecutor {

    private final Cozycore core;

    public CozycoreCommandExecutor(final Cozycore core) {
        this.core = core;
    }

    public CozycoreCommandExecutor enable() {
        cmd("home").setExecutor(new PlayerHomeCommandExecutor());
        cmd("homes").setExecutor(new PlayerHomesCommandExecutor());
        cmd("homeset").setExecutor(new PlayerHomeSetCommandExecutor());
        cmd("homeunset").setExecutor(new PlayerHomeUnsetCommandExecutor());
        cmd("back").setExecutor(new PlayerBackCommandExecutor());
        cmd("refer").setExecutor(new ReferCommandExecutor());
        return this;
    }

    private PluginCommand cmd(String name) {
        return Objects.requireNonNull(core.getCommand(name));
    }

}
