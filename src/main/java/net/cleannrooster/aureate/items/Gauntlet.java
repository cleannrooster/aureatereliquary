package net.cleannrooster.aureate.items;

import net.cleannrooster.aureate.StatusEffectsModded;
import net.cleannrooster.aureate.aureate;
import net.cleannrooster.aureate.entities.GauntletProjectile;
import net.cleannrooster.aureate.entities.MirrorEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.cleannrooster.aureate.entities.MirrorEntity.OWNER;

public class Gauntlet extends Item {
    public Gauntlet(Settings settings) {
        super(settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        NbtCompound nbt;
        ItemStack itemstack = user.getStackInHand(hand);
        if(user.getMainHandStack().isFood()){
            return TypedActionResult.fail(user.getOffHandStack());
        }
        if(user.isSneaking() || user.isDescending()) {
            if (itemstack.hasNbt()) {
                if (itemstack.getNbt().get("Flurry") != null) {
                    nbt = itemstack.getNbt();
                    nbt.remove("Flurry");
                    user.sendMessage(Text.of("Shooting mode"), true);
                } else {
                    nbt = itemstack.getOrCreateNbt();
                    nbt.putInt("Flurry", 1);
                    user.sendMessage(Text.of("Flurry mode"), true);
                }

            } else {
                nbt = itemstack.getOrCreateNbt();
                nbt.putInt("Flurry", 1);
                user.sendMessage(Text.of("Flurry mode"), true);
            }

        }
        else{

            if(itemstack.hasNbt()){
                if(itemstack.getNbt().get("Flurry") != null) {

                    user.getItemCooldownManager().set(this, 10);

                    float f7 = user.getHeadYaw();
                    float f = user.getPitch();
                    float f1 = (float) (-Math.sin(f7 * ((float) Math.PI / 180F)) * Math.cos(f * ((float) Math.PI / 180F)));
                    float f2 = (float) -Math.sin(f * ((float) Math.PI / 180F));
                    float f3 = (float) (Math.cos(f7 * ((float) Math.PI / 180F)) * Math.cos(f * ((float) Math.PI / 180F)));
                    float f4 = (float) Math.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
                    float f5 = 3.0F * ((1.0F + (float) 4) / 4.0F);
                    f1 *= f5 / f4;
                    f2 *= f5 / f4;
                    f3 *= f5 / f4;
                /*if (p_43406_.isOnGround()) {
                    float f6 = 1.1999999F;
                    p_43406_.move(MoverType.SELF, new Vec3(0.0D, (double) 1.1999999F, 0.0D));
                }*/
                    float f6 = 1.1999999F;
                    user.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.FISTFLURRY, 10, 0, false, false));

                    SoundEvent soundevent;
                    soundevent = SoundEvents.ITEM_TRIDENT_RIPTIDE_3;

                    world.playSound((PlayerEntity) null, user.getBlockPos(), soundevent, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    if(itemstack.getDamage() >= 9){
                        itemstack.setDamage(0);
                        user.getItemCooldownManager().set(this,200);
                        return TypedActionResult.consume(user.getStackInHand(hand));
                    }
                    if(hand == Hand.MAIN_HAND) {
                        itemstack.damage(1, user, (e) -> {
                            e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
                        });
                    }
                    if(hand == Hand.OFF_HAND) {
                        itemstack.damage(1, user, (e) -> {
                            e.sendEquipmentBreakStatus(EquipmentSlot.OFFHAND);
                        });
                    }
                }
                else {
                    GauntletProjectile proj = new GauntletProjectile(world,user, false, null);
                    proj.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 4F, 0F);
                    ItemStack stack = new ItemStack(Items.AIR);

                    proj.setItem(stack);
                    proj.setNoGravity(true);
                    world.spawnEntity(proj);
                    user.getWorld().playSoundFromEntity(null,user,SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, SoundCategory.PLAYERS,1,1);

                    user.getItemCooldownManager().set(this, (int) (30F / (float) user.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED)));
                    if(itemstack.getDamage() >= 9){
                        itemstack.setDamage(0);
                        user.getItemCooldownManager().set(this,200);
                        return TypedActionResult.consume(user.getStackInHand(hand));
                    }
                    if(hand == Hand.MAIN_HAND) {
                        itemstack.damage(1, user, (e) -> {
                            e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
                        });
                    }
                    if(hand == Hand.OFF_HAND) {
                        itemstack.damage(1, user, (e) -> {
                            e.sendEquipmentBreakStatus(EquipmentSlot.OFFHAND);
                        });
                    }

                }
            }
            else{
                GauntletProjectile proj = new GauntletProjectile(world,user, false, null);
                proj.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 4F, 0F);
                ItemStack stack = new ItemStack(Items.AIR);

                proj.setItem(stack);
                proj.setNoGravity(true);
                world.spawnEntity(proj);
                user.getItemCooldownManager().set(this, (int) (30F / (float) user.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED)));
                user.getWorld().playSoundFromEntity(null,user,SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, SoundCategory.PLAYERS,1,1);
                if(itemstack.getDamage() >= 9){
                    itemstack.setDamage(0);
                    user.getItemCooldownManager().set(this,200);
                    return TypedActionResult.consume(user.getStackInHand(hand));
                }
                if(hand == Hand.MAIN_HAND) {
                    itemstack.damage(1, user, (e) -> {
                        e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
                    });
                }
                if(hand == Hand.OFF_HAND) {
                    itemstack.damage(1, user, (e) -> {
                        e.sendEquipmentBreakStatus(EquipmentSlot.OFFHAND);
                    });
                }
/*
                List<LivingEntity> targets = user.getWorld().getEntitiesByType(TypeFilter.instanceOf(LivingEntity.class), new Box(new BlockPos(new Vec3d(user.raycast(20, 0, false).getPos().getX(), user.raycast(20, 0, false).getPos().getY(), user.raycast(20, 0, false).getPos().getZ())))
                        .expand(3), EntityPredicates.VALID_LIVING_ENTITY);
                LivingEntity realtarget = raycastentity(user);
                if(realtarget != null) {
                    execute(realtarget,user);
                }*/
            }
        }
        return TypedActionResult.consume(user.getStackInHand(hand));
    }
    public static LivingEntity raycastentity(PlayerEntity player){
        float f7 = player.getHeadYaw();
        float f8 = player.getHeadYaw()+60;
        float f9 = player.getHeadYaw()-60;
        float f = player.getPitch();
        float f1 = (float) (-Math.sin(f7 * ((float) Math.PI / 180F)) * Math.cos(f * ((float) Math.PI / 180F)));
        float f2 = (float) -Math.sin(f * ((float) Math.PI / 180F));
        float f3 = (float) (Math.cos(f7 * ((float) Math.PI / 180F)) * Math.cos(f * ((float) Math.PI / 180F)));

        Vec3d pos1 = (player.getEyePos().add(f1 * 20, f2 * 20, f3 * 20));
        LivingEntity realtarget = null;
        List<LivingEntity> clipped = new ArrayList<>();

        List<Entity> list = player.world.getOtherEntities(player, new Box(player.getEyePos().getX(), player.getEyePos().getY(), player.getEyePos().getZ(), (double)pos1.getX(), (double)pos1.getY(), (double)pos1.getZ()));
        for (Entity entity : list) {
            if( entity instanceof LivingEntity living) {
                Optional<Vec3d> vec1 = entity.getBoundingBox().expand(0.5).raycast(player.getEyePos(), pos1);
                if (vec1.isPresent() && player.canSee(entity) && entity != player) {
                    clipped.add(living);

                }
            }
        }
        for(LivingEntity clip : clipped){
            if(realtarget == null){
                realtarget = clip;
            }
            if (clip.distanceTo(player) < realtarget.distanceTo(player)) {
                realtarget = clip;
            }
        }
        return realtarget;
    }
    public void execute(LivingEntity realtarget, PlayerEntity user){
        MirrorEntity mirror = new MirrorEntity(aureate.MIRROR_ENTITY,user.getWorld());
        NbtCompound nbt = new NbtCompound();
        nbt.putString("mirror",user.getUuidAsString());
        mirror.setCustomName(Text.of(user.getGameProfile().getName()));
        mirror.getDataTracker().set(OWNER,Optional.of(user.getUuid()));
        mirror.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY,80,0,false,false));

        mirror.clearGoalsAndTasks();
        mirror.setNoGravity(true);
        mirror.setAiDisabled(true);
        mirror.setSilent(true);
        mirror.setCustomNameVisible(false);
        mirror.isFireImmune();
        mirror.setInvulnerable(true);
        mirror.setHeadYaw(user.getYaw());
        mirror.setPitch(user.getPitch());
        Vec3d vec3 = realtarget.getBoundingBox().getCenter().subtract(user.getPos());
        Vec3d vec32 = vec3.normalize();
        mirror.setPos(realtarget.getX()-realtarget.getBoundingBox().getXLength()*vec32.getX()-vec32.getX(),realtarget.getBlockY(),realtarget.getZ()-realtarget.getBoundingBox().getZLength()*vec32.getZ()-vec32.getZ());
        mirror.equipStack(EquipmentSlot.MAINHAND,user.getMainHandStack());

        mirror.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.MIRROR,7,1, false, false));
        mirror.setInvulnerable(true);
        realtarget.getWorld().spawnEntity(mirror);
        mirror.swingHand(Hand.MAIN_HAND);
        user.attack(realtarget);

    }
}
