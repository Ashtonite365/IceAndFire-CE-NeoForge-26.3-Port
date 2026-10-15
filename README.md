# Ice and Fire CE - NeoForge 26.3 Port

> **Unofficial** NeoForge 26.3 (Minecraft 26.3) port. Not affiliated with or endorsed by the original authors.

## Credits

This is an **unofficial port** of **[Ice And Fire: Community Edition](https://www.curseforge.com/minecraft/mc-mods/iceandfire-ce)** by **IAFEnvoy** and the CE contributors (xiaowu, uoay, aelux and others).

- Original mod: https://www.curseforge.com/minecraft/mc-mods/iceandfire-ce
- Original source: https://github.com/IAFEnvoy/IceAndFire-CE
- Based on the original **Ice and Fire** by Alexthe666 / AlexModGuy: https://www.curseforge.com/minecraft/mc-mods/ice-and-fire-dragons
- Dragon Seeker item/textures come from **Ice and Fire: Dragonseeker** by syrikalis (MIT): https://www.curseforge.com/minecraft/mc-mods/ice-and-fire-dragonseeker
- NeoForge 26.3 port by Ashtonite365

## Licence

Ice And Fire: Community Edition is licensed under the **GNU LGPL-3.0-or-later**, (c) 2024 IAFEnvoy. This port is a modified version and is released under the same LGPL-3.0 licence. The licence text is in the jar (`LICENSE`, plus `LICENSE_dragonseeker` for the MIT notice).


## How this port differs

- Ported from **Ice And Fire CE 2.2-beta.4 (NeoForge, Minecraft 26.1.2)** to **NeoForge 26.3.0.76+ for Minecraft 26.3**.
- **Unofficial and beta:** expect bugs and back up your worlds.
- The mod id is changed to **`iceandfire_port263`** so it doesn't clash with the official `iceandfire`. Content still uses the `iceandfire:` namespace, so datapacks and resource packs keep working. Don't install it alongside another Ice and Fire build.
- You need the **Jupiter NeoForge 26.3 Port** and the **Uranus NeoForge 26.3 Port** (`jupiter_port263` / `uranus_port263`). The official Jupiter/Uranus builds won't work.
- Curios support (optional) still works. The original's other integrations (JEI, Jade, EMI, Ponder, ProjectE, Ars Nouveau) are **disabled** in this port.

## Not affiliated

This port is **not affiliated with or endorsed by** IAFEnvoy, the Ice and Fire CE team, or Alexthe666. Please don't report bugs from this port to them.

## Building

Build outputs and dependency jars are not committed. Put the Uranus and Jupiter NeoForge 26.3 port jars in `libs/` (`uranus_port263-3.0-alpha.1-port.1-beta.jar`, `jupiter_port263-2.4.3-port.1-beta-26.3-universal.jar`) before building - see `build.gradle`.

`./gradlew build` (JDK required; the jar lands in `build/libs/`).

---

## Original README

# Ice And Fire Community Edition

This is an unofficial fork of [Ice And Fire](https://github.com/AlexModGuy/Ice_And_Fire). Contains optimizations, new
content and rewrites.

**Note: DO NOT directly replace mod, or your saves will be corrupted!**

Related Links:
[Origin CurseForge](https://www.curseforge.com/minecraft/mc-mods/ice-and-fire-dragons) | [Origin Modrinth](https://modrinth.com/mod/ice-and-fire-dragons)

**Please DO NOT report any issue to the original mod!**

Join our [Discord](https://discord.gg/NDzz2upqAk) and check
[the wiki](https://docs.iafenvoy.com/docs/mod/ice-and-fire-ce/) if you have any questions.

## What have we have added

**All the things from original mod except myrmex series.**

Netherite Related things.

## Capability

✅: Fully Supported<br>
🔗: Supported by an addon (See Notes)<br>
🚧: Partly Supported<br>
🔲: Planned Support<br>
❌: No Support Planned<br>

| Mod                         | Content                               | Status | Notes                                                                                                                                                                                                                  |
|-----------------------------|:--------------------------------------|:------:|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Better Combat               | Weapon use animation, etc.            |   🔗   | Ice and Fire Dragons X Better Combat <br> [CurseForge](https://www.curseforge.com/minecraft/mc-mods/ice-and-fire-dragons-x-better-combat) \| [Modrinth](https://modrinth.com/mod/ice-and-fire-dragons-x-better-combat) |
| Curios API                  | Some accessories                      |   ✅    |                                                                                                                                                                                                                        |
| Dragon Survival             | Apply dragon things to players        |   🔗   | Dragon Survival: IAF Integration <br> [CurseForge](https://www.curseforge.com/minecraft/mc-mods/dragon-survival-iaf-integration) \| [Modrinth](https://modrinth.com/mod/dragon-survival-iaf-integration)               |
| EMF&ETF                     | Model adjust                          |   ✅    |                                                                                                                                                                                                                        |
| EMI                         | Recipe view                           |   ✅    |                                                                                                                                                                                                                        |
| Epic Fight                  | Weapon use animation, etc.            |   ❌    |                                                                                                                                                                                                                        |
| Farmer's Delight            | Food based on IAF                     |   🚧   | Will be move to standalone mod.                                                                                                                                                                                        |
| Iron's Spells 'n Spellbooks | IAF Spellbooks                        |   🔗   | Ice and Fire: Spellbooks <br> [CurseForge](https://www.curseforge.com/minecraft/mc-mods/ice-and-fire-spellbooks) \| [Modrinth](https://modrinth.com/mod/ice-and-fire-spellbooks)                                       |
| Jade                        | Additional crosshair information      |   ✅    |                                                                                                                                                                                                                        |
| Journey Map                 | Map Mod                               |   🔗   | IAFCE - JourneyMap Support <br> [CurseForge](https://www.curseforge.com/minecraft/texture-packs/iafce-journeymap-support) \| [Modrinth](https://modrinth.com/resourcepack/iafce-journeymap-support)                    |
| Just Enough Items           | Recipe view                           |   ✅    |                                                                                                                                                                                                                        |
| Ponder                      | Dragon Forge Structure Tutorial       |   ✅    | MC 1.21+ only                                                                                                                                                                                                          |
| ProjectE                    | EMC & Dragon Forge Recipe Calculation |   ✅    |                                                                                                                                                                                                                        |
| Roughly Enough Items        | Recipe view                           |   ❌    |                                                                                                                                                                                                                        |
| Tinkers' Construct          | New materials                         |   🔲   |                                                                                                                                                                                                                        |
| Thaumcraft 7                | New vis                               |   🔲   | TC7 is not published yet but is planned so I added here lol                                                                                                                                                            |
| Trinkets                    | Some accessories                      |   ✅    |                                                                                                                                                                                                                        |
| Xaero's Minimap & Worldmap  | Map Mod                               |   🔗   | IAFCE - XaeroMap Support <br> [CurseForge](https://www.curseforge.com/minecraft/texture-packs/iafce-xaeromap-support) \| [Modrinth](https://modrinth.com/resourcepack/iafce-xaeromap-support)                          |

Also, there's a recipe extension pack for updated and harder crafts using netherite:
[Ice And Fire Smithing](https://modrinth.com/datapack/iceandfire_smithing)

## FAQ

### Will new contents in 1.21.1 be updated in 1.20.1?

No. 1.20.1 now only receive bug fixes since parallel update is very hard. That means code are very different on 2
versions. If you are addon developers and confused when upgrading version, join my discord and feel free to ask me.

### Other Minecraft Version?

This mod is planned to be updated to the later Minecraft version after bugs are resolved and some rewrites completed.

I'm not planning to backport the mod, but anyone can do it.

### I cannot find config file

As the config system is based on `Jupiter`, the config will only save needed data. You can change config by using client
side GUI through mod menu. (Requires server operator permission.)

### How to change biome configs

Change biome tags by using datapacks.
