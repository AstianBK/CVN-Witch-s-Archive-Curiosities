package com.TBK.WitchArchive.common.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.network.NetworkHooks;

public class TowerPart<T extends MetalGearRayEntity> extends PartEntity<T> {
    public final T parentMob;
    public final String name;
    private EntityDimensions size;

    public TowerPart(T parent,String name,float sizeX,float sizeZ) {
        super(parent);
        this.parentMob = parent;
        this.name = name;
        this.size = EntityDimensions.scalable(sizeX,sizeZ);
    }
    protected void setSize(EntityDimensions size) {
        this.size = size;
        this.refreshDimensions();
    }
    protected void defineSynchedData() {
    }

    protected void readAdditionalSaveData(CompoundTag p_31025_) {
    }

    protected void addAdditionalSaveData(CompoundTag p_31028_) {
    }

    public boolean isPickable() {
        return true;
    }


    public boolean hurt(DamageSource p_31020_, float p_31021_) {
        return !this.isInvulnerableTo(p_31020_) && this.parentMob.hurt(p_31020_, p_31021_);
    }

    public boolean is(Entity p_31031_) {
        return this == p_31031_ || this.parentMob == p_31031_;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public boolean shouldBeSaved() {
        return false;
    }
    public EntityDimensions getDimensions(Pose p_19975_) {
        return this.size;
    }
}
