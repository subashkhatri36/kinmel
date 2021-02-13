package sabaijanakaari.blogspot.kinmel.Adaptor;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

import sabaijanakaari.blogspot.kinmel.Model.product_specification_Model;
import sabaijanakaari.blogspot.kinmel.ProdcutspecificationFragment;
import sabaijanakaari.blogspot.kinmel.ProductdescriptionFragment;

public class product_details_Adaptor extends FragmentPagerAdapter {

    private int totaltabs;
    private String product_description;
    private String product_otherdetail;
    private List<product_specification_Model> productSpecificationModelList;

    public product_details_Adaptor(FragmentManager fm, int totaltabs, String product_description, String product_otherdetail, List<product_specification_Model> productSpecificationModelList) {
        super(fm);
        this.totaltabs = totaltabs;
        this.product_description = product_description;
        this.product_otherdetail = product_otherdetail;
        this.productSpecificationModelList = productSpecificationModelList;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ProductdescriptionFragment productdescriptionFragment = new ProductdescriptionFragment();
               productdescriptionFragment.body = product_description;
               return productdescriptionFragment;
            case 1:
                ProdcutspecificationFragment prodcutspecificationFragment = new ProdcutspecificationFragment();
                prodcutspecificationFragment.product_specification_modelList = productSpecificationModelList;
                return prodcutspecificationFragment;
            case 2:
                ProductdescriptionFragment productdescriptionFragment1 = new ProductdescriptionFragment();
                productdescriptionFragment1.body = product_otherdetail;
                return productdescriptionFragment1;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totaltabs;
    }
}