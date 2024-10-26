package com.TBK.WitchArchive.common.entity;

import com.TBK.WitchArchive.common.register.CVNEntityType;
import com.TBK.WitchArchive.common.register.CVNItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class HarpyEntity extends TamableAnimal implements FlyingAnimal {
    public final AnimationState attackMelee=new AnimationState();
    public final AnimationState attackRange=new AnimationState();
    public final AnimationState sitting=new AnimationState();
    public final AnimationState idle=new AnimationState();

    private static final EntityDataAccessor<Integer> DATA_COLOR =
            SynchedEntityData.defineId(HarpyEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_SKIN =
            SynchedEntityData.defineId(HarpyEntity.class, EntityDataSerializers.INT);

    protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(HarpyEntity.class,
            EntityDataSerializers.BYTE);
    private int idleAnimationTimeout;

    public HarpyEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.moveControl = new FlyingMoveControl(this, 10, true);
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
    public boolean isFood(ItemStack p_27600_) {
        return p_27600_.is(CVNItems.GOLDEN_WHEAT_SEEDS.get());
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
        this.goalSelector.addGoal(2, new HarpyAttack(this,0.25, 8.0, 20, 4, 10));
        this.goalSelector.addGoal(1, new HarpyFlyGoal(this, 0.5, 5, 8));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 2.0D, 5.0F, 1.0F, true){
            @Override
            public boolean canUse() {
                return super.canUse() && HarpyEntity.this.isFollowing();
            }
        });
        this.targetSelector.addGoal(2, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, Husk.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Player.class, true));
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

    @Override
    public boolean isOrderedToSit() {
        return this.isSitting();
    }

    public void chargeState(){
        if(this.isPatrolling()){
            this.setSitting(true);
            this.setPatrolling(false);
        }else if(this.isSitting()){
            this.setIsFollowing(true);
            this.setSitting(false);
        }else {
            this.setPatrolling(true);
            this.setIsFollowing(false);
        }
    }

    public DyeColor getColor() {
        return DyeColor.byId(this.entityData.get(DATA_COLOR));
    }

    public void setColor(DyeColor pcolor) {
        this.entityData.set(DATA_COLOR, pcolor.getId());
    }




    @Override
    public InteractionResult mobInteract(Player p_27584_, InteractionHand p_27585_) {
        ItemStack stack=p_27584_.getItemInHand(p_27585_);
        if(stack.is(Tags.Items.SEEDS) && !this.isTame()){
            this.tame(p_27584_);
            if(!this.level().isClientSide){
                this.level().broadcastEntityEvent(this,(byte) 7);
            }
        }else if(stack.getItem() instanceof DyeItem item){
            this.setColor(item.getDyeColor());
        }else if(stack.isEmpty() && this.isTame()){
            this.chargeState();
            if(p_27584_.level().isClientSide){
                p_27584_.displayClientMessage(Component.translatable("harpy."+this.getIdState()),true);
            }
        }
        return super.mobInteract(p_27584_, p_27585_);
    }
    public int getIdState(){
        if(this.isSitting()){
            return 2;
        }else if(this.isPatrolling()){
            return 1;
        }else {
            return 0;
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        HarpyEntity harpy=new HarpyEntity(CVNEntityType.HARPY.get(),this.level());
        if(this.isTame()){
            harpy.tame((Player) this.getOwner());
        }
        return harpy;
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
        if (this.idleAnimationTimeout <= 0 && !this.isSitting()) {
            this.idleAnimationTimeout = 24;
            this.idle.start(this.tickCount);
            this.attackMelee.stop();
            this.attackRange.stop();
        } else {
            --this.idleAnimationTimeout;
        }

        if (this.isSitting()) {
            this.sitting.startIfStopped(this.tickCount);
            this.idle.stop();
        } else {
            this.sitting.stop();
        }

    }
    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> p_146754_) {
        super.onSyncedDataUpdated(p_146754_);
        if(p_146754_.equals(DATA_FLAGS_ID) && this.isSitting()){
            this.sitting.start(this.tickCount);
        }
    }

    @Override
    public void handleEntityEvent(byte p_21807_) {
        if(p_21807_==4){
            this.attackMelee.start(this.tickCount);
            this.idle.stop();
            this.idleAnimationTimeout=20;
        }else if(p_21807_==62){
            this.attackRange.start(this.tickCount);
            this.idle.stop();
            this.idleAnimationTimeout=24;

        }else {
            super.handleEntityEvent(p_21807_);
        }
    }

    public boolean isSitting(){
        return this.getSoulEaterEntityFlag(2);
    }

    public void setSitting(boolean pIsSitting){
        this.setSoulEaterEntityFlag(2,pIsSitting);
        if(pIsSitting){
            double y=this.level().getHeight(Heightmap.Types.MOTION_BLOCKING, (int) this.getX(), (int) this.getZ());
            this.setPos(this.getX(),y,this.getZ());
        }
    }
    public boolean isPatrolling(){
        return this.getSoulEaterEntityFlag(4);
    }


    public void setPatrolling(boolean patrolling){
        this.setSoulEaterEntityFlag(4,patrolling);
    }

    public int getIdSkin() {
        return this.entityData.get(DATA_SKIN);
    }

    public Skin getSkin() {
        return Skin.byId(this.getIdSkin() & 255);
    }

    public void setIdSkin(int pId) {
        this.entityData.set(DATA_SKIN, pId);
    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS_ID, (byte)0);
        this.entityData.define(DATA_COLOR,-1);
        this.entityData.define(DATA_SKIN,-1);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag p_21819_) {
        super.addAdditionalSaveData(p_21819_);
        p_21819_.putBoolean("isFollowing",this.isFollowing());
        p_21819_.putBoolean("isPatrolling",this.isPatrolling());
        p_21819_.putBoolean("isSitting",this.isSitting());
        p_21819_.putInt("color",this.getColor().getId());
        p_21819_.putInt("skin",this.getIdSkin());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_21815_) {
        super.readAdditionalSaveData(p_21815_);
        this.setIsFollowing(p_21815_.getBoolean("isFollowing"));
        this.setSitting(p_21815_.getBoolean("isSitting"));
        this.setPatrolling(p_21815_.getBoolean("isPatrolling"));
        this.setColor(DyeColor.byId(p_21815_.getInt("color")));
        this.setIdSkin(p_21815_.getInt("skin"));
    }

    public boolean isFollowing(){
        return this.getSoulEaterEntityFlag(1);
    }

    public void setIsFollowing(boolean isFollowing){
        this.setSoulEaterEntityFlag(1, isFollowing);
    }

    @Override
    public boolean isFlying() {
        return true;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_, MobSpawnType p_146748_, @Nullable SpawnGroupData p_146749_, @Nullable CompoundTag p_146750_) {
        DyeColor color=DyeColor.values()[this.level().random.nextInt(0,15)];
        this.setColor(color!=null ? color : DyeColor.BLUE);
        this.setIdSkin(this.random.nextInt(0,3));
        return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_, p_146750_);
    }

    static class HarpyAttack extends Goal{
        private final HarpyEntity harpy;
        private final double speed;
        private final double circleRadius;
        private final Level world;
        private double circlingAngle;
        private final int minAltitude;
        private final int maxAltitude;
        private Vec3 circlingPosition;
        private int attackCooldown=0;
        private int countFeather=0;
        public boolean meleeAttack=false;
        public boolean rot=false;

        public HarpyAttack(HarpyEntity harpy, double speed, double circleRadius, int attackInterval, int minAltitude, int maxAltitude) {
            this.harpy = harpy;
            this.speed = speed;
            this.circleRadius = circleRadius;
            this.minAltitude = minAltitude;
            this.maxAltitude = maxAltitude;
            this.world = harpy.level();
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
            this.circlingAngle = 0.0;
        }

        public boolean canUse() {
            LivingEntity potentialTarget = this.harpy.getTarget();
            return potentialTarget != null && potentialTarget.isAlive();
        }

        public void start() {
            this.circlingPosition = null;
            this.circlingAngle = 0.0;
            this.attackCooldown = 0;
            this.resetAmount();
        }

        public void resetAmount(){
            this.rot=this.world.random.nextBoolean();
            this.countFeather = 5 + world.random.nextInt(1,5);
        }

        public boolean canContinueToUse() {
            LivingEntity livingEntity = this.harpy.getTarget();
            if (livingEntity instanceof Player playerEntity) {
                if (playerEntity.isCreative() || playerEntity.isInvulnerable()) {
                    return false;
                }
            }

            return livingEntity != null && livingEntity.isAlive();
        }

        public void stop() {
            this.circlingPosition = null;
        }

        public void tick() {
            LivingEntity target = this.harpy.getTarget();
            if (target != null) {
                double distanceToTarget = this.harpy.distanceToSqr(target.getX(), target.getY(), target.getZ());
                this.circlingAngle += this.rot ? 0.05F : -0.05F;

                Vec3 direction=null;
                if(!this.meleeAttack){
                    double offsetX = Math.cos(this.circlingAngle) * this.circleRadius;
                    double offsetZ = Math.sin(this.circlingAngle) * this.circleRadius;
                    double heightOffset = this.calculateHeightOffset(target);
                    this.circlingPosition = new Vec3(target.getX() + offsetX, target.getY() + heightOffset, target.getZ() + offsetZ);
                    direction = this.circlingPosition.subtract(this.harpy.position()).normalize();
                    this.harpy.setDeltaMovement(direction.scale(this.speed));
                }
                this.rotateTowardsTarget(target);
                if (this.attackCooldown>=20 && !this.meleeAttack && direction!=null && direction.length()<1.0F) {
                    FeatherProjectile abstractarrow = new FeatherProjectile(this.world,this.harpy);
                    double d0 = target.getX() - this.harpy.getX();
                    double d1 = target.getY(0.3333333333333333D) - abstractarrow.getY();
                    double d2 = target.getZ() - this.harpy.getZ();
                    double d3 = Math.sqrt(d0 * d0 + d2 * d2);
                    abstractarrow.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.harpy.level().getDifficulty().getId() * 4));
                    this.harpy.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.harpy.getRandom().nextFloat() * 0.4F + 0.8F));
                    this.world.addFreshEntity(abstractarrow);
                    if(!this.world.isClientSide){
                        this.world.broadcastEntityEvent(this.harpy,(byte) 62);
                    }
                    if(this.countFeather<=0){
                        this.meleeAttack=true;
                    }else {
                        this.attackCooldown=0;
                        this.countFeather--;
                    }
                } else {
                    if(this.meleeAttack){
                        this.harpy.setDeltaMovement(target.position().subtract(this.harpy.position()).normalize().scale(0.5F));
                        if(distanceToTarget<9.0F){
                            this.attackCooldown=0;
                            if(!this.world.isClientSide){
                                this.world.broadcastEntityEvent(this.harpy,(byte) 4);
                            }
                            this.harpy.doHurtTarget(target);
                            this.meleeAttack=false;
                            this.resetAmount();
                        }
                    }
                }
                this.attackCooldown=Math.min(this.attackCooldown+1,20);

            }

        }
        public boolean requiresUpdateEveryTick() {
            return true;
        }
        

        private void rotateTowardsTarget(LivingEntity target) {
            Vec3 targetPos = target.position();
            Vec3 harpyPos = this.harpy.position();
            double dx = targetPos.x - harpyPos.x;
            double dz = targetPos.z - harpyPos.z;
            double targetYaw = Math.toDegrees(Math.atan2(dz, dx)) - 90.0;
            this.harpy.setYRot(this.lerpRotation(this.harpy.getYRot(), (float)targetYaw, 30.0F));
        }

        private float lerpRotation(float currentYaw, float targetYaw, float maxTurnSpeed) {
            float deltaYaw = Mth.floor(targetYaw - currentYaw);
            return currentYaw + Mth.clamp(deltaYaw, -maxTurnSpeed, maxTurnSpeed);
        }



        private double calculateHeightOffset(LivingEntity target) {
            double currentAltitude = this.harpy.getY();
            double targetAltitude = target.getY();
            double targetHeight = targetAltitude + (double)this.minAltitude + Math.random() * (double)(this.maxAltitude - this.minAltitude);
            return targetHeight - currentAltitude;
        }
    }


    public static class HarpyFlyGoal extends Goal {
        private final HarpyEntity harpy;
        private final double speed;
        private final int minAltitude;
        private final int maxAltitude;
        private Vec3 targetPos;
        private int idleTime = 0;
        private boolean isIdle = false;

        public HarpyFlyGoal(HarpyEntity harpy, double speed, int minAltitude, int maxAltitude) {
            this.harpy = harpy;
            this.speed = speed;
            this.minAltitude = minAltitude;
            this.maxAltitude = maxAltitude;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            return !this.isIdle && !this.harpy.isSitting();
        }

        public void start() {
            if (!this.isIdle) {
                label31: {
                    if (this.targetPos != null) {
                        double d0 = this.harpy.position().distanceTo(this.targetPos);
                        if (!(d0 < 1.5)) {
                            break label31;
                        }
                    }

                    this.targetPos = this.getRandomAirPosition();
                    if (this.targetPos == null || (double)this.harpy.getDeltaMovement().length() < 0.4) {
                        this.enterIdleMode();
                        return;
                    }
                }

                if (this.targetPos != null) {
                    Vec3 direction = this.targetPos.subtract(this.harpy.position()).normalize().scale(this.speed);
                    this.harpy.setDeltaMovement(direction);
                    this.harpy.lookControl.setLookAt(this.targetPos.x, this.targetPos.y, this.targetPos.z);
                    this.rotateTowardsTarget();
                }

            }
        }


        public boolean canContinueToUse() {
            if (this.isIdle) {
                return this.idleTime > 0;
            } else {
                boolean var1;
                label36: {
                    if (!this.harpy.navigation.isDone()) {
                        if (this.targetPos == null) {
                            break label36;
                        }

                        double d0 = this.harpy.position().distanceTo(this.targetPos);
                        Objects.requireNonNull(this);
                        if (d0 >= 1.5) {
                            break label36;
                        }
                    }

                    var1 = false;
                    return var1;
                }

                var1 = true;
                return var1;
            }
        }

        public void tick() {
            super.tick();
            if (this.isIdle) {
                --this.idleTime;
                this.harpy.setDeltaMovement(Vec3.ZERO);
                this.harpy.lookControl.setLookAt(this.harpy.getX(), this.harpy.getY(), this.harpy.getZ());
                if (this.idleTime <= 0) {
                    this.isIdle = false;
                }
            } else {
                if (this.harpy.isFlying()) {
                    this.targetPos = this.getRandomAirPosition();
                }

                label21: {
                    if (this.targetPos != null) {
                        double d0 = this.harpy.position().distanceTo(this.targetPos);
                        if (!(d0 < 1.5)) {
                            break label21;
                        }
                    }

                    this.targetPos = this.getRandomAirPosition();
                }

                if (this.targetPos != null) {
                    Vec3 direction = this.targetPos.subtract(this.harpy.position()).normalize().scale(this.speed);
                    this.harpy.setDeltaMovement(direction);
                    this.harpy.lookControl.setLookAt(this.targetPos.x, this.targetPos.y, this.targetPos.z);
                    this.rotateTowardsTarget();
                }
            }

        }

        private void enterIdleMode() {
            RandomSource random = this.harpy.random;
            this.idleTime = random.nextInt(41) + 20;
            this.isIdle = true;
            this.harpy.setDeltaMovement(Vec3.ZERO);
        }

        private Vec3 getRandomAirPosition() {
            RandomSource random = this.harpy.getRandom();
            Level world = this.harpy.level();
            BlockPos currentPos = this.harpy.blockPosition();
            int currentGroundHeight = world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, currentPos.getX(), currentPos.getZ());
            int altitudeRange = this.maxAltitude - this.minAltitude;

            for(int i = 0; i < 10; ++i) {
                double newY = (double)(currentGroundHeight + this.minAltitude) + random.nextInt() * (double)altitudeRange;
                double x = this.harpy.getX() + (random.nextInt() * 20.0 - 10.0);
                double z = this.harpy.getZ() + (random.nextInt() * 20.0 - 10.0);
                Vec3 targetPos = new Vec3(x, newY, z);
                BlockPos targetBlockPos = BlockPos.containing(targetPos);
                if (this.isValidFlyPosition(world, targetBlockPos)) {
                    return targetPos;
                }
            }

            return null;
        }

        private boolean isValidFlyPosition(Level world, BlockPos pos) {
            if (world.isEmptyBlock(pos) && world.isEmptyBlock(pos.above())) {
                BlockPos belowPos = pos.below();
                if (!(world.getBlockState(belowPos).getBlock() instanceof LeavesBlock)) {
                    Iterator<BlockPos> var4 = BlockPos.betweenClosedStream(pos.offset(-1, -1, -1), pos.offset(1, 1, 1)).iterator();

                    BlockPos adjacentPos;
                    do {
                        if (!var4.hasNext()) {
                            return true;
                        }

                        adjacentPos = (BlockPos) var4.next();
                    } while (world.getBlockState(adjacentPos).getBlock() instanceof LeavesBlock);

                }
            }
            return false;
        }

        private void rotateTowardsTarget() {
            Vec3 currentPosition = this.harpy.position();
            Vec3 directionToTarget = this.targetPos.subtract(currentPosition).normalize();
            double yaw = Math.toDegrees(Math.atan2(directionToTarget.x, directionToTarget.z));
            double pitch = -Math.toDegrees(Math.atan2(directionToTarget.y, Math.sqrt(directionToTarget.x * directionToTarget.x + directionToTarget.z * directionToTarget.z)));
            this.harpy.setYRot((float)yaw);
            this.harpy.setXRot((float)pitch);
        }

    }

    public enum Skin{
        VARIANT_1(0),
        VARIANT_2(1),
        VARIANT_3(2);
        private static final Skin[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(Skin::getId)).toArray(Skin[]::new);

        private final int id;
        Skin(int id){
            this.id=id;
        }

        public int getId() {
            return this.id;
        }
        public static Skin byId(int p_30987_) {
            return BY_ID[p_30987_ % BY_ID.length];
        }
    }
}
