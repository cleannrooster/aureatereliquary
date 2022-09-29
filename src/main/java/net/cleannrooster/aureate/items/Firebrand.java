package net.cleannrooster.aureate.items;

import net.cleannrooster.aureate.StatusEffectsModded;
import net.cleannrooster.aureate.entities.FirebrandProjectile;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.MinecraftClientGame;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

import java.util.ArrayList;
import java.util.List;

public class Firebrand extends SwordItem {
    public Firebrand(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity player) {
            if (!player.getItemCooldownManager().isCoolingDown(this)) {

                if(!FabricLoader.getInstance().isModLoaded("bettercombat") && !(player.getOffHandStack().getItem() instanceof Gauntlet)) {
                    player.getItemCooldownManager().set(this, (int) (20F / (float) attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED)));
                }                if (attacker.hasStatusEffect(StatusEffectsModded.ETHEREAL)) {
                    attacker.setAbsorptionAmount(attacker.getAbsorptionAmount() + 2);
                }
                if(!target.isOnFire()) {
                    target.setOnFireFor(4);
                }
                else{
                    target.setOnFireFor(4);
                    List<Entity> entities = target.world.getOtherEntities(player, target.getBoundingBox().expand(3));
                    for (Entity entity : entities) {
                        if (entity instanceof LivingEntity) {
                            LivingEntity target2 = (LivingEntity) entity;
                            FirebrandProjectile flux = new FirebrandProjectile(target.world, attacker);
                            flux.list.add(target);
                            if (!flux.list.contains(target2) && target2 != target) {
                                flux.target = target2;
                                flux.setOwner(attacker);
                                flux.setPos(target.getX(), target.getY(), target.getZ());
                                if (!target2.isOnFire()) {
                                    flux.bool = true;
                                }
                                target.world.spawnEntity(flux);
                            }
                        }
                    }
                }

            }
        }
        return super.postHit(stack, target, attacker);
    }
    public static void FluxFlux(LivingEntity target, PlayerEntity player, List<LivingEntity> list){
        target.hurtTime = 0;
        target.timeUntilRegen = 0;
        target.damage(DamageSource.IN_FIRE,1);
        target.hurtTime = 0;
        target.timeUntilRegen = 0;
        if(!target.isOnFire()) {
            target.setOnFireFor(4);
        }
        else{
            List<Entity> entities = target.world.getOtherEntities(player, target.getBoundingBox().expand(3));
            int iii = 0;
            for (Entity entity : entities) {
                if(entity instanceof LivingEntity) {
                    target.setOnFireFor(4);
                    LivingEntity target2 = (LivingEntity) entity;
                    FirebrandProjectile flux = new FirebrandProjectile(target.world, player);
                    flux.list = list;
                    flux.list.add(target);
                    if (!flux.list.contains(target2) && target2 != target) {
                        flux.target = target2;
                        flux.setOwner(player);
                        flux.setPos(target.getX(), target.getY(), target.getZ());
                        if (!target2.isOnFire()) {
                            flux.bool = true;
                        }
                        target.world.spawnEntity(flux);
                        iii++;
                    }
                }
            }
        }
    }
}
