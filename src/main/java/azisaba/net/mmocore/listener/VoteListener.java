package azisaba.net.mmocore.listener;

import azisaba.net.mmocore.ItemUtil;
import azisaba.net.mmocore.MMOCore;
import azisaba.net.mmoutils.utils.PlayerUtil;
import com.vexsoftware.votifier.model.VotifierEvent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class VoteListener implements Listener {

    private final MMOCore core;
    public VoteListener(MMOCore core) {
        this.core = core;
    }

    @EventHandler
    public void onVote(@NotNull VotifierEvent e) {
        processVotePacket(core, e.getVote().getUsername(), e.getVote().getServiceName());
    }

    @EventHandler
    public void onJoin(@NotNull PlayerJoinEvent e) {

        Bukkit.getScheduler().runTaskLater(core, () -> {

            FileConfiguration config = core.getMMOConfig();

            Player player = Bukkit.getPlayer(e.getPlayer().getUniqueId());
            if (player == null || !player.isOnline()) {
                return;
            }
            String id = player.getUniqueId().toString();
            int i = 0;
            if (config.contains(id)) i = config.getInt(id);
            if (i == 0) return;

            config.set(id, 0);
            core.saveMMOConfig();
            processVotes(core, player, i);

        }, 20 * 10);
    }

    public static synchronized void processVotePacket(JavaPlugin plugin, String userName, String serviceName) {

        Player p = Bukkit.getPlayerExact(userName);
        Bukkit.broadcast(LegacyComponentSerializer.legacyAmpersand()
                .deserialize("&6[&4Broadcast&6] &2Thanks &c" + userName + " &2for voting on " + serviceName));

        String id = Bukkit.getOfflinePlayer(userName).getUniqueId().toString();
        Configuration config = MMOCore.get().getMMOConfig();
        int i = 0;
        if (config.contains(id)) i = config.getInt(id);
        i++;
        if (p == null) {
            config.set(id, i);
            MMOCore.get().saveMMOConfig();
            return;
        }
        p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&a" + serviceName + "で投票していただきありがとうございます！"));
        config.set(id, 0);
        MMOCore.get().saveMMOConfig();
        processVotes(plugin, p, i);
    }

    private static void processVotes(@NotNull JavaPlugin plugin, @NotNull Player player, long count) {
        plugin.getLogger().info("Processing vote from " + player.getName() + " (count: " + count + ")");
        for (long i = 0; i < count; i++) {
            for (ItemStack item : ItemUtil.getVoteItemList()) {
                PlayerUtil.dropPlayer(player, item);
            }
        }
        player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&a" + count + "回分の投票報酬を受け取りました。"));
    }
}
