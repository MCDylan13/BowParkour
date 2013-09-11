package com.gmail.dylan354100mail.bowparkour;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.gmail.dylan354100mail.bowparkour.arena.Arena;
	
public class BowParkour extends JavaPlugin implements Listener{
	public final Logger logger = Logger.getLogger("Minecraft");
	public static BowParkour plugin;
	public Arena ar;
	
	@Override
	public void onDisable()  {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " a sido desactivado.");
		saveConfig();
	}
	
	@Override
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " a sido activado");
		getCommand("bp").setExecutor(new BowParkourCommandExecutor(this));
		this.saveDefaultConfig();
		getConfig();
		// Register a new listener
    getServer().getPluginManager().registerEvents(new Listener() {
    	@EventHandler
        public void playerJoin(PlayerJoinEvent event) {
            // On player join send them the message from config.yml
    		event.getPlayer().sendMessage(ChatColor.DARK_RED+"[Bow Parkour]"+ChatColor.AQUA+"El server utiliza Bow Parkour v1.0");
        }
    	@EventHandler
    	public void onSignChange(SignChangeEvent event) {
            Player p = event.getPlayer();
            if(event.getLine(0).contains("[BowParkour]")){
                 event.setLine(0, ChatColor.DARK_RED+"[BowParkour]");
                 if(event.getLine(1).contains("Join")){
                 event.setLine(1, "Click to Join");
                 event.setLine(2, "by MCDylan13");
                 p.sendMessage(ChatColor.BLUE + "Cartel creado correctamente");
            }else if(event.getLine(1).contains("Finish")){
            	event.setLine(1, "Click to Finish");
            	event.setLine(2, "by MCDylan13");
            	}
            }
    	}
      //handle sign click
    	@EventHandler
    	public void onSignClick(PlayerInteractEvent e)
    	{
    		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {return;}
    		if (!(e.getClickedBlock().getState() instanceof Sign)) {return;}
    		Sign sign = (Sign) e.getClickedBlock().getState();
    		Player player = e.getPlayer();
		    ItemStack bow = new ItemStack(Material.BOW, 1);
		    ItemStack arrow = new ItemStack(Material.ARROW, 1);
			if (sign.getLine(0).equals(ChatColor.DARK_RED+"[BowParkour]")){
    			if (sign.getLine(1).equals("Click to Join")){
    				Arena.setPlayerMode(player.getGameMode());
    				Arena.setPlayerLoc(player.getLocation());
    				Arena.setBedSpawn(player.getBedSpawnLocation());
    				player.teleport(Arena.getSpawnGame());
    				player.setBedSpawnLocation(Arena.spawnpoint, true);
    				player.setGameMode(GameMode.ADVENTURE);
    				PlayerInventory inventory = player.getInventory(); // El inventorio del jugador
    			    bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
    			    inventory.addItem(bow); // Agregar el stack.
    			    inventory.addItem(arrow); // Agregar el stack.
    			    player.sendMessage(ChatColor.DARK_RED+"[Bow Parkour]"+ChatColor.AQUA+"Aviso: Si no te aparece los objetos pulsa Q y agarralos.");
    			    Bukkit.broadcastMessage(ChatColor.DARK_RED+"[Bow Parkour]"+ChatColor.AQUA+player.getDisplayName()+" a entrado al juego.");
    			}else if (sign.getLine(1).equals("Click to Finish")){
    				bow.removeEnchantment(Enchantment.ARROW_INFINITE);
    				player.teleport(Arena.getLobby());
    				player.setGameMode(Arena.getPlayerMode());
    				player.setBedSpawnLocation(Arena.getBedspawn());
    				PlayerInventory inventory = player.getInventory(); // El inventorio del jugador
    			    ItemStack premio = new ItemStack(Material.DIAMOND, 1); // Un stack de diamantes
    			    inventory.clear();
    			    inventory.addItem(premio);
    			    player.sendMessage(ChatColor.DARK_RED+"[Bow Parkour]"+ChatColor.AQUA+"Aviso: Si no te aparece el premio da click con el arco y las flechas.");
    			    Bukkit.broadcastMessage(ChatColor.DARK_RED+"[Bow Parkour]"+ChatColor.AQUA+player.getDisplayName()+" a terminado el Bow Parkour.");
    			}
    		}
    	
    	}
    }, this);
	}
}

	
