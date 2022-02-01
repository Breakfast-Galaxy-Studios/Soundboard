# BGS-Soundboard
This program is a platform independent soundboard that was written in Java. This, combined with virtual audio cables / virtual audio mixer, allows anyone to play sounds through their microphone.

This project is heavy inspired by [DCSB,](https://github.com/kalejin/dcsb) the main difference being that this one can be easily used on all platforms.

## Usage
To run this program, you must have Java 17 installed, which can be found either [here](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) or [here.](https://jdk.java.net/17/) <br>
If you wish to have it output to your microphone, you will likely need to use a virtual audio cable. 

### Virtual audio cables / mixers
There are several free and open source audio cables / mixers, and some closed source but free ones as well.

- macOS: We recommend using [Jack Audio,](https://jackaudio.org/) or [Soundflower.](https://github.com/mattingalls/Soundflower)
- Linux: The only mainstream option is [Jack Audio,](https://jackaudio.org/) although you can potentially achieve the same thing from command line.
- Windows: The open source option is [Jack Audio,](https://jackaudio.org/) but the free but closed source [VB-Audio VoiceMeeter](https://vb-audio.com/Voicemeeter/banana.htm) is more widely adopted and has much more online support.

> The only tested support is with VoiceMeeter on Windows. Jack Audio likely needs more support from us, but as of right now there is no push for the feature by users, and therefore this isn't one of our priorities. If there is enough request for the feature we will implement it, but as of right now there are no plans to finish the implementation.

### Supported Sound formats:

`.wav`is the only supported file format, although you can technically use `.mp3`.<br>
All`.wav`files should be supported. Some`.mp3`files are known to not work.<br>
Almost all sound formats are potentially playable, but nothing other than WAVE and MP3 have been tested.<br>
If you would like to convert sound files to `.wav`, we recommend using [this site.](https://convertio.co/mp3-wav/)
<br>Additionally you can convert MP3 files to WAV files in [Audacity](https://www.audacityteam.org/). (Tutorial Coming soon...)
> ***DISCLAIMER: MP3 SOUNDS WILL LIKELY BE DISTORTED***

## Known issues
- Users on version 1.2 or 1.3 will not recieve auto-updates. 1.3.1+ or 1.1 and earlier will work.
- Popup alerts aren't always on top. This can lead to getting stuck behind fullscreen apps and not being able to open soundboard without minimizing everything. No ETA for a fix, but it will be fixed soon.

## Branches
> More branches will be made, seperating interception and jack audio development from main dev.
### Main
- Known to build
- Stable
- Likely has older features, but they are known to work and are 
- Matches release

### Dev
- Likely isn't stable or will crash
- Typically has major, app breaking, bugs
- Has the newest features, but likely have broken or incomplete implementation.

### Building
Building either branch should be done with maven. The build enviornment should be set up already in `pom.xml`, so all you should have to do is compile with maven.
