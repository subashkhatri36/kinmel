package sabaijanakaari.blogspot.kinmel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sabaijanakaari.blogspot.kinmel.Database.ConstantVariable;
import sabaijanakaari.blogspot.kinmel.Database.DBquery;

public class OtpvarificationActivity extends AppCompatActivity {
    String mobileno;
    private TextView phoneno;
    private EditText Otp;
    private Button verifybtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpvarification);
        mobileno=getIntent().getStringExtra("mobileno");

        phoneno=findViewById(R.id.phone_no);
        Otp=findViewById(R.id.otp_editText);
        verifybtn=findViewById(R.id.verify_button);
        phoneno.setText("Verification code has been send to"+mobileno+" please check your message.");
        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
