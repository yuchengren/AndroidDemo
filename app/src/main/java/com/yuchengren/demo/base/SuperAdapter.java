package com.yuchengren.demo.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchengren on 2016/8/15.
 */
public abstract class SuperAdapter<T> extends BaseAdapter{

    protected   String TAG = this.getClass().getSimpleName();

    protected Context mContext;
    protected List<T> mDataList;

    public SuperAdapter(Context context,List<T> dataList) {
        this.mContext = context;
        if(dataList == null){
            this.mDataList = new ArrayList<>();
        }else{
            this.mDataList = dataList;
        }
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public T getItem(int position) {
        if(position > mDataList.size() - 1){
            return null;
        }
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if(convertView == null){
            convertView = inflateItemView(position);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        inflateItemChildView(position,holder);

        return convertView;
    }

    protected abstract View inflateItemView(int position);

    protected abstract void inflateItemChildView(int position, ViewHolder holder) ;



    protected static class ViewHolder{
        private View view;
        private SparseArray<View> mSparseArray = new SparseArray<>();

        public ViewHolder(View view){
            this.view = view;
        }
        public <V extends View> V getView(int resLayoutId) {
            V childView = (V) mSparseArray.get(resLayoutId);
            if(childView == null){
                childView = (V) view.findViewById(resLayoutId);
                mSparseArray.put(resLayoutId,childView);
            }
            return childView;
        }
    }

    public void clear(){
        mDataList.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<T> list){
        mDataList.addAll(list);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> list){
        mDataList.clear();
        addAll(list);
    }

    public void add(T data){
        mDataList.add(data);
        notifyDataSetChanged();
    }

    public void add(T data,int position){
        mDataList.add(position,data);
        notifyDataSetChanged();
    }
}
