package com.example.zooui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class ImageSliderAdapter extends PagerAdapter {

    private Context context;
    private List<Integer> imageList;
    private String selectedLanguage;
    private OnItemClickListener itemClickListener;

    public ImageSliderAdapter(Context context, List<Integer> imageList, String selectedLanguage) {
        this.context = context;
        this.imageList = imageList;
        this.selectedLanguage = selectedLanguage;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.slider_item, container, false);

        ImageView imageView = itemView.findViewById(R.id.slider_image);
        imageView.setImageResource(imageList.get(position));

        TextView textView = itemView.findViewById(R.id.slider_text); // Add this line

        // Set the text for the current position
        String eventTitle;
        if (position == 0) {
            eventTitle = "Multi-Animal Show";
        } else {
            eventTitle = "Animal Feeding Session";
        }
        textView.setText(eventTitle); // Add this line

        // Set a click listener for the item view
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(position);
                }
            }
        });

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
