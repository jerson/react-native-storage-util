
# react-native-storage-util

info about free space and external devices, only for **Android**

## Getting started

`$ npm install react-native-storage-util --save`

### Mostly automatic installation

`$ react-native link react-native-storage-util`

### Manual installation


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
import StorageUtil from 'react-native-storage-util';

StorageUtil.getAll();
StorageUtil.getLocations();
StorageUtil.getFreeSpace(path);
StorageUtil.getTotalSpace(path); 
```
  