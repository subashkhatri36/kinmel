package sabaijanakaari.blogspot.kinmel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sabaijanakaari.blogspot.kinmel.Adaptor.myOrder_Adaptor;
import sabaijanakaari.blogspot.kinmel.Model.MyOrder_Items_Model;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyorderFragment extends Fragment {
    private RecyclerView myOrderRecyclerview;

    public MyorderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_myorder, container, false);
        myOrderRecyclerview=(RecyclerView)v.findViewById(R.id.myorder_recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myOrderRecyclerview.setLayoutManager(layoutManager);

        List<MyOrder_Items_Model> models=new ArrayList<>();
        models.add(new MyOrder_Items_Model(R.drawable.top_banner_two,"Hello Brother","Delivered on 15 march",0));
        models.add(new MyOrder_Items_Model(R.drawable.banner_one,"Sony TV","Delivered on 15 march",1));
        models.add(new MyOrder_Items_Model(R.drawable.banner_three,"Hello Brother","Cancelled",5));

        myOrder_Adaptor myOrder_adaptor=new myOrder_Adaptor(models);
        myOrderRecyclerview.setAdapter(myOrder_adaptor);
        myOrder_adaptor.notifyDataSetChanged();
        return v;
    }

}
