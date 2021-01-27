package com.glf.utilslibrary.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.glf.utilslibrary.R;
import com.glf.utilslibrary.util.AppUtils;

import java.util.List;

/**
 * 自定义下拉框适配器
 */
public class SuperSpinnerAdapter extends RecyclerView.Adapter<SuperSpinnerAdapter.ViewHolder> {
    private List lists;
    private Context context;
    private String keyName;

    public SuperSpinnerAdapter(Context context, List lists, String keyName) {
        this.keyName = keyName;
        this.context = context;
        this.lists = lists;
    }

    //创建View,被LayoutManager所用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_spinner_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //数据的绑定
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String attribute = AppUtils.getAttribute(lists.get(position), keyName);
        holder.tv_item.setText(attribute);

        //单击
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //触发自定义监听的单击事件
                onItemClickListener.onItemClick(holder.itemView, position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    //自定义ViewHolder,包含item的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_item;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_item = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }

    /**
     * 获取当前选中的值
     *
     * @param position
     * @return
     */
    public String getItemValue(int position) {
        return AppUtils.getAttribute(lists.get(position), keyName);
    }

    public void setOnItemClickListener(SuperSpinnerAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

    }

}