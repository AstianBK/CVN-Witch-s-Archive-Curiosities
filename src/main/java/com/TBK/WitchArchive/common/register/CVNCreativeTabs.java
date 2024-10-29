package com.TBK.WitchArchive.common.register;

import com.TBK.WitchArchive.CVNWitchArchiveCuriosities;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CVNCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CVNWitchArchiveCuriosities.MODID);

    public static final RegistryObject<CreativeModeTab> BK_MOBS_TAB = TABS.register(CVNWitchArchiveCuriosities.MODID,()-> CreativeModeTab.builder()
            .icon(()->new ItemStack(CVNItems.GOLDEN_WHEAT_SEEDS.get()))
            .title(Component.translatable("itemGroup.witch_archive"))
            .displayItems((s,a)-> {
                a.accept(CVNItems.GOLDEN_WHEAT_SEEDS.get());
                a.accept(CVNItems.HARPY_SPAWN_EGG.get());
                a.accept(CVNItems.HARPY_FEATHER.get());
            })
            .build());
}
