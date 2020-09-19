package fr.namu.tg.listener;

import fr.namu.tg.MainTG;
import fr.namu.tg.enums.ScenarioTG;
import fr.namu.tg.enums.StateTG;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class ItemSpawnEvent implements Listener {

    private MainTG main;

    public ItemSpawnEvent(MainTG main) {
        this.main = main;
    }

    @EventHandler
    public void onItemSpawn(org.bukkit.event.entity.ItemSpawnEvent event) {
        if(ScenarioTG.CUT_CLEAN.getValue()) {
            ItemStack item = event.getEntity().getItemStack();
            Material mat = item.getType();
            switch (mat) {
                case COAL:
                    item.setType(Material.TORCH);
                    break;
                case IRON_ORE:
                    item.setType(Material.IRON_INGOT);
                    break;
                case GOLD_ORE:
                    item.setType(Material.GOLD_INGOT);
                    break;
                case GRAVEL:
                    item.setType(Material.FLINT);
                    break;
                case PORK:
                    item.setType(Material.GRILLED_PORK);
                    break;
                case RAW_BEEF:
                    item.setType(Material.COOKED_BEEF);
                    break;
                case RAW_CHICKEN:
                    item.setType(Material.COOKED_CHICKEN);
                    break;
                case MUTTON:
                    item.setType(Material.COOKED_MUTTON);
                    break;
                case RABBIT:
                    item.setType(Material.COOKED_RABBIT);
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if(this.main.info.getState().equals(StateTG.LOBBY) && event.getPlayer().getGameMode() == GameMode.ADVENTURE) {
            event.setCancelled(true);
        }
    }

}
