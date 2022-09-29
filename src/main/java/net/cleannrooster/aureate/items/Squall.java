package net.cleannrooster.aureate.items;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static net.minecraft.entity.effect.StatusEffectCategory.BENEFICIAL;

public class Squall extends StatusEffect {
    public Squall() {
        super(
                BENEFICIAL, // whether beneficial or harmful for entities
                0x98D982); // color in RGB
    }
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // In our case, we just make it return true so that it applies the status effect every tick.
        if (duration % 5 == 1) {
            return true;
        }
        else{
            return false;
        }

    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);
        int num_pts = 200;
        /*if (this.getLevel().isClientSide)
            {
                ClientLevel level = (ClientLevel) this.getLevel();*/
        int i = 0;
        if(entity.getAttacking() == null){
            return;
        }
        for ( i = 0; i <= num_pts; i = i + 1) {
            double[] indices = IntStream.rangeClosed(0, (int) ((1000 - 0) / 1))
                    .mapToDouble(x -> x * 1 + 0).toArray();

            double phi = Math.acos(1 - 2 * indices[i] / num_pts);
            double theta = Math.PI * (1 + Math.pow(5, 0.5) * indices[i]);
            double x = cos(theta) * sin(phi);
            double y = Math.sin(theta) * sin(phi);
            double z = cos(phi);
            PacketByteBuf buf = PacketByteBufs.create();

            buf.writeDouble(4*x+entity.getAttacking().getX());
            buf.writeDouble(4*y+entity.getAttacking().getY());
            buf.writeDouble(4*z+entity.getAttacking().getZ());

            if(!entity.world.isClient()) {
                for (PlayerEntity player : entity.world.getPlayers()) {
                    ServerPlayNetworking.send((ServerPlayerEntity) player, Objects.requireNonNull(Identifier.tryParse("a")), buf);
                }
            }
        }
        List<Entity> others =  entity.world.getOtherEntities(null,entity.getAttacking().getBoundingBox().expand(4));
        for(Entity target : others){
            if(target instanceof LivingEntity living){

                if(entity instanceof PlayerEntity player) {
                    if(target != entity) {
                        living.hurtTime = 0;
                        living.timeUntilRegen = 0;
                        living.damage(DamageSource.FREEZE, 1F);
                        living.hurtTime = 0;
                        living.timeUntilRegen = 0;
                    }
                }
            }
        }
    }
}
