# Battle for Cybertron

Battle for Cybertron is an application developed for Android devices that lets you create your own Transformers, be it An Autobot or Decepticon, you can add a new Transformer to whichever Team you prefer.
Every Transformer can be edited or deleted. After all parameters have been configured, the app lets you wage war between the teams that puts every Transformer to the test and decides a Winner based on how many opposing transformers were destroyed.

## Assumptions

-Teams should be displayed as opposed to a single list displaying every Transformer regardless of team.

-Add and edit actions are independent to each team, therefore, you can't explicitly select a team for a Transformer you are creating or modifiying. The team gets selected automatically.

-Documentation of classes in the form of Javadoc.

-Unit tests test the basic CRUD operations for a transformer and tests a single battle.

-Everytime there is an add,modify,or delete on a Transformer, Each list is recreated and resorted and the view is refreshed to display said changes.

-Only the Allspark token is persisted for future runs of the app.

## Installation

For physical devices:

1)grab the "app-debug.apk" file in the "outputs" folder

2)transfer the apk into the physical device

3)in the device configurations enable installing applications from external sources

4)navigate to the apk file in the device

5)click on the file and accept the prompt message to install the app

For Emulator:

1)Use the adb push command to transfer the apk into the emulator files 

```bash
adb push <file-source-local> <destination-path-remote>
```

2)<file-source-local> should be the folder path for the location of the apk

3)<destination-path-remote> should be the folder path where the apk will be stored in the emulator

4)follow steps 3-5 of the physical device installation process