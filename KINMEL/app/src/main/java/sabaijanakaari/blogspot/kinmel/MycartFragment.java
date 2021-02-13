package sabaijanakaari.blogspot.kinmel;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sabaijanakaari.blogspot.kinmel.Adaptor.Cart_Adaptor;
import sabaijanakaari.blogspot.kinmel.Database.DBquery;
import sabaijanakaari.blogspot.kinmel.Model.Cart_items_Model;


/**
 * A simple {@link Fragment} subclass.
 */
public class MycartFragment extends Fragment {
    RecyclerView cartRecyclerView;
    public static Cart_Adaptor cartAdaptor;
    private Button continueButton;
    private Dialog loadingDilaog;
    private TextView mtotalAomut;


    public MycartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mycart, container, false);
        //loading dilog start

        loadingDilaog = new Dialog(getContext());
        loadingDilaog.setContentView(R.layout.loading_progress_dialog);
        loadingDilaog.setCancelable(false);
        loadingDilaog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDilaog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_banner_background));
        loadingDilaog.show();

        cartRecyclerView = v.findViewById(R.id.cart_item_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartRecyclerView.setLayoutManager(layoutManager);
        continueButton = v.findViewById(R.id.cart_continue_button);
        mtotalAomut = v.findViewById(R.id.total_cart_amount);


        cartAdaptor = new Cart_Adaptor(DBquery.cartlist_modelList, mtotalAomut, true);
        cartRecyclerView.setAdapter(cartAdaptor);
        cartAdaptor.notifyDataSetChanged();

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeliveryActivity.cartItemsModelList = new ArrayList<>();
                DeliveryActivity.fromCart=true;
                for (int x = 0; x < DBquery.cartlist_modelList.size(); x++) {
                    Cart_items_Model cart_items_model = DBquery.cartlist_modelList.get(x);
                    if (cart_items_model.isInstock()) {
                        DeliveryActivity.cartItemsModelList.add(cart_items_model);
                    }
                }
                DeliveryActivity.cartItemsModelList.add(new Cart_items_Model(Cart_items_Model.TOTAL_AMOUNT));
                loadingDilaog.show();
                if(DBquery.addresses_modelList.size()==0) {
                    DBquery.loadAddresses(getContext(), loadingDilaog);
                }else{
                    loadingDilaog.dismiss();
                    Intent inted = new Intent(getContext(), DeliveryActivity.class);
                    startActivity(inted);
                }

            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        cartAdaptor.notifyDataSetChanged();
        if (DBquery.cartlist_modelList.size() == 0) {
            DBquery.cartList.clear();
            DBquery.loadCartList(getContext(), loadingDilaog, true, new TextView(getContext()),mtotalAomut);
        } else {
            if (DBquery.cartlist_modelList.get(DBquery.cartlist_modelList.size() - 1).getType() == Cart_items_Model.TOTAL_AMOUNT) {
                LinearLayout parent = (LinearLayout) mtotalAomut.getParent().getParent();
                parent.setVisibility(View.VISIBLE);
            }
            loadingDilaog.dismiss();
        }
    }
}
