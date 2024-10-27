package com.TBK.WitchArchive.common.entity;

import com.TBK.WitchArchive.common.register.CVNEntityType;
import com.TBK.WitchArchive.common.register.CVNItems;
import com.google.common.collect.Sets;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class FeatherProjectile extends AbstractArrow implements ItemSupplier {
    private final Set<MobEffectInstance> effects = Sets.newHashSet();

    public FeatherProjectile(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
    }
    public FeatherProjectile(Level pLevel, LivingEntity pShooter) {
        super(CVNEntityType.FEATHER_PROJECTILE.get(),pShooter,pLevel);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        if(pResult.getEntity() instanceof LivingEntity living && !(living instanceof HarpyEntity)){
            living.invulnerableTime = 0;
            living.hurt(damageSources().arrow(this,this.getOwner()),5.0F);
            living.invulnerableTime = 0;
        }
        this.discard();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (!this.effects.isEmpty()) {
            ListTag listtag = new ListTag();

            for(MobEffectInstance mobeffectinstance : this.effects) {
                listtag.add(mobeffectinstance.save(new CompoundTag()));
            }
            pCompound.put("CustomPotionEffects", listtag);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        for(MobEffectInstance mobeffectinstance : PotionUtils.getCustomEffects(pCompound)) {
            this.addEffect(mobeffectinstance);
        }
    }

    protected void doPostHurtEffects(LivingEntity pLiving) {
        super.doPostHurtEffects(pLiving);
        Entity entity = this.getEffectSource();
        if (!this.effects.isEmpty()) {
            for(MobEffectInstance mobeffectinstance1 : this.effects) {
                pLiving.addEffect(mobeffectinstance1, entity);
            }
        }

    }

    public void addEffect(MobEffectInstance pEffect) {
        this.effects.add(pEffect);
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return new ItemStack(CVNItems.HARPY_FEATHER.get());
    }

    @Override
    public @NotNull ItemStack getItem() {
        return new ItemStack(CVNItems.HARPY_FEATHER.get());
    }
}
