package com.example.quanlychitieu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.quanlychitieu.R;
import com.example.quanlychitieu.holders.CategoryTypeExpenseHolder;
import com.example.quanlychitieu.holders.CategoryTypeIncomeHolder;
import com.example.quanlychitieu.models.CategoryRoot;
import com.example.quanlychitieu.models.CategoryType;

import java.util.List;

public class CategoryTypeIncomeAdapter extends RecyclerView.Adapter<CategoryTypeIncomeHolder> {
    private Context context;
    private List<CategoryType> categoryTypes;

    public CategoryTypeIncomeAdapter(List<CategoryType> categoryTypes) {
        this.categoryTypes = categoryTypes;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryTypeIncomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_category_type_income_item, parent, false);
        return new CategoryTypeIncomeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryTypeIncomeHolder holder, int position) {
        CategoryType categoryType = categoryTypes.get(position);

        holder.getCategoryTypeIncomeItemTitle().setText(categoryType.getName());
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.app_icon_background) // Replace with your placeholder image resource
                .error(R.drawable.app_icon_background); // Replace with your error image resource

        Glide.with(context)
                .load(categoryType.getImageLink())
                .apply(requestOptions)
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.getCategoryTypeIncomeItemImage());
    }

    @Override
    public int getItemCount() {
        return categoryTypes == null ? 0 : categoryTypes.size();
    }
}