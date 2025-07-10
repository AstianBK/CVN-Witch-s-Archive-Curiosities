package com.TBK.WitchArchive.common.entity;

import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public class MetalGearRayEntity extends PathfinderMob {
    public AnimationState stomp = new AnimationState();
    public AnimationState meleeAttack = new AnimationState();
    public AnimationState idle = new AnimationState();
    public AnimationState blade_on = new AnimationState();
    public AnimationState blade_off = new AnimationState();
    public AnimationState tower_on = new AnimationState();
    public AnimationState tower_off = new AnimationState();
    private int idleAnimationTimeout;

    public MetalGearRayEntity(EntityType<? extends PathfinderMob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide){
            this.clientTick();
        }
    }

    public void clientTick(){
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 80;
            this.idle.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

    }
}
