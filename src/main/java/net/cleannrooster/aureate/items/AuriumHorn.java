package net.cleannrooster.aureate.items;

import net.cleannrooster.aureate.entities.ZombieSiegeAureate;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class AuriumHorn extends Item {


    public AuriumHorn(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        SoundEvent soundEvent = SoundEvents.GOAT_HORN_SOUNDS.get(world.random.nextInt(SoundEvents.GOAT_HORN_SOUND_COUNT-1));
        world.playSoundFromEntity(null, user, soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);
        world.emitGameEvent(GameEvent.INSTRUMENT_PLAY, user.getPos(), GameEvent.Emitter.of(user));
        if(!world.isClient()) {
            if(ZombieSiegeAureate.spawn((ServerWorld) world, true, true,user) == 1){
                user.getItemCooldownManager().set(this,600);
                if(hand == Hand.MAIN_HAND) {
                    itemStack.damage(1, user, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
                }
                if(hand == Hand.OFF_HAND) {
                    itemStack.damage(1, user, e -> e.sendEquipmentBreakStatus(EquipmentSlot.OFFHAND));
                }
            }
            else{
                user.sendMessage(Text.of("But nobody came."),true);
                user.getItemCooldownManager().set(this,100);
            }
        }

        return TypedActionResult.consume(user.getStackInHand(hand));
    }
}
