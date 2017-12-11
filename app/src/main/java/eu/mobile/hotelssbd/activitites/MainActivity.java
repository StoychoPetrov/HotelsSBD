package eu.mobile.hotelssbd.activitites;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import eu.mobile.hotelssbd.FilterDialog;
import eu.mobile.hotelssbd.models.Room;
import eu.mobile.hotelssbd.database.CategoriesTable;
import eu.mobile.hotelssbd.database.HotelsTable;
import eu.mobile.hotelssbd.models.Hotel;
import eu.mobile.hotelssbd.models.RoomDetails;
import eu.mobile.hotelssbd.R;
import eu.mobile.hotelssbd.adapters.HotelsAdapter;
import eu.mobile.hotelssbd.database.RoomsTable;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, TextWatcher{

    private ListView        mHotelsListView;
    private List<Hotel>     mHotels             = new ArrayList<>();
    private List<Hotel>     mBuffHotels         = new ArrayList<>();
    private Button          mShowReservationsBtn;
    private ImageView       mSearchImg;
    private ImageView       mCloseImg;
    private EditText        mSearchEdt;
    private TextView        mTitleTxt;
    private ImageView       mFilterImg;

    private HotelsAdapter   mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        setListeners();
        loadDatabase();

        setAdapter();
        mHotelsListView.setOnItemClickListener(this);
    }

    private void initUI(){
        mHotelsListView         = (ListView)    findViewById(R.id.hotels_list_view);
        mShowReservationsBtn    = (Button)      findViewById(R.id.show_btn);
        mSearchImg              = (ImageView)   findViewById(R.id.search_img);
        mSearchEdt              = (EditText)    findViewById(R.id.search_edt);
        mTitleTxt               = (TextView)    findViewById(R.id.title_txt);
        mCloseImg               = (ImageView)   findViewById(R.id.close_img);
        mFilterImg              = (ImageView)   findViewById(R.id.filter_img);
    }

    private void setListeners(){
        mShowReservationsBtn.setOnClickListener(this);
        mSearchImg.setOnClickListener(this);
        mCloseImg.setOnClickListener(this);
        mSearchEdt.addTextChangedListener(this);
        mFilterImg.setOnClickListener(this);
    }

    private void setAdapter(){
        mHotels  = HotelsTable.getInstance(this).selectAllHotels();
        mBuffHotels.addAll(mHotels);
        mAdapter = new HotelsAdapter(this,mBuffHotels);
        mHotelsListView.setAdapter(mAdapter);
    }

    private void loadDatabase(){
        HotelsTable hotelsTable = HotelsTable.getInstance(this);
        if(hotelsTable.getHotelCount() == 0) {
            Hotel hotelFirst    = new Hotel(0, "INTERNATIONAL Hotel", "5-star hotel", 5);
            Hotel hotelSecond   = new Hotel(0, "Meliá Grand Hermitage", "5-star hotel", 5);
            Hotel hotelThird    = new Hotel(0, "Grifid Hotel \"Arabella\"", "4-star hotel", 4);
            Hotel hotelFourth   = new Hotel(0, "Hotel \"Preslav\"", "3-star hotel", 3);
            Hotel hotelFifth    = new Hotel(0, "Hotel \"Viva Club\"", "4-star hotel", 3);

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

        if(RoomsTable.getInstance(this).countRooms() == 0)
            insertRooms();
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

    private void insertRooms(){
        List<Room> meliaRooms   = new ArrayList<>();
        meliaRooms.add(new Room(1001,"Very nice room",1));
        meliaRooms.add(new Room(1002,"Very nice room",2));
        meliaRooms.add(new Room(1003,"Very nice room",3));
        meliaRooms.add(new Room(1004,"Very nice room",4));
        meliaRooms.add(new Room(1005,"Very nice room",5));
        meliaRooms.add(new Room(1006,"Very nice room",6));
        meliaRooms.add(new Room(1007,"Very nice room",7));
        meliaRooms.add(new Room(1008,"Very nice room",8));
        meliaRooms.add(new Room(1009,"Very nice room",9));
        meliaRooms.add(new Room(1010,"Very nice room",10));
        meliaRooms.add(new Room(1011,"Very nice room",11));
        meliaRooms.add(new Room(1012,"Very nice room",12));
        meliaRooms.add(new Room(1013,"Very nice room",13));
        meliaRooms.add(new Room(1014,"Very nice room",14));
        meliaRooms.add(new Room(1015,"Very nice room",15));
        meliaRooms.add(new Room(1016,"Very nice room",16));
        meliaRooms.add(new Room(1017,"Very nice room",17));
        meliaRooms.add(new Room(1018,"Very nice room",18));
        meliaRooms.add(new Room(1019,"Very nice room",19));
        meliaRooms.add(new Room(1020,"Very nice room",20));

        List<Room> interaRooms  = new ArrayList<>();
        interaRooms.add(new Room(3100,"Very nice room",21));
        interaRooms.add(new Room(3101,"Very nice room",22));
        interaRooms.add(new Room(3102,"Very nice room",23));
        interaRooms.add(new Room(3103,"Very nice room",24));
        interaRooms.add(new Room(3104,"Very nice room",25));
        interaRooms.add(new Room(3105,"Very nice room",26));
        interaRooms.add(new Room(3106,"Very nice room",27));
        interaRooms.add(new Room(3107,"Very nice room",28));
        interaRooms.add(new Room(3108,"Very nice room",29));
        interaRooms.add(new Room(3109,"Very nice room",30));
        interaRooms.add(new Room(3110,"Very nice room",31));
        interaRooms.add(new Room(3111,"Very nice room",32));

        List<Room> grifidRooms  = new ArrayList<>();
        grifidRooms.add(new Room(401,"Very nice room",33));
        grifidRooms.add(new Room(402,"Very nice room",34));
        grifidRooms.add(new Room(403,"Very nice room",35));
        grifidRooms.add(new Room(404,"Very nice room",36));
        grifidRooms.add(new Room(405,"Very nice room",37));
        grifidRooms.add(new Room(406,"Very nice room",38));
        grifidRooms.add(new Room(407,"Very nice room",39));
        grifidRooms.add(new Room(408,"Very nice room",40));
        grifidRooms.add(new Room(409,"Very nice room",41));

        List<Room> preslavRooms = new ArrayList<>();
        preslavRooms.add(new Room(301,"Very nice room",42));

        List<Room> vivaRooms    = new ArrayList<>();
        vivaRooms.add(new Room(201,"Very nice room",43));
        vivaRooms.add(new Room(202,"Very nice room",44));
        vivaRooms.add(new Room(203,"Very nice room",45));
        vivaRooms.add(new Room(204,"Very nice room",46));
        vivaRooms.add(new Room(205,"Very nice room",47));

        RoomsTable  roomsTable  = RoomsTable.getInstance(this);
        roomsTable.insertRooms(meliaRooms);
        roomsTable.insertRooms(interaRooms);
        roomsTable.insertRooms(grifidRooms);
        roomsTable.insertRooms(preslavRooms);
        roomsTable.insertRooms(vivaRooms);
    }

    private void showKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(mSearchEdt.getWindowToken(), InputMethodManager.SHOW_IMPLICIT, 0);
    }

    private void showHideSearchBox(final boolean showSearch){
        final AlphaAnimation fadeIn   = new AlphaAnimation(0f, 1f);
        final AlphaAnimation fadeOut        = new AlphaAnimation(1f, 0f);

        fadeIn.setDuration(300);
        fadeOut.setDuration(300);

        if(showSearch) {
            mTitleTxt.startAnimation(fadeOut);
            mSearchImg.startAnimation(fadeOut);
            mFilterImg.startAnimation(fadeOut);
        }
        else {
            mSearchEdt.startAnimation(fadeOut);
            mCloseImg.startAnimation(fadeOut);
        }

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(showSearch) {
                    mSearchEdt.startAnimation(fadeIn);
                    mCloseImg.startAnimation(fadeIn);

                    mTitleTxt.setVisibility(View.INVISIBLE);
                    mSearchImg.setVisibility(View.INVISIBLE);
                    mFilterImg.setVisibility(View.INVISIBLE);

                }
                else {
                    mTitleTxt.startAnimation(fadeIn);
                    mSearchImg.startAnimation(fadeIn);
                    mFilterImg.startAnimation(fadeIn);

                    mSearchEdt.setVisibility(View.INVISIBLE);
                    mCloseImg.setVisibility(View.INVISIBLE);
                }

                fadeOut.setAnimationListener(null);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if(showSearch) {
                    mSearchEdt.setVisibility(View.VISIBLE);
                    mCloseImg.setVisibility(View.VISIBLE);
                }
                else {
                    mTitleTxt.setVisibility(View.VISIBLE);
                    mSearchImg.setVisibility(View.VISIBLE);
                    mFilterImg.setVisibility(View.VISIBLE);
                }

                fadeIn.setAnimationListener(null);

                showKeyboard();
                mSearchEdt.requestFocus();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void onSearch(String searchWord){
        mBuffHotels.clear();

        mBuffHotels.addAll(HotelsTable.getInstance(this).searchHotels(searchWord));

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this,RoomsActivity.class);
        intent.putExtra("hotel_id",mHotels.get(i).getmHotelId());
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.show_btn:
                Intent intent = new Intent(this,ReservationsActivity.class);
                startActivity(intent);
                break;
            case R.id.search_img:
                showHideSearchBox(true);
                break;
            case R.id.close_img:
                showHideSearchBox(false);
                mBuffHotels.clear();
                mBuffHotels.addAll(mHotels);

                mAdapter.notifyDataSetChanged();
                mSearchEdt.setText("");
                break;
            case R.id.filter_img:
                onFilter();
                break;
        }
    }

    private void onFilter(){
        FilterDialog filterDialog = new FilterDialog(this);
        filterDialog.show();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        onSearch(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}