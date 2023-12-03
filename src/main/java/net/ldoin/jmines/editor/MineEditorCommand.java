package net.ldoin.jmines.editor;

import lombok.Value;
import net.ldoin.jmines.lang.LangManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Value
public class MineEditorCommand implements CommandExecutor {

    MineEditor mineEditor;
    LangManager langManager;

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String str, @NotNull String[] args) {

        if (!str.equalsIgnoreCase("jmines"))
            return false;

        if (!(sender instanceof Player)) {

            sender.sendMessage(langManager.get("not_player"));
            return false;

        }

        if (!sender.hasPermission("jmines.editor")) {

            sender.sendMessage(langManager.get("no_permissions"));
            return false;

        }

        mineEditor.openMainPage((Player) sender);

        return true;

    }
}