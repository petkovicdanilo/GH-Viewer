package com.github.petkovicdanilo.ghviewer.view.util;

import android.app.Activity;
import android.view.Menu;

import com.github.petkovicdanilo.ghviewer.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNav {
    public static void unselect(Activity activity) {
        BottomNavigationView bottomNav = activity.findViewById(R.id.bottom_nav);
        Menu bottomNavMenu = bottomNav.getMenu();

        bottomNavMenu.setGroupCheckable(0, true, false);
        for(int i = 0; i < bottomNavMenu.size(); i++) {
            bottomNavMenu.getItem(i).setChecked(false);
        }
        bottomNavMenu.setGroupCheckable(0, true, true);
    }
}
