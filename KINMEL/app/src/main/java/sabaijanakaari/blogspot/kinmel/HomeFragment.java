package sabaijanakaari.blogspot.kinmel;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import sabaijanakaari.blogspot.kinmel.Adaptor.CategoryMenuAdaptor;
import sabaijanakaari.blogspot.kinmel.Adaptor.HomePageAdaptor;
import sabaijanakaari.blogspot.kinmel.Database.DBquery;
import sabaijanakaari.blogspot.kinmel.Model.CategoryMenuModel;
import sabaijanakaari.blogspot.kinmel.Model.HomepageModel;
import sabaijanakaari.blogspot.kinmel.Model.Whistlist_Model;
import sabaijanakaari.blogspot.kinmel.Model.banner_Slider_Model;
import sabaijanakaari.blogspot.kinmel.Model.horizental_product_Scroll_Model;

import static sabaijanakaari.blogspot.kinmel.Database.DBquery.categoryMenuModels;
import static sabaijanakaari.blogspot.kinmel.Database.DBquery.lists;
import static sabaijanakaari.blogspot.kinmel.Database.DBquery.loadCategories;
import static sabaijanakaari.blogspot.kinmel.Database.DBquery.loadFragmentData;
import static sabaijanakaari.blogspot.kinmel.Database.DBquery.loadedCategoriesName;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    //Variable Defination
    ImageView noInternetConnection;
    RecyclerView categoryMenuRecyler;
    View view;
    CategoryMenuAdaptor categoryMenuAdaptor;
    RecyclerView homepageRecyclerview;
    private List<HomepageModel> fakehomepagelist=new ArrayList<>();
    public static SwipeRefreshLayout refreshLayout;
    HomePageAdaptor adaptor;

    Button refreshButton;

    //fake list
    private List<CategoryMenuModel> categoryfakeList=new ArrayList<>();
    //fake list

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    ////Banner Slider View Pager
    private List<banner_Slider_Model> bannerSlideModelList;
///Banner Slider View bapge


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home, container, false);
        noInternetConnection=view.findViewById(R.id.no_internet_connection);
        refreshLayout=view.findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeColors(getContext().getResources().getColor(R.color.colorPrimary),getContext().getResources().getColor(R.color.colorPrimary),getContext().getResources().getColor(R.color.colorPrimary));

        categoryMenuRecyler=(RecyclerView)view.findViewById(R.id.category_recyclerview_menu);
        refreshButton=view.findViewById(R.id.refreshbutton);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryMenuRecyler.setLayoutManager(layoutManager);


        homepageRecyclerview=view.findViewById(R.id.homepage_recyclerview);
        LinearLayoutManager testingLayoutManager=new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homepageRecyclerview.setLayoutManager(testingLayoutManager);

        //fake category list
        categoryfakeList.add(new CategoryMenuModel("null",""));
        categoryfakeList.add(new CategoryMenuModel("",""));
        categoryfakeList.add(new CategoryMenuModel("",""));
        categoryfakeList.add(new CategoryMenuModel("",""));
        categoryfakeList.add(new CategoryMenuModel("",""));
        categoryfakeList.add(new CategoryMenuModel("",""));
        categoryfakeList.add(new CategoryMenuModel("",""));
        categoryfakeList.add(new CategoryMenuModel("",""));
        categoryfakeList.add(new CategoryMenuModel("",""));


        //fake category list

        //fake homepage list
        List<banner_Slider_Model> fakebannerSlider=new ArrayList<>();
        fakebannerSlider.add(new banner_Slider_Model("null","#dfdfdf"));
        fakebannerSlider.add(new banner_Slider_Model("null","#dfdfdf"));
        fakebannerSlider.add(new banner_Slider_Model("null","#dfdfdf"));

        List<horizental_product_Scroll_Model> fakehorizentalmodellist=new ArrayList<>();
        fakehorizentalmodellist.add(new horizental_product_Scroll_Model("","","","",""));
        fakehorizentalmodellist.add(new horizental_product_Scroll_Model("","","","",""));
        fakehorizentalmodellist.add(new horizental_product_Scroll_Model("","","","",""));
        fakehorizentalmodellist.add(new horizental_product_Scroll_Model("","","","",""));
        fakehorizentalmodellist.add(new horizental_product_Scroll_Model("","","","",""));

        fakehomepagelist.add(new HomepageModel(0,fakebannerSlider));
        fakehomepagelist.add(new HomepageModel(1,"","#dfdfdf"));
        fakehomepagelist.add(new HomepageModel(2,"","#dfdfdf",fakehorizentalmodellist,new ArrayList<Whistlist_Model>()));
        fakehomepagelist.add(new HomepageModel(3,"","#dfdfdf",fakehorizentalmodellist));
        //fake homepage list

        adaptor=new HomePageAdaptor(fakehomepagelist);
        connectivityManager=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo=connectivityManager.getActiveNetworkInfo();


        if(networkInfo!=null && networkInfo.isConnected()==true){
            MainActivity.drawer.setDrawerLockMode(MainActivity.drawer.LOCK_MODE_UNLOCKED);
           // MainActivity.drawer.setDrawerLockMode(0);
            noInternetConnection.setVisibility(View.GONE);
            refreshButton.setVisibility(View.GONE);
            categoryMenuRecyler.setVisibility(View.VISIBLE);
            homepageRecyclerview.setVisibility(View.VISIBLE);
            categoryMenuAdaptor=new CategoryMenuAdaptor(categoryfakeList);

            if(categoryMenuModels.size()==0){
                loadCategories(categoryMenuRecyler,getContext());
            }else{
                categoryMenuAdaptor=new CategoryMenuAdaptor(categoryMenuModels);
                categoryMenuAdaptor.notifyDataSetChanged();
            }
            categoryMenuRecyler.setAdapter(categoryMenuAdaptor);

            if(lists.size()==0){
                loadedCategoriesName.add("HOME");
                lists.add(new ArrayList<HomepageModel>());

                loadFragmentData(homepageRecyclerview,getContext(),0,"HOME");
            }else{
                adaptor=new HomePageAdaptor(lists.get(0));
                adaptor.notifyDataSetChanged();
            }
            homepageRecyclerview.setAdapter(adaptor);
            ////End Home Page Adaptor

        }else{
           // MainActivity.drawer.setDrawerLockMode(1);
            MainActivity.drawer.setDrawerLockMode(MainActivity.drawer.LOCK_MODE_LOCKED_CLOSED);
            categoryMenuRecyler.setVisibility(View.GONE);
            homepageRecyclerview.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.loading).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
            refreshButton.setVisibility(View.VISIBLE);
        }
        ////refreshlayout
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                reLoadPage();

            }
        });
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reLoadPage();
            }
        });

        // refreshlayout

        return view;
    }

    private void reLoadPage() {
        DBquery.ClearData();
        connectivityManager=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo=connectivityManager.getActiveNetworkInfo();
        //checking network
        if(networkInfo!=null && networkInfo.isConnected()==true){
            noInternetConnection.setVisibility(View.GONE);
            refreshButton.setVisibility(View.GONE);
            categoryMenuRecyler.setVisibility(View.VISIBLE);
            homepageRecyclerview.setVisibility(View.VISIBLE);

            categoryMenuAdaptor =new CategoryMenuAdaptor(categoryfakeList);
            adaptor=new HomePageAdaptor(fakehomepagelist);
            categoryMenuRecyler.setAdapter(categoryMenuAdaptor);
            homepageRecyclerview.setAdapter(adaptor);

            loadCategories(categoryMenuRecyler,getContext());
            lists.add(new ArrayList<HomepageModel>());
            loadFragmentData(homepageRecyclerview,getContext(),0,"HOME");

           // MainActivity.drawer.setDrawerLockMode(0);
            MainActivity.drawer.setDrawerLockMode(MainActivity.drawer.LOCK_MODE_UNLOCKED);
        }
        else {
            categoryMenuRecyler.setVisibility(View.GONE);
            homepageRecyclerview.setVisibility(View.GONE);
            Glide.with(getContext()).load(R.drawable.loading).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
            refreshButton.setVisibility(View.VISIBLE);
            refreshLayout.setRefreshing(false);
            Toast.makeText(getContext(),"No Internet Connnection Available",Toast.LENGTH_SHORT).show();
          // MainActivity.drawer.setDrawerLockMode(1);
            MainActivity.drawer.setDrawerLockMode(MainActivity.drawer.LOCK_MODE_LOCKED_CLOSED);

        }

    }

}
