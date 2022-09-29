package net.cleannrooster.aureate.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class DualWieldMedallion extends Spell{

    public DualWieldMedallion(Settings p_41383_) {
        super(p_41383_);
    }


    public boolean onClicked(ItemStack itemstack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        NbtCompound nbt;
        if(clickType == ClickType.RIGHT) {
            if (itemstack.hasNbt()) {
                if (itemstack.getNbt().get("Enabled") != null) {
                    nbt = itemstack.getNbt();
                    nbt.remove("Enabled");
                } else {
                    nbt = itemstack.getOrCreateNbt();
                    nbt.putInt("Enabled", 1);
                }

            } else {
                nbt = itemstack.getOrCreateNbt();
                nbt.putInt("Enabled", 1);
            }
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        if (stack.hasNbt()){
            if(stack.getNbt().getInt("Enabled") == 1){
                return true;
            }
        }
        return false;
    }
}
