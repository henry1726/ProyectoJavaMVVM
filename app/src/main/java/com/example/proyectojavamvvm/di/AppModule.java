package com.example.proyectojavamvvm.di;

import android.content.Context;
import androidx.room.Room;
import com.example.proyectojavamvvm.data.local.AbastecimientoDao;
import com.example.proyectojavamvvm.data.local.AppDatabase;
import com.example.proyectojavamvvm.data.network.ApiService;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {
    @Provides @Singleton
    public static ApiService provideApi() {
        return new Retrofit.Builder()
                .baseUrl("https://www.calymayor.com.mx/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build().create(ApiService.class);
    }

    @Provides @Singleton
    public static AppDatabase provideDb(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "abastecimiento_db").build();
    }

    @Provides @Singleton
    public static AbastecimientoDao provideDao(AppDatabase db) {
        return db.dao();
    }
}
