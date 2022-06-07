package com.example.tostudy.ui.objetivos.ObjetivosManage;

import android.app.DatePickerDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.tostudy.R;
import com.example.tostudy.broadcastreciver.TemporizadorServiceObj;
import com.example.tostudy.data.model.Objetivo;
import com.example.tostudy.data.model.Prioridad;
import com.example.tostudy.databinding.FragmentEditarObjetivoBinding;
import com.example.tostudy.ui.main.MainActivity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;


public class EditarObjetivosFragment extends Fragment implements MainActivity.OnBackPressedListener, ObjManageContract.View{

    private final int JOBID = 1;
    FragmentEditarObjetivoBinding binding;
    NavController navController;
    Objetivo objetivo;

    ObjManageContract.Presenter presenter;


    final Calendar calendario = Calendar.getInstance();
    int anio = calendario.get(Calendar.YEAR);
    int mes = calendario.get(Calendar.MONTH);
    int diaDelMes = calendario.get(Calendar.DAY_OF_MONTH);
    int hora = calendario.get(Calendar.HOUR_OF_DAY);
    int min = calendario.get(Calendar.MINUTE);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).setOnBackPressedListener(this);
        binding = FragmentEditarObjetivoBinding.inflate(getLayoutInflater());
        navController = Navigation.findNavController(getActivity(), R.id.nav_host);
        presenter = new ObjManagePresenter(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        objetivo = (Objetivo) getArguments().getSerializable("obj");

        if (objetivo != null){
            binding.tilNombreObj.getEditText().setText(objetivo.getNombre());
            binding.tilFechaObj.getEditText().setText(objetivo.getFecha());
            binding.tilDescrObj.getEditText().setText(objetivo.getDescripcion());
            binding.spPrioridades.setSelection(objetivo.getPrioridad().getIndice());
            iniBtnEditar();
        }else{
            objetivo = new Objetivo();

            binding.tilFechaObj.getEditText().setText(String.format("%02d", diaDelMes) + "/" + String.format("%02d", (mes+1)) + "/" + String.format("%04d", anio));
            iniBtnAdd();
        }

        binding.imgDataPicker.setOnClickListener(v-> showDatePickerDialog());

        return binding.getRoot();
    }

    void iniBtnEditar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.btnAnadirObj.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));
            binding.btnAnadirObj.setOnClickListener(v -> {
                objetivo.setNombre(binding.tilNombreObj.getEditText().getText().toString());
                objetivo.setFecha(binding.tilFechaObj.getEditText().getText().toString());
                objetivo.setDescripcion(binding.tilDescrObj.getEditText().getText().toString());
                switch (binding.spPrioridades.getSelectedItem().toString()){
                    case "Baja":
                        objetivo.setPrioridad(Prioridad.BAJA);
                        break;
                    case "Alta":
                        objetivo.setPrioridad(Prioridad.ALTA);
                        break;
                    case "Media":
                        objetivo.setPrioridad(Prioridad.MEDIA);
                        break;
                    default:
                        objetivo.setPrioridad(Prioridad.BAJA);
                        break;
                }
                presenter.edit(objetivo);
                navController.navigate(R.id.objetivoFragment);
            });
        }
    }

    void iniBtnAdd(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.btnAnadirObj.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
            binding.btnAnadirObj.setOnClickListener(v -> {

                binding.tilNombreObj.setError(null);
                binding.tilFechaObj.setError(null);

                objetivo.setNombre(binding.tilNombreObj.getEditText().getText().toString());
                objetivo.setFecha(binding.tilFechaObj.getEditText().getText().toString());
                objetivo.setDescripcion(binding.tilDescrObj.getEditText().getText().toString());
                switch (binding.spPrioridades.getSelectedItem().toString()){
                    case "Baja":
                        objetivo.setPrioridad(Prioridad.BAJA);
                        break;
                    case "Alta":
                        objetivo.setPrioridad(Prioridad.ALTA);
                        break;
                    case "Media":
                        objetivo.setPrioridad(Prioridad.MEDIA);
                        break;
                    default:
                        objetivo.setPrioridad(Prioridad.BAJA);
                        break;
                }
                presenter.add(objetivo);
                iniJob();
                navController.navigate(R.id.objetivoFragment);
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void iniJob() {
        ComponentName componentName = new ComponentName(getActivity(), TemporizadorServiceObj.class);
        JobInfo.Builder builder = new JobInfo.Builder(JOBID,componentName);

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime date = LocalDateTime.parse(objetivo.getFecha()+" 09:00",format);

        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()-Calendar.getInstance().getTimeInMillis());

        builder.setMinimumLatency(now.getTimeInMillis());
        builder.setOverrideDeadline(now.getTimeInMillis());

        JobScheduler jobScheduler = (JobScheduler) getActivity().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());

    }

    private void showDatePickerDialog() {
        DatePickerDialog dialogoFecha = new DatePickerDialog(getContext(), listenerDeDatePicker, anio, mes, diaDelMes);
        dialogoFecha.show();
    }
    private DatePickerDialog.OnDateSetListener listenerDeDatePicker = (view, anio, mes, diaDelMes) -> {
        final String selectedDate = String.format("%02d", diaDelMes) + "/" + String.format("%02d", (mes+1)) + "/" + String.format("%02d", anio);
        binding.tvFecha.setText(selectedDate);
    };

    @Override
    public void onBackPressed() {
       /* getActivity().findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
        ((MainActivity)getActivity()).setOnBackPressedListener(null);*/
    }

    @Override
    public <T> void onSuccessLoad(List<T> list) {

    }

    @Override
    public void onDeleteSuccess(String msg) {

    }

    @Override
    public void onSuccess(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setNombreEmpty() {
        binding.tilNombreObj.setError("EL nombre no puede estar vacio");
    }

    @Override
    public void setFechaEmpty() {
        binding.tilFechaObj.setError("La fecha no puede estar vacia");
    }

    @Override
    public void setFechaErr() {
        binding.tilFechaObj.setError("La fecha no puede ser menor que la fecha actual");
    }
}