package net.cleannrooster.aureate.entities;

import com.mojang.logging.LogUtils;
import net.cleannrooster.aureate.items.ModItems;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BiomeTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.spawner.Spawner;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.*;

public class ZombieSiegeAureate {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static boolean spawned;
    private static State state;
    private static int remaining;
    private static int countdown;
    private static int startX;
    private static int startY;
    private static int startZ;
    private static boolean spawnedboss = false;

    public ZombieSiegeAureate() {
        this.state = ZombieSiegeAureate.State.SIEGE_DONE;
    }

    public static int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals,PlayerEntity player) {
        if (!world.isDay() && spawnMonsters) {
            spawnedboss = false;
            if (!spawn(world,player)) {
                return 0;
            }

            spawned = true;
            remaining = 15;
            while (remaining > 0) {
                trySpawnZombie(world, remaining);
                --remaining;
            }
            return 1;
        } else {
            return 0;
        }
    }

    private static boolean spawn(ServerWorld world,PlayerEntity player) {

            PlayerEntity playerEntity = (PlayerEntity) player;
            if (!playerEntity.isSpectator()) {
                BlockPos blockPos = playerEntity.getBlockPos();
                    for (int i = 0; i < 10; ++i) {
                        float f = world.random.nextFloat() * 6.2831855F;
                        startX = blockPos.getX() + MathHelper.floor(MathHelper.cos(f) * 32.0F);
                        startY = blockPos.getY();
                        startZ = blockPos.getZ() + MathHelper.floor(MathHelper.sin(f) * 32.0F);


                    return true;
                }
            }


        return false;
    }

    private static void trySpawnZombie(ServerWorld world, int remaining) {
        Vec3d vec3d = getSpawnVector(world, new BlockPos(startX, startY, startZ));
        if (vec3d != null) {
            ZombieEntity zombieEntity;
            try {
                zombieEntity = new ZombieEntity(world);
                if(!spawnedboss) {
                    for(int ii = 0; ii < 3; ii++) {
                        ZombieEntity zombieEntity2 = new ZombieEntity(world);

                        Random rand = new Random();
                        List<ItemStack> list = new ArrayList<>();
                        list.add(ModItems.FLURRYBLADE.getDefaultStack());
                        list.add(ModItems.AUREATE_MACE.getDefaultStack());
                        list.add(ModItems.AUREATE_SWORD.getDefaultStack());
                        list.add(ModItems.PRIMAL_AXE.getDefaultStack());
                        list.add(ModItems.GAUNTLET.getDefaultStack());
                        list.add(ModItems.SQUALLSWORD.getDefaultStack());
                        list.add(ModItems.MACUAHUITL.getDefaultStack());
                        list.add(ModItems.FIREBRAND.getDefaultStack());
                        list.add(ModItems.QUANTUMBLADE.getDefaultStack());
                        list.add(ModItems.SEETHING_FURY.getDefaultStack());


                        zombieEntity2.equipStack(EquipmentSlot.MAINHAND, list.get(rand.nextInt(10)));
                        zombieEntity2.setEquipmentDropChance(EquipmentSlot.MAINHAND, 1);
                        zombieEntity2.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).addPersistentModifier(new EntityAttributeModifier("strength", 0.5, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
                        zombieEntity2.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).addPersistentModifier(new EntityAttributeModifier("health", 1, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
                        zombieEntity2.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).addPersistentModifier(new EntityAttributeModifier("speed", 0.5, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
                        zombieEntity2.initialize(world, world.getLocalDifficulty(zombieEntity.getBlockPos()), SpawnReason.EVENT, (EntityData) null, (NbtCompound) null);
                        zombieEntity2.refreshPositionAndAngles(vec3d.x, vec3d.y, vec3d.z, world.random.nextFloat() * 360.0F, 0.0F);
                        world.spawnEntityAndPassengers(zombieEntity2);
                    }


                    spawnedboss = true;
                }
                zombieEntity.initialize(world, world.getLocalDifficulty(zombieEntity.getBlockPos()), SpawnReason.EVENT, (EntityData)null, (NbtCompound)null);

            } catch (Exception var5) {
                LOGGER.warn("Failed to create zombie for village siege at {}", vec3d, var5);
                return;
            }

            zombieEntity.refreshPositionAndAngles(vec3d.x, vec3d.y, vec3d.z, world.random.nextFloat() * 360.0F, 0.0F);
            world.spawnEntityAndPassengers(zombieEntity);
        }
    }

    @Nullable
    private static Vec3d getSpawnVector(ServerWorld world, BlockPos pos) {
        for(int i = 0; i < 10; ++i) {
            int j = pos.getX() + world.random.nextInt(16) - 8;
            int k = pos.getZ() + world.random.nextInt(16) - 8;
            int l = world.getTopY(Heightmap.Type.WORLD_SURFACE, j, k);
            BlockPos blockPos = new BlockPos(j, l, k);
                return Vec3d.ofBottomCenter(blockPos);

        }

        return null;
    }

    private static enum State {
        SIEGE_CAN_ACTIVATE,
        SIEGE_TONIGHT,
        SIEGE_DONE;

        private State() {
        }
    }
}