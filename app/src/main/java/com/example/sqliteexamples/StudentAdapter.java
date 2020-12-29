package com.example.sqliteexamples;

import android.content.ClipData;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends BaseAdapter {
    List<ItemModel> items;
    Cursor cs;
    Context context;
    public StudentAdapter(Context context, SQLiteDatabase db, List<ItemModel> items) {
        this.context=context;
        this.items=items;
        cs = db.rawQuery("select * from sinhvien", null);
    }

    @Override
    public int getCount() {
        return cs.getCount();
    }

    @Override
    public Object getItem(int i) {
        cs.moveToPosition(i);
        return cs;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item, viewGroup, false);
            viewHolder=new ViewHolder();
            viewHolder.mssv= view.findViewById(R.id.text_mssv);
            viewHolder.hoTen= view.findViewById(R.id.text_hoten);
            viewHolder.email= view.findViewById(R.id.text_email);
            view.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder) view.getTag();
        }

        if(i>=0){
            ItemModel itemModel=items.get(i);
            viewHolder.mssv.setText(itemModel.mssv);
            viewHolder.hoTen.setText(itemModel.hoTen);
            viewHolder.email.setText(itemModel.email);
        }else return null;

        return view;
    }
    private class ViewHolder{
        TextView mssv;
        TextView hoTen;
        TextView email;
    }

}
