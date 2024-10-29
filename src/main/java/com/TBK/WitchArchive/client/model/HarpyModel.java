package com.TBK.WitchArchive.client.model;// Made with Blockbench 4.11.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.TBK.WitchArchive.CVNWitchArchiveCuriosities;
import com.TBK.WitchArchive.client.animations.HarpyAnimations;
import com.TBK.WitchArchive.common.entity.HarpyEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class HarpyModel<T extends HarpyEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(CVNWitchArchiveCuriosities.MODID, "harpy"), "main");
	private final ModelPart main;
	private final ModelPart Head;
	private final ModelPart Torso;
	private final ModelPart Chest;
	private final ModelPart boobies;
	private final ModelPart Waist;
	private final ModelPart Hips;
	private final ModelPart Tail;
	private final ModelPart LeftArm;
	private final ModelPart LeftUpper;
	private final ModelPart LeftLower;
	private final ModelPart RightArm;
	private final ModelPart RightUpper;
	private final ModelPart RightLower;
	private final ModelPart LeftLeg;
	private final ModelPart UpperLeftLeg;
	private final ModelPart LowerleftLeg;
	private final ModelPart LeftClaw;
	private final ModelPart Feet;
	private final ModelPart RightLeg;
	private final ModelPart UpperRightLeg;
	private final ModelPart LowerrightLeg;
	private final ModelPart RightClaw;
	private final ModelPart Feet2;

	public HarpyModel(ModelPart root) {
		this.main = root.getChild("main");
		this.Head = this.main.getChild("Head");
		this.Torso = this.main.getChild("Torso");
		this.Chest = this.Torso.getChild("Chest");
		this.boobies = this.Torso.getChild("boobies");
		this.Waist = this.Torso.getChild("Waist");
		this.Hips = this.Waist.getChild("Hips");
		this.Tail = this.Hips.getChild("Tail");
		this.LeftArm = this.main.getChild("LeftArm");
		this.LeftUpper = this.LeftArm.getChild("LeftUpper");
		this.LeftLower = this.LeftArm.getChild("LeftLower");
		this.RightArm = this.main.getChild("RightArm");
		this.RightUpper = this.RightArm.getChild("RightUpper");
		this.RightLower = this.RightArm.getChild("RightLower");
		this.LeftLeg = this.main.getChild("LeftLeg");
		this.UpperLeftLeg = this.LeftLeg.getChild("UpperLeftLeg");
		this.LowerleftLeg = this.LeftLeg.getChild("LowerleftLeg");
		this.LeftClaw = this.LowerleftLeg.getChild("LeftClaw");
		this.Feet = this.LeftClaw.getChild("Feet");
		this.RightLeg = this.main.getChild("RightLeg");
		this.UpperRightLeg = this.RightLeg.getChild("UpperRightLeg");
		this.LowerrightLeg = this.RightLeg.getChild("LowerrightLeg");
		this.RightClaw = this.LowerrightLeg.getChild("RightClaw");
		this.Feet2 = this.RightClaw.getChild("Feet2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, 0.0F));

		PartDefinition Head = main.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.9375F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.1F))
				.texOffs(24, 0).addBox(-3.0F, -3.0625F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.45F))
				.texOffs(48, 6).addBox(-3.0F, -4.4875F, -3.5F, 6.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(48, 6).addBox(-3.0F, -4.4875F, -3.5F, 6.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(24, 0).addBox(-3.0F, -4.0625F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, -7.0625F, 0.2F));

		PartDefinition Hair3_r1 = Head.addOrReplaceChild("Hair3_r1", CubeListBuilder.create().texOffs(0, 34).addBox(-3.0F, -3.25F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -0.0625F, 0.0F, 0.0F, 0.0F, -0.0873F));

		PartDefinition Hair3_r2 = Head.addOrReplaceChild("Hair3_r2", CubeListBuilder.create().texOffs(27, 2).addBox(-2.5F, -1.5F, -2.0F, 5.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.05F, -3.6375F, 3.0F, 1.5708F, -1.3963F, -1.5708F));

		PartDefinition Hair3_r3 = Head.addOrReplaceChild("Hair3_r3", CubeListBuilder.create().texOffs(52, 11).addBox(-2.775F, -1.7F, 1.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.9875F, -3.5F, 0.0F, 0.5236F, 0.0F));

		PartDefinition Ear3_r1 = Head.addOrReplaceChild("Ear3_r1", CubeListBuilder.create().texOffs(42, -8).mirror().addBox(-0.55F, -3.5F, -4.0F, 0.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.1688F, -0.6658F, 2.4859F, 0.2284F, -0.298F, -0.0681F));

		PartDefinition Ear4_r1 = Head.addOrReplaceChild("Ear4_r1", CubeListBuilder.create().texOffs(49, -5).addBox(0.0F, -3.0F, -2.5F, 0.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.3688F, -2.2408F, 5.7933F, -0.0436F, 0.0F, -1.5708F));

		PartDefinition Ear3_r2 = Head.addOrReplaceChild("Ear3_r2", CubeListBuilder.create().texOffs(49, -5).addBox(0.0F, -2.175F, -2.5F, 0.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6216F, -4.7908F, 6.6595F, 0.1745F, 0.0F, -1.5708F));

		PartDefinition Ear2_r1 = Head.addOrReplaceChild("Ear2_r1", CubeListBuilder.create().texOffs(42, -8).addBox(0.55F, -3.5F, -4.0F, 0.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.1688F, -0.6658F, 2.4859F, 0.2284F, 0.298F, 0.0681F));

		PartDefinition Torso = main.addOrReplaceChild("Torso", CubeListBuilder.create(), PartPose.offset(-0.05F, -3.6F, 0.0647F));

		PartDefinition Chest = Torso.addOrReplaceChild("Chest", CubeListBuilder.create().texOffs(0, 12).addBox(-2.5F, 0.0F, -1.5F, 5.0F, 6.0F, 3.0F, new CubeDeformation(0.15F)), PartPose.offset(0.05F, -0.4F, 0.1375F));

		PartDefinition boobies = Torso.addOrReplaceChild("boobies", CubeListBuilder.create(), PartPose.offset(0.05F, 1.1F, -1.2375F));

		PartDefinition boobies_r1 = boobies.addOrReplaceChild("boobies_r1", CubeListBuilder.create().texOffs(20, 1).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(1.4F, 0.0F, 0.0F, 0.8727F, -0.0436F, 0.0F));

		PartDefinition boobies_r2 = boobies.addOrReplaceChild("boobies_r2", CubeListBuilder.create().texOffs(20, 1).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-1.4F, 0.0F, 0.0F, 0.8727F, 0.0436F, 0.0F));

		PartDefinition Waist = Torso.addOrReplaceChild("Waist", CubeListBuilder.create().texOffs(22, 21).addBox(-2.5F, 0.0F, -1.5F, 5.0F, 4.0F, 3.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.05F, 5.325F, 0.1375F));

		PartDefinition Hips = Waist.addOrReplaceChild("Hips", CubeListBuilder.create().texOffs(22, 28).addBox(-2.5F, -1.0F, -1.5F, 5.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.65F, 0.0F));

		PartDefinition Tail = Hips.addOrReplaceChild("Tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 1.525F));

		PartDefinition cube_r1 = Tail.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(43, 42).addBox(-2.5F, -2.5F, 0.0F, 5.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.025F, -1.9F, 1.275F, -0.5672F, 0.0F, 0.0F));

		PartDefinition LeftArm = main.addOrReplaceChild("LeftArm", CubeListBuilder.create(), PartPose.offset(2.445F, -2.957F, 0.1936F));

		PartDefinition LeftUpper = LeftArm.addOrReplaceChild("LeftUpper", CubeListBuilder.create().texOffs(16, 12).addBox(-1.05F, -0.9F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offset(1.055F, -0.143F, 0.0336F));

		PartDefinition LeftLower = LeftArm.addOrReplaceChild("LeftLower", CubeListBuilder.create(), PartPose.offset(1.055F, 5.857F, 0.0336F));

		PartDefinition RightArmFeathers_r1 = LeftLower.addOrReplaceChild("RightArmFeathers_r1", CubeListBuilder.create().texOffs(10, 15).mirror().addBox(0.0F, -6.5F, -3.0F, 0.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.025F, 4.7758F, 6.0938F, -1.0908F, 0.0F, 0.0F));

		PartDefinition LeftArmFeathers_r1 = LeftLower.addOrReplaceChild("LeftArmFeathers_r1", CubeListBuilder.create().texOffs(0, 16).addBox(-0.575F, -4.7F, -0.95F, 0.0F, 9.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 16).addBox(0.425F, -4.7F, -0.95F, 0.0F, 9.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(10, 15).addBox(-0.05F, -5.7F, 0.05F, 0.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(24, 13).addBox(-1.025F, -0.7F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.0908F, 0.0F, 0.0F));

		PartDefinition RightArm = main.addOrReplaceChild("RightArm", CubeListBuilder.create(), PartPose.offset(-2.445F, -3.082F, 0.2186F));

		PartDefinition RightUpper = RightArm.addOrReplaceChild("RightUpper", CubeListBuilder.create().texOffs(16, 12).mirror().addBox(-0.95F, -0.9F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(-0.05F)).mirror(false), PartPose.offset(-1.055F, -0.018F, 0.0086F));

		PartDefinition RightLower = RightArm.addOrReplaceChild("RightLower", CubeListBuilder.create(), PartPose.offset(-1.055F, 5.982F, 0.0086F));

		PartDefinition RightArmFeathers_r2 = RightLower.addOrReplaceChild("RightArmFeathers_r2", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(0.575F, -4.7F, -0.95F, 0.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(11, 16).mirror().addBox(0.05F, -5.7F, 0.05F, 0.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 16).mirror().addBox(-0.425F, -4.7F, -0.95F, 0.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(24, 13).mirror().addBox(-0.975F, -0.7F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.15F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.0908F, 0.0F, 0.0F));

		PartDefinition RightArmFeathers_r3 = RightLower.addOrReplaceChild("RightArmFeathers_r3", CubeListBuilder.create().texOffs(10, 15).mirror().addBox(0.0F, -6.5F, -3.0F, 0.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.05F, 4.7758F, 6.0938F, -1.0908F, 0.0F, 0.0F));

		PartDefinition LeftLeg = main.addOrReplaceChild("LeftLeg", CubeListBuilder.create(), PartPose.offset(1.0F, 7.875F, 0.2022F));

		PartDefinition UpperLeftLeg = LeftLeg.addOrReplaceChild("UpperLeftLeg", CubeListBuilder.create().texOffs(32, 13).addBox(-0.5F, -0.5F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition LowerleftLeg = LeftLeg.addOrReplaceChild("LowerleftLeg", CubeListBuilder.create().texOffs(38, 21).addBox(-0.975F, -0.0625F, -0.9812F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.975F, 4.5625F, 0.0062F));

		PartDefinition LeftClaw = LowerleftLeg.addOrReplaceChild("LeftClaw", CubeListBuilder.create().texOffs(44, 15).mirror().addBox(-1.025F, 0.4407F, -1.0311F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.35F)).mirror(false), PartPose.offset(0.0F, 2.1218F, 0.0124F));

		PartDefinition Feet = LeftClaw.addOrReplaceChild("Feet", CubeListBuilder.create().texOffs(35, 26).mirror().addBox(-1.5F, 1.5F, -3.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.025F, 3.8157F, 0.1189F));

		PartDefinition LeftlegLower_r1 = Feet.addOrReplaceChild("LeftlegLower_r1", CubeListBuilder.create().texOffs(46, 21).mirror().addBox(-1.0F, -2.0F, -0.5F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.05F, 0.3686F, -0.2378F, -0.7418F, 0.0F, 0.0F));

		PartDefinition RightLeg = main.addOrReplaceChild("RightLeg", CubeListBuilder.create(), PartPose.offset(-1.0F, 7.875F, 0.2022F));

		PartDefinition UpperRightLeg = RightLeg.addOrReplaceChild("UpperRightLeg", CubeListBuilder.create().texOffs(32, 13).mirror().addBox(-2.5F, -0.5F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition LowerrightLeg = RightLeg.addOrReplaceChild("LowerrightLeg", CubeListBuilder.create().texOffs(38, 21).mirror().addBox(-1.025F, -0.0625F, -0.9812F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-0.975F, 4.5625F, 0.0062F));

		PartDefinition RightClaw = LowerrightLeg.addOrReplaceChild("RightClaw", CubeListBuilder.create().texOffs(44, 15).addBox(-0.975F, 0.4407F, -1.0311F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.35F)), PartPose.offset(0.0F, 2.1218F, 0.0124F));

		PartDefinition Feet2 = RightClaw.addOrReplaceChild("Feet2", CubeListBuilder.create().texOffs(35, 26).addBox(-1.525F, 1.3157F, -3.3811F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 0.0F));

		PartDefinition RightlegLower_r1 = Feet2.addOrReplaceChild("RightlegLower_r1", CubeListBuilder.create().texOffs(46, 21).addBox(-1.0F, -2.0F, -0.5F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.025F, 0.1843F, -0.1189F, -0.7418F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(entity,netHeadYaw,headPitch,ageInTicks);
		this.animateWalk(entity.isAggressive() ? HarpyAnimations.HARPY_IDLEFIGHT :HarpyAnimations.HARPY_IDLEFLY,limbSwing,limbSwingAmount,1.0F,1.0F);
		this.animate(entity.idle,HarpyAnimations.HARPY_IDLEFLY,ageInTicks,1.0F);
		this.animate(entity.idleWings,HarpyAnimations.HARPY_WINGS1,ageInTicks,2.0F);
		this.animate(entity.sitting,HarpyAnimations.HARPY_SIT,ageInTicks,1.0F);
		this.animate(entity.attackRange,HarpyAnimations.HARPY_RANGED,ageInTicks,1.0F);
		this.animate(entity.attackMelee,HarpyAnimations.HARPY_MELEE,ageInTicks,1.0F);
		if(entity.isSitting()){
			this.main.y=13F;
		}else {
			this.main.y=0.0F;
		}
	}

	private void applyHeadRotation(T p_250436_, float p_249176_, float p_251814_, float p_248796_) {
		p_249176_ = Mth.clamp(p_249176_, -30.0F, 30.0F);
		p_251814_ = Mth.clamp(p_251814_, -25.0F, 45.0F);

		this.Head.yRot = p_249176_ * ((float)Math.PI / 180F);
		this.Head.xRot = p_251814_ * ((float)Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return this.main;
	}
}