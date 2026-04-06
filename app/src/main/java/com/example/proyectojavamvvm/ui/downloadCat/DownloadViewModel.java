package com.example.proyectojavamvvm.ui.downloadCat;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.proyectojavamvvm.data.model.Abastecimiento;
import com.example.proyectojavamvvm.data.repository.DownloadRepository;
import com.example.proyectojavamvvm.util.Resource;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class DownloadViewModel extends ViewModel{
    private final DownloadRepository repo;
    public MutableLiveData<Resource<List<Abastecimiento>>> status = new MutableLiveData<>();
    private final CompositeDisposable disposables = new CompositeDisposable();


    @Inject
    public DownloadViewModel(DownloadRepository repo) { this.repo = repo; }

    public void startSync() {
        status.setValue(Resource.loading());
        disposables.add(
                repo.syncCatalog()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                data -> status.setValue(Resource.success(data)),
                                e -> status.setValue(Resource.error(e.getMessage()))
                        )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
