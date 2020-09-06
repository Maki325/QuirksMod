package me.maki325.bokunoheroacademia.api.commands.set;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import me.maki325.bokunoheroacademia.Helper;
import me.maki325.bokunoheroacademia.LanguageHelper;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.IQuirk;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.QuirkProvider;
import me.maki325.bokunoheroacademia.api.quirk.Quirk;
import me.maki325.bokunoheroacademia.api.quirk.QuirkRegistry;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;

public class CommandSetQuirk {

    public static final SuggestionProvider<CommandSource> suggestionProvider = (context, builder) -> ISuggestionProvider.suggestIterable(QuirkRegistry.QUIRKS.keySet(), builder);

    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("quirk")
                .then(Commands
                        .argument("qurik", ResourceLocationArgument.resourceLocation())
                        .suggests(suggestionProvider)
                        .then(Commands
                                .argument("player", EntityArgument.player())
                                .executes(otherPlayer))
                        .executes(thisPlayer));
    }

    public static Command<CommandSource> thisPlayer = context -> {
        ResourceLocation id = context.getArgument("qurik", ResourceLocation.class);
        if(!QuirkRegistry.QUIRKS.containsKey(id)) {
            Message message = new StringTextComponent("No such quirk: " + id);
            throw new CommandSyntaxException(new SimpleCommandExceptionType(message), message);
        }
        ServerPlayerEntity player = context.getSource().asPlayer();

        LazyOptional<IQuirk> lazyOptional = player.getCapability(QuirkProvider.QUIRK_CAP);
        IQuirk iquirk = lazyOptional.orElse(null);
        if(iquirk == null) return 0 ;
        if(iquirk.getQuirks() == null) return 0;

        if(iquirk.getQuirks().isEmpty()) {
            Quirk quirk = QuirkRegistry.get(id);
            iquirk.addQuirks(quirk);
            MinecraftForge.EVENT_BUS.register(quirk);

            Helper.syncQuirkWithClient(quirk, player, true);
            player.sendMessage(new StringTextComponent("You've changed your quirk to " + LanguageHelper.getQuirkName(id)), player.getUniqueID());
        } else {
            Quirk q = iquirk.getQuirks().get(0);
            MinecraftForge.EVENT_BUS.unregister(q);
            Quirk quirk = QuirkRegistry.get(id);
            iquirk.getQuirks().set(0, quirk);
            MinecraftForge.EVENT_BUS.register(quirk);

            Helper.syncQuirkWithClient(q, player, true);
            player.sendMessage(new StringTextComponent("You've changed your quirk from " + LanguageHelper.getQuirkName(q.getId()) + " to " + LanguageHelper.getQuirkName(id)), player.getUniqueID());
        }

        return 0;
    };

    public static Command<CommandSource> otherPlayer = context -> {
        ResourceLocation id = context.getArgument("qurik", ResourceLocation.class);
        if(!QuirkRegistry.QUIRKS.containsKey(id)) {
            Message message = new StringTextComponent("No such quirk: " + id);
            throw new CommandSyntaxException(new SimpleCommandExceptionType(message), message);
        }
        ServerPlayerEntity player = EntityArgument.getPlayer(context, "player");

        LazyOptional<IQuirk> lazyOptional = player.getCapability(QuirkProvider.QUIRK_CAP);
        IQuirk iquirk = lazyOptional.orElse(null);
        if(iquirk == null) return 0 ;
        if(iquirk.getQuirks() == null) return 0;

        if(iquirk.getQuirks().isEmpty()) {
            Quirk quirk = QuirkRegistry.get(id);
            iquirk.addQuirks(quirk);
            MinecraftForge.EVENT_BUS.register(quirk);

            Helper.syncQuirkWithClient(quirk, player, true);
            player.sendMessage(new StringTextComponent("You've changed " + player.getScoreboardName() + "'s quirk to " + LanguageHelper.getQuirkName(id)), player.getUniqueID());
        } else {
            Quirk q = iquirk.getQuirks().get(0);
            MinecraftForge.EVENT_BUS.unregister(q);
            Quirk quirk = QuirkRegistry.get(id);
            iquirk.getQuirks().set(0, quirk);
            MinecraftForge.EVENT_BUS.register(quirk);

            Helper.syncQuirkWithClient(q, player, true);
            player.sendMessage(new StringTextComponent("You've changed " + player.getScoreboardName() + "'s quirk from " + LanguageHelper.getQuirkName(q.getId()) + " to " + LanguageHelper.getQuirkName(id)), player.getUniqueID());
        }

        return 0;
    };

}
