package com.winta.exceltosign;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mcxtzhang.commonadapter.lvgv.CommonAdapter;
import com.mcxtzhang.commonadapter.lvgv.ViewHolder;
import com.winta.exceltosign.db.ProjectTable;
import com.winta.exceltosign.db.SignTable;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mLv;
    private List<ProjectTable> mDatas;
    private Button mBtn_increase;

    public String selectProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //增加按钮
        mBtn_increase = (Button) findViewById(R.id.Btn_increase);
        mBtn_increase.setVisibility(View.GONE);
//        mBtn_increase.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(view.getContext(),SelectActivity.class));
//            }
//        });


    }

    //将查询数据放在resume
    @Override
    protected void onResume() {
        super.onResume();
        mLv = (ListView) findViewById(R.id.Lv_project);

        //取出数据
        initDatas();
        //将数据绑定在ListView
        mLv.setAdapter(new CommonAdapter<ProjectTable>(this, mDatas, R.layout.listview_item_swipe) {
            @Override
            public void convert(final ViewHolder holder, final ProjectTable projectTable, final int position) {

                holder.setText(R.id.Tv_projectName, projectTable.getProjectName());
                holder.setText(R.id.Tv_beginDate, "创建日期："+projectTable.getBeginDate());
                holder.setText(R.id.Tv_recentDate, projectTable.getRecentDate());
                //对item监听

                //对编辑按钮的监听
                holder.setOnClickListener(R.id.Btn_edit, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this,"暂未开放此功能",Toast.LENGTH_SHORT).show();
                    }
                });
                //对删除按钮监听
                holder.setOnClickListener(R.id.Btn_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("删除警告");
                        builder.setMessage("确定删除——"+projectTable.getProjectName()+"——这个项目吗？");

                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                final EditText editText = new EditText(MainActivity.this);
                                AlertDialog.Builder inputDialog =
                                        new AlertDialog.Builder(MainActivity.this);
                                inputDialog.setTitle(" 请输入密码").setView(editText);
                                inputDialog.setPositiveButton("确定",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                //删除的密码为123456

                                                if (editText.getText().toString().equals("123456")) {
                                                    //数据表删除
                                                    DataSupport.deleteAll(ProjectTable.class, "projectName = ?", projectTable.getProjectName());
                                                    DataSupport.deleteAll(SignTable.class, "projectName = ?", projectTable.getProjectName());
                                                    //删除签名图片
                                                    deleteDir(projectTable.getProjectName());


                                                    mDatas.remove(position);
                                                    notifyDataSetChanged();
                                                } else {
                                                    Toast.makeText(MainActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                                                }
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

                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                        builder.show();
                    }
                });

                //对整个行进行监听
                holder.setOnClickListener(R.id.Rl_touch, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, ProjectActivity.class);
                        intent.putExtra("ProjectName", projectTable.getProjectName());
                        intent.putExtra("Type", projectTable.getTitle_type());
                        intent.putExtra("Head", projectTable.getHead_type());
                        intent.putExtra("Image_a", projectTable.getImage_a());
                        intent.putExtra("Image_b", projectTable.getImage_b());
                        intent.putExtra("beizhu", projectTable.getBeizhu());
                        startActivity(intent);
                    }
                });


            }
        });

    }
    private void initDatas() {
        mDatas =  DataSupport.findAll(ProjectTable.class);
        Collections.reverse(mDatas);
    }

    //删除文件夹以及文件夹
    private void deleteDir(final String projectName) {
//        File filepath = new File(getApplicationContext().getFilesDir().getAbsolutePath() + projectName + "/");
//        Log.e("dafasdf", getApplicationContext().getFilesDir().getAbsolutePath() + projectName + "/");
//        if (filepath.exists()) {
//            filepath.delete();
//
//        } else {
//            return;
//        }

        File dir = new File(getApplicationContext().getFilesDir().getAbsolutePath() + projectName + "/");
        deleteDirWihtFile(dir);
    }

    public static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWihtFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

}
