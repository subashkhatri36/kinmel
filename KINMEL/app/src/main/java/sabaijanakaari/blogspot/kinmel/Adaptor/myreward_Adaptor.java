package sabaijanakaari.blogspot.kinmel.Adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sabaijanakaari.blogspot.kinmel.Model.reward_Model;
import sabaijanakaari.blogspot.kinmel.ProductdetailActivity;
import sabaijanakaari.blogspot.kinmel.R;

public class myreward_Adaptor extends RecyclerView.Adapter<myreward_Adaptor.viewHolder> {
    private List<reward_Model> reward_modelList;
    private boolean usedminilayout=false;

    public myreward_Adaptor(List<reward_Model> reward_modelList,boolean usedminilayout) {
        this.reward_modelList = reward_modelList;
        this.usedminilayout=usedminilayout;
    }

    @Override
    public myreward_Adaptor.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(usedminilayout){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mini_reward_item_layout,parent,false);
        }else {
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rewards_item_layout,parent,false);
        }

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(myreward_Adaptor.viewHolder holder, int position) {
        String title=reward_modelList.get(position).getRewardTitle();
        String expery=reward_modelList.get(position).getExperyDate();
        String body=reward_modelList.get(position).getRewardBody();
        holder.setData(title,expery,body);
    }

    @Override
    public int getItemCount() {
        return reward_modelList.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder{

        private TextView coupenTitle,coupenexperidate,coupenbody;
        public viewHolder(View itemView) {
            super(itemView);
            coupenTitle=itemView.findViewById(R.id.coupentitle);
            coupenexperidate=itemView.findViewById(R.id.coupenValiditi);
            coupenbody=itemView.findViewById(R.id.coupenbody);
        }
        private void setData(final String title, final String expery, final String body){
            coupenTitle.setText(title);
            coupenexperidate.setText(expery);
            coupenbody.setText(body);
            if(usedminilayout){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProductdetailActivity.coupenTitle.setText(title);
                        ProductdetailActivity.coupenexperidate.setText(expery);
                        ProductdetailActivity.coupenbody.setText(body);
                        ProductdetailActivity.showDialogRecyclerview();
                    }
                });
            }

        }
    }
}
