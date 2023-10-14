package azisaba.net.mmocore.listener;

import azisaba.net.mmocore.MMOCore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

public class WorldListener implements Listener {

    private final MMOCore core;
    public WorldListener(MMOCore core) {
        this.core = core;
    }

    @EventHandler
    public void onBorder(@NotNull EntityDamageEvent e) {

        if (e.getEntity() instanceof Player p &&
                e.getCause().equals(EntityDamageEvent.DamageCause.WORLD_BORDER) &&
                !p.getGameMode().equals(GameMode.CREATIVE) &&
                !p.getGameMode().equals(GameMode.SPECTATOR)) {

            WorldBorder w = p.getWorld().getWorldBorder();
            if (w.isInside(p.getLocation())) return;

            p.teleport(new Location(Bukkit.getWorld("world"), 0.5, 64, 0.5, 180, 0));
            p.sendMessage(Component.text("おっと、世界の端っこに着いちゃったみたい！", NamedTextColor.RED));
        }
    }
}
