package sabaijanakaari.blogspot.kinmel.Adaptor;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import sabaijanakaari.blogspot.kinmel.Model.horizental_product_Scroll_Model;
import sabaijanakaari.blogspot.kinmel.ProductdetailActivity;
import sabaijanakaari.blogspot.kinmel.R;

public class horizental_product_Scroll_Adaptor extends RecyclerView.Adapter<horizental_product_Scroll_Adaptor.viewHolder> {

    private List<horizental_product_Scroll_Model> horizental_product_scroll_models;

    public horizental_product_Scroll_Adaptor(List<horizental_product_Scroll_Model> horizental_models) {
        this.horizental_product_scroll_models = horizental_models;
    }

    @Override
    public horizental_product_Scroll_Adaptor.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizental_scroll_items_layout, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(horizental_product_Scroll_Adaptor.viewHolder holder, int position) {
        String resources = horizental_product_scroll_models.get(position).getProductImage();
        String title = horizental_product_scroll_models.get(position).getProductTitle();
        String price = horizental_product_scroll_models.get(position).getProductPrice();
        String descritpion = horizental_product_scroll_models.get(position).getProductDescription();
        String productId = horizental_product_scroll_models.get(position).getProductId();
        holder.setProductDetail(resources, title, descritpion, price, productId);

    }

    @Override
    public int getItemCount() {
        if (horizental_product_scroll_models.size() > 8) {
            return 8;
        } else
            return horizental_product_scroll_models.size();

    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productTitle;
        private TextView productDescription;
        private TextView productPrice;

        public viewHolder(final View itemView) {
            super(itemView);
            productImage = (ImageView) itemView.findViewById(R.id.hs_product_image);
            productTitle = (TextView) itemView.findViewById(R.id.hs_product_title);
            productDescription = (TextView) itemView.findViewById(R.id.hs_product_description);
            productPrice = (TextView) itemView.findViewById(R.id.hs_product_pricing);
        }

        private void setProductDetail(String res, String title, String description, String price, final String productId) {
            Glide.with(itemView.getContext()).load(res).apply(new RequestOptions().placeholder(R.drawable.placeholderimge)).into(productImage);
            productTitle.setText(title);
            productDescription.setText(description);
            productPrice.setText("Rs." + price + "/-");
            if (!title.equals("")) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent productdetailIntent = new Intent(itemView.getContext(), ProductdetailActivity.class);
                        productdetailIntent.putExtra("ProductName", productTitle.getText().toString());
                        productdetailIntent.putExtra("productId", productId);
                        itemView.getContext().startActivity(productdetailIntent);
                    }
                });
            }

        }
    }
}