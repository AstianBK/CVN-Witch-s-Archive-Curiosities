package com.TBK.WitchArchive.client.renderer;

import com.TBK.WitchArchive.CVNWitchArchiveCuriosities;
import com.TBK.WitchArchive.client.animations.MetalGearRayAnim;
import com.TBK.WitchArchive.client.model.MetalGearRayModel;
import com.TBK.WitchArchive.common.entity.MetalGearRayEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.model.geometry.StandaloneGeometryBakingContext;
import net.minecraftforge.client.model.obj.ObjLoader;
import net.minecraftforge.client.model.obj.ObjModel;
import net.minecraftforge.client.model.renderable.CompositeRenderable;
import net.minecraftforge.client.model.renderable.ITextureRenderTypeLookup;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;
import java.util.Optional;

public class HeadModel <T extends MetalGearRayEntity, M extends MetalGearRayModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation HEAD_MODEL = new ResourceLocation(CVNWitchArchiveCuriosities.MODID, "obj/entity/ray_head.obj");
    private static final ResourceLocation HEAD_OPEN_MODEL = new ResourceLocation(CVNWitchArchiveCuriosities.MODID, "obj/entity/ray_head_open.obj");

    public Vector3f VECTOR_CACHE = new Vector3f();
    private final ObjModel model;
    private final ObjModel modelOpen;


    public HeadModel(RenderLayerParent<T, M> p_117346_) {
        super(p_117346_);
        this.model = ObjLoader.INSTANCE.loadModel(new ObjModel.ModelSettings(HEAD_MODEL,true,true,true,true,"ray_head.mtl"));
        this.modelOpen = ObjLoader.INSTANCE.loadModel(new ObjModel.ModelSettings(HEAD_OPEN_MODEL,true,true,true,true,"ray_head_open.mtl"));

    }

    @Override
    public void render(PoseStack p_117349_, MultiBufferSource p_117350_, int p_117351_, T p_117352_, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        ModelPart part = this.getParentModel().getHeadObj();
        ModelPart part1 = this.getParentModel().getTorso();

        boolean flag = p_117352_.isLaser();
        StandaloneGeometryBakingContext renderable=StandaloneGeometryBakingContext.create(flag ? HEAD_OPEN_MODEL : HEAD_MODEL);
        ITextureRenderTypeLookup renderTypeLookup = RenderType::entityCutout;
        p_117349_.pushPose();
        p_117349_.mulPose(Axis.ZP.rotationDegrees(180.0F));

        this.animation(part,part1,this.getElapsedSeconds(MetalGearRayAnim.idlebody,p_117352_.idle.getAccumulatedTime()),p_117349_,p_117352_);
        if(p_117352_.isLaser()){
            p_117349_.mulPose(Axis.YP.rotationDegrees(-(p_117352_.rotHeadY)));
            p_117349_.mulPose(Axis.XP.rotationDegrees((float) -p_117352_.rotHeadX));
        }
        if(flag){
            this.modelOpen.bakeRenderable(renderable).render(p_117349_,p_117350_,renderTypeLookup,p_117351_, OverlayTexture.NO_OVERLAY,1.0F, CompositeRenderable.Transforms.EMPTY);
        }else {
            this.model.bakeRenderable(renderable).render(p_117349_,p_117350_,renderTypeLookup,p_117351_, OverlayTexture.NO_OVERLAY,1.0F, CompositeRenderable.Transforms.EMPTY);
        }

        p_117349_.popPose();
    }

    public void animation(ModelPart part,ModelPart part1,float f,PoseStack stack,T animatable){
        stack.translate(part1.x/16.0D,15+part1.y/16.0D,part1.z/16.0D);
        if (part1.xRot != 0.0F || part1.yRot != 0.0F || part1.zRot != 0.0F) {
            stack.mulPose((new Quaternionf()).rotationZYX(-part1.zRot , part1.yRot, -part1.xRot));
        }
        stack.translate(0,0.25,-8.0D);
        if (part.xRot != 0.0F || part.yRot != 0.0F || part.zRot != 0.0F) {
            stack.mulPose((new Quaternionf()).rotationZYX(-part.zRot , part.yRot, -part.xRot));
        }
    }

    public float getElapsedSeconds(AnimationDefinition p_232317_, long p_232318_) {
        float f = (float)p_232318_ / 1000.0F;
        return p_232317_.looping() ? f % p_232317_.lengthInSeconds() : f;
    }
}
