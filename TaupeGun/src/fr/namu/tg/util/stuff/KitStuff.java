package fr.namu.tg.util.stuff;

import fr.namu.tg.InfoTG;
import fr.namu.tg.MainTG;
import fr.namu.tg.PlayerTG;
import fr.namu.tg.enums.KitTG;
import fr.namu.tg.enums.StateTG;
import fr.namu.tg.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class KitStuff {

    private MainTG main;
    private Random rand = new Random();

    public KitStuff(MainTG main) {
        this.main = main;
    }

    public void setKit(Player player) {
        PlayerTG ptg = this.main.playertg.get(player.getUniqueId());

        List<KitTG> kits = Arrays.asList(KitTG.values());

        ptg.setKit(kits.get(rand.nextInt(kits.size())));
    }

    public void giveKit(Player player) {
        PlayerTG ptg = this.main.playertg.get(player.getUniqueId());
        Inventory inv = player.getInventory();

        if(!this.main.info.getState().equals(StateTG.TAUPE))
            return;
        if (ptg.getHasClaimedKit())
            return;

        ptg.setHasClaimedKit(true);
        player.sendMessage(InfoTG.prefix + "§aVous avez reçu votre Équipement avec succès !");

        switch (ptg.getKit()) {
            case MINOR:
                inv.addItem(new ItemBuilder(Material.DIAMOND_PICKAXE, 1).addEnchant(Enchantment.DIG_SPEED, 3).toItemStack());
                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 0, false, false));
                break;
            case ALCHEMIST:
                inv.addItem(ItemBuilder.splashPotion(PotionType.POISON, 1, false));
                break;
        }
    }
}
