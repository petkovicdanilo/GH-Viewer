package com.github.petkovicdanilo.ghviewer.viewmodel;

import androidx.lifecycle.ViewModel;

import lombok.Setter;

public class RepositoryBlobViewModel extends ViewModel {
    @Setter
    private boolean reload = true;

    public boolean doReload() {
        return reload;
    }
}
