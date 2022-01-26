# AutoTotem
Automatically use a totem of undying from your inventory

Spigot Download https://www.spigotmc.org/resources/autototem.99443/

![](https://web.blujay.xyz/images/AutoTotem.png)

![](https://web.blujay.xyz/images/AutoTotem.gif)

Automatically use totems of undying from your inventory without needing to hold them in your hand!

No configuration required, just drag and drop!

![](https://web.blujay.xyz/images/Permissions.png)

**autototem.use** - Enables AutoTotems for the player (Default true)
**autototem.credits** - Access to /autototem command (Default true)
**autototem.*** - All permissions enabled

![](https://web.blujay.xyz/images/PlannedFeatures.png)

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
returns **boolean**: true if the player has a totem in their inventory and AutoTotem could activate.
