import { NativeModules, Platform } from "react-native";

const { RNStorageUtil } = NativeModules;

const TAG = "[StorageUtil]";

export default class StorageUtil {
  static async getAll() {
    __DEV__ && console.debug(TAG, "getAll");
    if (Platform.OS !== "android") {
      __DEV__ && console.warn(TAG, "not supported on", Platform.OS);
      return;
    }

    let all = [];
    try {
      const locations = await this.getLocations();
      const locationsKeys = Object.keys(locations);
      for (const key of locationsKeys) {
        const path = locations[key];
        const freeSpace = Math.abs(await this.getFreeSpace(path));
        const totalSpace = Math.abs(await this.getTotalSpace(path));
        all.push({
          path,
          key,
          totalSpace,
          freeSpace
        });
      }
    } catch (e) {
      __DEV__ && console.warn(TAG, "getAll", e);
    }

    return all;
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
