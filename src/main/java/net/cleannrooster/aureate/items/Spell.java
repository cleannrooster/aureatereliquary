package net.cleannrooster.aureate.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;


import java.util.Properties;

public class Spell extends Item {
    public Spell(Settings p_41383_) {
        super(p_41383_);
    }
    public boolean targeted = false;
    public boolean trigger(World level, PlayerEntity player, float modifier){
        return false;
    }
    public boolean triggeron(World level, PlayerEntity player, LivingEntity target, float modifier){
        return false;
    }
    public boolean isTargeted(){
        return false;
    }

}
