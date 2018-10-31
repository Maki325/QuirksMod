package maki325.bnha.commands.subcommands;

import java.util.ArrayList;
import java.util.List;

import maki325.bnha.api.Quirk;
import maki325.bnha.api.QuirkRegistry;
import maki325.bnha.api.Subcommand;
import maki325.bnha.capability.quirk.IQuirk;
import maki325.bnha.capability.quirk.providers.QuirkProvider;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import scala.actors.threadpool.Arrays;

public class SubcommandGet extends Subcommand {

	private List<String> setters;// = Arrays.asList(new String[] { "quirk", "level", "xp" });
	public static final String usage = "/quirk get <quirk:level:xp> [player name]";
	
	public SubcommandGet() {
		setters = new ArrayList<String>();
		setters.add("quirk");
		setters.add("level");
		setters.add("xp");
	}
	
	@Override
	public String getName() {
		return "get";
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		
		List<String> ret = new ArrayList<String>();
		
		if(args.length == 2) {
			setters.forEach(s -> { if(s.startsWith(args[1].toLowerCase())) ret.add(s); });
			return ret;
		}
		
		if(args.length == 3) {
			( (List<String>) Arrays.asList(server.getPlayerList().getOnlinePlayerNames())).forEach(s -> { if(s.startsWith(args[2])) ret.add(s); });
			return ret;
		}
		
		return ret;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		
		if(args.length == 2) {
			
			if (!(sender instanceof EntityPlayerMP))
				sender.sendMessage(new TextComponentString("Specify a player, because consoles don't get a quirk"));
			else {
				if(!setters.contains(args[1].toLowerCase()))
					throw new CommandException(args[1] + " is not a part of a quirk");

				getStuff(args[1], (EntityPlayerMP) sender, sender);
			}
			
		} else if(args.length == 3) {
			
			EntityPlayerMP player = server.getPlayerList().getPlayerByUsername(args[2]);
			if(player == null) {
    			sender.sendMessage(new TextComponentString(TextFormatting.RED + "Player with the name " + args[2] + " does not exist"));
    			return;
    		}
			
			if(!setters.contains(args[1].toLowerCase()))
				throw new CommandException(args[1] + " is not a part of a quirk");

			getStuff(args[1], player, sender);
			
		} else {
			throw new CommandException("Usage: " + usage);
		}
		
	}

	public void getStuff(String type, EntityPlayerMP player, ICommandSender sender) throws CommandException {
		
		IQuirk iquirk = player.getCapability(QuirkProvider.QUIRK_CAP, null);
		if(iquirk == null) return;
		
		Quirk q = iquirk.getQuirks().get(0);
		
		switch(type.toLowerCase()) {
			case "quirk":
				if(player.getName().endsWith("s"))
					sender.sendMessage(new TextComponentString("Player " + player.getName() + "' quirk is " + q.getName()));
				else
					sender.sendMessage(new TextComponentString("Player " + player.getName() + "'s quirk is " + q.getName()));
				break;
			case "xp":
				if(player.getName().endsWith("s"))
					sender.sendMessage(new TextComponentString("Player " + player.getName() + "' quirk level is " + q.getXp()));
				else
					sender.sendMessage(new TextComponentString("Player " + player.getName() + "'s quirk level is " + q.getXp()));
				break;
			case "level":
				if(player.getName().endsWith("s"))
					sender.sendMessage(new TextComponentString("Player " + player.getName() + "' quirk level is " + q.getLevel()));
				else
					sender.sendMessage(new TextComponentString("Player " + player.getName() + "'s quirk level is " + q.getLevel()));
				break;
			default:
				throw new CommandException(type + " is not a part of a quirk");
		}
		
	}
	
}
