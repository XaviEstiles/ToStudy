package com.example.tostudy.ui.eventos.EventosManage;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.tostudy.R;
import com.example.tostudy.broadcastreciver.TemporizadorServiceEve;
import com.example.tostudy.data.model.Evento;
import com.example.tostudy.databinding.FragmentEditarEventoBinding;
import com.example.tostudy.ui.main.MainActivity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

public class EditarEventoFragment extends Fragment implements MainActivity.OnBackPressedListener, EveManageContract.View {

    private final int JOBID = 1;
    FragmentEditarEventoBinding binding;
    Evento evento;
    Boolean newEve = false;
    NavController navController;
    EveManageContract.Presenter presenter;
    SharedPreferences prefs;

    final Calendar calendario = Calendar.getInstance();
    int anio = calendario.get(Calendar.YEAR);
    int mes = calendario.get(Calendar.MONTH);
    int diaDelMes = calendario.get(Calendar.DAY_OF_MONTH);
    int hora = calendario.get(Calendar.HOUR_OF_DAY);
    int min = calendario.get(Calendar.MINUTE);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Toolbar) getActivity().findViewById(R.id.toolbar)).setTitle("ToStudy");
        ((MainActivity)getActivity()).setOnBackPressedListener(this);
        binding = FragmentEditarEventoBinding.inflate(getLayoutInflater());
        presenter = new EveManagePresenter(this);
        prefs = getActivity().getSharedPreferences("com.example.tostudy.PREFERENCES_FILE_KEY", Context.MODE_PRIVATE);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding.textInputLayout1.setError(null);
        binding.textInputLayout2.setError(null);
        binding.textInputLayout3.setError(null);
        binding.textInputLayout4.setError(null);
        binding.textInputLayout5.setError(null);

        navController = Navigation.findNavController(getActivity(),R.id.nav_host);

        evento = (Evento) getArguments().getSerializable("eve");
        if (evento != null){
            binding.textInputLayout1.getEditText().setText(evento.getName());
            binding.textInputLayout2.getEditText().setText(evento.getStartTime());
            binding.textInputLayout3.getEditText().setText(evento.getFinishTime());
            binding.textInputLayout4.getEditText().setText(evento.getDate());
            binding.textInputLayout5.getEditText().setText(evento.getDescription());
            binding.spPrioridades.setSelection(evento.getPriority()-1);
            iniBtnEditar();
        }else{
            evento = new Evento();
            binding.textInputLayout2.getEditText().setText(String.format("%02d:%02d:00", hora, min));
            binding.textInputLayout3.getEditText().setText(String.format("%02d:%02d:00", hora+1, min));
            binding.textInputLayout4.getEditText().setText(String.format("%04d-%02d-%02d", anio, mes+1, diaDelMes));
            iniBtnAdd();
        }
        binding.imgDataPicker.setOnClickListener(v-> showDatePickerDialog());
        binding.imgHoraIni.setOnClickListener(v -> showTimePickerDialog(0));
        binding.imgHoraFin.setOnClickListener(v -> showTimePickerDialog(1));
        binding.linearLayout4.setOnClickListener(v -> showDatePickerDialog());
        binding.textInputLayout2.setOnClickListener(v -> showTimePickerDialog(0));
        binding.textInputLayout3.setOnClickListener(v -> showTimePickerDialog(1));

        return binding.getRoot();
    }

    void iniBtnEditar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.button.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));
            binding.button.setOnClickListener(v -> {

                binding.textInputLayout1.setError(null);
                binding.textInputLayout2.setError(null);
                binding.textInputLayout3.setError(null);
                binding.textInputLayout4.setError(null);
                binding.textInputLayout5.setError(null);

                evento.setName(binding.textInputLayout1.getEditText().getText().toString());
                evento.setStartTime(binding.textInputLayout2.getEditText().getText().toString());
                evento.setFinishTime(binding.textInputLayout3.getEditText().getText().toString());
                evento.setDate(binding.textInputLayout4.getEditText().getText().toString());
                evento.setDescription(binding.textInputLayout5.getEditText().getText().toString());
                switch (binding.spPrioridades.getSelectedItem().toString()){
                    case "Baja":
                        evento.setPriority(1);
                        break;
                    case "Alta":
                        evento.setPriority(3);
                        break;
                    case "Media":
                        evento.setPriority(2);
                        break;
                    default:
                        evento.setPriority(1);
                        break;
                }
                presenter.edit(evento);

            });
        }
    }

    void iniBtnAdd(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.button.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
            binding.button.setOnClickListener(v -> {

                binding.textInputLayout1.setError(null);
                binding.textInputLayout2.setError(null);
                binding.textInputLayout3.setError(null);
                binding.textInputLayout4.setError(null);
                binding.textInputLayout5.setError(null);

                evento.setName(binding.textInputLayout1.getEditText().getText().toString());
                evento.setUserId(Integer.parseInt(prefs.getString("IdUser", "1")));
                evento.setStartTime(binding.textInputLayout2.getEditText().getText().toString());
                evento.setFinishTime(binding.textInputLayout3.getEditText().getText().toString());
                evento.setDate(binding.textInputLayout4.getEditText().getText().toString());
                evento.setDescription(binding.textInputLayout5.getEditText().getText().toString());
                switch (binding.spPrioridades.getSelectedItem().toString()){
                    case "Baja":
                        evento.setPriority(1);
                        break;
                    case "Alta":
                        evento.setPriority(3);
                        break;
                    case "Media":
                        evento.setPriority(2);
                        break;
                    default:
                        evento.setPriority(1);
                        break;
                }
                presenter.add(evento);
                newEve=true;

            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void iniJob() {
        ComponentName componentName = new ComponentName(getActivity(), TemporizadorServiceEve.class);
        JobInfo.Builder builder = new JobInfo.Builder(JOBID,componentName);

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse(evento.getDate()+" "+evento.getStartTime(),format).minusDays(1);
        Log.d("Fecha", date.toString());
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()-Calendar.getInstance().getTimeInMillis());

        builder.setMinimumLatency(now.getTimeInMillis());
        builder.setOverrideDeadline(now.getTimeInMillis());

        JobScheduler jobScheduler = (JobScheduler) getActivity().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());

    }

    private void showTimePickerDialog(int num) {
        TimePickerDialog dialogoTime = null;
        switch (num){
            case 0:
                dialogoTime = new TimePickerDialog(getContext(), listenerDeTimePickerIni,hora,min,true);
                break;
            case 1:
                dialogoTime = new TimePickerDialog(getContext(), listenerDeTimePickerFin,hora,min,true);
                break;
        }
        dialogoTime.show();
    }
    private TimePickerDialog.OnTimeSetListener listenerDeTimePickerIni = (view, hora, min) -> {
        final String selectedDate = String.format("%02d:%02d:00", hora, min);
        binding.tvHoraIni.setText(selectedDate);
    };
    private TimePickerDialog.OnTimeSetListener listenerDeTimePickerFin = (view, hora, min) -> {
        final String selectedDate = String.format("%02d:%02d:00", hora, min);
        binding.tvHoraFin.setText(selectedDate);
    };

    private void showDatePickerDialog() {
        DatePickerDialog dialogoFecha = new DatePickerDialog(getContext(), listenerDeDatePicker, anio, mes, diaDelMes);
        dialogoFecha.show();
    }
    private DatePickerDialog.OnDateSetListener listenerDeDatePicker = (view, anio, mes, diaDelMes) -> {
        final String selectedDate = String.format("%04d-%02d-%02d", anio, mes+1, diaDelMes);
        binding.tvFecha.setText(selectedDate);
    };

    @Override
    public void onBackPressed() {
        /*getActivity().findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
        ((MainActivity)getActivity()).setOnBackPressedListener(null);*/
    }

    @Override
    public <T> void onSuccessLoad(List<T> list) {

    }

    @Override
    public void onDeleteSuccess(String msg) {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onSuccess(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
        navController.navigate(R.id.eventosRecientesFragment);

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime date = LocalDateTime.parse(evento.getDate()+" "+evento.getStartTime(),format);

        Calendar now = Calendar.getInstance();

        if(prefs.getBoolean("NotObj",false) &&
                newEve &&
                now.getTimeInMillis() != date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        ){
            iniJob();
        }
    }

    @Override
    public void onFailure(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setNameEmpty() {
        binding.textInputLayout1.setError("El nombre no puede estar vacio");
    }

    @Override
    public void setHoraIniEmpty() {
        binding.textInputLayout2.setError("La hora de inicio no puede estar vacia");
    }

    @Override
    public void setHoraFinEmpty() {
        binding.textInputLayout3.setError("La hora de fin no puede estar vacia");

    }

    @Override
    public void setDateEmpty() {
        binding.textInputLayout4.setError("La fecha no puede estar vacia");
    }

    @Override
    public void setHoraFinErr() {
        binding.textInputLayout3.setError("La hora de fin no puede ser menor que la hora de inicio");
    }

    @Override
    public void setDateErr() {
        binding.textInputLayout4.setError("La fecha no puede ser menor que la fecha actual");
    }
}