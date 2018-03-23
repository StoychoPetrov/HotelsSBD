package eu.mobile.hotelssbd.activitites;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import eu.mobile.hotelssbd.models.Client;
import eu.mobile.hotelssbd.models.Reservation;
import eu.mobile.hotelssbd.R;
import eu.mobile.hotelssbd.database.ReservationsTable;
import eu.mobile.hotelssbd.database.RoomsTable;

public class MakeReservationActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

    private EditText            mFromDateEdt;
    private EditText            mToDateEdt;
    private EditText            mFirstNameEdt;
    private EditText            mLastNameEdt;
    private EditText            mEgnEdt;
    private Button              mSaveBtn;
    private DatePickerDialog    mDatePicker;
    private EditText            mSelectedEditText;
    private Calendar            mStartDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reservation);

        initUI();
        setListeners();
    }

    private void initUI(){
        mFromDateEdt        = (EditText) findViewById(R.id.from_date_edt);
        mToDateEdt          = (EditText) findViewById(R.id.to_date_edt);
        mFirstNameEdt       = (EditText) findViewById(R.id.first_name_edt);
        mLastNameEdt        = (EditText) findViewById(R.id.last_name_edt);
        mEgnEdt             = (EditText) findViewById(R.id.egn_edt);
        mSaveBtn            = (Button)   findViewById(R.id.save_btn);

        Calendar calendar   = Calendar.getInstance();
        mDatePicker         = new DatePickerDialog(this, this, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
        mDatePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());
    }

    private void setListeners(){
        mFromDateEdt.setOnClickListener(this);
        mToDateEdt.setOnClickListener(this);
        mSaveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.from_date_edt:
            case R.id.to_date_edt:
                if(mStartDate != null && view.getId() == R.id.to_date_edt) {
                    mStartDate.add(Calendar.DAY_OF_MONTH,1);
                    mDatePicker.getDatePicker().setMinDate(mStartDate.getTimeInMillis());
                }
                mDatePicker.show();
                mSelectedEditText   = (EditText) view;
                break;
            case R.id.save_btn:
                onSave();
                break;
        }
    }

    private void onSave(){

        ReservationsTable reservationsTable     =  ReservationsTable.getInstance(this);
        int roomId      = RoomsTable.getInstance(this).selectRoomId(getIntent().getIntExtra("room_Details_id",-1));

        Client      client      = reservationsTable.selectClient(mEgnEdt.getText().toString());

        if(client == null)
            client = new Client(0,mFirstNameEdt.getText().toString(),mLastNameEdt.getText().toString(),mEgnEdt.getText().toString());

        Reservation reservation = new Reservation(0,mFromDateEdt.getText().toString(),mToDateEdt.getText().toString());

        int clientId                            = (int) reservationsTable.insertClient(client);

        reservationsTable.insertReservation(reservation,clientId,roomId);
        Intent intents = new Intent(this, MainActivity.class);
        intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intents);
        finish();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        if(mSelectedEditText.getId() == R.id.from_date_edt)
            mStartDate  = calendar;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        mSelectedEditText.setText(dateFormat.format(calendar.getTime()));
    }
}
