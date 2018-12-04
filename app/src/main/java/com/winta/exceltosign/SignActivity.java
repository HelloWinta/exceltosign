package com.winta.exceltosign;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.winta.exceltosign.handwrite.view.HandWriteView;

import java.io.IOException;

public class SignActivity extends Activity {

    HandWriteView mHandWriteView;
    Button mSave;
    Button mClear;
    Button mCancel;
    TextView mTextView;
    String path;//路径
    String name;//项目名字
    String signSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign);
        final boolean isCrop = getIntent().getBooleanExtra("isCrop", false);
        path = getIntent().getStringExtra("path");
        name = getIntent().getStringExtra("name");
        signSelect = getSignSelect(getIntent().getIntExtra("signSelect",1));
        mHandWriteView=(HandWriteView) findViewById(R.id.view);
        mTextView = (TextView) findViewById(R.id.Tv_signName);
        mTextView.setText(signSelect);
        mSave=findViewById(R.id.save);
        mClear=findViewById(R.id.clear);

        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandWriteView.clear();
            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SignActivity.this);
                builder.setTitle("确认");
                builder.setMessage("确认修改签名吗");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (mHandWriteView.isSign()) {
                            try {
                                if (isCrop) {
                            /*
                               path 存储路径
                               clearblank 清除白边
                               blank 白边距离
                               是否加密
                             */
                                    mHandWriteView.save(path,name,false, 10, false);
                                    setResult(101);
                                    finish();
                                }
                            } catch(IOException e){
                                e.printStackTrace();
                            }
                        }else {
                            Toast.makeText(SignActivity.this, "还没有签名！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.show();


            }
        });

    }

    private String getSignSelect(int b) {
        String str = null;
        switch (b) {
            case 1:
                str="主修";
                break;
            case 2:
                str="施工员";
                break;
            case 3:
                str="质检员";
                break;
            case 4:
                str="成本中心";
                break;
            case 5:
                str="动设备团队";
                break;
        }
        return str;
    }
}