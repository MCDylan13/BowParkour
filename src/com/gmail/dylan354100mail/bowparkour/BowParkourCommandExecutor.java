package com.gmail.dylan354100mail.bowparkour;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.util.Vector;

import com.gmail.dylan354100mail.bowparkour.arena.*;


public class BowParkourCommandExecutor implements CommandExecutor {
	public BowParkour plugin;
	public BowParkourCommandExecutor(BowParkour plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player){
			ScoreboardManager manager = Bukkit.getScoreboardManager();
	        Scoreboard bowscore = manager.getNewScoreboard();
	        Objective objective = bowscore.registerNewObjective("kills", "playerKillCount");
	        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	        objective.setDisplayName(""+ChatColor.RED+ChatColor.BOLD+"Bow Parkour Kills");
			Player player = (Player) sender;
			ItemStack bow = new ItemStack(Material.BOW, 1); // Un stack de diamantes
		    ItemStack arrow = new ItemStack(Material.ARROW, 1); // Un stack de diamantes
		    PlayerInventory inventory = player.getInventory(); // El inventorio del jugador
		    if(args.length == 0){
				sender.sendMessage(ChatColor.GOLD+"========== "+ChatColor.DARK_RED+"Bow Parkour "+ChatColor.GOLD+"==========");
				sender.sendMessage(ChatColor.AQUA+"Creado por MCDylan13 v1.0");
				sender.sendMessage(ChatColor.AQUA+"Para ver los comandos pon /bp help");
				sender.sendMessage(ChatColor.GOLD+"================================");
			}
			if(player.hasPermission("bowparkour.admin")){
			if(args.length == 1 && args[0].equalsIgnoreCase("help")){
				sender.sendMessage(ChatColor.GOLD+"=============== Comandos ===============");
				sender.sendMessage("/bp help - Muestra la lista de comandos del plugin.");
				sender.sendMessage("/bp join - Entra al juego");
				sender.sendMessage("/bp setspawn - Establece el spawn al comenzar el juego.");
				sender.sendMessage("/bp sign - Muestra la ayuda para los carteles");
				sender.sendMessage("/bp setend - Establece el lugar de salida.");
				sender.sendMessage("/bp load - Carga los spawns de la arena.");
				
			}
			if(args.length == 1 && args[0].equalsIgnoreCase("setend")){
				Arena.setLobby(player.getLocation());
				plugin.getConfig().set("SpawnEnd", Arena.lobby.toVector());
				plugin.saveConfig();
				sender.sendMessage("Final establecido.");
			}
			if(args.length == 1 && args[0].equalsIgnoreCase("sign")){
				sender.sendMessage(ChatColor.AQUA+"Informacion sobre los carteles");
				sender.sendMessage("Para poder poner un cartel pon en la primera linea [BowParkour]");
				sender.sendMessage("Para poner un cartel de entrada en la segunda linea pon Join");
				sender.sendMessage("Para poder poner un cartel de terminado pon Finish");
				sender.sendMessage("Este ultimo se pone en el lugar en donde termina el parkour, al apretarlo ganas");
			}
			if(args.length == 1 && args[0].equalsIgnoreCase("setspawn")){
				if (Arena.setSpawnPoint(player.getLocation()))
				{
					plugin.getConfig().set("SpawnGame", Arena.spawnpoint.toVector());
					plugin.saveConfig();
					sender.sendMessage("Spawn establecido");
				return true;
			}
		}
	}else{
		sender.sendMessage("No tienes los permisos para usar el comando.");
	}
			if(args.length == 1 && args[0].equalsIgnoreCase("join")){
				Arena.setScoreboard(player.getScoreboard());
				player.getPlayer().setScoreboard(bowscore);
				Arena.setPlayerLoc(player.getLocation());
				Arena.setBedSpawn(player.getBedSpawnLocation());
				Arena.setPlayerMode(player.getGameMode());
				player.teleport(Arena.getSpawnGame());
				player.setBedSpawnLocation(Arena.spawnpoint, true);
				player.setGameMode(GameMode.ADVENTURE);
			    bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
			    inventory.addItem(bow); // Agregar el stack.
			    inventory.addItem(arrow); // Agregar el stack.
			    player.sendMessage(ChatColor.AQUA+"Si no aparecen el arco y la flecha apreta Q y agarralos.");
				Bukkit.broadcastMessage(ChatColor.DARK_RED+"[Bow Parkour]"+ChatColor.AQUA+player.getDisplayName()+ChatColor.AQUA+" a entrado al juego.");
			}
			if(args.length == 1 && args[0].equalsIgnoreCase("leave")){
				bowscore.resetScores(player);
				player.setScoreboard(Arena.getScoreboard()); //manager.getNewScoreboard() will return a blank scoreboard
				bow.removeEnchantment(Enchantment.ARROW_INFINITE);
				player.teleport(Arena.getPlayerLoc());
				player.setGameMode(Arena.getPlayerMode());
				player.setBedSpawnLocation(Arena.getBedspawn());
				inventory.clear();
			    Bukkit.broadcastMessage(ChatColor.DARK_RED+"[Bow Parkour]"+ChatColor.AQUA+player.getDisplayName()+ChatColor.AQUA+" a salido del juego.");
			}
			if(player.hasPermission("bowparkour.admin")){
			if(args.length == 1 && args[0].equalsIgnoreCase("load")){
				Vector v = plugin.getConfig().getVector("SpawnGame", null);
				Arena.spawnpoint = new Location(player.getWorld(), v.getX(), v.getY(), v.getZ());
				Vector vend = plugin.getConfig().getVector("SpawnEnd", null);
				Arena.lobby = new Location(player.getWorld(), vend.getX(), vend.getY(), vend.getZ());
				player.sendMessage("Spawns cargados correctamente");
			}
			}
		}else{
			sender.sendMessage("Solo un jugador puede utilizarlo.");
		return true;
		}
	return false;
	}
}