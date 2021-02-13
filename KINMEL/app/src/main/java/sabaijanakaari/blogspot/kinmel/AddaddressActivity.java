package sabaijanakaari.blogspot.kinmel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import sabaijanakaari.blogspot.kinmel.Database.ConstantVariable;
import sabaijanakaari.blogspot.kinmel.Database.DBquery;
import sabaijanakaari.blogspot.kinmel.Model.addresses_Model;

public class AddaddressActivity extends AppCompatActivity {
    private Dialog loadingDilaog;
    Toolbar toolbar;
    Button saveButton;
    EditText mCity, mlocality, mflatno, mpincode, mlandmark, mname, mmobileno, malternatemobile;
    Spinner mstate;
    private String selectedState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addaddress);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Add a new address");

        loadingDilaog = new Dialog(AddaddressActivity.this);
        loadingDilaog.setContentView(R.layout.loading_progress_dialog);
        loadingDilaog.setCancelable(false);
        loadingDilaog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDilaog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_banner_background));


        saveButton = findViewById(R.id.save_address_button);
        mCity = findViewById(R.id.city);
        mlocality = findViewById(R.id.locality);
        mflatno = findViewById(R.id.flat_no);
        mpincode = findViewById(R.id.pincode);
        mlandmark = findViewById(R.id.landmark);
        mname = findViewById(R.id.name);
        mmobileno = findViewById(R.id.mobileno);
        malternatemobile = findViewById(R.id.altername_mobileno);
        mstate = findViewById(R.id.statespinner);

        mstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedState = mstate.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedState = "";
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDilaog.show();
                if (!TextUtils.isEmpty(mCity.getText().toString()) && mCity.getText().toString().length() > 0) {
                    mCity.setError(null);
                    if (!TextUtils.isEmpty(mlocality.getText().toString()) && mlocality.getText().toString().length() > 0) {
                        mlocality.setError(null);
                        if (!TextUtils.isEmpty(mflatno.getText().toString()) && mflatno.getText().toString().length() > 0) {
                            mflatno.setError(null);
                            if (!TextUtils.isEmpty(mpincode.getText().toString()) && (mpincode.getText().toString().length() > 5 && mpincode.getText().toString().length() < 7)) {
                                mpincode.setError(null);
                                if (!TextUtils.isEmpty(mname.getText().toString()) && mname.getText().toString().length() > 0) {
                                    mname.setError(null);
                                    if (!TextUtils.isEmpty(mmobileno.getText().toString()) && (mmobileno.getText().toString().length() > 9 && mmobileno.getText().toString().length() < 11)) {
                                        mmobileno.setError(null);
                                        final String tempaddress = mflatno.getText().toString() + " " + mlocality.getText().toString() + " " + mlandmark.getText().toString() + " " + mCity.getText().toString() + " " + selectedState;

                                        Map<String, Object> addressMap = new HashMap<>();
                                        addressMap.put(ConstantVariable.MYAddress_listsize, DBquery.addresses_modelList.size() + 1);
                                        if (TextUtils.isEmpty(malternatemobile.getText())) {
                                            addressMap.put(ConstantVariable.MYAddress_moobileno + String.valueOf((long) DBquery.addresses_modelList.size() + 1), mmobileno.getText().toString());


                                        } else {

                                            addressMap.put(ConstantVariable.MYAddress_moobileno + String.valueOf((long) DBquery.addresses_modelList.size() + 1), mmobileno.getText().toString() + " Or " + malternatemobile.getText().toString());

                                        }
                                        addressMap.put(ConstantVariable.MYAddress_fullname + String.valueOf((long) DBquery.addresses_modelList.size() + 1), mname.getText().toString());

                                        addressMap.put(ConstantVariable.MYAddress_addresses + String.valueOf((long) DBquery.addresses_modelList.size() + 1), tempaddress);
                                        addressMap.put(ConstantVariable.MYAddress_pincode + String.valueOf((long) DBquery.addresses_modelList.size() + 1), mpincode.getText().toString());
                                        addressMap.put(ConstantVariable.MYAddress_selected + String.valueOf((long) DBquery.addresses_modelList.size() + 1), true);
                                        if (DBquery.addresses_modelList.size() > 0) {
                                            addressMap.put(ConstantVariable.MYAddress_selected + (long) DBquery.selectedaddress + 1, false);
                                        }


                                        FirebaseFirestore.getInstance().collection(ConstantVariable.User).document(FirebaseAuth.getInstance().getUid()).collection(ConstantVariable.User_Data)
                                                .document(ConstantVariable.MYAddress)
                                                .update(addressMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    if (DBquery.addresses_modelList.size() > 0) {
                                                        DBquery.addresses_modelList.get(DBquery.selectedaddress).setSelected(false);
                                                    }
                                                    if (TextUtils.isEmpty(malternatemobile.getText())) {
                                                        DBquery.addresses_modelList.add(new addresses_Model(mname.getText().toString(),
                                                                tempaddress, mpincode.getText().toString(), true,mmobileno.getText().toString()));
                                                    }else{
                                                        DBquery.addresses_modelList.add(new addresses_Model(mname.getText().toString(),
                                                                tempaddress, mpincode.getText().toString(), true,mmobileno.getText().toString()+" Or"+malternatemobile.getText().toString()));
                                                    }
                                                    if(getIntent().getStringExtra("INTENT").equals("deliveryintent")){
                                                        Intent gotodelivery = new Intent(AddaddressActivity.this, DeliveryActivity.class);
                                                        startActivity(gotodelivery);
                                                    }else{
                                                        MyaddressesActivity.reFresh_ITems(DBquery.selectedaddress,DBquery.addresses_modelList.size()-1);
                                                    }
                                                    DBquery.selectedaddress=DBquery.addresses_modelList.size()-1;
                                                    finish();
                                                } else {
                                                    Toast.makeText(AddaddressActivity.this, task.getException().toString() + "", Toast.LENGTH_SHORT).show();

                                                }
                                                loadingDilaog.dismiss();
                                            }
                                        });
                                    } else {
                                        mmobileno.requestFocus();
                                        mmobileno.setError("Mobile no must not be empty");
                                    }
                                } else {
                                    mname.requestFocus();
                                    mname.setError("Name must not be empty");
                                }
                            } else {
                                mpincode.requestFocus();
                                mpincode.setError("Pin no must not be empty");
                            }
                        } else {
                            mflatno.requestFocus();
                            mflatno.setError("Flat no must not be empty");
                        }
                    } else {
                        mlocality.requestFocus();
                        mlocality.setError("Locality must not be empty");
                    }
                } else {
                    mCity.requestFocus();
                    mCity.setError("City must not be empty");
                }


            }
        });

    }

    private boolean validationAddress() {
        boolean status = false;


        return status;
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
}
