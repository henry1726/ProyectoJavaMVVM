package com.example.proyectojavamvvm.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "abastecimiento")
public class Abastecimiento {
    @PrimaryKey @SerializedName("idAbastecimiento") public int idAbastecimiento;
    @SerializedName("tipoAbastecimiento") public String tipoAbastecimiento;
    @SerializedName("usuarioCreacion") public String usuarioCreacion;
    @SerializedName("usuarioModificacion") public String usuarioModificacion;
    @SerializedName("usuarioEliminacion") public String usuarioEliminacion;
    @SerializedName("fechaCreacion") public String fechaCreacion;
    @SerializedName("fechaModificacion") public String fechaModificacion;
    @SerializedName("fechaEliminacion") public String fechaEliminacion;
}

