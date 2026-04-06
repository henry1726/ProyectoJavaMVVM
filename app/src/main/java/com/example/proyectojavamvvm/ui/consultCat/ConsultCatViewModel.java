package com.example.proyectojavamvvm.ui.consultCat;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.proyectojavamvvm.data.model.Abastecimiento;
import com.example.proyectojavamvvm.data.model.UserSelection;
import com.example.proyectojavamvvm.data.repository.ConsultCatRepository;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ConsultCatViewModel extends ViewModel {
    private final ConsultCatRepository repo;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public MutableLiveData<List<Abastecimiento>> catalog = new MutableLiveData<>();
    public MutableLiveData<Boolean> saveSuccess = new MutableLiveData<>();

    @Inject
    public ConsultCatViewModel(ConsultCatRepository repo) {
        this.repo = repo;
    }

    public void loadLocalCatalog() {
        disposables.add(
                repo.getLocalCatalog()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(list -> catalog.setValue(list), Throwable::printStackTrace)
        );
    }

    public void saveSelections(List<UserSelection> selections) {
        disposables.add(
                repo.saveUserSelections(selections)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> saveSuccess.setValue(true),
                                Throwable::printStackTrace
                        )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
