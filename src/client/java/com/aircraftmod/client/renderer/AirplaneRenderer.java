package com.aircraftmod.client.renderer;

import com.aircraftmod.AircraftMod;
import com.aircraftmod.client.model.AirplaneModel;
import com.aircraftmod.client.state.AirplaneRenderState;
import com.aircraftmod.entity.AirplaneEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;

public class AirplaneRenderer extends EntityRenderer<AirplaneEntity, AirplaneRenderState> {
    private static final Identifier TEXTURE = AircraftMod.id("textures/entity/airplane.png");
    private final AirplaneModel model;

    public AirplaneRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new AirplaneModel(context.bakeLayer(AirplaneModel.LAYER));
    }

    @Override
    public AirplaneRenderState createRenderState() {
        return new AirplaneRenderState();
    }

    @Override
    public void extractRenderState(AirplaneEntity entity, AirplaneRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.yaw = entity.getYRot();
        state.pitch = entity.getXRot();
        state.propellerAngle = (entity.tickCount + partialTick) * (entity.getFuel() > 0 ? 1.35f : 0.18f);
        state.fuel = entity.getFuel();
    }

    @Override
    public void submit(AirplaneRenderState state, PoseStack poseStack, SubmitNodeCollector collector,
                       CameraRenderState cameraState) {
        poseStack.pushPose();
        poseStack.translate(0.0f, 0.15f, 0.0f);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0f - state.yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(-state.pitch));
        model.setupAnim(state);
        collector.submitModel(
                model,
                state,
                poseStack,
                RenderTypes.entityCutoutNoCull(TEXTURE),
                15728880,
                0,
                -1
        );
        poseStack.popPose();
    }
}
