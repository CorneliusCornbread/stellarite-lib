# Stellaris Progression Mod

## NeoForge 1.21.1 — Skeleton Requirements & Design Document

### 1. Project Goal

Create a small NeoForge 1.21.1 mod that serves as the custom progression/worldgen layer for the use in Minecraft modpacks.

The mod should initially provide:

1. Custom ore/item/block registration.
2. Custom ore world generation.
3. Custom crafting recipes.
4. Data generation for recipes and worldgen data where practical.
5. A clean architecture that can later support:

   * Planet-specific resources.
   * Rocket progression.
   * Technology-tier progression.
   * Integration with Create.
   * Integration with The Factory Must Grow.
   * Integration with Mekanism.
   * Integration with Stellaris.
   * Integration with Applied Energistics 2.
   * Integration with ComputerCraft/CC:Tweaked.

The initial implementation should be a **skeleton**, not a complete implementation of the final progression system.

Do not add unnecessary gameplay mechanics, GUIs, machines, capabilities, networking, or custom entities.

---

# 2. Target Environment

Target exactly:

* Minecraft: **1.21.1**
* Mod loader: **NeoForge**
* Java version: use the version required by the NeoForge 1.21.1 MDK.
* Build system: Gradle using the standard NeoForge MDK structure.

The mod must be usable as a normal mod in a NeoForge 1.21.1 modpack.

The mod should not depend on KubeJS.

KubeJS will remain responsible for pack-specific recipe modifications and automation where appropriate.

The custom mod should instead provide functionality that is difficult or impossible to conveniently express through KubeJS.

---

# 3. Proposed Mod Identity

Use placeholders where necessary:

```text
Mod name: Muffed Mining Core
Mod ID: muffed_mining
Package: com.example.muffedmining
```

The package name should be easy to change later.

Do not hard-code the package name throughout the project. Put the mod ID in a central constant.

---

# 4. Design Philosophy

The mod should be:

* Data-driven.
* Modular.
* Easy to extend.
* Easy to debug.
* Compatible with other mods.
* Conservative about injecting world generation.
* Explicit about dependencies.
* Suitable for a large modpack.

Prefer NeoForge's standard registration and data-generation APIs over custom runtime manipulation.

NeoForge's `DeferredRegister` should be used for normal registered objects.

World generation should preferentially use Minecraft/NeoForge's data-driven worldgen system and biome modifiers rather than injecting generation directly into chunk generation code.

NeoForge's biome modifier system supports adding `PlacedFeature`s to selected biomes, including ore features, and uses the `underground_ores` generation step for ores.

---

# 5. Initial Content

The initial skeleton should contain a very small set of test content.

Do NOT implement every planned resource yet.

Create one example ore that proves the entire pipeline works.

Suggested example:

```text
muffed_mining:test_ore
muffed_mining:raw_test_ore
muffed_mining:test_ore_block
```

The exact name may be changed to something more appropriate if needed.

The test ore should:

* Exist as a block.
* Have an item form.
* Have a raw material item.
* Have a block form if appropriate.
* Generate naturally in the Overworld.
* Have a smelting recipe.
* Have a blasting recipe.
* Have a 9-item storage-block recipe.
* Have a reverse 1-block-to-9-items recipe.

The purpose is to prove that registration, tags, recipes, worldgen, and datagen all function correctly.

---

# 6. Registration Architecture

Organize registration into separate classes.

Suggested structure:

```text
src/main/java/com/example/muffedmining/

    MuffedMining.java

    registry/
        ModBlocks.java
        ModItems.java

    worldgen/
        ModConfiguredFeatures.java
        ModPlacedFeatures.java
        ModBiomeModifiers.java

    data/
        ModDataGenerators.java
        ModRecipeProvider.java
        ModTagProvider.java

    recipe/
        ...
```

Do not create classes for systems that are not currently implemented.

Use `DeferredRegister` for blocks and items.

Example conceptual structure:

```java
public static final DeferredRegister.Blocks BLOCKS =
    DeferredRegister.createBlocks(MOD_ID);

public static final DeferredRegister.Items ITEMS =
    DeferredRegister.createItems(MOD_ID);
```

Use the appropriate NeoForge 1.21.1 API rather than blindly copying examples from newer or older Minecraft versions.

---

# 7. Ore Registration

The ore should have:

### Block

Properties should be similar to an appropriate vanilla ore.

The following should be configurable/easy to modify:

* Hardness.
* Blast resistance.
* Required tool.
* Tool tier.
* Sound.
* Map color.
* Light level if ever desired.

Do not make the ore unnecessarily strong.

### Raw Material

Example:

```text
raw_test_ore
```

### Ore Block

Example:

```text
test_ore_block
```

### Tags

Provide appropriate tags for compatibility.

At minimum investigate whether the following tags are appropriate for the implementation:

```text
c:ores
c:raw_materials
c:storage_blocks
c:ores/test_ore
c:raw_materials/test_ore
minecraft:mineable/pickaxe
```

Do not invent tags that are not actually used by the relevant ecosystem.

Where possible, use common convention tags for compatibility with other mods.

---

# 8. World Generation

Implement ore generation using the modern worldgen system.

The intended architecture is:

```text
ConfiguredFeature
        |
        v
PlacedFeature
        |
        v
NeoForge Biome Modifier
        |
        v
Selected biomes
```

Do not directly inject ore generation into chunk generation.

NeoForge documents `PlacedFeature` injection through biome modifiers specifically for this purpose.

## Configured Feature

Create an ore `ConfiguredFeature`.

The feature should use an ore feature configuration with:

* Target block.
* Replacement target.
* Vein size.
* Other standard ore feature parameters.

Initially target stone/deepslate appropriately.

## Placed Feature

Create a placed feature controlling:

* Count.
* Height distribution.
* Spread.
* Placement height range.

Use a simple distribution initially.

Example conceptual configuration:

```text
vein size: 8
veins per chunk: 6
minimum Y: -32
maximum Y: 64
```

These numbers are placeholders and should be easy to change.

Do not attempt to perfectly balance the ore yet.

## Biome Modifier

Create a NeoForge biome modifier that adds the placed feature to Overworld biomes.

Initially use an appropriate broad Overworld biome tag rather than enumerating every vanilla biome.

The generation step should be:

```text
underground_ores
```

NeoForge specifically identifies `underground_ores` as the generation step used for ores and veins.

---

# 9. Worldgen Data Generation

Where practical, use `DatapackBuiltinEntriesProvider` and the relevant data-generation APIs to generate worldgen registry data.

The generated output should correspond to the normal datapack structure.

For example:

```text
data/muffed_mining/worldgen/configured_feature/...
data/muffed_mining/worldgen/placed_feature/...
data/muffed_mining/neoforge/biome_modifier/...
```

The exact paths must follow the NeoForge 1.21.1 API.

NeoForge's biome modifier documentation confirms that biome modifiers can be supplied as data under:

```text
data/<modid>/neoforge/biome_modifier/
```

and can also be generated through `DatapackBuiltinEntriesProvider`.

---

# 10. Recipes

The initial test ore should have normal Minecraft recipes.

Generate:

### Smelting

```text
raw_test_ore -> test_ore
```

### Blasting

```text
raw_test_ore -> test_ore
```

### Storage Block

```text
9 x test_ore -> test_ore_block
```

### Reverse Storage Block

```text
test_ore_block -> 9 x test_ore
```

### Raw Storage Block

```text
9 x raw_test_ore -> raw_test_ore_block
```

and the reverse.

Recipes should be generated through a `RecipeProvider`.

NeoForge 1.21.1 supports recipe datagen through `RecipeProvider#buildRecipes(RecipeOutput)`.

Do not implement custom recipe serializers unless the test content actually requires them.

---

# 11. Recipe Design for Future Progression

The architecture should allow future custom recipes to be added without changing the registration architecture.

Eventually recipes will be divided into technology tiers.

Planned progression:

```text
TIER 1 — CREATE
        |
        v
TIER 2 — TFMG
        |
        v
TIER 3 — MEKANISM
        |
        v
TIER 4 — STELLARIS
```

The mod should eventually be able to provide custom recipes that bridge these mods.

However, do not implement those recipes yet.

The first skeleton should only establish the infrastructure.

---

# 12. Planned Resource Progression

This is a design target for future implementation.

The pack will eventually use planetary resources as progression gates.

Conceptually:

```text
Create
  |
  | Tier 1 rocket
  v
Moon resources
  |
  v
TFMG
  |
  | Tier 2 rocket
  v
Mars / other planetary resources
  |
  v
Mekanism
  |
  | Tier 3 rocket
  v
Outer planets / exotic resources
  |
  v
Late Mekanism
  |
  | Final rocket
  v
Stellaris endgame
```

The final resource distribution has NOT yet been finalized.

Do not hard-code this progression into the initial skeleton.

---

# 13. Future Planet Support

The architecture should make it possible to eventually define ore generation separately for different dimensions.

Conceptually:

```text
Resource
    |
    +-- Overworld generation
    |
    +-- Moon generation
    |
    +-- Mars generation
    |
    +-- Asteroid generation
    |
    +-- Other planetary dimensions
```

Do not assume that all dimensions are vanilla dimensions.

Some will be provided by Stellaris or another space mod.

The final implementation should use dimension/biome tags or appropriately scoped biome modifiers wherever possible.

Do not create custom dimension-generation code unless absolutely necessary.

---

# 14. Dependency Handling

The mod should NOT require Create, TFMG, Mekanism, AE2, or Stellaris merely to launch.

Instead, design future integrations as optional.

Potential future integrations:

```text
Create integration
TFMG integration
Mekanism integration
AE2 integration
CC:Tweaked integration
Stellaris integration
```

Each integration should eventually have its own package/module or clearly separated classes.

For example:

```text
integration/
    create/
    tfmg/
    mekanism/
    ae2/
    computercraft/
    stellaris/
```

The initial skeleton may leave these packages empty or omit them entirely.

---

# 15. Optional Dependency Strategy

When integration is eventually implemented:

* Do not reference optional mod classes from common code.
* Use NeoForge's dependency information appropriately.
* Ensure the base mod can launch without optional integrations.
* Only initialize integration code when the relevant mod is loaded.

Do not add optional dependencies simply for the skeleton.

---

# 16. Data Generation

Set up server-side data generation.

At minimum implement:

```text
RecipeProvider
BlockTagProvider
ItemTagProvider
Worldgen/datapack registry provider
BiomeModifier generation
```

Only add providers that actually generate useful data.

The generated data should be reproducible.

Running the Gradle data-generation task twice should produce equivalent output.

---

# 17. Source/Data Separation

Keep generated data separate from handwritten data.

Suggested structure:

```text
src/main/resources/
    META-INF/
    assets/
    data/

src/main/java/
    ...
```

If datagen generates resources into the normal generated resources directory, configure Gradle appropriately rather than manually copying generated files into source resources.

The repository should make it clear which files are:

* Hand-authored.
* Generated.
* Build artifacts.

---

# 18. Logging

Add a small logger using the standard NeoForge/Minecraft logging infrastructure.

Use a mod-specific logger.

Useful messages during development:

```text
Muffed Mining initialized
Registered custom ores
Registered worldgen features
Registered biome modifiers
Registered recipe providers
```

Do not spam the log every time an ore generates.

---

# 19. Testing Requirements

The skeleton is considered complete only if it can:

1. Build successfully.
2. Launch a NeoForge 1.21.1 development client.
3. Launch a NeoForge 1.21.1 development server if practical.
4. Register the test ore.
5. Display the test ore in the creative inventory.
6. Place and break the ore.
7. Generate the ore in a newly created Overworld.
8. Smelt the raw ore.
9. Blast the raw ore.
10. Craft and unpack the storage block.
11. Run data generation successfully.
12. Load the generated worldgen data without errors.

A fresh world should be used for worldgen testing.

Existing chunks should not be considered evidence that worldgen is working.

---

# 20. Debugging Requirements

Make the code easy to debug.

Avoid:

* Reflection.
* Mixins unless absolutely necessary.
* Runtime modification of worldgen.
* Access transformers.
* Coremod-style behavior.
* Global event handlers where a data-driven solution exists.

Prefer:

* Registries.
* Datapack registries.
* Biome modifiers.
* Tags.
* Datagen.
* Standard NeoForge events.

---

# 21. Documentation

Create:

```text
README.md
```

The README should explain:

* What the mod is.
* Target Minecraft/NeoForge version.
* How to build it.
* How to run the development client.
* How to run data generation.
* Where generated data appears.
* Basic architecture.
* How to add another ore.
* How to add another recipe.
* How to add a new biome modifier.

Include a short explanation of the relationship between:

```text
ConfiguredFeature
PlacedFeature
BiomeModifier
```

This is particularly important because this is the first NeoForge 1.21.1 worldgen project for the developer.

---

# 22. Developer-Facing Documentation

Add comments where NeoForge's architecture is non-obvious.

Do NOT comment trivial Java.

Good:

```java
// PlacedFeatures control how frequently and at what heights the
// configured ore feature is placed. The biome modifier then injects
// this placed feature into the target biomes.
```

Bad:

```java
// Register the block.
register(block);
```

The goal is to explain NeoForge concepts rather than explain Java syntax.

---

# 23. Important NeoForge 1.21.1 Constraint

Do not use examples from NeoForge 1.20.x, 1.21.5+, or another Minecraft version without verifying that the API exists in 1.21.1.

When an API differs from current NeoForge documentation:

1. Prefer the NeoForge 1.21.1 documentation.
2. Inspect the actual dependency/API available in the project.
3. Adapt examples to the installed version.
4. Do not blindly copy current documentation.

In particular, verify:

* Registry APIs.
* Data generation APIs.
* Worldgen registry APIs.
* Biome modifier APIs.
* Recipe provider APIs.
* Gradle/MDK configuration.

---

# 24. Initial Deliverable

The first implementation should produce a repository containing:

```text
Muffed Mining Core
|
+-- NeoForge 1.21.1 project
|
+-- Mod initialization
|
+-- Block registration
|
+-- Item registration
|
+-- Tags
|
+-- Test ore
|
+-- ConfiguredFeature
|
+-- PlacedFeature
|
+-- Biome Modifier
|
+-- Recipe datagen
|
+-- Worldgen datagen
|
+-- README
|
+-- Gradle build configuration
```

The mod should compile and run before any larger progression system is implemented.

---

# 25. Future Expansion

Once this skeleton works, the next development phases will likely be:

### Phase 2 — Real Resources

Replace the test ore with actual progression resources such as:

* Aluminum
* Titanium
* Platinum
* Iridium
* Other custom planetary resources

### Phase 3 — Existing Mod Resources

Determine which existing mod resources should be:

* Removed from Overworld generation.
* Made rarer.
* Restricted to certain planets.
* Added to new planetary dimensions.

### Phase 4 — Rocket Progression

Implement progression around multiple rocket tiers.

Rockets should become available progressively:

```text
Tier 1 rocket → Create
Tier 2 rocket → TFMG
Tier 3 rocket → Mekanism
Tier 4 rocket → late Mekanism / Stellaris
```

However, rocket progression should also require resources obtained from previously visited planets.

Therefore progression should form a loop:

```text
Technology
    ↓
Rocket
    ↓
Planet
    ↓
New Resource
    ↓
Higher Technology
    ↓
Better Rocket
```

### Phase 5 — Technology Integration

Add integration recipes between:

* Create
* TFMG
* Mekanism
* AE2
* CC:Tweaked
* Stellaris

### Phase 6 — Final Pack Balancing

Only after the mechanics work should actual quantities, spawn rates, recipe costs, and progression gates be balanced.

---

# 26. Codex Instructions

When implementing this specification:

1. First inspect the repository and determine whether an existing NeoForge project exists.
2. If an existing project exists, preserve its current structure unless it conflicts with this specification.
3. If no project exists, create a standard NeoForge 1.21.1 MDK project.
4. Verify the exact NeoForge/Minecraft API versions before writing implementation code.
5. Implement the smallest complete vertical slice:

   * One block.
   * One item.
   * One raw item.
   * One storage block.
   * One configured ore feature.
   * One placed ore feature.
   * One biome modifier.
   * Basic recipes.
   * Datagen.
6. Run the build.
7. Run data generation.
8. Fix compilation/data-generation errors.
9. If possible, launch the development client and verify the mod loads.
10. Do not implement future progression systems yet.
11. Do not add KubeJS.
12. Do not add custom machines.
13. Do not add rocket mechanics.
14. Do not add custom dimensions.
15. Do not implement final resource balancing.
16. Leave the project in a clean state suitable for continuing development.

When uncertain about an API, inspect the actual NeoForge 1.21.1 dependency/source or official 1.21.1 documentation rather than assuming the API from another Minecraft version.

The final result should be a small, understandable NeoForge mod that proves the complete registration → datagen → worldgen → recipe pipeline.
