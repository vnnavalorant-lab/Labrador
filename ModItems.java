package com.example.labradorpuppy;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    private ModItems() {}

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, LabradorPuppyMod.MOD_ID);

    public static final RegistryObject<Item> PUPPY_SPAWN_EGG = ITEMS.register("puppy_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.PUPPY, 0xEBDCC0, 0x8A6A45, new Item.Properties()));
}
