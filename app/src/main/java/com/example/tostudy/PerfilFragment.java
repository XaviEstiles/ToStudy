package com.example.tostudy;

import static java.lang.System.load;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.androidplot.pie.PieChart;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.androidplot.util.PixelUtils;
import com.bumptech.glide.Glide;
import com.example.tostudy.databinding.FragmentPerfilBinding;;

public class PerfilFragment extends Fragment {

    FragmentPerfilBinding binding;

    public PieChart pie;

    private Segment s1;
    private Segment s2;
    SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getActivity().getSharedPreferences("com.example.tostudy.PREFERENCES_FILE_KEY", Context.MODE_PRIVATE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("ToStudy");
        toolbar.setVisibility(View.VISIBLE);

        binding = FragmentPerfilBinding.inflate(getLayoutInflater());

        binding.tvEmail.setText(prefs.getString("Email","example@gmail.com"));

        pie = binding.mySimplePieChart;

        final float paddingTop = PixelUtils.dpToPix(60);
        final float padding = PixelUtils.dpToPix(30);
        pie.getPie().setPadding(padding, paddingTop, padding, padding);

        Glide.with(getContext())
                .load("http://vps-9e48c221.vps.ovh.net/fotos-perfil/prueba.jpg")
                .error(R.drawable.imgperfil)
                .circleCrop()
                .into(binding.imgPerfil);

        s1 = new Segment("Completados", 3);
        s2 = new Segment("No completados", 1);

        EmbossMaskFilter emf = new EmbossMaskFilter(
                new float[]{1, 1, 1}, 0.4f, 10, 2f);

        SegmentFormatter sf1 = new SegmentFormatter(R.color.primaryDarkColor);
        sf1.getLabelPaint().setTextSize(30);
        sf1.getLabelPaint().setColor(Color.BLACK);
        sf1.getFillPaint().setColor(Color.argb(100,0,149,135));
        sf1.getRadialEdgePaint().setColor(Color.WHITE);
        sf1.getInnerEdgePaint().setColor(Color.WHITE);
        sf1.getOuterEdgePaint().setColor(Color.WHITE);


        SegmentFormatter sf2 = new SegmentFormatter(R.color.primaryDarkColor);
        sf2.getLabelPaint().setTextSize(30);
        sf2.getLabelPaint().setColor(Color.BLACK);
        sf2.getFillPaint().setColor(Color.argb(100,105, 240, 174));
        sf2.getRadialEdgePaint().setColor(Color.WHITE);
        sf2.getInnerEdgePaint().setColor(Color.WHITE);
        sf2.getOuterEdgePaint().setColor(Color.WHITE);


        pie.addSegment(s1, sf1);
        pie.addSegment(s2, sf2);

        pie.getBorderPaint().setColor(Color.TRANSPARENT);
        pie.getBackgroundPaint().setColor(Color.TRANSPARENT);

        return binding.getRoot();
    }
}