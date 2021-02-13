package sabaijanakaari.blogspot.kinmel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.accessibilityservice.GestureDescription;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.khalti.checkout.helper.Config;
import com.khalti.checkout.helper.OnCheckOutListener;
import com.khalti.checkout.helper.PaymentPreference;
import com.khalti.utils.Constant;
import com.swifttechnology.imepaysdk.ENVIRONMENT;
import com.swifttechnology.imepaysdk.IMEPayment;
import com.swifttechnology.imepaysdk.IMEPaymentCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sabaijanakaari.blogspot.kinmel.Adaptor.Cart_Adaptor;
import sabaijanakaari.blogspot.kinmel.Database.ConstantVariable;
import sabaijanakaari.blogspot.kinmel.Database.DBquery;
import sabaijanakaari.blogspot.kinmel.Model.Cart_items_Model;

public class DeliveryActivity extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    public static List<Cart_items_Model> cartItemsModelList;
    private boolean successresponse=false;
    public static boolean fromCart;
    public static boolean cod_order_conformation=false;

    public static final int SElECT_ADDRESS = 0;
    Toolbar toolbar;
    RecyclerView deliveryRecyclerview;
    private Button changeaddressButton;
    private TextView mCartTotalAMount;
    private TextView mfullname;
    private TextView mfulladdress;
    private TextView mpincode;
    private Button mContinubtn;
    Dialog loadingDilaog;
    Dialog paymentMethod;
    LinearLayout khalti,esewa,imepay;
    ImageView cashondeliver;

    String MERCHANT_CODE;
    String MERCHANT_NAME;
    String MERCAHNT_TRANSACTION_RECORDING_URL="https://merchantname.com/merchant_transaction_recording_method";
    String AMOUNT;
    String REFERENCE_ID;
    String MODULE;
    String USERNAME;
    String PASSWORD;

    //conformation layout show after submitting.
    ConstraintLayout mOderConformationLayout;
    TextView mOder_OderId;
    ImageView mcontinueshopping;

    //for OTP Verification
    String name,mobileno;


    private boolean  allProductAvailable=true;
    public static boolean getQtyIds=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");
        firebaseFirestore=FirebaseFirestore.getInstance();
        loadingDilaog = new Dialog(DeliveryActivity.this);
        loadingDilaog.setContentView(R.layout.loading_progress_dialog);
        loadingDilaog.setCancelable(false);
        loadingDilaog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDilaog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_banner_background));

        paymentMethod = new Dialog(DeliveryActivity.this);
        paymentMethod.setContentView(R.layout.payment_method);
        paymentMethod.setCancelable(true);
        paymentMethod.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        getQtyIds=true;

        mOderConformationLayout=findViewById(R.id.order_conformation_layout);
        mOder_OderId=findViewById(R.id.order_id_tx);
        mcontinueshopping=findViewById(R.id.gotohome_btn);

       // paymentMethod.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_banner_background));

        changeaddressButton = findViewById(R.id.change_or_add_address_Button);
        changeaddressButton.setVisibility(View.VISIBLE);
        mfullname = findViewById(R.id.fullname);
        mfulladdress = findViewById(R.id.address);
        mpincode = findViewById(R.id.pincode);//cart_continue_button

        mContinubtn = findViewById(R.id.cart_continue_button);
        mCartTotalAMount = findViewById(R.id.total_cart_amount);

        ///Cart
        deliveryRecyclerview = findViewById(R.id.delivery_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryRecyclerview.setLayoutManager(layoutManager);

        Cart_Adaptor cartAdaptor = new Cart_Adaptor(cartItemsModelList, mCartTotalAMount,false);
        deliveryRecyclerview.setAdapter(cartAdaptor);
        cartAdaptor.notifyDataSetChanged();

        changeaddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQtyIds=false;
                Intent myaddressIntent = new Intent(DeliveryActivity.this, MyaddressesActivity.class);
                myaddressIntent.putExtra("Mode", SElECT_ADDRESS);
                startActivity(myaddressIntent);
            }
        });
        ///cart
        //continu btn for payment
        mContinubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(allProductAvailable){
                    paymentMethod.show();
                    ImageView cashondelivery;
                    khalti=paymentMethod.findViewById(R.id.khaltipaymentBtn);
                    esewa=paymentMethod.findViewById(R.id.esewapaymentbtn);
                    imepay=paymentMethod.findViewById(R.id.imepaybtn);
                    cashondelivery=paymentMethod.findViewById(R.id.cashondeliver_imageButton);
                }else{
                    //all product are not available
                }


            }
        });
        khalti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQtyIds=false;
                paymentMethod.dismiss();
                loadingDilaog.show();
                //khalti account 9861231323
                //password subash@123
                //esewa 9861231323
                //password 5541
                //imepay 9861231323 password 5541
                Map<String, Object> map = new HashMap<>();
                map.put("merchant_extra", "This is extra data");
                Config.Builder builder = new Config.Builder(Constant.pub, "Product ID", "Main", Long.parseLong(AMOUNT), new OnCheckOutListener() {
                    @Override
                    public void onError(@NonNull String action, @NonNull Map<String, String> errorMap) {
                        Log.i(action, errorMap.toString());
                    }

                    @Override
                    public void onSuccess(@NonNull Map<String, Object> data) {
                        Log.i("success", data.toString());
                    }
                }).paymentPreferences(new ArrayList<PaymentPreference>() {{
                            add(PaymentPreference.KHALTI);
                            add(PaymentPreference.EBANKING);
                            add(PaymentPreference.MOBILE_BANKING);
                            add(PaymentPreference.CONNECT_IPS);
                            add(PaymentPreference.SCT);
                        }})
                        .additionalData(map)
                        .productUrl("http://example.com/product")
                        .mobile("9800000000");

            }
        });
        imepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQtyIds=false;
                Toast.makeText(DeliveryActivity.this, "This is under Construction Please check cash on delivery.", Toast.LENGTH_SHORT).show();

                IMEPayment imePayment = new IMEPayment(DeliveryActivity.this, ENVIRONMENT.LIVE);
                imePayment.performPayment(MERCHANT_CODE,MERCHANT_NAME,
                        "MERCAHNT_TRANSACTION_RECORDING_URL",
                        "AMOUNT",
                        "REFERENCE_ID",
                        "MODULE",
                        "USERNAME",
                        "PASSWORD",
                        new IMEPaymentCallback() {
                            @Override
                            public void onSuccess(int responseCode, String responseDescription, String transactionId, String msisdn, String    amount, String refId) {
                                // Response Code 100 : Transaction successful.
                                // Response Code 101 : Transaction failed.
                                // responseDescription : Message sent from server, contains transaction success message/ failure message with reason
                                // transactionId : Unique ID generated from IME pay system
                                // msisdn : Customer Mobile Number
                                // amount : Amount paid by customer
                                // refId : Reference Value
                            }
                        });

            }
        });
        esewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQtyIds=false;

            }
        });
        cashondeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQtyIds=false;
                Intent otpIntent=new Intent(DeliveryActivity.this,OtpvarificationActivity.class);
                otpIntent.putExtra("mobileno",mobileno.substring(0,10));
                startActivity(otpIntent);
            }
        });


    }
    private void showThankyou(String oderId){

        successresponse=true;
        cod_order_conformation=false;
        getQtyIds=false;
        for(int x=0;x<cartItemsModelList.size()-1;x++){
            for(String qtyId:cartItemsModelList.get(x).getQuanityIds()){
                firebaseFirestore.collection(ConstantVariable.Product).document(cartItemsModelList.get(x).getProduct_id()).collection(ConstantVariable.Quanity)
                        .document(qtyId).update(ConstantVariable.Quanity_userid,FirebaseAuth.getInstance().getUid());
                //cartItemsModelList.get(x).getQuanityIds().remove(qtyId);

            }
        }


        if(MainActivity.mainActivity!=null){
            MainActivity.mainActivity.finish();
            MainActivity.mainActivity=null;
            MainActivity.SHOW_CART=false;
        }
        if(ProductdetailActivity.productDetailActiviy!=null){
            ProductdetailActivity.productDetailActiviy.finish();
            ProductdetailActivity.productDetailActiviy=null;
        }
       if(fromCart){
           loadingDilaog.show();
           Map<String,Object> updatecartlist=new HashMap<>();
           long cartlistsize=0;
           final List<Integer> indexlist=new ArrayList<>();

           for(int x=0;x<DBquery.cartList.size();x++){
               if(!DBquery.cartlist_modelList.get(x).isInstock()){
                   cartlistsize++;
                   updatecartlist.put(ConstantVariable.MYCart_product_id+cartlistsize,DBquery.cartlist_modelList.get(x).getProduct_id());
               }else{
                   indexlist.add(x);
               }

           }
           updatecartlist.put(ConstantVariable.MyCart_listsize,cartlistsize);

           DBquery.firebaseFirestore.collection(ConstantVariable.User).document(FirebaseAuth.getInstance().getUid())
                   .collection(ConstantVariable.User_Data).document(ConstantVariable.MYCart)
                   .set(updatecartlist).addOnCompleteListener(new OnCompleteListener<Void>() {
               @Override
               public void onComplete(@NonNull Task<Void> task) {
                   if(task.isSuccessful()){
                        for(int x=0;x<indexlist.size();x++){
                            DBquery.cartList.remove(indexlist.get(x).intValue());
                            DBquery.cartlist_modelList.remove(indexlist.get(x));
                            DBquery.cartlist_modelList.remove(DBquery.cartlist_modelList.size()-1);
                        }
                   }else{

                       Toast.makeText(DeliveryActivity.this, task.getException().getMessage()+"", Toast.LENGTH_SHORT).show();
                   }
                   loadingDilaog.dismiss();

               }
           });

       }else{
           //not coming form cart
       }
        mContinubtn.setEnabled(false);
        changeaddressButton.setEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mOderConformationLayout.setVisibility(View.VISIBLE);
        mOder_OderId.setText("Order Id : "+oderId);
        mcontinueshopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        name=DBquery.addresses_modelList.get(DBquery.selectedaddress).getFullname();
        mobileno=DBquery.addresses_modelList.get(DBquery.selectedaddress).getMobileno();
        mfullname.setText(name+"-"+mobileno);
        mfulladdress.setText(DBquery.addresses_modelList.get(DBquery.selectedaddress).getAddress());
        mpincode.setText(DBquery.addresses_modelList.get(DBquery.selectedaddress).getPinCode());

        //excessing quantity

        if(getQtyIds){
            for(int x=0;x<cartItemsModelList.size()-1;x++){
                final int finalX = x;
                firebaseFirestore.collection(ConstantVariable.Product).document(cartItemsModelList.get(x).getProduct_id()).collection(ConstantVariable.Quanity)
                        .orderBy(ConstantVariable.Quanity_available, Query.Direction.DESCENDING)
                        .limit(cartItemsModelList.get(x).getProductQuanity()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(final QueryDocumentSnapshot queryDocumentSnapshot:task.getResult()){
                                if(queryDocumentSnapshot.exists()){
                                    if(queryDocumentSnapshot.getBoolean(ConstantVariable.Quanity_available)){
                                        //disiable related quanity

                                        firebaseFirestore.collection(ConstantVariable.Product).document(cartItemsModelList.get(finalX).getProduct_id()).collection(ConstantVariable.Quanity)
                                                .document(queryDocumentSnapshot.getId()).update(ConstantVariable.Quanity_available,false)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            //allProductAvailable=true;
                                                            cartItemsModelList.get(finalX).getQuanityIds().add(queryDocumentSnapshot.getId());
                                                        }else{
                                                            //error
                                                            Toast.makeText(DeliveryActivity.this, task.getException().getMessage()+"", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                    }else{
                                        //not available
                                        allProductAvailable=false;
                                        Toast.makeText(DeliveryActivity.this, "All quanity may not be available.", Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                }
                            }
                        }else {
                            Toast.makeText(DeliveryActivity.this, task.getException().getMessage()+"", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }else{
            getQtyIds=true;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(getQtyIds){
            for(int x=0;x<cartItemsModelList.size()-1;x++){
                if(!successresponse){
                    for(String qtyId:cartItemsModelList.get(x).getQuanityIds()){
                        firebaseFirestore.collection(ConstantVariable.Product).document(cartItemsModelList.get(x).getProduct_id()).collection(ConstantVariable.Quanity)
                                .document(qtyId).update(ConstantVariable.Quanity_available,true);
                    }
                }else{

                }
                cartItemsModelList.get(x).getQuanityIds().clear();
            }
        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            //Search Icon
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(successresponse){
            finish();
            return;
        }
        super.onBackPressed();
    }
}

