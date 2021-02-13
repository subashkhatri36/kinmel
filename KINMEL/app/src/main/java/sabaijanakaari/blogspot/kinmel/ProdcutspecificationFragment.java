package sabaijanakaari.blogspot.kinmel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sabaijanakaari.blogspot.kinmel.Adaptor.productSepcificationAdaptor;
import sabaijanakaari.blogspot.kinmel.Model.product_specification_Model;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProdcutspecificationFragment extends Fragment {
    RecyclerView productspecificationRecyclerview;
    View view;
    public List<product_specification_Model> product_specification_modelList;
    public ProdcutspecificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_prodcutspecification, container, false);
        productspecificationRecyclerview=(RecyclerView)view.findViewById(R.id.product_specification_recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        productspecificationRecyclerview.setLayoutManager(linearLayoutManager);

        productSepcificationAdaptor psa=new productSepcificationAdaptor(product_specification_modelList);

        productspecificationRecyclerview.setAdapter(psa);
        psa.notifyDataSetChanged();
        return view;
    }
}
