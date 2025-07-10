package com.TBK.WitchArchive.client.renderer;

import com.TBK.WitchArchive.CVNWitchArchiveCuriosities;
import com.TBK.WitchArchive.client.animations.MetalGearRayAnimations;
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
    private static final ResourceLocation OBJ_MODEL = new ResourceLocation(CVNWitchArchiveCuriosities.MODID, "obj/entity/ray_head.obj");

    public Vector3f VECTOR_CACHE = new Vector3f();
    private final ObjModel model;

    public HeadModel(RenderLayerParent<T, M> p_117346_) {
        super(p_117346_);
        this.model = ObjLoader.INSTANCE.loadModel(new ObjModel.ModelSettings(OBJ_MODEL,true,true,true,true,"ray_head.mtl"));

    }

    @Override
    public void render(PoseStack p_117349_, MultiBufferSource p_117350_, int p_117351_, T p_117352_, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        ModelPart part = this.getParentModel().getHeadObj();
        ModelPart part1 = this.getParentModel().getTorso();

        double size = 1.0F;
        StandaloneGeometryBakingContext renderable=StandaloneGeometryBakingContext.create(OBJ_MODEL);
        ITextureRenderTypeLookup renderTypeLookup = RenderType::entityCutout;
        p_117349_.pushPose();
        p_117349_.mulPose(Axis.ZP.rotationDegrees(180.0F+part.xRot));
        double fy1 = (double) (Math.cos((double) (part.xRot*180.0D/Math.PI))*size);
        part1.translateAndRotate(p_117349_);
        p_117349_.translate(0,4,0);
        this.animation(part,this.getElapsedSeconds(MetalGearRayAnimations.idlebody,p_117352_.idle.getAccumulatedTime()),p_117349_,p_117352_);
        this.model.bakeRenderable(renderable).render(p_117349_,p_117350_,renderTypeLookup,p_117351_, OverlayTexture.NO_OVERLAY,1.0F, CompositeRenderable.Transforms.EMPTY);
        p_117349_.popPose();
    }

    public void animation(ModelPart part,float f,PoseStack stack,T animatable){
        Optional<ModelPart> optional = Optional.of(part);
        List<AnimationChannel> list = MetalGearRayAnimations.idlebody.boneAnimations().get("Torso");
        optional.ifPresent((p_232330_) -> {
            list.forEach((p_288241_) -> {
                Keyframe[] akeyframe = p_288241_.keyframes();
                int i = Math.max(0, Mth.binarySearch(0, akeyframe.length, (p_232315_) -> {
                    return f <= akeyframe[p_232315_].timestamp();
                }) - 1);
                int j = Math.min(akeyframe.length - 1, i + 1);
                Keyframe keyframe = akeyframe[i];
                Keyframe keyframe1 = akeyframe[j];
                float f1 = f - keyframe.timestamp();
                float f2;
                if (j != i) {
                    f2 = Mth.clamp(f1 / (keyframe1.timestamp() - keyframe.timestamp()), 0.0F, 1.0F);
                } else {
                    f2 = 0.0F;
                }


                keyframe1.interpolation().apply(VECTOR_CACHE, f2, akeyframe, i, j, 1.0F);
                Vector3f vector3f = new Vector3f((float) animatable.getX(), (float) (animatable.getY()-animatable.getEyeHeight()-2.5F), (float) (animatable.getZ()-6.5F));
                //CVNWitchArchiveCuriosities.LOGGER.debug("BEFORE VECTOR :"+vector3f);

                vector3f = vector3f.rotate(new Quaternionf(VECTOR_CACHE.x,VECTOR_CACHE.y,VECTOR_CACHE.z,1.0F));
                //CVNWitchArchiveCuriosities.LOGGER.debug("AFTER VECTOR :"+vector3f);

                //stack.translate(vector3f.x,vector3f.y,vector3f.z);

                //stack.translate(0.0F,fy,VECTOR_CACHE.z);
            });
        });
    }

    public float getElapsedSeconds(AnimationDefinition p_232317_, long p_232318_) {
        float f = (float)p_232318_ / 1000.0F;
        return p_232317_.looping() ? f % p_232317_.lengthInSeconds() : f;
    }
}
