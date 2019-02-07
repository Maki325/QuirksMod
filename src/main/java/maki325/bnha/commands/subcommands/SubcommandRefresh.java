package maki325.bnha.commands.subcommands;

import java.util.ArrayList;
import java.util.List;

import maki325.bnha.BnHA;
import maki325.bnha.api.Quirk;
import maki325.bnha.api.QuirkRegistry;
import maki325.bnha.api.Subcommand;
import maki325.bnha.capability.quirk.IQuirk;
import maki325.bnha.capability.quirk.providers.QuirkProvider;
import maki325.bnha.net.quirk.messages.MessageChangeQuirk;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;

public class SubcommandRefresh extends Subcommand {

	public static final String usage = "/quirk points <set/add/remove/reset/get> <amount> [player name]";
	
	@Override
	public String getName() {
		return "refresh";
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		return new ArrayList<String>();
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(!(sender instanceof EntityPlayerMP)) return;
		
		EntityPlayerMP player = (EntityPlayerMP) sender;
		
		IQuirk iquirk = player.getCapability(QuirkProvider.QUIRK_CAP, null);
		if(iquirk == null || iquirk.getQuirks().isEmpty()) return;
		
		Quirk q = iquirk.getQuirks().get(0);
		if(q == null) return;
		
		Quirk quirk = null;
		try {
			quirk = QuirkRegistry.getQuirkByName(q.getName()).getClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			quirk = QuirkRegistry.getQuirkByName(q.getName());
		}
		quirk.level = q.getLevel();
		quirk.xp = q.getXp();
		quirk.nextXp = q.getNextXP();
		
		if(!iquirk.getQuirks().isEmpty()) {
			MinecraftForge.EVENT_BUS.unregister(iquirk.getQuirks().get(0));
			iquirk.getQuirks().set(0, quirk);
			MinecraftForge.EVENT_BUS.register(iquirk.getQuirks().get(0));
		} else {
			iquirk.addQuirks(quirk);
			MinecraftForge.EVENT_BUS.register(iquirk.getQuirks().get(0));
		}
		
		BnHA.proxy.simpleNetworkWrapper.sendTo(new MessageChangeQuirk(quirk, player.getName(), true), player);

		sender.sendMessage(new TextComponentString(TextFormatting.GREEN + "Refreshed"));
		
	}

}
