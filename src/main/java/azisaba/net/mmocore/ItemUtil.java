package azisaba.net.mmocore;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ItemUtil {

    @NotNull
    public static List<ItemStack> getVoteItemList() {

        List<ItemStack> list = new ArrayList<>();
        for (String s : MMOCore.get().getConfig().getStringList("vote-reward")) {
            if (s == null) continue;
            ItemStack item = azisaba.net.mmoutils.utils.MythicUtil.getMythicItem(s);
            if (item == null) continue;
            list.add(item);
        }
        return list;
    }
}
