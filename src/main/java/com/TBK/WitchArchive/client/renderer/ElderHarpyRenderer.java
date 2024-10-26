package com.TBK.WitchArchive.client.renderer;

import com.TBK.WitchArchive.CVNWitchArchiveCuriosities;
import com.TBK.WitchArchive.client.layer.HarpyEyeLayer;
import com.TBK.WitchArchive.client.layer.HarpyFeatherLayer;
import com.TBK.WitchArchive.client.model.HarpyModel;
import com.TBK.WitchArchive.common.entity.HarpyEntity;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class ElderHarpyRenderer<T extends HarpyEntity,M extends HarpyModel<T>> extends MobRenderer<T,M> {
    private static final Map<HarpyEntity.Skin, ResourceLocation> LOCATION_BY_VARIANT = Util.make(Maps.newEnumMap(HarpyEntity.Skin.class), (p_114874_) -> {
        p_114874_.put(HarpyEntity.Skin.VARIANT_1, new ResourceLocation(CVNWitchArchiveCuriosities.MODID, "textures/entity/harpy/harpy_skin1.png"));
        p_114874_.put(HarpyEntity.Skin.VARIANT_2, new ResourceLocation(CVNWitchArchiveCuriosities.MODID, "textures/entity/harpy/harpy_skin2.png"));
        p_114874_.put(HarpyEntity.Skin.VARIANT_3, new ResourceLocation(CVNWitchArchiveCuriosities.MODID, "textures/entity/harpy/harpy_skin3.png"));
    });
    public ElderHarpyRenderer(EntityRendererProvider.Context p_174304_) {
        super(p_174304_, (M) new HarpyModel<>(p_174304_.bakeLayer(HarpyModel.LAYER_LOCATION)), 0.0F);
        this.addLayer(new HarpyEyeLayer<>(this));
        this.addLayer(new HarpyFeatherLayer<>(this));
    }

    @Override
    protected void scale(T p_115314_, PoseStack p_115315_, float p_115316_) {
        p_115315_.scale(1.5F,1.5F,1.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(T p_114482_) {
        return LOCATION_BY_VARIANT.get(p_114482_.getSkin());
    }
}
