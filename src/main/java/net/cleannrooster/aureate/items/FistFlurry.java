package net.cleannrooster.aureate.items;

import net.cleannrooster.aureate.aureate;
import net.cleannrooster.aureate.entities.FistEntity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.ZombieSiegeManager;

import javax.swing.text.Position;

import static net.minecraft.entity.effect.StatusEffectCategory.BENEFICIAL;
import static net.minecraft.entity.effect.StatusEffectCategory.NEUTRAL;

public class FistFlurry extends StatusEffect {
    public FistFlurry() {
        super(
                BENEFICIAL, // whether beneficial or harmful for entities
                0x98D982); // color in RGB
    }

    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // In our case, we just make it return true so that it applies the status effect every tick.

        return duration % 2 == 1;

    }

    // This method is called when it applies the status effect. We implement custom functionality here.
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(entity instanceof PlayerEntity user) {
            float f7 = user.getHeadYaw();
            float f = user.getPitch();
            float f1 = (float) (-Math.sin(f7 * ((float) Math.PI / 180F)) * Math.cos(f * ((float) Math.PI / 180F)));
            float f2 = (float) -Math.sin(f * ((float) Math.PI / 180F));
            float f3 = (float) (Math.cos(f7 * ((float) Math.PI / 180F)) * Math.cos(f * ((float) Math.PI / 180F)));
            float f4 = (float) Math.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
            float f5 = 1.5F * ((1.0F + (float) 4) / 4.0F);
            f1 *= f5 / f4;
            f2 *= f5 / f4;
            f3 *= f5 / f4;
                /*if (p_43406_.isOnGround()) {
                    float f6 = 1.1999999F;
                    p_43406_.move(MoverType.SELF, new Vec3(0.0D, (double) 1.1999999F, 0.0D));
                }*/
            float f6 = 1.1999999F;

            SoundEvent soundevent;
            soundevent = SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP;
           user.getWorld().playSound((PlayerEntity) null, user.getBlockPos(), soundevent, SoundCategory.PLAYERS, 1.0F, 1.0F);

            if(!entity.getWorld().isClient()) {
                Vec3d vec = new Vec3d(user.getBoundingBox().getCenter().getX()+1.2*user.getWorld().getRandom().nextFloat()-0.6,user.getBoundingBox().getCenter().getY()+1.2*user.getWorld().getRandom().nextFloat()-0.6,user.getBoundingBox().getCenter().getZ()+1.2*user.getWorld().getRandom().nextFloat()-0.6);

                FistEntity fist = new FistEntity(entity.getWorld(),user, user.getX()+1.2*user.getWorld().getRandom().nextFloat()-0.6, user.getBoundingBox().getCenter().getY()+1.2*user.getWorld().getRandom().nextFloat()-0.6,user.getZ()+1.2*user.getWorld().getRandom().nextFloat()-0.6);
                fist.setVelocity(user,user.getPitch(),user.getHeadYaw(),user.getRoll(),3,0.1F);

                    entity.getWorld().spawnEntity(fist);
                }
        }
    }
}
