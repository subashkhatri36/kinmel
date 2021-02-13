package sabaijanakaari.blogspot.kinmel.Adaptor;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import sabaijanakaari.blogspot.kinmel.Database.DBquery;
import sabaijanakaari.blogspot.kinmel.DeliveryActivity;
import sabaijanakaari.blogspot.kinmel.MainActivity;
import sabaijanakaari.blogspot.kinmel.Model.Cart_items_Model;
import sabaijanakaari.blogspot.kinmel.ProductdetailActivity;
import sabaijanakaari.blogspot.kinmel.R;
import sabaijanakaari.blogspot.kinmel.SplashActivity;

public class Cart_Adaptor extends RecyclerView.Adapter {
    int lastposition = -1;
    private TextView cartTotalAmount;
    boolean showdeletebtn;
    List<Cart_items_Model> cart_items_modelList;

    public Cart_Adaptor(List<Cart_items_Model> cart_items_modelList, TextView carttotalAmount, boolean showdeletebtn) {
        this.cart_items_modelList = cart_items_modelList;
        this.cartTotalAmount = carttotalAmount;
        this.showdeletebtn = showdeletebtn;
    }

    @Override
    public int getItemViewType(int position) {
        switch (cart_items_modelList.get(position).getType()) {
            case 0:
                return Cart_items_Model.CART_ITEM;
            case 1:
                return Cart_items_Model.TOTAL_AMOUNT;
            default:
                return -1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case Cart_items_Model.CART_ITEM:
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
                return new cartItemsViewHolder(v);
            case Cart_items_Model.TOTAL_AMOUNT:
                View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_total_amount_layout, parent, false);
                return new cartTotalAmountViewHolder(v1);
            default:
                return null;

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (cart_items_modelList.get(position).getType()) {
            case Cart_items_Model.CART_ITEM:
                String productId = cart_items_modelList.get(position).getProduct_id();
                String reso = cart_items_modelList.get(position).getProductImage();
                String title = cart_items_modelList.get(position).getProductTitle();
                long freecoupen = cart_items_modelList.get(position).getFreeCoupen();
                String productprice = cart_items_modelList.get(position).getProductPrice();
                String cuttedprice = cart_items_modelList.get(position).getProductCuttedprice();
                long offerApplied = cart_items_modelList.get(position).getOffersApplied();
                boolean instock = cart_items_modelList.get(position).isInstock();
                long minqty = cart_items_modelList.get(position).getMinquanity();
                long maxqty = cart_items_modelList.get(position).getMaxquanity();
                long pqty = cart_items_modelList.get(position).getProductQuanity();

                ((cartItemsViewHolder) holder).setItemsDetails(instock, position, productId, reso, title, freecoupen, productprice, cuttedprice, offerApplied, maxqty, minqty, pqty);
                break;

            case Cart_items_Model.TOTAL_AMOUNT:
                String deliver_price = "";
                String save_price = "";
                int saveAmount = 0;
                int totalamt = 0;
                int totalItems = 0;
                int totalItemsprice = 0;


                for (int x = 0; x < DBquery.cartlist_modelList.size(); x++) {
                    if (cart_items_modelList.get(x).getType() == Cart_items_Model.CART_ITEM && cart_items_modelList.get(x).isInstock()) {

                        totalItems++;
                        totalItemsprice = totalItemsprice + Integer.parseInt(cart_items_modelList.get(x).getProductPrice());
                    }
                    //max delivery price should
                    if (totalItemsprice > SplashActivity.maxsetDeliveryChargeFree) {
                        deliver_price = "Free";
                        totalamt = totalItemsprice;
                        // save_price=save_price+deliveryprice;
                    } else {
                        deliver_price = String.valueOf(SplashActivity.setDeliveryCharge);
                        totalamt = totalamt + SplashActivity.setDeliveryCharge;
                    }

                }
                ((cartTotalAmountViewHolder) holder).setTotalAmount(cart_items_modelList.get(position).isInstock(), totalItems, totalItemsprice, deliver_price, totalamt, save_price);

                break;
            default:
                return;
        }
        if (lastposition < position) {
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fading);
            holder.itemView.setAnimation(animation);
            lastposition = position;
        }

    }

    @Override
    public int getItemCount() {
        return cart_items_modelList.size();
    }

    class cartItemsViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImag;
        private ImageView freecoupenImage;
        private TextView productTitle;
        private TextView productCOupen;
        private TextView productPrice;
        private TextView productCutprice;
        private TextView offerSupply;
        private TextView coupenSupply;
        private TextView productQuantity;
        LinearLayout removeCartBtn;
        LinearLayout coupenRedeemptionLayout;
        Button coupenredemptionbtn;


        public cartItemsViewHolder(View itemView) {
            super(itemView);
            productImag = itemView.findViewById(R.id.product_image);
            freecoupenImage = itemView.findViewById(R.id.free_coupen_icon);
            productTitle = itemView.findViewById(R.id.product_title);
            productCOupen = itemView.findViewById(R.id.tv_free_coupen);
            productPrice = itemView.findViewById(R.id.product_price);
            productCutprice = itemView.findViewById(R.id.product_cutted_price);
            offerSupply = itemView.findViewById(R.id.offered_applied);
            coupenSupply = itemView.findViewById(R.id.coupen_applied);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            removeCartBtn = itemView.findViewById(R.id.remove_item_button);
            coupenRedeemptionLayout = itemView.findViewById(R.id.coupen_redemption_layout);
            coupenredemptionbtn = itemView.findViewById(R.id.coupen_redemtption_button);
        }
        //((cartItemsViewHolder)holder).setItemsDetails(reso,title,freecoupen,productprice,cuttedprice,offerApplied);

        private void setItemsDetails(boolean instock, final int position, String productId, String productImage, String title,
                                     Long freecoupen, String productprice, String cuttedprice, Long offersupply, final Long maxqty,
                                     final Long minqty, Long productqty) {

            Glide.with(itemView.getContext()).load(productImage).apply(new RequestOptions().placeholder(R.drawable.loading)).into(productImag);

            productTitle.setText(title);
            productQuantity.setText("Qty: " + String.valueOf(productqty));


            if (instock) {
                productPrice.setText("Rs." + String.valueOf(productprice) + "/-");
                productPrice.setTextColor(Color.parseColor("#000000"));
                productCutprice.setText("Rs." + String.valueOf(cuttedprice) + "/-");
                coupenRedeemptionLayout.setVisibility(View.VISIBLE);
                coupenredemptionbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });
                productQuantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog qtydialog = new Dialog(itemView.getContext(), R.style.AlertDialogCustom);
                        qtydialog.setContentView(R.layout.quantity_dialog);
                        qtydialog.setCancelable(false);
                        qtydialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        final EditText qtyNumber = qtydialog.findViewById(R.id.quantity_number);
                        qtyNumber.setHint("Max " + maxqty);
                        qtyNumber.setMaxLines(5);
                        Button cancelButton = qtydialog.findViewById(R.id.cancel_button);
                        Button OkButton = qtydialog.findViewById(R.id.ok_button);
                        qtydialog.show();
                        cancelButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                qtydialog.dismiss();
                            }
                        });

                        //showing quanity btn
                        OkButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!TextUtils.isEmpty(qtyNumber.getText()) && Integer.parseInt(qtyNumber.getText().toString()) > 0) {
                                    if (Long.valueOf(qtyNumber.getText().toString()) <= maxqty && Long.valueOf(qtyNumber.getText().toString()) >= minqty) {
                                        if(itemView.getContext() instanceof MainActivity){
                                            //checking context
                                            DBquery.cartlist_modelList.get(position).setProductQuanity(Long.valueOf(qtyNumber.getText().toString()));
                                        }else{
                                            if(DeliveryActivity.fromCart){

                                                DBquery.cartlist_modelList.get(position).setProductQuanity(Long.valueOf(qtyNumber.getText().toString()));
                                            }else{
                                                DeliveryActivity.cartItemsModelList.get(position).setProductQuanity(Long.valueOf(qtyNumber.getText().toString()));
                                            }
                                        }

                                        productQuantity.setText("Qty: " + qtyNumber.getText().toString());
                                    } else {
                                        Toast.makeText(itemView.getContext(), "Minimum quanity must be " + minqty + " and maximum qantity " + maxqty, Toast.LENGTH_SHORT).show();
                                        productQuantity.setText("Qty: " + String.valueOf(minqty));
                                        qtydialog.dismiss();
                                    }
                                } else {
                                    productQuantity.setText("Qty: " + minqty);
                                    Toast.makeText(itemView.getContext(), "Minimum quanity must be " + minqty + " and maximum qantity " + maxqty, Toast.LENGTH_SHORT).show();
                                    qtydialog.dismiss();
                                }
                            }
                        });

                    }
                });
                productQuantity.setVisibility(View.VISIBLE);

                if (freecoupen > 0) {
                    freecoupenImage.setVisibility(View.VISIBLE);
                    productCOupen.setVisibility(View.VISIBLE);
                    if (freecoupen == 1) {
                        productCOupen.setText(" Free (" + freecoupen + ") Coupen");
                    } else {
                        productCOupen.setText(" Free (" + freecoupen + ") Coupens");
                    }
                    //freecoupenImage.setImageResource(fcI);

                } else {
                    freecoupenImage.setVisibility(View.INVISIBLE);
                    productCOupen.setVisibility(View.INVISIBLE);
                }

                if (offersupply > 0) {
                    offerSupply.setVisibility(View.VISIBLE);
                    offerSupply.setText(offersupply + " Offers Applied");
                } else {
                    offerSupply.setVisibility(View.INVISIBLE);
                    offerSupply.setVisibility(View.INVISIBLE);
                }
                productCOupen.setVisibility(View.VISIBLE);
                coupenSupply.setVisibility(View.VISIBLE);
            } else {
                productPrice.setText("Out of Stock");
                productPrice.setTextColor(itemView.getContext().getResources().getColor(R.color.colorPrimary));
                productCutprice.setText("");
                coupenRedeemptionLayout.setVisibility(View.GONE);
                productQuantity.setText("Qty : 0");
                productQuantity.setVisibility(View.INVISIBLE);
                productCOupen.setVisibility(View.INVISIBLE);
                offerSupply.setVisibility(View.GONE);
                freecoupenImage.setVisibility(View.INVISIBLE);
                coupenSupply.setVisibility(View.GONE);
            }


            if (showdeletebtn) {
                removeCartBtn.setVisibility(View.VISIBLE);
            } else {
                removeCartBtn.setVisibility(View.GONE);
            }

            removeCartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            itemView.getContext(), R.style.AlertDialogCustom);
                    AlertDialog.Builder buildernew = new AlertDialog.Builder(itemView.getContext());
                    builder.setMessage("Are you sure to delete Cart Iteme! ").setTitle("Warning")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    if (!ProductdetailActivity.running_cart_query) {

                                        Dialog loadingDilaog = new Dialog(itemView.getContext());
                                        loadingDilaog.setContentView(R.layout.loading_progress_dialog);
                                        loadingDilaog.setCancelable(false);
                                        loadingDilaog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        loadingDilaog.getWindow().setBackgroundDrawable(itemView.getContext().getDrawable(R.drawable.slider_banner_background));
                                        loadingDilaog.show();
                                        ProductdetailActivity.running_cart_query = true;
                                        DBquery.removeFromCart(position, itemView.getContext(), cartTotalAmount);
                                        loadingDilaog.dismiss();
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
                    alert.setTitle("Delete Cart Items");
                    alert.show();


                }
            });
        }
    }

    class cartTotalAmountViewHolder extends RecyclerView.ViewHolder {

        private TextView totalItems;
        private TextView totalItems_price;
        private TextView deliveryamount;
        private TextView totalAmount;
        private TextView saveAmount;

        public cartTotalAmountViewHolder(View itemView) {
            super(itemView);
            totalItems = itemView.findViewById(R.id.total_items);
            totalItems_price = itemView.findViewById(R.id.total_items_price);
            deliveryamount = itemView.findViewById(R.id.delivery_charge_price);
            totalAmount = itemView.findViewById(R.id.total_price);
            saveAmount = itemView.findViewById(R.id.save_amount);

        }

        public void setTotalAmount(boolean stock, int totalitems, int totalitemsprice, String deliveramt, int totalamt, String saveamt) {
            totalItems.setText("Price (" + totalitems + ") Items");
            totalItems_price.setText("Rs. " + totalitemsprice + "/-");
            if (deliveramt.toLowerCase().equals("free")) {
                deliveryamount.setText("FREE");
            } else {
                deliveryamount.setText("Rs. " + deliveramt + "/-");
            }
            totalAmount.setText("Rs. " + totalamt + "/-");
            cartTotalAmount.setText("Rs. " + totalamt + "/-");
            saveAmount.setText("You Saved Rs. " + saveamt + "/- on this Order.");

            LinearLayout parent = (LinearLayout) cartTotalAmount.getParent().getParent();
            if (totalitemsprice == 0) {
                DBquery.cartlist_modelList.remove(DBquery.cartlist_modelList.size() - 1);
                parent.setVisibility(View.GONE);
            } else {
                parent.setVisibility(View.VISIBLE);
            }

        }
    }
}
