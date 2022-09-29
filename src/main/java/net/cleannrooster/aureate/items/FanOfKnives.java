package net.cleannrooster.aureate.items;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.cleannrooster.aureate.StatusEffectsModded;
import net.cleannrooster.aureate.aureate;
import net.cleannrooster.aureate.entities.MirrorEntity;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static net.cleannrooster.aureate.entities.MirrorEntity.OWNER;
import static net.minecraft.entity.effect.StatusEffectCategory.BENEFICIAL;

public class FanOfKnives extends StatusEffect {
    public FanOfKnives() {
        super(
                BENEFICIAL, // whether beneficial or harmful for entities
                0x98D982); // color in RGB
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % amplifier == 1;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {

        super.applyUpdateEffect(entity, amplifier);

    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        /*if(entity instanceof PlayerEntity player){
            player.getItemCooldownManager().set(ModItems.FLURRYBLADE, (int) (80F/player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED)));
        }*/
        if(entity instanceof PlayerEntity player){
            if(player.getAttacking() != null) {
                player.getAttacking().hurtTime = 0;
                player.getAttacking().timeUntilRegen = 0;

                MirrorEntity mirror = new MirrorEntity(aureate.MIRROR_ENTITY,entity.getWorld());
                mirror.setInvisible(true);
                mirror.getDataTracker().set(OWNER,Optional.of(player.getUuid()));
                mirror.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY,80,0,false,false));
                mirror.setSilent(true);
                NbtCompound nbt = new NbtCompound();
                nbt.putString("mirror",player.getUuidAsString());
                mirror.setCustomName(Text.of(player.getGameProfile().getName()));
                mirror.setNoGravity(true);
                mirror.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY,20,0,false,false));
                mirror.setHeadYaw(180+player.getYaw());
                mirror.setPitch(-player.getPitch());
                mirror.equipStack(EquipmentSlot.OFFHAND,player.getOffHandStack());
                Vec3d vec3 = player.getAttacking().getBoundingBox().getCenter().subtract(player.getPos());
                Vec3d vec32 = vec3.normalize().multiply(-1);
                mirror.setPos(player.getAttacking().getX()-player.getAttacking().getBoundingBox().getXLength()*vec32.getX()-vec32.getX(),player.getAttacking().getBlockY(),player.getAttacking().getZ()-player.getAttacking().getBoundingBox().getZLength()*vec32.getZ()-vec32.getZ());
                mirror.equipStack(EquipmentSlot.MAINHAND,player.getMainHandStack());

                mirror.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.MIRROR,7,1, false, false));
                mirror.setInvulnerable(true);
                mirror.setInvisible(true);
                player.getAttacking().getWorld().spawnEntity(mirror);
                mirror.swingHand(Hand.MAIN_HAND);
                if(player.getMainHandStack().getItem() != ModItems.FLURRYBLADE && !player.getItemCooldownManager().isCoolingDown(player.getMainHandStack().getItem())){
                    player.getMainHandStack().getItem().postHit(player.getMainHandStack(),player.getAttacking(),player);
                }
                if(player.getOffHandStack().getItem() != ModItems.FLURRYBLADE && !player.getItemCooldownManager().isCoolingDown(player.getOffHandStack().getItem())){
                    player.getOffHandStack().getItem().postHit(player.getOffHandStack(),player.getAttacking(),player);
                }
                player.getAttacking().damage(DamageSource.player(player), (float) (0.5F*player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)));
                player.getAttacking().hurtTime = 0;
                player.getAttacking().timeUntilRegen = 0;
            }
        }
        super.onRemoved(entity, attributes, amplifier);
    }
    /*@Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if(entity.getAttacking() != null && entity instanceof PlayerEntity player) {
                    if (amplifier == 0 && entity.getAttacking().getBoundingBox().intersects(player.getBoundingBox().expand(ReachEntityAttributes.getAttackRange(player, 3)))) {
                        entity.getAttacking().hurtTime = 0;
                        entity.getAttacking().timeUntilRegen = 0;
                        player.attack(entity.getAttacking());
                        entity.getAttacking().hurtTime = 0;
                        entity.getAttacking().timeUntilRegen = 0;

                    }
                    else if(amplifier == 1){
                        entity.getAttacking().hurtTime = 0;
                        entity.getAttacking().timeUntilRegen = 0;
                        player.attack(entity.getAttacking());
                        entity.getAttacking().hurtTime = 0;
                        entity.getAttacking().timeUntilRegen = 0;
                    }
                    else if(amplifier == 2){
                        entity.getAttacking().hurtTime = 0;
                        entity.getAttacking().timeUntilRegen = 0;
                        player.attack(entity.getAttacking());
                        entity.getAttacking().hurtTime = 0;
                        entity.getAttacking().timeUntilRegen = 0;
                    }
                    else if(amplifier == 3){
                        entity.getAttacking().hurtTime = 0;
                        entity.getAttacking().timeUntilRegen = 0;
                        player.attack(entity.getAttacking());
                        entity.getAttacking().hurtTime = 0;
                        entity.getAttacking().timeUntilRegen = 0;
                    }
        }
        super.onRemoved(entity, attributes, amplifier);

        if(entity instanceof PlayerEntity player && entity.getAttacking() != null) {

            *//*if (entity.getMainHandStack().getItem() instanceof AureateSword && ReachEntityAttributes.isWithinAttackRange(player, player.getAttacking())) {
                entity.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.FLURRYING, (int) ((20 / entity.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED))+1)));
            }*//*
        }

    }*/
}
