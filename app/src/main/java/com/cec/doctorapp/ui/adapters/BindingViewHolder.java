package com.cec.doctorapp.ui.adapters;


import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

public abstract class BindingViewHolder extends RecyclerView.ViewHolder {

    protected final ViewBinding binding;

    public BindingViewHolder(ViewBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }

    abstract void bind();

}
