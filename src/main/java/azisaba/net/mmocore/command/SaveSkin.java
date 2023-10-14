package azisaba.net.mmocore.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SaveSkin implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender instanceof Player p) {

            if (!p.hasPermission("azisaba.command.saveSkin")) {
                p.sendMessage(Component.text("権限がありません。", NamedTextColor.RED));
                return true;

            } else {
                if (strings.length == 2) {

                    String name = strings[0];
                    String savePng = strings[1];

                    if (!savePng.contains(".png")) {
                        savePng = savePng + ".png";
                    }

                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "savedisguise " + name + " player boss setSkin " + savePng + " setDynamicName");
                    p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&a" + name + "という名前で" + savePng + "を保存しました。"));
                    return true;
                }
            }
        } else {
            if (strings.length == 2) {

                String name = strings[0];
                String savePng = strings[1];

                if (savePng.contains(".png")) {
                    savePng = savePng + ".png";
                }

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "savedisguise " + name + " player boss setSkin " + savePng + " setDynamicName");
                commandSender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&a" + name + "という名前で" + savePng + "を保存しました。"));
                return true;
            }
        }

        commandSender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&c/setSkin <セーブ後使用する名前> <セーブするpng名(.pngがない場合は後付けします。)>"));
        return true;
    }
}
