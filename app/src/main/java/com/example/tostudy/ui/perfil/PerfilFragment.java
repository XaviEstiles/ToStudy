package com.example.tostudy.ui.perfil;

import static java.lang.System.load;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
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
import com.example.tostudy.R;
import com.example.tostudy.data.Repository.ObjetivoRepositoryRoom;
import com.example.tostudy.data.model.Objetivo;
import com.example.tostudy.databinding.FragmentPerfilBinding;
import com.example.tostudy.ui.base.OnRepositoryCallBack;
import com.example.tostudy.ui.base.OnRepositoryListCallBack;;import java.util.ArrayList;
import java.util.List;

public class PerfilFragment extends Fragment implements OnRepositoryListCallBack {

    FragmentPerfilBinding binding;

    List<Objetivo> list;

    public PieChart pie;

    int cumpl = 0;
    int noCumpl = 0;

    private Segment s1;
    private Segment s2;
    SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getActivity().getSharedPreferences("com.example.tostudy.PREFERENCES_FILE_KEY", Context.MODE_PRIVATE);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("ToStudy");
        toolbar.setVisibility(View.VISIBLE);

        binding = FragmentPerfilBinding.inflate(getLayoutInflater());

        list = ObjetivoRepositoryRoom.getInstance(this).getObjetivos();

        binding.tvEmail.setText(prefs.getString("Email","example@gmail.com"));
        binding.tvNombre.setText(prefs.getString("Name","example"));

        Glide.with(getContext())
                .load(prefs.getString("Img",""))
                .error(R.drawable.imgperfil)
                .circleCrop()
                .into(binding.imgPerfil);

        list.forEach(item ->{
            if (item.getProgress() == 100F)
                cumpl++;
            else
                noCumpl++;
        });

        pie = binding.mySimplePieChart;

        final float paddingTop = PixelUtils.dpToPix(60);
        final float padding = PixelUtils.dpToPix(30);
        pie.getPie().setPadding(padding, paddingTop, padding, padding);

        if (cumpl == noCumpl){
            s1 = new Segment("Completados", 1);
            s2 = new Segment("No completados", 1);
        }else{
            s1 = new Segment("Completados", cumpl);
            s2 = new Segment("No completados", noCumpl);
        }

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

        if (cumpl == noCumpl || cumpl != 0)
            pie.addSegment(s1, sf1);
        if (cumpl == noCumpl || noCumpl != 0)
            pie.addSegment(s2, sf2);

        pie.getBorderPaint().setColor(Color.TRANSPARENT);
        pie.getBackgroundPaint().setColor(Color.TRANSPARENT);


        return binding.getRoot();
    }

    @Override
    public <T> void onSuccessLoad(List<T> list) {

    }

    @Override
    public void onDeleteSuccess(String msg) {

    }

    @Override
    public void onSuccess(String msg) {

    }

    @Override
    public void onFailure(String msg) {

    }
}