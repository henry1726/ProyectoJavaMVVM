package com.example.proyectojavamvvm.ui.downloadCat;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.proyectojavamvvm.R;
import com.example.proyectojavamvvm.databinding.ActivityDescargaCatalogoBinding;
import com.example.proyectojavamvvm.util.Resource;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DescargaCatalogoActivity extends AppCompatActivity {

    private ActivityDescargaCatalogoBinding binding;
    private DownloadViewModel viewModel;
    private DownloadAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_descarga_catalogo);
        viewModel = new ViewModelProvider(this).get(DownloadViewModel.class);
        setupRecyclerView();

        viewModel.status.observe(this, resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case LOADING:
                        showLoading();
                        break;
                    case SUCCESS:
                        showSuccess(resource.data);
                        break;
                    case ERROR:
                        showError(resource.message);
                        break;
                }
            }
        });

        viewModel.startSync();
    }

    private void setupRecyclerView() {
        adapter = new DownloadAdapter();
        binding.rvDownload.setLayoutManager(new LinearLayoutManager(this));
        binding.rvDownload.setAdapter(adapter);
    }

    private void showLoading() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.viewError.setVisibility(View.GONE);
        binding.rvDownload.setVisibility(View.GONE);
    }

    private void showSuccess(List data) {
        binding.progressBar.setVisibility(View.GONE);
        binding.viewError.setVisibility(View.GONE);
        binding.rvDownload.setVisibility(View.VISIBLE);
        adapter.setList(data);
    }

    private void showError(String message) {
        binding.progressBar.setVisibility(View.GONE);
        binding.rvDownload.setVisibility(View.GONE);
        binding.viewError.setVisibility(View.VISIBLE);

        // Mostrar Dialog con el error detallado
        new AlertDialog.Builder(this)
                .setTitle("Error de Descarga")
                .setMessage("No se pudo obtener el catálogo: " + message)
                .setPositiveButton("Aceptar", null)
                .setNeutralButton("Reintentar", (dialog, which) -> viewModel.startSync())
                .show();
    }
}
