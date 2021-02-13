package sabaijanakaari.blogspot.kinmel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sabaijanakaari.blogspot.kinmel.Adaptor.Addresses_Adaptor;
import sabaijanakaari.blogspot.kinmel.Database.ConstantVariable;
import sabaijanakaari.blogspot.kinmel.Database.DBquery;
import sabaijanakaari.blogspot.kinmel.Model.addresses_Model;

import static sabaijanakaari.blogspot.kinmel.DeliveryActivity.SElECT_ADDRESS;

public class MyaddressesActivity extends AppCompatActivity {
    private int preaddress;
    Toolbar toolbar;
    RecyclerView myAddressesRecyclerview;
    private static Addresses_Adaptor adaptor;
    private Button deliverHere;
    TextView maddAddress;
    private TextView addressessaved;
    private Dialog loadingDilaog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaddresses);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Address");
        preaddress=DBquery.selectedaddress;

        loadingDilaog=new Dialog(this);
        loadingDilaog.setContentView(R.layout.loading_progress_dialog);
        loadingDilaog.setCancelable(false);
        loadingDilaog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDilaog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_banner_background));

        myAddressesRecyclerview=findViewById(R.id.addresses_recyclerview);
        addressessaved=findViewById(R.id.address_saved);

        deliverHere=findViewById(R.id.deliever_here_button);
        maddAddress=findViewById(R.id.textView23);
        maddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte=new Intent(MyaddressesActivity.this,AddaddressActivity.class);
                inte.putExtra("INTENT","null");
                startActivity(inte);
            }
        });


        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myAddressesRecyclerview.setLayoutManager(layoutManager);


        int mode=getIntent().getIntExtra("Mode",-1);
        if(mode==SElECT_ADDRESS){
            deliverHere.setVisibility(View.VISIBLE);
        }else {
            deliverHere.setVisibility(View.GONE);
        }
        adaptor=new Addresses_Adaptor(DBquery.addresses_modelList,mode);
        myAddressesRecyclerview.setAdapter(adaptor);
        ((SimpleItemAnimator)myAddressesRecyclerview.getItemAnimator()).setSupportsChangeAnimations(false);
        adaptor.notifyDataSetChanged();

        deliverHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(DBquery.selectedaddress!=preaddress){
                    final int preaddressindex=preaddress;
                    loadingDilaog.show();
                    Map<String,Object> updateselction=new HashMap<>();
                    updateselction.put(ConstantVariable.MYAddress_selected+String.valueOf(preaddress+1),false);
                    updateselction.put(ConstantVariable.MYAddress_selected+String.valueOf(DBquery.selectedaddress+1),true);
                    preaddress=DBquery.selectedaddress;
                    FirebaseFirestore.getInstance().collection(ConstantVariable.User).document(FirebaseAuth.getInstance().getUid()).collection(ConstantVariable.User_Data)
                            .document(ConstantVariable.MYAddress).update(updateselction).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                finish();

                            }else{
                                preaddress=preaddressindex;
                                Toast.makeText(MyaddressesActivity.this, task.getException().getMessage()+"", Toast.LENGTH_SHORT).show();
                            }
                            loadingDilaog.dismiss();
                        }
                    });
                }else{
                    finish();
                }
            }
        });


    }
    public static void reFresh_ITems(int deselect,int select){
        adaptor.notifyItemChanged(deselect);
        adaptor.notifyItemChanged(select);

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            //return back to home
            if(DBquery.selectedaddress!=preaddress){
                DBquery.addresses_modelList.get(DBquery.selectedaddress).setSelected(false);
                DBquery.addresses_modelList.get(preaddress).setSelected(true);
                DBquery.selectedaddress=preaddress;
            }
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(DBquery.addresses_modelList.size()>0)
        addressessaved.setText(String.valueOf(DBquery.addresses_modelList.size())+" address(es) saved");
        else
            addressessaved.setText("0 address(es) saved");
    }

    @Override
    public void onBackPressed() {
        if(DBquery.selectedaddress!=preaddress){
            DBquery.addresses_modelList.get(DBquery.selectedaddress).setSelected(false);
            DBquery.addresses_modelList.get(preaddress).setSelected(true);
            DBquery.selectedaddress=preaddress;
        }

        super.onBackPressed();

    }
}
