package sabaijanakaari.blogspot.kinmel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import sabaijanakaari.blogspot.kinmel.Adaptor.HomePageAdaptor;
import sabaijanakaari.blogspot.kinmel.Database.DBquery;
import sabaijanakaari.blogspot.kinmel.Model.HomepageModel;
import sabaijanakaari.blogspot.kinmel.Model.Whistlist_Model;
import sabaijanakaari.blogspot.kinmel.Model.banner_Slider_Model;
import sabaijanakaari.blogspot.kinmel.Model.horizental_product_Scroll_Model;

public class CategoryActivity extends AppCompatActivity {
    RecyclerView CategorRecyclerview;
    HomePageAdaptor adaptor;

    private List<HomepageModel> fakehomepagelist=new ArrayList<>();


    //banner slider List
    private List<banner_Slider_Model> bannerSlideModelList;
    //banner slider list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        String title=getIntent().getStringExtra("CategoryName");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //

        CategorRecyclerview=(RecyclerView)findViewById(R.id.category_recyclerView);
        LinearLayoutManager testingLayoutManager=new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        CategorRecyclerview.setLayoutManager(testingLayoutManager);



        //fake homepage list
        List<banner_Slider_Model> fakebannerSlider=new ArrayList<>();
        fakebannerSlider.add(new banner_Slider_Model("null","#ffffff"));
        fakebannerSlider.add(new banner_Slider_Model("null","#ffffff"));
        fakebannerSlider.add(new banner_Slider_Model("null","#ffffff"));

        List<horizental_product_Scroll_Model> fakehorizentalmodellist=new ArrayList<>();
        fakehorizentalmodellist.add(new horizental_product_Scroll_Model("","","","",""));
        fakehorizentalmodellist.add(new horizental_product_Scroll_Model("","","","",""));
        fakehorizentalmodellist.add(new horizental_product_Scroll_Model("","","","",""));
        fakehorizentalmodellist.add(new horizental_product_Scroll_Model("","","","",""));
        fakehorizentalmodellist.add(new horizental_product_Scroll_Model("","","","",""));

        fakehomepagelist.add(new HomepageModel(0,fakebannerSlider));
        fakehomepagelist.add(new HomepageModel(1,"","#ffffff"));
        fakehomepagelist.add(new HomepageModel(2,"","#ffffff",fakehorizentalmodellist,new ArrayList<Whistlist_Model>()));
        fakehomepagelist.add(new HomepageModel(3,"","#ffffff",fakehorizentalmodellist));
        //fake homepage list

        adaptor=new HomePageAdaptor(fakehomepagelist);


        //=new HomePageAdaptor(homepageModelList);
        int listposititon=0;

        for(int x=0;x< DBquery.loadedCategoriesName.size();x++){
            if(DBquery.loadedCategoriesName.get(x).equals(title.toUpperCase())){
                listposititon=x;
            }
        }
        if(listposititon==0){
            DBquery.loadedCategoriesName.add(title.toUpperCase());
            DBquery.lists.add(new ArrayList<HomepageModel>());
            //adaptor=new HomePageAdaptor(lists.get(loadedCategoriesName.size()-1));
            DBquery.loadFragmentData(CategorRecyclerview,this,DBquery.loadedCategoriesName.size()-1,title.toUpperCase());

        }else {
            adaptor=new HomePageAdaptor(DBquery.lists.get(listposititon));
            //loadFragmentData(adaptor,this,loadedCategoriesName.size()-1);
        }
        CategorRecyclerview.setAdapter(adaptor);
        adaptor.notifyDataSetChanged();



        /* if(lists.size()==0){
                loadedCategoriesName.add("HOME");
                lists.add(new ArrayList<HomepageModel>());

                loadFragmentData(homepageRecyclerview,getContext(),0,"HOME");
            }else{
                adaptor=new HomePageAdaptor(lists.get(0));
                adaptor.notifyDataSetChanged();
            }
            homepageRecyclerview.setAdapter(adaptor);
        *
        * */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.main_search_icon) {
            //Search Icon
            return true;
        }else if(id==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

