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

import sabaijanakaari.blogspot.kinmel.Adaptor.myreward_Adaptor;
import sabaijanakaari.blogspot.kinmel.Model.reward_Model;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyrewardFragment extends Fragment {
    RecyclerView reward_recyclerview;
    public MyrewardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_myreward, container, false);
        reward_recyclerview=view.findViewById(R.id.myreward_recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        reward_recyclerview.setLayoutManager(layoutManager);

        List<reward_Model> reward_modelList=new ArrayList<>();
        reward_modelList.add(new reward_Model("Cash Back","Till 2nd June 2015","Hurry Up! you will get cash back in each item above Rs.1000/-"));
        reward_modelList.add(new reward_Model("Discount","Till 2nd June 2015","Hurry Up! you will get 20% discount in each item above Rs.1000/-"));
        reward_modelList.add(new reward_Model("By 1 Get 1 Free","Till 2nd June 2015","Hurry Up! you will get cash back in each item above Rs.1000/-"));
        reward_modelList.add(new reward_Model("Cash Back","Till 2nd June 2015","Hurry Up! you will get cash back in each item above Rs.1000/-"));

        myreward_Adaptor adaptor=new myreward_Adaptor(reward_modelList,false);
        reward_recyclerview.setAdapter(adaptor);
        adaptor.notifyDataSetChanged();

        return view;
    }
}
