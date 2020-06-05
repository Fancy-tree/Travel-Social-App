package com.example.travelsocialapp.ui.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.travelsocialapp.R;
import com.example.travelsocialapp.ui.CityInfoActivity;
import com.example.travelsocialapp.ui.SearchCityActivity;


public class LocationFragment extends Fragment implements View.OnClickListener{
    private TextView location_locationT;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location,null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        location_locationT = (TextView)view.findViewById(R.id.location_location);

        view.findViewById(R.id.location_city_info_img).setOnClickListener(this);
        view.findViewById(R.id.location_city_info_text).setOnClickListener(this);
        view.findViewById(R.id.location_location).setOnClickListener(this);
        view.findViewById(R.id.location_location_img).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.location_city_info_img || v.getId()==R.id.location_city_info_text){
            Intent intent = new Intent(getActivity(), CityInfoActivity.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.location_location || v.getId()==R.id.location_location_img){
            Intent intent = new Intent(getActivity(), SearchCityActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        if(getActivity()!=null){
            //读取用户选取的城市名
            SharedPreferences geteditorsp = getActivity().getSharedPreferences("user_setting",getActivity().MODE_PRIVATE);
            String city = geteditorsp.getString("city","定位");
//        showToast(city);
            //如果用户选择了其他城市
            if(!city.equals(location_locationT.getText().toString())){
                location_locationT.setText(city);
            }


        }else {
            Log.i("LocationFragment","getActivity()==null");
        }


    }

}
