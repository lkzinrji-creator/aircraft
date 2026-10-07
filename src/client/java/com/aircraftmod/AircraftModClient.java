package com.aircraftmod;

import com.aircraftmod.client.model.AirplaneModel;
import com.aircraftmod.client.renderer.AirplaneRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.entity.EntityRenderers;

public final class AircraftModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRenderers.registerLayerDefinition(AirplaneModel.LAYER, AirplaneModel::createBodyLayer);
        EntityRenderers.register(AircraftMod.AIRPLANE, AirplaneRenderer::new);
    }
}
