package fr.namu.tg.listener;

import fr.namu.tg.MainTG;
import fr.namu.tg.PlayerTG;
import fr.namu.tg.enums.ScenarioTG;
import fr.namu.tg.enums.StateTG;
import fr.namu.tg.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakEvent implements Listener {

    private MainTG main;

    public BreakEvent(MainTG main) {
        this.main = main;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        PlayerTG ptg = this.main.playertg.get(player.getUniqueId());

        Material mat = event.getBlock().getType();

        if(this.main.info.getState().equals(StateTG.LOBBY) || this.main.info.getState().equals(StateTG.END)) {
            event.setCancelled(true);
            return;
        }

        switch (mat) {
            case DIAMOND_ORE:
                if(ScenarioTG.DIAMONDLIMIT.getNumber() != 0) {
                    if(ptg.getDiamondleft() == 0) {
                        event.setCancelled(true);
                        event.getBlock().setType(Material.AIR);
                        event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemBuilder(Material.GOLD_INGOT, 1).toItemStack());
                        return;
                    }
                    ptg.hasMinedDiamond();
                }

        }
    }
}
