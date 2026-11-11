package com.example.labradorpuppy;

import com.example.labradorpuppy.entity.PuppyEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModEntities {
    private ModEntities() {}

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, LabradorPuppyMod.MOD_ID);

    /** Entity id: labrador_puppy:puppy  (use /summon labrador_puppy:puppy) */
    public static final RegistryObject<EntityType<PuppyEntity>> PUPPY = ENTITY_TYPES.register("puppy",
            () -> EntityType.Builder.of(PuppyEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.65f)
                    .clientTrackingRange(4)   // 4 chunks = 64 blocks
                    .build(new ResourceLocation(LabradorPuppyMod.MOD_ID, "puppy").toString()));
}
