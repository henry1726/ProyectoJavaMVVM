package com.example.proyectojavamvvm.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "abastecimiento")
public class Abastecimiento {
    @PrimaryKey
    @SerializedName("id")
    public int idAbastecimiento;

    @SerializedName("nombre")
    public String nombre;

    public int opcionSeleccionada = 0; // 1 = SÍ, 2 = NP, 3 = N/A
    public String rutaImagen = "";
}
