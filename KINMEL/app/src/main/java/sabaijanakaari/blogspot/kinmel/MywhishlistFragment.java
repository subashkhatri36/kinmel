package sabaijanakaari.blogspot.kinmel;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sabaijanakaari.blogspot.kinmel.Adaptor.Whislist_Adaptor;
import sabaijanakaari.blogspot.kinmel.Database.DBquery;
import sabaijanakaari.blogspot.kinmel.Model.Whistlist_Model;


/**
 * A simple {@link Fragment} subclass.
 */
public class MywhishlistFragment extends Fragment {
    RecyclerView whislistRecyclerview;
    private Dialog loadingDilaog;
    public static Whislist_Adaptor whishlist_adaptor;
    public MywhishlistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_mywhishlist, container, false);

        //loading dilog start
        loadingDilaog=new Dialog(getContext());
        loadingDilaog.setContentView(R.layout.loading_progress_dialog);
        loadingDilaog.setCancelable(false);
        loadingDilaog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDilaog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_banner_background));
        loadingDilaog.show();

        whislistRecyclerview=view.findViewById(R.id.mywhishlist_recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        whislistRecyclerview.setLayoutManager(layoutManager);
        List<Whistlist_Model> modelList=new ArrayList<>();

        //(resources,title,coupen,rating,totalrating,pprice,cutprice,payment);

        if(DBquery.whistlist_modelList.size()==0){
            DBquery.whishList.clear();
            DBquery.loadWhishList(getContext(),loadingDilaog,true);
        }else{
            loadingDilaog.dismiss();
        }

        whishlist_adaptor=new Whislist_Adaptor(DBquery.whistlist_modelList,true);
        whislistRecyclerview.setAdapter(whishlist_adaptor);
        whishlist_adaptor.notifyDataSetChanged();

        return view;

    }
}
