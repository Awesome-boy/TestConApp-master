package com.it.testconapp.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.it.testconapp.R;

import java.util.List;

/**
 * Created by zt on 2019/3/5.
 */
public class CoordAdapter extends BaseAdapter {
    private Context context;
    private List<String> mlist;

    public CoordAdapter(Context context, List<String> mlist) {
        this.context=context;
        this.mlist=mlist;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int i) {
        return mlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (view==null){
            view=LayoutInflater.from(context).inflate(R.layout.item_listview, viewGroup, false);
            holder=new ViewHolder();
            holder.tv_name=view.findViewById(R.id.tv_name);
            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }
        holder.tv_name.setText(mlist.get(i));
        return view;
    }
    private class ViewHolder{
        TextView tv_name;
    }
}
