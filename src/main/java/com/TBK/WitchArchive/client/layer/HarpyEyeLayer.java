package com.TBK.WitchArchive.client.layer;

import com.TBK.WitchArchive.CVNWitchArchiveCuriosities;
import com.TBK.WitchArchive.client.model.HarpyModel;
import com.TBK.WitchArchive.common.entity.HarpyEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class HarpyEyeLayer<T extends HarpyEntity, M extends HarpyModel<T>> extends EyesLayer<T,M> {
    private final ResourceLocation EYE=new ResourceLocation(CVNWitchArchiveCuriosities.MODID,"textures/entity/harpy/harpy_eyes.png");
    public HarpyEyeLayer(RenderLayerParent<T, M> p_116981_) {
        super(p_116981_);
    }

    @Override
    public RenderType renderType() {
        return RenderType.eyes(EYE);
    }
}
