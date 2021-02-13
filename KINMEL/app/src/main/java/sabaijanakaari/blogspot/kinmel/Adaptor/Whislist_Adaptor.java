package sabaijanakaari.blogspot.kinmel.Adaptor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import sabaijanakaari.blogspot.kinmel.Database.DBquery;
import sabaijanakaari.blogspot.kinmel.Model.Whistlist_Model;
import sabaijanakaari.blogspot.kinmel.ProductdetailActivity;
import sabaijanakaari.blogspot.kinmel.R;

public class Whislist_Adaptor extends RecyclerView.Adapter<Whislist_Adaptor.viewHolder> {

    List<Whistlist_Model> whistlist_modelList;
    boolean whislist;
    private int lastposition=-1;

    public Whislist_Adaptor(List<Whistlist_Model> whistlist_modelList, boolean whislist) {
        this.whistlist_modelList = whistlist_modelList;
        this.whislist = whislist;
    }

    @Override
    public Whislist_Adaptor.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.whishlist_item_layout, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(Whislist_Adaptor.viewHolder holder, int position) {

        String productId = whistlist_modelList.get(position).getProductId();
        String resources = whistlist_modelList.get(position).getProductImage();
        String title = whistlist_modelList.get(position).getProductTitle();
        long coupen = whistlist_modelList.get(position).getFreeCoupen();
        String rating = whistlist_modelList.get(position).getRating();
        String totalrating = whistlist_modelList.get(position).getTotalrating();
        String pprice = whistlist_modelList.get(position).getProductPrice();
        String cutprice = whistlist_modelList.get(position).getProductCuttedPrice();
        boolean payment = whistlist_modelList.get(position).isCod();
        boolean stock = whistlist_modelList.get(position).isInstock();
        holder.setData(position,resources, title, coupen, rating, totalrating, pprice, cutprice, payment,productId,stock);
        if(lastposition<position){
            Animation animation= AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.fading);
            holder.itemView.setAnimation(animation);
            lastposition=position;
        }

    }

    @Override
    public int getItemCount() {
        return whistlist_modelList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productTile;
        private TextView freeCoupen;
        private ImageView coupenIcon;
        private View priceCut;
        private TextView rating;
        private TextView totalRating;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView paymentMethod;
        private ImageView deleteButton;


        public viewHolder(View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.whislist_product_image);
            productTile = itemView.findViewById(R.id.wishlist_product_title);
            freeCoupen = itemView.findViewById(R.id.whislist_free_coupen);
            coupenIcon = itemView.findViewById(R.id.whislist_copen_icon);
            priceCut = itemView.findViewById(R.id.whishlist_priceCut);
            rating = itemView.findViewById(R.id.tv_product_rating_miniview);
            totalRating = itemView.findViewById(R.id.total_rating);
            productPrice = itemView.findViewById(R.id.whishlist_product_price);
            cuttedPrice = itemView.findViewById(R.id.whishlist_product_cuttedprice);
            paymentMethod = itemView.findViewById(R.id.whishlist_product_paymentMethod);
            deleteButton = itemView.findViewById(R.id.whishlist_delete_button);

        }

        private void setData(final Integer index, String resourc, final String title, long coupen, String rat, String totalR, String pprice, String cutprice, boolean payMethod, final String productId,boolean stock) {
            Glide.with(itemView.getContext()).load(resourc).apply(new RequestOptions().placeholder(R.drawable.loading)).into(productImage);
            //productImage.setImageResource(resourc);
            productTile.setText(title);
            LinearLayout ratingparent=(LinearLayout) rating.getParent();
            if(stock){
                rating.setVisibility(View.VISIBLE);
                totalRating.setVisibility(View.VISIBLE);
                productPrice.setTextColor(Color.parseColor("#000000"));
                cuttedPrice.setVisibility(View.VISIBLE);
                if (coupen != 0) {
                    coupenIcon.setVisibility(View.VISIBLE);
                    if (coupen == 1) {
                        freeCoupen.setText("Free (" + coupen + ") Coupen");
                    } else {
                        freeCoupen.setText("Free (" + coupen + ") Coupens");
                    }
                } else {
                    freeCoupen.setVisibility(View.INVISIBLE);
                    coupenIcon.setVisibility(View.INVISIBLE);
                }
                rating.setText(rat);
                totalRating.setText("(" + totalR + ") rating");
                productPrice.setText("Rs." + pprice + " /-");
                cuttedPrice.setText("Rs." + cutprice + " /-");

                if (payMethod) {
                    paymentMethod.setVisibility(View.VISIBLE);
                } else {
                    paymentMethod.setVisibility(View.INVISIBLE);
                }
                ratingparent.setVisibility(View.VISIBLE);

            }else{

                ratingparent.setVisibility(View.INVISIBLE);
                rating.setVisibility(View.INVISIBLE);
                totalRating.setVisibility(View.INVISIBLE);
                productPrice.setText("Out OF STOCK");
                productPrice.setTextColor(itemView.getContext().getResources().getColor(R.color.colorPrimary));
                cuttedPrice.setVisibility(View.INVISIBLE);
                paymentMethod.setVisibility(View.INVISIBLE);

            }
            if (whislist) {
                deleteButton.setVisibility(View.VISIBLE);
            } else {
                deleteButton.setVisibility(View.GONE);
            }


            //paymentMethod.setText(payMethod);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(
                            itemView.getContext(),R.style.AlertDialogCustom);
                    AlertDialog.Builder buildernew=new AlertDialog.Builder(itemView.getContext());
                    builder.setMessage("Are you sure to delete Wishlist! ") .setTitle("Warning")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //finish();
                                    if(!ProductdetailActivity.runningWishlistquery){
                                        ProductdetailActivity.runningWishlistquery=true;
                                        DBquery.removeFromWhishlist(index,itemView.getContext());
                                    }
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();

                                }
                            });
                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("Delete Wishlist");
                    alert.show();

                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailIntent = new Intent(itemView.getContext(), ProductdetailActivity.class);
                    productDetailIntent.putExtra("ProductName", title);
                    productDetailIntent.putExtra("productId", productId);
                    itemView.getContext().startActivity(productDetailIntent);
                }
            });

        }
    }
}
