package xyz.blujay.autototem;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;
import xyz.blujay.autototem.classes.PlayerCooldown;
import xyz.blujay.autototem.enums.ResetReason;
import xyz.blujay.autototem.utilities.ChatUtility;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class AutoTotemAPI {

    //region Behavior Variables
    private int coolDown;
    public boolean includeVanillaTotemsInCooldown;
    private Map<UUID, PlayerCooldown> coolDowns;
    public boolean hotBarOnly;
    public boolean resetCooldownOnDeath;
    //endregion

    //region Message Variables
    public String prefix;

    public boolean sendCooldownStartMessage;
    public String cooldownStartMessage;

    public boolean sendCooldownEndMessage;
    public String cooldownEndMessage;

    public boolean sendResetOnDeathMessage;
    public String resetOnDeathMessage;
    //endregion

    AutoTotemAPI(FileConfiguration config){
        setConfigOptions(config);
    }

    public void setConfigOptions(FileConfiguration config){
        this.coolDown = config.getInt("cooldown");
        this.includeVanillaTotemsInCooldown = config.getBoolean("includeVanillaTotemsInCooldown");
        this.hotBarOnly = config.getBoolean("hotBarOnly");
        this.coolDowns = new HashMap<>();
        this.resetCooldownOnDeath = config.getBoolean("resetCooldownOnDeath");

        this.prefix = ChatUtility.colorize(config.getString("prefix"));

        this.sendCooldownStartMessage = config.getBoolean("sendCooldownStartMessage");
        this.cooldownStartMessage = ChatUtility.colorize(Objects.requireNonNull(config.getString("cooldownStartMessage")).replace("[TIME]", String.valueOf(coolDown)));

        this.sendCooldownEndMessage = config.getBoolean("sendCooldownEndMessage");
        this.cooldownEndMessage = ChatUtility.colorize(config.getString("cooldownEndMessage"));

        this.sendResetOnDeathMessage = config.getBoolean("sendResetOnDeathMessage");
        this.resetOnDeathMessage = ChatUtility.colorize(config.getString("resetOnDeathMessage"));
    }

    //@return True if the player will use a totem when they die
    public boolean canUseTotem(Player player){

        var totem = new ItemStack(Material.TOTEM_OF_UNDYING);
        var playerInv = player.getInventory();
        boolean playerHasTotem;

        if(hotBarOnly){
            var offHand = playerInv.getItemInOffHand();
            var hotBar1 = playerInv.getItem(0);
            var hotBar2 = playerInv.getItem(1);
            var hotBar3 = playerInv.getItem(2);
            var hotBar4 = playerInv.getItem(3);
            var hotBar5 = playerInv.getItem(4);
            var hotBar6 = playerInv.getItem(5);
            var hotBar7 = playerInv.getItem(6);
            var hotBar8 = playerInv.getItem(7);
            var hotBar9 = playerInv.getItem(8);

            playerHasTotem = ((offHand.isSimilar(totem)) ||
                    (hotBar1 != null && hotBar1.getType().equals(Material.TOTEM_OF_UNDYING)) ||
                    (hotBar2 != null && hotBar2.getType().equals(Material.TOTEM_OF_UNDYING)) ||
                    (hotBar3 != null && hotBar3.getType().equals(Material.TOTEM_OF_UNDYING)) ||
                    (hotBar4 != null && hotBar4.getType().equals(Material.TOTEM_OF_UNDYING)) ||
                    (hotBar5 != null && hotBar5.getType().equals(Material.TOTEM_OF_UNDYING)) ||
                    (hotBar6 != null && hotBar6.getType().equals(Material.TOTEM_OF_UNDYING)) ||
                    (hotBar7 != null && hotBar7.getType().equals(Material.TOTEM_OF_UNDYING)) ||
                    (hotBar8 != null && hotBar8.getType().equals(Material.TOTEM_OF_UNDYING)) ||
                    (hotBar9 != null && hotBar9.getType().equals(Material.TOTEM_OF_UNDYING)) );
        }
        else{
            playerHasTotem = player.getInventory().contains(Material.TOTEM_OF_UNDYING);
        }

        return playerHasTotem && player.hasPermission("autototem.use") && !isPlayerOnCoolDown(player);
    }

    public boolean isPlayerOnCoolDown(Player player){
        var playerCooldown = getCoolDown(player);
        if(playerCooldown == null || !playerCooldown.coolDownStarted){
            return false;
        }
        else{
            return playerCooldown.coolDown.isAfter(Instant.now());
        }
    }

    //@return the PlayerCooldown of the given player, or null if the player does not have a cooldown set yet.
    public PlayerCooldown getCoolDown(Player player){
        return coolDowns.get(player.getUniqueId());
    }

    //Marks the player's cooldown as started.
    public void startCooldown(Player player, boolean isVanillaTotem){
        var playerCooldown = getCoolDown(player);
        if(playerCooldown != null && !playerCooldown.bypassCooldown){
            playerCooldown.coolDownStarted = true;
            if(sendCooldownStartMessage && coolDown > 0 && (includeVanillaTotemsInCooldown || !isVanillaTotem)){
                player.sendMessage(prefix + cooldownStartMessage);
            }
        }
    }

    //Set a player's totem cooldown X seconds into the future.
    public void setCoolDown(Player player, int coolDown){
        var coolDownTime = Instant.now().plusSeconds(coolDown);
        coolDowns.put(player.getUniqueId(), new PlayerCooldown(coolDownTime, player.hasPermission("autototem.bypass")) );

        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskLater(AutoTotem.getInstance(), () -> resetCooldown(player, ResetReason.REASON_COOLDOWN_END, coolDownTime), coolDown * 20L);
    }

    //Set a player's totem cool down to the default number of seconds into the future.
    public void setCoolDown(Player player){
        setCoolDown(player, coolDown);
    }

    //Removes the player's cooldown from the map, reason controls the message sent. If coolDownExpiry is not null, will only reset cooldown if times match.
    public void resetCooldown(Player player, ResetReason reason, Instant coolDownExpiry){
        var playerCooldown = getCoolDown(player);

        if((coolDown > 0) && (playerCooldown != null) && (coolDownExpiry == null || coolDownExpiry.equals(playerCooldown.coolDown))){
            if(!playerCooldown.bypassCooldown){
                if(reason == ResetReason.REASON_DEATH && sendResetOnDeathMessage){
                    player.sendMessage(prefix + resetOnDeathMessage);
                }
                else if(reason == ResetReason.REASON_COOLDOWN_END && sendCooldownEndMessage){
                    player.sendMessage(prefix + cooldownEndMessage);
                }
            }
            coolDowns.remove(player.getUniqueId());
        }
    }
}

