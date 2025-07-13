package com.TBK.WitchArchive.common.register;

import com.TBK.WitchArchive.CVNWitchArchiveCuriosities;
import com.TBK.WitchArchive.common.entity.ElderHarpyEntity;
import com.TBK.WitchArchive.common.entity.FeatherProjectile;
import com.TBK.WitchArchive.common.entity.HarpyEntity;
import com.TBK.WitchArchive.common.entity.MetalGearRayEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CVNEntityType {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CVNWitchArchiveCuriosities.MODID);

    public static final RegistryObject<EntityType<HarpyEntity>> HARPY =
            ENTITY_TYPES.register("harpy",
                    () -> EntityType.Builder.of(HarpyEntity::new, MobCategory.MONSTER)
                            .sized(0.60f, 2.0f)
                            .build(new ResourceLocation(CVNWitchArchiveCuriosities.MODID, "harpy").toString()));

    public static final RegistryObject<EntityType<MetalGearRayEntity>> RAY =
            ENTITY_TYPES.register("ray",
                    () -> EntityType.Builder.of(MetalGearRayEntity::new, MobCategory.MONSTER)
                            .sized(1f, 1f)
                            .build(new ResourceLocation(CVNWitchArchiveCuriosities.MODID, "ray").toString()));

    public static final RegistryObject<EntityType<ElderHarpyEntity>> ELDER_HARPY =
            ENTITY_TYPES.register("elder_harpy",
                    () -> EntityType.Builder.of(ElderHarpyEntity::new, MobCategory.MONSTER)
                            .sized(0.60f, 2.0f)
                            .build(new ResourceLocation(CVNWitchArchiveCuriosities.MODID, "elder_harpy").toString()));

    public static final RegistryObject<EntityType<FeatherProjectile>> FEATHER_PROJECTILE = ENTITY_TYPES
            .register("feather_projectile", () -> EntityType.Builder.<FeatherProjectile>of(FeatherProjectile::new, MobCategory.MISC)
                    .fireImmune().sized(0.2F, 0.2F).build(CVNWitchArchiveCuriosities.MODID+ "feather_projectile"));

}
