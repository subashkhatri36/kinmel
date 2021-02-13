package sabaijanakaari.blogspot.kinmel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import sabaijanakaari.blogspot.kinmel.Database.ConstantVariable;

import com.facebook.Profile;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    public static boolean COMING_FOROM=false;
    CallbackManager mCallbackManager;
    private static final String EMAIL = "email";
    LoginButton loginButton;
    private ImageView mClose;
    CircleImageView mProfile;

    TextView mProfliename,mEmail;

    Dialog loadingDilaog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        mAuth=FirebaseAuth.getInstance();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        mClose=(ImageView)findViewById(R.id.close_imageView);
        mProfliename=(TextView) findViewById(R.id.fb_username);
        mEmail=(TextView)findViewById(R.id.fb_email_id);
        mProfile=(CircleImageView)findViewById(R.id.profile_pic);

        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMainActivity();
            }
        });

        boolean loggedOut = AccessToken.getCurrentAccessToken() == null;
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();


        if(networkInfo!=null && networkInfo.isConnected()==true){
            if (!loggedOut) {
                Glide.with(this).load(Profile.getCurrentProfile().getProfilePictureUri(200, 200)).into(mProfile);
                //  Log.d("TAG", "Username is: " + Profile.getCurrentProfile().getName());
                //Using Graph API
                getUserProfile(AccessToken.getCurrentAccessToken());
            }
        }


        //FirebaseAuth.getInstance().signOut();
        loginButton.setReadPermissions("email","public_profile", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginButton.setVisibility(View.INVISIBLE);
                loginButton.setEnabled(false);
                //Loading Dialog SHowing
                loadingDilaog=new Dialog(RegisterActivity.this);
                loadingDilaog.setContentView(R.layout.loading_progress_dialog);
                loadingDilaog.setCancelable(false);
                loadingDilaog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                loadingDilaog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_banner_background));
                loadingDilaog.show();
               // Log.d(TAG, "facebook:onSuccess:" + loginResult);
                getUserProfile(loginResult.getAccessToken());
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
              //  Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
               // Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
    private void handleFacebookAccessToken(AccessToken token) {
      //  Log.d(TAG, "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                          //  Log.d(TAG, "signInWithCredential:success");
                            mAuth=FirebaseAuth.getInstance();
                            FirebaseUser muser=mAuth.getCurrentUser();
                            final FirebaseFirestore db=FirebaseFirestore.getInstance();
                            final String[] isEmail = {""};
                            db.collection("USERS").whereEqualTo("Email",email)
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(DocumentSnapshot documentSnapshot:task.getResult()){
                                            if(documentSnapshot.exists()){
                                                isEmail[0] =documentSnapshot.getString("Email");
                                            }
                                        }
                                    }
                                }
                            });
                                try{
                                    //IsAdmin
                                    if(isEmail[0].isEmpty() && isEmail[0].length()==0) {
                                        Map<String, Object> logmap = new HashMap<>();
                                        logmap.put("Username", firstname);
                                        logmap.put("Email", email);
                                        logmap.put("Image", img_url);
                                        logmap.put("IsAdmin",(boolean) false);

                                        mAuth = FirebaseAuth.getInstance();
                                         muser = mAuth.getCurrentUser();

                                        final FirebaseUser finalMuser = muser;
                                        db.collection(ConstantVariable.User).document(muser.getUid()).set(logmap)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            //maps

                                                            CollectionReference userdataReference=db.collection(ConstantVariable.User).document(finalMuser.getUid()).collection(ConstantVariable.User_Data);
                                                            final List<String> doumentnames=new ArrayList<>();
                                                            doumentnames.add(ConstantVariable.Mywishlist);
                                                            doumentnames.add(ConstantVariable.MYRating);
                                                            doumentnames.add(ConstantVariable.MYCart);
                                                            doumentnames.add(ConstantVariable.MYAddress);

                                                            //wishlist data
                                                            Map<String, Object> wishlistmap = new HashMap<>();
                                                            wishlistmap.put(ConstantVariable.Mywishlist_listsize, (long) 0);
                                                            //Rating
                                                            Map<String, Object> myratingmap = new HashMap<>();
                                                            myratingmap.put(ConstantVariable.MYRating_listsize, (long) 0);

                                                            //MYCart filed
                                                            Map<String, Object> mycartmap = new HashMap<>();
                                                            mycartmap.put(ConstantVariable.MyCart_listsize, (long) 0);

                                                            //addressfiled filed
                                                            Map<String, Object> myaddressmap = new HashMap<>();
                                                            myaddressmap.put(ConstantVariable.MYAddress_listsize, (long) 0);

                                                            //field setting table and fileds
                                                            List<Map<String, Object>> docuemntFeilds=new ArrayList<>();
                                                            docuemntFeilds.add(wishlistmap);
                                                            docuemntFeilds.add(myratingmap);
                                                            docuemntFeilds.add(mycartmap);
                                                            docuemntFeilds.add(myaddressmap);

                                                            for(int x=0;x<doumentnames.size();x++){
                                                                final int finalX = x;
                                                                userdataReference.document(String.valueOf(doumentnames.get(x)))
                                                                        .set(docuemntFeilds.get(x)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if(task.isSuccessful()){
                                                                            if(finalX ==doumentnames.size()-1){
                                                                                mProfliename.setText(firstname);
                                                                                mEmail.setText(email);
                                                                                Glide.with(RegisterActivity.this).load(img_url).into(mProfile);
                                                                                loadingDilaog.dismiss();
                                                                                gotoMainActivity();
                                                                            }
                                                                        }else{
                                                                            loadingDilaog.dismiss();
                                                                            loginButton.setVisibility(View.VISIBLE);
                                                                            loginButton.setEnabled(true);
                                                                            Toast.makeText(RegisterActivity.this, "Sorry Some problem Occured. in saving wishlist" + task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                                                                        }


                                                                    }
                                                                });
                                                            }
                                                        }

                                                    }
                                                });
                                    }

                                }catch (Exception ex){

                                }

                           // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }
    String firstname="",email="",img_url="";
    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                       // Log.d("TAG", object.toString());
                        try {
                            final String first_name = object.getString("first_name");
                            final String last_name = object.getString("last_name");
                            firstname=first_name+" "+last_name;
                            email = object.getString("email");
                            String id = object.getString("id");
                            img_url = "https://graph.facebook.com/" + id + "/picture?type=normal";
                            //store this image into firebase


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            gotoMainActivity();
        }
    }

    private void gotoMainActivity() {
            Intent gotointent=new Intent(RegisterActivity.this,MainActivity.class);
            startActivity(gotointent);
            finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(COMING_FOROM){
               finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
