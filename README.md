# UHCDataPresenter
Pipe locally retrieved data from CK to localhost server.

Special thanks to https://github.com/RubenNL/ (RubenNL) for doing most of the heavy lifting with Fabric to make this possible (he did around 90% of the mod itself) and https://github.com/DownloadPizza (DownloadPizza) for providing assistance with Fabric.
 
# Using
**IMPORTANT**: Requires Fabric loader 0.11.3 or above, and version 0.34.2 of the Fabric API or above.
 
Simply put into your mods folder and run. The data server will be run on `localhost` on port `8081`, so please make sure that port is free. At the moment, this is not configurable without rebuilding the mod itself and changing the overlay code.
 
To use the pre-made overlay, add a Browser Source in OBS and link it to EITHER `overlay1/index.html` or `overlay2/index.html` in the overlay zip file (or `overlay` folder if you cloned the repo). Set the size to 1920x1080.

`overlay1` and `overlay2` fucntion exactly the same. The two overlays just have different sizes and layouts.

The mod will detect when the message `All your chat messages now appear in team chat.` appears in-game, which is what the CK UHC plugin displays when the game starts. It will then send the flag `started: true` to the overlay via. the data.json file, which tells the overlay to start receiving and displaying data. If this does not happen, please run `/forcestartuhc` in-game.

You can make your own overlay by looking at the data in `http://localhost:8081/data.json`, but please get it approved by CK staff if you plan on using it in the CK UHC.

# Commands
The mod has 3 built in commands:
* `/resetpresenter`: Resets certain data in the presenter. As of now, only the deaths are reset.
* `/forcestartuhc`: Forces the flag `started` in the data.json file to `true`, which the pre-made overlay will detect and start showing the overlay.
* `/forcestopuhc`: Forces the flag `started` in the data.json file to `true`, which the pre-made overlay will detect and stop showing the overlay.

# Contributing

Run these commands to set up the dev environment:
```bash
git clone https://github.com/CAG2Mark/UHCDataPresenter
cd UHCDataPresenter
./gradlew
````
If you are using IntelliJ IDEA, then you should open build.gradle to open the project file. IntellIJ will then sync everything for you; no need to run `./gradlew`.

To build:
```bash
./gradlew build
```
The .jar files should now be in `UHCDataPresenter/build/lib`.
