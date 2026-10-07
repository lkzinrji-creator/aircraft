package com.aircraftmod.entity;

import com.aircraftmod.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

public class AirplaneEntity extends Entity {
    private static final EntityDataAccessor<Integer> FUEL = SynchedEntityData.defineId(
            AirplaneEntity.class, EntityDataSerializers.INT
    );

    public static final int MAX_FUEL = 2400;

    public AirplaneEntity(EntityType<? extends AirplaneEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(FUEL, 0);
    }

    public int getFuel() {
        return entityData.get(FUEL);
    }

    public void setFuel(int amount) {
        entityData.set(FUEL, Math.max(0, Math.min(MAX_FUEL, amount)));
    }

    public void addFuel(int amount) {
        setFuel(getFuel() + amount);
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);

        if (held.is(ModItems.GASOLINE)) {
            if (getFuel() >= MAX_FUEL) {
                return InteractionResult.PASS;
            }

            addFuel(600);
            if (!player.getAbilities().instabuild) {
                held.shrink(1);
            }

            if (!level().isClientSide()) {
                player.displayClientMessage(
                        Component.literal("⛽ Gasolina: " + getFuel() + "/" + MAX_FUEL), true
                );
            }
            return InteractionResult.SUCCESS;
        }

        if (getPassengers().isEmpty()) {
            player.startRiding(this, true);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public void tick() {
        super.tick();

        Entity passenger = getPassengers().isEmpty() ? null : getPassengers().getFirst();

        if (passenger instanceof Player player) {
            if (getFuel() > 0) {
                Vec3 look = player.getLookAngle().normalize();
                double speed = player.isSprinting() ? 0.72 : 0.42;
                if (player.isCrouching()) {
                    speed *= 0.45;
                }

                Vec3 desired = look.scale(speed);
                setDeltaMovement(getDeltaMovement().lerp(desired, 0.30));
                setYRot(player.getYRot());
                setXRot(player.getXRot());
                setFuel(getFuel() - 1);
            } else {
                setDeltaMovement(getDeltaMovement().multiply(0.92, 0.92, 0.92));
            }

            if (!level().isClientSide() && tickCount % 10 == 0) {
                player.displayClientMessage(
                        Component.literal(
                                "✈ Combustível: " + getFuel() + "/" + MAX_FUEL
                                        + "   |   SHIFT: desacelerar   |   SPRINT: turbo"
                        ), true
                );
            }
        } else {
            setDeltaMovement(getDeltaMovement().multiply(0.98, 0.98, 0.98));
        }

        Vec3 velocity = getDeltaMovement();
        if (velocity.lengthSqr() > 0.0001) {
            move(MoverType.SELF, velocity);
        }
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        output.putInt("Fuel", getFuel());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        setFuel(input.getInt("Fuel").orElse(0));
    }

    @Override
    public boolean isPickable() {
        return true;
    }
}
