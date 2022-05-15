package com.example.tostudy.ui.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class BaseDialogFragment extends DialogFragment {
    public static final String KEY="recuestDialog";
    public static final String KEY_BUNDLE="result";
    public static final String TITLE="title";
    public static final String MESSAGE="message";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        if(getArguments() != null){
            String title = getArguments().getString(TITLE);
            String msg = getArguments().getString(MESSAGE);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(title);
            builder.setMessage(msg);
            builder.setNegativeButton("Cancelar",(dialogInterface, i) -> {
                dismiss();
            });
            builder.setPositiveButton("Aceptar",(dialogInterface, i) -> {
                Bundle bundle = new Bundle();
                bundle.putBoolean(KEY_BUNDLE,true);
                getActivity().getSupportFragmentManager().setFragmentResult(KEY,bundle);
            });
            return builder.create();
        }
        return null;
    }
}
