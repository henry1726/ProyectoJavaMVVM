package com.example.proyectojavamvvm.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AbastecimientoResponse {
    @SerializedName("Sanit_abastecimiento")
    public List<Abastecimiento> abastecimientoList;

    @SerializedName("success")
    public int success;

    @SerializedName("message")
    public String message;
}
