package com.example.proyectojavamvvm.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.proyectojavamvvm.data.model.Abastecimiento;
import com.example.proyectojavamvvm.data.model.UserSelection;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import java.util.List;
@Dao
public interface AbastecimientoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) Completable insertCatalog(List<Abastecimiento> list);
    @Query("SELECT * FROM abastecimiento") Observable<List<Abastecimiento>> getCatalog();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertSelections(List<UserSelection> selections);

}
