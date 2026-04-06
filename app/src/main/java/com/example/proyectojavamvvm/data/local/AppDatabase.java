package com.example.proyectojavamvvm.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.proyectojavamvvm.data.model.Abastecimiento;
import com.example.proyectojavamvvm.data.model.UserSelection;

@Database(entities = {Abastecimiento.class, UserSelection.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AbastecimientoDao dao();
}
