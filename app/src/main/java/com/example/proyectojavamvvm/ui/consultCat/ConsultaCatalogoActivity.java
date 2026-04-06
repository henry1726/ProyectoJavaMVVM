package com.example.proyectojavamvvm.ui.consultCat;

import androidx.appcompat.app.AppCompatActivity;
import dagger.hilt.android.AndroidEntryPoint;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import com.example.proyectojavamvvm.R;
import com.example.proyectojavamvvm.data.model.Abastecimiento;
import com.example.proyectojavamvvm.data.model.UserSelection;
import com.example.proyectojavamvvm.databinding.ActivityConsultCatBinding;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AndroidEntryPoint
public class ConsultaCatalogoActivity extends AppCompatActivity {
    private ActivityConsultCatBinding binding;
    private ConsultCatViewModel viewModel;

    // Mapa para guardar idAbastecimiento -> opción seleccionada (1=SI, 2=NO, 3=N/A)
    private final Map<Integer, Integer> seleccionesActuales = new HashMap<>();
    private String fotoBase64 = "";

    // Lanzador para la cámara
    private final ActivityResultLauncher<Void> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.TakePicturePreview(),
            bitmap -> {
                if (bitmap != null) {
                    binding.imgCamera.setImageBitmap(bitmap);
                    fotoBase64 = convertirBitmapABase64(bitmap);
                }
            }
    );

    private final ActivityResultLauncher<String> permissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    cameraLauncher.launch(null);
                } else {
                    mostrarDialogoPermiso(); // Ciclo recursivo si niega
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_consult_cat);
        viewModel = new ViewModelProvider(this).get(ConsultCatViewModel.class);

        binding.imgCamera.setOnClickListener(v -> verificarPermisoCamara());

        binding.btnSave.setOnClickListener(v -> guardarDatos());

        viewModel.catalog.observe(this, this::construirTabla);

        viewModel.saveSuccess.observe(this, success -> {
            if(success) {
                Toast.makeText(this, "Guardado correctamente en Room", Toast.LENGTH_SHORT).show();
                finish(); // Cerramos activity al guardar exitosamente
            }
        });

        viewModel.loadLocalCatalog();

    }

    // Método para crear la tabla dinámicamente sin RecyclerView
    private void construirTabla(List<Abastecimiento> catalog) {
        binding.llTableContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Abastecimiento item : catalog) {
            View rowView = inflater.inflate(R.layout.item_table_row, binding.llTableContainer, false);

            TextView tvName = rowView.findViewById(R.id.tvItemName);
            RadioGroup radioGroup = rowView.findViewById(R.id.radioGroup);

            tvName.setText(item.tipoAbastecimiento);

            // Escuchar cambios en los RadioButtons
            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                int opcion = 0;
                if (checkedId == R.id.rbSi) opcion = 1;
                else if (checkedId == R.id.rbNo) opcion = 2;
                else if (checkedId == R.id.rbNa) opcion = 3;

                seleccionesActuales.put(item.idAbastecimiento, opcion);
            });

            binding.llTableContainer.addView(rowView);
        }
    }

    // Lógica del permiso como solicitaste (loop amigable)
    private void verificarPermisoCamara() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            cameraLauncher.launch(null);
        } else {
            mostrarDialogoPermiso();
        }
    }

    private void mostrarDialogoPermiso() {
        new AlertDialog.Builder(this)
                .setTitle("Permiso Requerido")
                .setMessage("Es muy importante otorgar el permiso de la cámara para tomar la evidencia de la consulta. Por favor, acéptalo.")
                .setPositiveButton("Dar Permiso", (dialog, which) -> permissionLauncher.launch(Manifest.permission.CAMERA))
                .setNegativeButton("Cancelar", null)
                .show();
    }

    // Guardado y conversión de datos
    private void guardarDatos() {
        if (seleccionesActuales.isEmpty()) {
            Toast.makeText(this, "Selecciona al menos una opción", Toast.LENGTH_SHORT).show();
            return;
        }

        List<UserSelection> listaAGuardar = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : seleccionesActuales.entrySet()) {
            UserSelection selection = new UserSelection();
            selection.idAbastecimiento = entry.getKey();
            selection.opcion = entry.getValue();
            selection.fotoBase64 = this.fotoBase64; // Se guarda la misma foto en cada selección según las instrucciones
            listaAGuardar.add(selection);
        }

        viewModel.saveSelections(listaAGuardar);
    }

    private String convertirBitmapABase64(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteArray = stream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}


