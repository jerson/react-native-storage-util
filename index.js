import { NativeModules, Platform } from "react-native";

const { RNStorageUtil } = NativeModules;

const TAG = "[StorageUtil]";

export default class StorageUtil {
  static getAll() {
    __DEV__ && console.debug(TAG, "getAll");
    if (Platform.OS !== "android") {
      __DEV__ && console.warn(TAG, "not supported on", Platform.OS);
      return;
    }
    return this.getLocations().then(locations => {
      let locationsKeys = Object.keys(locations);
      return Promise.mapSeries(locationsKeys, key => {
        let path = locations[key];
        return this.getFreeSpace(path).then(freeSpace => {
          return this.getTotalSpace(path).then(totalSpace => {
            return {
              path,
              key,
              totalSpace: Math.abs(totalSpace),
              freeSpace: Math.abs(freeSpace)
            };
          });
        });
      });
    });
  }

  static getLocations() {
    __DEV__ && console.debug(TAG, "getLocations");
    if (Platform.OS !== "android") {
      __DEV__ && console.warn(TAG, "not supported on", Platform.OS);
      return;
    }
    return RNStorageUtil.getAllStorageLocations();
  }

  static getFreeSpace(path) {
    __DEV__ && console.debug(TAG, "getFreeSpace", path);
    if (Platform.OS !== "android") {
      __DEV__ && console.warn(TAG, "not supported on", Platform.OS);
      return;
    }
    return RNStorageUtil.getFreeSpace(path);
  }

  static getTotalSpace(path) {
    __DEV__ && console.debug(TAG, "getTotalSpace", path);
    if (Platform.OS !== "android") {
      __DEV__ && console.warn(TAG, "not supported on", Platform.OS);
      return;
    }
    return RNStorageUtil.getTotalSpace(path);
  }
}
