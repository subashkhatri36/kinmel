package sabaijanakaari.blogspot.kinmel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import sabaijanakaari.blogspot.kinmel.Adaptor.GridProductlayoutAdaptor;
import sabaijanakaari.blogspot.kinmel.Adaptor.Whislist_Adaptor;
import sabaijanakaari.blogspot.kinmel.Model.Whistlist_Model;
import sabaijanakaari.blogspot.kinmel.Model.horizental_product_Scroll_Model;

public class ViewallActivity extends AppCompatActivity {
    Toolbar toolbar;
    private RecyclerView detail_recyclerview;
    private GridView detail_Gridview;
    int layoutCode=-1;
    public static List<Whistlist_Model> whistlist_modelList=new ArrayList<>();

    public static List<horizental_product_Scroll_Model> horizental_product_scroll_models;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewall);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));


        layoutCode=getIntent().getIntExtra("layoutCode",-1);
        //viewallIntent.putExtra("title",titte);
        if(layoutCode==0){
            //Horizental Adaptor

            detail_recyclerview=findViewById(R.id.recyclerview_viewlla);

            detail_recyclerview.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager=new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            detail_recyclerview.setLayoutManager(layoutManager);


            Whislist_Adaptor adaptor=new Whislist_Adaptor(whistlist_modelList,false);
            detail_recyclerview.setAdapter(adaptor);
            adaptor.notifyDataSetChanged();
            //Horizental Adaptor

        }else if(layoutCode==1){
            //Grid view Code
            detail_Gridview=findViewById(R.id.gridview_viewall);
            detail_Gridview.setVisibility(View.VISIBLE);

            GridProductlayoutAdaptor gridProductlayoutAdaptor=new GridProductlayoutAdaptor(horizental_product_scroll_models);

            detail_Gridview.setAdapter(gridProductlayoutAdaptor);
            //Grid view Code
        }




    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            //Search Icon
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
