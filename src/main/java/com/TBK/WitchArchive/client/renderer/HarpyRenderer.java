package com.TBK.WitchArchive.client.renderer;

import com.TBK.WitchArchive.CVNWitchArchiveCuriosities;
import com.TBK.WitchArchive.client.model.HarpyModel;
import com.TBK.WitchArchive.common.entity.HarpyEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class HarpyRenderer <T extends HarpyEntity,M extends HarpyModel<T>> extends MobRenderer<T,M> {
    public HarpyRenderer(EntityRendererProvider.Context p_174304_) {
        super(p_174304_, (M) new HarpyModel<>(), 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(T p_114482_) {
        return new ResourceLocation(CVNWitchArchiveCuriosities.MODID,"texture/entity/harpy/harpy.png");
    }
}
