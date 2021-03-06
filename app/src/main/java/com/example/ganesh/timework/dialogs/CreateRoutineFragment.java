package com.example.ganesh.timework.dialogs;

import android.app.*;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ganesh.timework.R;
import com.example.ganesh.timework.adapter.DialogToDatabaseAdapter;
import com.example.ganesh.timework.utils.Constants;
import com.example.ganesh.timework.utils.Routines;
import com.example.ganesh.timework.utils.Utilities;

import java.util.Calendar;

/**
 * Created by Ganesh Prasad on 06-07-2016.
 * This fragment is used to create new routines
 */
public class CreateRoutineFragment extends DialogFragment implements TimePickerDialog.SetTimeListener {

    private static final String LOG_TAG = "create routine dialog";

    View rootView;
    InputMethodManager imm;

    RadioGroup radioDaysGroup;
    LinearLayout checkBoxContainerLl;
    EditText eventNameEt;
    Spinner spinnerType;
    Spinner spinnerTimeSize;
    CheckBox notifyCb;
    TextView timePickerTv;

    OnNewRoutineCreatedListener saveRoutineListener;

//    Remove these once done
    int timeHour;
    int timeMinutes;

    long time;

    boolean isUpdate = false;

    CheckBox[] daysCbArray = new CheckBox[7];
    int[] daysCbViewArray = new int[]{
            R.id.check_box_m_routine_dialogfragment ,
            R.id.check_box_tu_routine_dialogfragment ,
            R.id.check_box_w_routine_dialogfragment ,
            R.id.check_box_th_routine_dialogfragment ,
            R.id.check_box_f_routine_dialogfragment ,
            R.id.check_box_sa_routine_dialogfragment ,
            R.id.check_box_su_routine_dialogfragment
    };

    Routines mItem;
    int routineId;

    /**
     * creating instances of create routines
     * getting the listener from parent
      * @param listener
     * @return
     */
    public static CreateRoutineFragment newInstance( OnNewRoutineCreatedListener listener ) {
        CreateRoutineFragment createRoutineFragment = new CreateRoutineFragment();
        createRoutineFragment.saveRoutineListener = listener;
        return createRoutineFragment;
    }

    /**
     * Creating new instance to edit existing routines
     * @param _item
     * @param listener
     * @return
     */
    public static CreateRoutineFragment newInstance(Routines _item , OnNewRoutineCreatedListener listener){
        CreateRoutineFragment fragment = CreateRoutineFragment.newInstance(listener);
        fragment.mItem = _item;
        fragment.saveRoutineListener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.dialogfragment_create_routine , container , false);
        setHasOptionsMenu(true);

//        Toolbar
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar_routine_dialogfragment);
        toolbar.setTitle(R.string.dialog_routine_title);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_close_clear_cancel);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                hideSoftKeyboard();
            }
        });
//        Not Actionbar so - Menu inflate here
        toolbar.inflateMenu(R.menu.dialogfragment_menu_create_routine);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.create_routine_menu_save) {
                    saveRoutine();
                    dismiss();
                    hideSoftKeyboard();
                }

                return false;
            }
        });

        /**
         * Handing keyboard
         */
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);


        spinnerType = (Spinner) rootView.findViewById(R.id.spinner_type_routine_dialogfragment);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getActivity() ,R.array.type_create_routine ,
                android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(arrayAdapter);

        spinnerTimeSize = (Spinner) rootView.findViewById(R.id.spinner_time_size_routine_dialogfragment);
        ArrayAdapter<CharSequence> arrayAdapter_time = ArrayAdapter.createFromResource(getActivity(), R.array.time_size,
                android.R.layout.simple_spinner_item);
        arrayAdapter_time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeSize.setAdapter(arrayAdapter_time);

        radioDaysGroup = (RadioGroup) rootView.findViewById( R.id.radio_group_days_routine_dialogfragment );
        radioDaysGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if ( checkedId == R.id.radio_custom_routine_dialogfragment ) {

                    // handling keyboard
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

        checkBoxContainerLl = (LinearLayout) rootView.findViewById( R.id.check_box_container_routine_dialogfragment );

//        Initialise Views that return event data
//        event name
        eventNameEt = (EditText) rootView.findViewById( R.id.event_name_routine_dialogfragment );
        eventNameEt.requestFocus();
        handleTheSoftKeyboard( eventNameEt );

//        notify user checkbox
        notifyCb = (CheckBox) rootView.findViewById( R.id.checkbox_notify_routine_dialogfragment );

//        initialise days checkbox
        for ( int i = 0; i < daysCbArray.length; i++ ){
            daysCbArray[i] = (CheckBox) rootView.findViewById(daysCbViewArray[i]);
        }

        timePickerTv = (TextView) rootView.findViewById( R.id.time_picker_tv_routine_dialogfragment );

        Calendar c = Calendar.getInstance();
        timeHour = c.get(Calendar.HOUR_OF_DAY);
        timeMinutes = c.get( Calendar.MINUTE );
        updateTimeTv( timeHour , timeMinutes );

        timePickerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker = TimePickerDialog.newInstance( CreateRoutineFragment.this, timeHour, timeMinutes );
                timePicker.show( getFragmentManager() , " Time Picker " );
            }
        });

        if (mItem != null){
            isUpdate = true;
            fillRoutineFragment();
        }

        return rootView;
    }

    /**
     * Fill the fragment for editing existing routines
     */
    public void fillRoutineFragment(){

        eventNameEt.setText(mItem.getRoutineName());
        routineId = mItem.getRoutineid();

        for (int i = 0; i < daysCbArray.length; i++){
            daysCbArray[i].setChecked(mItem.getDaysToRepeat()[i]);
        }

        spinnerType.setSelection(Constants.getIntForTypeOfRoutine(mItem.getRoutineType()));
        spinnerTimeSize.setSelection(Constants.getIntForTimeSize(mItem.getTime_size()));
        String time = Utilities.formattedTimeForRoutines(mItem.getHour() , mItem.getMinutes());
        timePickerTv.setText(time);
        notifyCb.setChecked( mItem.isNotify() );
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

    public void hideSoftKeyboard(){
        imm.hideSoftInputFromWindow(rootView.getWindowToken() , 0);
    }

    private void saveRoutine() {

        boolean custom[] = new boolean[7];

        int dayGroup;
        int idRadioDays = radioDaysGroup.getCheckedRadioButtonId();
        int idTypeSelection = spinnerType.getSelectedItemPosition();
        int routineTimeSize = spinnerTimeSize.getSelectedItemPosition();
        boolean notifyBool = notifyCb.isChecked();
        String eventNameString = eventNameEt.getText().toString();

        DialogToDatabaseAdapter adapter;

        if ( !eventNameString.isEmpty() ) {
            if ( idRadioDays == R.id.radio_custom_routine_dialogfragment ) {
                dayGroup = Constants.DayGroups.CUSTOM;
                for (int i = 0; i < custom.length; i++){
                    custom[i] = daysCbArray[i].isChecked();
                }
            } else if ( idRadioDays == R.id.radio_alldays_routine_dialogfragment ) {
                dayGroup = Constants.DayGroups.ALLDAYS;
                custom = null;
            } else {
                dayGroup = Constants.DayGroups.WEEKDAYS;
                custom = null;
            }
            Log.d(LOG_TAG, "From createRoutine : notify " + notifyBool);

            adapter = adapterInitialise( eventNameString , dayGroup ,
                    idTypeSelection , notifyBool , custom , timeHour , timeMinutes , isUpdate, routineTimeSize);
            adapter.addValuesToDb();
            saveRoutineListener.onNewRoutineCreated();
        }else {
            eventNameEt.requestFocus();
            handleTheSoftKeyboard( eventNameEt );
        }
    }

    /**
     * method to create an instance of the adapter
     * initialises @id in adapter if the routine is being updated
     */
    private DialogToDatabaseAdapter adapterInitialise(
            String eventName ,
            int day,
            int type,
            boolean notify,
            boolean[] daysSelectedArray,
            int hour,
            int minutes,
            boolean isUpdate,
            int routineTimeSize
    ){
        DialogToDatabaseAdapter adapter = new DialogToDatabaseAdapter(getContext() ,
                eventName , day , type , notify , daysSelectedArray , hour , minutes , isUpdate, routineTimeSize);
        if ( isUpdate ){
            adapter.setId(routineId);
        }
        return adapter;
    }

    @Override
    public void onSetTime(int hour, int minutes) {
        updateTimeTv( hour , minutes );
        timeHour = hour;
        timeMinutes = minutes;
    }

    private void updateTimeTv( int hour , int minutes ) {
        timePickerTv.setText(Utilities.formattedTimeForRoutines(hour,minutes));
    }

    public interface OnNewRoutineCreatedListener{
        void onNewRoutineCreated();
    }
}
