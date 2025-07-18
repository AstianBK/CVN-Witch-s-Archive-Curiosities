package com.TBK.WitchArchive.common.entity;

import com.TBK.WitchArchive.CVNWitchArchiveCuriosities;
import com.TBK.WitchArchive.common.network.PacketHandler;
import com.TBK.WitchArchive.common.network.messager.PacketActionRay;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class MetalGearRayEntity extends PathfinderMob implements PlayerRideableJumping, RiderShieldingMount  {
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
    private final TowerPart<?> head;
    private final TowerPart<?> body;
    private final TowerPart<?>[] towers;
    private final TowerPart<?>[] bodyParts;
    private final TowerPart<?>[] parts;
    public Vec3 laserPosition = Vec3.ZERO;
    public int prepareLaserTimer = 0;
    public int laserTimer = 0;
    public float rotHeadY = 0.0F;
    public float rotHeadX = 0.0F;
    public MetalGearRayEntity(EntityType<? extends PathfinderMob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
        this.tower0=new TowerPart<>(this,"tower0",1,1);
        this.tower1=new TowerPart<>(this,"tower1",1,1);
        this.tower2=new TowerPart<>(this,"tower2",1,1);
        this.tower3=new TowerPart<>(this,"tower3",1,1);
        this.head = new TowerPart<>(this,"head",3,3);
        this.body = new TowerPart<>(this,"body",10,10);

        this.towers = new TowerPart[]{this.tower0,this.tower1,this.tower2,this.tower3};
        this.bodyParts = new TowerPart[]{this.body,this.head};
        this.parts = new TowerPart[]{this.tower0,this.tower1,this.tower2,this.tower3,this.body,this.head};
        this.setId(ENTITY_COUNTER.getAndAdd(this.parts.length + 1) + 1);
    }
    @Override
    public void setId(int p_20235_) {
        super.setId(p_20235_);
        for (int i = 0; i < this.parts.length; i++) // Forge: Fix MC-158205: Set part ids to successors of parent mob id
            this.parts[i].setId(p_20235_ + i + 1);
    }

    public static AttributeSupplier setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 35.0D)
                .add(Attributes.FOLLOW_RANGE, 45.D)
                .add(Attributes.MOVEMENT_SPEED, 0.1d)
                .add(Attributes.ATTACK_DAMAGE,12.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE,1.0F)
                .build();

    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3,new RayAttackGoal(this,2.0D,false));
        this.targetSelector.addGoal(4,new NearestAttackableTargetGoal<>(this,LivingEntity.class,false));
    }

    public Vec3 getHeadPos(float partialTicks){
        double x = this.head.getX();
        double y = this.head.getY();
        double z = this.head.getZ();

        return new Vec3(x,y,z);
    }

    public Vec3 getBeamDirection(float partialTicks){
        Vec3 direction;
        if(this.isVehicle()){
            direction = this.getHeadPos(partialTicks).add(this.getControllingPassenger().getLookAngle().scale(50.0D)).subtract(this.getHeadPos(partialTicks));
        }else {
            direction = this.laserPosition.subtract(this.getHeadPos(partialTicks));
        }
        return direction;
    }

    @Override
    public void tick() {
        super.tick();

        if(this.level().getEntities(this,this.body.getBoundingBox().inflate(3.0F)).isEmpty()){
            this.setTowerOn(false);
            if(!this.level().isClientSide){
                this.level().broadcastEntityEvent(this,(byte) 9);
            }
        }else {
            this.setTowerOn(true);
            if(!this.level().isClientSide){
                this.level().broadcastEntityEvent(this,(byte) 12);
            }
        }
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

        float yawRad = (float)Math.toRadians(this.getYRot());
        float sin = (float)Math.sin(yawRad);
        float cos = (float)Math.cos(yawRad);

        double body0X =  (-7 * sin);
        double body0Z = -(-7 * cos);

        Vec3[] pos = new Vec3[this.bodyParts.length];
        for (int j = 0 ; j<this.bodyParts.length ; j++){
            pos[j]=new Vec3(this.bodyParts[j].getX(), this.bodyParts[j].getY(), this.bodyParts[j].getZ());
        }

        this.tickPart(this.head,body0X,7,body0Z);
        this.tickPart(this.body,0,0,0);

        for (int k = 0 ; k<this.bodyParts.length ; k++){
            this.bodyParts[k].xo = pos[k].x;
            this.bodyParts[k].yo = pos[k].y;
            this.bodyParts[k].zo = pos[k].z;
            this.bodyParts[k].xOld = pos[k].x;
            this.bodyParts[k].yOld = pos[k].y;
            this.bodyParts[k].zOld = pos[k].z;
        }

        if(this.towerOn()){
            Vec3[] avec3 = new Vec3[this.towers.length];

            for(int j = 0; j < this.towers.length; ++j) {
                avec3[j] = new Vec3(this.towers[j].getX(), this.towers[j].getY(), this.towers[j].getZ());
            }

            // Rotación enY ( horizontal)

            // torre 1: adelante derecha
            double leg0X =  ( 2 * cos) + ( -4 * sin);
            double leg0Z =  ( 2 * sin) - ( -4 * cos);

            // torre 2: atrás izquierda
            double leg1X =  ( -2 * cos) + ( -4 * sin);
            double leg1Z =  ( -2 * sin) - ( -4 * cos);

            // torre 3: atrás derecha
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
            if(!this.isVehicle()){
                this.checkTick();
            }
        }

        if(this.prepareLaserTimer>0){
            this.prepareLaserTimer--;
            this.getNavigation().stop();
            if (!this.level().isClientSide && this.getTarget()!=null){
                this.setPos(this.position());

                Vec3 vec32 = this.getTarget().position().subtract(this.getEyePosition());
                double f5 = -Math.toDegrees(Math.atan2(vec32.y,Math.sqrt(vec32.x*vec32.x + vec32.z*vec32.z)));
                double f6 = Math.toDegrees(Math.atan2(vec32.z, vec32.x)) - 90.0F;
                this.yHeadRot=(float)f6;
                this.setYHeadRot((float) f6);
                this.yBodyRot= (float) (f6);
                this.setYRot((float) f6);
                this.setXRot((float) f5);
                this.setRot(this.getYRot(),this.getXRot());
            }
            if(this.prepareLaserTimer==0){
                if(!this.level().isClientSide){
                    this.setLaser(true);
                    if(this.isVehicle()){
                        this.laserPosition=null;
                        this.level().broadcastEntityEvent(this,(byte) 14);
                    }else if(this.getTarget()!=null){
                        this.laserPosition = this.getTarget().position();
                        PacketHandler.sendToAllTracking(new PacketActionRay(this.getId(), (int) this.getTarget().getX(), (int) this.getTarget().getY(), (int) this.getTarget().getZ()),this);
                    }
                }else {
                    this.idleAnimationTimeout = 2000;
                    this.prepare_laser.stop();
                    this.laser.start(this.tickCount);
                }
            }
        }


        if(this.isLaser()){
            this.laserTimer--;
            this.getNavigation().stop();
            if(this.isVehicle()){
                Vec3 direction = this.getBeamDirection(1.0F);
                BlockHitResult result = this.level().clip(new ClipContext(this.getHeadPos(1.0F),direction, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,this));
                if(this.tickCount%10 == 0){
                    EntityHitResult hit = this.getBeamEntityHitResult(this.level(),this,this.getHeadPos(1.0F),this.getHeadPos(1.0F).add(this.getControllingPassenger().getLookAngle().scale(50.0D)),this.getBoundingBox().inflate(50.0F),e->!this.is(e),3.0F);
                    if(hit!=null && hit.getEntity() instanceof  LivingEntity){
                        LivingEntity entity = (LivingEntity) hit.getEntity();
                        if(entity.hurt(this.damageSources().magic(),3.0F)){
                            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,3,10));
                        }
                    }
                }
            }else {
                if(this.tickCount%10 == 0){
                    EntityHitResult hit = this.getBeamEntityHitResult(this.level(),this,this.getHeadPos(1.0F),this.laserPosition,this.getBoundingBox().inflate(50.0F),e->!this.is(e),3.0F);
                    if(hit!=null && hit.getEntity() instanceof  LivingEntity){
                        LivingEntity entity = (LivingEntity) hit.getEntity();
                        if(entity.hurt(this.damageSources().magic(),3.0F)){
                            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,3,10));
                        }
                    }
                }
                if(!this.level().isClientSide && (this.getTarget() == null || this.getTarget().isAlive())){
                    LivingEntity livingEntity = this.level().getEntitiesOfClass(LivingEntity.class,this.getBoundingBox().inflate(30.0D)).stream().findFirst().orElse(null);
                    if(livingEntity!=null){
                        this.setTarget(livingEntity);
                        this.laserPosition = livingEntity.position();
                        PacketHandler.sendToAllTracking(new PacketActionRay(this.getId(), (int) this.getTarget().getX(), (int) this.getTarget().getY(), (int) this.getTarget().getZ()),this);
                    }
                }
                if(!this.level().isClientSide && this.laserPosition!=null){
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
            if(this.laserTimer<=0){
                this.setLaser(false);
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

    public  EntityHitResult getBeamEntityHitResult(Level p_150176_, Entity p_150177_, Vec3 p_150178_, Vec3 p_150179_, AABB p_150180_, Predicate<Entity> p_150181_, float p_150182_) {
        double d0 = Double.MAX_VALUE;
        Entity entity = null;

        for(Entity entity1 : p_150176_.getEntities(p_150177_, p_150180_, p_150181_)) {
            AABB aabb = entity1.getBoundingBox().inflate((double)p_150182_);
            Optional<Vec3> optional = aabb.clip(p_150178_, p_150179_);
            if (optional.isPresent()) {
                double d1 = p_150178_.distanceToSqr(optional.get());
                if (d1 < d0) {
                    entity = entity1;
                    d0 = d1;
                }
            }
        }
        return entity == null ? null : new EntityHitResult(entity);
    }
    @Override
    public InteractionResult mobInteract(Player p_27584_, InteractionHand p_27585_) {
        this.doPlayerRide(p_27584_);
        return super.mobInteract(p_27584_, p_27585_);
    }

    @javax.annotation.Nullable
    public LivingEntity getControllingPassenger() {
        for(Entity entity:this.getPassengers()){
            return (LivingEntity) entity;
        }
        return null;
    }

    @javax.annotation.Nullable
    private Vec3 getDismountLocationInDirection(Vec3 p_30562_, LivingEntity p_30563_) {
        double d0 = this.getX() + p_30562_.x;
        double d1 = this.getBoundingBox().minY;
        double d2 = this.getZ() + p_30562_.z;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(Pose pose : p_30563_.getDismountPoses()) {
            blockpos$mutableblockpos.set(d0, d1, d2);
            double d3 = this.getBoundingBox().maxY + 0.75D;

            while(true) {
                double d4 = this.level().getBlockFloorHeight(blockpos$mutableblockpos);
                if ((double)blockpos$mutableblockpos.getY() + d4 > d3) {
                    break;
                }

                if (DismountHelper.isBlockFloorValid(d4)) {
                    AABB aabb = p_30563_.getLocalBoundsForPose(pose);
                    Vec3 vec3 = new Vec3(d0, (double)blockpos$mutableblockpos.getY() + d4, d2);
                    if (DismountHelper.canDismountTo(this.level(), p_30563_, aabb.move(vec3))) {
                        p_30563_.setPose(pose);
                        return vec3;
                    }
                }

                blockpos$mutableblockpos.move(Direction.UP);
                if (!((double)blockpos$mutableblockpos.getY() < d3)) {
                    break;
                }
            }
        }

        return null;
    }
    protected void doPlayerRide(Player pPlayer) {
        if (!this.level().isClientSide) {
            pPlayer.setYRot(this.getYRot());
            pPlayer.setXRot(this.getXRot());
            pPlayer.startRiding(this);
        }
    }
    public void travel(Vec3 pTravelVector) {
        LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();
        if (this.isAlive()) {
            if (this.isVehicle() && livingentity!=null && !this.isImmobile()) {
                if(!this.isLaser()){
                    this.setYRot(livingentity.getYRot());
                    this.yRotO = this.getYRot();
                    this.setXRot(livingentity.getXRot() * 0.5F);
                    this.setRot(this.getYRot(), this.getXRot());
                    this.yBodyRot = this.getYRot();
                    this.yHeadRot = this.yBodyRot;
                    float f = livingentity.xxa * 0.5F;
                    float f1 = livingentity.zza;
                    if (f1 <= 0.0F) {
                        f1 *= 0.25F;
                    }

                    if (this.isControlledByLocalInstance()) {
                        float f0 =  (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED)/3 ;
                        this.setSpeed(f0);
                        super.travel(new Vec3((double) f, pTravelVector.y, (double) f1));
                    } else if (livingentity instanceof Player) {
                        this.setDeltaMovement(Vec3.ZERO);
                    }
                }else {
                    this.rotHeadY = livingentity.getYRot() - this.getYRot();
                    //this.yRotO = this.getYRot();
                    this.rotHeadX = livingentity.getXRot() * 0.5F;
                    //this.setRot(this.getYRot(), this.getXRot());
                    //this.yBodyRot = this.getYRot();
                    //this.yHeadRot = this.yBodyRot;
                }
                this.calculateEntityAnimation( false);
                this.tryCheckInsideBlocks();
            } else {
                super.travel(pTravelVector);
            }
        }
    }

    @Override
    public void positionRider(Entity pPassenger, MoveFunction moveFunction) {
        super.positionRider(pPassenger, moveFunction);
        if( this.getControllingPassenger()==pPassenger){
            if (pPassenger instanceof Mob mob) {
                this.yBodyRot = mob.yBodyRot;
            }
            pPassenger.setPos(this.getHeadPos(1.0F));
        }
    }
    @Override
    public boolean is(Entity p_20356_) {
        for (int j=0 ; j<this.getParts().length ; j++){
            if(this.getParts()[j]==p_20356_){
                return true;
            }
        }
        return super.is(p_20356_);
    }

    public boolean isLaser(){
        return this.entityData.get(LASER);
    }

    public void setLaser(boolean value){
        this.laserTimer = value ? 2000 : 0;
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
        TowerPart<?>[] part = this.parts;


        for(int i = 0; i < part.length; ++i) {
            part[i].setId(i + p_218825_.getId());
        }

    }

    @Override
    public @Nullable PartEntity<?>[] getParts() {
        return this.parts;
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
        this.entityData.define(LASER,false);
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
            this.prepare_laser.start(this.tickCount);
            this.prepareLaserTimer = 58;
        }else if(p_21375_==14){
            this.setLaser(true);
        }
        super.handleEntityEvent(p_21375_);
    }

    @Override
    public void onPlayerJump(int p_21696_) {

    }

    @Override
    public boolean canJump() {
        return true;
    }

    @Override
    public void handleStartJump(int p_21695_) {
        if(!this.level().isClientSide){
            this.level().broadcastEntityEvent(this,(byte) 13);
            this.prepareLaserTimer = 58;
        }
    }

    @Override
    public void handleStopJump() {

    }

    @Override
    public double getRiderShieldingHeight() {
        return 0;
    }

    class RayAttackGoal extends MeleeAttackGoal {
        public StratAttack currentAttack = StratAttack.STOMP;
        public int nextTimerStrat = 0;
        public int maxTimerStrat = 0;
        public RayAttackGoal(PathfinderMob p_25552_, double p_25553_, boolean p_25554_) {
            super(p_25552_, p_25553_, p_25554_);
        }

        @Override
        public void start() {
            super.start();
            this.currentAttack = StratAttack.STOMP;
            this.maxTimerStrat= 200 + MetalGearRayEntity.this.level().random.nextInt(10)*MetalGearRayEntity.this.level().random.nextInt(5);
        }

        @Override
        public boolean canUse() {
            return super.canUse() && !MetalGearRayEntity.this.isLaser();
        }

        @Override
        public void tick() {
            StratAttack strat = this.currentAttack;
            if(strat == StratAttack.STOMP){
                super.tick();
            }else {
                MetalGearRayEntity.this.getNavigation().stop();
            }
            if(!MetalGearRayEntity.this.isAttacking() && this.nextTimerStrat++>this.maxTimerStrat){
                this.nextTimerStrat = 0;
                this.maxTimerStrat= 20 + MetalGearRayEntity.this.level().random.nextInt(3)*MetalGearRayEntity.this.level().random.nextInt(3);
                int nextStrat = MetalGearRayEntity.this.random.nextInt(0,1);
                this.switchStrat(nextStrat);
            }
        }

        public void switchStrat(int idStrat){
            switch (idStrat){
                case 0 ->{
                    this.currentAttack = StratAttack.LASER;
                    MetalGearRayEntity.this.prepareLaserTimer = 58;
                    MetalGearRayEntity.this.level().broadcastEntityEvent(MetalGearRayEntity.this,(byte) 13);
                }
                case 1 ->{
                    this.currentAttack = StratAttack.STOMP;
                }
            }
        }
        @Override
        public void stop() {
            super.stop();
            MetalGearRayEntity.this.level().broadcastEntityEvent(MetalGearRayEntity.this,(byte) 12);
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
        enum StratAttack{
            LASER,
            STOMP;
        }
    }


}
