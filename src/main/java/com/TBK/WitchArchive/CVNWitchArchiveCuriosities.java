package com.TBK.WitchArchive;

import com.TBK.WitchArchive.client.renderer.ElderHarpyRenderer;
import com.TBK.WitchArchive.client.renderer.FeatherRenderer;
import com.TBK.WitchArchive.client.renderer.HarpyRenderer;
import com.TBK.WitchArchive.common.register.CVNEntityType;
import com.TBK.WitchArchive.common.register.CVNItems;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CVNWitchArchiveCuriosities.MODID)
public class CVNWitchArchiveCuriosities
{
    public static final String MODID = "witch_archive";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CVNWitchArchiveCuriosities()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MinecraftForge.EVENT_BUS.register(this);
        CVNItems.ITEMS.register(modEventBus);
        CVNEntityType.ENTITY_TYPES.register(modEventBus);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT,()->()->{
            modEventBus.addListener(this::registerRenderers);
        });

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }


    @OnlyIn(Dist.CLIENT)
    public void registerRenderers(FMLCommonSetupEvent event){
        EntityRenderers.register(CVNEntityType.FEATHER_PROJECTILE.get(), FeatherRenderer::new);
        EntityRenderers.register(CVNEntityType.HARPY.get(), HarpyRenderer::new);
        EntityRenderers.register(CVNEntityType.ELDER_HARPY.get(), ElderHarpyRenderer::new);
    }

}
