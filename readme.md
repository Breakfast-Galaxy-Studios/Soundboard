# BGS-Soundboard

This program is a platform independent soundboard that was written in Java. This, combined with virtual audio cables / virtual audio mixer, allows anyone to play sounds through their microphone.

This project is heavy inspired by [DCSB,](https://github.com/kalejin/dcsb) the main difference being that this one can be easily used on all platforms.

## Usage
To run this program, you must have Java 16 installed, which can be found either [here](https://www.oracle.com/java/technologies/javase-jdk16-downloads.html) or [here.](https://jdk.java.net/16/) <br>
If you wish to have it output to your microphone, you will likely need to use a virtual audio cable. 

### Virtual audio cables / mixers
There are several free and open source audio cables / mixers, and some closed source but free ones as well.

- macOS: We recommend using [Jack Audio,](https://jackaudio.org/) or [Soundflower.](https://github.com/mattingalls/Soundflower)
- Linux: The only mainstream option is [Jack Audio,](https://jackaudio.org/) although you can potentially achieve the same thing from command line.
- Windows: The open source option is [Jack Audio,](https://jackaudio.org/) but the free but closed source [VB-Audio VoiceMeeter](https://vb-audio.com/Voicemeeter/banana.htm) is more widely adopted and has much more online support.


### Supported Sound formats:

`.wav`is the only supported file format, although you can technically use `.mp3`.<br>
All`.wav`files should be supported. Some`.mp3`files are known to not work.<br>
Almost all sound formats are potentially playable, but nothing other than WAVE and MP3 have been tested.<br>
If you would like to convert sound files to `.wav`, we recommend using [this site.](https://convertio.co/mp3-wav/)
<br>Additionally you can convert MP3 files to WAV files in [Audacity](https://www.audacityteam.org/). (Tutorial Coming soon...)
> ***DISCLAIMER: MP3 SOUNDS WILL LIKELY BE DISTORTED***

## Known issues
- Tray icon doesn't appear.
- If it can't fild a file, it will crash instead of trying to load the rest.
