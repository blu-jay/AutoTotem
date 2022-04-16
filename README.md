# AutoTotem
Spigot Download https://www.spigotmc.org/resources/autototem.99443/

[![](https://web.blujay.xyz/images/AutoTotem.png)](https://www.spigotmc.org/resources/autototem.99443/)

[![](https://web.blujay.xyz/images/AutoTotem.gif)](https://www.spigotmc.org/resources/autototem.99443/)

Automatically use totems of undying from your inventory without needing to hold them in your hand!

No configuration required, just drag and drop!

## Permissions

**autototem.use** - Enables AutoTotems for the player (Default true)
**autototem.credits** - Access to /autototem command (Default true)
**autototem.*** - All permissions enabled

## PlannedFeatures

Command to toggle AutoTotem on and off
Configurable totem cooldowns

------------
# API Reference
Want to extend the functionallity of AutoTotem? You can use the API !

### Download or build the jar
Download => https://www.spigotmc.org/resources/autototem.99443/

### Add the jar to your project
Drop it into a package called libs
(You can put it anywhere you want in your project just make sure to adjust the dependency to match!)

### Add the dependency to pom.xml
```xml
<dependency>
    <groupId>xyz.blujay</groupId>
    <artifactId>AutoTotem</artifactId>
    <version>1.0</version>
    <scope>system</scope>
    <systemPath>${basedir}/libs/AutoTotem-1.0.jar</systemPath>
</dependency>
```


### Get the API object:
`AutoTotemAPI autoTotemAPI = ((AutoTotem) Bukkit.getServer().getPluginManager().getPlugin("AutoTotem")).getAPI();`

------------

# API Methods
#### canUseTotem(Player player)
returns **boolean**: true if AutoTotem could activate for the given player.
