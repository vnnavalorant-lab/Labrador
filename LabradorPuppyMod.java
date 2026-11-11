package com.example.labradorpuppy;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(LabradorPuppyMod.MOD_ID)
public class LabradorPuppyMod {
    public static final String MOD_ID = "labrador_puppy";
    public static final Logger LOGGER = LogUtils.getLogger();

    public LabradorPuppyMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModEntities.ENTITY_TYPES.register(modBus);
        ModItems.ITEMS.register(modBus);
        modBus.register(ModEvents.class);
        LOGGER.info("Labrador Puppy loaded!");
    }
}
