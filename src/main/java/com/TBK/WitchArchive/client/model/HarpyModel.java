package com.TBK.WitchArchive.client.model;

import com.TBK.WitchArchive.common.entity.HarpyEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;

public class HarpyModel <T extends HarpyEntity> extends HierarchicalModel<T> {
    @Override
    public ModelPart root() {
        return null;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //this.animateWalk(CustomModelAnimation.walk,limbSwing,limbSwingAmount,2.0F, 2.5F);

    }
}
