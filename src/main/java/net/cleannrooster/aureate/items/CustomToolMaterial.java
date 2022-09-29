package net.cleannrooster.aureate.items;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class CustomToolMaterial implements ToolMaterial {
    @Override
    public int getDurability() {
        return 1600;
    }
    @Override
    public float getMiningSpeedMultiplier() {
        return 5.0F;
    }
    @Override
    public float getAttackDamage() {
        return 2.0F;
    }
    @Override
    public int getMiningLevel() {
        return 2;
    }
    @Override
    public int getEnchantability() {
        return 25;
    }
    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ModItems.AURIUM);
    }
}
