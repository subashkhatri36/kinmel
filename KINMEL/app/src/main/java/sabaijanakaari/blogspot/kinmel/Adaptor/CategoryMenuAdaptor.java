package sabaijanakaari.blogspot.kinmel.Adaptor;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import sabaijanakaari.blogspot.kinmel.CategoryActivity;
import sabaijanakaari.blogspot.kinmel.Model.CategoryMenuModel;
import sabaijanakaari.blogspot.kinmel.R;

public class CategoryMenuAdaptor extends RecyclerView.Adapter<CategoryMenuAdaptor.viewHolder> {
    //Variable List
    private List<CategoryMenuModel> categoryMenuModelList;
    int lastposition=-1;

    public CategoryMenuAdaptor(List<CategoryMenuModel> categoryMenuModelList) {
        this.categoryMenuModelList = categoryMenuModelList;
    }

    @Override
    public CategoryMenuAdaptor.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_layout,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryMenuAdaptor.viewHolder holder, int position) {
        String Icon=categoryMenuModelList.get(position).getCategoryIconlink();
        String name=categoryMenuModelList.get(position).getCategoryname();
        holder.setCategoryIcon(Icon);
        holder.setCategory(name,position);
        if(lastposition<position){
            Animation animation= AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.fading);
            holder.itemView.setAnimation(animation);
            lastposition=position;
        }
    }

    @Override
    public int getItemCount() {
        return categoryMenuModelList.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder{
        private ImageView categoryIcon;
        private TextView categoryName;
        public viewHolder(View itemView) {
            super(itemView);
            categoryIcon=(ImageView)itemView.findViewById(R.id.category_icon);
            categoryName=(TextView) itemView.findViewById(R.id.category_name);
        }
        private void setCategoryIcon(String Iconurl){
            if(!Iconurl.equals("null")){
                Glide.with(itemView.getContext()).load(Iconurl).apply(new RequestOptions().placeholder(R.drawable.placeholderimge)).into(categoryIcon);
            }else {
                categoryIcon.setImageResource(R.drawable.ic_home_black_24dp);
            }

        }
        private void setCategory(final String name, final int position){
            categoryName.setText(name);
            if(!name.equals("")){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(position!=0){
                            Intent categoryINtent=new Intent(itemView.getContext(), CategoryActivity.class);
                            categoryINtent.putExtra("CategoryName",name);
                            itemView.getContext().startActivity(categoryINtent);
                        }
                    }
                });
            }


        }
    }
}
