package com.aehdev.worldguardcitymigrator.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aehdev.worldguardcitymigrator.Point;
import com.aehdev.worldguardcitymigrator.Selection;
import com.aehdev.worldguardcitymigrator.WorldGuardCityMigrator;

/**
 * Selects a From point for migration.
 */
public class CommandWGMigrateFrom extends Command
{
	/**
	 * Create a new From selection order.
	 * @param plugin
	 * reference to the main plugin object
	 * @param commandLabel
	 * command name/alias
	 * @param sender
	 * who sent the command
	 * @param args
	 * arguments to the subcommand
	 */
	public CommandWGMigrateFrom(WorldGuardCityMigrator plugin, CommandSender sender, String[] args)
	{
		super(plugin, sender, args);
	}

	/**
	 * Execute From-point select order.
	 */
	public boolean process()
	{
		String playerName = null;
		Player player = null;
		if(sender instanceof Player)
		{
			player = (Player)sender;
			if(!player.hasPermission("worldguardcitymigrator.use"))
			{
				sender.sendMessage("You do not have permission to use WorldGuardCityMigrator.");
				return false;
			}
			playerName = player.getName();
		}
		
		if(args.length != 3 && (args.length != 0 || player == null))
		{
			sender.sendMessage(CommandWGMigrateHelp.HELP_FROM);
			return false;
		}
		int x,y,z;
		if(args.length == 3)
		{
			try{
				x = Integer.parseInt(args[0]);
				y = Integer.parseInt(args[1]);
				z = Integer.parseInt(args[2]);
			}catch(NumberFormatException e){
				sender.sendMessage(CommandWGMigrateHelp.HELP_FROM);
				return false;
			}
		}else{
			if(player == null)
			{
				sender.sendMessage(CommandWGMigrateHelp.HELP_FROM);
				return false;
			}
			Location pl = player.getLocation();
			x = pl.getBlockX();
			y = pl.getBlockY();
			z = pl.getBlockZ();
		}
		
		Selection sel = WorldGuardCityMigrator.selections.get(playerName);
		if(sel == null)
		{
			sel = new Selection();
			sel.from = new Point(x,y,z);
			WorldGuardCityMigrator.selections.put(playerName, sel);
		}else{
			sel.from = new Point(x,y,z);
		}
		sender.sendMessage("From point selected.");
		
		return true;
	}
}
