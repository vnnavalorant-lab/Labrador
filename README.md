# Labrador Puppy – Forge 1.20.1

Animated Labrador puppy entity (Idle + Walk) using the original 3D mesh (not cubes).

## Build / run
Requires **JDK 17**. Forge **1.20.1-47.3.0** (ForgeGradle 6, official Mojang mappings).

**GitHub Actions:** `.github/workflows/build.yml` builds on every push and uploads the jar as an artifact
(works with or without the Gradle wrapper).

**Local:** this project has no Gradle wrapper jar. Either
* open the folder in IntelliJ IDEA (it imports the Gradle project), or
* run `gradle wrapper --gradle-version 8.8` once (needs a local Gradle) and commit the wrapper, or
* copy `src/`, `tools/` and `gradle.properties` into a fresh Forge 1.20.1 MDK.

Then: `./gradlew runClient` (test) or `./gradlew build` (jar in `build/libs/`).

## In game
* `/summon labrador_puppy:puppy`
* Creative inventory -> Spawn Eggs -> *Labrador Puppy Spawn Egg*
* Spawns naturally in plains / sunflower plains / meadow / forest. On Forge this is a data-driven
  biome modifier: edit `data/labrador_puppy/forge/biome_modifier/puppy_spawns.json`.
* Right-click with a **bone**: heals 2 hearts and shows hearts.

## Fabric -> Forge port notes
Mesh, rig, pose math and texture are unchanged. Only the loader glue changed:
* Yarn mappings -> official Mojang mappings (`EntityModel.setupAnim/renderToBuffer`, `Animal`, ...).
* `ModInitializer` / `ClientModInitializer` -> `@Mod` class + mod-bus events (`ModEvents`, `LabradorPuppyClient`).
* Registries via `DeferredRegister`; spawn egg is a `ForgeSpawnEggItem`.
* `fabric.mod.json` -> `META-INF/mods.toml` (+ `pack.mcmeta`).
* `BiomeModifications.addSpawn` -> `forge:add_spawns` biome modifier JSON.

## How the animation works
`PuppyMesh` loads `assets/labrador_puppy/mesh/puppy_mesh.bin` (964 vertices, 14 bones, skin weights).
`PuppyPose` computes the pose every frame from vanilla values (`limbAngle`, `limbDistance`, age,
head yaw/pitch); `PuppyMesh.skin` deforms the vertices on the CPU and `PuppyModel` draws them.
* **Idle** (limbDistance≈0): breathing, head sway, tail wag, ear twitches.
* **Walk** (limbDistance≈1): diagonal trot, knee flex, body bob/roll, harder wag, bouncing ears.
They blend automatically. Tune the constants at the top of `PuppyPose.java`.

## Regenerating the rig
`tools/` has the Python scripts (numpy + Pillow + matplotlib). Edit bone pivots / weights in
`tools/rig.py`, then `cd tools && python3 build_assets.py` (re-installs mesh + texture) and
`python3 preview.py walk 24` / `idle 28` for GIF previews. Bone order in `rig.py` must match the
constants in `PuppyPose.java`.

See `docs/preview_walk.gif` and `docs/preview_idle.gif`.
