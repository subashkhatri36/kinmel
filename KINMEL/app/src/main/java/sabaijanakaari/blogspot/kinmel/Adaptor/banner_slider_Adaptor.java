package sabaijanakaari.blogspot.kinmel.Adaptor;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import sabaijanakaari.blogspot.kinmel.Model.banner_Slider_Model;
import sabaijanakaari.blogspot.kinmel.R;

public class banner_slider_Adaptor extends PagerAdapter {
    //Variable Defination
    private List<banner_Slider_Model> list;

    public banner_slider_Adaptor(List<banner_Slider_Model> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(container.getContext()).inflate(R.layout.slider_banner_layout,container,false);
        ImageView banner=(ImageView)view.findViewById(R.id.banner_slide_image);
        Glide.with(container.getContext()).load(list.get(position).getBannerLink()).apply(new RequestOptions().placeholder(R.drawable.bigplaceholderimage)).into(banner);
        ConstraintLayout layout=view.findViewById(R.id.banner_container);
        layout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(list.get(position).getBackgroundColor())));
        container.addView(view,0);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
