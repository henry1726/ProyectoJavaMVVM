package com.example.proyectojavamvvm.data.repository;

import com.example.proyectojavamvvm.data.local.AbastecimientoDao;
import com.example.proyectojavamvvm.data.model.Abastecimiento;
import com.example.proyectojavamvvm.data.network.ApiService;
import java.util.List;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javax.inject.Inject;
public class DownloadRepository {
    private final ApiService api;
    private final AbastecimientoDao dao;

    @Inject public DownloadRepository(ApiService api, AbastecimientoDao dao) {
        this.api = api; this.dao = dao;
    }

    public Observable<List<Abastecimiento>> syncCatalog() {
        return api.fetchCatalogos().map(r -> r.abastecimientoList)
                .flatMap(list -> dao.insertCatalog(list).andThen(Observable.just(list)))
                .subscribeOn(Schedulers.io());
    }
}
