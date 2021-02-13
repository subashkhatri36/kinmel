package sabaijanakaari.blogspot.kinmel.Adaptor;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import sabaijanakaari.blogspot.kinmel.Model.HomepageModel;
import sabaijanakaari.blogspot.kinmel.Model.Whistlist_Model;
import sabaijanakaari.blogspot.kinmel.Model.banner_Slider_Model;
import sabaijanakaari.blogspot.kinmel.Model.horizental_product_Scroll_Model;
import sabaijanakaari.blogspot.kinmel.ProductdetailActivity;
import sabaijanakaari.blogspot.kinmel.R;
import sabaijanakaari.blogspot.kinmel.ViewallActivity;

public class HomePageAdaptor extends RecyclerView.Adapter {

    private RecyclerView.RecycledViewPool recylerviewPull;
    private List<HomepageModel> homepageModelList;
    private int lastposition=-1;

    public HomePageAdaptor(List<HomepageModel> homepageModelList) {
        this.homepageModelList = homepageModelList;
        recylerviewPull=new RecyclerView.RecycledViewPool();
    }

    @Override
    public int getItemViewType(int position) {
        switch (homepageModelList.get(position).getType()) {
            case 0:
                return HomepageModel.BANNER_SLIDER;
            case 1:
                return HomepageModel.STRIP_ADS_BANNER;
            case 2:
                return HomepageModel.HORIZENTAL_PRODUCT_VIEW;
            case 3:
                return HomepageModel.GRIDVIEW_PRODCUT_VIEW;
            default:
                return -1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case HomepageModel.BANNER_SLIDER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sliding_ad_banner_layout, parent, false);
                return new BannerSliderViewHolder(view);

            case HomepageModel.STRIP_ADS_BANNER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.strip_ad_layout, parent, false);
                return new StripAdViewHolder(view);

            case HomepageModel.HORIZENTAL_PRODUCT_VIEW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizantal_scroll_layout, parent, false);
                return new Horizental_ProductViewHolder(view);
            case HomepageModel.GRIDVIEW_PRODCUT_VIEW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_layout, parent, false);
                return new Grid_productViewHolder(view);

            default:
                return null;

        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (homepageModelList.get(position).getType()) {
            case HomepageModel.BANNER_SLIDER:
                List<banner_Slider_Model> slideModelList = homepageModelList.get(position).getBannerSlideModelList();
                ((BannerSliderViewHolder) holder).setBannerSlider_viewpager(slideModelList);
                break;
            case HomepageModel.STRIP_ADS_BANNER:
                String resource=homepageModelList.get(position).getResources();
                String color=homepageModelList.get(position).getBackgroundcolor();
                ((StripAdViewHolder)holder).setStripAds(resource,color);
                break;
            case HomepageModel.HORIZENTAL_PRODUCT_VIEW:
                String title=homepageModelList.get(position).getProduct_title();
                String colour=homepageModelList.get(position).getLayout_background();
                List<horizental_product_Scroll_Model> horizental_product_scroll_models=homepageModelList.get(position).getHorizental_product_scroll_models();
                List<Whistlist_Model> viewallproduct=homepageModelList.get(position).getViewAllproductlist();
                ((Horizental_ProductViewHolder)holder).sethorizentalProductLayout(horizental_product_scroll_models,title,colour,viewallproduct);
                break;
            case HomepageModel.GRIDVIEW_PRODCUT_VIEW:
                String gridtitle=homepageModelList.get(position).getProduct_title();
                String coloruri=homepageModelList.get(position).getLayout_background();
                List<horizental_product_Scroll_Model> grid_horizental_product_scroll_models=homepageModelList.get(position).getHorizental_product_scroll_models();
                ((Grid_productViewHolder)holder).setGridProdcutLayout(grid_horizental_product_scroll_models,gridtitle,coloruri);
                break;

            default:
                return;
        }
        if(lastposition<position){
            Animation animation= AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.fading);
            holder.itemView.setAnimation(animation);
            lastposition=position;
        }

    }

    @Override
    public int getItemCount() {
        return homepageModelList.size();
    }
    ///////////////Gridview product viewHolder
    public class Grid_productViewHolder extends RecyclerView.ViewHolder{
        TextView girdlayoutTitle;
        Button girdlayoutButton;
        private GridLayout gridProductLayout;
        private ConstraintLayout container;

        public Grid_productViewHolder(View itemView) {

            super(itemView);
            girdlayoutTitle=itemView.findViewById(R.id.grid_product_layout_textview_title);
            girdlayoutButton=itemView.findViewById(R.id.grid_product_layout_button_viewall);
            gridProductLayout=itemView.findViewById(R.id.grid_layout_product);
            container=itemView.findViewById(R.id.gridcontainer);

        }
        private void setGridProdcutLayout(final List<horizental_product_Scroll_Model> list, final String titte, String color){
            girdlayoutTitle.setText(titte);
            if(!color.isEmpty() && !color.equals("")){
                container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            }

            for( int i=0;i<4;i++){
                ImageView productImage=gridProductLayout.getChildAt(i).findViewById(R.id.hs_product_image);
                final TextView productTitle=gridProductLayout.getChildAt(i).findViewById(R.id.hs_product_title);
                TextView productdescritption=gridProductLayout.getChildAt(i).findViewById(R.id.hs_product_description);
                TextView productprice=gridProductLayout.getChildAt(i).findViewById(R.id.hs_product_pricing);
                Glide.with(itemView.getContext()).load(list.get(i).getProductImage()).apply(new RequestOptions().placeholder(R.drawable.placeholderimge)).into(productImage);
                //productImage.setImageResource(list.get(i).getProductImage());
                productTitle.setText(list.get(i).getProductTitle());
                productdescritption.setText(list.get(i).getProductDescription());
                productprice.setText("Rs. "+list.get(i).getProductPrice()+" /-");
                gridProductLayout.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));

                if(!titte.equals("")){
                    final int finalI = i;
                    gridProductLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent productdetailIntent=new Intent(itemView.getContext(), ProductdetailActivity.class);
                            productdetailIntent.putExtra("ProductName",productTitle.getText());
                            productdetailIntent.putExtra("productId",list.get(finalI).getProductId());
                            itemView.getContext().startActivity(productdetailIntent);
                        }
                    });
                }

            }

            if(!titte.equals("")){
                girdlayoutButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       ViewallActivity.horizental_product_scroll_models=list;
                        Intent viewallIntent=new Intent(itemView.getContext(), ViewallActivity.class);
                        viewallIntent.putExtra("layoutCode",1);
                        viewallIntent.putExtra("title",titte);
                        itemView.getContext().startActivity(viewallIntent);
                    }
                });

            }

        }

    }

///////////////Gridview product viewHolder

    ///////////////HOrizental product viewHolder
    public class Horizental_ProductViewHolder extends RecyclerView.ViewHolder{
        private TextView horizental_layout_title;
        private Button horizental_viewall_button;
        private RecyclerView horizental_recyclerView;
        private ConstraintLayout hs_container;

        public Horizental_ProductViewHolder(View itemView) {
            super(itemView);
            horizental_layout_title=itemView.findViewById(R.id.hs_layout_title);
            hs_container=itemView.findViewById(R.id.hs_container);
            horizental_viewall_button=itemView.findViewById(R.id.hs_layout_viewall);
            horizental_recyclerView=itemView.findViewById(R.id.hs_layout_recycleview);
            horizental_recyclerView.setRecycledViewPool(recylerviewPull);


        }
        private void sethorizentalProductLayout(List<horizental_product_Scroll_Model> horizental_product_scroll_models, final String title, String color, final List<Whistlist_Model> viewallproductlist){
            hs_container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            horizental_layout_title.setText(title);

            if(horizental_product_scroll_models.size()>8){
                horizental_viewall_button.setVisibility(View.VISIBLE);
                horizental_viewall_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewallActivity.whistlist_modelList=viewallproductlist;
                        Intent viewallIntent=new Intent(itemView.getContext(), ViewallActivity.class);
                        viewallIntent.putExtra("layoutCode",0);
                        viewallIntent.putExtra("title",title);
                       itemView.getContext().startActivity(viewallIntent);

                    }
                });

            }else{
                horizental_viewall_button.setVisibility(View.INVISIBLE);
            }
            horizental_product_Scroll_Adaptor horizental_product_scroll_adaptor=new horizental_product_Scroll_Adaptor(horizental_product_scroll_models);
            LinearLayoutManager horizentallayoutManager=new LinearLayoutManager(itemView.getContext());
            horizentallayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizental_recyclerView.setLayoutManager(horizentallayoutManager);
            horizental_recyclerView.setAdapter(horizental_product_scroll_adaptor);
            horizental_product_scroll_adaptor.notifyDataSetChanged();
        }
    }
    ///////////////HOrizental product viewHolder

    ///////////////banner Slider class Start
    public class BannerSliderViewHolder extends RecyclerView.ViewHolder {
        ViewPager bannerSlider_viewpager;
        banner_slider_Adaptor bannerSliderAdaptor;
        int currentpage;
        Timer timer;
        final private long DELAY_TIME = 3000;
        final private long PERIOD_TIME = 3000;
        List<banner_Slider_Model> arrangeList;

        public BannerSliderViewHolder(View itemView) {
            super(itemView);
            bannerSlider_viewpager = (ViewPager) itemView.findViewById(R.id.banner_slider_viewpager);
            //List<banner_Slider_Model> bannerSlideModelLists = new ArrayList<>();
            //setBannerSlider_viewpager(List<banner_Slider_Model> bannerModel);

        }

        private void pageLooper(List<banner_Slider_Model> bannerSlideModelList) {
            if (currentpage == bannerSlideModelList.size() - 2) {
                currentpage = 2;
                bannerSlider_viewpager.setCurrentItem(currentpage, false);
            }
            if (currentpage == 1) {
                currentpage = bannerSlideModelList.size() - 3;
                bannerSlider_viewpager.setCurrentItem(currentpage, false);
            }
        }

        private void startSlideShow(final List<banner_Slider_Model> bannerSlideModelList) {
            final Handler handler = new Handler();
            final Runnable update = new Runnable() {
                @Override
                public void run() {
                    if (currentpage >= bannerSlideModelList.size()) {
                        currentpage = 1;
                    }
                    bannerSlider_viewpager.setCurrentItem(currentpage++, true);
                }
            };
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            }, DELAY_TIME, PERIOD_TIME);
        }

        private void stopBannerSLideshow() {
            timer.cancel();
        }

        private void setBannerSlider_viewpager(final List<banner_Slider_Model> bannerSlideModelList) {
            currentpage=2;
            if(timer!=null){
                timer.cancel();
            }

            arrangeList=new ArrayList<>();
            for(int x=0;x<bannerSlideModelList.size();x++){
                arrangeList.add(x,bannerSlideModelList.get(x));
            }
            arrangeList.add(0,bannerSlideModelList.get(bannerSlideModelList.size()-2));
            arrangeList.add(1,bannerSlideModelList.get(bannerSlideModelList.size()-1));
            arrangeList.add(bannerSlideModelList.get(0));
            arrangeList.add(bannerSlideModelList.get(1));



            bannerSliderAdaptor = new banner_slider_Adaptor(arrangeList);
            bannerSlider_viewpager.setAdapter(bannerSliderAdaptor);
            bannerSlider_viewpager.setClipToPadding(false);
            bannerSlider_viewpager.setPageMargin(20);
            bannerSlider_viewpager.setCurrentItem(currentpage);

            ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentpage = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == bannerSlider_viewpager.SCROLL_STATE_IDLE) {
                        pageLooper(arrangeList);
                    }
                }
            };
            bannerSlider_viewpager.addOnPageChangeListener(onPageChangeListener);
            startSlideShow(arrangeList);
            bannerSlider_viewpager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pageLooper(arrangeList);
                    stopBannerSLideshow();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        startSlideShow(arrangeList);
                    }
                    return false;
                }
            });
        }
    }
    ///////////////banner Slider class ends

    //strip ad banner
    public class StripAdViewHolder extends RecyclerView.ViewHolder {

        ImageView strip_image;
        ConstraintLayout strip_constrainlayou;

        public StripAdViewHolder(View itemView) {
            super(itemView);
            strip_image = (ImageView) itemView.findViewById(R.id.strip_ad_image);
            strip_constrainlayou = (ConstraintLayout) itemView.findViewById(R.id.strip_container);


        }

        private void setStripAds(String resources, String abcd) {
            Glide.with(itemView.getContext()).load(resources).apply(new RequestOptions().placeholder(R.drawable.bigplaceholderimage)).into(strip_image);

            //strip_image.setImageResource(R.drawable.top_banner_one);
            strip_constrainlayou.setBackgroundColor(Color.parseColor(abcd));
        }
    }

    //  private ImageView strip_image;
    //private ConstraintLayout strip_constrainlayou;
    //strip ad banner

}
