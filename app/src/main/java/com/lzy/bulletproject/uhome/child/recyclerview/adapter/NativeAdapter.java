package com.lzy.bulletproject.uhome.child.recyclerview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.bulletproject.R;

/**
 * @author bullet
 * @date 2019\2\19 0019.
 */

public class NativeAdapter extends RecyclerView.Adapter<NativeAdapter.ViewHolder>  {

    private static final int TYPE_TOP = R.layout.item_rcl_one;
    private static final int TYPE_DOWN = R.layout.item_rcl_two;


    private Context mContext;

    public NativeAdapter(Context mContext) {
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(viewType, parent, false), viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        switch (holder.viewType){
            case TYPE_TOP:

                break;
            case TYPE_DOWN:
                break;
            default:
        }


    }

    /**
     * 主要是为了获得manager 重写spanSizeLookup
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        final GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position==1 ? manager.getSpanCount() : 1 ;
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name1,name2;
        int viewType;

        /**
         * 如果布局不一样可以在这里区分
         * @param itemView
         * @param type
         */
        public ViewHolder(View itemView ,int type) {
            super(itemView);
            this.viewType = type;
            switch (type){
                case TYPE_TOP:
                    name1 = itemView.findViewById(R.id.tv_rcl_one);
                    break;
                case TYPE_DOWN:
                    name2 = itemView.findViewById(R.id.tv_rcl_two);
                    break;
                default:
                    break;
            }
        }
    }
}
