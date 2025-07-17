package com.TBK.WitchArchive.client.renderer;

import com.TBK.WitchArchive.CVNWitchArchiveCuriosities;
import com.TBK.WitchArchive.client.layer.HarpyEyeLayer;
import com.TBK.WitchArchive.client.layer.HarpyFeatherLayer;
import com.TBK.WitchArchive.client.model.HarpyModel;
import com.TBK.WitchArchive.client.model.MetalGearRayModel;
import com.TBK.WitchArchive.common.entity.HarpyEntity;
import com.TBK.WitchArchive.common.entity.MetalGearRayEntity;
import com.TBK.WitchArchive.common.register.CVNRenderType;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.Map;

import static com.TBK.WitchArchive.common.Util.internalRaycastForEntity;

public class MetalGearRayRenderer<T extends MetalGearRayEntity,M extends MetalGearRayModel<T>> extends MobRenderer<T,M> {
    public final ResourceLocation TEXTURE = new ResourceLocation(CVNWitchArchiveCuriosities.MODID,"textures/entity/metal_gear_ray/ray.png");
    private static final ResourceLocation GUARDIAN_BEAM_LOCATION = new ResourceLocation(CVNWitchArchiveCuriosities.MODID,"textures/entity/beacon_beam.png");
    private static final ResourceLocation BEAM_INNER_LOCATION = new ResourceLocation(CVNWitchArchiveCuriosities.MODID,"textures/entity/metal_gear_ray/beam/beam_inner.png");
    private static final ResourceLocation BEAM_OUTER_LOCATION = new ResourceLocation(CVNWitchArchiveCuriosities.MODID,"textures/entity/metal_gear_ray/beam/beam_outer.png");

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
        if(p_115455_.isLaser()){
            this.render(p_115458_,p_115459_,p_115455_,p_115456_);
        }
    }

    public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, T jellyfish, float pPartialTicks) {
        float beamProgress = 1.0F;


        float ageInTicks = jellyfish.tickCount + pPartialTicks;
        float shakeByX = (float) Math.sin(ageInTicks * 4F) * 0.075F;
        float shakeByY = (float) Math.sin(ageInTicks * 4F + 1.2F) * 0.075F;
        float shakeByZ = (float) Math.sin(ageInTicks * 4F + 2.4F) * 0.075F;
        Vec3 beamOrigin = jellyfish.getHeadPos(pPartialTicks);
        Vec3 rawBeamPosition = jellyfish.laserPosition.subtract(beamOrigin);
        float length = (float) rawBeamPosition.length();
        Vec3 vec3 = rawBeamPosition.normalize();
        float xRot = (float) Math.acos(vec3.y);
        float yRot = (float) Math.atan2(vec3.z, vec3.x);
        float width = beamProgress * 1.5F;

        Vec3 offSet = beamOrigin.subtract(jellyfish.position());
        pMatrixStack.pushPose();
        pMatrixStack.translate(
                shakeByX+offSet.x,
                shakeByY+offSet.y,
                shakeByZ+offSet.z
        );

        pMatrixStack.mulPose(Axis.YP.rotationDegrees(((Mth.PI / 2F) - yRot) * Mth.RAD_TO_DEG));
        pMatrixStack.mulPose(Axis.XP.rotationDegrees((-(Mth.PI / 2F) + xRot) * Mth.RAD_TO_DEG));
        pMatrixStack.mulPose(Axis.ZP.rotationDegrees(45));


        renderBeam(jellyfish,pMatrixStack,pBuffer,pPartialTicks,1.5F,length,true,false);
        renderBeam(jellyfish,pMatrixStack,pBuffer,pPartialTicks,1.5F,length,false,false);

        pMatrixStack.popPose();
    }

    private void renderBeam(T entity, PoseStack poseStack, MultiBufferSource source, float partialTicks, float width, float length, boolean inner, boolean glowSecondPass) {
        poseStack.pushPose();
        int vertices;
        VertexConsumer vertexconsumer;
        float speed;
        float startAlpha = 1.0F;
        float endAlpha = 1.0F;
        if (inner) {
            vertices = 4;
            ResourceLocation resourceLocation = BEAM_INNER_LOCATION;
            vertexconsumer = source.getBuffer(CVNRenderType.getTremorzillaBeam(resourceLocation,false));
            speed = 0.5F;
        } else {
            vertices = 8;
            ResourceLocation resourceLocation = BEAM_OUTER_LOCATION;
            vertexconsumer = source.getBuffer(CVNRenderType.getTremorzillaBeam(resourceLocation,false));

            width += 0.25F;
            speed = 1F;
            endAlpha = 0.0F;
        }

        float v = ((float) entity.tickCount + partialTicks) * -0.25F * speed;
        float v1 = v + length * (inner ? 0.5F : 0.15F);
        float f4 = -width;
        float f5 = 0;
        float f6 = 0.0F;
        PoseStack.Pose posestack$pose = poseStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        for (int j = 0; j <= vertices; ++j) {
            Matrix3f matrix3f = posestack$pose.normal();
            float f7 = Mth.cos((float) Math.PI + (float) j * ((float) Math.PI * 2F) / (float) vertices) * width;
            float f8 = Mth.sin((float) Math.PI + (float) j * ((float) Math.PI * 2F) / (float) vertices) * width;
            float f9 = (float) j + 1;
            vertexconsumer.vertex(matrix4f, f4 * 0.55F, f5 * 0.55F, 0.0F).color(1.0F, 1.0F, 1.0F, startAlpha).uv(f6, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
            vertexconsumer.vertex(matrix4f, f4, f5, length).color(1.0F, 1.0F, 1.0F, endAlpha).uv(f6, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(matrix3f, 0.0F, -1F, 0.0F).endVertex();
            vertexconsumer.vertex(matrix4f, f7, f8, length).color(1.0F, 1.0F, 1.0F, endAlpha).uv(f9, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(matrix3f, 0.0F, -1F, 0.0F).endVertex();
            vertexconsumer.vertex(matrix4f, f7 * 0.55F, f8 * 0.55F, 0.0F).color(1.0F, 1.0F, 1.0F, startAlpha).uv(f9, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
            f4 = f7;
            f5 = f8;
            f6 = f9;
        }
        /*if(inner){
            VertexConsumer endVertexConsumer;
            if (AlexsCaves.CLIENT_CONFIG.radiationGlowEffect.get() && glowSecondPass) {
                PostEffectRegistry.renderEffectForNextTick(ClientProxy.IRRADIATED_SHADER);
                endVertexConsumer = source.getBuffer(ACRenderTypes.getTremorzillaBeam(getEndBeamTexture(entity), true));
            } else {
                endVertexConsumer = source.getBuffer(ACRenderTypes.getTremorzillaBeam(getEndBeamTexture(entity), false));
            }
            poseStack.pushPose();
            poseStack.translate(0, 0, length - 1.5F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(45));
            poseStack.mulPose(Axis.XP.rotationDegrees(90));
            poseStack.scale(width, width, width);
            BEAM_END_MODEL.resetToDefaultPose();
            BEAM_END_MODEL.renderToBuffer(poseStack, endVertexConsumer, 240, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.popPose();
        }*/
        poseStack.popPose();
    }


    public static void renderBeaconBeam(PoseStack p_112185_, MultiBufferSource p_112186_, ResourceLocation p_112187_, float p_112188_, float p_112189_, long p_112190_, int p_112191_, int p_112192_, float[] p_112193_, float p_112194_, float p_112195_) {
        int i = p_112191_ + p_112192_;
        float f = (float)Math.floorMod(p_112190_, 40) + p_112188_;
        float f1 = p_112192_ < 0 ? f : -f;
        float f2 = Mth.frac(f1 * 0.2F - (float)Mth.floor(f1 * 0.1F));
        float f3 = p_112193_[0];
        float f4 = p_112193_[1];
        float f5 = p_112193_[2];
        float f6 = 0.0F;
        float f8 = 0.0F;
        float f9 = -p_112194_;
        float f12 = -p_112194_;
        float f15 = -1.0F + f2;
        float f16 = (float)p_112192_ * p_112189_ * (0.5F / p_112194_) + f15;
        p_112185_.pushPose();
        p_112185_.mulPose(Axis.YP.rotationDegrees(f * 2.25F - 45.0F));
        renderPart(p_112185_, p_112186_.getBuffer(RenderType.eyes(p_112187_)), 1.0F, 1.0F, 1.0F, 0.1F, p_112191_, i, 0.0F, p_112194_, p_112194_, 0.0F, f9, 0.0F, 0.0F, f12, 0.0F, 1.0F, f16, f15);
        p_112185_.popPose();
        f6 = -p_112195_;
        float f7 = -p_112195_;
        f8 = -p_112195_;
        f9 = -p_112195_;
        f15 = -1.0F + f2;
        f16 = (float)p_112192_ * p_112189_ + f15;
        renderPart(p_112185_, p_112186_.getBuffer(RenderType.eyes(p_112187_)), f3, f4, f5, 1.0F, p_112191_, i, f6, f7, p_112195_, f8, f9, p_112195_, p_112195_, p_112195_, 0.0F, 1.0F, f16, f15);
    }

    private static void renderPart(PoseStack p_112156_, VertexConsumer p_112157_, float p_112158_, float p_112159_, float p_112160_, float p_112161_, int p_112162_, int p_112163_, float p_112164_, float p_112165_, float p_112166_, float p_112167_, float p_112168_, float p_112169_, float p_112170_, float p_112171_, float p_112172_, float p_112173_, float p_112174_, float p_112175_) {
        PoseStack.Pose posestack$pose = p_112156_.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();
        renderQuad(matrix4f, matrix3f, p_112157_, p_112158_, p_112159_, p_112160_, p_112161_, p_112162_, p_112163_, p_112164_, p_112165_, p_112166_, p_112167_, p_112172_, p_112173_, p_112174_, p_112175_);
        renderQuad(matrix4f, matrix3f, p_112157_, p_112158_, p_112159_, p_112160_, p_112161_, p_112162_, p_112163_, p_112170_, p_112171_, p_112168_, p_112169_, p_112172_, p_112173_, p_112174_, p_112175_);
        renderQuad(matrix4f, matrix3f, p_112157_, p_112158_, p_112159_, p_112160_, p_112161_, p_112162_, p_112163_, p_112166_, p_112167_, p_112170_, p_112171_, p_112172_, p_112173_, p_112174_, p_112175_);
        renderQuad(matrix4f, matrix3f, p_112157_, p_112158_, p_112159_, p_112160_, p_112161_, p_112162_, p_112163_, p_112168_, p_112169_, p_112164_, p_112165_, p_112172_, p_112173_, p_112174_, p_112175_);
    }

    private static void renderQuad(Matrix4f p_253960_, Matrix3f p_254005_, VertexConsumer p_112122_, float p_112123_, float p_112124_, float p_112125_, float p_112126_, int p_112127_, int p_112128_, float p_112129_, float p_112130_, float p_112131_, float p_112132_, float p_112133_, float p_112134_, float p_112135_, float p_112136_) {
        addVertex(p_253960_, p_254005_, p_112122_, p_112123_, p_112124_, p_112125_, p_112126_, p_112128_, p_112129_, p_112130_, p_112134_, p_112135_);
        addVertex(p_253960_, p_254005_, p_112122_, p_112123_, p_112124_, p_112125_, p_112126_, p_112127_, p_112129_, p_112130_, p_112134_, p_112136_);
        addVertex(p_253960_, p_254005_, p_112122_, p_112123_, p_112124_, p_112125_, p_112126_, p_112127_, p_112131_, p_112132_, p_112133_, p_112136_);
        addVertex(p_253960_, p_254005_, p_112122_, p_112123_, p_112124_, p_112125_, p_112126_, p_112128_, p_112131_, p_112132_, p_112133_, p_112135_);
    }

    private static void addVertex(Matrix4f p_253955_, Matrix3f p_253713_, VertexConsumer p_253894_, float p_253871_, float p_253841_, float p_254568_, float p_254361_, int p_254357_, float p_254451_, float p_254240_, float p_254117_, float p_253698_) {
        p_253894_.vertex(p_253955_, p_254451_, (float)p_254357_, p_254240_).color(p_253871_, p_253841_, p_254568_, p_254361_).uv(p_254117_, p_253698_).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(p_253713_, 0.0F, 1.0F, 0.0F).endVertex();
    }
    @Override
    public ResourceLocation getTextureLocation(T p_114482_) {
        return TEXTURE;
    }
}
