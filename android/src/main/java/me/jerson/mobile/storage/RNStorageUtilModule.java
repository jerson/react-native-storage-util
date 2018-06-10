
package me.jerson.mobile.storage;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.module.annotations.ReactModule;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class RNStorageUtilModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNStorageUtilModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNStorageUtil";
  }

  public static List<String> getExternalMounts() {
    final List<String> out = new ArrayList<>();
    String reg = "(?i).*vold.*(vfat|ntfs|exfat|fat32|ext3|ext4).*rw.*";
    String s = "";
    try {
      final Process process = new ProcessBuilder().command("mount").redirectErrorStream(true).start();
      process.waitFor();
      final InputStream is = process.getInputStream();
      final byte[] buffer = new byte[1024];
      while (is.read(buffer) != -1) {
        s = s + new String(buffer);
      }
      is.close();
    } catch (final Exception e) {
      e.printStackTrace();
    }

    // parse output
    final String[] lines = s.split("\n");
    for (String line : lines) {
      if (!line.toLowerCase(Locale.US).contains("asec")) {
        if (line.matches(reg)) {
          String[] parts = line.split(" ");
          for (String part : parts) {
            if (part.startsWith("/"))
              if (!part.toLowerCase(Locale.US).contains("vold"))
                out.add(part);
          }
        }
      }
    }
    return out;
  }

  public String[] getStorageDirectories() {
    String[] storageDirectories;
    String rawSecondaryStoragesStr = System.getenv("SECONDARY_STORAGE");

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      List<String> results = new ArrayList<String>();
      File[] externalDirs = reactContext.getExternalFilesDirs(null);
      for (File file : externalDirs) {
        String path = file.getPath().split("/Android")[0];
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Environment.isExternalStorageRemovable(file))
            || rawSecondaryStoragesStr != null && rawSecondaryStoragesStr.contains(path)) {
          results.add(path);
        }
      }
      storageDirectories = results.toArray(new String[0]);
    } else {
      final Set<String> rv = new HashSet<String>();

      if (!TextUtils.isEmpty(rawSecondaryStoragesStr)) {
        final String[] rawSecondaryStorages = rawSecondaryStoragesStr.split(File.pathSeparator);
        Collections.addAll(rv, rawSecondaryStorages);
      }
      storageDirectories = rv.toArray(new String[rv.size()]);
    }
    return storageDirectories;
  }

  @ReactMethod
  public void getAllStorageLocations(Promise promise) {

    WritableMap data = new WritableNativeMap();
    try {

      int i = 1;
      File externalStorageDirectory = Environment.getExternalStorageDirectory();
      if (externalStorageDirectory != null) {
        data.putString("internal-" + i, externalStorageDirectory.getAbsolutePath());
        i++;
      }

      String[] storageDirectories = getStorageDirectories();
      for (String storageDirectory : storageDirectories) {
        data.putString("external-" + i, storageDirectory);
        i++;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    promise.resolve(data);
  }

  @ReactMethod
  public void getFreeSpace(String path, Promise promise) {

    // new Thread(new Runnable() {
    // public void run() {
    Long space = 0l;
    try {
      StatFs stats = new StatFs(path);
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
        long availableBlocks = stats.getAvailableBlocks();
        long blockSize = stats.getBlockSize();
        space = availableBlocks * blockSize;

      } else {
        space = stats.getAvailableBytes();
      }
    } catch (Exception e) {
      space = 0l;
    }

    promise.resolve(space.intValue());
    // }
    // }).start();

  }

  @ReactMethod
  public void getTotalSpace(String path, Promise promise) {

    // new Thread(new Runnable() {
    // public void run() {
    Long space = 0l;
    try {
      StatFs stats = new StatFs(path);
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
        long totalBlocks = stats.getBlockCount();
        long blockSize = stats.getBlockSize();
        space = totalBlocks * blockSize;
      } else {
        space = stats.getTotalBytes();
      }
    } catch (Exception e) {
      space = 0l;
    }
    promise.resolve(space.intValue());
    // }
    // }).start();

  }
}