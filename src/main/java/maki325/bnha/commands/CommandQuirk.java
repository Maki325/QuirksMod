package maki325.bnha.commands;

import java.util.ArrayList;
import java.util.List;

import maki325.bnha.commands.subcommands.SubcommandGet;
import maki325.bnha.commands.subcommands.SubcommandPoints;
import maki325.bnha.commands.subcommands.SubcommandSet;
import maki325.bnha.commands.subcommands.SubcommandSkill;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandQuirk extends CommandBase {

	private static List<String> pos1;// = Arrays.asList(new String[] { "set", "get" });
	private static List<String> ret;
	
	private SubcommandSet set;
	private SubcommandGet get;
	private SubcommandSkill skill;
	private SubcommandPoints points;
	
	public CommandQuirk() {
		
		set = new SubcommandSet();
		get = new SubcommandGet();
		skill = new SubcommandSkill();
		points = new SubcommandPoints();
		
		pos1 = new ArrayList<String>();
		pos1.add(set.getName());
		pos1.add(get.getName());
		pos1.add(points.getName());
		pos1.add(skill.getName());
		
		ret = new ArrayList<String>();
		ret.add("quirk");
		ret.add("q");
	}
	
	@Override
	public String getName() {
		return "quirk";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return set.usage + " or " + get.usage + " or " + skill.usage + " or " + points.usage;
	}
	
	@Override
	public List<String> getAliases() {
		return ret;
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		
		List<String> ret = new ArrayList<String>();
		
		if(args.length == 1) {
			pos1.forEach(s -> { if(s.startsWith(args[0].toLowerCase())) ret.add(s); });
			return ret;
		}
		
		if(args[0].equalsIgnoreCase("get")) {
			return get.getTabCompletions(server, sender, args, targetPos);
		} else if(args[0].equalsIgnoreCase("set")) {
			return set.getTabCompletions(server, sender, args, targetPos);
		} else if(args[0].equalsIgnoreCase("skill")) {
			return skill.getTabCompletions(server, sender, args, targetPos);
		} else if(args[0].equalsIgnoreCase("points")) {
			return points.getTabCompletions(server, sender, args, targetPos);
		}
		
		return ret;
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length < 2)
			throw new CommandException("Usage: " + set.usage + " or \n" + get.usage + " or \n" + skill.usage + " or \n" + points.usage);
		
		if(args[0].equalsIgnoreCase("set")) {
			
			set.execute(server, sender, args);
			
		} else if(args[0].equalsIgnoreCase("get")) {
			
			get.execute(server, sender, args);
			
		}  else if(args[0].equalsIgnoreCase("skill")) {
			
			skill.execute(server, sender, args);
			
		} else if(args[0].equalsIgnoreCase("points")) {
			
			points.execute(server, sender, args);
			
		} else {
			throw new CommandException("Usage: " + set.usage + " or \n" + get.usage + " or \n" + skill.usage + " or \n" + points.usage);
		}
	}

}
