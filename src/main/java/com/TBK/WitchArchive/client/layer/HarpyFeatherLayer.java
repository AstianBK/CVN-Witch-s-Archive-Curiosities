package com.TBK.WitchArchive.client.layer;

import com.TBK.WitchArchive.CVNWitchArchiveCuriosities;
import com.TBK.WitchArchive.client.model.HarpyModel;
import com.TBK.WitchArchive.common.entity.HarpyEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class HarpyFeatherLayer<T extends HarpyEntity, M extends HarpyModel<T>> extends RenderLayer<T,M> {
    private final ResourceLocation FEATHERS=new ResourceLocation(CVNWitchArchiveCuriosities.MODID,"textures/entity/harpy/harpy_feathers.png");

    public HarpyFeatherLayer(RenderLayerParent<T, M> p_117346_) {
        super(p_117346_);
    }

    @Override
    public void render(PoseStack p_117349_, MultiBufferSource p_117350_, int p_117351_, T p_117352_, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        float[] afloat = p_117352_.getColor().getTextureDiffuseColors();
        renderColoredCutoutModel(this.getParentModel(),FEATHERS,p_117349_,p_117350_,p_117351_,p_117352_, afloat[0], afloat[1], afloat[2]);
    }
    protected static <T extends LivingEntity> void renderColoredCutoutModel(EntityModel<T> p_117377_, ResourceLocation p_117378_, PoseStack p_117379_, MultiBufferSource p_117380_, int p_117381_, T p_117382_, float p_117383_, float p_117384_, float p_117385_) {
        VertexConsumer vertexconsumer = p_117380_.getBuffer(RenderType.entityTranslucent(p_117378_));
        p_117377_.renderToBuffer(p_117379_, vertexconsumer, p_117381_, LivingEntityRenderer.getOverlayCoords(p_117382_, 0.0F), p_117383_, p_117384_, p_117385_, 1.0F);
    }

}
