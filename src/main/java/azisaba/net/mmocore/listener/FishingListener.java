package azisaba.net.mmocore.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import static azisaba.net.mmocore.MMOCore.fishingCount;
import static azisaba.net.mmocore.MMOCore.stopFishing;

public class FishingListener implements Listener {

    @EventHandler
    public void onFish(@NotNull PlayerFishEvent e) {

        if (e.isCancelled()) return;

        Player p = e.getPlayer();

        if (stopFishing.contains(p.getName()) && e.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
            p.sendMessage(Component.text("AFKフィッシング判定の為、釣ることはできません。", NamedTextColor.RED));
            p.sendMessage(Component.text("リログすることで判定を消すことができます。", NamedTextColor.RED));
            e.setCancelled(true);
            return;
        }

        if (e.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
            int i = 0;
            if (fishingCount.containsKey(p.getName())) i = fishingCount.get(p.getName());
            i++;
            fishingCount.put(p.getName(), i);
        }
    }

    @EventHandler
    public void onRemove(@NotNull PlayerJoinEvent e) {
        Player p = e.getPlayer();
        stopFishing.remove(p.getName());
    }
}
