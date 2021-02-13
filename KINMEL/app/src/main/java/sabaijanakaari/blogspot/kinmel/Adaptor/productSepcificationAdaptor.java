package sabaijanakaari.blogspot.kinmel.Adaptor;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sabaijanakaari.blogspot.kinmel.Model.product_specification_Model;
import sabaijanakaari.blogspot.kinmel.R;

public class productSepcificationAdaptor extends RecyclerView.Adapter<productSepcificationAdaptor.ViewHolder> {

    List<product_specification_Model> product_specification_modelList;


    public productSepcificationAdaptor(List<product_specification_Model> product_specification_modelList) {
        this.product_specification_modelList = product_specification_modelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (product_specification_modelList.get(position).getType()) {
            case 0:
                return product_specification_Model.PRODUCT_SPECIFICATION_TITLE;
            case 1:
                return product_specification_Model.PRODUCT_SPECIFICATION_BODY;
            default:
                return -1;
        }
    }

    @Override
    public productSepcificationAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case product_specification_Model.PRODUCT_SPECIFICATION_TITLE:
                TextView title = new TextView(parent.getContext());
                title.setTypeface(null, Typeface.BOLD);
                title.setTextColor(Color.parseColor("#000000"));
                LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutparams.setMargins(setdp(16, parent.getContext()), setdp(16, parent.getContext()),
                        setdp(16, parent.getContext()), setdp(8, parent.getContext()));
                title.setLayoutParams(layoutparams);
                return new ViewHolder(title);

            case product_specification_Model.PRODUCT_SPECIFICATION_BODY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_specification_item_layout, parent, false);
                return new ViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(productSepcificationAdaptor.ViewHolder holder, int position) {

        switch (product_specification_modelList.get(position).getType()) {
            case product_specification_Model.PRODUCT_SPECIFICATION_TITLE:
                holder.setTitle(product_specification_modelList.get(position).getTitle());
                break;

            case product_specification_Model.PRODUCT_SPECIFICATION_BODY:
                String name = product_specification_modelList.get(position).getFeatureName();
                String value = product_specification_modelList.get(position).getFeatureValue();
                holder.setValues(name, value);
                break;
            default:
                return;
        }


    }

    @Override
    public int getItemCount() {
        return product_specification_modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView featurename, featurevalue;
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);

        }

        private void setTitle(String titletext) {
            title = (TextView) itemView;
            title.setText(titletext);
        }

        private void setValues(String name, String value) {
            featurename = (TextView) itemView.findViewById(R.id.featurename);
            featurevalue = (TextView) itemView.findViewById(R.id.featurevalue);
            featurename.setText(name);
            featurevalue.setText(value);
        }
    }

    private int setdp(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}