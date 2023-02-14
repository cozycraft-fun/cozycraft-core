package fun.cozycraft.cozycore.events;

import fun.cozycraft.cozycore.Cozycore;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

public class CozycoreEventListener {
    private final Cozycore core;
    private final PluginManager pluginManager;
    public CozycoreEventListener(final Cozycore core) {
        this.core = core;
        this.pluginManager = Bukkit.getPluginManager();
    }
    public CozycoreEventListener enable() {
//        listen(new CreativeModeListener());
        listen(new PlayerTeleportListener());
        listen(new PlayerNetworkListener());
        return this;
    }

    private void listen(@NotNull Listener listener) {
        pluginManager.registerEvents(listener, core);
    }
}
