package com.example.proyectojavamvvm.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_selections")
public class UserSelection {
    @PrimaryKey
    public int idAbastecimiento;
    public int opcion; // 1: SI, 2: NP, 3: N/A
    public String fotoBase64;
}
