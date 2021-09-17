package main.dartanman.duels.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.dartanman.duels.Main;
import main.dartanman.duels.arenas.Arena;

import java.util.List;

import org.bukkit.ChatColor;

/**
 * DuelCmd - Executes the /duel command.
 * @author Austin Dart (Dartanman)
 *
 */
public class DuelCmd implements CommandExecutor{
	
	private Main plugin;
	
	/**
	 * Constructs the DuelCmd class with access to the Main class
	 * @param pl
	 *   The Main class
	 */
	public DuelCmd(Main pl) {
		plugin = pl;
	}
	
	/**
	 * Shows the help menu to the player. Hides commands they do not have permission for.
	 * @param player
	 *   The player to show the help menu to
	 * @param label
	 *   The exact command they used when they asked for the help menu
	 */
	private void showHelpMenu(Player player, String label) {
		player.sendMessage(ChatColor.BLUE + "Duels Help Menu" + ChatColor.WHITE + ":");
		player.sendMessage(ChatColor.GOLD + "/" + label + " help " + ChatColor.WHITE + "- " + ChatColor.YELLOW +
				"Shows this help menu.");
		if(player.hasPermission("duels.join"))
			player.sendMessage(ChatColor.GOLD + "/" + label + " join [arena] " + ChatColor.WHITE + "- " + ChatColor.YELLOW +
					"Join a duel if there are arenas available. You may specify an arena.");
		if(player.hasPermission("duels.leaderboard"))
			player.sendMessage(ChatColor.GOLD + "/" + label + " leaderboard " + ChatColor.WHITE + "- " + ChatColor.YELLOW +
					"Show the players with the top 10 amount of wins");
		if(player.hasPermission("duels.arenas.create"))
			player.sendMessage(ChatColor.GOLD + "/" + label + " arenas create <arenaName> " + ChatColor.WHITE + "- " + ChatColor.YELLOW +
					"Create an arena called <arenaName>");
		if(player.hasPermission("duels.arenas.setspawn")) {
			player.sendMessage(ChatColor.GOLD + "/" + label + " arenas setspawn1 <arenaName>" + ChatColor.WHITE + "- " + ChatColor.YELLOW +
					"Sets the first spawnpoint in <arenaName>");
			player.sendMessage(ChatColor.GOLD + "/" + label + " arenas setspawn2 <arenaName>" + ChatColor.WHITE + "- " + ChatColor.YELLOW +
					"Sets the second spawnpoint in <arenaName>");
		}
		if(player.hasPermission("duels.kits.create"))
			player.sendMessage(ChatColor.GOLD + "/" + label + " kits create <kitName> " + ChatColor.WHITE + "- " + ChatColor.YELLOW +
					"Create a kit with your current inventory.");
		if(player.hasPermission("duels.kits.delete"))
			player.sendMessage(ChatColor.GOLD + "/" + label + " kits delete <kitName> " + ChatColor.WHITE + "- " + ChatColor.YELLOW +
					"Deletes <kitname>");
		if(player.hasPermission("duels.kits.list"))
			player.sendMessage(ChatColor.GOLD + "/" + label + " kits list " + ChatColor.WHITE + "- " + ChatColor.YELLOW +
					"Lists all available kits.");
		if(player.hasPermission("duels.kits.select"))
			player.sendMessage(ChatColor.GOLD + "/" + label + " kits select <kitName> " + ChatColor.WHITE + "- " + ChatColor.YELLOW +
					"Select <kit> to be used in your next duel.");
	}
	
	/**
	 * Runs the command
	 */
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.NotAPlayerMessage")));
			return true;
		}
		Player player = (Player) sender;
		
		if(args.length == 0) {
			showHelpMenu(player, label);
			return true;
		}else if(args.length == 1) {
			if(args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
				showHelpMenu(player, label);
				return true;
			}else if(args[0].equalsIgnoreCase("join")){
				if(!player.hasPermission("duels.join")) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.NoPermissionMessage")));
					return true;
				}
				Arena check = plugin.getArenaManager().getArena(player);
				if(check != null) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.AlreadyInDuelMessage")));
					return true;
				}
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.AttemptJoinMessage")));
				for(Arena arena : plugin.getArenaManager().getArenaList()) {
					if(!arena.getActive()) {
						arena.addPlayer(player);
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.JoinedMessage")).replace("<arena>", arena.getName()));
						if(arena.canStart()) {
							arena.start();
						}
						return true;
					}
				}
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.NoArenaAvailableMessage")));
				return true;
			}else if(args[0].equalsIgnoreCase("leaderboard") || args[0].equalsIgnoreCase("lb") || args[0].equalsIgnoreCase("top")) {
				if(!player.hasPermission("duels.leaderboard")) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.NoPermissionMessage")));
					return true;
				}
				List<String> lb = plugin.getLBUtils().createLeaderboard();
				int i = 1;
				player.sendMessage(ChatColor.BLUE + "Duels Wins Leaderboard" + ChatColor.WHITE + ":");
				for(String s : lb) {
					player.sendMessage(i + ". " + s);
					i++;
				}
				return true;
			}else {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.IncorrectArgsMessage")));
				return true;	
			}
		}else if (args.length == 2) {
			if(args[0].equalsIgnoreCase("kit") || args[0].equalsIgnoreCase("kits")) {
				if(args[1].equalsIgnoreCase("list")) {
					if(!player.hasPermission("duels.kits.list")) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.NoPermissionMessage")));
						return true;
					}
					List<String> kitList = plugin.getKitManager().getKitNames();
					player.sendMessage(ChatColor.BLUE + "Kits" + ChatColor.WHITE + ":");
					for(String kit : kitList) {
						player.sendMessage(ChatColor.GREEN + kit);
					}
					return true;
				}else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.IncorrectArgsMessage")));
					return true;
				}
			}else if(args[0].equalsIgnoreCase("join")) {
				Arena check = plugin.getArenaManager().getArena(player);
				if(check != null) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.AlreadyInDuelMessage")));
					return true;
				}
				String arenaName = args[1];
				Arena arena = plugin.getArenaManager().getArena(arenaName);
				if(arena == null) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.NoArenaByThatNameMessage")).replace("<arena>", arenaName));
					return true;
				}else {
					if(arena.getActive()) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.ThatArenaIsFullMessage")));
						return true;
					}else {
						arena.addPlayer(player);
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.JoinedMessage")).replace("<arena>", arena.getName()));
						if(arena.canStart()) {
							arena.start();
						}
						return true;
					}
				}
			}else {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.IncorrectArgsMessage")));
				return true;
			}
		}else if (args.length == 3) {
			if(args[0].equalsIgnoreCase("kit") || args[0].equalsIgnoreCase("kits")) {
				if(args[1].equalsIgnoreCase("create")) {
					if(!player.hasPermission("duels.kits.create")) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.NoPermissionMessage")));
						return true;
					}
					String kitName = args[2];
					plugin.getKitManager().saveKit(player, kitName);
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.CreateKitMessage")).replace("<kit>", kitName.toLowerCase()));
					return true;
				}else if(args[1].equalsIgnoreCase("select") || args[1].equalsIgnoreCase("equip")) {
					if(!player.hasPermission("duels.kits.select")) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.NoPermissionMessage")));
						return true;
					}
					String kitName = args[2];
					if(!plugin.getKitManager().setKit(player, kitName)) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.NoKitByThatNameMessage")).replace("<kit>", kitName));
						return true;
					}else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.SelectKitMessage")).replace("<kit>", kitName));
						return true;
					}
				}else if(args[1].equalsIgnoreCase("delete")) {
					if(!player.hasPermission("duels.kits.delete")) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.NoPermissionMessage")));
						return true;
					}
					String kitName = args[2];
					if(!plugin.getKitManager().deleteKit(kitName)) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.NoKitByThatNameMessage")).replace("<kit>", kitName));
						return true;
					}else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.DeleteKitMessage")).replace("<kit>", kitName));
						return true;
					}
				}else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.IncorrectArgsMessage")));
					return true;
				}
			}else if(args[0].equalsIgnoreCase("arena") || args[0].equalsIgnoreCase("arenas")) {
				if(args[1].equalsIgnoreCase("create")) {
					if(!player.hasPermission("duels.arenas.create")) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.NoPermissionMessage")));
						return true;
					}
					String arenaName = args[2];
					Arena arena = plugin.getArenaManager().createArena(arenaName);
					plugin.getArenaManager().saveArenaToList(arena);
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.CreateArenaMessage")));
					return true;
				}else if(args[1].equalsIgnoreCase("setspawn1")) {
					if(!player.hasPermission("duels.arenas.setspawn")) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.NoPermissionMessage")));
						return true;
					}
					String arenaName = args[2];
					Arena arena = plugin.getArenaManager().getArena(arenaName);
					if(arena == null) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.NoArenaByThatNameMessage")).replace("<arena>", arenaName));
						return true;
					}
					arena.setSpawn1(player.getLocation());
					player.sendMessage(ChatColor.GREEN + "Spawn1 Set!");
					plugin.getArenaManager().saveArenaToFile(arena);
					return true;
				}else if(args[1].equalsIgnoreCase("setspawn2")) {
					if(!player.hasPermission("duels.arenas.setspawn")) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.NoPermissionMessage")));
						return true;
					}
					String arenaName = args[2];
					Arena arena = plugin.getArenaManager().getArena(arenaName);
					if(arena == null) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.NoArenaByThatNameMessage")).replace("<arena>", arenaName));
						return true;
					}
					arena.setSpawn2(player.getLocation());
					player.sendMessage(ChatColor.GREEN + "Spawn2 Set!");
					plugin.getArenaManager().saveArenaToFile(arena);
					return true;
				}else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.IncorrectArgsMessage")));
					return true;
				}
			}else {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.IncorrectArgsMessage")));
				return true;
			}
		}else {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.IncorrectArgsMessage")));
			return true;
		}
	}

}
