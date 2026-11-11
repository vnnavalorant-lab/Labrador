package com.example.labradorpuppy.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A simple, friendly Labrador puppy. It wanders, looks at nearby players and
 * can be fed bones (heals + hearts). All animation is purely client side
 * (see client/PuppyPose.java) and is driven by the vanilla walk animator,
 * so no extra networking or synced data is needed.
 */
public class PuppyEntity extends Animal {

    public PuppyEntity(EntityType<? extends PuppyEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createPuppyAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 12.0)
                .add(Attributes.MOVEMENT_SPEED, 0.28);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.5));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    // --- breeding is disabled (it's a puppy!) -------------------------------------------------
    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob other) {
        return null;
    }

    // --- interaction: give a bone to heal and see hearts --------------------------------------
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(Items.BONE)) {
            if (!this.level().isClientSide) {
                this.heal(4.0f);
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                ((ServerLevel) this.level()).sendParticles(ParticleTypes.HEART,
                        this.getX(), this.getY(0.8), this.getZ(), 6, 0.3, 0.2, 0.3, 0.02);
                this.playSound(SoundEvents.WOLF_PANT, 0.8f, 1.4f);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    // --- sounds (borrowed from the vanilla wolf) ------------------------
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.WOLF_WHINE;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.WOLF_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WOLF_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.WOLF_STEP, 0.15f, 1.0f);
    }
}
