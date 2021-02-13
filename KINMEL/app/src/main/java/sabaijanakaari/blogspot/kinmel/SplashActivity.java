package sabaijanakaari.blogspot.kinmel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;

import sabaijanakaari.blogspot.kinmel.Database.DBquery;

public class SplashActivity extends AppCompatActivity {
    public static int maxsetDeliveryChargeFree;
    public static int setDeliveryCharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SystemClock.sleep(3000);
       // printHashKey(getApplicationContext());
        Intent loginIntent=new Intent(SplashActivity.this,RegisterActivity.class);
        startActivity(loginIntent);
        finish();
    }
    public static void printHashKey(Context context) {
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                final MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                final String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("AppLog", "key:" + hashKey + "=");
            }
        } catch (Exception e) {
            Log.e("AppLog", "error:", e);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        DBquery.DeliveryFreeMaxdata();
    }
}
