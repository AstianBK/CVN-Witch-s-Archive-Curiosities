package com.TBK.WitchArchive.client.model;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.TBK.WitchArchive.CVNWitchArchiveCuriosities;
import com.TBK.WitchArchive.client.animations.MetalGearRayAnim;
import com.TBK.WitchArchive.common.entity.MetalGearRayEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Pose;

public class MetalGearRayModel<T extends MetalGearRayEntity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(CVNWitchArchiveCuriosities.MODID, "metalgearray"), "main");
	private final ModelPart main;
	private final ModelPart truemain;
	private final ModelPart Head;
	private final ModelPart headcap;
	private final ModelPart face;
	private final ModelPart bone;
	private final ModelPart Torso;
	private final ModelPart Neck;
	private final ModelPart obj;
	private final ModelPart Hip;
	private final ModelPart Tail;
	private final ModelPart Tail1;
	private final ModelPart Tail2;
	private final ModelPart Tail3;
	private final ModelPart Tail4;
	private final ModelPart Tail5;
	private final ModelPart Tail6;
	private final ModelPart Tail7;
	private final ModelPart Tail10;
	private final ModelPart Tail9;
	private final ModelPart Tail11;
	private final ModelPart Tail12;
	private final ModelPart RightArm;
	private final ModelPart RightUpper;
	private final ModelPart RightArmTurret;
	private final ModelPart RightMiddle;
	private final ModelPart RightLower;
	private final ModelPart Tip;
	private final ModelPart mainblade2;
	private final ModelPart blade3;
	private final ModelPart mainblade;
	private final ModelPart blade2;
	private final ModelPart LeftArm;
	private final ModelPart LeftUpper;
	private final ModelPart LeftArmTurret;
	private final ModelPart LeftMiddle;
	private final ModelPart LeftLower;
	private final ModelPart Tip2;
	private final ModelPart RightLeg;
	private final ModelPart RightLegUpper;
	private final ModelPart RightLegTurret;
	private final ModelPart RightLegLower;
	private final ModelPart RightLegFoot;
	private final ModelPart LeftLeg;
	private final ModelPart LeftLegUpper;
	private final ModelPart LeftLegTurret;
	private final ModelPart LeftLegLower;
	private final ModelPart LeftLower2;
	private final ModelPart LeftLegFoot;

	public MetalGearRayModel(ModelPart root) {
		this.main = root.getChild("main");
		this.truemain = this.main.getChild("truemain");
		this.Head = this.truemain.getChild("Head");
		this.headcap = this.Head.getChild("headcap");
		this.face = this.Head.getChild("face");
		this.bone = this.face.getChild("bone");
		this.Torso = this.truemain.getChild("Torso");
		this.Neck = this.Torso.getChild("Neck");
		this.obj = this.Neck.getChild("obj");
		this.Hip = this.Torso.getChild("Hip");
		this.Tail = this.Torso.getChild("Tail");
		this.Tail1 = this.Tail.getChild("Tail1");
		this.Tail2 = this.Tail1.getChild("Tail2");
		this.Tail3 = this.Tail2.getChild("Tail3");
		this.Tail4 = this.Tail3.getChild("Tail4");
		this.Tail5 = this.Tail4.getChild("Tail5");
		this.Tail6 = this.Tail5.getChild("Tail6");
		this.Tail7 = this.Tail6.getChild("Tail7");
		this.Tail10 = this.Tail7.getChild("Tail10");
		this.Tail9 = this.Tail10.getChild("Tail9");
		this.Tail11 = this.Tail9.getChild("Tail11");
		this.Tail12 = this.Tail11.getChild("Tail12");
		this.RightArm = this.truemain.getChild("RightArm");
		this.RightUpper = this.RightArm.getChild("RightUpper");
		this.RightArmTurret = this.RightUpper.getChild("RightArmTurret");
		this.RightMiddle = this.RightArm.getChild("RightMiddle");
		this.RightLower = this.RightArm.getChild("RightLower");
		this.Tip = this.RightLower.getChild("Tip");
		this.mainblade2 = this.RightArm.getChild("mainblade2");
		this.blade3 = this.mainblade2.getChild("blade3");
		this.mainblade = this.RightArm.getChild("mainblade");
		this.blade2 = this.mainblade.getChild("blade2");
		this.LeftArm = this.truemain.getChild("LeftArm");
		this.LeftUpper = this.LeftArm.getChild("LeftUpper");
		this.LeftArmTurret = this.LeftUpper.getChild("LeftArmTurret");
		this.LeftMiddle = this.LeftArm.getChild("LeftMiddle");
		this.LeftLower = this.LeftArm.getChild("LeftLower");
		this.Tip2 = this.LeftLower.getChild("Tip2");
		this.RightLeg = this.truemain.getChild("RightLeg");
		this.RightLegUpper = this.RightLeg.getChild("RightLegUpper");
		this.RightLegTurret = this.RightLegUpper.getChild("RightLegTurret");
		this.RightLegLower = this.RightLeg.getChild("RightLegLower");
		this.RightLegFoot = this.RightLegLower.getChild("RightLegFoot");
		this.LeftLeg = this.truemain.getChild("LeftLeg");
		this.LeftLegUpper = this.LeftLeg.getChild("LeftLegUpper");
		this.LeftLegTurret = this.LeftLegUpper.getChild("LeftLegTurret");
		this.LeftLegLower = this.LeftLeg.getChild("LeftLegLower");
		this.LeftLower2 = this.LeftLegLower.getChild("LeftLower2");
		this.LeftLegFoot = this.LeftLegLower.getChild("LeftLegFoot");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, -69.25F, -71.8F));

		PartDefinition truemain = main.addOrReplaceChild("truemain", CubeListBuilder.create(), PartPose.offset(0.0F, 93.25F, 71.8F));

		PartDefinition Head = truemain.addOrReplaceChild("Head", CubeListBuilder.create(), PartPose.offset(0.0F, -140.0F, -58.3F));

		PartDefinition headcap = Head.addOrReplaceChild("headcap", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition face = Head.addOrReplaceChild("face", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone = face.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offsetAndRotation(-7.6477F, 5.1357F, -85.2464F, -0.1042F, -0.1475F, -0.0133F));

		PartDefinition Torso = truemain.addOrReplaceChild("Torso", CubeListBuilder.create().texOffs(0, 0).addBox(-17.0F, -13.25F, -98.5F, 34.0F, 35.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(217, 65).addBox(-4.0F, -17.25F, -98.5F, 8.0F, 4.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(185, 65).addBox(-3.0F, -17.25F, -68.5F, 6.0F, 4.0F, 25.0F, new CubeDeformation(0.0F))
		.texOffs(157, 66).addBox(-3.0F, -17.25F, -43.5F, 6.0F, 4.0F, 20.0F, new CubeDeformation(0.0F))
		.texOffs(115, 71).addBox(-3.0F, -17.25F, -23.5F, 6.0F, 4.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(83, 71).addBox(-3.0F, -17.25F, -8.5F, 6.0F, 9.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(144, 71).addBox(-3.0F, -17.25F, -108.5F, 6.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(128, 10).addBox(-12.5F, -13.25F, -68.5F, 25.0F, 30.0F, 25.0F, new CubeDeformation(0.0F))
		.texOffs(350, 20).addBox(-10.0F, -13.25F, -43.5F, 20.0F, 25.0F, 20.0F, new CubeDeformation(0.0F))
		.texOffs(430, 25).addBox(-8.0F, -13.25F, -23.5F, 16.0F, 20.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -142.0F, 11.7F));

		PartDefinition Neck = Torso.addOrReplaceChild("Neck", CubeListBuilder.create().texOffs(228, 30).addBox(-11.5F, -15.25F, -8.5F, 23.0F, 25.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(294, 25).addBox(-6.5F, -15.25F, -23.5F, 13.0F, 25.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -100.0F));

		PartDefinition OBJ = Neck.addOrReplaceChild("obj", CubeListBuilder.create(), PartPose.offset(0.0F, -6.25F, -24.5F));

		PartDefinition Hip = Torso.addOrReplaceChild("Hip", CubeListBuilder.create().texOffs(0, 65).addBox(-14.5F, -10.25F, -12.5F, 29.0F, 30.0F, 25.0F, new CubeDeformation(0.0F))
		.texOffs(325, 168).addBox(-9.0F, 19.75F, -4.5F, 18.0F, 10.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -11.0F));

		PartDefinition cube_r1 = Hip.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(328, 187).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.825F, 25.35F, 3.0858F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r2 = Hip.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(328, 187).mirror().addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(7.825F, 25.35F, 3.0858F, 0.0F, -0.7854F, 0.0F));

		PartDefinition Tail = Torso.addOrReplaceChild("Tail", CubeListBuilder.create(), PartPose.offset(4.5F, 3.75F, -0.5F));

		PartDefinition Tail1 = Tail.addOrReplaceChild("Tail1", CubeListBuilder.create().texOffs(148, 100).addBox(-12.0F, -15.0F, 1.0F, 24.0F, 30.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.5F, 3.0F, 1.0F));

		PartDefinition Tail2 = Tail1.addOrReplaceChild("Tail2", CubeListBuilder.create().texOffs(236, 99).addBox(-10.0F, -14.5F, 0.0F, 20.0F, 29.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 21.0F));

		PartDefinition Tail3 = Tail2.addOrReplaceChild("Tail3", CubeListBuilder.create().texOffs(300, 65).addBox(-9.0F, -13.5F, -0.5F, 18.0F, 27.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 24.5F));

		PartDefinition Tail4 = Tail3.addOrReplaceChild("Tail4", CubeListBuilder.create().texOffs(327, 117).addBox(-9.0F, -12.5F, 0.5F, 18.0F, 25.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 24.1F));

		PartDefinition Tail5 = Tail4.addOrReplaceChild("Tail5", CubeListBuilder.create().texOffs(327, 117).addBox(-9.0F, -12.5F, -0.5F, 18.0F, 25.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 26.0F));

		PartDefinition Tail6 = Tail5.addOrReplaceChild("Tail6", CubeListBuilder.create().texOffs(327, 117).addBox(-9.0F, -12.5F, -0.5F, 18.0F, 25.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 25.0F));

		PartDefinition Tail7 = Tail6.addOrReplaceChild("Tail7", CubeListBuilder.create().texOffs(327, 117).addBox(-9.0F, -12.5F, -0.5F, 18.0F, 25.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 25.0F));

		PartDefinition Tail10 = Tail7.addOrReplaceChild("Tail10", CubeListBuilder.create().texOffs(327, 117).addBox(-9.0F, -12.5F, -0.5F, 18.0F, 25.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 25.0F));

		PartDefinition Tail9 = Tail10.addOrReplaceChild("Tail9", CubeListBuilder.create().texOffs(327, 117).addBox(-9.0F, -12.5F, -0.5F, 18.0F, 25.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 25.0F));

		PartDefinition Tail11 = Tail9.addOrReplaceChild("Tail11", CubeListBuilder.create().texOffs(390, 60).addBox(-9.0F, -13.5F, 0.0F, 18.0F, 25.0F, 40.0F, new CubeDeformation(0.001F))
		.texOffs(396, 128).addBox(-9.0F, -9.5F, 40.0F, 18.0F, 21.0F, 40.0F, new CubeDeformation(0.0012F)), PartPose.offset(0.0F, 1.0F, 24.5F));

		PartDefinition Tail12 = Tail11.addOrReplaceChild("Tail12", CubeListBuilder.create().texOffs(269, 152).addBox(-9.0F, -4.953F, -35.4114F, 18.0F, 13.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 73.5F, -0.2182F, 0.0F, 0.0F));

		PartDefinition RightArm = truemain.addOrReplaceChild("RightArm", CubeListBuilder.create(), PartPose.offset(-17.0F, -147.25F, -71.8F));

		PartDefinition RightUpper = RightArm.addOrReplaceChild("RightUpper", CubeListBuilder.create().texOffs(392, 392).addBox(-46.575F, -23.975F, -19.0F, 14.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(130, 155).addBox(-53.1047F, -15.3529F, -27.0F, 7.0F, 18.0F, 27.0F, new CubeDeformation(0.0F))
		.texOffs(0, 124).addBox(-26.5F, -15.0F, -21.0F, 10.0F, 15.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(383, 213).addBox(-61.5F, -20.2F, -26.0F, 35.0F, 25.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(17.0F, 7.5F, 13.5F));

		PartDefinition RightArmTurret = RightUpper.addOrReplaceChild("RightArmTurret", CubeListBuilder.create().texOffs(431, 193).addBox(-10.0F, -15.3333F, -8.0F, 20.0F, 5.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(442, 343).addBox(-9.0F, -10.3333F, -7.0F, 18.0F, 10.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(469, 370).addBox(-9.0F, -8.3333F, -10.0F, 18.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-39.575F, -23.8667F, -13.0F));

		PartDefinition RightMiddle = RightArm.addOrReplaceChild("RightMiddle", CubeListBuilder.create().texOffs(386, 294).addBox(-45.1202F, -16.3412F, -27.0F, 25.0F, 21.0F, 27.0F, new CubeDeformation(0.01F))
		.texOffs(243, 300).addBox(-20.1202F, -12.3412F, -27.0F, 33.0F, 17.0F, 27.0F, new CubeDeformation(0.02F))
		.texOffs(0, 442).addBox(-45.5F, -11.0F, -26.0F, 50.0F, 20.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(-49.0F, -0.5F, 13.5F));

		PartDefinition rightarmupper_r1 = RightMiddle.addOrReplaceChild("rightarmupper_r1", CubeListBuilder.create().texOffs(390, 263).addBox(-7.5F, -12.5F, -13.5F, 23.0F, 4.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.9047F, -2.7287F, -13.5F, 0.0F, 0.0F, 0.1745F));

		PartDefinition RightLower = RightArm.addOrReplaceChild("RightLower", CubeListBuilder.create().texOffs(145, 417).addBox(-1.0526F, -8.8876F, -12.5F, 4.0F, 25.0F, 25.0F, new CubeDeformation(0.0F))
		.texOffs(145, 417).addBox(3.9474F, -8.8876F, -12.5F, 4.0F, 25.0F, 25.0F, new CubeDeformation(0.0F))
		.texOffs(145, 417).addBox(8.9474F, -8.8876F, -12.5F, 4.0F, 25.0F, 25.0F, new CubeDeformation(0.0F))
		.texOffs(145, 417).addBox(13.9474F, -8.8876F, -12.5F, 4.0F, 25.0F, 25.0F, new CubeDeformation(0.0F))
		.texOffs(145, 417).addBox(18.9474F, -8.8876F, -12.5F, 4.0F, 25.0F, 25.0F, new CubeDeformation(0.0F))
		.texOffs(251, 344).addBox(-1.6728F, -14.4538F, -14.5F, 25.0F, 23.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offset(-117.4474F, -3.6124F, 0.0F));

		PartDefinition rightarmtiplower3_r1 = RightLower.addOrReplaceChild("rightarmtiplower3_r1", CubeListBuilder.create().texOffs(181, 368).addBox(-14.0F, -2.0F, -0.5F, 21.0F, 14.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.6995F, -8.3143F, -0.58F, 0.0F, -0.3491F, 0.0F));

		PartDefinition rightarmtiplower2_r1 = RightLower.addOrReplaceChild("rightarmtiplower2_r1", CubeListBuilder.create().texOffs(181, 368).addBox(-14.0F, -2.0F, -13.5F, 21.0F, 14.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.6995F, -8.3143F, 0.58F, 0.0F, 0.3491F, 0.0F));

		PartDefinition rightarmtiplower3_r2 = RightLower.addOrReplaceChild("rightarmtiplower3_r2", CubeListBuilder.create().texOffs(220, 407).addBox(-113.8795F, 35.9087F, -81.2383F, 10.0F, 12.0F, 10.0F, new CubeDeformation(-0.02F)), PartPose.offsetAndRotation(134.4474F, 2.5371F, -4.5442F, 0.0F, 0.6109F, 0.3054F));

		PartDefinition rightarmtiplower3_r3 = RightLower.addOrReplaceChild("rightarmtiplower3_r3", CubeListBuilder.create().texOffs(260, 409).addBox(-4.5801F, -6.3629F, -3.9149F, 8.0F, 12.0F, 8.0F, new CubeDeformation(-0.02F)), PartPose.offsetAndRotation(-10.3891F, 0.8128F, 0.5292F, 0.0F, -0.7854F, 0.3054F));

		PartDefinition rightarmtiplower2_r2 = RightLower.addOrReplaceChild("rightarmtiplower2_r2", CubeListBuilder.create().texOffs(220, 407).addBox(-5.0F, -6.0F, 1.5F, 10.0F, 12.0F, 10.0F, new CubeDeformation(-0.02F)), PartPose.offsetAndRotation(-1.3647F, 3.6582F, -0.7803F, 0.0F, -0.6109F, 0.3054F));

		PartDefinition rightarmtiplower1_r1 = RightLower.addOrReplaceChild("rightarmtiplower1_r1", CubeListBuilder.create().texOffs(298, 398).addBox(-4.675F, -12.0F, -4.0F, 10.0F, 12.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.1187F, 10.0269F, -7.5F, 0.0F, 0.0F, 0.3054F));

		PartDefinition Tip = RightLower.addOrReplaceChild("Tip", CubeListBuilder.create(), PartPose.offset(-23.6223F, -3.3143F, -1.175F));

		PartDefinition rightarmtiplower6_r1 = Tip.addOrReplaceChild("rightarmtiplower6_r1", CubeListBuilder.create().texOffs(260, 355).addBox(4.0F, 5.0F, -0.5F, 1.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.6532F, -12.0F, 4.3676F, 0.0F, 0.7854F, 0.0F));

		PartDefinition rightarmtiplower5_r1 = Tip.addOrReplaceChild("rightarmtiplower5_r1", CubeListBuilder.create().texOffs(238, 355).addBox(-3.0F, -7.0F, -7.0F, 10.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.1498F, 0.0F, 3.7569F, 0.0F, 0.7854F, 0.0F));

		PartDefinition rightarmtiplower4_r1 = Tip.addOrReplaceChild("rightarmtiplower4_r1", CubeListBuilder.create().texOffs(150, 385).addBox(-3.0F, -7.0F, -4.0F, 10.0F, 14.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.1498F, 0.0F, -1.4069F, 0.0F, -0.7854F, 0.0F));

		PartDefinition mainblade2 = RightArm.addOrReplaceChild("mainblade2", CubeListBuilder.create().texOffs(332, 468).addBox(-4.0F, -3.0F, -38.0F, 3.0F, 3.0F, 29.0F, new CubeDeformation(0.01F))
		.texOffs(277, 450).addBox(-6.0681F, -3.0F, -25.4725F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(299, 460).addBox(-1.0F, -3.0F, -17.0F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(333, 456).addBox(-1.0F, -3.0F, -25.0F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(319, 456).addBox(-1.0F, -3.0F, -33.0F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(275, 479).addBox(-4.0F, -3.0F, -9.0F, 8.0F, 3.0F, 11.0F, new CubeDeformation(0.01F))
		.texOffs(265, 461).addBox(-4.75F, -4.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(255, 461).addBox(-4.75F, -2.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-38.75F, 5.75F, 16.6087F, 1.5708F, 0.0F, 1.5708F));

		PartDefinition cube_r3 = mainblade2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(319, 450).addBox(-2.5F, -1.0F, -2.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2407F, -0.6237F, -2.4073F, 0.3491F, -0.7418F, 0.0F));

		PartDefinition cube_r4 = mainblade2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(319, 450).addBox(-2.5F, -1.0F, -2.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2407F, -2.3763F, -2.4073F, -0.3491F, -0.7418F, 0.0F));

		PartDefinition cube_r5 = mainblade2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(377, 474).addBox(-2.5F, -2.5F, -3.5F, 5.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.1624F, -1.5F, -6.1213F, 0.0F, -0.7418F, 0.0F));

		PartDefinition cube_r6 = mainblade2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(377, 486).addBox(-4.0F, -1.5F, -5.5F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.2141F, -1.5F, 4.7631F, 0.0F, 0.5236F, 0.0F));

		PartDefinition cube_r7 = mainblade2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(377, 461).addBox(-1.5F, -1.05F, -6.5F, 3.0F, 2.0F, 11.0F, new CubeDeformation(-0.02F)), PartPose.offsetAndRotation(-1.0514F, -1.5F, -41.7156F, 0.0F, -0.3491F, 0.0F));

		PartDefinition cube_r8 = mainblade2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(240, 485).addBox(-1.5F, -1.5F, -6.0F, 3.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.549F, -1.5F, -11.8241F, 0.0F, 0.1745F, 0.0F));

		PartDefinition cube_r9 = mainblade2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(210, 485).addBox(-1.5F, -1.5F, -6.0F, 3.0F, 3.0F, 12.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-3.549F, -1.5F, -31.1209F, 0.0F, -0.1745F, 0.0F));

		PartDefinition blade3 = mainblade2.addOrReplaceChild("blade3", CubeListBuilder.create(), PartPose.offsetAndRotation(18.0F, -1.55F, -24.5F, 0.0F, -0.5236F, 0.0F));

		PartDefinition cube_r10 = blade3.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(438, 477).addBox(-2.5F, -1.0F, 1.0F, 4.0F, 2.0F, 32.0F, new CubeDeformation(0.01F))
		.texOffs(352, 505).addBox(-1.95F, -1.0F, -0.575F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(275, 461).addBox(-1.5F, -1.0F, -1.575F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.01F))
		.texOffs(299, 450).addBox(-0.5F, -1.0F, -4.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-22.6289F, 0.0F, -7.1047F, 0.0F, 0.5236F, 0.0F));

		PartDefinition cube_r11 = blade3.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(397, 486).addBox(-0.5033F, -1.0F, -0.8252F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.01F))
		.texOffs(313, 460).addBox(-0.5033F, -1.0F, 0.0248F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-24.3353F, 0.0F, -11.8078F, 0.0F, 0.6545F, 0.0F));

		PartDefinition cube_r12 = blade3.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(288, 472).addBox(-0.5F, -1.0F, -4.0F, 1.0F, 2.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.7101F, 0.0F, 0.4574F, 0.0F, 0.5236F, 0.0F));

		PartDefinition cube_r13 = blade3.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(255, 449).addBox(-0.5F, -1.0F, -5.95F, 1.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-24.1695F, 0.0F, -6.7878F, 0.0F, 0.1309F, 0.0F));

		PartDefinition mainblade = RightArm.addOrReplaceChild("mainblade", CubeListBuilder.create().texOffs(332, 468).addBox(-4.0F, -3.0F, -38.0F, 3.0F, 3.0F, 29.0F, new CubeDeformation(0.01F))
		.texOffs(277, 450).addBox(-6.0681F, -3.0F, -25.4725F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(299, 460).addBox(-1.0F, -3.0F, -17.0F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(333, 456).addBox(-1.0F, -3.0F, -25.0F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(319, 456).addBox(-1.0F, -3.0F, -33.0F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(275, 479).addBox(-4.0F, -3.0F, -9.0F, 8.0F, 3.0F, 11.0F, new CubeDeformation(0.01F))
		.texOffs(265, 461).addBox(-4.75F, -4.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(255, 461).addBox(-4.75F, -2.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-38.75F, 5.75F, -13.3913F, 1.5708F, 0.0F, 1.5708F));

		PartDefinition cube_r14 = mainblade.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(319, 450).addBox(-2.5F, -1.0F, -2.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2407F, -0.6237F, -2.4073F, 0.3491F, -0.7418F, 0.0F));

		PartDefinition cube_r15 = mainblade.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(319, 450).addBox(-2.5F, -1.0F, -2.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2407F, -2.3763F, -2.4073F, -0.3491F, -0.7418F, 0.0F));

		PartDefinition cube_r16 = mainblade.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(377, 474).addBox(-2.5F, -2.5F, -3.5F, 5.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.1624F, -1.5F, -6.1212F, 0.0F, -0.7418F, 0.0F));

		PartDefinition cube_r17 = mainblade.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(377, 486).addBox(-2.0F, -2.5F, -3.0F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(377, 486).addBox(-2.0F, -1.5F, -3.0F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6015F, 0.8033F, 1.5916F, -0.0072F, 1.0923F, 1.4908F));

		PartDefinition cube_r18 = mainblade.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(377, 461).addBox(-1.5F, -1.05F, -6.5F, 3.0F, 2.0F, 11.0F, new CubeDeformation(-0.02F)), PartPose.offsetAndRotation(-1.0514F, -1.5F, -41.7156F, 0.0F, -0.3491F, 0.0F));

		PartDefinition cube_r19 = mainblade.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(240, 485).addBox(-1.5F, -1.5F, -6.0F, 3.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.549F, -1.5F, -11.8241F, 0.0F, 0.1745F, 0.0F));

		PartDefinition cube_r20 = mainblade.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(210, 485).addBox(-1.5F, -1.5F, -6.0F, 3.0F, 3.0F, 12.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-3.549F, -1.5F, -31.1209F, 0.0F, -0.1745F, 0.0F));

		PartDefinition blade2 = mainblade.addOrReplaceChild("blade2", CubeListBuilder.create(), PartPose.offsetAndRotation(18.0F, -1.55F, -24.5F, 0.0F, -0.5236F, 0.0F));

		PartDefinition cube_r21 = blade2.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(438, 477).addBox(-2.5F, -1.0F, 1.0F, 4.0F, 2.0F, 32.0F, new CubeDeformation(0.01F))
		.texOffs(352, 505).addBox(-1.95F, -1.0F, -0.575F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(275, 461).addBox(-1.5F, -1.0F, -1.575F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.01F))
		.texOffs(299, 450).addBox(-0.5F, -1.0F, -4.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-22.6289F, 0.0F, -7.1047F, 0.0F, 0.5236F, 0.0F));

		PartDefinition cube_r22 = blade2.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(397, 486).addBox(-0.5033F, -1.0F, -0.8252F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.01F))
		.texOffs(313, 460).addBox(-0.5033F, -1.0F, 0.0248F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-24.3353F, 0.0F, -11.8078F, 0.0F, 0.6545F, 0.0F));

		PartDefinition cube_r23 = blade2.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(288, 472).addBox(-0.5F, -1.0F, -4.0F, 1.0F, 2.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.7101F, 0.0F, 0.4574F, 0.0F, 0.5236F, 0.0F));

		PartDefinition cube_r24 = blade2.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(255, 449).addBox(-0.5F, -1.0F, -5.95F, 1.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-24.1695F, 0.0F, -6.7878F, 0.0F, 0.1309F, 0.0F));

		PartDefinition LeftArm = truemain.addOrReplaceChild("LeftArm", CubeListBuilder.create(), PartPose.offset(17.0F, -147.25F, -71.8F));

		PartDefinition LeftUpper = LeftArm.addOrReplaceChild("LeftUpper", CubeListBuilder.create().texOffs(392, 392).mirror().addBox(32.575F, -23.975F, -19.0F, 14.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(130, 155).mirror().addBox(46.1047F, -15.2279F, -27.0F, 7.0F, 18.0F, 27.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 124).mirror().addBox(16.5F, -15.0F, -21.0F, 10.0F, 15.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(383, 213).mirror().addBox(26.5F, -20.0F, -26.0F, 35.0F, 25.0F, 25.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-17.0F, 7.5F, 13.5F));

		PartDefinition LeftArmTurret = LeftUpper.addOrReplaceChild("LeftArmTurret", CubeListBuilder.create().texOffs(431, 193).mirror().addBox(-10.0F, -15.3333F, -8.0F, 20.0F, 5.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(442, 343).mirror().addBox(-9.0F, -10.3333F, -7.0F, 18.0F, 10.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(469, 370).mirror().addBox(-9.0F, -8.3333F, -10.0F, 18.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(39.575F, -23.8667F, -13.0F));

		PartDefinition LeftMiddle = LeftArm.addOrReplaceChild("LeftMiddle", CubeListBuilder.create().texOffs(386, 294).mirror().addBox(20.1202F, -16.3412F, -27.0F, 25.0F, 21.0F, 27.0F, new CubeDeformation(0.01F)).mirror(false)
		.texOffs(145, 417).mirror().addBox(45.5F, -12.0F, -26.0F, 4.0F, 25.0F, 25.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(145, 417).mirror().addBox(50.5F, -12.0F, -26.0F, 4.0F, 25.0F, 25.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(145, 417).mirror().addBox(55.5F, -12.0F, -26.0F, 4.0F, 25.0F, 25.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(145, 417).mirror().addBox(60.5F, -12.0F, -26.0F, 4.0F, 25.0F, 25.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(145, 417).mirror().addBox(65.5F, -12.0F, -26.0F, 4.0F, 25.0F, 25.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(145, 417).mirror().addBox(40.5F, -12.0F, -26.0F, 4.0F, 25.0F, 25.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(145, 417).mirror().addBox(35.5F, -12.0F, -26.0F, 4.0F, 25.0F, 25.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(145, 417).mirror().addBox(30.5F, -12.0F, -26.0F, 4.0F, 25.0F, 25.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(145, 417).mirror().addBox(25.5F, -12.0F, -26.0F, 4.0F, 25.0F, 25.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(145, 417).mirror().addBox(20.5F, -12.0F, -26.0F, 4.0F, 25.0F, 25.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(145, 417).mirror().addBox(10.5F, -12.0F, -26.0F, 4.0F, 25.0F, 25.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(145, 417).mirror().addBox(15.5F, -12.0F, -26.0F, 4.0F, 25.0F, 25.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(145, 417).mirror().addBox(5.5F, -12.0F, -26.0F, 4.0F, 25.0F, 25.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(243, 300).mirror().addBox(-12.8798F, -12.3412F, -27.0F, 33.0F, 17.0F, 27.0F, new CubeDeformation(0.02F)).mirror(false)
		.texOffs(0, 155).mirror().addBox(-4.5F, -11.0F, -26.0F, 10.0F, 20.0F, 25.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(49.0F, -0.5F, 13.5F));

		PartDefinition leftarmupper_r1 = LeftMiddle.addOrReplaceChild("leftarmupper_r1", CubeListBuilder.create().texOffs(390, 263).mirror().addBox(-15.5F, -12.5F, -13.5F, 23.0F, 4.0F, 27.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(14.9047F, -2.7287F, -13.5F, 0.0F, 0.0F, -0.1745F));

		PartDefinition LeftLower = LeftArm.addOrReplaceChild("LeftLower", CubeListBuilder.create().texOffs(251, 344).mirror().addBox(-23.3272F, -14.2287F, -14.5F, 25.0F, 23.0F, 29.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(117.4474F, -3.6124F, 0.0F));

		PartDefinition leftarmtiplower4_r1 = LeftLower.addOrReplaceChild("leftarmtiplower4_r1", CubeListBuilder.create().texOffs(181, 368).mirror().addBox(-7.0F, -2.0F, -0.5F, 21.0F, 14.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.6995F, -8.3143F, -0.58F, 0.0F, 0.3491F, 0.0F));

		PartDefinition leftarmtiplower3_r1 = LeftLower.addOrReplaceChild("leftarmtiplower3_r1", CubeListBuilder.create().texOffs(181, 368).mirror().addBox(-7.0F, -2.0F, -13.5F, 21.0F, 14.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.6995F, -8.3143F, 0.58F, 0.0F, -0.3491F, 0.0F));

		PartDefinition leftarmtiplower4_r2 = LeftLower.addOrReplaceChild("leftarmtiplower4_r2", CubeListBuilder.create().texOffs(220, 407).mirror().addBox(103.8795F, 35.9087F, -81.2383F, 10.0F, 12.0F, 10.0F, new CubeDeformation(-0.02F)).mirror(false), PartPose.offsetAndRotation(-134.4474F, 2.5371F, -4.5442F, 0.0F, -0.6109F, -0.3054F));

		PartDefinition leftarmtiplower4_r3 = LeftLower.addOrReplaceChild("leftarmtiplower4_r3", CubeListBuilder.create().texOffs(260, 409).mirror().addBox(-3.4199F, -6.3629F, -3.9149F, 8.0F, 12.0F, 8.0F, new CubeDeformation(-0.02F)).mirror(false), PartPose.offsetAndRotation(10.3891F, 0.8128F, 0.5292F, 0.0F, 0.7854F, -0.3054F));

		PartDefinition leftarmtiplower3_r2 = LeftLower.addOrReplaceChild("leftarmtiplower3_r2", CubeListBuilder.create().texOffs(220, 407).mirror().addBox(-5.0F, -6.0F, 1.5F, 10.0F, 12.0F, 10.0F, new CubeDeformation(-0.02F)).mirror(false), PartPose.offsetAndRotation(1.3647F, 3.6582F, -0.7803F, 0.0F, 0.6109F, -0.3054F));

		PartDefinition leftarmtiplower_r1 = LeftLower.addOrReplaceChild("leftarmtiplower_r1", CubeListBuilder.create().texOffs(298, 398).mirror().addBox(-5.325F, -12.0F, -4.0F, 10.0F, 12.0F, 23.0F, new CubeDeformation(-0.02F)).mirror(false), PartPose.offsetAndRotation(1.1187F, 10.0269F, -7.5F, 0.0F, 0.0F, -0.3054F));

		PartDefinition Tip2 = LeftLower.addOrReplaceChild("Tip2", CubeListBuilder.create(), PartPose.offset(23.6223F, -3.3143F, -1.175F));

		PartDefinition leftarmtiplower7_r1 = Tip2.addOrReplaceChild("leftarmtiplower7_r1", CubeListBuilder.create().texOffs(260, 355).mirror().addBox(-5.0F, 5.0F, -0.5F, 1.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.6532F, -12.0F, 4.3676F, 0.0F, -0.7854F, 0.0F));

		PartDefinition leftarmtiplower6_r1 = Tip2.addOrReplaceChild("leftarmtiplower6_r1", CubeListBuilder.create().texOffs(238, 355).mirror().addBox(-7.0F, -7.0F, -7.0F, 10.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.1498F, 0.0F, 3.7569F, 0.0F, -0.7854F, 0.0F));

		PartDefinition leftarmtiplower5_r1 = Tip2.addOrReplaceChild("leftarmtiplower5_r1", CubeListBuilder.create().texOffs(150, 385).mirror().addBox(-7.0F, -7.0F, -4.0F, 10.0F, 14.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.1498F, 0.0F, -1.4069F, 0.0F, 0.7854F, 0.0F));

		PartDefinition RightLeg = truemain.addOrReplaceChild("RightLeg", CubeListBuilder.create(), PartPose.offset(-12.5F, -138.0F, 0.7F));

		PartDefinition RightLegUpper = RightLeg.addOrReplaceChild("RightLegUpper", CubeListBuilder.create().texOffs(108, 90).addBox(-14.0F, -5.0F, -7.0F, 15.0F, 15.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(167, 171).addBox(-34.0F, -10.0F, -32.0F, 25.0F, 29.0F, 43.0F, new CubeDeformation(-0.01F)), PartPose.offset(-1.0F, -2.0F, 0.0F));

		PartDefinition RightLegUpper_r1 = RightLegUpper.addOrReplaceChild("RightLegUpper_r1", CubeListBuilder.create().texOffs(0, 120).addBox(-12.5F, 0.0F, -25.0F, 25.0F, 10.0F, 80.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(-21.513F, 21.6702F, -44.6985F, 0.2094F, 0.0F, 0.0F));

		PartDefinition RightLegUpper_r2 = RightLegUpper.addOrReplaceChild("RightLegUpper_r2", CubeListBuilder.create().texOffs(0, 214).addBox(-12.5F, -15.0F, -25.0F, 25.0F, 28.0F, 50.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-21.5F, 13.4253F, -49.3567F, 0.3927F, 0.0F, 0.0F));

		PartDefinition RightLegLogo_r1 = RightLegUpper.addOrReplaceChild("RightLegLogo_r1", CubeListBuilder.create().texOffs(29, 221).addBox(-0.025F, -21.45F, 2.45F, 0.0F, 38.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-34.0F, 14.1424F, -27.5064F, 1.8326F, 0.0F, 0.0F));

		PartDefinition RightLegTurret = RightLegUpper.addOrReplaceChild("RightLegTurret", CubeListBuilder.create(), PartPose.offset(-42.2F, -0.8F, -0.45F));

		PartDefinition rightarturret_r1 = RightLegTurret.addOrReplaceChild("rightarturret_r1", CubeListBuilder.create().texOffs(469, 370).addBox(-9.0F, -2.5F, -9.5F, 18.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.375F, 0.0F, 1.95F, 0.0F, 0.0F, -1.5708F));

		PartDefinition rightarturret_r2 = RightLegTurret.addOrReplaceChild("rightarturret_r2", CubeListBuilder.create().texOffs(392, 392).addBox(-7.0F, -2.0F, -6.0F, 14.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.125F, 0.0F, 1.65F, 0.0F, 0.0F, -1.5708F));

		PartDefinition rightarturret_r3 = RightLegTurret.addOrReplaceChild("rightarturret_r3", CubeListBuilder.create().texOffs(442, 343).addBox(-9.0F, -5.0F, -7.0F, 18.0F, 10.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.125F, 0.0F, 2.45F, 0.0F, 0.0F, -1.5708F));

		PartDefinition rightarmextra_r1 = RightLegTurret.addOrReplaceChild("rightarmextra_r1", CubeListBuilder.create().texOffs(431, 193).addBox(-10.0F, -2.5F, -7.5F, 20.0F, 5.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.375F, 0.5F, 1.95F, 0.0F, 0.0F, -1.5708F));

		PartDefinition RightLegLower = RightLeg.addOrReplaceChild("RightLegLower", CubeListBuilder.create().texOffs(0, 292).addBox(-9.95F, -7.2351F, -11.2286F, 20.0F, 31.0F, 21.0F, new CubeDeformation(0.0F))
		.texOffs(82, 297).addBox(-8.95F, -7.4851F, 9.7714F, 18.0F, 30.0F, 17.0F, new CubeDeformation(0.0F))
		.texOffs(80, 347).addBox(-8.95F, -6.4851F, 46.7713F, 18.0F, 22.0F, 20.0F, new CubeDeformation(0.0F))
		.texOffs(0, 344).addBox(-9.95F, -4.4851F, 26.7714F, 20.0F, 25.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(-22.3F, 31.2351F, -55.7714F));

		PartDefinition RightLegFoot = RightLegLower.addOrReplaceChild("RightLegFoot", CubeListBuilder.create().texOffs(273, 214).addBox(-12.5F, -9.25F, -15.0F, 25.0F, 55.0F, 30.0F, new CubeDeformation(0.02F))
		.texOffs(426, 390).addBox(-12.5F, 45.75F, -11.0F, 25.0F, 21.0F, 18.0F, new CubeDeformation(0.02F))
		.texOffs(153, 308).addBox(-9.75F, 45.75F, -11.5F, 19.0F, 35.0F, 23.0F, new CubeDeformation(0.01F)), PartPose.offset(-0.2F, 26.1688F, 56.5111F));

		PartDefinition RightLegUpper_r3 = RightLegFoot.addOrReplaceChild("RightLegUpper_r3", CubeListBuilder.create().texOffs(157, 250).addBox(-12.5F, -14.0F, -17.5F, 25.0F, 28.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.9785F, 7.4035F, 0.7069F, 0.0F, 0.0F));

		PartDefinition LeftLeg = truemain.addOrReplaceChild("LeftLeg", CubeListBuilder.create(), PartPose.offset(12.5F, -138.0F, 0.7F));

		PartDefinition LeftLegUpper = LeftLeg.addOrReplaceChild("LeftLegUpper", CubeListBuilder.create().texOffs(108, 90).mirror().addBox(-1.0F, -5.0F, -7.0F, 15.0F, 15.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(167, 171).mirror().addBox(9.0F, -10.0F, -32.0F, 25.0F, 29.0F, 43.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offset(1.0F, -2.0F, 0.0F));

		PartDefinition LeftLegUpper_r1 = LeftLegUpper.addOrReplaceChild("LeftLegUpper_r1", CubeListBuilder.create().texOffs(0, 120).mirror().addBox(-12.5F, 0.0F, -25.0F, 25.0F, 10.0F, 80.0F, new CubeDeformation(0.05F)).mirror(false), PartPose.offsetAndRotation(21.513F, 21.6702F, -44.6985F, 0.2094F, 0.0F, 0.0F));

		PartDefinition LeftLegUpper_r2 = LeftLegUpper.addOrReplaceChild("LeftLegUpper_r2", CubeListBuilder.create().texOffs(0, 214).mirror().addBox(-12.5F, -15.0F, -25.0F, 25.0F, 28.0F, 50.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(21.5F, 13.4253F, -49.3567F, 0.3927F, 0.0F, 0.0F));

		PartDefinition LeftLegTurret = LeftLegUpper.addOrReplaceChild("LeftLegTurret", CubeListBuilder.create(), PartPose.offset(42.2F, -0.8F, -0.45F));

		PartDefinition leftarturret_r1 = LeftLegTurret.addOrReplaceChild("leftarturret_r1", CubeListBuilder.create().texOffs(469, 370).mirror().addBox(-9.0F, -2.5F, -9.5F, 18.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.375F, 0.0F, 1.95F, 0.0F, 0.0F, 1.5708F));

		PartDefinition leftarturret_r2 = LeftLegTurret.addOrReplaceChild("leftarturret_r2", CubeListBuilder.create().texOffs(392, 392).mirror().addBox(-7.0F, -2.0F, -6.0F, 14.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-7.125F, 0.0F, 1.65F, 0.0F, 0.0F, 1.5708F));

		PartDefinition leftarturret_r3 = LeftLegTurret.addOrReplaceChild("leftarturret_r3", CubeListBuilder.create().texOffs(442, 343).mirror().addBox(-9.0F, -5.0F, -7.0F, 18.0F, 10.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.125F, 0.0F, 2.45F, 0.0F, 0.0F, 1.5708F));

		PartDefinition leftarmextra_r1 = LeftLegTurret.addOrReplaceChild("leftarmextra_r1", CubeListBuilder.create().texOffs(431, 193).mirror().addBox(-10.0F, -2.5F, -7.5F, 20.0F, 5.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(7.375F, 0.5F, 1.95F, 0.0F, 0.0F, 1.5708F));

		PartDefinition LeftLegLower = LeftLeg.addOrReplaceChild("LeftLegLower", CubeListBuilder.create(), PartPose.offset(22.3F, 31.2351F, -55.7714F));

		PartDefinition LeftLower2 = LeftLegLower.addOrReplaceChild("LeftLower2", CubeListBuilder.create().texOffs(0, 292).mirror().addBox(-10.05F, -7.2351F, -11.2286F, 20.0F, 31.0F, 21.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(82, 297).mirror().addBox(-9.05F, -7.4851F, 9.7714F, 18.0F, 30.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(80, 347).mirror().addBox(-9.05F, -6.4851F, 46.7713F, 18.0F, 22.0F, 20.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 344).mirror().addBox(-10.05F, -4.4851F, 26.7714F, 20.0F, 28.0F, 20.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition LeftLegFoot = LeftLegLower.addOrReplaceChild("LeftLegFoot", CubeListBuilder.create().texOffs(426, 390).mirror().addBox(-12.5F, 45.75F, -11.0F, 25.0F, 21.0F, 18.0F, new CubeDeformation(0.02F)).mirror(false)
		.texOffs(153, 308).mirror().addBox(-9.25F, 45.75F, -11.5F, 19.0F, 35.0F, 23.0F, new CubeDeformation(0.01F)).mirror(false)
		.texOffs(273, 214).mirror().addBox(-12.5F, -9.25F, -15.0F, 25.0F, 55.0F, 30.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(0.2F, 26.1688F, 56.5111F));

		PartDefinition LeftLegUpper_r3 = LeftLegFoot.addOrReplaceChild("LeftLegUpper_r3", CubeListBuilder.create().texOffs(157, 250).mirror().addBox(-12.5F, -14.0F, -17.5F, 25.0F, 28.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -9.9785F, 7.4035F, 0.7069F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	public ModelPart getHeadObj(){
		return this.Neck;
	}
	public ModelPart getTorso(){
		return this.Torso;
	}
	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animate(entity.idle, MetalGearRayAnim.idlebody,ageInTicks,1.0F);
		this.animate(entity.idle,MetalGearRayAnim.idlelegs,ageInTicks,1.0F);
		this.animate(entity.idle,MetalGearRayAnim.idletail,ageInTicks,1.0F);

		this.animate(entity.tower_on,MetalGearRayAnim.turreton,ageInTicks,1.0F);
		this.animate(entity.tower_off,MetalGearRayAnim.turretoff,ageInTicks,1.0F);

		this.animate(entity.blade_on,MetalGearRayAnim.bladeon,ageInTicks,1.0F);
		this.animate(entity.blade_off,MetalGearRayAnim.bladeoff,ageInTicks,1.0F);

		this.animateWalk(MetalGearRayAnim.walkbody,limbSwing,limbSwingAmount,2.0F,2.0F);
		this.animateWalk(MetalGearRayAnim.walklegs,limbSwing,limbSwingAmount,2.0F,2.0F);
		this.animateWalk(MetalGearRayAnim.walktail,limbSwing,limbSwingAmount,2.0F,2.0F);
		if(entity.stomp.isStarted()){
			this.LeftLeg.getAllParts().forEach(ModelPart::resetPose);
			this.RightLeg.getAllParts().forEach(ModelPart::resetPose);
		}
		this.animate(entity.stomp,MetalGearRayAnim.stomp,ageInTicks,1.0F);

		this.animate(entity.meleeAttack,MetalGearRayAnim.stomp,ageInTicks,1.0F);
		this.animate(entity.prepare_laser,MetalGearRayAnim.lazer,ageInTicks,1.0F);
		this.animate(entity.laser,MetalGearRayAnim.lazerloop,ageInTicks,1.0F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return main;
	}
}