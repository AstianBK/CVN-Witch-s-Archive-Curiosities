package com.TBK.WitchArchive.server;

import com.TBK.WitchArchive.CVNWitchArchiveCuriosities;
import com.TBK.WitchArchive.common.entity.ElderHarpyEntity;
import com.TBK.WitchArchive.common.entity.HarpyEntity;
import com.TBK.WitchArchive.common.register.CVNEntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = CVNWitchArchiveCuriosities.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvent {
    @SubscribeEvent
    public static void registerAttribute(EntityAttributeCreationEvent event) {
        SpawnPlacements.register(CVNEntityType.HARPY.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules);

        event.put(CVNEntityType.ELDER_HARPY.get(), ElderHarpyEntity.setAttributes());
        event.put(CVNEntityType.HARPY.get(), HarpyEntity.setAttributes());
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        //event.enqueueWork(BKItemProperties::register);
    }
}
