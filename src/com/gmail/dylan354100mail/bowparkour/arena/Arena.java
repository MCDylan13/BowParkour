package com.gmail.dylan354100mail.bowparkour.arena;

import java.io.File;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import com.gmail.dylan354100mail.bowparkour.BowParkour;

public class Arena{
	public static Vector SpawnGame = null;
	public static Vector SpawnEnd = null;
	public static BowParkour plugin;
	public File arenafile;
	public Arena(BowParkour plugin){
	arenafile = new File("plugins/BowParkour/config.yml");
	}
	public static Location lobby = null;
	public static Location getLobby()
	{
		return lobby;
	}
	public static Location spawnpoint = null;
	protected Location getSpawnPoint()
	{
		return spawnpoint;
	}

	public static Location getSpawnGame()
	{
		return spawnpoint;
	}
	
	public static boolean setSpawnPoint(Location loc) {
		spawnpoint = loc;
		return true;
	}
	public static Location Playerloc;
	public static GameMode PlayerMode;
	public static Location getPlayerLoc()
	{
		return Playerloc;
	}
	public static GameMode getPlayerMode()
	{
		return PlayerMode;
	}
	public static boolean setPlayerLoc(Location loc)
	{
		Playerloc = loc;
		return true;
	}
	public static boolean setPlayerMode(GameMode loc)
	{
		PlayerMode = loc;
		return true;
	}
	public static boolean setLobby(Location loc) {
		lobby = loc;
		return true;
	}
	public static Location BedSpawn;
	public static Location getBedspawn()
	{
		return BedSpawn;
	}
	public static boolean setBedSpawn(Location loc)
	{
		BedSpawn = loc;
		return true;
	}
}