package com.it.testconapp.adpter;


/**
 * Created by Administrator on 2017/10/25.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.it.testconapp.R;
import com.it.testconapp.javabean.ImageInfor;

import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ImageInfor> list;

    public MyAdapter(List<ImageInfor> list) {
        this.list = list;
    }

    //新建点击事件接口
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 初始化布局视图
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carditem, parent, false);
        return new ViewHolder(view);
    }

    /**
     * 绑定视图组件数据
     */
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.iv_backgroud.setImageBitmap(list.get(position).getImageId());
        holder.tv_title.setText(list.get(position).getName());
        final int postions = position;
        if (mOnItemClickLitener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=holder.getAdapterPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView,pos);
                }
            });
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_backgroud;
        public TextView tv_title;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_backgroud = (ImageView) itemView.findViewById(R.id.picture);
            tv_title = (TextView) itemView.findViewById(R.id.name);
        }
    }

}



