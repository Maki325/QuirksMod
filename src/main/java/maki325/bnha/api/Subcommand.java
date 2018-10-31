package maki325.bnha.api;

import java.util.List;

import com.google.common.primitives.Doubles;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public abstract class Subcommand {
	
	public abstract String getName();
	
	public abstract List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos);
	
	public abstract void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException;

	public static double parseDouble(String input) throws NumberInvalidException {
        try {
            double d0 = Double.parseDouble(input);

            if (!Doubles.isFinite(d0)) {
                throw new NumberInvalidException("commands.generic.num.invalid", new Object[] {input});
            } else {
                return d0;
            }
        }
        catch (NumberFormatException var3) {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[] {input});
        }
    }
	
	public static int parseInt(String input) throws NumberInvalidException {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException var2) {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[] {input});
        }
    }
	
}
