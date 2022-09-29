package net.cleannrooster.aureate.entities;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AbstractZombieModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;

@Environment(EnvType.CLIENT)
public class MirrorModel extends BipedEntityModel {


    public MirrorModel(ModelPart root) {
        super(root);
    }
}
