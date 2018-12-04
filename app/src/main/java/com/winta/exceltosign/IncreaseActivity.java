package com.winta.exceltosign;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.winta.exceltosign.Adapter.ListViewAdapter;
import com.winta.exceltosign.PictureSelect.FileUtil;
import com.winta.exceltosign.PictureSelect.PictureSelectDialog;
import com.winta.exceltosign.db.ProjectTable;
import com.winta.exceltosign.db.SignTable;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class IncreaseActivity extends AppCompatActivity implements ListViewAdapter.CallBack {

    private ListView mLv;
    private Button btn_save;
    List<SignTable> mSignTables = new ArrayList<SignTable>();
    List<ProjectTable> mProjectTables = new ArrayList<>();
    ListViewAdapter mListViewAdapter;
    private boolean isData = false;//是否有数据导入
    SignTable signTable;//项目内容表
    private String beginDate;//项目开始时间
    ProjectTable projectTable;//项目清单表

    int titleType;//标题的种类
    int headType;//表头种类
    Uri mUri;

    ImageView mImageView_a;
    ImageView mImageView_b;
    String mInmageView_a_address;//a图片的地址 //就是assets重的照片文件名
    String mInmageView_b_address;//b图片的地址
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_increase);

        final Intent intent = getIntent();

        //判断是那个title
        titleType = intent.getIntExtra("title", 1);
        LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.Ll_1);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.Ll_2);
        if (titleType == 1) {
            linearLayout2.setVisibility(View.GONE);
            mImageView_a = (ImageView) findViewById(R.id.image1);
            mImageView_b = (ImageView) findViewById(R.id.image2);
            mEditText = (EditText) findViewById(R.id.Et_title1);
        } else {
            linearLayout1.setVisibility(View.GONE);
            mImageView_a = (ImageView) findViewById(R.id.image3);
            mImageView_b = (ImageView) findViewById(R.id.image4);
            mEditText = (EditText) findViewById(R.id.Et_title2);
        }

        //判断是哪个表头
        headType = intent.getIntExtra("table_head", 3);
        View tablehead1 = findViewById(R.id.tablehead1);
        View tablehead2 = findViewById(R.id.tablehead2);
        if (headType == 3) {
            tablehead2.setVisibility(View.GONE);
        } else {
            tablehead1.setVisibility(View.GONE);
        }


        //选择图片
        mImageView_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toPictureSelect(1);
            }
        });
        mImageView_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toPictureSelect(2);
            }
        });


        //解析传过来的数据
        mUri = Uri.parse(intent.getStringExtra("Uri"));
        importSheet(mUri);
        isData = true;

//        if (intent.ACTION_VIEW.equals(intent.getAction())) {
//
//            Uri uri = intent.getData();
//            //解析Uri传过来的xls数据
//            importSheet(uri);
//            isData = true;
//        }


        //配置adapter
        mLv = (ListView) findViewById(R.id.Lv_main);
        mListViewAdapter = new ListViewAdapter(mSignTables, this);
        mLv.setAdapter(mListViewAdapter);

        //判断有没有重复

        mProjectTables = DataSupport.findAll(ProjectTable.class);
        final List<String> names = new ArrayList<>();
        for (int i = 0; i < mProjectTables.size(); i++) {
            names.add(mProjectTables.get(i).getProjectName());
        }

        btn_save = (Button) findViewById(R.id.Btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (names.contains(mEditText.getText().toString())) {
                    Toast.makeText(IncreaseActivity.this, "已经有此项目", Toast.LENGTH_SHORT).show();
                } else {
                    if (mInmageView_a_address != null && mInmageView_b_address != null && mEditText.getText().toString().length() != 0) {
                        for (int i = 0; i < mSignTables.size(); i++) {
                            signTable = mSignTables.get(i);
                            signTable.setProjectName(mEditText.getText().toString());
                            signTable.save();
                        }
                        getRightDate();//获取当前日期：xxxx年xx月xx日
                        projectTable = new ProjectTable();
                        projectTable.setProjectName(mEditText.getText().toString());
                        projectTable.setBeginDate(beginDate);
                        projectTable.setImage_a(mInmageView_a_address);
                        projectTable.setImage_b(mInmageView_b_address);
                        projectTable.setTitle_type(titleType);
                        projectTable.setHead_type(headType);
                        projectTable.save();
                        Intent intent1 = new Intent(IncreaseActivity.this, MainActivity.class);
                        startActivity(intent1);
                        finish();

                    } else {

                        Toast.makeText(IncreaseActivity.this, "请配置全部内容", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }


    //选择图片
    void toPictureSelect(int i) {
        Intent intent = new Intent(this, PictureSelectDialog.class);
        startActivityForResult(intent, i);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        String selectPicture = data.getStringExtra(PictureSelectDialog.KEY_PATH);
        if (TextUtils.isEmpty(selectPicture)) {
            return;
        }
        Bitmap bitmap = FileUtil.getAssetsBitmap(this, selectPicture);

        switch (requestCode) {
            case 1:
                mImageView_a.setImageBitmap(bitmap);
                mInmageView_a_address = selectPicture;
                break;
            case 2:
                mImageView_b.setImageBitmap(bitmap);
                mInmageView_b_address = selectPicture;
                break;
            default:
                break;

        }


    }

    @Override
    public void onClick(View v) {

    }

    //解析Uri传过来的xls数据
    //从第三行开始
    //使用poi
    private void importSheet(Uri uri) {

        String path = uri.getPath();
        String type = gettype(path); //xls or xlsx;
        Log.e("asdfasdf", type);
        if ("xls".equals(type)) {
            xlsData(uri);

        } else if ("xlsx".equals(type)) {

            xlsxData(uri);

        } else {
            Log.e("导入", "位置版本");
        }

    }

    //根据路径查询是xls文件还是xlsx文件
    public static String gettype(String path) {
        if (path == null || "".equals(path.trim())) {
            return "";
        }
        if (path.contains(".")) {
            return path.substring(path.lastIndexOf(".") + 1, path.length());
        }
        return "";
    }

    //导入xls文件
    private void xlsData(Uri uri) {
        try {
            InputStream is = getContentResolver().openInputStream(uri);
            POIFSFileSystem fs = new POIFSFileSystem(is);
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();
            loopRow:
            for (int i = 2; i < rows; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    int cells = row.getPhysicalNumberOfCells();
                    signTable = new SignTable();
                    for (int j = 0; j < cells; j++) {
                        row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                        Cell cell = row.getCell(j);
                        switch (j) {
                            case 0:
                                signTable.setNumber(cell.getStringCellValue());
                                break;
                            case 1:
                                signTable.setProcess(cell.getStringCellValue());
                                break;
                            case 2:
                                signTable.setTestContent(cell.getStringCellValue());
                                break;
                            case 3:
                                if (cell.getStringCellValue().equals("")) {
                                    break loopRow;
                                } else {
                                    signTable.setControl(cell.getStringCellValue());
                                    break;
                                }

                            case 4:
                                signTable.setStandard(cell.getStringCellValue());
                                break;
                            default:
                                break;
                        }
                    }
                    mSignTables.add(signTable);

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //导入xlsx文件
    private void xlsxData(Uri uri) {
        try {
            InputStream is = getContentResolver().openInputStream(uri);
            XSSFWorkbook wb = new XSSFWorkbook(is);
            XSSFSheet sheet = wb.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();
            loopRow:
            for (int i = 2; i < rows; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    int cells = row.getPhysicalNumberOfCells();
                    signTable = new SignTable();
                    for (int j = 0; j < cells; j++) {
                        row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                        Cell cell = row.getCell(j);
                        switch (j) {
                            case 0:
                                signTable.setNumber(cell.getStringCellValue());
                                break;
                            case 1:
                                signTable.setProcess(cell.getStringCellValue());
                                break;
                            case 2:
                                signTable.setTestContent(cell.getStringCellValue());
                                break;
                            case 3:
                                if (cell.getStringCellValue().equals("")) {
                                    break loopRow;
                                } else {
                                    signTable.setControl(cell.getStringCellValue());
                                    break;
                                }

                            case 4:
                                signTable.setStandard(cell.getStringCellValue());
                                break;
                            default:
                                break;
                        }
                    }
                    mSignTables.add(signTable);

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getRightDate() {
        //获取时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (month > 9 && day >= 10) {
            beginDate = year + "年" + month + "月" + day + "日";
        } else if (month <= 9 && day < 10) {
            beginDate = year + "年" + "0" + month + "月" + "0" + day + "日";
        } else if (month <= 9 && day >= 10) {
            beginDate = year + "年" + "0" + month + "月" + day + "日";
        } else if (month > 9 && day < 10) {
            beginDate = year + "年" + month + "月" + "0" + day + "日";
        }
        return beginDate;
    }
}
