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
import eu.mobile.hotelssbd.database.HotelsTable;
import eu.mobile.hotelssbd.Models.Hotel;
import eu.mobile.hotelssbd.Models.RoomDetails;
import eu.mobile.hotelssbd.R;
import eu.mobile.hotelssbd.adapters.HotelsAdapter;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView        mHotelsListView;
    private List<Hotel>     mHotels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        loadDatabase();

        setAdapter();
        mHotelsListView.setOnItemClickListener(this);
    }

    private void initUI(){
        mHotelsListView = (ListView) findViewById(R.id.hotels_list_view);
    }

    private void setAdapter(){
        List<Hotel>     hotels  = HotelsTable.getInstance(this).selectAllHotels();
        HotelsAdapter   adapter = new HotelsAdapter(this,hotels);
        mHotelsListView.setAdapter(adapter);
    }

    private void loadDatabase(){
        HotelsTable hotelsTable = HotelsTable.getInstance(this);
        if(hotelsTable.getHotelCount() == 0) {
            Hotel hotelFirst    = new Hotel(0, "INTERNATIONAL Hotel Casino & Tower Suites", "5-star hotel");
            Hotel hotelSecond   = new Hotel(0, "Meliá Grand Hermitage", "5-star hotel");
            Hotel hotelThird    = new Hotel(0, "Grifid Hotel \"Arabella\"", "4-star hotel");
            Hotel hotelFourth   = new Hotel(0, "Hotel \"Preslav\"", "3-star hotel");
            Hotel hotelFifth    = new Hotel(0, "Hotel \"Viva Club\"", "4-star hotel");

            List<String> images = new ArrayList<>();

            images.add("https://cdn.ostrovok.ru/t/1024x768/mec/hotels/3000000/2870000/2861100/2861078/2861078_84_b.jpg");
            hotelFirst.setmImages(images);

            images = new ArrayList<>();
            images.add("https://cdn.ostrovok.ru/t/1024x768/mec/hotels/2000000/1440000/1436700/1436617/1436617_105_b.jpg");
            hotelSecond.setmImages(images);

            images = new ArrayList<>();
            images.add("http://bstatic.com/images/hotel/840x460/318/31841139.jpg");
            hotelThird.setmImages(images);

            images = new ArrayList<>();
            images.add("http://bstatic.com/images/hotel/840x460/464/46497621.jpg");
            hotelFourth.setmImages(images);

            images = new ArrayList<>();
            images.add("http://bstatic.com/images/hotel/840x460/464/46497621.jpg");
            hotelFifth.setmImages(images);

            mHotels.add(hotelFirst);
            mHotels.add(hotelSecond);
            mHotels.add(hotelThird);
            mHotels.add(hotelFourth);
            mHotels.add(hotelFifth);

            hotelsTable.insertHotels(mHotels);
        }
        else
            mHotels = hotelsTable.selectAllHotels();

        if(CategoriesTable.getInstance(this).getRoomDetailsCount() == 0)
            loadCategoriesOfRooms();
    }

    private void loadCategoriesOfRooms(){
        String[] categoriesDescriptionsMelia = new String[]{"Standard Double or Twin Room (2 Adults + 1 Child)","The Level Deluxe Suite – 4 Adults","The Level Junior Suite - 3 Adults + 1 Child","The Level Deluxe Suite - 3 Adults + 1 Child","Standard Double or Twin Room with Sea View (2 Adults + 1 Child)",
        "The LEVEL Junior Suite – 4 Adults","Standard Double or Twin Room with Sea View (3 Adults)","The Level Junior Suite - 2 Adults + 2 Children","Standard Double or Twin Room (3 Adults)","Premium Level Room - 3 Adults","Premium Level Room","Premium Level Room - 2 Adults +1 Child","The Level Deluxe Suite - 2 Adults + 2 Children",
        "Standard Double or Twin Room","Standard Double or Twin Room with Sea View","The Level Junior Suite"};

        String[] categoriesDescriptionsInterNational = new String[]{"Superior Double or Twin Room with Park View","Presidential Suite","Superior Room with Sea View and Complimentary Breakfast in Executive Lounge","Executive Double or Twin Room","Superior Double or Twin with SPA Package","Superior Double or Twin Room - New Year`s Offer",
        "Deluxe Room with Sea View and Complimentary Breakfast in Executive Lounge","Superior Suite (2 Adults + 2 Children)","Superior Room with Park View and Complimentary Breakfast in Executive Lounge","Deluxe Double or Twin Room with Sea View","Superior Double or Twin Room with Sea View"};

        String[] categoriesGrifid = new String[]{"Deluxe Double Room with Sea View (3 Adults + 1 Child)","Family Room with Sea View (3 Adults + 1 Child)","Deluxe Double Room with Sea View (2 Adults + 1 Child)","Deluxe Double Room with Side Sea View ( 3 Adults )","Family Room with Sea View ( 4 Adults)","Deluxe Double Room with Sea View ( 3 Adults)",
        "Deluxe Double Room with Side Sea View (2 Adults + 1 Child )","Deluxe Double Room with Side Sea View","Deluxe Double Room with Sea View"};

        String[] categoriesPreslav  = new String[]{"Twin Room"};

        String[] categoriesViva     = new String[]{"Standard Double or Twin Room with Balcony - All Inclusive","Superior Double or Twin Room (2 Adults) All Inclusive","Superior Double or Twin Room (3 Adults) All Inclusive","Family Room (2 Adults + 2 Children) All Inclusive","Standard Single Room with Balcony (1 Adult + 1 Child) All Inclusive"};

        double[] pricesMelia = {264,293,194,215,282,313,211,235,299,333,317,352,361,401,299,333,370,411,500,160};

        double[] priceInter  = {361,401,299,333,370,411,500,160,282,313,211};

        double[] priceGrifid  = {130,210,100,90,220,160,200,280,260};

        double[] pricePreslav = {60};

        double[] priceViva  = {80,60,50,120,150};

        CategoriesTable categoriesTable = CategoriesTable.getInstance(this);
        for(int i = 0 ; i < categoriesDescriptionsMelia.length; i ++){
            RoomDetails roomDetails = new RoomDetails(0,pricesMelia[i],categoriesDescriptionsMelia[i],2);
            categoriesTable.insertCategories(roomDetails);
        }

        for(int i = 0 ; i < categoriesDescriptionsInterNational.length; i ++){
            RoomDetails roomDetails = new RoomDetails(0,priceInter[i],categoriesDescriptionsInterNational[i],1);
            categoriesTable.insertCategories(roomDetails);
        }

        for(int i = 0 ; i < categoriesGrifid.length; i ++){
            RoomDetails roomDetails = new RoomDetails(0,priceGrifid[i],categoriesGrifid[i],3);
            categoriesTable.insertCategories(roomDetails);
        }

        for(int i = 0 ; i < categoriesPreslav.length; i ++){
            RoomDetails roomDetails = new RoomDetails(0,pricePreslav[i],categoriesPreslav[i],4);
            categoriesTable.insertCategories(roomDetails);
        }

        for(int i = 0 ; i < categoriesViva.length; i ++){
            RoomDetails roomDetails = new RoomDetails(0,priceViva[i],categoriesViva[i],5);
            categoriesTable.insertCategories(roomDetails);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this,RoomsActivity.class);
        intent.putExtra("hotel_id",mHotels.get(i).getmHotelId());
        startActivity(intent);
    }
}