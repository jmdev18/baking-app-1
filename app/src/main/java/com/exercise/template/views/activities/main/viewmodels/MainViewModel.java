package com.exercise.template.views.activities.main.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.exercise.template.App;
import com.exercise.template.api.AppApi;
import com.exercise.template.api.models.Ingredient;
import com.exercise.template.api.models.Recipe;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;
import timber.log.Timber;

/**
 * File Created by pandu on 31/03/18.
 */
@Getter
public class MainViewModel extends ViewModel {

    public enum Status {LOADING, SUCCESS, FAILED};

    private AppApi appApi;

    private MutableLiveData<Status> status = new MutableLiveData<>();

    private MutableLiveData<Boolean> isTablet = new MutableLiveData<>();

    private MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();

    private MutableLiveData<Recipe> selectedRecipe = new MutableLiveData<>();

    private MutableLiveData<Integer> numberOfCols = new MutableLiveData<>();

    private CompositeDisposable disposables = new CompositeDisposable();

    public MainViewModel(AppApi appApi) {
        this.appApi = appApi;
    }

    public void fetchRecipes(){
        status.setValue(Status.LOADING);
        Disposable subscribe = appApi.getRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ingredients -> {
                    status.setValue(Status.SUCCESS);
                    recipes.setValue(ingredients);
                }, throwable -> {
                    status.setValue(Status.FAILED);
                });

        disposables.add(subscribe);
    }

    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();
    }

    public static class Factory implements ViewModelProvider.Factory{

        private AppApi appApi;

        @Inject
        public Factory(AppApi appApi) {
            this.appApi = appApi;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MainViewModel(appApi);
        }
    }
}
