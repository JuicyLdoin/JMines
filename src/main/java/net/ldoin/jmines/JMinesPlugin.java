package net.ldoin.jmines;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.ldoin.jmines.editor.MineEditor;
import net.ldoin.jmines.lang.LangManager;
import net.ldoin.jmines.listener.ListenerManager;
import net.ldoin.jmines.mine.manager.MineManager;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JMinesPlugin extends JavaPlugin {

    @Getter
    private static PluginLogger pluginLogger;
    @Getter
    private static JMinesPlugin plugin;

    MineManager mineManager;
    LangManager langManager;

    MineEditor mineEditor;

    public void onEnable() {

        if (!getDataFolder().exists()) {

            getDataFolder().mkdirs();
            saveDefaultConfig();

        }

        plugin = this;
        pluginLogger = new PluginLogger(this);

        mineManager = new MineManager();
        langManager = new LangManager();

        mineEditor = new MineEditor();

        mineManager.load();
        langManager.load();

        mineEditor.load();

        new ListenerManager(this);

    }

    public void onDisable() {

        mineManager.unload();

    }
}