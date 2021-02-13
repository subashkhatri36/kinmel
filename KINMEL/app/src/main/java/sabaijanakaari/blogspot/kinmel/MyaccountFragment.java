package sabaijanakaari.blogspot.kinmel;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import sabaijanakaari.blogspot.kinmel.Database.DBquery;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyaccountFragment extends Fragment {
    public static final int Manage_Address=1;
    Button allAddressbutton;
    Button mSingOut;

    public MyaccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_myaccount, container, false);
        mSingOut=(Button)view.findViewById(R.id.signout_button);
        mSingOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                DBquery.ClearData();
                Intent registeractivity=new Intent(getContext(),RegisterActivity.class);
                registeractivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(registeractivity);
            }
        });

        allAddressbutton=view.findViewById(R.id.viewall_addresses_button);
        allAddressbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myaddressIntent=new Intent(getContext(),MyaddressesActivity.class);
                myaddressIntent.putExtra("Mode",Manage_Address);
                startActivity(myaddressIntent);
            }
        });

        return view;
    }
}
