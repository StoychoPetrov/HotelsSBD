package eu.mobile.hotelssbd.activitites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import eu.mobile.hotelssbd.database.CategoriesTable;
import eu.mobile.hotelssbd.Models.RoomDetails;
import eu.mobile.hotelssbd.R;
import eu.mobile.hotelssbd.adapters.RoomsAdapter;

public class RoomsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView            mListView;
    private List<RoomDetails>   mRoomsDetails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
        initUI();
        loadRoomsFromDataBase();
        setAdapter();
    }

    private void initUI(){
        mListView   = (ListView)findViewById(R.id.list_view);
        mListView.setOnItemClickListener(this);
    }

    private void loadRoomsFromDataBase(){
        CategoriesTable categoriesTable = CategoriesTable.getInstance(this);
        mRoomsDetails   = categoriesTable.getRooms(getIntent().getIntExtra("hotel_id",-1));
    }

    private void setAdapter(){
        RoomsAdapter roomsAdapter = new RoomsAdapter(this,mRoomsDetails);
        mListView.setAdapter(roomsAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(this,MakeReservationActivity.class);
        intent.putExtra("room_Details_id",mRoomsDetails.get(position).getmRoomDetailId());
        startActivity(intent);
    }
}