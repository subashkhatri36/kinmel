package sabaijanakaari.blogspot.kinmel.Adaptor;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import sabaijanakaari.blogspot.kinmel.Model.horizental_product_Scroll_Model;
import sabaijanakaari.blogspot.kinmel.ProductdetailActivity;
import sabaijanakaari.blogspot.kinmel.R;

public class GridProductlayoutAdaptor extends BaseAdapter {

    List<horizental_product_Scroll_Model> grid_product_scroll_modelList;

    public GridProductlayoutAdaptor(List<horizental_product_Scroll_Model> grid_product_scroll_modelList) {
        this.grid_product_scroll_modelList = grid_product_scroll_modelList;
    }

    @Override
    public int getCount() {
        return grid_product_scroll_modelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view;
        if(convertView==null){

            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.horizental_scroll_items_layout,null);
            view.setElevation(0);
            view.setBackgroundColor(Color.parseColor("#ffffff"));
            ImageView productImage=(ImageView) view.findViewById(R.id.hs_product_image);
            final TextView productTitle=(TextView) view.findViewById(R.id.hs_product_title);
            TextView productDescription=(TextView)view.findViewById(R.id.hs_product_description);
            TextView productPrice=(TextView)view.findViewById(R.id.hs_product_pricing);

            //productImage.setImageResource(girdModel.get(position).getProductImage());
            Glide.with(view.getContext()).load(grid_product_scroll_modelList.get(position).getProductImage()).apply(new RequestOptions().placeholder(R.drawable.placeholderimge)).into(productImage);
            final String pTitle=grid_product_scroll_modelList.get(position).getProductTitle();
            productTitle.setText(grid_product_scroll_modelList.get(position).getProductTitle());

            productDescription.setText(grid_product_scroll_modelList.get(position).getProductDescription());
            productPrice.setText("Rs. "+grid_product_scroll_modelList.get(position).getProductPrice()+" /-");

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent prodcutdetailIntent=new Intent(parent.getContext(), ProductdetailActivity.class);
                    prodcutdetailIntent.putExtra("ProductName",pTitle);
                    prodcutdetailIntent.putExtra("ProductId",grid_product_scroll_modelList.get(position).getProductId());
                    parent.getContext().startActivity(prodcutdetailIntent);
                }
            });

        }else {
            view=convertView;
        }
        return view;
    }
}
