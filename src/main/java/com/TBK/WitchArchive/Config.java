package com.TBK.WitchArchive;

import com.TBK.WitchArchive.server.world.BKBiomeConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = CVNWitchArchiveCuriosities.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.IntValue HARPY_WEIGHT = BUILDER
            .comment("Spawn weight of Harpies")
            .defineInRange("harpy_weight", 2,0,100);


    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int harpyWeight;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        harpyWeight = HARPY_WEIGHT.get();
        BKBiomeConfig.init();

    }
}
