package com.cec.doctorapp.ui.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cec.doctorapp.databinding.BookedAdapterBinding;
import com.cec.doctorapp.model.response.DataBookModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BookedConsultAdapter extends RecyclerView.Adapter<BookedConsultAdapter.ViewHolder> {
    
    private final ArrayList<DataBookModel> dataList = new ArrayList<>();
    private final BookInterface bookInterface;
    
    public interface BookInterface{
        void book(DataBookModel dataBookModel,int position);
    }

    public BookedConsultAdapter(ArrayList<DataBookModel> dataList, BookInterface bookInterface) {
        this.dataList.addAll(dataList);
        this.bookInterface=bookInterface;
    }


    @NonNull
    @Override
    public BookedConsultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    BookedAdapterBinding  binding= BookedAdapterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BookedConsultAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookedConsultAdapter.ViewHolder holder, int position) {
        holder.bind();

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends BindingViewHolder {


        public ViewHolder(@NotNull ViewBinding binding) {
            super(binding);
        }

        @Override
        public void bind() {
            DataBookModel list = dataList.get(getAdapterPosition());
            if (binding instanceof BookedAdapterBinding) {
               BookedAdapterBinding mBinding = ((BookedAdapterBinding) binding);
               mBinding.textTit.setText(list.bookTime);
                Log.d("TAG", "set");
//                mBinding.card.setOnClickListener(v -> {
//                    if(bookInterface!=null){
//                        bookInterface.book(dataList.get(getAdapterPosition()),getAdapterPosition());
//                    }
//                });

            }

        }
    }
}