package fr.namu.tg.listener;

import fr.namu.tg.MainTG;
import fr.namu.tg.enums.ScenarioTG;
import fr.namu.tg.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PrepareItemEvent implements Listener {

    private MainTG main;

    public PrepareItemEvent(MainTG main) {
        this.main = main;
    }

    @EventHandler
    public void onPrepareItem(PrepareItemCraftEvent event) {
        Material mat = event.getInventory().getResult().getType();

        if(ScenarioTG.RODLESS.getValue()) {
            if(mat.equals(Material.FISHING_ROD))
                event.getInventory().setResult(new ItemBuilder(Material.AIR, 1).toItemStack());
        }
        if(ScenarioTG.HASTEY_BOYS.getValue()) {
            switch (mat) {
                case WOOD_PICKAXE:
                case STONE_PICKAXE:
                    event.getInventory().setResult(new ItemBuilder(Material.STONE_PICKAXE, 1)
                            .addEnchant(Enchantment.DURABILITY, 3).addEnchant(Enchantment.DIG_SPEED, 3).toItemStack());
                    break;
                case WOOD_AXE:
                case STONE_AXE:
                    event.getInventory().setResult(new ItemBuilder(Material.STONE_AXE, 1)
                            .addEnchant(Enchantment.DURABILITY, 3).addEnchant(Enchantment.DIG_SPEED, 3).toItemStack());
                    break;
                case WOOD_SPADE:
                case STONE_SPADE:
                    event.getInventory().setResult(new ItemBuilder(Material.STONE_SPADE, 1)
                            .addEnchant(Enchantment.DURABILITY, 3).addEnchant(Enchantment.DIG_SPEED, 3).toItemStack());
                    break;
                case IRON_PICKAXE:
                    event.getInventory().setResult(new ItemBuilder(Material.IRON_PICKAXE, 1)
                            .addEnchant(Enchantment.DURABILITY, 3).addEnchant(Enchantment.DIG_SPEED, 3).toItemStack());
                    break;
                case IRON_AXE:
                    event.getInventory().setResult(new ItemBuilder(Material.IRON_AXE, 1)
                            .addEnchant(Enchantment.DURABILITY, 3).addEnchant(Enchantment.DIG_SPEED, 3).toItemStack());
                    break;
                case IRON_SPADE:
                    event.getInventory().setResult(new ItemBuilder(Material.IRON_SPADE, 1)
                            .addEnchant(Enchantment.DURABILITY, 3).addEnchant(Enchantment.DIG_SPEED, 3).toItemStack());
                    break;
                case DIAMOND_PICKAXE:
                    event.getInventory().setResult(new ItemBuilder(Material.DIAMOND_PICKAXE, 1)
                            .addEnchant(Enchantment.DURABILITY, 3).addEnchant(Enchantment.DIG_SPEED, 3).toItemStack());
                    break;
                case DIAMOND_AXE:
                    event.getInventory().setResult(new ItemBuilder(Material.DIAMOND_AXE, 1)
                            .addEnchant(Enchantment.DURABILITY, 3).addEnchant(Enchantment.DIG_SPEED, 3).toItemStack());
                    break;
                case DIAMOND_SPADE:
                    event.getInventory().setResult(new ItemBuilder(Material.DIAMOND_SPADE, 1)
                            .addEnchant(Enchantment.DURABILITY, 3).addEnchant(Enchantment.DIG_SPEED, 3).toItemStack());
                    break;

            }
        }

    }

    @EventHandler
    public void onBurn(FurnaceBurnEvent event) {
        if (ScenarioTG.FASTSMELTING.getValue())
            handleCookingTime((Furnace)event.getBlock().getState());
    }

    private void handleCookingTime(final Furnace block) {
        (new BukkitRunnable() {
            public void run() {
                if (block.getCookTime() > 0 || block.getBurnTime() > 0) {
                    block.setCookTime((short)(block.getCookTime() + 8));
                    block.update();
                } else {
                    cancel();
                }
            }
        }).runTaskTimer((Plugin)this.main, 1L, 1L);
    }
}
