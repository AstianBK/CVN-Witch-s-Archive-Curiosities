package com.TBK.WitchArchive.client.renderer;

import com.TBK.WitchArchive.CVNWitchArchiveCuriosities;
import com.TBK.WitchArchive.client.layer.HarpyEyeLayer;
import com.TBK.WitchArchive.client.layer.HarpyFeatherLayer;
import com.TBK.WitchArchive.client.model.HarpyModel;
import com.TBK.WitchArchive.client.model.MetalGearRayModel;
import com.TBK.WitchArchive.common.entity.HarpyEntity;
import com.TBK.WitchArchive.common.entity.MetalGearRayEntity;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class MetalGearRayRenderer<T extends MetalGearRayEntity,M extends MetalGearRayModel<T>> extends MobRenderer<T,M> {
    public final ResourceLocation TEXTURE = new ResourceLocation(CVNWitchArchiveCuriosities.MODID,"textures/entity/metal_gear_ray/ray.png");
    public MetalGearRayRenderer(EntityRendererProvider.Context p_174304_) {
        super(p_174304_, (M) new MetalGearRayModel<>(p_174304_.bakeLayer(MetalGearRayModel.LAYER_LOCATION)), 0.0F);
        this.addLayer(new HeadModel<>(this));
    }

    @Nullable
    @Override
    protected RenderType getRenderType(T p_115322_, boolean p_115323_, boolean p_115324_, boolean p_115325_) {
        return RenderType.entityTranslucent(this.getTextureLocation(p_115322_));
    }

    @Override
    public void render(T p_115455_, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_, int p_115460_) {
        super.render(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
    }

    @Override
    public ResourceLocation getTextureLocation(T p_114482_) {
        return TEXTURE;
    }
}
