package maki325.bnha.commands.subcommands;

import java.util.ArrayList;
import java.util.List;

import maki325.bnha.BnHA;
import maki325.bnha.api.Quirk;
import maki325.bnha.api.Subcommand;
import maki325.bnha.capability.quirk.IQuirk;
import maki325.bnha.capability.quirk.providers.QuirkProvider;
import maki325.bnha.net.points.MessageChangePoints;
import maki325.bnha.net.points.MessageChangePoints.Change;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import scala.actors.threadpool.Arrays;

public class SubcommandPoints extends Subcommand {

	public static final String usage = "/quirk skill <set/add/remove/reset/get> <amount> [player name]";
	private List<String> setters;// = Arrays.asList(new String[] { "quirk", "level", "xp" });
	
	public SubcommandPoints() {
		setters = new ArrayList<String>();
		setters.add("set");
		setters.add("add");
		setters.add("remove");
		setters.add("reset");
		setters.add("get");
	}
	
	@Override
	public String getName() {
		return "points";
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		
		List<String> ret = new ArrayList<String>();
		
		if(args.length == 2) {
			setters.forEach(s -> { if(s.startsWith(args[1].toLowerCase())) ret.add(s); });
			return ret;
		}
		
		if(args.length == 3 && (args[1].equals("reset") || args[1].equals("get"))) {
			( (List<String>) Arrays.asList(server.getPlayerList().getOnlinePlayerNames())).forEach(s -> {if(s.startsWith(args[3])) ret.add(s); });
			return ret;
		}
		
		if(args.length == 4 && !(args[1].equals("reset") || args[1].equals("get"))) {
			( (List<String>) Arrays.asList(server.getPlayerList().getOnlinePlayerNames())).forEach(s -> {if(s.startsWith(args[3])) ret.add(s); });
			return ret;
		}
		
		return ret;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (!(sender instanceof EntityPlayerMP)) {
			sender.sendMessage(new TextComponentString("Specify a player, because consoles don't get a quirk"));
			return;
		}
		//		[0]		[1]					[2]			[3]
		// /q	points 	set/get/add/remove	<amount>	[player name]
		
		if(args.length < 2 || args.length > 4) {
			throw new CommandException("Usage: " + usage);
		}
		
		EntityPlayerMP player = (EntityPlayerMP) sender;
		
		switch(args[1]) {
			case "set":
				
				if(args.length < 3) {
					throw new CommandException("Usage: /quirk skill set <amount> [player name]");
				}
				
				int ps = parseInt(args[2]);
				
				if(args.length == 3) {
					player.getCapability(QuirkProvider.QUIRK_CAP, null).setPoints(ps);
					BnHA.proxy.simpleNetworkWrapper.sendTo(new MessageChangePoints(ps, Change.SET, player.getName()), player);
				} else {
					EntityPlayerMP p = player.mcServer.getPlayerList().getPlayerByUsername(args[3]);
					if(p == null) {
		    			player.sendMessage(new TextComponentString(TextFormatting.RED + "Player with the name " + args[3] + " does not exist"));
		    			return;
		    		}
					p.getCapability(QuirkProvider.QUIRK_CAP, null).setPoints(ps);
					BnHA.proxy.simpleNetworkWrapper.sendTo(new MessageChangePoints(ps, Change.SET, args[3]), p);
				}
				
				break;
			case "add":
				
				if(args.length < 3) {
					throw new CommandException("Usage: /quirk skill add <amount> [player name]");
				}
				
				int ps2 = parseInt(args[2]);
				
				if(args.length == 3) {
					player.getCapability(QuirkProvider.QUIRK_CAP, null).addPoints(ps2);
					BnHA.proxy.simpleNetworkWrapper.sendTo(new MessageChangePoints(ps2, Change.ADD, player.getName()), player);
				} else {
					EntityPlayerMP p = player.mcServer.getPlayerList().getPlayerByUsername(args[3]);
					if(p == null) {
		    			player.sendMessage(new TextComponentString(TextFormatting.RED + "Player with the name " + args[3] + " does not exist"));
		    			return;
		    		}
					p.getCapability(QuirkProvider.QUIRK_CAP, null).setPoints(ps2);
					BnHA.proxy.simpleNetworkWrapper.sendTo(new MessageChangePoints(ps2, Change.ADD, args[3]), p);
				}
				
				break;
			case "remove":
				
				if(args.length < 3) {
					throw new CommandException("Usage: /quirk skill remove <amount> [player name]");
				}
				int ps3 = parseInt(args[2]);
				
				if(args.length == 3) {
					player.getCapability(QuirkProvider.QUIRK_CAP, null).addPoints(ps3);
					BnHA.proxy.simpleNetworkWrapper.sendTo(new MessageChangePoints(ps3, Change.REMOVE, player.getName()), player);
				} else {
					EntityPlayerMP p = player.mcServer.getPlayerList().getPlayerByUsername(args[3]);
					if(p == null) {
		    			player.sendMessage(new TextComponentString(TextFormatting.RED + "Player with the name " + args[3] + " does not exist"));
		    			return;
		    		}
					p.getCapability(QuirkProvider.QUIRK_CAP, null).setPoints(ps3);
					BnHA.proxy.simpleNetworkWrapper.sendTo(new MessageChangePoints(ps3, Change.REMOVE, args[3]), p);
				}
				
				break;
			case "reset":
				
				if(args.length < 2 || args.length > 3) {
					throw new CommandException("Usage: /quirk skill reset [player name]");
				}
				
				if(args.length == 2) {
					player.getCapability(QuirkProvider.QUIRK_CAP, null).setPoints(0);
					BnHA.proxy.simpleNetworkWrapper.sendTo(new MessageChangePoints(0, Change.RESET, player.getName()), player);
				} else {
					EntityPlayerMP p = player.mcServer.getPlayerList().getPlayerByUsername(args[2]);
					if(p == null) {
		    			player.sendMessage(new TextComponentString(TextFormatting.RED + "Player with the name " + args[2] + " does not exist"));
		    			return;
		    		}
					p.getCapability(QuirkProvider.QUIRK_CAP, null).setPoints(0);
					BnHA.proxy.simpleNetworkWrapper.sendTo(new MessageChangePoints(0, Change.RESET, args[2]), p);
				}
				
				break;
			case "get":
				
				if(args.length < 2 || args.length > 3) {
					throw new CommandException("Usage: /quirk skill get [player name]");
				}
				
				if(args.length == 2) {
					IQuirk iquirk = player.getCapability(QuirkProvider.QUIRK_CAP, null);
					if(iquirk == null) return;
					
					sender.sendMessage(new TextComponentString("You have " + iquirk.getPoints() + " points."));
				} else {
					EntityPlayerMP p = player.mcServer.getPlayerList().getPlayerByUsername(args[2]);
					if(p == null) {
		    			player.sendMessage(new TextComponentString(TextFormatting.RED + "Player with the name " + args[3] + " does not exist"));
		    			return;
		    		}
	    			player.sendMessage(new TextComponentString("Player " + player.getName() + " has " + p.getCapability(QuirkProvider.QUIRK_CAP, null).getPoints() + " points."));
				}
				
				break;
		}
		
	}

}
