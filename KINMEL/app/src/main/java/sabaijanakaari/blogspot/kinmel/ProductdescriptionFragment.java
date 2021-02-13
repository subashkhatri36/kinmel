package sabaijanakaari.blogspot.kinmel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductdescriptionFragment extends Fragment {
    TextView tv_description;
    public String body;

    public ProductdescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_productdescription, container, false);
        tv_description=v.findViewById(R.id.tv_product_description);
        tv_description.setText(body);

        return v;
    }
}
