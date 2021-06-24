package com.example.findbomd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FindBondAdapter<T> extends BaseAdapter {
    private int id;
    private List<T> mData;
    private Context mContext;
    private CallBack<T> callBack;

    public FindBondAdapter(int id, List<T> mData, Context mContext, CallBack<T> callBack) {
        this.id = id;
        this.mData = mData;
        this.mContext = mContext;
        this.callBack = callBack;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        //数据集中指定索引对应的数据项
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        //指定行所对应的ID
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(id, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        callBack.item(viewHolder, mData.get(position), position);
        return convertView;
    }

    public static class ViewHolder {
        View view;

        public ViewHolder(View view) {
            this.view = view;
        }

        public TextView getTextView(int id) {
            return view.findViewById(id);
        }
    }

    public interface CallBack<T> {
        void item(ViewHolder viewHolder, T data, int position);
    }
}
