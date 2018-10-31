package maki325.bnha.commands.subcommands;

import java.util.ArrayList;
import java.util.List;

import maki325.bnha.BnHA;
import maki325.bnha.api.Subcommand;
import maki325.bnha.net.tag.add.MessageAddTag;
import maki325.bnha.net.tag.remove.MessageRemoveTag;
import maki325.bnha.util.Reference;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class SubcommandSkill extends Subcommand {

	private List<String> setters;// = Arrays.asList(new String[] { "quirk", "level", "xp" });
	public static final String usage = "/quirk skill <op/deop>";
	
	public SubcommandSkill() {
		setters = new ArrayList<String>();
		setters.add("op");
		setters.add("deop");
	}
	
	@Override
	public String getName() {
		return "skill";
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		
		List<String> ret = new ArrayList<String>();
		
		if(args.length == 2) {
			setters.forEach(s -> { if(s.startsWith(args[1].toLowerCase())) ret.add(s); });
			return ret;
		}
		
		return ret;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		
		if (!(sender instanceof EntityPlayerMP)) {
			throw new CommandException("Specify a player, because consoles don't have a skill");
		}
		EntityPlayerMP player = (EntityPlayerMP)sender;
		if(args.length == 2) {
			if(args[1].equalsIgnoreCase("op")) {
				//player.addTag(Reference.MOD_ID + ":skill_op");
				BnHA.proxy.simpleNetworkWrapper.sendTo(new MessageAddTag(Reference.MOD_ID + ":skill_op", player.getName()), player);
				
				player.sendMessage(new TextComponentString("You can now activate/deactivate skill in Skill tree GUI"));
			} else if(args[1].equalsIgnoreCase("deop")) {
				//player.removeTag(Reference.MOD_ID + ":skill_op");
				BnHA.proxy.simpleNetworkWrapper.sendTo(new MessageRemoveTag(Reference.MOD_ID + ":skill_op", player.getName()), player);
				
				player.sendMessage(new TextComponentString("You can't activate/deactivate skill in Skill tree GUI anymore"));
			} else {
				throw new CommandException("Usage: " + usage);
			}
		} else {
			throw new CommandException("Usage: " + usage);
		}
		
	}
	
}
