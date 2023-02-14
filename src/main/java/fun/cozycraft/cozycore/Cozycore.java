package fun.cozycraft.cozycore;

import fun.cozycraft.cozycore.commands.CozycoreCommandExecutor;
import fun.cozycraft.cozycore.events.CozycoreEventListener;
import org.bukkit.plugin.java.JavaPlugin;


public final class Cozycore extends JavaPlugin {

    private CozycoreCommandExecutor commands;
    private CozycoreEventListener events;

    @Override
    public void onEnable() {
        this.commands = new CozycoreCommandExecutor(this).enable();
        this.events = new CozycoreEventListener(this).enable();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
