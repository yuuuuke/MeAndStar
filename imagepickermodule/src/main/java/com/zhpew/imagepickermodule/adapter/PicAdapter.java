package com.zhpew.imagepickermodule.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zhpew.imagepickermodule.R;
import com.zhpew.imagepickermodule.bean.PicItem;
import com.zhpew.imagepickermodule.utils.ImageLoader;
import com.zhpew.imagepickermodule.utils.Utils;

import java.util.ArrayList;

public class PicAdapter extends RecyclerView.Adapter<PicAdapter.BaseHolder> {

    private ArrayList<PicItem> mData;
    private int maxCount = Integer.MAX_VALUE;
    private int count = 0;
    public ImageLoader loader;

    public void setData(ArrayList<PicItem> data) {
        loader = new ImageLoader();
        loader.initLoader();
        this.mData = data;
    }

    public void setCount(int count) {
        this.maxCount = count;
    }

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == PicItem.TYPE_IMG) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_image_and_video_item, parent, false);
            return new PicAndVideoHolder(view);
        } else if (viewType == PicItem.TYPE_VIDEO) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_image_and_video_item, parent, false);
            return new PicAndVideoHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_date_item, parent, false);
            return new DateHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
        holder.bindView(mData.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull BaseHolder holder) {
        holder.detachView();
    }

    class PicAndVideoHolder extends BaseHolder {

        private ImageView ivPic;
        private ImageView ivHasLocation;
        private TextView tvType;
        private PicItem item;
        private ImageView ivSelected;
        private View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count >= maxCount){
                    Toast.makeText(v.getContext(), "最多选择" + maxCount + "张图片", Toast.LENGTH_SHORT).show();
                    return;
                }
                item.setSelected(!item.isSelected());
                if(item.isSelected()){
                    count ++;
                }else{
                    count --;
                }
                notifyItemChanged(getAbsoluteAdapterPosition());
            }
        };

        public PicAndVideoHolder(@NonNull View itemView) {
            super(itemView);
            ivPic = itemView.findViewById(R.id.iv_image);
            ivHasLocation = itemView.findViewById(R.id.iv_has_location);
            tvType = itemView.findViewById(R.id.tv_type);
            ivSelected = itemView.findViewById(R.id.iv_selected);
            itemView.setOnClickListener(listener);
        }

        @Override
        public void bindView(PicItem item) {
            this.item = item;
            loader.load(item, ivPic);
            if (item.isHasLocationInfo()) {
                ivHasLocation.setVisibility(View.VISIBLE);
            } else {
                ivHasLocation.setVisibility(View.GONE);
            }
            if (item.getType() == PicItem.TYPE_IMG) {
                tvType.setText("图片");
            } else {
                tvType.setText("视频");
            }
            if (item.isSelected()) {
                ivSelected.setVisibility(View.VISIBLE);
            } else {
                ivSelected.setVisibility(View.GONE);
            }
        }

        @Override
        public void detachView() {
            loader.detachImg(item.getUrl());
        }
    }

    class DateHolder extends BaseHolder {

        private TextView mTvDate;

        public DateHolder(@NonNull View itemView) {
            super(itemView);
            mTvDate = itemView.findViewById(R.id.tv_date);
        }

        @Override
        public void bindView(PicItem item) {
            mTvDate.setText(Utils.stampToDate(item.getDate()));
        }
    }

    abstract static class BaseHolder extends RecyclerView.ViewHolder {

        public BaseHolder(View itemView) {
            super(itemView);
        }

        abstract public void bindView(PicItem item);

        public void detachView() {

        }
    }
}
