package azisaba.net.mmocore.listener;

import azisaba.net.mmocore.MMOCore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;

public class TutorialListener implements Listener {

    private final MMOCore core;
    public TutorialListener(MMOCore core) {
        this.core = core;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onShiftTutorial1(@NotNull PlayerToggleSneakEvent e) {

        if (!e.isSneaking()) return;
        Player p = e.getPlayer();

        Scoreboard scoreboardOrigin = p.getScoreboard();
        if (scoreboardOrigin.getObjective(p.getName().toLowerCase() + "tutorial") == null) return;

        p.playSound(p, Sound.ENTITY_ITEM_PICKUP, 1, 2);

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Component comp = Component.text("ワールド構成", NamedTextColor.AQUA).decoration(TextDecoration.BOLD, true);
        Objective obj = scoreboard.registerNewObjective(p.getName().toLowerCase() + "tutorial1", Criteria.DUMMY, comp, RenderType.INTEGER);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        if (obj.getScoreboard() == null) return;
        Score score1 = obj.getScore("建築ワールドは「world」という名前のワールドのみで");
        score1.setScore(10);

        Score score2 = obj.getScore("建築は全て、保護しないと立てることができません。");
        score2.setScore(9);

        Score score3 = obj.getScore("資源ワールドは「resource...」と続くワールドで");
        score3.setScore(8);

        Score score4 = obj.getScore("荒らし対応は一切行われません。");
        score4.setScore(7);

        Score score5 = obj.getScore("");
        score5.setScore(6);

        Score score6 = obj.getScore("また、PvEワールドも存在します、主に「Field...」と");
        score6.setScore(5);

        Score score7 = obj.getScore("つくワールドが対象です。");
        score7.setScore(4);

        Score score8 = obj.getScore("※PVEワールド以外は死亡時アイテムドロップします。");
        score8.setScore(3);

        Score score9 = obj.getScore("");
        score9.setScore(2);

        Score score10 = obj.getScore("続けるにShift(しゃがむ)してください。");
        score10.setScore(0);

        p.setScoreboard(scoreboard);
        Bukkit.getScheduler().runTaskLaterAsynchronously(core, obj::unregister, 600L);
    }

    @EventHandler
    public void onShiftTutorial2(@NotNull PlayerToggleSneakEvent e) {

        if (!e.isSneaking()) return;
        Player p = e.getPlayer();

        Scoreboard scoreboardOrigin = p.getScoreboard();
        if (scoreboardOrigin.getObjective(p.getName().toLowerCase() + "tutorial1") == null) return;

        p.playSound(p, Sound.ENTITY_ITEM_PICKUP, 1, 2);

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Component comp = Component.text("便利なコマンド", NamedTextColor.AQUA).decoration(TextDecoration.BOLD, true);
        Objective obj = scoreboard.registerNewObjective(p.getName().toLowerCase() + "tutorial2", Criteria.DUMMY, comp, RenderType.INTEGER);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        if (obj.getScoreboard() == null) return;
        Score score1 = obj.getScore("「/spawn」...初期スポーン地点に戻れます");
        score1.setScore(10);

        Score score2 = obj.getScore("「/sethome」、「/home」...Home地点設定ができます");
        score2.setScore(9);

        Score score3 = obj.getScore("「/home bed」...最後に寝たベッドにTPできます");
        score3.setScore(8);

        Score score4 = obj.getScore("「/tpa <MCID>」...対象にTPしていいか聞きます");
        score4.setScore(7);

        Score score5 = obj.getScore("「/recipes」...アイテムレシピを開きます");
        score5.setScore(6);

        Score score6 = obj.getScore("「/lvl」...自分の生活レベルを表示します");
        score6.setScore(5);

        Score score7 = obj.getScore("「/ah」...オークションを開きます");
        score7.setScore(4);

        Score score8 = obj.getScore("「/claim」...world限定で保護を作成します");
        score8.setScore(3);

        Score score9 = obj.getScore("また、木のシャベルでも保護することや、編集も可");
        score9.setScore(2);

        Score score10 = obj.getScore("木の棒を右クリで周囲の保護の確認もできます");
        score10.setScore(1);

        Score score11 = obj.getScore("チュートリアルは以上です。再度受注する場合は「/tutorial」");
        score11.setScore(0);

        p.setScoreboard(scoreboard);

        Bukkit.getScheduler().runTaskLaterAsynchronously(core, obj::unregister, 200L);
    }
}
