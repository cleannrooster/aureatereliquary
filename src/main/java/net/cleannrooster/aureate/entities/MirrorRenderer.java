package net.cleannrooster.aureate.entities;

import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.sun.jdi.Mirror;
import net.cleannrooster.aureate.StatusEffectsModded;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringHelper;
import org.jetbrains.annotations.Nullable;

import org.w3c.dom.Text;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import static net.cleannrooster.aureate.entities.MirrorEntity.OWNER;

@Environment(EnvType.CLIENT)
public class MirrorRenderer extends HuskEntityRenderer {


    public MirrorRenderer(EntityRendererFactory.Context context) {
        super(context);
    }



    @Override
    public Identifier getTexture(ZombieEntity zombieEntity) {
            if(zombieEntity.hasCustomName() ) {
           return DefaultSkinHelper.getTexture(zombieEntity.getDataTracker().get(OWNER).get());

        }
            return new Identifier("textures/entity/zombie/husk.png");
    }
}
