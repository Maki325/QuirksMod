package me.maki325.bokunoheroacademia.api.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.maki325.bokunoheroacademia.BnHA;
import me.maki325.bokunoheroacademia.api.commands.get.CommandGetQuirk;
import me.maki325.bokunoheroacademia.api.commands.set.CommandSetQuirk;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class BnHACommands {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(register(BnHA.MODID));
        dispatcher.register(register("bnha"));
    }

    public static LiteralArgumentBuilder<CommandSource> register(String name) {
        return Commands.literal(name)
                .then(Commands.literal("get")
                        .then(CommandGetQuirk.register()))
                .then(Commands.literal("set")
                        .requires(cs -> cs.hasPermissionLevel(2))
                        .then(CommandSetQuirk.register()));
    }

}
