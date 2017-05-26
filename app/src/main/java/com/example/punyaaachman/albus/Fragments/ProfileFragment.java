package com.example.punyaaachman.albus.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.punyaaachman.albus.R;

import static com.example.punyaaachman.albus.POJO.GlobalVariables.profile;

/**
 * Created by Punya Aachman on 12-Apr-17.
 */

public class ProfileFragment extends Fragment
{

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        return rootView;
    }
}
