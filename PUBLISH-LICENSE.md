# Ice and Fire CE 26.3 port: licence check for public redistribution (CurseForge, beta)

Checked 2026-10-14 (AEDT). Sources: the files in this repo, the Jupiter and Uranus repos and jars under `/workspace/mod-port/iceandfire`, and the upstream GitHub / CurseForge pages listed below. This is not legal advice.

## Verdict

**Allowed. No permission from IAFEnvoy is required**, provided the LGPL-3.0 conditions below are met.

Ice and Fire CE, Jupiter and Uranus are all licensed **LGPL-3.0-or-later**, and neither the code nor the assets carry an All-Rights-Reserved clause. The upstream it was forked from (AlexModGuy's Ice and Fire) is LGPL-3.0 as well. The ported Dragonseeker content is MIT.

The real obligations are:
- licence files in every jar;
- a **public Corresponding Source** of the port;
- clear credits and "unofficial / modified" marking;
- and, strongly recommended, a distinct mod id so it does not impersonate the official CE.

Asking IAFEnvoy anyway is a courtesy, not a legal requirement. The CE README asks users not to report issues to the original mod, and a 26.3 port should likewise route bug reports away from IAFEnvoy.

## 1. Ice and Fire Community Edition (`port/`, upstream IAFEnvoy/IceAndFire-CE master)

- **`LICENSE`** (repo root, 859 lines). Lines 1–15 are a header, "Copyright (c) 2024 IAFEnvoy", stating the program is licensed "under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version". It is followed by the full **LGPL-3.0** text (line 17) and the full **GPL-3.0** text (line 183). Upstream copy: https://raw.githubusercontent.com/IAFEnvoy/IceAndFire-CE/master/LICENSE. GitHub reports NOASSERTION because of the custom header.
- **`gradle.properties` line 14:** `mod_license=LGPL-3.0`. This goes into `neoforge.mods.toml` via `src/main/templates/META-INF/neoforge.mods.toml` line 15. The built jar says `license = "LGPL-3.0"`, with authors "IAFEnvoy, xiaowu, uoay, aelux".
- **CurseForge** (IceAndFire Community Edition, by IAFEnvoy) lists "GNU Lesser General Public License version 3 (LGPLv3)": https://www.curseforge.com/minecraft/mc-mods/iceandfire-ce/license
- **`README.md`** lines 3–11: "This is an unofficial fork of [Ice And Fire](https://github.com/AlexModGuy/Ice_And_Fire) …" and "**Please DO NOT report any issue to the original mod!**". It contains no asset or licence restrictions.
- **Asset notes:** I searched (md/txt/json/toml/properties) for "All Rights Reserved", CC-BY, CC0 and Creative Commons. The only hit is the MDK template comment in the mods.toml template. **There are no ARR notes for models or textures**, so the 3734 files in `assets/iceandfire/**` are covered by the LGPL-3.0 like the code.
- **Inherited upstream: AlexModGuy/Ice_And_Fire.** GitHub reports **LGPL-3.0**: https://raw.githubusercontent.com/AlexModGuy/Ice_And_Fire/1.20/LICENSE is the LGPL-3.0 text, and the 1.20 `mods.toml` has `license = "GNU LESSER GENERAL PUBLIC LICENSE"`. CurseForge "Ice and Fire: Dragons" also lists LGPLv3: https://www.curseforge.com/minecraft/mc-mods/ice-and-fire-dragons/license. No ARR asset clause was found there either.
- **Ported third-party content: Dragonseeker.** `assets/iceandfire/lang/en_us.json` line 1381 says "Ported from mod Ice and Fire: Dragonseeker by Syrikal". This covers `item/DragonSeekerItem.java` and the textures `textures/item/*dragon_seeker.png`. Dragonseeker (by syrikalis) is **MIT** on CurseForge: https://www.curseforge.com/minecraft/mc-mods/ice-and-fire-dragonseeker/license. MIT only requires its copyright and permission notice to be kept; none is currently in the repo or jar, so add one to the credits / a `LICENSE_dragonseeker` file.
- **The current port jar** (`mods-26.3/iceandfire-2.2-beta.4-port.26.3.jar`, = `port/build/libs/…`) contains **no LICENSE file**. One must be added.

## 2. Jupiter (`/workspace/mod-port/iceandfire/Jupiter`, upstream https://github.com/IAFEnvoy/Jupiter, branch main)

- `Jupiter/LICENSE` has the same structure: "Copyright (c) 2024 IAFEnvoy", LGPL-3.0-or-later header, then the LGPL-3.0 and GPL-3.0 texts.
- `Jupiter/gradle.properties` line 12: `mod.license=LGPL-3.0`.
- Port jar `mods-26.3/jupiter-2.4.3-26.3-universal.jar` (= `port/libs/`): mods.toml has `license = "LGPL-3.0"` and authors "IAFEnvoy". **The jar already contains `LICENSE_jupiter`.** Its assets are only `assets/jupiter/lang` (3 files), and there is no ARR clause.

## 3. Uranus (`/workspace/mod-port/iceandfire/Uranus`, upstream https://github.com/IAFEnvoy/Uranus, branch master)

- `Uranus/LICENSE` has the same LGPL-3.0-or-later header and the LGPL + GPL texts. Its copyright line is blank ("Copyright (c) 2024 "); the author is IAFEnvoy per mods.toml.
- `Uranus/gradle.properties` line 13: `mod_license=LGPL-3.0`.
- Port jar `mods-26.3/uranus-3.0-alpha.1.jar`: mods.toml has `license="LGPL-3.0"` and authors "IAFEnvoy". **The jar already contains `LICENSE_uranus`.** It has no assets.

## Can they be bundled or published as ports?

- **Separate jars:** yes. Each can be uploaded as its own unofficial port project under LGPL-3.0.
- **Bundled** (jar-in-jar inside the IAF port): also yes. LGPL-3.0 §4 ("Combined Works") is satisfied because the libraries remain separate replaceable jars with their licence and source. Keep `LICENSE_jupiter` and `LICENSE_uranus` inside them.
- Either way, the libraries are modified 26.3 ports, so the same source and marking conditions apply to them.

## Conditions (LGPL-3.0 together with GPL-3.0 §4–§6)

1. **Licence files in the jars.** Add `LICENSE` (the LGPL-3.0 and GPL-3.0 texts, with the IAFEnvoy header) to the IAF jar. Keep `LICENSE_jupiter` and `LICENSE_uranus`. Add the MIT notice for Dragonseeker.
2. **Public source.** Publish the complete Corresponding Source of the modified IAF, Jupiter and Uranus ports, with build scripts, e.g. public repos or forks linked from the CurseForge page (GPL §6, via LGPL §4d/§0). A binary-only upload is not compliant.
3. **Mark the changes.** State that the work is a modified port to NeoForge 26.3 with a date (GPL §5a), and keep all copyright notices (GPL §4).
4. **Same licence, no extra restrictions.** Licence the port under LGPL-3.0 (or later) and add no further restrictions (GPL §10).
5. **Credits:**
   - IAFEnvoy and the CE contributors (xiaowu, uoay, aelux, …);
   - Alexthe666 / AlexModGuy and the original Ice and Fire team (upstream);
   - syrikalis (Dragonseeker, MIT);
   - IAFEnvoy for Jupiter and Uranus;
   - plus "unofficial, beta, do not report issues to IAFEnvoy or the original mod".
6. **Recommended, not legally required:** use a distinct mod id and project name (e.g. `iceandfire_port263`, "Ice and Fire CE – NeoForge 26.3 Port (unofficial)"). This avoids clashing with or impersonating the official `iceandfire` mod id and CurseForge project. Note that changing the mod id would also rename the `iceandfire:` content namespace unless the code separates the two, which would break existing worlds.

## Does anything need IAFEnvoy's permission?

**No**, legally. Everything is LGPL-3.0 (and MIT for Dragonseeker), with no ARR or asset restriction in the repos, jars or CurseForge licence fields. Permission would only be needed to deviate from the licence, for example a binary-only release or relicensing. A courtesy notice to IAFEnvoy (Discord https://discord.gg/NDzz2upqAk, or GitHub) is still advisable, especially since an official 26.3 release may follow.

## Runtime dependencies (not bundled)

Curios (LGPL-3.0-or-later) and GeckoLib (MIT, `compat/geckolib-neoforge-26.3-5.5.7.jar`) are installed by users and add no obligations. Optional compat jars under `iceandfire/compat/` are test-only and are not redistributed.
