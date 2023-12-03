package net.ldoin.jmines.lang;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.ldoin.jmines.JMinesPlugin;
import net.ldoin.jmines.event.LangChangeEvent;
import net.ldoin.jmines.util.util.ColorUtil;
import net.ldoin.jmines.util.util.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LangManager {

    private static final String[] DEFAULT = new String[]{"en", "ru"};

    final JMinesPlugin plugin = JMinesPlugin.getPlugin();

    final File folder = new File(plugin.getDataFolder(), "langs");
    final FileUtil fileUtil = new FileUtil();
    final ColorUtil colorUtil = new ColorUtil();
    final List<String> supportedLanguages = new ArrayList<>();
    final Table<String, String, String> langTable = HashBasedTable.create();
    String lang = "en";

    public String get(String id) {

        return colorUtil.makeColor(langTable.get(lang, id.toLowerCase()));

    }

    public List<String> getList(String id) {

        List<String> list = new ArrayList<>();

        for (int i = 1; i < Integer.MAX_VALUE; i++) {

            String str = get(id + "_" + i);

            if (str == null)
                break;

            list.add(str);

        }

        return list;

    }

    public void setLang(String lang) {

        LangChangeEvent event = new LangChangeEvent(this.lang, lang);
        Bukkit.getPluginManager().callEvent(event);

        this.lang = event.getTo();

    }

    public void load() {

        if (!folder.exists()) {

            folder.mkdirs();

            for (String lang : DEFAULT)
                try {

                    File langFile = new File(folder, lang + ".yml");
                    langFile.createNewFile();

                    fileUtil.copy(LangManager.class.getResourceAsStream(lang + ".yml"), langFile);

                } catch (IOException e) {

                    e.printStackTrace();

                }
        }

        for (File file : Objects.requireNonNull(folder.listFiles())) {

            String lang = file.getName().replace(".yml", "").toLowerCase();
            YamlConfiguration langConfig = YamlConfiguration.loadConfiguration(file);

            for (String key : langConfig.getKeys(false))
                langTable.put(lang, key, langConfig.getString(key));

        }

        String lang = plugin.getConfig().getString("lang");

        if (lang != null) {

            lang = lang.toLowerCase();

            if (!langTable.containsRow(lang))
                JMinesPlugin.getPluginLogger().log(Level.SEVERE, String.format("Lang %s not supported", lang));
            else
                this.lang = lang;

        }
    }
}