package com.github.petkovicdanilo.ghviewer.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.github.petkovicdanilo.ghviewer.api.dto.ActivityDto;

import java.util.List;

import lombok.Getter;

public class ActivitiesViewModel extends ViewModel {

    @Getter()
    private LiveData<List<ActivityDto>> activitiesList;
}
