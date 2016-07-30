package com.example.ganesh.timework.dialogs;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ganesh.timework.R;
import com.example.ganesh.timework.adapter.DialogToDatabaseAdapter;
import com.example.ganesh.timework.utils.Constants;

import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Ganesh Prasad on 06-07-2016.
 */
public class CreateRoutineFragment extends DialogFragment implements TimePickerDialog.SetTimeListener {

    private static final String LOG_TAG = "create routine dialog";

    RadioGroup radioDaysGroup;
    LinearLayout checkBoxContainerLl;
    EditText eventNameEt;
    Spinner spinnerTypeCreateRoutine;
    CheckBox notifyCb;
    TextView timePickerTv;

    int timeHour;
    int timeMinutes;

    CheckBox mon;
    CheckBox tue;
    CheckBox wed;
    CheckBox thu;
    CheckBox fri;
    CheckBox sat;
    CheckBox sun;

    onSaveButtonListener saveButtonListener;

    final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

    public static CreateRoutineFragment newInstance( onSaveButtonListener listener ) {

        CreateRoutineFragment createRoutineFragment = new CreateRoutineFragment();
        createRoutineFragment.saveButtonListener = listener;
        return createRoutineFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.dialogfragment_create_routine , container , false);

        setHasOptionsMenu(true);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar_routine_dialogfragment);
        toolbar.setTitle(R.string.dialog_routine_title);
        ((AppCompatActivity) getActivity() ).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity() ).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        }

//        Spinner for Type of routine selection
        spinnerTypeCreateRoutine = (Spinner) rootView.findViewById(R.id.spinner_type_routine_dialogfragment);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getActivity() ,R.array.type_create_routine ,
                android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeCreateRoutine.setAdapter(arrayAdapter);

//        Implementing radio button read
        radioDaysGroup = (RadioGroup) rootView.findViewById( R.id.radio_group_days_routine_dialogfragment );
        checkBoxContainerLl = (LinearLayout) rootView.findViewById( R.id.check_box_container_routine_dialogfragment );

        radioDaysGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if ( checkedId == R.id.radio_custom_routine_dialogfragment ) {

                    moveFocusAndSoftKeyboard( imm , eventNameEt , rootView );

                    checkBoxContainerLl.setAlpha(1);

                    for ( int i = 0; i < checkBoxContainerLl.getChildCount(); i++ ) {
                        View child = checkBoxContainerLl.getChildAt(i);
                        child.setClickable(true);
                    }

                } else {

                    checkBoxContainerLl.setAlpha(0.5f);

                    for ( int i = 0; i < checkBoxContainerLl.getChildCount(); i++ ) {
                        CheckBox child = (CheckBox) checkBoxContainerLl.getChildAt(i);
                        child.setClickable(false);
                        child.setChecked(false);
                    }

                }
            }
        });

//        Initialise Views that return event data
        eventNameEt = (EditText) rootView.findViewById( R.id.event_name_routine_dialogfragment );
        eventNameEt.requestFocus();
        handleTheSoftKeyboard( eventNameEt );

        notifyCb = (CheckBox) rootView.findViewById( R.id.checkbox_notify_routine_dialogfragment );

        mon = (CheckBox) rootView.findViewById( R.id.check_box_m_routine_dialogfragment );
        tue = (CheckBox) rootView.findViewById( R.id.check_box_tu_routine_dialogfragment );
        wed = (CheckBox) rootView.findViewById( R.id.check_box_w_routine_dialogfragment );
        thu = (CheckBox) rootView.findViewById( R.id.check_box_th_routine_dialogfragment );
        fri = (CheckBox) rootView.findViewById( R.id.check_box_f_routine_dialogfragment );
        sat = (CheckBox) rootView.findViewById( R.id.check_box_sa_routine_dialogfragment );
        sun = (CheckBox) rootView.findViewById( R.id.check_box_su_routine_dialogfragment );

        timePickerTv = (TextView) rootView.findViewById( R.id.time_picker_tv_routine_dialogfragment );

        Calendar c = Calendar.getInstance();
        int currentHour = c.get(Calendar.HOUR_OF_DAY);
        int currentMinute = c.get( Calendar.MINUTE );
        updateTimeTv( currentHour , currentMinute );

        timePickerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog time = TimePickerDialog.newInstance( CreateRoutineFragment.this );
                time.show( getFragmentManager() , " Time Picker " );
            }
        });

        return rootView;
    }

    private void handleTheSoftKeyboard(final EditText editView ) {

        imm.toggleSoftInput( InputMethod.SHOW_FORCED , InputMethodManager.HIDE_IMPLICIT_ONLY );
        editView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ( event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    moveFocusAndSoftKeyboard( imm , editView , v );
                }
                return false;
            }
        });

    }

    private boolean moveFocusAndSoftKeyboard( InputMethodManager imm , View view , View window ){
        Log.d( LOG_TAG , "Enter key pressed" );
        imm.hideSoftInputFromWindow(window.getWindowToken() , 0);
        view.setFocusable(false);
        view.setFocusableInTouchMode(true);
        return true;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.dialogfragment_menu_create_routine , menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.create_routine_menu_save) {
            saveButtonRoutine();
            return true;
        }else if (id == android.R.id.home) {
            dismiss();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void saveButtonRoutine() {

        boolean custom[] = new boolean[7];

        int dayGroup;
        String eventNameString = eventNameEt.getText().toString();
        int idRadioDays = radioDaysGroup.getCheckedRadioButtonId();
        int idTypeSelection = spinnerTypeCreateRoutine.getSelectedItemPosition();
        boolean notifyBool = notifyCb.isChecked();

        DialogToDatabaseAdapter adapter;

        if ( !eventNameString.equals("") ) {

            if ( idRadioDays == R.id.radio_custom_routine_dialogfragment ) {

                dayGroup = Constants.DayGroups.CUSTOM;

                custom[0] = mon.isChecked();
                custom[1] = tue.isChecked();
                custom[2] = wed.isChecked();
                custom[3] = thu.isChecked();
                custom[4] = fri.isChecked();
                custom[5] = sat.isChecked();
                custom[6] = sun.isChecked();


                adapter = new DialogToDatabaseAdapter(getContext() , eventNameString , dayGroup ,
                        idTypeSelection , notifyBool , custom , timeHour , timeMinutes);

            } else if ( idRadioDays == R.id.radio_alldays_routine_dialogfragment ) {
                dayGroup = Constants.DayGroups.ALLDAYS;

                adapter = new DialogToDatabaseAdapter( getContext() , eventNameString , dayGroup ,
                        idTypeSelection , notifyBool , timeHour , timeMinutes);

            } else {
                dayGroup = Constants.DayGroups.WEEKDAYS;

                adapter = new DialogToDatabaseAdapter( getContext() , eventNameString , dayGroup ,
                        idTypeSelection , notifyBool , timeHour , timeMinutes);
            }

            adapter.addValuesToDb();

            saveButtonListener.onSaveButton();

            dismiss();


        }else {
            CoordinatorLayout coordinatorLayout = new CoordinatorLayout(getContext());
            Snackbar.make( coordinatorLayout , " Event must have a name?! Isn't it? " , Snackbar.LENGTH_LONG );
            eventNameEt.requestFocus();
            handleTheSoftKeyboard( eventNameEt );
        }
    }

    @Override
    public void onSetTime(int hour, int minutes) {

        updateTimeTv( hour , minutes );

        timeHour = hour;
        timeMinutes = minutes;

    }

    private void updateTimeTv( int hour , int minutes ) {

        timePickerTv.setText( String.format(Locale.getDefault() , " %d : %d " , hour , minutes ) );

    }

    public interface onSaveButtonListener{
        void onSaveButton();
    }
}
