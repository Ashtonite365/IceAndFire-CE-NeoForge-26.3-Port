# Ice and Fire CE → NeoForge 26.3.0.76 — work split (personal port, local only)

Repo: /workspace/mod-port/iceandfire/port (branch iaf-integration, from IceAndFire-CE master c67d9077). Owners: OWNERS.tsv (exact path wins, else longest `/**` glob). Package root `com/iafenvoy/iceandfire/`. Plan: ../PLAN.md §7.

| Owner | Scope |
|---|---|
| H1 (FIRST) | build files, libs/, templates/META-INF (neoforge.mods.toml), META-INF (AT), IceAndFire.java, registry, config, network, data, event, util, loot, effect, recipe, compat/** (stubs), com/iafenvoy/integration (Integration stub) |
| H2 | entity root: Dragon*/FireDragon*/IceDragon*/LightningDragon*, Amphithere*, Hippogryph*, Hippocampus, SeaSerpent*, multipart (BodyPart, MultipartPart, SlowPart) |
| H3 | rest of entity root, entity/util/** (incl. util/dragon), entity/pathfinding/** |
| H4 | entity/ai/**, item/** |
| H5 | world/**, mixin/**, iceandfire.mixins.json, particle/*ParticleType.java (server side), src/main/resources/data/** |
| H6 | render/**, screen/**, client/**, IceAndFireClient.java, particle/** client particles, assets/**, resourcepacks/**, logo.png |

## Build setup (iaf-integration)
- NeoForge 26.3.0.76, MC 26.3, moddev 2.0.148 (same as other ports). Gradle: -Xmx2500m, daemon/parallel/configuration-cache off.
- Uranus 3.0-alpha.1 (local port-26.3 4f54561, sha256 f3e95fac…) and Jupiter 2.4.3-26.3 (local port-26.3 e287a60, sha256 ade122b4…) as `files("libs/…jar")`.
- Curios 17.0.0-beta.3+26.3 (`maven.modrinth:curios:W912LxOl`).
- IAFEnvoy Integration → local stub `com.iafenvoy.integration.IntegrationExecutor.runWhenLoad(modId, Supplier<Runnable>)` (ModList check).
- Disabled (sources excluded in build.gradle, deps removed): JEI, Jade, EMI, Ponder, Ars Nouveau, ProjectE.

## Rules
- Edit only files you own (OWNERS.tsv). Need a change elsewhere? Ask the owner or H1, and log it here.
- Commit often in your own worktree /workspace/mod-port/iaf-wt-hN on branch iaf-hN.
- Never push or publish. Never use runClient.
- Kill processes by PID only (no pkill/killall).
- Only build with ≥ 4 GB free (`free -g` available column), only via ars-compile.sh, with your own lock and cache.
- Keep the LGPL/licence notices and file headers of upstream and library code.
- H1 lands first; then everyone runs `git merge iaf-integration` into their branch before continuing.

## Worktrees / locks / caches
| Owner | Worktree | Branch | Lock | Cache | Log |
|---|---|---|---|---|---|
| H1 | /workspace/mod-port/iaf-wt-h1 | iaf-h1 | /tmp/port-iaf-h1.lock | /tmp/pcache-iaf-h1 | /tmp/iaf-h1.log |
| H2 | /workspace/mod-port/iaf-wt-h2 | iaf-h2 | /tmp/port-iaf-h2.lock | /tmp/pcache-iaf-h2 | /tmp/iaf-h2.log |
| H3 | /workspace/mod-port/iaf-wt-h3 | iaf-h3 | /tmp/port-iaf-h3.lock | /tmp/pcache-iaf-h3 | /tmp/iaf-h3.log |
| H4 | /workspace/mod-port/iaf-wt-h4 | iaf-h4 | /tmp/port-iaf-h4.lock | /tmp/pcache-iaf-h4 | /tmp/iaf-h4.log |
| H5 | /workspace/mod-port/iaf-wt-h5 | iaf-h5 | /tmp/port-iaf-h5.lock | /tmp/pcache-iaf-h5 | /tmp/iaf-h5.log |
| H6 | /workspace/mod-port/iaf-wt-h6 | iaf-h6 | /tmp/port-iaf-h6.lock | /tmp/pcache-iaf-h6 | /tmp/iaf-h6.log |

## Compile (example for H1; substitute N)
```
[ $(free -g | awk '/Mem/{print $7}') -ge 4 ] && /workspace/mod-port/ars-compile.sh /workspace/mod-port/iaf-wt-h1 /tmp/port-iaf-h1.lock /tmp/pcache-iaf-h1 /tmp/iaf-h1.log compileJava
```
## Errors per owner
```
python3 tools/owner_errors.py /tmp/iaf-hN.log          # counts per owner
python3 tools/owner_errors.py /tmp/iaf-hN.log H3       # error lines for H3
```
Codemods (from origins-legacy/port/tools): codemod.py, fiximports.py, addimport.py, sigmod.py, linefix.py, gui_codemod.py, mixin_audit.py.

## Cross-owner notes / API requests

### H1 core/registry API (iaf-h1, MC 26.3 changes)
- Lightning list: `ClientEvents.LIGHTNINGS` is `CopyOnWriteArrayList<com.mojang.datafixers.util.Pair<Vec3,Vec3>>` and `LightningBoltS2CPayload(List<Pair<Vec3,Vec3>>)` (per H4). **H5** LevelRendererMixin: use Pair (getFirst/getSecond). `util.Tuple<A,B>` shim (getA/getB) also exists for other removed-Tuple uses.
- Menus: `IafMenus` is server-safe (MenuTypes only). Screens are bound in client-only `registry.client.IafMenuScreens` (`@EventBusSubscriber(Dist.CLIENT)`, RegisterMenuScreensEvent).
- `IafProcessors`: registry is `DeferredRegister<MapCodec<? extends StructureProcessor>>` (Registries.STRUCTURE_PROCESSOR). `IafProcessors.X.get()` returns the `MapCodec`. **H5**: processors `implements StructureProcessor` (now an interface) and replace `getType()` with `public MapCodec<? extends StructureProcessor> codec() { return IafProcessors.X.get(); }` (or CODEC).
- `IafFeatures`: registry is `DeferredRegister<MapCodec<? extends Feature>>` (Registries.FEATURE_TYPE). ConfiguredFeature is gone: `IafFeatures.DREADWOOD/DREADWOOD_LARGE` are now `ResourceKey<Feature>` (Registries.FEATURE). **H5**: each world/feature class `implements Feature` (codec() + `place(WorldGenLevel, ChunkGenerator, RandomSource, BlockPos)`) with `public static final MapCodec<X> CODEC`; DragonSkeletonSpawnFeature exposes `FIRE_CODEC`, `ICE_CODEC`, `LIGHTNING_CODEC` (e.g. `MapCodec.unit(() -> new DragonSkeletonSpawnFeature(IafEntities.FIRE_DRAGON))`, resolve the EntityType lazily). Verified: with these shapes IafFeatures/IafProcessors compile. Worldgen JSON (configured_feature → feature) also needs H5 review.
- **H4** DreadwoodSaplingBlock: TreeGrower ctor is now `(name, WeightedList<ResourceKey<Feature>> trees, megaTrees, flowerTrees, @Nullable ResourceKey<Feature> shortest)`.
- Brewing: `RegisterBrewingRecipesEvent` is gone; brewing is data recipes (`"type": "minecraft:brewing"`, input/reagent/output, see vanilla data/minecraft/recipe/brewing). Removed the event from IceAndFire.java. **H5**: add data/iceandfire/recipe/brewing/ JSONs for potion/splash/lingering: water + iceandfire:shiny_scales → minecraft:water_breathing; awkward + iceandfire:gorgon_head → iceandfire:depetrification.
- Loot functions: `LootItemConditionalFunction(Optional<Holder<LootItemCondition>>)`, `LootContext#getOptional(ContextKey)`.
- Vanilla removals used by H1 and probably elsewhere: ShovelItem/AxeItem/HoeItem → `new Item(new Item.Properties().shovel|axe|hoe|pickaxe|sword(material, dmg, speed))`; `PushReaction.DESTROY` → `POPPED`; `Blocks.WHITE_WOOL` → `Blocks.WOOL.white()`; `PlayerInteractEvent.EntityInteractSpecific` → `EntityInteract` (getLocation()); GLFW keys → `InputConstants.KEY_*`.
- (Cleared after merging iaf-integration @fed53929.) Former cascade errors from **H4**: IafItems 272-299 (ActivePostHitShovel/Axe/HoeItem extend removed ShovelItem/AxeItem/HoeItem; make them `extends Item` and pass `props.shovel/axe/hoe(...)`), IafBlocks 53/60/69 (CharedPathBlock extends removed DirtPathBlock).
- H6 (iaf-h6): render/screen/client compile clean (0 H6 errors). Nothing stubbed, no features dropped.
  - LightningRenderer.render(float, PoseStack, MultiBufferSource) removed (MultiBufferSource is gone in 26.3); use submit(...), already called from ClientEvents. → H5: mixin/LevelRendererMixin.java (not listed in iceandfire.mixins.json, dead upstream too) still calls render(); delete it or switch to submit.
  - ChainRenderer / FrozenStateRenderer now take a SubmitNodeCollector (submitCustomGeometry). They have no callers upstream either.
  - SirenShaderRenderHelper uses GameRenderer.set/clear/spectatedEntityPostEffect (26.3 replaced setPostEffect/currentPostEffect).
  - Dedicated-server risk (@OnlyIn no longer strips): client classes referenced from common code in item/CockatriceScepterItem:135 (ClientLevel), item/SummoningCrystalItem:68 (I18n), item/BestiaryItem:53 (Minecraft), entity/util/ChainBuffer:125 + ReversedBuffer:134 (Minecraft), registry/IafMenus (screens + RegisterMenuScreensEvent). Owners: H4/H3/H1.
- [H4→H1] `net.minecraft.util.Tuple` is gone in 26.3. H4 `LightningMultihitAbility` now builds `List<com.mojang.datafixers.util.Pair<Vec3,Vec3>>`; please change `network/payload/LightningBoltS2CPayload` to `List<Pair<Vec3,Vec3>>` (codec: `Pair::getFirst/getSecond`, `Pair::of`). Also used in ClientEvents (H1) and LevelRendererMixin (H5).
- [H4 info, all] 26.3 API notes found while porting: `EntityType.X` → `EntityTypes.X`; `swing(hand)` → `swing(hand, SwingAnimation.DEFAULT, false)`; `knockback(p,x,z)` → `knockback(p,x,z, DamageSource, damage)`; `attackAnim` → `getSwingAnimation(pt)`, `swingTime` gone (use `isSwinging()`/NeoForge `onEntitySwing`); `BlockState.blocksMotion()` → `isSolid()`; block `codec()`/`simpleCodec` removed; `PushReaction.DESTROY` → `POPPED`; `DirtPathBlock` → `PathBlock(Blocks.DIRT, props)`; `AxeItem/HoeItem/ShovelItem` → `Item` + `Properties.axe/hoe/shovel(...)`; `EntityType.byString` → `BuiltInRegistries.ENTITY_TYPE.getOptional(Identifier.tryParse(..))`; `InputConstants.isKeyDown(int)`; `Block.playerDestroy(ServerLevel, ServerPlayer, …)`.
- [H4 info] Client refs in H4 items moved to `item/client/ItemClientHelper` (only called behind `isClientSide()`); SummoningCrystal tooltip uses Component.translatable (no I18n).
  - H6 fixed: render/model/IFChainBuffer (partial ticks via client/ClientPartialTicks behind FMLEnvironment.getDist().isClient(), 1.0F on server); screen/menu Dragon/Hippocampus/HippogryphMenu resolve the entity via playerInventory.player.level() instead of Minecraft. Remaining note: IFChainBuffer method signatures still take Uranus BasicModelPart (uses blaze3d PoseStack), only touched from model code, never executed server-side.
- Remaining H1 errors (30, IafFeatures 27-34 / IafProcessors 23-25) are pure **H5** cascades: they vanish once world/feature + world/processor follow the contract above (checked by compiling with throwaway stubs, then reverted).
- [H5] world/feature, world/processor, structures, siren biome modifier and TitleScreenMixin compile (0 H5 errors; H1's IafFeatures/IafProcessors cascade is gone). Deleted mixin/LevelRendererMixin.java (dead). Brewing: data/iceandfire/recipe/brewing/ (6 JSONs, potion/splash/lingering, same as upstream addMix). Data converted with /workspace/h5/iaf/convert.py; /workspace/h5/iaf/check.py is CLEAN.
  26.3 API notes: StructureProcessor.processBlock(level, pos, pivot, BlockPos originalPos, StructureBlockInfo current, settings) (original info is now just a BlockPos); Structure#getLowestYIn5by5BoxOffset7Blocks -> getLowestYIn5by5Box(ctx, chunkPos.getBlockX(7), chunkPos.getBlockZ(7), rot); GenerationContext.biomeResolver().getNoiseBiome(x,y,z); BiomeModifier.modify(RegistryAccess, ...); MobSpawnSettings.Builder.addSpawn(type, category, weight, IntProvider); SpawnerData(type, IntProvider).
  Upstream data fixes: enchant_with_levels "treasure" (1.20) -> options #minecraft:on_random_loot; damage predicate is_projectile -> tags; rule processor output_state Name -> id.

- [H3, H2-owned files, on request] HippogryphEntity: `Items.RED_DYE/LIGHT_GRAY_DYE` -> `Items.DYE.red()/lightGray()` (26.3 ColorCollection), `hurtMarked` -> `syncVelocity`. DragonEggEntity: `EntityType.LIGHTNING_BOLT` -> `EntityTypes.LIGHTNING_BOLT`. OWNERS unchanged (still H2).
- [H4 integrator → H5, trivial boot fix, logged] mixin/EntityIdAccessor: 26.3 moved `ENTITY_COUNTER` from `Entity` to `ServerLevel` (`ServerLevel.getNextEntityId`); the accessor on Entity crashed the dedicated server at mixin apply (InvalidAccessorException). Target changed to `@Mixin(ServerLevel.class)`; accessor name/signature unchanged, so MultipartPartEntity.reserveParentId needs no change. H5 please review.
- [Integrator → H5, BOOT BLOCKER, not fixed] Dedicated server stops at "Failed to load datapacks" (log /tmp/iaf-servertest.log). 30 failing elements in src/main/resources/data/iceandfire:
  - advancement (15): entity conditions like `{"entity":{"type":"iceandfire:cyclops"}}` are read as loot conditions in 26.3 → use `{"type":"minecraft:entity_properties","entity":"this","predicate":{"minecraft:entity_type":"iceandfire:cyclops"}}` (vanilla kill_a_mob style); damage `direct_entity` → `{"minecraft:entity_type":"iceandfire:tide_trident"}`.
  - loot_table (14, chest/*): number ranges `{"min":1,"max":4}` need `"type":"minecraft:uniform"`.
  - recipe (root, 18 unbound ids): recipe advancements unlock ids that don't exist — `armor_amethyst_*` (files are `armor_amythest_*`), `dragonscale(s)_amethyst_*`, `dragonarmor_dragonsteel_{fire,ice,lightning}_{body,head,neck,tail}` (no recipe files). Fix the ids or drop those advancements.
- [Integrator → H5, BOOT BLOCKER after 8edc3998, not fixed] Server still stops at "Failed to load datapacks" (/tmp/iaf-servertest.log), 2 items left:
  - advancement/iceandfire/use_tide_trident.json: damage tag needs the `#` prefix in 26.3: `"id": "#minecraft:is_projectile"` (vanilla style `"#minecraft:mace_smash"`).
  - 18 recipe advancements under advancement/recipes/**: only `rewards.recipes` was renamed; the criterion `has_the_recipe.conditions.recipes` still names the old ids (`iceandfire:armor_amethyst_*`, `dragonscale(s)_amethyst_*`, `dragonarmor_dragonsteel_*`). Change them to the real ids too (`armor_amythest_*`, `dragonscale(s)_amythest_*`, `dragonarmor_dragon_steel_*`).
- [H5 side-safety audit, iaf-integration ced2a510] No @OnlyIn left (only a comment). Client subscribers all carry Dist.CLIENT (IceAndFireClient, ClientEvents, FlightClientEvents, IafMenuScreens); untagged ones (IceAndFire, ServerEvents, NetworkManager, IafEntities, IafAttachments, Siren*, DragonForgeRecipeSync) only touch common classes. The common mixin list is server-safe; client mixins sit under "client". Guarded/acceptable: ChainBuffer/ReversedBuffer/IFChainBuffer (FMLEnvironment dist check), AmphithereEntity IFChainBuffer (only built when isClientSide), BestiaryItem/CockatriceScepterItem -> ItemClientHelper (isClientSide), DragonColor -> DragonTextureProvider (no client imports).
  Fixed (H5): NetworkManager used ClientNetworkHandlers::x method refs, which link the client handler class at registration on a dedicated server too -> lambdas. Added network/ClientSend (client-only sendToServer).
  DragonBaseEntity (H2): removed its ClientPacketDistributor import; line ~1925 now calls ClientSend.toServer (one line, H2 please keep it).
