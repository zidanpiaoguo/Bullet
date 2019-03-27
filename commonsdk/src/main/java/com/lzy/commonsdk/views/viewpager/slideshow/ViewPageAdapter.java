package com.lzy.commonsdk.views.viewpager.slideshow;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lzy.commonsdk.utils.imageload.ImageUtils;

import java.util.List;

/**
 *
 * viewpager 的辅助适配器
 *
 * @author bullet
 * @date 2019\1\4 0004.
 */

public class ViewPageAdapter<T> extends PagerAdapter {


    private Context mContext;
    private List<T> mImages;
    private OnItemClickListener mOnItemClickListener;
    /**
     * 真是图片数量
     */
    private int totalSize;
    /**
     * 是否是网络图片
     * 0: 本地加载drawable(Int), 1: 网络加载url (String)，2: View数据(Fragment)
     */
    private int loadSign;
    /**
     * 是否是一张图片
     */
    private boolean mIsOneImg = false;


    public ViewPageAdapter(Context mContext, List<T> mList, int sign) {
        this.mContext = mContext;
        this.mImages = mList;
        this.loadSign = sign;
        if(mList.size()<=1){
            totalSize =1;
            mIsOneImg = true;
        } else {
            totalSize = mList.size();
            mIsOneImg = false;
        }
    }

    @Override
    public int getCount() {
        //当只有一张图片时返回1
        if (mIsOneImg) {
            return 1;
        } else {
            return mImages.size() + 2;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view ==object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        // TODO Auto-generated method stub

        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(toRealPosition(position));
                }
            }
        });

        switch (loadSign){
            case 0:
                imageView.setImageResource((Integer) mImages.get(toRealPosition(position)));
                break;
            case 1:
                ImageUtils.loadBasicImg(mContext, (String) mImages.get(toRealPosition(position)),imageView);
                break;
            case 2:
            default:
        }
        container.addView(imageView);
        return imageView;
    }

    /**
     * 返回真实的位置
     *
     * @param position
     * @return
     */
    private int toRealPosition(int position) {
        return (position) % totalSize;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }



    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    private interface OnItemClickListener{
        /**
         * 点击事件处理
         * @param position 数量
         */
        void  onItemClick(int position);
    }





}
