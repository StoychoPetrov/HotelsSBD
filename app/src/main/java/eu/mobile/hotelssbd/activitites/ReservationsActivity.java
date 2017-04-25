package eu.mobile.hotelssbd.activitites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import eu.mobile.hotelssbd.models.Client;
import eu.mobile.hotelssbd.models.Hotel;
import eu.mobile.hotelssbd.models.Reservation;
import eu.mobile.hotelssbd.models.Room;
import eu.mobile.hotelssbd.models.RoomDetails;
import eu.mobile.hotelssbd.R;
import eu.mobile.hotelssbd.adapters.ReservationsAdapter;
import eu.mobile.hotelssbd.database.ReservationsTable;

public class ReservationsActivity extends AppCompatActivity {

    private ListView            mListView;

    private List<Client>        mClients        = new ArrayList<>();
    private List<Reservation>   mResrvations    = new ArrayList<>();
    private List<Room>          mRooms          = new ArrayList<>();
    private List<RoomDetails>   mRoomDetails    = new ArrayList<>();
    private List<Hotel>         mHotels         = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);
        initUI();
        mResrvations = ReservationsTable.getInstance(this).selectReservations(this,mClients,mRooms,mRoomDetails,mHotels);

        ReservationsAdapter adapter = new ReservationsAdapter(this,mResrvations,mRooms,mClients,mRoomDetails,mHotels);
        mListView.setAdapter(adapter);
    }

    private void initUI(){
        mListView   = (ListView) findViewById(R.id.list_view);
    }
}
