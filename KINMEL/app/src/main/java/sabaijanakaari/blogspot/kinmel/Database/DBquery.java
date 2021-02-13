package sabaijanakaari.blogspot.kinmel.Database;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.SystemClock;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.internal.$Gson$Preconditions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sabaijanakaari.blogspot.kinmel.Adaptor.CategoryMenuAdaptor;
import sabaijanakaari.blogspot.kinmel.Adaptor.HomePageAdaptor;
import sabaijanakaari.blogspot.kinmel.AddaddressActivity;
import sabaijanakaari.blogspot.kinmel.DeliveryActivity;
import sabaijanakaari.blogspot.kinmel.HomeFragment;
import sabaijanakaari.blogspot.kinmel.Model.Cart_items_Model;
import sabaijanakaari.blogspot.kinmel.Model.CategoryMenuModel;
import sabaijanakaari.blogspot.kinmel.Model.HomepageModel;
import sabaijanakaari.blogspot.kinmel.Model.Whistlist_Model;
import sabaijanakaari.blogspot.kinmel.Model.addresses_Model;
import sabaijanakaari.blogspot.kinmel.Model.banner_Slider_Model;
import sabaijanakaari.blogspot.kinmel.Model.horizental_product_Scroll_Model;
import sabaijanakaari.blogspot.kinmel.MycartFragment;
import sabaijanakaari.blogspot.kinmel.MywhishlistFragment;
import sabaijanakaari.blogspot.kinmel.ProductdetailActivity;
import sabaijanakaari.blogspot.kinmel.R;
import sabaijanakaari.blogspot.kinmel.SplashActivity;

public class DBquery {
    public  static int selectedaddress=-1;


    public static FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    public static List<CategoryMenuModel> categoryMenuModels=new ArrayList<>();
    public static List<String> whishList=new ArrayList<>();
    public static List<Whistlist_Model> whistlist_modelList=new ArrayList<>();
    public static List<List<HomepageModel>> lists=new ArrayList<>();
    public static List<String> loadedCategoriesName=new ArrayList<>();
    public static List<String> myratedIds=new ArrayList<>();
    public static List<Long> myRating=new ArrayList<>();
    public static List<String> cartList=new ArrayList<>();
    public static List<Cart_items_Model> cartlist_modelList=new ArrayList<>();
    public static List<addresses_Model> addresses_modelList=new ArrayList<>();


    public static void loadCategories(final RecyclerView cateogriesRecyclerview, final Context context){
        categoryMenuModels.clear();
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){

                            for(DocumentSnapshot documentSnaphot:task.getResult()){
                                if(documentSnaphot.exists()){
                                    categoryMenuModels.add(new CategoryMenuModel(documentSnaphot.get("Icon").toString(),documentSnaphot.get("category_name").toString()));
                                }

                            }
                            CategoryMenuAdaptor categoryMenuAdaptor=new CategoryMenuAdaptor(categoryMenuModels);
                            cateogriesRecyclerview.setAdapter(categoryMenuAdaptor);
                            categoryMenuAdaptor.notifyDataSetChanged();

                        } else {
                            Toast.makeText(context,""+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //Home page Load Fragment Data
    //final HomePageAdaptor adaptor
    public static void loadFragmentData(final RecyclerView homepagerecyclerview, final Context context, final int index, String categoryName){

        firebaseFirestore.collection("CATEGORIES").document(categoryName.toUpperCase())
                .collection("TOP_DEALS").orderBy("index", Query.Direction.ASCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){

                            for(DocumentSnapshot documentSnaphot:task.getResult()){
                                if(documentSnaphot.exists()){
                                    if((long)documentSnaphot.get("view_type")==0){
                                        List<banner_Slider_Model> sliderModelList=new ArrayList<>();
                                        long no_of_banner=(long)documentSnaphot.get("no_of_banner");
                                        for(long x=1;x<no_of_banner+1;x++){
                                            sliderModelList.add(new banner_Slider_Model(documentSnaphot.get("banner_"+x).toString(),
                                                    documentSnaphot.get("banner_"+x+"_background").toString()));
                                        }
                                        lists.get(index).add(new HomepageModel(0,sliderModelList));
                                    }else if((long)documentSnaphot.get("view_type")==1){
                                        lists.get(index).add(new HomepageModel(1,documentSnaphot.get("strip_ad_banner").toString(),documentSnaphot.get("background").toString()));
                                    }else if((long)documentSnaphot.get("view_type")==2){

                                        //horizental layout
                                        List<horizental_product_Scroll_Model> list=new ArrayList<>();
                                        List<Whistlist_Model> viewAllproductlist=new ArrayList<>();

                                        long no_of_product=(long)documentSnaphot.get("no_of_product");
                                        String layout_title=documentSnaphot.get("layout_title").toString();
                                        for(long x=1;x<no_of_product+1;x++){
                                            //String productId,String productImage, String productTitle, String productDescription, String productPrice) {

                                            list.add(new horizental_product_Scroll_Model(documentSnaphot.get("product_id_"+x).toString(),
                                                    documentSnaphot.get("product_image_"+x).toString(),
                                                    documentSnaphot.get("product_title_"+x).toString(),
                                                    documentSnaphot.get("product_subtitle_"+x).toString(),
                                                    documentSnaphot.get("product_price_"+x).toString()));

                                            viewAllproductlist.add(new Whistlist_Model(documentSnaphot.get("product_image_"+x).toString(),
                                                    documentSnaphot.get("product_full_title_"+x).toString(),
                                                    (long)documentSnaphot.get("free_coupens_"+x),
                                                    documentSnaphot.get("average_rating_"+x).toString(),
                                                    documentSnaphot.get("total_rating_"+x).toString(),
                                                    documentSnaphot.get("product_price_"+x).toString(),
                                                    documentSnaphot.get("cutted_price_"+x).toString(),
                                                    (boolean)documentSnaphot.get("cod_"+x),
                                                    documentSnaphot.get("product_id_"+x).toString(),
                                                    (boolean)documentSnaphot.get(ConstantVariable.Product_in_stock_+x)
                                            ));
                                        }
                                        lists.get(index).add(new HomepageModel(2,layout_title,documentSnaphot.get("layout_background").toString(),list,viewAllproductlist));
                                        //horizental layout
                                    }else if((long)documentSnaphot.get("view_type")==3){
                                        //Grid Layout
                                        List<horizental_product_Scroll_Model> gridlayoutlist=new ArrayList<>();
                                        long no_of_product=(long)documentSnaphot.get("no_of_product");
                                        String layout_title=documentSnaphot.get("layout_title").toString();
                                        for(long x=1;x<no_of_product+1;x++){
                                            //String productId,String productImage, String productTitle, String productDescription, String productPrice) {

                                            gridlayoutlist.add(new horizental_product_Scroll_Model(documentSnaphot.get("product_id_"+x).toString(),
                                                    documentSnaphot.get("product_image_"+x).toString(),
                                                    documentSnaphot.get("product_title_"+x).toString(),
                                                    documentSnaphot.get("product_subtitle_"+x).toString(),
                                                    documentSnaphot.get("product_price_"+x).toString()));
                                        }
                                        lists.get(index).add(new HomepageModel(3,layout_title,documentSnaphot.get("layout_background").toString(),gridlayoutlist));
                                        //Grid layout
                                    }

                                }

                            }
                            HomePageAdaptor adaptor=new HomePageAdaptor(lists.get(index));
                            homepagerecyclerview.setAdapter(adaptor);
                            HomeFragment.refreshLayout.setRefreshing(false);
                            adaptor.notifyDataSetChanged();

                        } else {
                            Toast.makeText(context,""+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void loadWhishList(final Context context, final Dialog loadingDilaog, final boolean loadProductData) {

        whishList.clear();
        firebaseFirestore.collection(ConstantVariable.User).document(FirebaseAuth.getInstance().getUid()).collection(ConstantVariable.User_Data).document(ConstantVariable.Mywishlist)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){

                    if(task.getResult().exists()){
                        long val=task.getResult().getLong(ConstantVariable.Mywishlist_listsize);
                        if(val>0)
                            for(long i=0;i<val;i++){

                                if(DBquery.whishList.contains(ProductdetailActivity.productId)){
                                    if(ProductdetailActivity.addToWhistlistbutton!=null){
                                        ProductdetailActivity.addToWhistlistbutton.setSupportImageTintList(context.getResources().getColorStateList(R.color.colorPrimary));
                                    }
                                    ProductdetailActivity.ALREADY_ADDED_TO_WHISHLIST=true;

                                }else{
                                    ProductdetailActivity.ALREADY_ADDED_TO_WHISHLIST=false;
                                    if(ProductdetailActivity.addToWhistlistbutton!=null){
                                        ProductdetailActivity.addToWhistlistbutton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#aeaeae")));
                                    }
                                    loadingDilaog.dismiss();
                                }
                                whishList.add(task.getResult().get(ConstantVariable.Mywishlist_product_id+i).toString());
                                if(loadProductData){
                                    whistlist_modelList.clear();
                                    firebaseFirestore.collection(ConstantVariable.Product).document(task.getResult().get(ConstantVariable.Mywishlist_product_id+i).toString())
                                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task1) {
                                            if(task1.isSuccessful()){
                                                whistlist_modelList.add(new Whistlist_Model(task1.getResult().get(ConstantVariable.Mywishlist_productimage).toString(),
                                                        task1.getResult().get(ConstantVariable.Mywishlist_title).toString(),
                                                        (long)task1.getResult().get(ConstantVariable.Mywishlist_freecoupen),
                                                        task1.getResult().get(ConstantVariable.Mywishlist_avragerating).toString(),
                                                        task1.getResult().get(ConstantVariable.Mywishlist_totalrating).toString(),
                                                        task1.getResult().get(ConstantVariable.Mywishlist_productprice).toString(),
                                                        task1.getResult().get(ConstantVariable.Mywishlist_cuttedprice).toString(),
                                                        (boolean)task1.getResult().get(ConstantVariable.Mywishlist_cod),
                                                        task1.getResult().getId(),
                                                        (boolean)task1.getResult().get(ConstantVariable.Product_in_stock)));

                                                MywhishlistFragment.whishlist_adaptor.notifyDataSetChanged();
                                            }else{
                                                Toast.makeText(context, task1.getException().getMessage()+"", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }

                            }
                        }
                    loadingDilaog.dismiss();
                    }else{
                    loadingDilaog.dismiss();
                        Toast.makeText(context, task.getException().getMessage()+"", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }

    public static void removeFromWhishlist(final int index, final Context productdetailActivity) {
        final String removedProductId=whishList.get(index);
        whishList.remove(index);
        Map<String,Object> updateWhislist=new HashMap<>();
        for(int x=0;x<whishList.size();x++){
            updateWhislist.put(ConstantVariable.Mywishlist_product_id+x,whishList.get(x));
        }
        updateWhislist.put(ConstantVariable.Mywishlist_listsize,(long)whishList.size());

        firebaseFirestore.collection(ConstantVariable.User).document(FirebaseAuth.getInstance().getUid())
                .collection(ConstantVariable.User_Data).document(ConstantVariable.Mywishlist)
                .set(updateWhislist).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    if(whistlist_modelList.size()!=0){
                        whistlist_modelList.remove(index);
                        MywhishlistFragment.whishlist_adaptor.notifyDataSetChanged();;
                    }
                    ProductdetailActivity.runningWishlistquery=false;
                    ProductdetailActivity.ALREADY_ADDED_TO_WHISHLIST=false;
                    Toast.makeText(productdetailActivity, "Remove Sucessfully", Toast.LENGTH_SHORT).show();

                }else{
                    if(ProductdetailActivity.addToWhistlistbutton!=null) {
                        ProductdetailActivity.addToWhistlistbutton.setSupportImageTintList(productdetailActivity.getResources().getColorStateList(R.color.colorPrimary));
                        ProductdetailActivity.runningWishlistquery=false;
                    }
                    whishList.add(index,removedProductId);
                    Toast.makeText(productdetailActivity, task.getException().getMessage()+"", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public static void loadRatingList(final Context context){

        if(!ProductdetailActivity.running_rating_query) {
            ProductdetailActivity.running_rating_query=true;
            myRating.clear();
            myratedIds.clear();
            firebaseFirestore.collection(ConstantVariable.User).document(FirebaseAuth.getInstance().getUid()).collection(ConstantVariable.User_Data).document(ConstantVariable.MYRating)
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            for (long x = 0; x < (long) task.getResult().get(ConstantVariable.MYRating_listsize); x++) {
                                myratedIds.add(task.getResult().get(ConstantVariable.MYRating_product_id + x).toString());
                                myRating.add((long) task.getResult().get(ConstantVariable.MYRating_rating + x));
                                if (task.getResult().get(ConstantVariable.MYRating_product_id + x).toString().equals(ProductdetailActivity.productId)) {
                                    ProductdetailActivity.initalrating = Integer.parseInt(String.valueOf(task.getResult().getLong(ConstantVariable.MYRating_rating + x))) - 1;
                                    if(ProductdetailActivity.ratenow_container != null) {
                                        ProductdetailActivity.setRating(ProductdetailActivity.initalrating);
                                    }
                                }
                            }
                        }
                        ProductdetailActivity.running_rating_query=false;

                    } else {
                        Toast.makeText(context, task.getException().getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

   public static void loadCartList(final Context contex,final Dialog loadingDialog,final boolean loadProductData,final TextView badgecount,final TextView cartTotalAmount){
       cartList.clear();
       firebaseFirestore.collection(ConstantVariable.User).document(FirebaseAuth.getInstance().getUid()).collection(ConstantVariable.User_Data).document(ConstantVariable.MYCart)
               .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull final Task<DocumentSnapshot> task) {
               if(task.isSuccessful()){
                   if(task.getResult().exists()){
                       long val=task.getResult().getLong(ConstantVariable.MyCart_listsize);
                       if(val>0)
                           for(long i=0;i<val;i++){
                               cartList.add(task.getResult().get(ConstantVariable.MYCart_product_id+i).toString());

                               if(DBquery.cartList.contains(ProductdetailActivity.productId)){
                                   ProductdetailActivity.ALREADY_ADDED_TO_CARTLIST=true;
                               }else{
                                   ProductdetailActivity.ALREADY_ADDED_TO_CARTLIST=false;
                               }

                               if(loadProductData){
                                   cartlist_modelList.clear();
                                   firebaseFirestore.collection(ConstantVariable.Product).document(task.getResult().get(ConstantVariable.MYCart_product_id+i).toString())
                                           .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                       @Override
                                       public void onComplete(@NonNull Task<DocumentSnapshot> task1) {
                                           if(task1.isSuccessful()){
                                               int index=0;
                                               if(cartList.size()>=2){
                                                   index=cartList.size()-2;
                                               }
                                               //int type,String product_id, String productPrice, String productCuttedprice, String productTitle,
                                               // Long freeCoupen, String productImage, Long productQuanity, Long offersApplied, Long coupenApplied) {
                                               cartlist_modelList.add(index,new Cart_items_Model(Cart_items_Model.CART_ITEM,
                                                       task1.getResult().getId(),
                                                       task1.getResult().get(ConstantVariable.MYCart_productprice).toString(),
                                                       task1.getResult().get(ConstantVariable.MYCart_cuttedprice).toString(),
                                                       task1.getResult().get(ConstantVariable.MYCart_title).toString(),
                                                       (long)task1.getResult().get(ConstantVariable.MYCart_freecoupen),
                                                       task1.getResult().get(ConstantVariable.MYCart_productimage).toString(),
                                                       (long)1,
                                                       (long)0,
                                                       (long)0,
                                               (boolean)task1.getResult().get(ConstantVariable.Product_in_stock),
                                                       (long)task1.getResult().get(ConstantVariable.Product_max_quantity),
                                                       (long)task1.getResult().get(ConstantVariable.Product_min_quantity)));

                                               if(cartList.size()==1){
                                                   cartlist_modelList.add(new Cart_items_Model(Cart_items_Model.TOTAL_AMOUNT));
                                                   LinearLayout parent=(LinearLayout) cartTotalAmount.getParent().getParent();
                                                   parent.setVisibility(View.VISIBLE);
                                               }
                                               if(cartList.size()==0){
                                                   cartlist_modelList.clear();
                                               }

                                               MycartFragment.cartAdaptor.notifyDataSetChanged();
                                           }else{
                                               Toast.makeText(contex, task1.getException().getMessage()+"", Toast.LENGTH_SHORT).show();
                                           }
                                       }
                                   });
                               }
                               if(cartList.size()!=0){
                                   badgecount.setVisibility(View.VISIBLE);
                               }else{
                                   badgecount.setVisibility(View.INVISIBLE);
                               }

                               if(DBquery.cartList.size()<99){
                                   badgecount.setText(String.valueOf(DBquery.cartList.size()));
                               }else{
                                   badgecount.setText("99");
                               }

                           }

                   }
                   loadingDialog.dismiss();
               }else{
                   loadingDialog.dismiss();
                   Toast.makeText(contex, task.getException().getMessage()+"", Toast.LENGTH_SHORT).show();
               }
           }
       });

   }

    public static void removeFromCart(final int index, final Context productdetailActivity, final TextView cartTotalAmt) {
        final String removedProductId=cartList.get(index);
        cartList.remove(index);
        Map<String,Object> updatecartlist=new HashMap<>();
        for(int x=0;x<cartList.size();x++){
            updatecartlist.put(ConstantVariable.MYCart_product_id+x,cartList.get(x));
        }
        updatecartlist.put(ConstantVariable.MyCart_listsize,(long)cartList.size());

        firebaseFirestore.collection(ConstantVariable.User).document(FirebaseAuth.getInstance().getUid())
                .collection(ConstantVariable.User_Data).document(ConstantVariable.MYCart)
                .set(updatecartlist).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    if(cartlist_modelList.size()!=0){
                        cartlist_modelList.remove(index);
                        MycartFragment.cartAdaptor.notifyDataSetChanged();;
                    }
                    if(cartList.size()==0){
                        LinearLayout parent=(LinearLayout) cartTotalAmt.getParent().getParent();
                        parent.setVisibility(View.INVISIBLE);
                        cartlist_modelList.clear();
                    }
                    Toast.makeText(productdetailActivity, "Remove Sucessfully", Toast.LENGTH_SHORT).show();

                }else{
                    cartList.add(index,removedProductId);
                    Toast.makeText(productdetailActivity, task.getException().getMessage()+"", Toast.LENGTH_SHORT).show();
                }
                ProductdetailActivity.running_cart_query=false;

            }
        });

    }

    public static void loadAddresses(final Context context, final Dialog loadingDilaog){
        addresses_modelList.clear();
        firebaseFirestore.collection(ConstantVariable.User).document(FirebaseAuth.getInstance().getUid()).collection(ConstantVariable.User_Data)
                .document(ConstantVariable.MYAddress).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult().exists()) {
                                Intent deliveryIntent = null;
                                if ((long) task.getResult().get(ConstantVariable.MYAddress_listsize) == 0) {

                                    deliveryIntent = new Intent(context, AddaddressActivity.class);
                                    deliveryIntent.putExtra("INTENT","deliveryintent");
                                } else {
                                    for(long x=1;x<(long) task.getResult().get(ConstantVariable.MYAddress_listsize)+1;x++){
                                            if((boolean)task.getResult().get(ConstantVariable.MYAddress_selected+x)){
                                                selectedaddress=Integer.parseInt(String.valueOf(x))-1;
                                            }

                                        DBquery.addresses_modelList.add(new addresses_Model(task.getResult().get(ConstantVariable.MYAddress_fullname+x).toString(),
                                                task.getResult().get(ConstantVariable.MYAddress_addresses+x).toString(),
                                                task.getResult().get(ConstantVariable.MYAddress_pincode+x).toString(),
                                                (boolean)task.getResult().get(ConstantVariable.MYAddress_selected+x),
                                                task.getResult().get(ConstantVariable.MYAddress_moobileno+x).toString()));

                                    }
                                    deliveryIntent = new Intent(context, DeliveryActivity.class);

                                }
                                context.startActivity(deliveryIntent);
                                loadingDilaog.dismiss();
                            }

                        }else{
                            Toast.makeText(context, task.getException().getMessage()+"", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public static void ClearData(){
        addresses_modelList.clear();
         categoryMenuModels.clear();
        whishList.clear();
         whistlist_modelList.clear();
        lists.clear();
        loadedCategoriesName.clear();
         myratedIds.clear();
         myRating.clear();
         cartList.clear();
         cartlist_modelList.clear();

    }

    public static void DeliveryFreeMaxdata() {
        SplashActivity.maxsetDeliveryChargeFree =1000;
        SplashActivity.setDeliveryCharge=110;

    }
}
