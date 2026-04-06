package com.example.proyectojavamvvm.ui.downloadConsultCat;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.example.proyectojavamvvm.R;
import com.example.proyectojavamvvm.databinding.ActivityDescargaYConsultaCatBinding;
import com.example.proyectojavamvvm.ui.consultCat.ConsultaCatalogoActivity;
import com.example.proyectojavamvvm.ui.downloadCat.DescargaCatalogoActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DescargaYConsultaCatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDescargaYConsultaCatBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_descarga_y_consulta_cat);

        binding.btnDescargar.setOnClickListener(v ->
                startActivity(new Intent(this, DescargaCatalogoActivity.class)));

        binding.btnConsultar.setOnClickListener(v ->
                startActivity(new Intent(this, ConsultaCatalogoActivity.class)));
    }
}
