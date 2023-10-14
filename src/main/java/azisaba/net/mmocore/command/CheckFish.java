package azisaba.net.mmocore.command;

import azisaba.net.mmocore.MMOCore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static azisaba.net.mmocore.MMOCore.fishingCount;
import static azisaba.net.mmocore.MMOCore.passwordMap;

public class CheckFish implements TabExecutor {

    private final MMOCore core;
    public CheckFish(MMOCore core) {
        this.core = core;
    }

    private static final Set<String> ctSet = new HashSet<>();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {

        if (strings.length != 1) return true;
        if (commandSender instanceof Player p) {

            if (ctSet.contains(p.getName())) return true;
            if (ctSet.contains(p.getName())) {
                ctSet.add(p.getName());
                Bukkit.getScheduler().runTaskLater(core, ()-> ctSet.remove(p.getName()), 1200L);
            }
            if (!passwordMap.containsKey(p.getName())) return true;

            int get = passwordMap.get(p.getName());
            if (String.valueOf(get).equalsIgnoreCase(strings[0])) {

                p.sendMessage(Component.text("ご協力ありがとうございます。報酬金は300$です。", NamedTextColor.GOLD));
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + p.getName() + " 300");
                fishingCount.remove(p.getName());
                passwordMap.remove(p.getName());
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player p)) return Collections.emptyList();
        if (strings.length == 1 && passwordMap.containsKey(p.getName())) return Collections.singletonList(String.valueOf(passwordMap.get(p.getName())));
        return Collections.emptyList();
    }
}
