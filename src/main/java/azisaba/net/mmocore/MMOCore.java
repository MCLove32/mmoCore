package azisaba.net.mmocore;

import azisaba.net.mmocore.command.SaveSkin;
import azisaba.net.mmocore.listener.FishingListener;
import azisaba.net.mmocore.listener.TutorialListener;
import azisaba.net.mmocore.listener.VoteListener;
import azisaba.net.mmocore.listener.WorldListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public final class MMOCore extends JavaPlugin {

    private static MMOCore core;
    private File file;
    private FileConfiguration fileConfiguration;
    public static final Map<String, Integer> fishingCount = new HashMap<>();
    public static final Map<String, Integer> passwordMap = new HashMap<>();
    public static final Set<String> stopFishing = new HashSet<>();
    public static Random ran = new Random();

    @Override
    public void onEnable() {
        // Plugin startup logic
        core = this;
        saveDefaultConfig();
        create();

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new VoteListener(this), this);
        pm.registerEvents(new WorldListener(this), this);
        pm.registerEvents(new FishingListener(), this);
        pm.registerEvents(new TutorialListener(this), this);

        Objects.requireNonNull(getCommand("saveSkin")).setExecutor(new SaveSkin());

        checkAFK();
    }

    public static MMOCore get() {return core;}

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public FileConfiguration getMMOConfig() {
        return fileConfiguration;
    }

    private void create() {
        file = new File(getDataFolder(), "votes.yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            saveResource("votes.yml", false);
        }

        fileConfiguration = new YamlConfiguration();
        try {
            fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveMMOConfig() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkAFK() {

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, ()-> Bukkit.getOnlinePlayers().forEach(player -> {

            if (!fishingCount.containsKey(player.getName())) return;

            int count = fishingCount.get(player.getName());
            int check = ran.nextInt(25) + 90;
            int password = ran.nextInt(10000);
            if (count >= check && !stopFishing.contains(player.getName())) {

                passwordMap.put(player.getName(), password);
                player.sendMessage(Component.text("Auto-Fish対策用メッセージです。下のメッセージを30秒以内にクリックしてください。", NamedTextColor.WHITE).decoration(TextDecoration.BOLD, true));
                player.sendMessage(Component.text("「確認した!」", NamedTextColor.GREEN).decoration(TextDecoration.BOLD, true).clickEvent(ClickEvent.runCommand("/checkcommand " + password)));
                player.sendMessage(Component.text("※クリックできない場合は「/checkcommand " + password + "」", NamedTextColor.GRAY));

                Bukkit.getScheduler().runTaskLaterAsynchronously(this, () -> {
                    if (fishingCount.containsKey(player.getName()) && fishingCount.get(player.getName()) >= check) stopFishing.add(player.getName());
                }, 600L);
            }
        }), 1200L, 1200L);
    }
}
