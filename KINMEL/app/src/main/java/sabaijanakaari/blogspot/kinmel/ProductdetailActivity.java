package sabaijanakaari.blogspot.kinmel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sabaijanakaari.blogspot.kinmel.Adaptor.ProductImagesAdaptor;
import sabaijanakaari.blogspot.kinmel.Adaptor.myreward_Adaptor;
import sabaijanakaari.blogspot.kinmel.Adaptor.product_details_Adaptor;
import sabaijanakaari.blogspot.kinmel.Database.ConstantVariable;
import sabaijanakaari.blogspot.kinmel.Database.DBquery;
import sabaijanakaari.blogspot.kinmel.Model.Cart_items_Model;
import sabaijanakaari.blogspot.kinmel.Model.Whistlist_Model;
import sabaijanakaari.blogspot.kinmel.Model.product_specification_Model;
import sabaijanakaari.blogspot.kinmel.Model.reward_Model;

public class ProductdetailActivity extends AppCompatActivity {
    TextView badgecount;

    private ViewPager productImagesViewpager;
    private TabLayout viewPagerIndicator;
    public static FloatingActionButton addToWhistlistbutton;

    public static boolean ALREADY_ADDED_TO_WHISHLIST = false;
    public static boolean ALREADY_ADDED_TO_CARTLIST = false;
    private ViewPager product_details_viewpage;
    private TabLayout product_details_tabLayout;
    private LinearLayout coupenredemptionLayout;
    private Button coupenReedemptButton;
    private TextView product_title;
    private TextView average_rating_miniview;
    private TextView total_rating_miniview;
    private TextView product_price;
    private TextView cutted_price;
    private ImageView codimage;
    private TextView codtext;
    private TextView rewardTitle;
    private TextView rewardbody;

    private TextView product_only_detail_body;

    ///Rating Layout
    public static LinearLayout ratenow_container;
    private LinearLayout rating_no_container;
    private LinearLayout rating_progressbar_container;
    public static TextView totalrating;
    private TextView totalrating_figure;
    private TextView average_tv;
    public static int initalrating;
    public static boolean running_rating_query = false;
    public static boolean running_cart_query = false;

    ///Rating Layout

    private DocumentSnapshot documentSnapshot;

    private Button buynowButton;
    private FirebaseFirestore firestore;
    LinearLayout addtoCartButton;
    public static MenuItem cartItem;

    Dialog loadingDilaog;
    private FirebaseUser currentUser;

    ////product Description variable
    private List<product_specification_Model> product_specification_modelList = new ArrayList<>();

    private ConstraintLayout product_details_only_layout;
    private ConstraintLayout product_details_tab_layout;
    private String product_description_data;
    private String product_Other_details;

    // product Description variable

    //couen dialog
    public static TextView coupenTitle;
    public static TextView coupenbody;
    public static TextView coupenexperidate;
    public static RecyclerView coupenrecyclerview;
    public static LinearLayout selectedcoupen;
    public static String productId;
    //couen dialog

    public static boolean runningWishlistquery = false;
    public static Activity productDetailActiviy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetail);
        String title = getIntent().getStringExtra("ProductName");
        productId = getIntent().getStringExtra("productId");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        buynowButton = findViewById(R.id.buynow_button);
        addtoCartButton = findViewById(R.id.addtocart_button);

        coupenredemptionLayout = findViewById(R.id.coupen_redemption_layout);


        productImagesViewpager = (ViewPager) findViewById(R.id.product_images_viewpager);
        viewPagerIndicator = (TabLayout) findViewById(R.id.viewpager_indicator);
        addToWhistlistbutton = (FloatingActionButton) findViewById(R.id.addtoWhistListButton);
        average_rating_miniview = findViewById(R.id.tv_product_rating_miniview);
        total_rating_miniview = findViewById(R.id.total_rating_miniview);
        product_price = findViewById(R.id.product_price);
        cutted_price = findViewById(R.id.cutted_price);
        codimage = findViewById(R.id.cod_indicator_imageview);
        codtext = findViewById(R.id.cod_indicator_textview);
        rewardTitle = findViewById(R.id.reward_title);
        rewardbody = findViewById(R.id.reward_body);
        product_only_detail_body = findViewById(R.id.product_detail_body);


        //rating
        totalrating = findViewById(R.id.total_rating_tv);
        rating_no_container = findViewById(R.id.ratings_numbers_containers);
        totalrating_figure = findViewById(R.id.total_rating_figure);
        rating_progressbar_container = findViewById(R.id.ratings_progressbar_container);
        average_tv = findViewById(R.id.average_rating_textview);
        initalrating = -1;
        //rating

        ///product details assign
        product_details_only_layout = findViewById(R.id.product_details_container);
        product_details_tab_layout = findViewById(R.id.product_details_tabs_container);
        ///product details assign

        product_details_viewpage = (ViewPager) findViewById(R.id.product_detail_viewpager);
        product_details_tabLayout = (TabLayout) findViewById(R.id.product_details_tablayout_tab);

        firestore = FirebaseFirestore.getInstance();
        final List<String> productImages = new ArrayList<>();
        coupenReedemptButton = findViewById(R.id.coupen_redemtption_button);
        product_title = findViewById(R.id.product_title);


        //Loading Dialog SHowing
        loadingDilaog = new Dialog(ProductdetailActivity.this);
        loadingDilaog.setContentView(R.layout.loading_progress_dialog);
        loadingDilaog.setCancelable(false);
        loadingDilaog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDilaog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_banner_background));
        loadingDilaog.show();

        //Toast.makeText(ProductdetailActivity.this,productId,Toast.LENGTH_SHORT).show();

        firestore.collection(ConstantVariable.Product).document(productId.toString()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()) {
                                for (long x = 1; x < (long) documentSnapshot.get(ConstantVariable.Product_no_ofImages) + 1; x++) {
                                    productImages.add(documentSnapshot.get(ConstantVariable.Product_image + x).toString());
                                    // Toast.makeText(ProductdetailActivity.this,String.valueOf(x),Toast.LENGTH_SHORT).show();
                                }
                                ProductImagesAdaptor imagesAdaptor = new ProductImagesAdaptor(productImages);
                                productImagesViewpager.setAdapter(imagesAdaptor);
                                product_title.setText(documentSnapshot.get(ConstantVariable.Product_title).toString());
                                average_rating_miniview.setText(documentSnapshot.get(ConstantVariable.Product_avragerating).toString());
                                total_rating_miniview.setText("(" + (long) documentSnapshot.get(ConstantVariable.Product_totalrating) + ") ratings".toString());
                                totalrating.setText("(" + (long) documentSnapshot.get(ConstantVariable.Product_totalrating) + ") ratings".toString());
                                product_price.setText("Rs." + documentSnapshot.get(ConstantVariable.Product_product_price).toString() + "/-");
                                cutted_price.setText("Rs." + documentSnapshot.get(ConstantVariable.Product_cutted_price).toString() + "/-");
                                if ((boolean) documentSnapshot.get(ConstantVariable.Product_cod)) {
                                    codimage.setVisibility(View.VISIBLE);
                                    codtext.setVisibility(View.VISIBLE);
                                } else {
                                    codimage.setVisibility(View.INVISIBLE);
                                    codtext.setVisibility(View.INVISIBLE);
                                }

                                rewardTitle.setText("(" + (long) documentSnapshot.get(ConstantVariable.Product_free_coupens) + ") " + documentSnapshot.get(ConstantVariable.Product_free_coupen_title).toString());
                                rewardbody.setText(documentSnapshot.get(ConstantVariable.Product_free_coupen_body).toString());

                                if ((boolean) documentSnapshot.get(ConstantVariable.Product_use_tab_layout)) {
                                    product_details_tab_layout.setVisibility(View.VISIBLE);
                                    product_details_only_layout.setVisibility(View.GONE);
                                    product_description_data = documentSnapshot.get(ConstantVariable.Product_description).toString();
                                    product_Other_details = documentSnapshot.get(ConstantVariable.Product_other_details).toString();
                                    // product_specification_modelList=new ArrayList<>();

                                    for (long x = 1; x < (long) documentSnapshot.get(ConstantVariable.Product_total_spec_title) + 1; x++) {
                                        product_specification_modelList.add(new product_specification_Model(0,
                                                documentSnapshot.get(ConstantVariable.Product_spec_title_ + x).toString()));

                                        for (long z = 1; z < (long) documentSnapshot.get(ConstantVariable.Product_spec_title_ + x + ConstantVariable.Product_spec_title_total_fields) + 1; z++) {

                                            product_specification_modelList.add(new product_specification_Model(1,
                                                    documentSnapshot.get(ConstantVariable.Product_spec_title_ + x + "_fields_" + z + "_name").toString(),
                                                    documentSnapshot.get(ConstantVariable.Product_spec_title_ + x + "_fields_" + z + "_value").toString()));
                                        }
                                    }

                                } else {
                                    product_details_tab_layout.setVisibility(View.GONE);
                                    product_details_only_layout.setVisibility(View.VISIBLE);
                                    product_only_detail_body.setText(documentSnapshot.get(ConstantVariable.Product_description).toString());

                                }

                                //rating code
                                totalrating.setText("(" + (long) documentSnapshot.get(ConstantVariable.Product_totalrating) + ") ratings".toString());

                                for (int x = 0; x < 5; x++) {

                                    TextView rating = (TextView) rating_no_container.getChildAt(x);
                                    rating.setText(String.valueOf((long) documentSnapshot.get((5 - x) + ConstantVariable.Product_star)));
                                    ProgressBar progressBar = (ProgressBar) rating_progressbar_container.getChildAt(x);
                                    int maxprogress = Integer.parseInt(String.valueOf((long) documentSnapshot.get(ConstantVariable.Product_totalrating)));
                                    progressBar.setMax(maxprogress);

                                    progressBar.setProgress(Integer.parseInt(String.valueOf((long) documentSnapshot.get((5 - x) + ConstantVariable.Product_star))));

                                }
                                totalrating_figure.setText(String.valueOf((long) documentSnapshot.get(ConstantVariable.Product_totalrating)));
                                average_tv.setText(documentSnapshot.get(ConstantVariable.Product_avragerating).toString());
                                product_details_viewpage.setAdapter(new product_details_Adaptor(getSupportFragmentManager(), product_details_tabLayout.getTabCount(), product_description_data, product_Other_details, product_specification_modelList));

                                //rating code

                                if (currentUser != null) {
                                    if (DBquery.myratedIds.size() == 0) {
                                        DBquery.loadRatingList(ProductdetailActivity.this);
                                    }
                                    if (DBquery.whishList.size() == 0) {
                                        DBquery.loadWhishList(ProductdetailActivity.this, loadingDilaog, false);

                                    } else {
                                        loadingDilaog.dismiss();
                                    }
                                    if (DBquery.cartList.size() == 0) {
                                        DBquery.loadCartList(ProductdetailActivity.this, loadingDilaog, false, badgecount,new TextView(ProductdetailActivity.this));
                                    }

                                } else {
                                    loadingDilaog.dismiss();
                                }
                                if (DBquery.myratedIds.contains(productId)) {
                                    int index = DBquery.myratedIds.indexOf(productId);
                                    initalrating = Integer.parseInt(String.valueOf(DBquery.myRating.get(index))) - 1;
                                    setRating(initalrating);

                                }
                                if (DBquery.cartList.contains(productId)) {
                                    ALREADY_ADDED_TO_CARTLIST = true;

                                } else {
                                    ALREADY_ADDED_TO_CARTLIST = false;
                                }

                                if (DBquery.whishList.contains(productId)) {
                                    ALREADY_ADDED_TO_WHISHLIST = true;
                                    addToWhistlistbutton.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));

                                } else {
                                    ALREADY_ADDED_TO_WHISHLIST = false;
                                    addToWhistlistbutton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#aeaeae")));
                                    loadingDilaog.dismiss();
                                }

                            }
                            //check stock for the product
                            if ((boolean) documentSnapshot.get(ConstantVariable.Product_in_stock)) {
                                addtoCartButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        if (currentUser == null) {
                                            //go to registe
                                            RegisterActivity.COMING_FOROM = true;
                                            Intent i = new Intent(ProductdetailActivity.this, RegisterActivity.class);
                                            startActivity(i);
                                        } else {
                                            //add to cart
                                            loadingDilaog.show();
                                            if (!running_cart_query) {
                                                running_cart_query = true;

                                                if (ALREADY_ADDED_TO_CARTLIST) {
                                                    running_cart_query = false;
                                                    Toast.makeText(ProductdetailActivity.this, "Already added to Cart.", Toast.LENGTH_SHORT).show();
                                                    loadingDilaog.dismiss();
                                                } else {
                                                    ALREADY_ADDED_TO_CARTLIST = true;
                                                    Map<String, Object> addproduct = new HashMap<>();
                                                    addproduct.put(ConstantVariable.MYCart_product_id + String.valueOf(DBquery.cartList.size()), productId);
                                                    addproduct.put(ConstantVariable.MyCart_listsize, (long) DBquery.cartList.size() + 1);

                                                    firestore.collection(ConstantVariable.User).document(FirebaseAuth.getInstance().getUid()).collection(ConstantVariable.User_Data).document(ConstantVariable.MYCart)
                                                            .update(addproduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                if (DBquery.cartlist_modelList.size() != 0) {
                                                                    DBquery.cartlist_modelList.add(0,new Cart_items_Model(
                                                                            Cart_items_Model.CART_ITEM,
                                                                            productId,
                                                                            documentSnapshot.get(ConstantVariable.MYCart_productprice).toString(),
                                                                            documentSnapshot.get(ConstantVariable.MYCart_cuttedprice).toString(),
                                                                            documentSnapshot.get(ConstantVariable.MYCart_title).toString(),
                                                                            (long) documentSnapshot.get(ConstantVariable.MYCart_freecoupen),
                                                                            documentSnapshot.get(ConstantVariable.MYCart_productimage).toString(),
                                                                            (long)documentSnapshot.get(ConstantVariable.Product_min_quantity),
                                                                            (long) 0,
                                                                            (long) 0,
                                                                             (boolean)documentSnapshot.getBoolean(ConstantVariable.Product_in_stock),
                                                                             (long)documentSnapshot.get(ConstantVariable.Product_max_quantity),
                                                                            (long)documentSnapshot.get(ConstantVariable.Product_min_quantity))
                                                                            );

                                                                }
                                                                ALREADY_ADDED_TO_CARTLIST = true;
                                                                Toast.makeText(ProductdetailActivity.this, "Added To Cart Successfully", Toast.LENGTH_SHORT).show();
                                                                DBquery.cartList.add(productId);
                                                                invalidateOptionsMenu();
                                                                loadingDilaog.dismiss();
                                                                running_cart_query = false;

                                                            } else {
                                                                loadingDilaog.dismiss();
                                                                running_cart_query = false;
                                                                Toast.makeText(ProductdetailActivity.this, "Sorry Some problem Occured." + task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });

                                                }
                                            } else {
                                                running_cart_query = false;
                                                loadingDilaog.dismiss();
                                            }
                                        }
                                    }
                                });
                            } else {
                                buynowButton.setVisibility(View.GONE);
                                TextView outofstock = (TextView) addtoCartButton.getChildAt(0);
                                outofstock.setText("Out Of Stock");
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    outofstock.setTextColor(getColor(R.color.colorPrimary));
                                }
                                outofstock.setCompoundDrawables(null, null, null, null);

                            }


                        } else {
                            Toast.makeText(ProductdetailActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            loadingDilaog.dismiss();
                        }
                    }
                });

        viewPagerIndicator.setupWithViewPager(productImagesViewpager, true);

        addToWhistlistbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentUser == null) {
                    RegisterActivity.COMING_FOROM = true;
                    Intent i = new Intent(ProductdetailActivity.this, RegisterActivity.class);
                    startActivity(i);
                } else {
                    if (!runningWishlistquery) {
                        runningWishlistquery = true;
                        if (ALREADY_ADDED_TO_WHISHLIST) {
                            int index = DBquery.whishList.indexOf(productId);
                            DBquery.removeFromWhishlist(index, ProductdetailActivity.this);
                            addToWhistlistbutton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#aeaeae")));
                        } else {
                            addToWhistlistbutton.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                            Map<String, Object> addproduct = new HashMap<>();
                            addproduct.put(ConstantVariable.Mywishlist_product_id + String.valueOf(DBquery.whishList.size()), productId);
                            addproduct.put(ConstantVariable.Mywishlist_listsize, (long) DBquery.whishList.size() + 1);

                            firestore.collection(ConstantVariable.User).document(FirebaseAuth.getInstance().getUid()).collection(ConstantVariable.User_Data).document(ConstantVariable.Mywishlist)
                                    .update(addproduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        if (DBquery.whistlist_modelList.size() != 0) {
                                            DBquery.whistlist_modelList.add(new Whistlist_Model(documentSnapshot.get(ConstantVariable.Mywishlist_productimage).toString(),
                                                    documentSnapshot.get(ConstantVariable.Mywishlist_title).toString(),
                                                    (long) documentSnapshot.get(ConstantVariable.Mywishlist_freecoupen),
                                                    documentSnapshot.get(ConstantVariable.Mywishlist_avragerating).toString(),
                                                    documentSnapshot.get(ConstantVariable.Mywishlist_totalrating).toString(),
                                                    documentSnapshot.get(ConstantVariable.Mywishlist_productprice).toString(),
                                                    documentSnapshot.get(ConstantVariable.Mywishlist_cuttedprice).toString(),
                                                    (boolean) documentSnapshot.get(ConstantVariable.Mywishlist_cod),
                                                    productId,
                                                    (boolean)documentSnapshot.get(ConstantVariable.Product_in_stock)
                                            ));
                                        }
                                        DBquery.whishList.add(productId);
                                        runningWishlistquery = false;
                                        ALREADY_ADDED_TO_WHISHLIST = true;
                                        Toast.makeText(ProductdetailActivity.this, "Added To whishlist", Toast.LENGTH_SHORT).show();
                                        addToWhistlistbutton.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));


                                    } else {

                                        addToWhistlistbutton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#aeaeae")));
                                        Toast.makeText(ProductdetailActivity.this, "Sorry Some problem Occured." + task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                    runningWishlistquery = false;
                                }
                            });

                        }
                    } else {
                        runningWishlistquery = false;
                    }

                }
            }
        });

        //Prodcut Details Layout works


        product_details_viewpage.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(product_details_tabLayout));
        product_details_tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                product_details_viewpage.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //Prodcut Details Layout works
        //ratenow_layout
        ratenow_container = (LinearLayout) findViewById(R.id.rate_now_container);

        for (int x = 0; x < ratenow_container.getChildCount(); x++) {
            final int startposition = x;
            ratenow_container.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentUser == null) {
                        RegisterActivity.COMING_FOROM = true;
                        Intent i = new Intent(ProductdetailActivity.this, RegisterActivity.class);
                        startActivity(i);
                    } else {
                        if (startposition != initalrating) {

                            if (!running_rating_query) {
                                running_rating_query = true;

                                setRating(startposition);
                                final Map<String, Object> updateRatingMap = new HashMap<>();
                                updateRatingMap.clear();
                                if (DBquery.myratedIds.contains(productId)) {
                                    TextView oldrating = (TextView) rating_no_container.getChildAt(5 - initalrating - 1);
                                    TextView finalrating = (TextView) rating_no_container.getChildAt(5 - startposition - 1);
                                    updateRatingMap.put(initalrating + 1 + ConstantVariable.Product_star, Long.parseLong(oldrating.getText().toString()) - 1);
                                    updateRatingMap.put(startposition + 1 + ConstantVariable.Product_star, Long.parseLong(finalrating.getText().toString()) + 1);
                                    updateRatingMap.put(ConstantVariable.Product_avragerating, CalculateAverage_Rating(startposition - initalrating, true));

                                } else {
                                    //storing first user folder in user data
                                    //boolean status=DBquery.setFirst_UserRating(,startposition,(long)documentSnapshot.get(startposition+1+ConstantVariable.Product_star)+1,CalculateAverage_Rating(startposition+1),(long)documentSnapshot.get(ConstantVariable.Product_totalrating)+1);
                                    updateRatingMap.put(startposition + 1 + ConstantVariable.Product_star, (long) documentSnapshot.get(startposition + 1 + ConstantVariable.Product_star) + 1);
                                    updateRatingMap.put(ConstantVariable.Product_avragerating, CalculateAverage_Rating(startposition + 1, false));
                                    updateRatingMap.put(ConstantVariable.Product_totalrating, (long) documentSnapshot.get(ConstantVariable.Product_totalrating) + 1);
                                    //firestore.collection(ConstantVariable.User)
                                }

                                firestore.collection(ConstantVariable.Product).document(ProductdetailActivity.productId)
                                        .update(updateRatingMap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    //myrating ratkhye ko xa ratingmap
                                                    Map<String, Object> ratingmap = new HashMap<>();

                                                    if (DBquery.myratedIds.contains(productId)) {
                                                        ratingmap.put(ConstantVariable.MYRating_rating + DBquery.myratedIds.indexOf(productId), (long) startposition + 1);
                                                    } else {
                                                        ratingmap.put(ConstantVariable.MYRating_listsize, (long) DBquery.myratedIds.size() + 1);
                                                        ratingmap.put(ConstantVariable.MYRating_product_id + DBquery.myratedIds.size(), ProductdetailActivity.productId);
                                                        ratingmap.put(ConstantVariable.MYRating_rating + DBquery.myratedIds.size(), (long) startposition + 1);
                                                    }

                                                    firestore.collection(ConstantVariable.User).document(FirebaseAuth.getInstance().getUid()).collection(ConstantVariable.User_Data).document(ConstantVariable.MYRating)
                                                            .update(ratingmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task1) {
                                                            if (task1.isSuccessful()) {

                                                                if (DBquery.myratedIds.contains(productId)) {
                                                                    // DBquery.myratedIds.add(ProductdetailActivity.productId);
                                                                    DBquery.myRating.set(DBquery.myratedIds.indexOf(productId), (long) startposition + 1);
                                                                    TextView oldrating = (TextView) rating_no_container.getChildAt(5 - initalrating - 1);
                                                                    oldrating.setText(String.valueOf(Integer.parseInt(oldrating.getText().toString()) - 1));
                                                                    TextView finalrating = (TextView) rating_no_container.getChildAt(5 - startposition - 1);
                                                                    finalrating.setText(String.valueOf(Integer.parseInt(finalrating.getText().toString()) + 1));

                                                                } else {

                                                                    DBquery.myratedIds.add(ProductdetailActivity.productId);
                                                                    DBquery.myRating.add((long) startposition + 1);

                                                                    TextView rating = (TextView) rating_no_container.getChildAt(5 - startposition - 1);
                                                                    rating.setText(String.valueOf(Integer.parseInt(rating.getText().toString()) + 1));

                                                                    total_rating_miniview.setText("(" + (long) documentSnapshot.get(ConstantVariable.Product_totalrating) + 1 + ") ratings".toString());
                                                                    totalrating.setText("(" + ((long) documentSnapshot.get(ConstantVariable.Product_totalrating) + 1) + ") ratings".toString());
                                                                    totalrating_figure.setText(String.valueOf((long) documentSnapshot.get(ConstantVariable.Product_totalrating) + 1));
                                                                    Toast.makeText(ProductdetailActivity.this, "Thank your for rating.", Toast.LENGTH_SHORT).show();
                                                                }


                                                                for (int x = 0; x < 5; x++) {
                                                                    //done in chapter 65
                                                                    TextView rating_figure = (TextView) rating_no_container.getChildAt(x);
                                                                    //TextView rating_figure = (TextView) rating_no_container.getChildAt(5 - startposition + 1);
                                                                    ProgressBar progressBar = (ProgressBar) rating_progressbar_container.getChildAt(x);
                                                                    int maxprogress = Integer.parseInt(String.valueOf(totalrating_figure.getText().toString()));
                                                                    progressBar.setMax(maxprogress);
                                                                    progressBar.setProgress(Integer.parseInt(rating_figure.getText().toString()));

                                                                }
                                                                initalrating = startposition;
                                                                average_tv.setText(CalculateAverage_Rating((long) 0, true));
                                                                average_rating_miniview.setText(CalculateAverage_Rating(0, true));
                                                                if (DBquery.whishList.contains(productId) && DBquery.whistlist_modelList.size() != 0) {
                                                                    int index = DBquery.whishList.indexOf(productId);
                                                                    DBquery.whistlist_modelList.get(index).setRating(average_tv.getText().toString());
                                                                    DBquery.whistlist_modelList.get(index).setTotalrating(totalrating_figure.getText().toString());

                                                                }

                                                                running_rating_query = false;
                                                            } else {
                                                                running_rating_query = false;
                                                                setRating(ProductdetailActivity.initalrating);
                                                                Toast.makeText(ProductdetailActivity.this, task1.getException().getMessage() + "", Toast.LENGTH_SHORT).show();
                                                            }

                                                        }
                                                    });
                                                } else {
                                                    ProductdetailActivity.running_rating_query = false;
                                                    setRating(ProductdetailActivity.initalrating);
                                                    Toast.makeText(ProductdetailActivity.this, task.getException().getMessage() + "", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                        });


                            } else {
                                running_rating_query = true;
                            }
                        }

                    }

                }
            });
        }

        //ratenow _layout

        //////coupen dilaog

        final Dialog checkcoupenpricedialog = new Dialog(ProductdetailActivity.this);
        checkcoupenpricedialog.setContentView(R.layout.coupen_redem_dialog);
        checkcoupenpricedialog.setCancelable(true);
        checkcoupenpricedialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageView toggle = checkcoupenpricedialog.findViewById(R.id.toggle_recyclerview_button);

        coupenrecyclerview = checkcoupenpricedialog.findViewById(R.id.coupen_recyclerview);
        selectedcoupen = checkcoupenpricedialog.findViewById(R.id.selected_coupen_container);
        coupenTitle = checkcoupenpricedialog.findViewById(R.id.coupentitle);
        coupenbody = checkcoupenpricedialog.findViewById(R.id.coupenbody);
        coupenexperidate = checkcoupenpricedialog.findViewById(R.id.coupenValiditi);

        TextView originalprice = checkcoupenpricedialog.findViewById(R.id.original_price);
        TextView discountprice = checkcoupenpricedialog.findViewById(R.id.discount_price);

        LinearLayoutManager layoutManager = new LinearLayoutManager(ProductdetailActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        coupenrecyclerview.setLayoutManager(layoutManager);

        List<reward_Model> reward_modelList = new ArrayList<>();
        reward_modelList.add(new reward_Model("Cash Back", "Till 2nd June 2015", "Hurry Up! you will get cash back in each item above Rs.1000/-"));
        reward_modelList.add(new reward_Model("Discount", "Till 2nd June 2015", "Hurry Up! you will get 20% discount in each item above Rs.1000/-"));
        reward_modelList.add(new reward_Model("By 1 Get 1 Free", "Till 2nd June 2015", "Hurry Up! you will get cash back in each item above Rs.1000/-"));
        reward_modelList.add(new reward_Model("Cash Back", "Till 2nd June 2015", "Hurry Up! you will get cash back in each item above Rs.1000/-"));

        myreward_Adaptor adaptor = new myreward_Adaptor(reward_modelList, true);
        coupenrecyclerview.setAdapter(adaptor);
        adaptor.notifyDataSetChanged();

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogRecyclerview();
            }
        });
        coupenReedemptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkcoupenpricedialog.show();
            }
        });

        ///coupen diilaog


        buynowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (currentUser == null) {
                    RegisterActivity.COMING_FOROM = true;
                    Intent i = new Intent(ProductdetailActivity.this, RegisterActivity.class);
                    startActivity(i);
                } else {
                    loadingDilaog.show();
                    DeliveryActivity.fromCart=false;
                    productDetailActiviy=ProductdetailActivity.this;
                    //DeliveryActivity.cartItemsModelList.clear();
                    DeliveryActivity.cartItemsModelList=new ArrayList<>();

                    DeliveryActivity.cartItemsModelList.add(new Cart_items_Model(
                            Cart_items_Model.CART_ITEM,
                            productId,
                            documentSnapshot.get(ConstantVariable.MYCart_productprice).toString(),
                            documentSnapshot.get(ConstantVariable.MYCart_cuttedprice).toString(),
                            documentSnapshot.get(ConstantVariable.MYCart_title).toString(),
                            (long) documentSnapshot.get(ConstantVariable.MYCart_freecoupen),
                            documentSnapshot.get(ConstantVariable.MYCart_productimage).toString(),
                            (long) 1,
                            (long) 0,
                            (long) 0,
                            (boolean)documentSnapshot.getBoolean(ConstantVariable.Product_in_stock),
                            (long)documentSnapshot.get(ConstantVariable.Product_max_quantity),
                            (long)documentSnapshot.get(ConstantVariable.Product_min_quantity))
                    );
                    DeliveryActivity.cartItemsModelList.add(new Cart_items_Model(Cart_items_Model.TOTAL_AMOUNT));

                    if(DBquery.addresses_modelList.size()==0) {
                        DBquery.loadAddresses(ProductdetailActivity.this, loadingDilaog);
                    }else{
                        loadingDilaog.dismiss();
                        Intent inted = new Intent(ProductdetailActivity.this, DeliveryActivity.class);
                        startActivity(inted);
                    }
                }

            }
        });

    }

    private String CalculateAverage_Rating(long currentuserRating, boolean update) {
        double totalstars = Double.valueOf(0);
        for (int i = 1; i < 6; i++) {
            TextView ratingno = (TextView) rating_no_container.getChildAt(5 - i);
            //*i
            totalstars = totalstars + (Long.parseLong(ratingno.getText().toString()) * i);
        }
        totalstars = totalstars + currentuserRating;
        if (update) {
            return String.valueOf(totalstars / Long.parseLong(totalrating_figure.getText().toString())).substring(0, 3);
        } else {

            return String.valueOf(totalstars / (Long.parseLong(totalrating_figure.getText().toString()) + 1)).substring(0, 3);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            coupenredemptionLayout.setVisibility(View.GONE);
        } else {
            coupenredemptionLayout.setVisibility(View.VISIBLE);
        }
        if (currentUser != null) {

            if (DBquery.myratedIds.size() == 0) {
                DBquery.loadRatingList(ProductdetailActivity.this);
            }
            if (DBquery.whishList.size() == 0) {
                DBquery.loadWhishList(ProductdetailActivity.this, loadingDilaog, false);

            } else {
                loadingDilaog.dismiss();
            }

        } else {
            loadingDilaog.dismiss();
        }
        if (DBquery.myratedIds.contains(productId)) {
            int index = DBquery.myratedIds.indexOf(productId);
            initalrating = Integer.parseInt(String.valueOf(DBquery.myRating.get(index))) - 1;
            setRating(initalrating);

        }
        if (DBquery.cartList.contains(productId)) {
            ALREADY_ADDED_TO_CARTLIST = true;

        } else {
            ALREADY_ADDED_TO_CARTLIST = false;
        }

        if (DBquery.whishList.contains(productId)) {
            ALREADY_ADDED_TO_WHISHLIST = true;
            addToWhistlistbutton.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));

        } else {
            ALREADY_ADDED_TO_WHISHLIST = false;
            addToWhistlistbutton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#aeaeae")));
            loadingDilaog.dismiss();
        }
        invalidateOptionsMenu();
    }

    public static void showDialogRecyclerview() {
        if (coupenrecyclerview.getVisibility() == View.GONE) {
            coupenrecyclerview.setVisibility(View.VISIBLE);
            selectedcoupen.setVisibility(View.GONE);
        } else {
            coupenrecyclerview.setVisibility(View.GONE);
            selectedcoupen.setVisibility(View.VISIBLE);
        }
    }

    public static void setRating(int startposition) {
        for (int x = 0; x < ratenow_container.getChildCount(); x++) {
            ImageView startButton = (ImageView) ratenow_container.getChildAt(x);
            startButton.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if (x <= startposition) {
                startButton.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);

        cartItem = menu.findItem(R.id.action_mychart);

        cartItem.setActionView(R.layout.bagde_layout);
        ImageView badgeIcon = cartItem.getActionView().findViewById(R.id.badge_icon);
        badgeIcon.setImageResource(R.drawable.ic_shopping_cart_white_24dp);
        badgecount = cartItem.getActionView().findViewById(R.id.badge_count);
        badgecount.setText(String.valueOf(DBquery.cartList.size()));
        if (currentUser != null) {
            if (DBquery.cartList.size() == 0) {
                DBquery.loadCartList(ProductdetailActivity.this, loadingDilaog, false, badgecount,new TextView(ProductdetailActivity.this));

            } else {
                badgecount.setVisibility(View.VISIBLE);
                if (DBquery.cartList.size() < 99) {
                    badgecount.setText(String.valueOf(DBquery.cartList.size()));
                } else {
                    badgecount.setText("99");
                }
            }
        }
        cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    RegisterActivity.COMING_FOROM = true;
                    Intent i = new Intent(ProductdetailActivity.this, RegisterActivity.class);
                    startActivity(i);
                } else {
                    Intent cartIntent = new Intent(ProductdetailActivity.this, MainActivity.class);
                    MainActivity.SHOW_CART = true;
                    startActivity(cartIntent);
                }
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.main_search_icon) {
            //Search Icon
            return true;
        } else if (id == R.id.action_mychart) {
            if (currentUser == null) {
                RegisterActivity.COMING_FOROM = true;
                Intent i = new Intent(ProductdetailActivity.this, RegisterActivity.class);
                startActivity(i);
            } else {
                Intent cartIntent = new Intent(ProductdetailActivity.this, MainActivity.class);
                MainActivity.SHOW_CART = true;
                startActivity(cartIntent);
                return true;
            }


        } else if (id == android.R.id.home) {
            productDetailActiviy=null;
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        productDetailActiviy=null;
        super.onBackPressed();

    }
}

