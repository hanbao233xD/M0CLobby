package ltd.m0c.moclobby;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import static ltd.m0c.moclobby.Utils.sendGet;

public final class MOCLobby extends JavaPlugin {
    public static String list;

    @Override
    public void onEnable() {
        // Plugin startup logic
        list= sendGet("https://raw.githubusercontent.com/hanbao233xD/CloudConfig/main/lobby.txt");
        Bukkit.getPluginManager().registerEvents(new LobbyManager(),this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
