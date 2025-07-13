package com.TBK.WitchArchive.common.entity;

import com.TBK.WitchArchive.common.network.PacketHandler;
import com.TBK.WitchArchive.common.network.messager.PacketActionRay;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class MetalGearRayEntity extends PathfinderMob {
    public static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(MetalGearRayEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> LASER =
            SynchedEntityData.defineId(MetalGearRayEntity.class, EntityDataSerializers.BOOLEAN);

    public static final EntityDataAccessor<Boolean> TOWER_ON =
            SynchedEntityData.defineId(MetalGearRayEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> BLADE_ON =
            SynchedEntityData.defineId(MetalGearRayEntity.class, EntityDataSerializers.BOOLEAN);

    public AnimationState stomp = new AnimationState();
    public AnimationState meleeAttack = new AnimationState();
    public AnimationState idle = new AnimationState();
    public AnimationState blade_on = new AnimationState();
    public AnimationState blade_off = new AnimationState();
    public AnimationState tower_on = new AnimationState();
    public AnimationState tower_off = new AnimationState();
    public AnimationState prepare_laser = new AnimationState();
    public AnimationState laser = new AnimationState();
    private int idleAnimationTimeout;
    private int attackTimer;
    private final TowerPart<?> tower0;
    private final TowerPart<?> tower1;
    private final TowerPart<?> tower2;
    private final TowerPart<?> tower3;
    private final TowerPart<?> body;
    private final TowerPart<?>[] towers;
    public Vec3 laserPosition = Vec3.ZERO;
    public int prepareLaserTimer = 0;
    public int laserTimer = 0;
    public MetalGearRayEntity(EntityType<? extends PathfinderMob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
        this.tower0=new TowerPart<>(this,"tower0",1,1);
        this.tower1=new TowerPart<>(this,"tower1",1,1);
        this.tower2=new TowerPart<>(this,"tower2",1,1);
        this.tower3=new TowerPart<>(this,"tower3",1,1);
        this.body = new TowerPart<>(this,"body",10,10);

        this.towers = new TowerPart[]{this.tower0,this.tower1,this.tower2,this.tower3};
        this.setId(ENTITY_COUNTER.getAndAdd(this.towers.length + 2) + 1);
    }
    @Override
    public void setId(int p_20235_) {
        super.setId(p_20235_);
        for (int i = 0; i < this.towers.length; i++) // Forge: Fix MC-158205: Set part ids to successors of parent mob id
            this.towers[i].setId(p_20235_ + i + 1);
    }

    public static AttributeSupplier setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 35.0D)
                .add(Attributes.FOLLOW_RANGE, 45.D)
                .add(Attributes.MOVEMENT_SPEED, 0.1d)
                .add(Attributes.ATTACK_DAMAGE,12.0D)
                .build();

    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5,new RandomStrollGoal(this,1.0D));
        this.goalSelector.addGoal(3,new RayAttackGoal(this,2.0D,false));
        this.targetSelector.addGoal(4,new NearestAttackableTargetGoal<>(this,LivingEntity.class,false));
    }

    @Override
    public void tick() {
        super.tick();

        if(this.towerOn()){
            for (TowerPart<?> leg : this.towers) {
                leg.tick();
                leg.setSize(EntityDimensions.scalable(1,1));
            }
        }else{
            for (TowerPart<?> leg : this.towers) {
                leg.tick();
                leg.setSize(EntityDimensions.scalable(0, 0));
            }
        }

        this.tickPart(this.body,0,0,0);
        if(this.towerOn()){
            Vec3[] avec3 = new Vec3[this.towers.length];

            for(int j = 0; j < this.towers.length; ++j) {
                avec3[j] = new Vec3(this.towers[j].getX(), this.towers[j].getY(), this.towers[j].getZ());
            }

            // Rotación enY ( horizontal)
            float yawRad = (float)Math.toRadians(this.getYRot());
            float sin = (float)Math.sin(yawRad);
            float cos = (float)Math.cos(yawRad);

            // Pierna 1: adelante derecha
            double leg0X =  ( 2 * cos) + ( -4 * sin);
            double leg0Z =  ( 2 * sin) - ( -4 * cos);

            // Pierna 2: atrás izquierda
            double leg1X =  ( -2 * cos) + ( -4 * sin);
            double leg1Z =  ( -2 * sin) - ( -4 * cos);

            // Pierna 3: atrás derecha
            double leg2X =  - ( 4 * cos);
            double leg2Z =  - ( 4 * sin);


            double leg3X =  - ( -4 * cos);
            double leg3Z =  - ( -4 * sin);

            this.tickPart(this.tower0,leg0X, 9,leg0Z);
            this.tickPart(this.tower1,leg1X,9,leg1Z);
            this.tickPart(this.tower2,leg2X,8,leg2Z);
            this.tickPart(this.tower3,leg3X,8,leg3Z);


            for(int l = 0; l < this.towers.length; ++l) {
                this.towers[l].xo = avec3[l].x;
                this.towers[l].yo = avec3[l].y;
                this.towers[l].zo = avec3[l].z;
                this.towers[l].xOld = avec3[l].x;
                this.towers[l].yOld = avec3[l].y;
                this.towers[l].zOld = avec3[l].z;
            }
            this.checkTick();
        }

        if(this.prepareLaserTimer>0 && this.getTarget()!=null){
            this.prepareLaserTimer--;
            if(this.prepareLaserTimer==0){
                this.setLaser(true);
                if(!this.level().isClientSide){
                    PacketHandler.sendToAllTracking(new PacketActionRay(this.getId(), (int) this.getTarget().getX(), (int) this.getTarget().getY(), (int) this.getTarget().getZ()),this);
                }else {
                    this.prepare_laser.stop();
                    this.laser.start(this.tickCount);
                }
            }
        }


        if(this.isLaser()){
            this.laserTimer--;
            this.setLaser(false);
            if(this.laserPosition!=null){
                this.setPos(this.position());

                Vec3 vec32 = this.laserPosition.subtract(this.getEyePosition());
                double f5 = -Math.toDegrees(Math.atan2(vec32.y,Math.sqrt(vec32.x*vec32.x + vec32.z*vec32.z)));
                double f6 = Math.toDegrees(Math.atan2(vec32.z, vec32.x)) - 90.0F;
                this.yHeadRot=(float)f6;
                this.setYHeadRot((float) f6);
                this.yBodyRot= (float) (f6);
                this.setYRot((float) f6);
                this.setXRot((float) f5);
                this.setRot(this.getYRot(),this.getXRot());
            }
        }
        if(this.isAttacking()){
            this.attackTimer--;
            if(this.attackTimer==0){
                this.pushEntities(this.level().getEntitiesOfClass(LivingEntity.class,this.getBoundingBox().inflate(20)));
                this.setIsAttacking(false);
            }
        }

        if(this.level().isClientSide){
            this.clientTick();
        }
    }

    public boolean isLaser(){
        return this.entityData.get(LASER);
    }
    public void setLaser(boolean value){
        this.laserTimer = value ? 200 : 0;
        this.entityData.set(LASER,value);
    }

    private void checkTick() {
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class,this.getBoundingBox().inflate(40.0D),e->!this.is(e));
        for (TowerPart<?> part : this.towers){
            Optional<LivingEntity> optional = list.stream().findAny();
            if(optional.isPresent() && this.tickCount%40==0 && !this.level().isClientSide){
                LivingEntity target = optional.get();
                Arrow arrow = new Arrow(this.level(),this);
                arrow.setPos(part.position());
                arrow.shoot(target.getX()-part.getX(),target.getY()-part.getY(),target.getZ()-part.getZ(),2.0F,1.0F);
                this.level().addFreshEntity(arrow);
            }
        }
    }

    private void tickPart(TowerPart<?> p_31116_, double p_31117_, double p_31118_, double p_31119_) {
        p_31116_.setPos(this.getX() + p_31117_, this.getY() + p_31118_, this.getZ() + p_31119_);
    }
    public void pushEntities(List<LivingEntity> list){
        for (LivingEntity livingEntity : list){
            if(!this.is(livingEntity) && livingEntity.hurt(this.damageSources().generic(),20.0F) ){
                double dx = livingEntity.getX() - this.getX();
                double dz = livingEntity.getZ() - this.getZ();
                double normalize = dx * dx + dz * dz;
                livingEntity.push(dx/normalize,1,dz/normalize);
            }
        }
    }
    protected void updateWalkAnimation(float p_268362_) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(p_268362_ * 6.0F, 1.0F);
        } else {
            this.idleAnimationTimeout=1;
            this.idle.stop();
            f = 0.0F;
        }

        this.walkAnimation.update(f, 0.2F);
    }


    public void recreateFromPacket(ClientboundAddEntityPacket p_218825_) {
        super.recreateFromPacket(p_218825_);
        if (true) return; // Forge: Fix MC-158205: Moved into setId()
        TowerPart<?>[] aenderdragonpart = this.towers;

        for(int i = 0; i < aenderdragonpart.length; ++i) {
            aenderdragonpart[i].setId(i + p_218825_.getId());
        }

    }

    @Override
    public @Nullable PartEntity<?>[] getParts() {
        return this.towers;
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    public void clientTick(){
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 80;
            this.idle.start(this.tickCount);
            this.stomp.stop();
        } else {
            --this.idleAnimationTimeout;
        }
    }
    private void setIsAttacking(boolean b) {
        this.entityData.set(ATTACKING,b);
        this.attackTimer = b ? 40 : 0;
    }

    private boolean isAttacking(){
        return this.entityData.get(ATTACKING);
    }
    private boolean towerOn(){
        return this.entityData.get(TOWER_ON);
    }
    private void setTowerOn(boolean b) {
        this.entityData.set(TOWER_ON,b);
    }
    private void setBladeOn(boolean b) {
        this.entityData.set(BLADE_ON,b);
    }
    private boolean bladeOn(){
        return this.entityData.get(BLADE_ON);
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING,false);
        this.entityData.define(BLADE_ON,false);
        this.entityData.define(TOWER_ON,false);

    }

    @Override
    public void handleEntityEvent(byte p_21375_) {
        if(p_21375_==4){
            this.setIsAttacking(true);
            this.idle.stop();
            this.idleAnimationTimeout=40;
            this.stomp.start(this.tickCount);
        }else if(p_21375_==8){
            this.setTowerOn(false);
            this.tower_off.start(this.tickCount);
        }else if(p_21375_==12){
            this.setTowerOn(true);
            this.tower_on.start(this.tickCount);
        }else if(p_21375_==9){
            this.setBladeOn(false);
            this.blade_off.start(this.tickCount);
        }else if(p_21375_==11){
            this.setBladeOn(true);
            this.blade_on.start(this.tickCount);
        }else if(p_21375_==13){
            this.setLaser(true);
            this.prepare_laser.start(this.tickCount);
            this.prepareLaserTimer = 58;
        }
        super.handleEntityEvent(p_21375_);
    }

    class RayAttackGoal extends MeleeAttackGoal {

        public RayAttackGoal(PathfinderMob p_25552_, double p_25553_, boolean p_25554_) {
            super(p_25552_, p_25553_, p_25554_);
        }

        @Override
        public void tick() {
            super.tick();
            if(!MetalGearRayEntity.this.isAggressive()){
                MetalGearRayEntity.this.setTowerOn(false);
                MetalGearRayEntity.this.level().broadcastEntityEvent(MetalGearRayEntity.this,(byte) 8);
            }else {
                MetalGearRayEntity.this.setTowerOn(true);
                MetalGearRayEntity.this.level().broadcastEntityEvent(MetalGearRayEntity.this,(byte) 12);
            }
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity p_25557_, double p_25558_) {
            double d0 = this.getAttackReachSqr(p_25557_) + 10.0D;
            if (p_25558_ <= d0 && this.getTicksUntilNextAttack()<=0 && MetalGearRayEntity.this.attackTimer<=0) {
                this.resetAttackCooldown();
            }
        }


        @Override
        protected void resetAttackCooldown() {
            super.resetAttackCooldown();
            MetalGearRayEntity.this.setIsAttacking(true);
            if(!MetalGearRayEntity.this.level().isClientSide){
                MetalGearRayEntity.this.level().broadcastEntityEvent(MetalGearRayEntity.this,(byte) 4);
            }
        }
    }


}
