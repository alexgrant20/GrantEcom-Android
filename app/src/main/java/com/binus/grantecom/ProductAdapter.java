package com.binus.grantecom;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<ProductHelperClass> products;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View menuView = layoutInflater.inflate(R.layout.item_product_list, parent,false);
        ViewHolder viewHolder = new ViewHolder(menuView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductHelperClass product = products.get(position);

        TextView textViewName = holder.productName;
        textViewName.setText(product.getTitle());

        TextView textViewPrice = holder.productPrice;
        textViewPrice.setText("$. " + product.getPrice());

        ImageView imageViewProduct = holder.productImage;
        Picasso.get().load(product.getImage()).into(imageViewProduct);

        holder.buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productId = product.getId();
                Bundle bundle = new Bundle();
                bundle.putString("productTitle", product.getTitle());
                bundle.putString("productDescription", product.getDescription());
                bundle.putString("productPrice", product.getPrice());
                bundle.putString("productImage", product.getImage());

                ProductDetailFragment productDetailFragment = new ProductDetailFragment();
                productDetailFragment.setArguments(bundle);

                FragmentManager fragmentManager = ((MainActivity) view.getContext()).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, productDetailFragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (products == null) return 0;
        return products.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView productName, productPrice;
        private ImageView productImage;
        private Button buttonView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.text_product_name);
            productPrice = (TextView) itemView.findViewById(R.id.text_product_price);
            productImage = (ImageView) itemView.findViewById(R.id.product_image);
            buttonView = (Button) itemView.findViewById(R.id.btn_view);
        }
    }

    public ProductAdapter(List<ProductHelperClass> products){
        this.products = products;
    }
}
