package eu.mobile.hotelssbd;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import java.util.ArrayList;

/**
 * Created by Stoycho on 12/11/2017.
 */

public class FilterDialog extends Dialog {

    public FilterDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.filter_layout);

        Button applyBtn = (Button) findViewById(R.id.apply_btn);

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer> stars = new ArrayList<>();

                if(((CheckBox)findViewById(R.id.five)).isChecked())
                    stars.add(5);
                if(((CheckBox)findViewById(R.id.four)).isChecked())
                    stars.add(4);
                if(((CheckBox)findViewById(R.id.three)).isChecked())
                    stars.add(3);
                if(((CheckBox)findViewById(R.id.two)).isChecked())
                    stars.add(2);
                if(((CheckBox)findViewById(R.id.one)).isChecked())
                    stars.add(1);

                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.price_group);
            }
        });


    }
}
