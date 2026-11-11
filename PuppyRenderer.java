package com.example.labradorpuppy.client;

import com.example.labradorpuppy.LabradorPuppyMod;
import com.example.labradorpuppy.entity.PuppyEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PuppyRenderer extends MobRenderer<PuppyEntity, PuppyModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(LabradorPuppyMod.MOD_ID, "textures/entity/labrador_puppy.png");

    public PuppyRenderer(EntityRendererProvider.Context context) {
        super(context, new PuppyModel(), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(PuppyEntity entity) {
        return TEXTURE;
    }
}
