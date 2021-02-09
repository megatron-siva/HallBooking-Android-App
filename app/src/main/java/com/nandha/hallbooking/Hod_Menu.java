package com.nandha.hallbooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
public class Hod_Menu extends Fragment  {
    CardView btn1,btn2,btn3,btn4;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_hod_menu, container, false);
        btn1 = v.findViewById(R.id.hodmenubtn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Bookings.class));
            }
        });
        btn2 = v.findViewById(R.id.hodmenubtn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Request.class));
            }
        });
        btn3 = v.findViewById(R.id.hodmenubtn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),HallSelecting.class));
            }
        });
        btn4 = v.findViewById(R.id.hodmenubtn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Approvals.class));
            }
        });
        return v;

    }

}
