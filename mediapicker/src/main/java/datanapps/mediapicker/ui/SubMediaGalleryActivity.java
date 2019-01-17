package datanapps.mediapicker.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import datanapps.mediapicker.ui.adapters.GalleryMediaAdapter;
import datanapps.mediapicker.R;

import java.util.ArrayList;
import java.util.List;


/*
 *  Yogendra
 *
 * https://github.com/datanapps
 *
 * 10-01-2019 */
public class SubMediaGalleryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GalleryMediaAdapter mAdapter;
    private List<String> mediaList = new ArrayList<>();
    public static List<Boolean> selected = new ArrayList<>();
    public static ArrayList<String> imagesSelected = new ArrayList<>();
    public static String parent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sub_media_gallery);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        findViewById(R.id.gallery_selected_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        toolbar.setNavigationIcon(R.drawable.arrow_back);
        setTitle(DNAGalleryPickerActivity.title);
        if (imagesSelected.size() > 0) {
            setTitle(String.valueOf(imagesSelected.size()));
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView = findViewById(R.id.content_open_gallery_recycler_view);
        parent = getIntent().getExtras().getString("FROM");
        mediaList.clear();
        selected.clear();
        if (parent.equals("Images")) {
            mediaList.addAll(ImageFragment.imagesList);
            selected.addAll(ImageFragment.selected);
        } else {
            mediaList.addAll(VideoFragment.videosList);
            selected.addAll(VideoFragment.selected);
        }
        populateRecyclerView();
    }


    private void populateRecyclerView() {
        for (int i = 0; i < selected.size(); i++) {
            if (imagesSelected.contains(mediaList.get(i))) {
                selected.set(i, true);
            } else {
                selected.set(i, false);
            }
        }
        mAdapter = new GalleryMediaAdapter(mediaList, selected, getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.getItemAnimator().setChangeDuration(0);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (!selected.get(position).equals(true) && imagesSelected.size() < DNAGalleryPickerActivity.maxSelection) {
                    imagesSelected.add(mediaList.get(position));
                    selected.set(position, !selected.get(position));
                    mAdapter.notifyItemChanged(position);
                } else if (selected.get(position).equals(true)) {
                    if (imagesSelected.indexOf(mediaList.get(position)) != -1) {
                        imagesSelected.remove(imagesSelected.indexOf(mediaList.get(position)));
                        selected.set(position, !selected.get(position));
                        mAdapter.notifyItemChanged(position);
                    }
                }
                DNAGalleryPickerActivity.selectionTitle = imagesSelected.size();
                if (imagesSelected.size() != 0) {
                    setTitle(String.valueOf(imagesSelected.size()+"/"+ DNAGalleryPickerActivity.maxSelection));
                } else {
                    setTitle(DNAGalleryPickerActivity.title);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }

        }));
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;
        private SubMediaGalleryActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final SubMediaGalleryActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }

}

