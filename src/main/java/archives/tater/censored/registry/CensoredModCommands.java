package archives.tater.censored.registry;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;

import static com.mojang.brigadier.arguments.BoolArgumentType.bool;
import static com.mojang.brigadier.arguments.BoolArgumentType.getBool;
import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static java.lang.Math.toIntExact;
import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;
import static net.minecraft.commands.arguments.EntityArgument.entities;
import static net.minecraft.commands.arguments.EntityArgument.getEntities;

public final class CensoredModCommands {
    private CensoredModCommands() {
    }

    @NullMarked
    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext, Commands.CommandSelection selection) {
        dispatcher.register(literal("censored")
                .then(literal("unseeable")
                        .then(argument("entity", entities())
                                .executes(context ->
                                        setUnseeable(getEntities(context, "entity"), true)
                                )
                                .then(argument("enabled", bool())
                                        .executes(context ->
                                                setUnseeable(getEntities(context, "entity"), getBool(context, "enabled"))))))
                .then(literal("lag")
                        .then(argument("entity", entities())
                                .executes(context ->
                                        setLag(getEntities(context, "entity"), null))
                                .then(argument("amount", integer(1))
                                        .executes(context ->
                                                setLag(getEntities(context, "entity"), getInteger(context, "amount"))))))
        );
    }

    private static int setUnseeable(Collection<? extends Entity> entities, boolean enabled) {
        var value = enabled ? Unit.INSTANCE : null;
        return toIntExact(entities.stream().filter(entity ->
                entity instanceof LivingEntity && entity.setAttached(CensoredModAttachments.UNSEEABLE, value) != value
        ).count());
    }

    private static int setLag(Collection<? extends Entity> entities, @Nullable Integer amount) {
        return toIntExact(entities.stream().filter(entity ->
                entity instanceof LivingEntity && Objects.equals(entity.setAttached(CensoredModAttachments.LAG, amount), amount)
        ).count());
    }
}
