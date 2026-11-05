# Quick Click (Fabric, Minecraft 1.21.11, client-side)

Separate from Quick Use. Shortens two left-click pauses. Neither is ever set to 0.

- **Swing-miss delay:** after you swing at air and miss, vanilla blocks new swings for 10 ticks.
  This mod uses 5 by default.
- **Block-break delay:** after you break a block, vanilla waits 5 ticks before you can start the next
  one while holding left click. This mod uses 3 by default.

Not changed: the attack cooldown the server uses to calculate damage (hitting before it recharges
still does reduced damage), and how long each block takes to break.

Heads-up: the block-break delay is the one most likely to be flagged by anti-cheat on some servers.
Set it back to 5 (vanilla) on servers you are unsure about, or switch the mod OFF.

## Settings
Install **Mod Menu**, open Mods > Quick Click > settings button:
- **Quick Click: ON/OFF**
- **Swing-miss delay** cycles 10 (vanilla) / 9 / ... / 1
- **Block-break delay** cycles 5 (vanilla) / 4 / 3 / 2 / 1

Without Mod Menu, edit `config/quickclick.json`:

    { "enabled": true, "missDelayTicks": 5, "blockDelayTicks": 3 }

## Get the jar
- **Online:** upload this folder to a GitHub repository (create `.github/workflows/build.yml` on GitHub
  if the hidden folder was skipped), then open Actions > latest run > Artifacts > quickclick-jar.
- **On your PC:** install JDK 21, run `gradlew build`, take `build/libs/quickclick-1.0.0.jar`.

Put the jar in `.minecraft/mods` with Fabric API for 1.21.11.

## If the build complains about a version
Open https://fabricmc.net/develop, pick 1.21.11 and copy the values into `gradle.properties`.
