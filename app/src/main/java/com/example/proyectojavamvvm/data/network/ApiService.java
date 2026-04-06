package com.example.proyectojavamvvm.data.network;

import com.example.proyectojavamvvm.data.model.Abastecimiento;
import io.reactivex.rxjava3.core.Observable;
import java.util.List;
import retrofit2.http.GET;
public interface ApiService {
    @GET("firebase/api/catalogos/Sanit_abastecimiento")
    Observable<List<Abastecimiento>> fetchCatalogos();
}
