package com.aircraftmod.client.model;

import com.aircraftmod.AircraftMod;
import com.aircraftmod.client.state.AirplaneRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

/**
 * Hand-built voxel aircraft model.  The shape is intentionally made from
 * multiple model parts so the propeller can rotate independently.
 */
public class AirplaneModel extends EntityModel<AirplaneRenderState> {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
            AircraftMod.id("airplane"), "main"
    );

    private static final int WHITE_U = 0;
    private static final int WHITE_V = 0;
    private static final int RED_U = 64;
    private static final int RED_V = 0;
    private static final int DARK_U = 0;
    private static final int DARK_V = 64;
    private static final int SILVER_U = 32;
    private static final int SILVER_V = 64;
    private static final int GLASS_U = 64;
    private static final int GLASS_V = 64;
    private static final int BLACK_U = 96;
    private static final int BLACK_V = 64;
    private static final int YELLOW_U = 112;
    private static final int YELLOW_V = 64;

    private final ModelPart propeller;

    public AirplaneModel(ModelPart root) {
        super(root);
        this.propeller = root.getChild("propeller");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // Main fuselage.
        root.addOrReplaceChild("fuselage", cube(WHITE_U, WHITE_V,
                -3.1f, -1.9f, -7.8f, 6.2f, 4.4f, 15.6f, 0.12f), PartPose.ZERO);

        // Silver belly for a more finished aircraft silhouette.
        root.addOrReplaceChild("belly", cube(SILVER_U, SILVER_V,
                -2.75f, 1.55f, -7.2f, 5.5f, 1.0f, 14.2f, 0.04f), PartPose.ZERO);

        // Engine cowling / rounded-looking nose made from layered boxes.
        root.addOrReplaceChild("nose", cube(WHITE_U, WHITE_V,
                -2.75f, -1.65f, -9.7f, 5.5f, 3.8f, 2.8f, 0.10f), PartPose.ZERO);
        root.addOrReplaceChild("nose_cap", cube(SILVER_U, SILVER_V,
                -2.1f, -1.20f, -10.65f, 4.2f, 2.9f, 1.15f, 0.05f), PartPose.ZERO);

        // Two red accent bands around the fuselage.
        root.addOrReplaceChild("stripe_front", cube(RED_U, RED_V,
                -3.15f, -0.25f, -4.9f, 6.3f, 0.65f, 4.0f, 0.015f), PartPose.ZERO);
        root.addOrReplaceChild("stripe_rear", cube(RED_U, RED_V,
                -3.15f, -0.25f, 0.7f, 6.3f, 0.65f, 3.7f, 0.015f), PartPose.ZERO);

        // Side doors / fuselage panels.
        root.addOrReplaceChild("left_door", cube(SILVER_U, SILVER_V,
                -3.16f, -0.9f, -1.5f, 0.10f, 2.8f, 3.7f, 0.0f), PartPose.ZERO);
        root.addOrReplaceChild("right_door", cube(SILVER_U, SILVER_V,
                3.06f, -0.9f, -1.5f, 0.10f, 2.8f, 3.7f, 0.0f), PartPose.ZERO);

        // Cockpit glass.
        root.addOrReplaceChild("cockpit", cube(GLASS_U, GLASS_V,
                -2.25f, -3.15f, -4.45f, 4.5f, 1.75f, 4.1f, 0.08f), PartPose.ZERO);
        root.addOrReplaceChild("cockpit_top", cube(GLASS_U, GLASS_V,
                -1.75f, -3.45f, -2.7f, 3.5f, 1.0f, 1.4f, 0.06f), PartPose.ZERO);

        // Main wings.
        root.addOrReplaceChild("left_wing", cube(WHITE_U, WHITE_V,
                -12.5f, 0.05f, -1.15f, 9.6f, 0.75f, 7.4f, 0.03f), PartPose.ZERO);
        root.addOrReplaceChild("right_wing", cube(WHITE_U, WHITE_V,
                2.9f, 0.05f, -1.15f, 9.6f, 0.75f, 7.4f, 0.03f), PartPose.ZERO);

        // Red wing tips / stripes.
        root.addOrReplaceChild("left_wingtip", cube(RED_U, RED_V,
                -12.6f, 0.0f, -1.0f, 1.35f, 0.9f, 7.0f, 0.02f), PartPose.ZERO);
        root.addOrReplaceChild("right_wingtip", cube(RED_U, RED_V,
                11.25f, 0.0f, -1.0f, 1.35f, 0.9f, 7.0f, 0.02f), PartPose.ZERO);

        // Small rear stabilizers.
        root.addOrReplaceChild("tail_left", cube(WHITE_U, WHITE_V,
                -5.2f, 0.25f, 4.0f, 5.0f, 0.65f, 3.4f, 0.03f), PartPose.ZERO);
        root.addOrReplaceChild("tail_right", cube(WHITE_U, WHITE_V,
                0.2f, 0.25f, 4.0f, 5.0f, 0.65f, 3.4f, 0.03f), PartPose.ZERO);
        root.addOrReplaceChild("tail_left_tip", cube(RED_U, RED_V,
                -5.25f, 0.20f, 4.0f, 0.95f, 0.75f, 3.3f, 0.02f), PartPose.ZERO);
        root.addOrReplaceChild("tail_right_tip", cube(RED_U, RED_V,
                4.3f, 0.20f, 4.0f, 0.95f, 0.75f, 3.3f, 0.02f), PartPose.ZERO);

        // Vertical tail with red cap.
        root.addOrReplaceChild("tail_fin", cube(WHITE_U, WHITE_V,
                -0.75f, -5.0f, 4.55f, 1.5f, 5.2f, 3.3f, 0.04f), PartPose.ZERO);
        root.addOrReplaceChild("tail_fin_red", cube(RED_U, RED_V,
                -0.80f, -5.05f, 6.2f, 1.6f, 2.0f, 1.55f, 0.02f), PartPose.ZERO);

        // Landing gear struts + wheels.
        addLandingGear(root, "front_gear", -2.1f, -2.4f);
        addLandingGear(root, "rear_gear", 2.0f, 2.4f);

        // Engine front and propeller hub. Blades are children so they rotate.
        root.addOrReplaceChild("engine", cube(DARK_U, DARK_V,
                -2.45f, -1.35f, -10.95f, 4.9f, 2.9f, 1.2f, 0.04f), PartPose.ZERO);
        PartDefinition prop = root.addOrReplaceChild("propeller",
                cube(DARK_U, DARK_V,
                        -0.65f, -0.65f, -12.0f, 1.3f, 1.3f, 1.2f, 0.03f),
                PartPose.ZERO);

        // Four broad propeller blades.
        prop.addOrReplaceChild("blade_up", cube(BLACK_U, BLACK_V,
                -0.42f, -4.9f, -11.92f, 0.84f, 4.35f, 0.62f, 0.03f), PartPose.ZERO);
        prop.addOrReplaceChild("blade_down", cube(BLACK_U, BLACK_V,
                -0.42f, 0.55f, -11.92f, 0.84f, 4.35f, 0.62f, 0.03f), PartPose.ZERO);
        prop.addOrReplaceChild("blade_left", cube(BLACK_U, BLACK_V,
                -4.9f, -0.42f, -11.92f, 4.35f, 0.84f, 0.62f, 0.03f), PartPose.ZERO);
        prop.addOrReplaceChild("blade_right", cube(BLACK_U, BLACK_V,
                0.55f, -0.42f, -11.92f, 4.35f, 0.84f, 0.62f, 0.03f), PartPose.ZERO);

        // Yellow propeller tips make the spin easy to see.
        prop.addOrReplaceChild("tip_up", cube(YELLOW_U, YELLOW_V,
                -0.46f, -5.15f, -11.95f, 0.92f, 0.72f, 0.65f, 0.01f), PartPose.ZERO);
        prop.addOrReplaceChild("tip_down", cube(YELLOW_U, YELLOW_V,
                -0.46f, 4.43f, -11.95f, 0.92f, 0.72f, 0.65f, 0.01f), PartPose.ZERO);
        prop.addOrReplaceChild("tip_left", cube(YELLOW_U, YELLOW_V,
                -5.15f, -0.46f, -11.95f, 0.72f, 0.92f, 0.65f, 0.01f), PartPose.ZERO);
        prop.addOrReplaceChild("tip_right", cube(YELLOW_U, YELLOW_V,
                4.43f, -0.46f, -11.95f, 0.72f, 0.92f, 0.65f, 0.01f), PartPose.ZERO);

        return LayerDefinition.create(mesh, 128, 128);
    }

    private static void addLandingGear(PartDefinition root, String name, float x, float z) {
        PartDefinition gear = root.addOrReplaceChild(name,
                cube(DARK_U, DARK_V, x - 0.28f, 1.75f, z - 0.25f, 0.56f, 2.1f, 0.56f, 0.02f),
                PartPose.ZERO);
        gear.addOrReplaceChild("axle", cube(BLACK_U, BLACK_V,
                x - 0.95f, 3.35f, z - 0.35f, 1.9f, 0.32f, 0.70f, 0.01f), PartPose.ZERO);
        gear.addOrReplaceChild("wheel", cube(BLACK_U, BLACK_V,
                x - 0.70f, 3.58f, z - 0.42f, 1.4f, 1.15f, 0.84f, 0.03f), PartPose.ZERO);
        gear.addOrReplaceChild("wheel_hub", cube(SILVER_U, SILVER_V,
                x - 0.28f, 3.50f, z - 0.15f, 0.56f, 0.55f, 0.32f, 0.02f), PartPose.ZERO);
    }

    private static CubeListBuilder cube(int u, int v, float x, float y, float z,
                                        float w, float h, float d, float inflate) {
        return CubeListBuilder.create().texOffs(u, v)
                .addBox(x, y, z, w, h, d, new CubeDeformation(inflate));
    }

    @Override
    public void setupAnim(AirplaneRenderState state) {
        super.setupAnim(state);
        propeller.zRot = state.propellerAngle;
    }
}
