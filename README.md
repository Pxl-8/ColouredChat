# ColouredChat
[![GitHub](https://img.shields.io/github/license/Pxl-8/ColouredChat?style=flat-square&logo=github&link=https://github.com/Pxl-8/ColouredChat/blob/1.12/LICENSE.md)](https://github.com/Pxl-8/ColouredChat/blob/1.12/LICENSE.md)
[![GitHub tag (latest SemVer)](https://img.shields.io/github/tag/Pxl-8/ColouredChat?label=latest&style=flat-square&logo=github&link=https://github.com/Pxl-8/ColouredChat/releases)](https://github.com/Pxl-8/ColouredChat/releases)
[![Travis (.com)](https://img.shields.io/travis/com/Pxl-8/ColouredChat?style=flat-square&logo=travis&link=https://travis-ci.com/Pxl-8/ColouredChat)](https://travis-ci.com/Pxl-8/ColouredChat)
[![Discord](https://img.shields.io/discord/163375257162350592?style=flat-square&color=7289da&label=discord&logo=discord&link=https://uberi.fi/x/discord/)](https://uberi.fi/x/discord/)
[![Downloads](https://img.shields.io/badge/dynamic/json?color=6441a4&label=curse-downloads&query=$..downloadCount&url=https%3A%2F%2Faddons-ecs.forgesvc.net%2Fapi%2Fv2%2Faddon%2Fsearch%3FgameId%3D432%26searchFilter%3Dcolouredchat&style=flat-square)](https://www.curseforge.com/minecraft/mc-mods/colouredchat)

A simple mod that randomly generates a name colour when a player joins the server

Makes chat more readable when multiple players are typing

## Releases

#### 1.12.2-0.1.0-beta release
- Initial release, random coloured names (no configs yet)
#### 1.12.2-0.2.0-beta release
Added configuration file and commands
- Config for name delimiters (\<username>)
- Config to enable/disable list of colours to use for random assignment
- Config to enable/disable custom name colouring command
- Command to allow players to choose a default name colour
#### 1.12.2-0.3.0-beta release
- Added quick compatibility with discordchat https://minecraft.curseforge.com/projects/discordchat
#### 1.12.2-1.4.0 release
- Backport of 1.14 code base to 1.12
- Added random and list commands
- Added quasi random assignment

---

#### 1.14.4-0.4.0 release
Major Rewrites for Forge 1.14
- Now using capabilities over WorldSavedData 
- Commands now use Mojangs new Brigadier system
- Chat properly reads and uses text components, you can now click a username for /tell \<name>
- Configs now use new toml format

Brand new quasi-random colour assignment system implemented
- Players are now randomly assigned colours from a reverse-bag system
- This system ensures all colours are assigned before using any repeated colours
- The system also attempts to maintain an equal distribution of all colours when repeated colours are needed
- This system is independent of any randomly assigned or set colours (using the commands)
- If you only want to use this system you can simply disable the random/set commands from the config
#### 1.14.4-1.4.2 release
- Added team assignment to experimental config
- Automatically creates and adds players to a team set to their colour
- Colours will be able to be seen in the name hovering above the player as well as in the player tab list

---

## Todo:
- ~~Command for letting players set their own colours~~
