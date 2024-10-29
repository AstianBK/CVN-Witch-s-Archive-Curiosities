package com.TBK.WitchArchive.common.register;

import com.TBK.WitchArchive.CVNWitchArchiveCuriosities;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CVNItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CVNWitchArchiveCuriosities.MODID);

    public static final RegistryObject<Item> HARPY_FEATHER = ITEMS.register("harpy_feather",
            ()-> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GOLDEN_WHEAT_SEEDS = ITEMS.register("golden_wheat_seeds",
            ()-> new Item(new Item.Properties()));


    public static final RegistryObject<Item> HARPY_SPAWN_EGG = ITEMS.register("harpy_spawn_egg",
            () -> new ForgeSpawnEggItem(CVNEntityType.HARPY,0x948e8d, 0x573f2c,
                    new Item.Properties()));

    public static final RegistryObject<Item> ELDER_HARPY_SPAWN_EGG = ITEMS.register("elder_harpy_spawn_egg",
            () -> new ForgeSpawnEggItem(CVNEntityType.ELDER_HARPY,0x948e8d, 0x573f2c,
                    new Item.Properties()));
}
