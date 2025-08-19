package net.ramixin.direfall;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.ramixin.direfall.util.LevelPropertiesDuck;

import java.util.ArrayList;
import java.util.List;

public class ModCommands {

    private static final DynamicCommandExceptionType FAILURE_EXCEPTION = new DynamicCommandExceptionType((intensity) -> Text.stringifiedTranslatable("commands.player.direfall.intensity.fail", intensity));

    public static void register(CommandNode<ServerCommandSource> rootNode) {
        CommandNode<ServerCommandSource> intensity = CommandManager.literal("intensity").executes(ModCommands::runGet).requires(source -> source.hasPermissionLevel(2)).build();
        for(LiteralCommandNode<ServerCommandSource> set : createSet())
            intensity.addChild(set);
        rootNode.addChild(intensity);
    }

    private static List<LiteralCommandNode<ServerCommandSource>> createSet() {
        List<LiteralCommandNode<ServerCommandSource>> list = new ArrayList<>();
        for(Intensity intensity : Intensity.values()) {
            list.add(CommandManager.literal(intensity.asString()).executes((context) -> runSet(context, intensity)).build());
        }
        return list;
    }

    private static int runSet(CommandContext<ServerCommandSource> ctx, Intensity intensity) throws CommandSyntaxException {
        if(Direfall.getIntensity(ctx.getSource().getWorld()) == intensity) throw FAILURE_EXCEPTION.create(intensity.getName());
        LevelPropertiesDuck.get(ctx.getSource().getWorld().getLevelProperties()).direfall$setIntensity(intensity);
        ctx.getSource().sendFeedback(() -> Text.translatable("commands.player.direfall.intensity.set", Direfall.getIntensity(ctx.getSource().getWorld()).getName()), true);
        return 1;
    }

    private static int runGet(CommandContext<ServerCommandSource> ctx) {
        ctx.getSource().sendFeedback(() -> Text.translatable("commands.player.direfall.intensity.get", Direfall.getIntensity(ctx.getSource().getWorld()).getName()), true);
        return 1;
    }
}
