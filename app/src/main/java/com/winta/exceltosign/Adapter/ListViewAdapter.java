package com.winta.exceltosign.Adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.winta.exceltosign.R;
import com.winta.exceltosign.db.SignTable;

import java.util.List;


public class ListViewAdapter extends BaseAdapter{

    public int a = 0;//当前表格的位置行
    public int b = 0;//当前列
    public String con="●";//判断那些是不需要签名的
    public boolean canClick = false;
    private Context mContext;
    private List<SignTable> mDatas;
    //自定义 点击接口
    private CallBack mCallBack;


    public ListViewAdapter(List<SignTable> datas) {
        this.mDatas = datas;
    }

    public ListViewAdapter(List<SignTable> datas , CallBack callBack) {
        this.mDatas = datas;
        this.mCallBack = callBack;
    }

    /**
     * 自定义回调接口
     *
     * @return
     */
    public interface CallBack {
        void onClick(View v);
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (mContext == null) {
            mContext = viewGroup.getContext();
        }
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listview_item_increase, null);
            viewHolder = new ViewHolder();
            viewHolder.Tv_number = (TextView) view.findViewById(R.id.Tv_number);
            viewHolder.Tv_process = (TextView) view.findViewById(R.id.Tv_process);
            viewHolder.Tv_content = (TextView) view.findViewById(R.id.Tv_content);
            viewHolder.Tv_control = (TextView) view.findViewById(R.id.Tv_control);
            viewHolder.Tv_standard = (TextView) view.findViewById(R.id.Tv_standard);
            viewHolder.Tv_result = (TextView) view.findViewById(R.id.Tv_result);

            viewHolder.Iv_zhuxiu = (ImageView) view.findViewById(R.id.Iv_zhuxiu);
            viewHolder.Iv_shigong = (ImageView) view.findViewById(R.id.Iv_shigong);
            viewHolder.Iv_zhijian = (ImageView) view.findViewById(R.id.Iv_zhijian);
            viewHolder.Iv_chengben = (ImageView) view.findViewById(R.id.Iv_chengben);
            viewHolder.Iv_dongshebei = (ImageView) view.findViewById(R.id.Iv_dongshebei);
            view.setTag(viewHolder);

        }

        //获取viewHolder实例
        viewHolder = (ViewHolder)view.getTag();

        //设置数据
        viewHolder.Tv_number.setText(mDatas.get(i).getNumber());
        viewHolder.Tv_process.setText(mDatas.get(i).getProcess());
        viewHolder.Tv_content.setText(mDatas.get(i).getTestContent());
        viewHolder.Tv_control.setText(mDatas.get(i).getControl());
        viewHolder.Tv_standard.setText(mDatas.get(i).getStandard());
        viewHolder.Tv_result.setText(mDatas.get(i).getResult());


        Glide.with(mContext).load(mDatas.get(i).getZhuXiu()).skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE).into(viewHolder.Iv_zhuxiu);
        Glide.with(mContext).load(mDatas.get(i).getShiGong()).skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE).into(viewHolder.Iv_shigong);

        //判断那些是不需要签名的
        //□●☆
        //□主修，施工员检验
        //☆主修，施工员检验，质检员，成本中心检验
        //●主修，施工员检验，质检员，成本中心，动设备团队检验
        //将adapter中的点击事件放到activity中处理
        if (mDatas.get(i).getControl().substring(0, 1).equals("☆")) {

            Glide.with(mContext).load(mDatas.get(i).getZhiJian()).skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).into(viewHolder.Iv_zhijian);
            Glide.with(mContext).load(mDatas.get(i).getChenBen()).skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).into(viewHolder.Iv_chengben);
            //动设备为禁止
            Glide.with(mContext).load(R.drawable.ban).skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).into(viewHolder.Iv_dongshebei);




        } else if (mDatas.get(i).getControl().substring(0, 1).equals("□")) {

            //质检员，成本中心，动设备团队为禁止
            Glide.with(mContext).load(R.drawable.ban).skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).into(viewHolder.Iv_zhijian);
            Glide.with(mContext).load(R.drawable.ban).skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).into(viewHolder.Iv_chengben);
            Glide.with(mContext).load(R.drawable.ban).skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).into(viewHolder.Iv_dongshebei);

        } else {

            Glide.with(mContext).load(mDatas.get(i).getZhiJian()).skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).into(viewHolder.Iv_zhijian);
            Glide.with(mContext).load(mDatas.get(i).getChenBen()).skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).into(viewHolder.Iv_chengben);
            Glide.with(mContext).load(mDatas.get(i).getDongSheBei()).skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).into(viewHolder.Iv_dongshebei);
        }


        viewHolder.Tv_result.setTag(R.id.id_result, i);
        viewHolder.Tv_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a = (int) view.getTag(R.id.id_result);
                b=0;

                mCallBack.onClick(view);
            }
        });
        viewHolder.Iv_zhuxiu.setTag(R.id.id_zhuxiu,i);
        viewHolder.Iv_zhuxiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a = (int) view.getTag(R.id.id_zhuxiu);
                b=1;
                mCallBack.onClick(view);
            }
        });
        viewHolder.Iv_shigong.setTag(R.id.id_shigong,i);
        viewHolder.Iv_shigong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a = (int) view.getTag(R.id.id_shigong);
                b = 2;
                mCallBack.onClick(view);
            }
        });
        viewHolder.Iv_zhijian.setTag(R.id.id_zhijian,i);
        viewHolder.Iv_zhijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a = (int) view.getTag(R.id.id_zhijian);
                b = 3;
                con = mDatas.get(a).getControl().substring(0, 1);
                if (con.equals("□")) {
                    Toast.makeText(mContext,"不能签名",Toast.LENGTH_SHORT).show();
                }else {
                    mCallBack.onClick(view);
                }
            }
        });
        viewHolder.Iv_chengben.setTag(R.id.id_chengben,i);
        viewHolder.Iv_chengben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a = (int) view.getTag(R.id.id_chengben);
                b = 4;
                con = mDatas.get(a).getControl().substring(0, 1);
                if (con.equals("□")) {
                    Toast.makeText(mContext,"不能签名",Toast.LENGTH_SHORT).show();
                }else {
                    mCallBack.onClick(view);
                }
            }
        });
        viewHolder.Iv_dongshebei.setTag(R.id.Iv_dongshebei,i);
        viewHolder.Iv_dongshebei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a = (int) view.getTag(R.id.Iv_dongshebei);
                b = 5;
                con = mDatas.get(a).getControl().substring(0, 1);
                if (con.equals("□")||con.equals("☆")) {
                    Toast.makeText(mContext,"不能签名",Toast.LENGTH_SHORT).show();
                }else {
                    mCallBack.onClick(view);
                }
            }
        });



        return view;
    }




      static class ViewHolder {
        TextView Tv_number;
        TextView Tv_process;
        TextView Tv_content;
        TextView Tv_control;
        TextView Tv_standard;
        TextView Tv_result;

        ImageView Iv_zhuxiu;
        ImageView Iv_shigong;
        ImageView Iv_zhijian;
        ImageView Iv_chengben;
        ImageView Iv_dongshebei;
    }





}
