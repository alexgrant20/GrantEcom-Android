package com.binus.grantecom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.DatePickerDialog;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class ProductDetailFragment extends Fragment {

    TextView productPrice, productDescription, productTitle;
    ImageView productImage;
    String price, description, title, image;

    public void ProductDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();

        price = bundle.getString("productPrice");
        description = bundle.getString("productDescription");
        title = bundle.getString("productTitle");
        image = bundle.getString("productImage");

        View rootView = inflater.inflate(R.layout.fragment_product_detail, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productPrice = getView().findViewById(R.id.product_price);
        productTitle = getView().findViewById(R.id.product_title);
        productDescription = getView().findViewById(R.id.product_description);
        productImage = getView().findViewById(R.id.product_image);

        productPrice.setText(price);
        productDescription.setText(description);
        productTitle.setText(title);
        Picasso.get().load(image).into(productImage);

    }
}
