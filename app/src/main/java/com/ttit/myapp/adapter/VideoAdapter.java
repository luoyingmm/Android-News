package com.ttit.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dueeeke.videocontroller.component.PrepareView;
import com.squareup.picasso.Picasso;
import com.ttit.myapp.R;
import com.ttit.myapp.entity.VideoEntity;
import com.ttit.myapp.listener.OnItemChildClickListener;
import com.ttit.myapp.listener.OnItemClickListener;
import com.ttit.myapp.view.CircleTransform;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<VideoEntity> data;

    public void setData(List<VideoEntity> data) {
        this.data = data;
    }

    private OnItemChildClickListener mOnItemChildClickListener;

    private OnItemClickListener mOnItemClickListener;
    public VideoAdapter(Context context, List<VideoEntity> data){
        this.mContext = context;
        this.data = data;
    }

    public VideoAdapter(Context context){
        this.mContext = context;
        this.data = data;
    }
    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_video_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        VideoEntity entity = data.get(position);
        vh.tvTitle.setText(entity.getVtitle());
        vh.tvAuthor.setText(entity.getAuthor());
//        vh.tvDz.setText(String.valueOf(String.valueOf(entity.getLikenum())));
//        vh.tvComment.setText(String.valueOf(String.valueOf(entity.getCommentnum())));
//        vh.tvCollect.setText(String.valueOf(String.valueOf(entity.getCollectnum())));
//        Log.e("test", String.valueOf(String.valueOf(entity.getLikenum())));
        Picasso.with(mContext).load(entity.getHeadurl())
                .transform(new CircleTransform())
                .into(vh.imgHeader);
        Picasso.with(mContext)
                .load(entity.getCoverurl())
                .into(vh.mThumb);
        vh.mPosition = position;
    }

    @Override
    public int getItemCount() {
        if (data != null && data.size()>0){
            return data.size();
        }else {
            return 0;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle;
        private TextView tvAuthor;
        private TextView tvDz;
        private TextView tvComment;
        private TextView tvCollect;
        private ImageView imgHeader;
        public ImageView mThumb;
        public PrepareView mPrepareView;
        public FrameLayout mPlayerContainer;
        public int mPosition;
        public ViewHolder(@NonNull @NotNull View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvAuthor = view.findViewById(R.id.author);
            tvDz = view.findViewById(R.id.dz);
            tvComment = view.findViewById(R.id.comment);
            tvCollect = view.findViewById(R.id.collect);
            imgHeader = view.findViewById(R.id.imgHeader);
            mPlayerContainer = view.findViewById(R.id.player_container);
            mPrepareView = view.findViewById(R.id.prepare_view);
            mThumb = mPrepareView.findViewById(R.id.thumb);

            if (mOnItemChildClickListener != null) {
                mPlayerContainer.setOnClickListener(this);
            }
            if (mOnItemClickListener != null) {
                view.setOnClickListener(this);
            }
            //通过tag将ViewHolder和itemView绑定
            view.setTag(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.player_container) {
                if (mOnItemChildClickListener != null) {
                    mOnItemChildClickListener.onItemChildClick(mPosition);
                }
            } else {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(mPosition);
                }
            }

        }
    }
    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        mOnItemChildClickListener = onItemChildClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

}
