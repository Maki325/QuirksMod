package me.maki325.bokunoheroacademia.api.commands.get;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import me.maki325.bokunoheroacademia.LanguageHelper;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.IQuirk;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.QuirkProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.LazyOptional;

public class CommandGetQuirk {

    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("quirk")
                .then(Commands
                        .argument("player", EntityArgument.player())
                        .requires(cs -> cs.hasPermissionLevel(2))
                        .executes(otherPlayer))
                .executes(thisPlayer);
    }

    public static Command<CommandSource> thisPlayer = context -> {
        ServerPlayerEntity player = context.getSource().asPlayer();

        LazyOptional<IQuirk> lazyOptional = player.getCapability(QuirkProvider.QUIRK_CAP);
        IQuirk iquirk = lazyOptional.orElse(null);
        if(iquirk == null || iquirk.getQuirks() == null || iquirk.getQuirk(0) == null) {
            player.sendMessage(new StringTextComponent("You don't have a quirk!"), player.getUniqueID());
        } else {
            String id = iquirk.getQuirk(0).getId().toString();
            player.sendMessage(new StringTextComponent("Your quirk is: " + LanguageHelper.getQuirkName(id)), player.getUniqueID());
        }

        return 0;
    };

    public static Command<CommandSource> otherPlayer = context -> {
        ServerPlayerEntity player = EntityArgument.getPlayer(context, "player");

        LazyOptional<IQuirk> lazyOptional = player.getCapability(QuirkProvider.QUIRK_CAP);
        IQuirk iquirk = lazyOptional.orElse(null);
        if(iquirk == null || iquirk.getQuirks() == null || iquirk.getQuirk(0) == null) {
            player.sendMessage(new StringTextComponent(player.getScoreboardName() + " doesn't have a quirk!"), player.getUniqueID());
        } else {
            String id = iquirk.getQuirk(0).getId().toString();
            player.sendMessage(new StringTextComponent(player.getScoreboardName() + "'s quirk is: " + LanguageHelper.getQuirkName(id)), player.getUniqueID());
        }

        return 0;
    };

}
