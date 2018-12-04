package com.winta.exceltosign;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class SelectActivity extends AppCompatActivity {

    CheckBox check1;
    CheckBox check2;
    CheckBox check3;
    CheckBox check4;
    Button mButton;

    boolean type1 = false;
    boolean type2 = false;
    boolean type3 = false;
    boolean type4 = false;

    Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);


        final Intent intent = getIntent();
        if (intent.ACTION_VIEW.equals(intent.getAction())) {

            mUri = intent.getData();

        }

        check1 = (CheckBox) findViewById(R.id.Cb_demo1);
        check2 = (CheckBox) findViewById(R.id.Cb_demo2);
        check3 = (CheckBox) findViewById(R.id.Cb_demo3);
        check4 = (CheckBox) findViewById(R.id.Cb_demo4);
        mButton = (Button) findViewById(R.id.Btn_login);

        check1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type1) {
                    check1.setChecked(false);
                    check2.setChecked(true);
                    type1 = false;
                    type2 = true;
                } else {
                    check1.setChecked(true);
                    check2.setChecked(false);
                    type1 = true;
                    type2 = false;
                }
            }
        });

        check2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type2) {
                    check2.setChecked(false);
                    check1.setChecked(true);
                    type2 = false;
                    type1 = true;
                } else {
                    check2.setChecked(true);
                    check1.setChecked(false);
                    type2 = true;
                    type1 = false;
                }
            }
        });

        check3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type3) {
                    check3.setChecked(false);
                    check4.setChecked(true);
                    type3 = false;
                    type4 = true;
                } else {
                    check3.setChecked(true);
                    check4.setChecked(false);
                    type3 = true;
                    type4 = false;
                }
            }
        });

        check4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (type4) {
                    check4.setChecked(false);
                    check3.setChecked(true);
                    type4 = false;
                    type3 = true;
                } else {
                    check4.setChecked(true);
                    check3.setChecked(false);
                    type4 = true;
                    type3 = false;
                }
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectActivity.this,IncreaseActivity.class);
                if ((type1 || type2)&&(type3 || type4)) {

                    if (type1) {

                        intent.putExtra("title", 1);
                    } else {

                        intent.putExtra("title", 2);
                    }

                    if (type3) {

                        intent.putExtra("table_head", 3);
                    } else {

                        intent.putExtra("table_head", 4);
                    }

                    intent.putExtra("Uri", mUri.toString());
                    startActivity(intent);
                    finish();

                } else{

                    Toast.makeText(SelectActivity.this, "请正确选择标题和签名模版", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
