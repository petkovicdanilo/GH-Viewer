package com.github.petkovicdanilo.ghviewer.view.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class SupportedExtensions {
    public static Set<String> supportedExtensions = new HashSet<>();

    public static void initialize(Context context) {
        AssetManager am = context.getAssets();
        try {
            InputStream is = am.open("extensions.txt");
            Scanner scanner = new Scanner(is).useDelimiter("\n");

            while(scanner.hasNext()) {
                String extension = scanner.next();
                supportedExtensions.add(extension);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean has(String extension) {
        return supportedExtensions.contains(extension);
    }
}
