package net.cleannrooster.aureate;

import net.cleannrooster.aureate.entities.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.Objects;
import java.util.UUID;

public class client implements ClientModInitializer {
    public static final Identifier PacketID = new Identifier("aureate", "spawn_packet");
    public static final EntityModelLayer MODEL_CUBE_LAYER = new EntityModelLayer(new Identifier("aureate", "blackwolf"), "main");
    public static final EntityModelLayer MODEL_FIST_LAYER = new EntityModelLayer(new Identifier("aureate", "fist"), "main");

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(aureate.FIREBRAND_PROJECTILE_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(aureate.GAUNTLET_PROJECTILE_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(aureate.GALESWORD_PROJECTILE_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(aureate.SEETHING_LIGHTNING, SeethingLightningRenderer::new);

        receiveEntityPacket();

        // In 1.17, use EntityRendererRegistry.register (seen below) instead of EntityRendererRegistry.INSTANCE.register (seen above)
        EntityRendererRegistry.register(aureate.BLACKWOLF, BlackWolfRenderer::new);
        EntityRendererRegistry.register(aureate.MIRROR_ENTITY, MirrorRenderer::new);
        EntityRendererRegistry.register(aureate.FIST, (context) -> {
            return new FistEntityRenderer(context);
        });


        EntityModelLayerRegistry.registerModelLayer(MODEL_CUBE_LAYER, BlackWolfModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(MODEL_FIST_LAYER, FistModel::getTexturedModelData);

        // older versions may have to use
		/* EntityRendererRegistry.INSTANCE.register(ProjectileTutorialMod.PackedSnowballEntityType, (context) ->
				 new FlyingItemEntityRenderer(context)); */

    }
    public void receiveEntityPacket() {
        ClientPlayNetworking.registerGlobalReceiver(Objects.requireNonNull(Identifier.tryParse("a")), (client, handler, buf, responseSender) -> {
            double x = buf.readDouble();
            double y = buf.readDouble();
            double z = buf.readDouble();

            client.execute(() -> {
                // Everything in this lambda is run on the render thread
                client.world.addParticle(ParticleTypes.FIREWORK,x,y,z,0,0,0);
            });
        });
        ClientPlayNetworking.registerGlobalReceiver(PacketID, (ctx,clientPlayNetworkHandler, packetByteBuf,packetSender) -> {
            EntityType<?> et = Registry.ENTITY_TYPE.get(packetByteBuf.readVarInt());
            UUID uuid = packetByteBuf.readUuid();
            int entityId = packetByteBuf.readVarInt();
            Vec3d pos = EntitySpawnPacket.PacketBufUtil.readVec3d(packetByteBuf);
            float pitch = EntitySpawnPacket.PacketBufUtil.readAngle(packetByteBuf);
            float yaw = EntitySpawnPacket.PacketBufUtil.readAngle(packetByteBuf);
                if (MinecraftClient.getInstance().world == null)
                    throw new IllegalStateException("Tried to spawn entity in a null world!");
            ctx.execute(() -> {
                Entity e = et.create(MinecraftClient.getInstance().world);
                if (e == null)
                    throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getId(et) + "\"!");
                e.updateTrackedPosition(pos.x, pos.y, pos.z);
                e.setPos(pos.x, pos.y, pos.z);
                e.setPitch(pitch);
                e.setYaw(yaw);
                e.setId(entityId);
                e.setUuid(uuid);
                MinecraftClient.getInstance().world.addEntity(entityId, e);
            });
        });
    }
}
