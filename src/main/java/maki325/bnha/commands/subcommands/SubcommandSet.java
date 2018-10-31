package maki325.bnha.commands.subcommands;

import java.util.ArrayList;
import java.util.List;

import maki325.bnha.BnHA;
import maki325.bnha.api.Quirk;
import maki325.bnha.api.QuirkRegistry;
import maki325.bnha.api.Subcommand;
import maki325.bnha.capability.quirk.IQuirk;
import maki325.bnha.capability.quirk.providers.QuirkProvider;
import maki325.bnha.net.points.MessageChangePoints;
import maki325.bnha.net.points.MessageChangePoints.Change;
import maki325.bnha.net.quirk.messages.MessageChangeQuirk;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import scala.actors.threadpool.Arrays;

public class SubcommandSet extends Subcommand {

	private List<String> setters;// = Arrays.asList(new String[] { "quirk", "level", "xp" });
	public static final String usage = "/quirk set <quirk:level:xp> <amount/quirk> [player name]";
	
	public SubcommandSet() {
		setters = new ArrayList<String>();
		setters.add("quirk");
		setters.add("level");
		setters.add("xp");
	}
	
	@Override
	public String getName() {
		return "set";
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		
		List<String> ret = new ArrayList<String>();
		
		if(args.length == 2) {
			setters.forEach(s -> { if(s.startsWith(args[1].toLowerCase())) ret.add(s); });
			return ret;
		}
		
		if(args.length == 3 && args[1].equalsIgnoreCase("quirk")) {
			QuirkRegistry.getQuirks().forEach(q -> { if(q.getName().startsWith(args[2].toLowerCase())) ret.add(q.getName()); });
			return ret;
		}
		
		if(args.length == 4) {
			( (List<String>) Arrays.asList(server.getPlayerList().getOnlinePlayerNames())).forEach(s -> {if(s.startsWith(args[3])) ret.add(s); });
			return ret;
		}
		
		return ret;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length == 3) {
			if (!(sender instanceof EntityPlayerMP))
				sender.sendMessage(new TextComponentString("Specify a player, because consoles don't get a quirk"));
			else {
				if(!setters.contains(args[1].toLowerCase()))
					throw new CommandException(args[1] + " is not a part of a quirk");
					
				setStuff(args[2], args[1], (EntityPlayerMP) sender, sender);
			}
		} else if(args.length == 4) {
			EntityPlayerMP player = server.getPlayerList().getPlayerByUsername(args[3]);
			if(player == null) {
    			sender.sendMessage(new TextComponentString(TextFormatting.RED + "Player with the name " + args[3] + " does not exist"));
    			return;
    		}
			
			if(!setters.contains(args[1].toLowerCase()))
				throw new CommandException(args[1] + " is not a part of a quirk");
			
			setStuff(args[2], args[1], player, sender);
		} else {
			throw new CommandException("Usage: " + usage);
		}
		
	}
	
	public void setStuff(String value, String type, EntityPlayerMP player, ICommandSender sender) throws CommandException {

		try {
			
			IQuirk iquirk = player.getCapability(QuirkProvider.QUIRK_CAP, null);
			if(iquirk == null) return;
			
			Quirk q = iquirk.getQuirks().get(0);

			switch(type.toLowerCase()) {
				case "quirk":
	
					if(QuirkRegistry.getQuirkByName(value) == null) {
						throw new CommandException("Quirk you selected (" + value + ") doesn't exist!");
					}
					
					q = QuirkRegistry.getQuirkByName(value);
					q.level = 0;
					q.xp = 0;
					q.nextXp = q.getLevelMinimum();
					
					sender.sendMessage(new TextComponentString(player.getName() + "'s quirk is set to " + value));
					changeQuirk(q, player, true);
					
					break;
				case "xp":
					double val = parseDouble(value);
					
					q.xp = val;
					q.level = q.xpToLevel(val);
					q.nextXp = val * Math.pow(q.getLevelFactor(), q.level);
					q.init();
					
					sender.sendMessage(new TextComponentString(player.getName() + "'s quirk xp is set to " + value));
					changeQuirk(q, player, true);
					
					break;
				case "level":
					int stuff = parseInt(value);
					
					q.level = stuff;
					q.xp = stuff * q.getLevelMinimum();
					q.nextXp = stuff * Math.pow(q.getLevelFactor(), q.level);
					q.init();
					
					sender.sendMessage(new TextComponentString(player.getName() + "'s quirk level is set to " + value));
					changeQuirk(q, player, true);
					
					break;
				default:
					throw new CommandException(type + " is not a part of a quirk");
			}
			
		} catch(Error e) {
			throw new CommandException("Something went wrong");
		}
	
	}
	
	public void changeQuirk(String quirk, EntityPlayerMP player, boolean quiet) {
		changeQuirk(QuirkRegistry.getQuirkByName(quirk), player, quiet);
	}
	
	public void changeQuirk(Quirk quirk, EntityPlayerMP player, boolean quiet) {
		player.getCapability(QuirkProvider.QUIRK_CAP, null).getQuirks().set(0, quirk);
		BnHA.proxy.simpleNetworkWrapper.sendTo(new MessageChangeQuirk(quirk, player.getName(), quiet), player);
	}

}
