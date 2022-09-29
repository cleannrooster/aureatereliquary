package net.cleannrooster.aureate;

import com.sun.jdi.Mirror;
import jdk.jfr.Enabled;
import net.cleannrooster.aureate.entities.*;
import net.cleannrooster.aureate.items.*;
import net.cleannrooster.aureate.items.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.data.server.ChestLootTableGenerator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.structure.Structures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static net.cleannrooster.aureate.items.ModItems.*;
import static net.minecraft.enchantment.EnchantmentHelper.*;
import static net.minecraft.particle.DustParticleEffect.RED;

public class aureate implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("aureate");
	public static final Identifier ETHEREAL = new Identifier("aureate", "ethereal");
	public static final EntityType<FirebrandProjectile> FIREBRAND_PROJECTILE_ENTITY_TYPE = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier("aureate", "fire"),
			FabricEntityTypeBuilder.<FirebrandProjectile>create(SpawnGroup.MISC, FirebrandProjectile::new)
					.dimensions(EntityDimensions.fixed(1F,1F)) // dimensions in Minecraft units of the projectile
					.trackRangeBlocks(64).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
					.build() // VERY IMPORTANT DONT DELETE FOR THE LOVE OF GOD PSLSSSSSS
	);
	public static final EntityType<GauntletProjectile> GAUNTLET_PROJECTILE_ENTITY_TYPE = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier("aureate", "gauntlet"),
			FabricEntityTypeBuilder.<GauntletProjectile>create(SpawnGroup.MISC, GauntletProjectile::new)
					.dimensions(EntityDimensions.changing(1F, 1F)) // dimensions in Minecraft units of the projectile
					.trackRangeBlocks(64).trackedUpdateRate(1) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
					.build() // VERY IMPORTANT DONT DELETE FOR THE LOVE OF GOD PSLSSSSSS
	);
	public static final EntityType<GaleswordProjectile> GALESWORD_PROJECTILE_ENTITY_TYPE = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier("aureate", "galeswordproj"),
			FabricEntityTypeBuilder.<GaleswordProjectile>create(SpawnGroup.MISC, GaleswordProjectile::new)
					.dimensions(EntityDimensions.changing(1F, 1F)) // dimensions in Minecraft units of the projectile
					.trackRangeBlocks(64).trackedUpdateRate(1) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
					.build() // VERY IMPORTANT DONT DELETE FOR THE LOVE OF GOD PSLSSSSSS
	);
	public static final EntityType<FistEntity> FIST = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier("aureate", "fist"),
			FabricEntityTypeBuilder.<FistEntity>create(SpawnGroup.MISC, FistEntity::new)
					.dimensions(EntityDimensions.changing(0.6F, 0.6F)) // dimensions in Minecraft units of the projectile
					.trackRangeBlocks(64).trackedUpdateRate(1) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
					.build() // VERY IMPORTANT DONT DELETE FOR THE LOVE OF GOD PSLSSSSSS
	);
	public static final EntityType<BlackWolf> BLACKWOLF = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier("aureate", "etherealwolf"),
			FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, BlackWolf::new)
					.dimensions(EntityDimensions.fixed(0.6F, 0.85F))
					.trackRangeBlocks(64)
					.trackedUpdateRate(1)
					.build()
	);
	public static final EntityType<SeethingLightning> SEETHING_LIGHTNING = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier("aureate", "seethinglightning"),
			FabricEntityTypeBuilder.create(SpawnGroup.MISC, SeethingLightning::new)
					.dimensions(EntityDimensions.fixed(0F, 0F))
					.trackRangeBlocks(128)
					.trackedUpdateRate(1)
					.build()
	);
	public static final EntityType<MirrorEntity> MIRROR_ENTITY = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier("aureate", "mirror"),
			FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MirrorEntity::new)
					.dimensions(EntityDimensions.fixed(0.6F, 1.95F))
					.trackRangeBlocks(10)
					.trackedUpdateRate(1)
					.build()
	);


	public static final ItemGroup GROUP = FabricItemGroupBuilder.create(
					new Identifier("aureate", "items"))
			.icon(() -> new ItemStack(AURIUM))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(AURIUM));
				stacks.add(new ItemStack(AUREATE_HELMET));
				stacks.add(new ItemStack(AUREATE_CHESTPLATE));
				stacks.add(new ItemStack(AUREATE_LEGGINGS));
				stacks.add(new ItemStack(AUREATE_BOOTS));
				stacks.add(new ItemStack(AURIUMHORN));

				stacks.add(new ItemStack(AUREATE_SWORD));
				stacks.add(new ItemStack(AUREATE_MACE));
				stacks.add(new ItemStack(QUANTUMBLADE));
				stacks.add(new ItemStack(FLURRYBLADE));
				stacks.add(new ItemStack(SQUALLSWORD));
				stacks.add(new ItemStack(FIREBRAND));
				stacks.add(new ItemStack(MACUAHUITL));
				stacks.add(new ItemStack(PRIMAL_AXE));
				stacks.add(new ItemStack(SEETHING_FURY));









				stacks.add(new ItemStack(GAUNTLET));




			})
			.build();

	private static final Identifier DUNGEON = new Identifier("minecraft", "chests/simple_dungeon");;
	private static final Identifier IGLOO = new Identifier("minecraft", "chests/igloo_chest");;
	private static final Identifier PYRAMID = new Identifier("minecraft", "chests/desert_pyramid");;
	private static final Identifier DESERTHOUSE = new Identifier("minecraft", "chests/village/village_desert_house");;
	private static final Identifier ARMORER = new Identifier("minecraft", "chests/village/village_armorer");;
	private static final Identifier WEAPONS = new Identifier("minecraft", "chests/village/village_weaponsmith");;
	private static final Identifier JUNGLETEMPLE = new Identifier("minecraft", "chests/jungle_temple");;

	private static final Identifier SHEPHERD = new Identifier("minecraft", "chests/village/village_shepherd");;
	private static final Identifier LIBRARY = new Identifier("minecraft", "chests/stronghold_library");;
	private static final Identifier CORRIDOR = new Identifier("minecraft", "chests/stronghold_corridor");;
	private static final Identifier CROSSING = new Identifier("minecraft", "chests/stronghold_crossing");;


	@Override
	public void onInitialize() {
		FabricDefaultAttributeRegistry.register(BLACKWOLF, BlackWolf.setAttributes());
		FabricDefaultAttributeRegistry.register(MIRROR_ENTITY, MirrorEntity.createZombieAttributes());


		// No magic constants!

// Actual code

		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			// Let's only modify built-in loot tables and leave data pack loot tables untouched by checking the source.
			// We also check that the loot table ID is equal to the ID we want.
			if (source.isBuiltin() && DUNGEON.equals(id)) {
				// Our code will go here
				LootPool.Builder poolBuilder = LootPool.builder()
						.with(ItemEntry.builder(AURIUM));

				tableBuilder.pool(poolBuilder);
			}
			if (source.isBuiltin() && IGLOO.equals(id)) {
				LootPool.Builder poolBuilder = LootPool.builder()
						.with(ItemEntry.builder(ModItems.SQUALLSWORD)).with(ItemEntry.builder(ModItems.PRIMAL_AXE));

				tableBuilder.pool(poolBuilder);
				// Our code will go here
			}
			if (source.isBuiltin() && PYRAMID.equals(id)) {
				// Our code will go here
				LootPool.Builder poolBuilder = LootPool.builder()
						.with(ItemEntry.builder(ModItems.FLURRYBLADE)).with(ItemEntry.builder(SEETHING_FURY)).with(ItemEntry.builder(ModItems.GAUNTLET)).with(ItemEntry.builder(ModItems.FIREBRAND));

				tableBuilder.pool(poolBuilder);
			}
			if (source.isBuiltin() && JUNGLETEMPLE.equals(id)) {
				// Our code will go here
				LootPool.Builder poolBuilder = LootPool.builder()
						.with(ItemEntry.builder(ModItems.MACUAHUITL)).with(ItemEntry.builder(SEETHING_FURY));

				tableBuilder.pool(poolBuilder);
			}
			if (source.isBuiltin() && DESERTHOUSE.equals(id)) {
				LootPool.Builder poolBuilder = LootPool.builder()
						.with(ItemEntry.builder(ModItems.FLURRYBLADE)).with(ItemEntry.builder(ModItems.GAUNTLET)).with(ItemEntry.builder(ModItems.FIREBRAND));

				tableBuilder.pool(poolBuilder);			}
			if (source.isBuiltin() && ARMORER.equals(id)) {
				LootPool.Builder poolBuilder = LootPool.builder()
						.with(ItemEntry.builder(ModItems.AUREATE_BOOTS)).with(ItemEntry.builder(AUREATE_HELMET)).with(ItemEntry.builder(AUREATE_CHESTPLATE)).with(ItemEntry.builder(ModItems.AUREATE_LEGGINGS));

				tableBuilder.pool(poolBuilder);
			}
			if (source.isBuiltin() && WEAPONS.equals(id)) {
				LootPool.Builder poolBuilder = LootPool.builder()
						.with(ItemEntry.builder(ModItems.AUREATE_SWORD)).with(ItemEntry.builder(ModItems.AUREATE_MACE));

				tableBuilder.pool(poolBuilder);
			}
			if (source.isBuiltin() && LIBRARY.equals(id)) {
				LootPool.Builder poolBuilder = LootPool.builder()
						.with(ItemEntry.builder(ModItems.GAUNTLET)).with(ItemEntry.builder(ModItems.QUANTUMBLADE));

				tableBuilder.pool(poolBuilder);
				// Our code will go here
			}
			if (source.isBuiltin() && SHEPHERD.equals(id)) {
				LootPool.Builder poolBuilder = LootPool.builder()
						.with(ItemEntry.builder(ModItems.PRIMAL_AXE));

				tableBuilder.pool(poolBuilder);
				// Our code will go here
			}
			if (source.isBuiltin() && CORRIDOR.equals(id)) {
				LootPool.Builder poolBuilder = LootPool.builder()
						.with(ItemEntry.builder(AURIUM));

				tableBuilder.pool(poolBuilder);
				// Our code will go here
			}
			if (source.isBuiltin() && CROSSING.equals(id)) {
				LootPool.Builder poolBuilder = LootPool.builder()
						.with(ItemEntry.builder(AURIUM));

				tableBuilder.pool(poolBuilder);
				// Our code will go here
			}
		});

		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) ->
		{

	/*		for (int i = 0; i <= player.getInventory().size(); i++)
				if(player.getInventory().getStack(i).hasNbt()){
					if(player.getInventory().getStack(i).getNbt().contains("Enabled")) {
						System.out.println(Registry.ITEM.getId(player.getOffHandStack().getItem()).getNamespace());*/
			if(FabricLoader.getInstance().isModLoaded("bettercombat")){
				return ActionResult.PASS;
			}
			if(entity instanceof LivingEntity living && Objects.equals(Registry.ITEM.getId(player.getOffHandStack().getItem()).getNamespace(), "aureate")) {
				if (!player.getItemCooldownManager().isCoolingDown(player.getOffHandStack().getItem())) {
					player.getOffHandStack().postHit(living, player);

				}
			}



					/*}
				}*/
			return ActionResult.PASS;
		});



		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ModItems.registerItems();
		StatusEffectsModded.registerStatusEffects();

		LOGGER.info("Hello Fabric world!");
	}
}
