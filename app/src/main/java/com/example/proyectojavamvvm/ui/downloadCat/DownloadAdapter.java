package com.example.proyectojavamvvm.ui.downloadCat;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyectojavamvvm.data.model.Abastecimiento;
import com.example.proyectojavamvvm.databinding.ItemDownloadBinding;
import java.util.ArrayList;
import java.util.List;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.DownloadViewHolder>{

    private List<Abastecimiento> listaAbastecimiento = new ArrayList<>();

    //Método para actualizar la lista cuando el ViewModel recibe datos del API
    @SuppressWarnings("NotifyDataSetChanged")
    public void setList(List<Abastecimiento> nuevaLista) {
        this.listaAbastecimiento = nuevaLista;
        notifyDataSetChanged(); // En proyectos más grandes usar DiffUtil
    }

    @NonNull
    @Override
    public DownloadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemDownloadBinding binding = ItemDownloadBinding.inflate(layoutInflater, parent, false);
        return new DownloadViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadViewHolder holder, int position) {
        Abastecimiento item = listaAbastecimiento.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return listaAbastecimiento != null ? listaAbastecimiento.size() : 0;
    }

    // ViewHolder que usa DataBinding para asignar el objeto a la vista
    public static class DownloadViewHolder extends RecyclerView.ViewHolder {
        private final ItemDownloadBinding binding;

        public DownloadViewHolder(ItemDownloadBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Abastecimiento item) {
            binding.tvIdAbastecimiento.setText("ID: " + item.idAbastecimiento);
            binding.tvTipoAbastecimiento.setText(item.tipoAbastecimiento);
            binding.tvUsuarioCreacion.setText("Creado por: " + item.usuarioCreacion);
            binding.tvFechaCreacion.setText("Fecha: " + item.fechaCreacion);
            binding.tvUsuarioMod.setText("Mod: " + (item.usuarioModificacion.isEmpty() ? "---" : item.usuarioModificacion));
            binding.tvFechaMod.setText("F. Mod: " + (item.fechaModificacion.isEmpty() ? "---" : item.fechaModificacion));
            binding.tvUsuarioElim.setText("Elim: " + (item.usuarioEliminacion.isEmpty() ? "---" : item.usuarioEliminacion));
        }
    }
}
