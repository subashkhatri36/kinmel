package sabaijanakaari.blogspot.kinmel.Adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sabaijanakaari.blogspot.kinmel.Database.DBquery;
import sabaijanakaari.blogspot.kinmel.DeliveryActivity;
import sabaijanakaari.blogspot.kinmel.Model.addresses_Model;
import sabaijanakaari.blogspot.kinmel.MyaccountFragment;
import sabaijanakaari.blogspot.kinmel.MyaddressesActivity;
import sabaijanakaari.blogspot.kinmel.R;

public class Addresses_Adaptor extends RecyclerView.Adapter<Addresses_Adaptor.viewHolder> {
    List<addresses_Model> addresses_modelList;
    private int Mode;
    private int preSelecedPosition;

    public Addresses_Adaptor(List<addresses_Model> addresses_modelList, int mode) {
        this.addresses_modelList = addresses_modelList;
        this.Mode = mode;
        preSelecedPosition= DBquery.selectedaddress;
    }

    @Override
    public Addresses_Adaptor.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addresses_item_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(Addresses_Adaptor.viewHolder holder, int position) {
        String name = addresses_modelList.get(position).getFullname();
        String add = addresses_modelList.get(position).getAddress();
        String pin = addresses_modelList.get(position).getPinCode();
        boolean selected = addresses_modelList.get(position).getSelected();
        String mobileno=addresses_modelList.get(position).getMobileno();
        holder.setData(name, add, pin, selected, position,mobileno);
    }

    @Override
    public int getItemCount() {
        return addresses_modelList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView fullname, address, pincode;
        ImageView icon;
        private LinearLayout optionContainer;

        public viewHolder(View itemView) {
            super(itemView);
            fullname = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            pincode = itemView.findViewById(R.id.pinCode);
            icon = itemView.findViewById(R.id.Icon_view);
            optionContainer = itemView.findViewById(R.id.option_container);


        }

        private void setData(String name, String addrss, String pinCode, boolean selected, final int position,final String mobileno) {

            fullname.setText(name+"-"+mobileno);
            address.setText(addrss);
            pincode.setText(pinCode);
            if (Mode == DeliveryActivity.SElECT_ADDRESS) {
                icon.setImageResource(R.drawable.ic_check_black_24dp);
                if (selected) {
                    icon.setVisibility(View.VISIBLE);
                    preSelecedPosition = position;

                } else {
                    icon.setVisibility(View.GONE);
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (preSelecedPosition != position) {
                            addresses_modelList.get(position).setSelected(true);
                            addresses_modelList.get(preSelecedPosition).setSelected(false);
                           // reFpreSelecedPosition=position;
                            MyaddressesActivity.reFresh_ITems(preSelecedPosition,position);
                            preSelecedPosition = position;
                            DBquery.selectedaddress=position;
                        }


                    }
                });
            } else if (Mode == MyaccountFragment.Manage_Address) {
                optionContainer.setVisibility(View.GONE);
                icon.setVisibility(View.VISIBLE);
                icon.setImageResource(R.drawable.ic_more_vert_black_24dp);
                icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionContainer.setVisibility(View.VISIBLE);
                        // reFresh_ITems(preSelecedPosition,preSelecedPosition);
                        preSelecedPosition = position;
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  reFresh_ITems(preSelecedPosition,preSelecedPosition);
                        preSelecedPosition = -1;
                    }
                });
            }
        }
    }
}
