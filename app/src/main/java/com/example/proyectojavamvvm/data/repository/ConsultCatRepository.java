package com.example.proyectojavamvvm.data.repository;

import com.example.proyectojavamvvm.data.local.AbastecimientoDao;
import com.example.proyectojavamvvm.data.model.Abastecimiento;
import com.example.proyectojavamvvm.data.model.UserSelection;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
public class ConsultCatRepository {
    private final AbastecimientoDao dao;

    @Inject
    public ConsultCatRepository(AbastecimientoDao dao) {
        this.dao = dao;
    }

    public Observable<List<Abastecimiento>> getLocalCatalog() {
        return dao.getCatalog();
    }

    public Completable saveUserSelections(List<UserSelection> selections) {
        return dao.insertSelections(selections);
    }
}
