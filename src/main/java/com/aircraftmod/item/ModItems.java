package com.aircraftmod.item;

import com.aircraftmod.AircraftMod;
import com.aircraftmod.entity.AirplaneEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public final class ModItems {
    public static final ResourceKey<Item> AIRPLANE_KEY = key("airplane");
    public static final ResourceKey<Item> GASOLINE_KEY = key("gasoline");

    public static final Item AIRPLANE = register("airplane", AirplaneItem::new,
            new Item.Properties().stacksTo(1));

    public static final Item GASOLINE = register("gasoline", Item::new,
            new Item.Properties().stacksTo(64));

    private ModItems() {}

    private static ResourceKey<Item> key(String name) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(AircraftMod.MOD_ID, name));
    }

    private static <T extends Item> T register(String name, java.util.function.Function<Item.Properties, T> factory,
                                                 Item.Properties properties) {
        ResourceKey<Item> itemKey = key(name);
        T item = factory.apply(properties.setId(itemKey));
        return Registry.register(BuiltInRegistries.ITEM, itemKey, item);
    }

    public static void initialize() {
        // Trigger static registration.
    }

    public static final class AirplaneItem extends Item {
        public AirplaneItem(Properties properties) {
            super(properties);
        }

        @Override
        public InteractionResult use(Level level, Player player, InteractionHand hand) {
            if (level.isClientSide()) return InteractionResult.PASS;

            Vec3 look = player.getLookAngle();
            Vec3 spawn = player.position().add(look.scale(3.0)).add(0, 1.0, 0);
            AirplaneEntity plane = new AirplaneEntity(AircraftMod.AIRPLANE, level);
            plane.setPos(spawn);
            plane.setYRot(player.getYRot());
            plane.setXRot(player.getXRot());
            plane.setFuel(1200);
            level.addFreshEntity(plane);
            player.startRiding(plane, true);

            ItemStack stack = player.getItemInHand(hand);
            if (!player.getAbilities().instabuild) stack.shrink(1);
            player.displayClientMessage(Component.literal("✈ Avião criado! Use GASOLINA para abastecer."), true);
            return InteractionResult.SUCCESS;
        }
    }
}
