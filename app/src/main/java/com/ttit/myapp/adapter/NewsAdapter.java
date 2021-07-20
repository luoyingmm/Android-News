package com.ttit.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.ttit.myapp.R;
import com.ttit.myapp.entity.NewsEntity;
import com.ttit.myapp.view.CircleTransform;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<NewsEntity> data;
    private OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setData(List<NewsEntity> data) {
        this.data = data;
    }

    public NewsAdapter(Context context, List<NewsEntity> data){
        this.mContext = context;
        this.data = data;
    }

    public NewsAdapter(Context context){
        this.mContext = context;
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        int type = data.get(position).getType();
        return type;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if (viewType == 1){
            View view = LayoutInflater.from(mContext).inflate(R.layout.news_item_one,parent,false);
            return new ViewHolderOne(view);
        }else if (viewType == 2){
            View view = LayoutInflater.from(mContext).inflate(R.layout.news_item_two,parent,false);
            return new ViewHolderTwo(view);
        }else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.news_item_three,parent,false);
            return new ViewHolderThree(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        NewsEntity newsEntity1 = data.get(position);
        if (type == 1){
            ViewHolderOne vh = (ViewHolderOne) holder;
            vh.title.setText(newsEntity1.getNewsTitle());
            vh.author.setText(newsEntity1.getAuthorName());
            vh.comment.setText(newsEntity1.getCommentCount()+"评论 .");
            vh.time.setText(newsEntity1.getReleaseDate());
            vh.newsEntity = newsEntity1;

            Picasso.with(mContext).load(newsEntity1.getHeaderUrl())
                    .transform(new CircleTransform())
                    .into(vh.header);
            Picasso.with(mContext)
                    .load(newsEntity1.getThumbEntities().get(0).getThumbUrl())
                    .into(vh.thumb);
        }else if (type == 2){
            ViewHolderTwo vh = (ViewHolderTwo) holder;
            vh.title.setText(newsEntity1.getNewsTitle());
            vh.author.setText(newsEntity1.getAuthorName());
            vh.comment.setText(newsEntity1.getCommentCount()+"评论 .");
            vh.time.setText(newsEntity1.getReleaseDate());
            vh.newsEntity = newsEntity1;

            Picasso.with(mContext).load(newsEntity1.getHeaderUrl())
                    .transform(new CircleTransform())
                    .into(vh.header);
            Picasso.with(mContext)
                    .load(newsEntity1.getThumbEntities().get(0).getThumbUrl())
                    .into(vh.pic1);
            Picasso.with(mContext)
                    .load(newsEntity1.getThumbEntities().get(1).getThumbUrl())
                    .into(vh.pic2);
            Picasso.with(mContext)
                    .load(newsEntity1.getThumbEntities().get(2).getThumbUrl())
                    .into(vh.pic3);
        }else {
            ViewHolderThree vh = (ViewHolderThree) holder;
            vh.title.setText(newsEntity1.getNewsTitle());
            vh.author.setText(newsEntity1.getAuthorName());
            vh.comment.setText(newsEntity1.getCommentCount()+"评论 .");
            vh.time.setText(newsEntity1.getReleaseDate());
            vh.newsEntity = newsEntity1;

            Picasso.with(mContext).load(newsEntity1.getHeaderUrl())
                    .transform(new CircleTransform())
                    .into(vh.header);
            Picasso.with(mContext)
                    .load(newsEntity1.getThumbEntities().get(0).getThumbUrl())
                    .into(vh.thumb);
        }
        NewsEntity newsEntity = data.get(position);
    }

    @Override
    public int getItemCount() {
        if (data != null && data.size()>0){
            return data.size();
        }else {
            return 0;
        }

    }

    public class ViewHolderOne extends RecyclerView.ViewHolder {
        private TextView title,author,comment,time;
        private ImageView header,thumb;
        private NewsEntity newsEntity;
        public ViewHolderOne(@NonNull @NotNull View view) {
            super(view);
            title = view.findViewById(R.id.tvTitle1);
            author = view.findViewById(R.id.author);
            comment = view.findViewById(R.id.comment);
            time = view.findViewById(R.id.time);
            header = view.findViewById(R.id.header);
            thumb = view.findViewById(R.id.thumb);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(newsEntity);
                }
            });
        }
    }


    public class ViewHolderTwo extends RecyclerView.ViewHolder {
        private TextView title,author,comment,time;
        private ImageView header,pic1,pic2,pic3;
        private NewsEntity newsEntity;
        public ViewHolderTwo(@NonNull @NotNull View view) {
            super(view);
            title = view.findViewById(R.id.tv_title_2);
            author = view.findViewById(R.id.author);
            comment = view.findViewById(R.id.comment);
            time = view.findViewById(R.id.time);
            header = view.findViewById(R.id.header);
            pic1 = view.findViewById(R.id.pic1);
            pic2 = view.findViewById(R.id.pic2);
            pic3 = view.findViewById(R.id.pic3);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(newsEntity);
                }
            });
        }
    }


    public class ViewHolderThree extends RecyclerView.ViewHolder {
        private TextView title,author,comment,time;
        private ImageView header,thumb;
        private NewsEntity newsEntity;
        public ViewHolderThree(@NonNull @NotNull View view) {
            super(view);
            title = view.findViewById(R.id.tvTitle3);
            author = view.findViewById(R.id.author);
            comment = view.findViewById(R.id.comment);
            time = view.findViewById(R.id.time);
            header = view.findViewById(R.id.header);
            thumb = view.findViewById(R.id.thumb);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(newsEntity);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Serializable obj);
    }


}
