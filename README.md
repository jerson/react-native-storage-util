
# react-native-storage-util

## Getting started

`$ npm install react-native-storage-util --save`

### Mostly automatic installation

`$ react-native link react-native-storage-util`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-storage-util` and add `RNStorageUtil.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNStorageUtil.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import me.jerson.mobile.storage.RNStorageUtilPackage;` to the imports at the top of the file
  - Add `new RNStorageUtilPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-storage-util'
  	project(':react-native-storage-util').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-storage-util/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-storage-util')
  	```


## Usage
```javascript
import RNStorageUtil from 'react-native-storage-util';

// TODO: What to do with the module?
RNStorageUtil;
```
  