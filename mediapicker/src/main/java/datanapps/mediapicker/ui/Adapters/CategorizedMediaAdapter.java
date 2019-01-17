package datanapps.mediapicker.ui.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;



import java.util.List;

import datanapps.mediapicker.R;


/*
 *  Yogendra
 *
 * https://github.com/datanapps
 *
 * 10-01-2019 */
public class CategorizedMediaAdapter extends RecyclerView.Adapter<CategorizedMediaAdapter.MyViewHolder>{
    private List<String> bucketNames,bitmapList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail=(ImageView) view.findViewById(R.id.image);
        }
    }

    public CategorizedMediaAdapter(List<String> bucketNames, List<String> bitmapList, Context context) {
        this.bucketNames=bucketNames;
        this.bitmapList = bitmapList;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_album_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        bucketNames.get(position);
        holder.title.setText(bucketNames.get(position));
        Glide.with(context).load("file://"+bitmapList.get(position)).apply(new RequestOptions().override(300,300).centerCrop()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return bucketNames.size();
    }
}

