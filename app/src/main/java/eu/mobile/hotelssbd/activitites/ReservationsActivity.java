package eu.mobile.hotelssbd.activitites;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

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

public class ReservationsActivity extends AppCompatActivity implements ReservationsAdapter.OnDeleteClicked{

    private ListView            mListView;

    private List<Client>        mClients        = new ArrayList<>();
    private List<Reservation>   mResrvations    = new ArrayList<>();
    private List<Room>          mRooms          = new ArrayList<>();
    private List<RoomDetails>   mRoomDetails    = new ArrayList<>();
    private List<Hotel>         mHotels         = new ArrayList<>();

    private ReservationsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);
        initUI();
        mResrvations = ReservationsTable.getInstance(this).selectReservations(this,mClients,mRooms,mRoomDetails,mHotels);

        mAdapter = new ReservationsAdapter(this,mResrvations,mRooms,mClients,mRoomDetails,mHotels, this);
        mListView.setAdapter(mAdapter);
    }

    private void initUI(){
        mListView   = (ListView) findViewById(R.id.list_view);
    }

    @Override
    public void onDelete(final int position) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Are you sure you want delete the reservation ?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ReservationsTable reservationsTable = ReservationsTable.getInstance(ReservationsActivity.this);
                if(reservationsTable.deleteReservation(mResrvations.get(position).getmReservationId())){
                    mResrvations.remove(position);
                    mClients.remove(position);
                    mRooms.remove(position);
                    mRoomDetails.remove(position);
                    mHotels.remove(position);

                    mAdapter.notifyDataSetChanged();

                    Toast.makeText(ReservationsActivity.this, "The reservation is deleted successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {
               dialogInterface.dismiss();
           }
       });

        alertDialog.show();
    }
}
