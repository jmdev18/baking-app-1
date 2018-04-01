package com.exercise.template.views.activities.detail.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.exercise.template.api.models.Recipe;
import com.exercise.template.api.models.Step;

import io.reactivex.disposables.CompositeDisposable;
import lombok.Data;

/**
 * File Created by pandu on 01/04/18.
 */
@Data
public class DetailViewModel extends ViewModel {

    private MutableLiveData<Recipe> recipe = new MutableLiveData<>();

    private MutableLiveData<Step> selectedStep = new MutableLiveData<>();

    private MutableLiveData<Boolean> isTablet = new MutableLiveData<>();

    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();
    }
}
