package com.own.simplecustomview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.own.simplecustomview.listener.OnIemClickListener;
import com.own.simplecustomview.R;

import java.util.List;

/**
 * Created by dhy on 2016/12/15.
 */

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.MyHolder> implements View.OnClickListener{
    private Context context;
    private List<String> list;
    private OnIemClickListener mOnIemClickListener;

    public MyListAdapter(List<String> mList, Context mContext) {
        list = mList;
        context = mContext;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        view.setOnClickListener(this);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.mTextView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return (list == null) ? 0 : list.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnIemClickListener != null) {
            mOnIemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }

    public void setOnIemClickListener(OnIemClickListener onIemClickListener) {
        this.mOnIemClickListener = onIemClickListener;
    }


    protected class MyHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public MyHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_list);
        }
    }



}
