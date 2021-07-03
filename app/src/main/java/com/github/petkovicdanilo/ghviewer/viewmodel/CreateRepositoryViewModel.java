package com.github.petkovicdanilo.ghviewer.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import lombok.Getter;

public class CreateRepositoryViewModel extends ViewModel {

    @Getter
    private MutableLiveData<String> name = new MutableLiveData<>();

    @Getter
    private MutableLiveData<String> description = new MutableLiveData<>();

    @Getter
    private MutableLiveData<String> homepage = new MutableLiveData<>();

    @Getter
    private MutableLiveData<Boolean> isPrivate = new MutableLiveData<>();

}
