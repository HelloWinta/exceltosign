package com.winta.exceltosign;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.winta.exceltosign.Adapter.ListViewAdapter;
import com.winta.exceltosign.PictureSelect.FileUtil;
import com.winta.exceltosign.db.ProjectTable;
import com.winta.exceltosign.db.SignTable;
import com.winta.exceltosign.utils.ScreenShot;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ProjectActivity extends AppCompatActivity implements ListViewAdapter.CallBack {

    public String path; // 签名的路径
    private String result;//数据结果

    private ListView mLv;
    EditText mEditText;//beizhu
    List<SignTable> mSignTables;
    ListViewAdapter mListViewAdapter;
    private String projectName;
    private String image_a;
    private String image_b;
    private Bitmap bitmap_a;
    private Bitmap bitmap_b;
    private String beizhu;
    private int title_Type;
    private int head_type;
    ScrollView sv_jpge;
    Button btn_save;
    Button btn_pdf;


    //□◻●☆

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_project);

        Intent intent = getIntent();
        projectName = intent.getStringExtra("ProjectName");
        image_a = intent.getStringExtra("Image_a");
        image_b = intent.getStringExtra("Image_b");
        bitmap_a = FileUtil.getAssetsBitmap(this,image_a);
        bitmap_b = FileUtil.getAssetsBitmap(this, image_b);
        title_Type = intent.getIntExtra("Type",1);
        //至于最上层
        LinearLayout linearLayout_head = (LinearLayout) findViewById(R.id.ll_head);
        linearLayout_head.bringToFront();
        //判断是那个title
        //一个是主layout，一个是scrollView
        LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.Ll_1);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.Ll_2);
        LinearLayout linearLayout1_1 = (LinearLayout) findViewById(R.id.Ll_1_1);
        LinearLayout linearLayout2_1 = (LinearLayout) findViewById(R.id.Ll_2_1);
        Bitmap bitmap;
        if (title_Type==1) {
            //mian
            linearLayout2.setVisibility(View.GONE);
            ImageView mImageView_a = (ImageView) findViewById(R.id.image1);
            ImageView mImageView_b = (ImageView) findViewById(R.id.image2);
            TextView Tv_title1= (TextView) findViewById(R.id.Tv_title1);
            mImageView_a.setImageBitmap(bitmap_a);
            mImageView_b.setImageBitmap(bitmap_b);
            Tv_title1.setText(projectName);
            //scrollview
            linearLayout2_1.setVisibility(View.GONE);
            ImageView mImageView_a_1 = (ImageView) findViewById(R.id.image1_1);
            ImageView mImageView_b_1 = (ImageView) findViewById(R.id.image2_1);
            TextView Tv_title1_1= (TextView) findViewById(R.id.Tv_title1_1);
            mImageView_a_1.setImageBitmap(bitmap_a);
            mImageView_b_1.setImageBitmap(bitmap_b);
            Tv_title1_1.setText(projectName);
        } else {
            //main
            linearLayout1.setVisibility(View.GONE);
            ImageView mImageView_a = (ImageView) findViewById(R.id.image3);
            ImageView mImageView_b = (ImageView) findViewById(R.id.image4);
            TextView Tv_title1= (TextView) findViewById(R.id.Tv_title2);
            mImageView_a.setImageBitmap(bitmap_a);
            mImageView_b.setImageBitmap(bitmap_b);
            Tv_title1.setText(projectName);
            //scrollview
            linearLayout1_1.setVisibility(View.GONE);
            ImageView mImageView_a_1 = (ImageView) findViewById(R.id.image3_1);
            ImageView mImageView_b_1 = (ImageView) findViewById(R.id.image4_1);
            TextView Tv_title1_1= (TextView) findViewById(R.id.Tv_title2);
            mImageView_a_1.setImageBitmap(bitmap_a);
            mImageView_b_1.setImageBitmap(bitmap_b);
            Tv_title1_1.setText(projectName);
        }

        //判断是哪个head
        head_type = intent.getIntExtra("Head", 3);
        View tablehead1= findViewById(R.id.tablehead1_2);
        View tablehead2 = findViewById(R.id.tablehead2_2);
        View tablehead1_s= findViewById(R.id.tablehead1_2_s);
        View tablehead2_s = findViewById(R.id.tablehead2_2_s);
        if (head_type == 3) {
            tablehead2.setVisibility(View.GONE);
            tablehead2_s.setVisibility(View.GONE);
        } else {
            tablehead1.setVisibility(View.GONE);
            tablehead1_s.setVisibility(View.GONE);
        }

//        //设置表头滑动停留
//        sv_jpge= (ScrollView) findViewById(R.id.SV_jpge);
//
//        if (head_type == 3) {
//
//            final View head1 = findViewById(R.id.tablehead1_1);
//            final View head2 = findViewById(R.id.tablehead1_2);
//
//            sv_jpge.setOnScrollChangeListener(new View.OnScrollChangeListener(){
//                @Override
//                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                    if (scrollY >= head2.getTop()) {
//                        head1.setVisibility(View.VISIBLE);
//                    } else {
//                        head1.setVisibility(View.GONE);
//                    }
//                }
//            });
//        } else {
//
//            final View head1 = findViewById(R.id.tablehead2_1);
//            final View head2 = findViewById(R.id.tablehead2_2);
//
//            sv_jpge.setOnScrollChangeListener(new View.OnScrollChangeListener(){
//                @Override
//                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                    if (scrollY >= head2.getTop()) {
//                        head1.setVisibility(View.VISIBLE);
//                    } else {
//                        head1.setVisibility(View.GONE);
//                    }
//                }
//            });
//        }



        //配置备注信息

        beizhu = intent.getStringExtra("beizhu");
        mEditText= (EditText) findViewById(R.id.Et_beizhu);
        mEditText.setText(beizhu);




        //配置adapter
        mSignTables = DataSupport.where("projectName = ?", projectName).find(SignTable.class);
        mLv = (ListView) findViewById(R.id.Lv_main);
        mListViewAdapter = new ListViewAdapter(mSignTables, this);
        mLv.setAdapter(mListViewAdapter);
        //ListViewAdapter.setListViewHeightBasedOnChildren(mLv,mListViewAdapter);
        mLv.setFocusable(false);

        //判断系统版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x555);
            }
        }

        btn_save = (Button) findViewById(R.id.Btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //保存备注到项目表

                ContentValues values = new ContentValues();
                values.put("beizhu",mEditText.getText().toString());
                values.put("recentDate",new IncreaseActivity().getRightDate()+"  "+new SimpleDateFormat("HH:mm:ss").format(new Date()));
                DataSupport.updateAll(ProjectTable.class, values, "projectName=?", projectName);
                finish();
            }
        });

        //保存png相册
        btn_pdf = (Button) findViewById(R.id.BTN_pdf);
        btn_pdf.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sv_jpge= (ScrollView) findViewById(R.id.SV_jpge);
                //ScreenShot.savePic(ScreenShot.getBitmapByView(sv_jpge), "/sdcard/"+projectName+".png");
                ScreenShot.saveImageToGallery(getApplicationContext()
                        ,ScreenShot.getBitmapByView(sv_jpge)
                        ,Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"建安电子签名"
                        ,projectName);
                Toast.makeText(ProjectActivity.this, "shengcheng", Toast.LENGTH_SHORT).show();
            }
        });






    }

    @Override
    public void onClick(View v) {
        beizhu = mEditText.getText().toString();
        if(mListViewAdapter.b == 0){ //b=0,点击的是数据结构框
            showInputDialog();
        } else if (mListViewAdapter.b == 1) { //b=1,点击的是主修框
            startSign();
        } else {
            //查看前面一项是否有签名。如果有才可以签。
            String aheadpath = getApplicationContext().getFilesDir().getAbsolutePath()
                    +projectName+"/"+"hang"+mListViewAdapter.a+"lie"+(mListViewAdapter.b-1) + ".png";
            List<String> pathes = new ArrayList<>();
            SignTable signTable = new SignTable();
            for(int i = 0;i<mSignTables.size();i++) {
                signTable = mSignTables.get(i);
                switch (mListViewAdapter.b - 1) {
                    case 1:
                        pathes.add(signTable.getZhuXiu());
                        break;
                    case 2:
                        pathes.add(signTable.getShiGong());
                        break;
                    case 3:
                        pathes.add(signTable.getZhiJian());
                        break;
                    case 4:
                        pathes.add(signTable.getChenBen());
                        break;
                }

            }
            if (pathes.contains(aheadpath)) {
                startSign();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProjectActivity.this);
                builder.setTitle("提示");
                builder.setMessage("上一级未签名");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                }).show();

            }

        }
    }

    private void startSign() {
        path = getApplicationContext().getFilesDir().getAbsolutePath() +projectName+"/"+"hang"+mListViewAdapter.a+"lie"+mListViewAdapter.b + ".png";
        Log.e("dafasd", path);
        Intent intent = new Intent(this, SignActivity.class);
        intent.putExtra("isCrop", true);
        intent.putExtra("path", path);
        intent.putExtra("name", projectName);
        intent.putExtra("signSelect", mListViewAdapter.b);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 101) {
            update(mListViewAdapter.a,mListViewAdapter.b);
            refresh();

        }

    }

    //因为新增的签名无法直接显示，需要刷新界面
    public void refresh() {
        finish();
        Intent intent = new Intent(ProjectActivity.this, ProjectActivity.class);
        intent.putExtra("ProjectName", projectName);
        intent.putExtra("Type", title_Type);
        intent.putExtra("Head", head_type);
        intent.putExtra("Image_a", image_a);
        intent.putExtra("Image_b", image_b);
        intent.putExtra("beizhu", beizhu);
        startActivity(intent);
    }


    //更新签名path。
    public void update(int a,int b) {
        ContentValues values = new ContentValues();

        if (b == 0) {

            SignTable updateSigntable = new SignTable();
            updateSigntable.setResult(result);
            updateSigntable.setZhuXiu("");
            updateSigntable.setShiGong("");
            updateSigntable.setZhiJian("");
            updateSigntable.setChenBen("");
            updateSigntable.setDongSheBei("");
            updateSigntable.update(a + mSignTables.get(0).getId());

        } else {
            switch (b) {
                case 1:
                    values.put("zhuxiu",path);
                    break;
                case 2:
                    values.put("shigong",path);
                    break;
                case 3:
                    values.put("zhijian",path);
                    break;
                case 4:
                    values.put("chengben",path);
                    break;
                case 5:
                    values.put("dongshebei",path);
                    break;

            }
            DataSupport.update(SignTable.class, values, a+mSignTables.get(0).getId());//sqlite 从1开始计算
        }
        ContentValues values1 = new ContentValues();
        values1.put("recentDate",new IncreaseActivity().getRightDate()+"  "+new SimpleDateFormat("HH:mm:ss").format(new Date()));
        DataSupport.updateAll(ProjectTable.class, values1, "projectName=?", projectName);

    }

    //数据和结果修改框
    private void showInputDialog() {
        /*@setView 装入一个EditView
         */
        final EditText editText = new EditText(ProjectActivity.this);


        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(ProjectActivity.this);
        inputDialog.setTitle("提示");
        inputDialog.setMessage("确定要增加或者修改吗");
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder inputDialog =
                                new AlertDialog.Builder(ProjectActivity.this);
                        inputDialog.setTitle("数据与结果").setView(editText);
                        inputDialog.setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        result = editText.getText().toString();
                                        update(mListViewAdapter.a, mListViewAdapter.b);
                                        refresh();
                                    }
                                });
                        inputDialog.setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        editText.clearFocus();
                                        return;
                                    }
                                }).show();
                    }
                });

        inputDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                }).show();


    }
}
