package datanapps.multiplemediapicker.java;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import datanapps.multiplemediapicker.R;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class SelectedMediaListAdapter extends RecyclerView.Adapter<SelectedMediaListAdapter.ViewHolder> {

    private List<String> mediaList = new ArrayList<>();
    private LayoutInflater mInflater;

    private Context context;
    // data is passed into the constructor
    SelectedMediaListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }


    public void setMediaList(List<String> data) {
        mediaList.clear();
        this.mediaList.addAll(data);
        notifyDataSetChanged();
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_selected_media_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //CnaImageLoader.getInstance().displayImage("file://" + mediaList.get(position), holder.imageView);
        com.bumptech.glide.Glide.with(context).load("file://"+mediaList.get(position)).apply(new RequestOptions().override(153,160).centerCrop().dontAnimate().skipMemoryCache(true)).transition(withCrossFade()).into(holder.imageView);
        holder.videoIcon.setVisibility(mediaList.get(position).endsWith(".mp4") ? View.VISIBLE : View.GONE);

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mediaList.get(id);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        //@BindView(R.id.selected_image)
        ImageView imageView;

        @Nullable
        //@BindView(R.id.icon_video)
        ImageView videoIcon;

        ViewHolder(View itemView) {
            super(itemView);
            //ButterKnife.bind(this, itemView);
            videoIcon = itemView.findViewById(R.id.icon_video);
            imageView= itemView.findViewById(R.id.selected_image);
        }


    }
}