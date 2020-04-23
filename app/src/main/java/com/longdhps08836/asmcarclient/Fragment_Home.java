package com.longdhps08836.asmcarclient;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.longdhps08836.asmcarclient.Adapter.Adapter_Car_Product;
import com.longdhps08836.asmcarclient.DAO.CarProductDAO;
import com.longdhps08836.asmcarclient.Model.Car_product;

import java.util.ArrayList;
import java.util.Collections;

public class Fragment_Home extends Fragment {

    RecyclerView rvCarProduct;

    ArrayList<Car_product> listCarProduct;

    CarProductDAO carProductDAO ;

    public static Adapter_Car_Product adapter_car_product;

    ArrayList<Car_product> searchList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_home, container, false);

        connectLayout(view);

        rvCarProduct.setHasFixedSize(true);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvCarProduct.setLayoutManager(layoutManager);

        carProductDAO = new CarProductDAO(getContext());

        updateListCarProduct();

        return view;
    }

    private void connectLayout(View view) {
        rvCarProduct = view.findViewById(R.id.rvCarProduct);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Your menu needs to be added here
        inflater.inflate(R.menu.menu_home, menu);

        MenuItem itemSearch = menu.findItem(R.id.menu_search_home);
        SearchView searchView = (SearchView) itemSearch.getActionView();

        searchView.setQueryHint("Please enter the car looking");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(getContext(), "" + newText, Toast.LENGTH_SHORT).show();
                searchList = new ArrayList<Car_product>();
                if (newText.length() == 0) {
                    searchList = listCarProduct;
                } else {
                    for (Car_product itemCar_Product : listCarProduct) {
                        if (itemCar_Product.getNameCar().toLowerCase().contains(newText.toLowerCase())) {
                            searchList.add(itemCar_Product);
                        }
                    }
                }

                adapter_car_product = new Adapter_Car_Product(getContext(), searchList);
                rvCarProduct.setAdapter(adapter_car_product);
                return true;
            }
        });



        // tăng kích thước item trong menu
        // tham khảo https://stackoverflow.com/questions/18301329/changing-text-size-of-menu-item-in-android
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
            int end = spanString.length();
            spanString.setSpan(new RelativeSizeSpan(1.5f), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            item.setTitle(spanString);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

//        switch (item.getItemId()) {
//            case R.id.mnuProductAdd:
//                Toast.makeText(getContext(), " Product Add ", Toast.LENGTH_SHORT).show();
//                break;
//        }

        return super.onOptionsItemSelected(item);
    }

    public void updateListCarProduct() {


        listCarProduct = carProductDAO.getAllCarProduct();
        Collections.reverse(listCarProduct);  //đảo ngược danh sách khi mới thêm vào
        Log.d("listCarInfor", String.valueOf(listCarProduct));
        adapter_car_product = new Adapter_Car_Product(getContext(), listCarProduct);
        rvCarProduct.setAdapter(adapter_car_product);
        adapter_car_product.notifyDataSetChanged();
        Log.d("rvCarProduct", String.valueOf(rvCarProduct));


    }

    public static void updateListAdapterCarProduct() {

        adapter_car_product.notifyDataSetChanged();
    }
}
