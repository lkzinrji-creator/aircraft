package com.aircraftmod;

import com.aircraftmod.entity.AirplaneEntity;
import com.aircraftmod.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AircraftMod implements ModInitializer {
    public static final String MOD_ID = "aircraftmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final ResourceKey<EntityType<?>> AIRPLANE_KEY = ResourceKey.create(
            Registries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(MOD_ID, "airplane")
    );

    public static final EntityType<AirplaneEntity> AIRPLANE = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            AIRPLANE_KEY,
            EntityType.Builder.<AirplaneEntity>of(AirplaneEntity::new, MobCategory.MISC)
                    .sized(2.8f, 1.2f)
                    .clientTrackingRange(10)
                    .updateInterval(1)
                    .build(AIRPLANE_KEY)
    );

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        ModItems.initialize();
        LOGGER.info("Aircraft Mod loaded for Minecraft 26.3");
    }
}
