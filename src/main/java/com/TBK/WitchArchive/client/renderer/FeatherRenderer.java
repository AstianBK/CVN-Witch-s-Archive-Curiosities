package com.TBK.WitchArchive.client.renderer;

import com.TBK.WitchArchive.CVNWitchArchiveCuriosities;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FeatherRenderer<T extends AbstractArrow & ItemSupplier> extends EntityRenderer<T> {

    private final ItemRenderer itemRenderer;
    public FeatherRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
        this.itemRenderer=p_174008_.getItemRenderer();
    }
    public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();
        pMatrixStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot())));
        pMatrixStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot())));
        this.itemRenderer.renderStatic(pEntity.getItem(), ItemDisplayContext.GROUND,pPackedLight, OverlayTexture.NO_OVERLAY,pMatrixStack,pBuffer,null,pEntity.getId());

        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(T pEntity) {
        return new ResourceLocation(CVNWitchArchiveCuriosities.MODID,"textures/projetiles/arrow_beast_projectile.png");
    }
}
