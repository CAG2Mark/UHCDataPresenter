# UHCDataPresenter
Pipe locally retrieved data from CK to localhost server.

Special thanks to https://github.com/RubenNL/ (RubenNL) for doing most of the heavy lifting with Fabric to make this possible and https://github.com/DownloadPizza (DownloadPizza) for providing assistance with Fabric.
 
# Using
**IMPORTANT**: Requires Fabric loader 0.11.3 or above, and version 0.34.2 of the Fabric API or above.
 
Simply put into your mods folder and run. The data server will be run on `localhost` on port `8081`, so please make sure that port is free. At the moment, this is not configurable.
 
To use the pre-made overlay, add a Browser Source in OBS and link it to the local file `index.html` in the overlay zip file (or `overlay` folder if you cloned the repo). Set the size to 1920x1080.

The mod will detect when the message `All your chat messages now appear in team chat.` appears in-game, which is what the CK UHC plugin displays when the game starts. It will then send the flag `started: true` to the overlay via. the data.json file.

You can make your own overlay by looking at the data in `http://localhost:8081/data.json`.

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

To build:
```bash
./gradlew build
```
The .jar files should now be in `UHCDataPresenter/build/lib`.
