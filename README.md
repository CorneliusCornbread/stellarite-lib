# Stellarite Lib

Stellarite Lib is a small NeoForge mod skeleton for Minecraft 1.21.1. It provides the first vertical slice for a future progression/worldgen layer: one moon-themed test ore, item/block registration, tags, recipes, and data-driven ore generation.

## Target environment

- Minecraft: 1.21.1
- Loader: NeoForge 21.1.x
- Java: 21
- Build: Gradle with ModDevGradle

## Build instructions

```bash
./gradlew build
```

If Gradle is not installed globally and the wrapper is not present yet, install Gradle 8.x or run the project from an IDE with Gradle support.

## Development runs

Run the client:

```bash
./gradlew runClient
```

Run the server:

```bash
./gradlew runServer
```

Run data generation:

```bash
./gradlew runData
```

Generated resources are configured to appear under `src/generated/resources`. Hand-authored resources live under `src/main/resources`.

## Current content

The skeleton currently registers:

- `stellarite_lib:moon_test_ore`
- `stellarite_lib:raw_moon_test_ore`
- `stellarite_lib:moon_test_ore_ingot`
- `stellarite_lib:raw_moon_test_ore_block`
- `stellarite_lib:moon_test_ore_block`

Recipes include smelting, blasting, 9-item storage blocks, and reverse unpacking recipes.

## Architecture

- `StellariteLib` is the mod entry point and owns the central `MOD_ID` constant.
- `registry/ModBlocks` contains block `DeferredRegister` entries.
- `registry/ModItems` contains item/block-item `DeferredRegister` entries and creative-tab population.
- Worldgen is data-driven through JSON resources.

### ConfiguredFeature, PlacedFeature, and BiomeModifier

A `ConfiguredFeature` describes *what* generates: in this case, an ore vein that replaces vanilla stone/deepslate ore-replaceable blocks with `moon_test_ore`.

A `PlacedFeature` describes *how often and where* that configured feature is attempted, including count per chunk, horizontal spread, and height range.

A NeoForge `BiomeModifier` describes *which biomes receive* the placed feature and injects it into the `underground_ores` generation step. This keeps ore generation data-driven rather than hard-coding chunk-generation hooks.

## Adding another ore

1. Add block entries in `ModBlocks`.
2. Add item/block-item entries in `ModItems` and decide which creative tabs should show them.
3. Add blockstates, block models, item models, textures, and translations.
4. Add compatibility tags under `data/c/tags` and tool tags under `data/minecraft/tags`.
5. Add a configured feature, placed feature, and biome modifier JSON if the ore should generate.
6. Add smelting/blasting/storage recipes under `data/<modid>/recipe`.

## Adding another recipe

Add a new JSON recipe under `src/main/resources/data/stellarite_lib/recipe`. Keep pack-specific recipe removal or broad automation changes in KubeJS; use this mod for reusable base content and harder-to-express systems.

## Adding a new biome modifier

Add a JSON file under `src/main/resources/data/stellarite_lib/neoforge/biome_modifier`. Prefer biome tags over enumerating individual biomes, and use `underground_ores` for ore features.
