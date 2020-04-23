package com.longdhps08836.asmcarclient;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.widget.EditText;

import android.widget.Toast;

import com.longdhps08836.asmcarclient.Adapter.Adapter_Car_User;
import com.longdhps08836.asmcarclient.DAO.CarUserDAO;
import com.longdhps08836.asmcarclient.Model.Car_user;

import java.util.ArrayList;

public class Fragment_Profile_Client extends Fragment {

    RecyclerView rvCarUser;
    CarUserDAO carUserDAO;

    public static ArrayList<Car_user> listCarUser;

    public static Adapter_Car_User adapter_car_user;

    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;


    Bitmap bm;

    SearchView searchView;

    ArrayList<Car_user> searchlist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_profile_client, container, false);

        connectLayout(view);

        rvCarUser.setHasFixedSize(true);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvCarUser.setLayoutManager(layoutManager);

        carUserDAO = new CarUserDAO(getContext());


        updateListClient();


        return view;
    }

    private void connectLayout(View view) {
        rvCarUser = view.findViewById(R.id.rvCarUser);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Your menu needs to be added here
        inflater.inflate(R.menu.menu_profile, menu);

        MenuItem itemSearch = menu.findItem(R.id.menu_search);
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
                searchlist = new ArrayList<Car_user>();
                if (newText.length() == 0) {
                    searchlist = listCarUser;
                } else {
                    for (Car_user car_user : listCarUser) {
                        if (car_user.getCarName().toLowerCase().contains(newText.toLowerCase())) {
                            searchlist.add(car_user);
                        }
                    }
                }

                adapter_car_user = new Adapter_Car_User(getContext(), searchlist);
                rvCarUser.setAdapter(adapter_car_user);
                return true;
            }
        });


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

        switch (item.getItemId()) {
            case R.id.mnLogoutProfile:
                getActivity().finish();
                break;

            case R.id.mnuProductAdd:
                Toast.makeText(getContext(), " Product Add ", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(), ActivityAddCarUser.class);
                startActivity(intent);


                break;


        }

        return super.onOptionsItemSelected(item);
    }

    public void updateListClient() {


        listCarUser = carUserDAO.getAllCarInfor();

        Log.d("listCarInfor", String.valueOf(listCarUser));
        adapter_car_user = new Adapter_Car_User(getContext(), listCarUser);
        rvCarUser.setAdapter(adapter_car_user);
        adapter_car_user.notifyDataSetChanged();
        Log.d("rvCarProduct", String.valueOf(rvCarUser));


//        Collections.reverse(listCarInfor);  đảo ngược danh sách khi mới thêm vào
    }

    public static void updateListAdapter() {

        adapter_car_user.notifyDataSetChanged();
    }


}
