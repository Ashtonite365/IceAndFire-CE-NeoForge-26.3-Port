# Ice and Fire CE NeoForge 26.3 Port

> **Unofficial, BETA.** This is a community port. It is **not** made or supported by IAFEnvoy, the Ice and Fire CE team, or Alexthe666. Please do **not** report bugs from this port upstream (not to IAFEnvoy / IceAndFire-CE, and not to the original Ice and Fire).

An unofficial, **modified** port of **Ice And Fire: Community Edition 2.2-beta.4** (IAFEnvoy/IceAndFire-CE master) to **NeoForge / Minecraft 26.3**, made in **October 2026**. Released under **LGPL-3.0**.

- **Version:** 2.2-beta.4-port.1-beta (beta: expect bugs, back up your worlds)
- **Mod loader:** NeoForge **26.3.0.76** or newer
- **Minecraft:** 26.3
- **Environment:** required on both client and server

## Required dependencies

- **Jupiter NeoForge 26.3 Port** (mod id `jupiter_port263`) **2.4.3-port.1-beta** or newer: unofficial 26.3 port of Jupiter by IAFEnvoy, a config library. Original: https://www.curseforge.com/minecraft/mc-mods/jupiter (source: https://github.com/IAFEnvoy/Jupiter)
- **Uranus NeoForge 26.3 Port** (mod id `uranus_port263`) **3.0-alpha.1-port.1-beta** or newer: unofficial 26.3 port of Uranus by IAFEnvoy, an animation/model library. Original: https://www.curseforge.com/minecraft/mc-mods/uranus (source: https://github.com/IAFEnvoy/Uranus)

The official Jupiter / Uranus builds (mod ids `jupiter` / `uranus`) do **not** satisfy this dependency; install the two 26.3 port jars.

## Optional

- **Curios API** 17.0.0-beta.3 for 26.3 (accessory slots): https://modrinth.com/mod/curios
- The other integrations of the original (JEI, Jade, EMI, Ponder, ProjectE, Ars Nouveau) are **disabled** in this port.

## Credits

- **Ice And Fire: Community Edition** by **IAFEnvoy and the CE contributors (xiaowu, uoay, aelux and others)**, the mod this port is based on:
  - Source: https://github.com/IAFEnvoy/IceAndFire-CE
  - CurseForge: https://www.curseforge.com/minecraft/mc-mods/iceandfire-ce
- **Ice and Fire** (the original mod) by **Alexthe666 / AlexModGuy** and the original Ice and Fire team: https://github.com/AlexModGuy/Ice_And_Fire and https://www.curseforge.com/minecraft/mc-mods/ice-and-fire-dragons
- **Dragonseeker** (ported into CE: the Dragon Seeker item and its textures) by **syrikalis**, MIT licence: https://www.curseforge.com/minecraft/mc-mods/ice-and-fire-dragonseeker
- **Jupiter** and **Uranus** libraries by **IAFEnvoy** (LGPL-3.0)
- **NeoForge 26.3 port** by **Ashtonite365**

## Licence

Ice And Fire: Community Edition is licensed under the **GNU Lesser General Public License v3.0 or later (LGPL-3.0)**, (c) 2024 IAFEnvoy. This port is a **modified version (NeoForge 26.3 port, October 2026)** and is released under the same **LGPL-3.0** licence, with no additional restrictions.

- In the jar: `LICENSE` (IAFEnvoy header + full LGPL-3.0 and GPL-3.0 texts) and `LICENSE_dragonseeker` (MIT notice, syrikalis).
- The Jupiter and Uranus port jars keep their own `LICENSE_jupiter` / `LICENSE_uranus`.
- Original source: https://github.com/IAFEnvoy/IceAndFire-CE
- Source: TODO (public repo pending)

## Compatibility notes

- The mod id is **`iceandfire_port263`**, so it does not clash with the official `iceandfire` mod id.
- All content still uses the original **`iceandfire:`** namespace (items, blocks, entities, data, assets, commands). Existing datapacks and resource packs keep working unchanged.
- Loading this alongside another Ice and Fire build won't cause a duplicate-mod error, but it is **not supported**: both use the same `iceandfire:` content namespace, so their registries and data will collide. Use only one Ice and Fire build at a time.
