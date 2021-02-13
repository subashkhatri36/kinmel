package sabaijanakaari.blogspot.kinmel.Adaptor;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sabaijanakaari.blogspot.kinmel.Model.MyOrder_Items_Model;
import sabaijanakaari.blogspot.kinmel.OrderdetailActivity;
import sabaijanakaari.blogspot.kinmel.R;

public class myOrder_Adaptor extends RecyclerView.Adapter<myOrder_Adaptor.viewHolder> {

    List<MyOrder_Items_Model> myOrder_items_modelList;

    public myOrder_Adaptor(List<MyOrder_Items_Model> myOrder_items_modelList) {
        this.myOrder_items_modelList = myOrder_items_modelList;
    }

    @Override
    public myOrder_Adaptor.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.myorder_item_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(myOrder_Adaptor.viewHolder holder, int position) {
        int resource=myOrder_items_modelList.get(position).getProductImage();
        int rating=myOrder_items_modelList.get(position).getRating();
        String title=myOrder_items_modelList.get(position).getProductTitle();
        String delievery=myOrder_items_modelList.get(position).getDeliveryStatus();
        holder.setData(resource,title,delievery,rating);
    }


    @Override
    public int getItemCount() {
        return myOrder_items_modelList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage,deliveryImage;
        private TextView productTitle,DeliveryStatus;
        private LinearLayout ratenow_container;

        public viewHolder(final View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.myorder_product_image);
            deliveryImage=itemView.findViewById(R.id.order_indicator);
            productTitle=itemView.findViewById(R.id.myorder_product_title);
            DeliveryStatus=itemView.findViewById(R.id.order_deliver_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mydetail=new Intent(itemView.getContext(), OrderdetailActivity.class);
                    itemView.getContext().startActivity(mydetail);
                }
            });
        }
        private void setData(int resourse,String title,String deliverDate,int rating){
            productImage.setImageResource(resourse);
            productTitle.setText(title);
            if(deliverDate.equals("Cancelled")){
                deliveryImage.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.colorPrimary)));
                DeliveryStatus.setText(deliverDate);
            }else{
                deliveryImage.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.successgree)));
                DeliveryStatus.setText(deliverDate);
            }

            //ratenow_layout

            ratenow_container=(LinearLayout)itemView.findViewById(R.id.rate_now_container);

            setRating(rating);
            for(int x=0;x<ratenow_container.getChildCount();x++){
                final int startposition=x;
                ratenow_container.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setRating(startposition);
                    }
                });
            }

            //ratenow _layout

        }
        private void setRating(int startposition) {
            for(int x=0;x<ratenow_container.getChildCount();x++){
                ImageView startButton=(ImageView)ratenow_container.getChildAt(x);
                startButton.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
                if(x<=startposition){
                    startButton.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
                }
            }
        }
    }
}
