package com.TBK.WitchArchive.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class HarpyEntity extends TamableAnimal implements FlyingAnimal {
    public final AnimationState attackMelee=new AnimationState();
    public final AnimationState attackRange=new AnimationState();
    public final AnimationState sitting=new AnimationState();
    public final AnimationState idle=new AnimationState();

    protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(HarpyEntity.class,
            EntityDataSerializers.BYTE);
    private int idleAnimationTimeout;

    public HarpyEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.moveControl=new HarpyFlyingMoveControl(this,5,false);
        this.xpReward = 3;
    }

    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    public static AttributeSupplier setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 9.0D)
                .add(Attributes.FOLLOW_RANGE, 45.D)
                .add(Attributes.MOVEMENT_SPEED, 0.2d)
                .add(Attributes.FLYING_SPEED,0.5D)
                .add(Attributes.ATTACK_DAMAGE,5.0D)
                .build();

    }

    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide){
            this.setupAnimationStates();
        }
    }

    protected PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, pLevel);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(4, new HarpyEntityChargeAttackGoal());
        this.goalSelector.addGoal(3,new HarpyWanderGoal(this,1.0D));
        this.goalSelector.addGoal(2,new HarpyFollowOwnerGoal(this,3.5d,15.0F,3.0F,true));
        this.goalSelector.addGoal(8, new HarpyEntityRandomMoveGoal());
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(2, new HarpyEntityCopyOwnerTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Husk.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }
    protected void updateWalkAnimation(float p_268362_) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(p_268362_ * 6.0F, 1.0F);
        } else {
            f = 0.0F;
        }

        this.walkAnimation.update(f, 0.2F);
    }
    public void chargeState(){
        if(!this.isSitting() && !this.isPatrolling()){
            this.setSitting(true);
        }else this.setPatrolling(this.isSitting());
    }

    @Override
    public InteractionResult mobInteract(Player p_27584_, InteractionHand p_27585_) {
        ItemStack stack=p_27584_.getItemInHand(p_27585_);
        if(stack.isEmpty()){
            this.chargeState();
        }
        return super.mobInteract(p_27584_, p_27585_);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    private boolean getSoulEaterEntityFlag(int pMask) {
        int i = this.entityData.get(DATA_FLAGS_ID);
        return (i & pMask) != 0;
    }

    private void setSoulEaterEntityFlag(int pMask, boolean pValue) {
        int i = this.entityData.get(DATA_FLAGS_ID);
        if (pValue) {
            i |= pMask;
        } else {
            i &= ~pMask;
        }

        this.entityData.set(DATA_FLAGS_ID, (byte)(i & 255));
    }


    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 24;
            this.idle.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        if (this.isSitting()) {
            this.sitting.startIfStopped(this.tickCount);
        } else {
            this.sitting.stop();
        }

    }
    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> p_146754_) {
        super.onSyncedDataUpdated(p_146754_);

    }

    @Override
    public void handleEntityEvent(byte p_21807_) {
        if(p_21807_==4){
            this.attackMelee.start(this.tickCount);
        }else if(p_21807_==62){
            this.attackRange.start(this.tickCount);
        }else {
            super.handleEntityEvent(p_21807_);
        }
    }

    public boolean isCharging() {
        return this.getSoulEaterEntityFlag(1);
    }
    public boolean isSitting(){
        return this.getSoulEaterEntityFlag(2);
    }

    public void setIsCharging(boolean pCharging) {
        this.setSoulEaterEntityFlag(1, pCharging);
    }
    public void setSitting(boolean pIsSitting){
        this.setSoulEaterEntityFlag(2,pIsSitting);
    }
    public boolean isPatrolling(){
        return this.getSoulEaterEntityFlag(4);
    }

    public void setPatrolling(boolean patrolling){
        this.setSoulEaterEntityFlag(4,patrolling);
        if(patrolling){
            this.setSitting(false);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS_ID, (byte)0);
    }

    @Override
    public boolean isFlying() {
        return this.onGround();
    }

    class HarpyEntityChargeAttackGoal extends Goal {
        private int attackCooldown=0;

        public HarpyEntityChargeAttackGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            LivingEntity livingentity = HarpyEntity.this.getTarget();
            if (livingentity != null && livingentity.isAlive() && !HarpyEntity.this.getMoveControl().hasWanted()) {
                return HarpyEntity.this.distanceToSqr(livingentity) > 4.0D;
            } else {
                return false;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return HarpyEntity.this.getMoveControl().hasWanted() && HarpyEntity.this.isCharging() && HarpyEntity.this.getTarget() != null && HarpyEntity.this.getTarget().isAlive();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.attackCooldown=0;
            LivingEntity livingentity = HarpyEntity.this.getTarget();
            if (livingentity != null) {
                Vec3 vec3 = livingentity.getEyePosition();
                HarpyEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0D);
            }

            HarpyEntity.this.setIsCharging(true);
            HarpyEntity.this.playSound(SoundEvents.VEX_CHARGE, 1.0F, 1.0F);
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            HarpyEntity.this.setIsCharging(false);
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            LivingEntity livingentity = HarpyEntity.this.getTarget();
            if (livingentity != null) {
                double d0 = HarpyEntity.this.distanceToSqr(livingentity);
                AttackMelee attackMelee =  d0 > 9.0D ? AttackMelee.RANGED : AttackMelee.MELEE;
                if (HarpyEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox()) && attackMelee == AttackMelee.MELEE) {
                    HarpyEntity.this.doHurtTarget(livingentity);
                    if(!HarpyEntity.this.level().isClientSide){
                        HarpyEntity.this.level().broadcastEntityEvent(HarpyEntity.this,(byte) 4);
                    }
                    HarpyEntity.this.setIsCharging(false);
                } else {
                    if (d0 < 9.0D) {
                        Vec3 vec3 = livingentity.getEyePosition();
                        HarpyEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0D);
                    }else{
                        HarpyEntity.this.getNavigation().stop();
                    }
                }
                if (attackMelee == AttackMelee.RANGED && this.attackCooldown==0){
                    this.attackCooldown = this.adjustedTickDelay(20);
                    FeatherProjectile abstractarrow =new FeatherProjectile(HarpyEntity.this.level(),HarpyEntity.this);
                    double d4 = livingentity.getX() - HarpyEntity.this.getX();
                    double d1 = livingentity.getY(0.3333333333333333D) - abstractarrow.getY();
                    double d2 = livingentity.getZ() - HarpyEntity.this.getZ();
                    double d3 = Math.sqrt(d4 * d4 + d2 * d2);
                    abstractarrow.shoot(d4, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - HarpyEntity.this.level().getDifficulty().getId() * 4));
                    HarpyEntity.this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (HarpyEntity.this.getRandom().nextFloat() * 0.4F + 0.8F));
                    HarpyEntity.this.level().addFreshEntity(abstractarrow);
                }
                this.attackCooldown=Math.max(this.attackCooldown - 1, 0);
            }
        }

        enum AttackMelee{
            MELEE,
            RANGED;
        }
    }



    class HarpyEntityCopyOwnerTargetGoal extends TargetGoal {
        private final TargetingConditions copyOwnerTargeting = TargetingConditions.forNonCombat().ignoreLineOfSight().ignoreInvisibilityTesting();

        public HarpyEntityCopyOwnerTargetGoal(PathfinderMob p_34056_) {
            super(p_34056_, false);
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return ((Mob)HarpyEntity.this.getOwner()) != null && ((Mob)HarpyEntity.this.getOwner()).getTarget() != null && this.canAttack(((Mob)HarpyEntity.this.getOwner()).getTarget(), this.copyOwnerTargeting);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            HarpyEntity.this.setTarget(((Mob)HarpyEntity.this.getOwner()).getTarget());
            super.start();
        }
    }

    class HarpyEntityMoveControl extends MoveControl {
        public HarpyEntityMoveControl(HarpyEntity p_34062_) {
            super(p_34062_);
        }

        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                Vec3 vec3 = new Vec3(this.wantedX - HarpyEntity.this.getX(), this.wantedY - HarpyEntity.this.getY(), this.wantedZ - HarpyEntity.this.getZ());
                double d0 = vec3.length();
                if (d0 < HarpyEntity.this.getBoundingBox().getSize()) {
                    this.operation = MoveControl.Operation.WAIT;
                    HarpyEntity.this.setDeltaMovement(HarpyEntity.this.getDeltaMovement().scale(0.5D));
                } else {
                    HarpyEntity.this.setDeltaMovement(HarpyEntity.this.getDeltaMovement().add(vec3.scale(this.speedModifier * 0.05D / d0)));
                    if (HarpyEntity.this.getTarget() == null) {
                        Vec3 vec31 = HarpyEntity.this.getDeltaMovement();
                        HarpyEntity.this.setYRot(-((float) Mth.atan2(vec31.x, vec31.z)) * (180F / (float)Math.PI));
                        HarpyEntity.this.yBodyRot = HarpyEntity.this.getYRot();
                    } else {
                        double d2 = HarpyEntity.this.getTarget().getX() - HarpyEntity.this.getX();
                        double d1 = HarpyEntity.this.getTarget().getZ() - HarpyEntity.this.getZ();
                        HarpyEntity.this.setYRot(-((float)Mth.atan2(d2, d1)) * (180F / (float)Math.PI));
                        HarpyEntity.this.yBodyRot = HarpyEntity.this.getYRot();
                    }
                }

            }else {
                HarpyEntity.this.getNavigation().stop();
            }
        }
    }

    class HarpyEntityRandomMoveGoal extends Goal {
        public HarpyEntityRandomMoveGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return !HarpyEntity.this.getMoveControl().hasWanted() && HarpyEntity.this.random.nextInt(reducedTickDelay(7)) == 0;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return false;
        }


    }

    static class HarpyFollowOwnerGoal extends Goal{
        private final TamableAnimal tamable;
        private LivingEntity owner;
        private final LevelReader level;
        private final double speedModifier;
        private final PathNavigation navigation;
        private int timeToRecalcPath;
        private final float stopDistance;
        private final float startDistance;
        private float oldWaterCost;
        private final boolean canFly;

        public HarpyFollowOwnerGoal(TamableAnimal pTamable, double pSpeedModifier, float pStartDistance, float pStopDistance, boolean pCanFly) {
            this.tamable = pTamable;
            this.level = pTamable.level();
            this.speedModifier = pSpeedModifier;
            this.navigation = pTamable.getNavigation();
            this.startDistance = pStartDistance;
            this.stopDistance = pStopDistance;
            this.canFly = pCanFly;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
            if (!(pTamable.getNavigation() instanceof GroundPathNavigation) && !(pTamable.getNavigation() instanceof FlyingPathNavigation)) {
                throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
            }
        }

        public boolean canUse() {
            LivingEntity livingentity =this.tamable.getOwner();
            if (livingentity == null) {
                return false;
            } else if (livingentity.isSpectator()) {
                return false;
            } else if (this.tamable.isOrderedToSit()) {
                return false;
            } else if (this.tamable.distanceToSqr(livingentity) < (double)(this.startDistance * this.startDistance)) {
                return false;
            }else {
                this.owner = livingentity;
                return true;
            }
        }

        public boolean canContinueToUse() {
            if (this.navigation.isDone()) {
                return false;
            } else if (this.tamable.isOrderedToSit()) {
                return false;
            } else {
                return !(this.tamable.distanceToSqr(this.owner) <= (double)(this.stopDistance * this.stopDistance));
            }
        }

        public void start() {
            this.timeToRecalcPath = 0;
            this.oldWaterCost = this.tamable.getPathfindingMalus(BlockPathTypes.WATER);
            this.tamable.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        }

        public void stop() {
            this.owner = null;
            this.navigation.stop();
            this.tamable.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
        }

        public void tick() {
            this.tamable.getLookControl().setLookAt(this.owner, 10.0F, (float)this.tamable.getMaxHeadXRot());
            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = this.adjustedTickDelay(10);
                if (!this.tamable.isLeashed() && !this.tamable.isPassenger()) {
                    double dx=this.tamable.isTame() ? 900.0D : 144.0D;
                    if (this.tamable.distanceToSqr(this.owner) >= dx) {
                        this.teleportToOwner();
                    } else {
                        this.navigation.moveTo(this.owner.getX(),this.owner.getY()+5.0d,this.owner.getZ(),this.speedModifier);
                    }

                }
            }
        }

        private void teleportToOwner() {
            BlockPos blockpos = this.owner.blockPosition();
            for(int i = 0; i < 10; ++i) {
                int j = this.randomIntInclusive(-3, 3);
                int k = this.randomIntInclusive(-1, 1);
                int l = this.randomIntInclusive(-3, 3);
                int b = 0;
                boolean flag = this.maybeTeleportTo(blockpos.getX() + j, blockpos.getY() + k + b, blockpos.getZ() + l);
                if (flag) {
                    return;
                }
            }

        }

        private boolean maybeTeleportTo(int pX, int pY, int pZ) {
            if (Math.abs((double)pX - this.owner.getX()) < 2.0D && Math.abs((double)pZ - this.owner.getZ()) < 2.0D) {
                return false;
            } else if (!this.canTeleportTo(new BlockPos(pX, pY, pZ))) {
                return false;
            } else {
                this.tamable.moveTo((double)pX + 0.5D, (double)pY, (double)pZ + 0.5D, this.tamable.getYRot(), this.tamable.getXRot());
                this.navigation.stop();
                return true;
            }
        }

        private boolean canTeleportTo(BlockPos pPos) {
            BlockPathTypes blockpathtypes = WalkNodeEvaluator.getBlockPathTypeStatic(this.level, pPos.mutable());
            if(this.level.getBlockState(pPos).isAir()){
                return this.level.noCollision(this.tamable,this.tamable.getBoundingBox().move(pPos));
            }
            if (blockpathtypes != BlockPathTypes.WALKABLE) {
                return false;
            } else {
                BlockState blockstate = this.level.getBlockState(pPos.below());
                if (!this.canFly && blockstate.getBlock() instanceof LeavesBlock) {
                    return false;
                } else {
                    BlockPos blockpos = pPos.subtract(this.tamable.blockPosition());
                    return this.level.noCollision(this.tamable, this.tamable.getBoundingBox().move(blockpos));
                }
            }
        }

        private int randomIntInclusive(int pMin, int pMax) {
            return this.tamable.getRandom().nextInt(pMax - pMin + 1) + pMin;
        }
    }
    class HarpyFlyingMoveControl extends MoveControl{
        private final int maxTurn;
        private final boolean hoversInPlace;
        private final int maxTimerOnAir;
        private int timerOnAir=0;

        public HarpyFlyingMoveControl(Mob pMob, int pMaxTurn, boolean pHoversInPlace) {
            super(pMob);
            this.maxTurn = pMaxTurn;
            this.hoversInPlace = pHoversInPlace;
            this.maxTimerOnAir =300;
        }

        public void tick() {
            if(this.mob.getTarget()==null){
                if (this.operation == MoveControl.Operation.MOVE_TO) {
                    this.operation = MoveControl.Operation.WAIT;
                    this.mob.setNoGravity(true);
                    double d0 = this.wantedX - this.mob.getX();
                    double d1 = this.wantedY - this.mob.getY();
                    double d2 = this.wantedZ - this.mob.getZ();
                    double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                    if (d3 < (double)2.5000003E-7F) {
                        this.mob.setYya(0.0F);
                        this.mob.setZza(0.0F);
                        return;
                    }

                    float f = (float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                    this.mob.setYRot(this.rotlerp(this.mob.getYRot(), f, 90.0F));
                    float f1;
                    if (this.mob.onGround()) {
                        f1 = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    } else {
                        f1 = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.FLYING_SPEED));
                    }

                    this.mob.setSpeed(f1);
                    double d4 = Math.sqrt(d0 * d0 + d2 * d2);
                    if (Math.abs(d1) > (double)1.0E-5F || Math.abs(d4) > (double)1.0E-5F) {
                        float f2 = (float)(-(Mth.atan2(d1, d4) * (double)(180F / (float)Math.PI)));
                        this.mob.setXRot(this.rotlerp(this.mob.getXRot(), f2, (float)this.maxTurn));
                        this.mob.setYya(d1 > 0.0D ? f1 : -f1);
                    }
                    this.timerOnAir=0;
                } else {
                    if(this.timerOnAir>this.maxTimerOnAir){
                        this.mob.setNoGravity(false);
                    }
                    this.mob.setYya(0.0F);
                    this.mob.setZza(0.0F);
                    this.timerOnAir++;
                }
            }else {
                if (this.operation == MoveControl.Operation.MOVE_TO) {
                    this.mob.setNoGravity(true);
                    Vec3 vec3 = new Vec3(this.wantedX - HarpyEntity.this.getX(), this.wantedY - HarpyEntity.this.getY(), this.wantedZ - HarpyEntity.this.getZ());
                    double d0 = vec3.length();
                    if (d0 < HarpyEntity.this.getBoundingBox().getSize()) {
                        this.operation = MoveControl.Operation.WAIT;
                        HarpyEntity.this.setDeltaMovement(HarpyEntity.this.getDeltaMovement().scale(0.5D));
                    } else {
                        HarpyEntity.this.setDeltaMovement(HarpyEntity.this.getDeltaMovement().add(vec3.scale(this.speedModifier * 0.05D / d0)));
                        if (HarpyEntity.this.getTarget() == null) {
                            Vec3 vec31 = HarpyEntity.this.getDeltaMovement();
                            HarpyEntity.this.setYRot(-((float) Mth.atan2(vec31.x, vec31.z)) * (180F / (float)Math.PI));
                            HarpyEntity.this.yBodyRot = HarpyEntity.this.getYRot();
                        } else {
                            double d2 = HarpyEntity.this.getTarget().getX() - HarpyEntity.this.getX();
                            double d1 = HarpyEntity.this.getTarget().getZ() - HarpyEntity.this.getZ();
                            HarpyEntity.this.setYRot(-((float)Mth.atan2(d2, d1)) * (180F / (float)Math.PI));
                            HarpyEntity.this.yBodyRot = HarpyEntity.this.getYRot();
                        }
                    }

                }else {
                    HarpyEntity.this.getNavigation().stop();
                }
            }

        }

    }
    static class HarpyWanderGoal extends WaterAvoidingRandomFlyingGoal {
        public HarpyWanderGoal(PathfinderMob p_186224_, double p_186225_) {
            super(p_186224_, p_186225_);
        }

        @Override
        public boolean canUse() {
            return super.canUse() && (this.mob instanceof HarpyEntity scrounger && !scrounger.isSitting());
        }

        @javax.annotation.Nullable
        protected Vec3 getPosition() {
            Vec3 vec3 = null;
            if (this.mob.isInWater()) {
                vec3 = LandRandomPos.getPos(this.mob, 15, 15);
            }

            if (this.mob.getRandom().nextFloat() >= this.probability) {
                vec3 = this.getTreePos();
            }

            return vec3 == null ? super.getPosition() : vec3;
        }

        @javax.annotation.Nullable
        private Vec3 getTreePos() {
            BlockPos blockpos = this.mob.blockPosition();
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
            BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();

            for(BlockPos blockpos1 : BlockPos.betweenClosed(Mth.floor(this.mob.getX() - 3.0D), Mth.floor(this.mob.getY() - 6.0D), Mth.floor(this.mob.getZ() - 3.0D), Mth.floor(this.mob.getX() + 3.0D), Mth.floor(this.mob.getY() + 6.0D), Mth.floor(this.mob.getZ() + 3.0D))) {
                if (!blockpos.equals(blockpos1)) {
                    BlockState blockstate = this.mob.level().getBlockState(blockpos$mutableblockpos1.setWithOffset(blockpos1, Direction.DOWN));
                    boolean flag = blockstate.getBlock() instanceof LeavesBlock || blockstate.is(BlockTags.LOGS);
                    if (flag && this.mob.level().isEmptyBlock(blockpos1) && this.mob.level().isEmptyBlock(blockpos$mutableblockpos.setWithOffset(blockpos1, Direction.UP))) {
                        return Vec3.atBottomCenterOf(blockpos1);
                    }
                }
            }

            return null;
        }
    }
}
