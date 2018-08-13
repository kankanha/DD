package com.example.pooja.doordashinterview.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pooja.doordashinterview.R;
import com.example.pooja.doordashinterview.Interface.RecyclerViewCLickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantInfoAdapter extends RecyclerView.Adapter<RestaurantInfoAdapter.RestaurantInfoViewHolder> {

    List<RestaurantListItem> restaurantItemList;
    private Context context;

    RecyclerViewCLickListener recyclerVwClickListener;

    public class RestaurantInfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView restImgVw;
        TextView txtTitleName;
        TextView txtDescription;
        TextView txtStatus;



        public RestaurantInfoViewHolder(View itemView) {
            super(itemView);

            restImgVw = (ImageView) itemView.findViewById(R.id.imgRestVw);
            txtTitleName = (TextView) itemView.findViewById(R.id.txtName);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            recyclerVwClickListener.OnRecyclerItemClicked(restaurantItemList.get(clickedPosition).restId,view.getId());
        }
    }

    public RestaurantInfoAdapter(Context context,RecyclerViewCLickListener listener)
    {
        this.context = context;
        this.recyclerVwClickListener = listener;
    }

    @Override
    public RestaurantInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new RestaurantInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RestaurantInfoViewHolder holder, int position) {
        holder.txtTitleName.setText(restaurantItemList.get(position).restName);
        holder.txtDescription.setText(restaurantItemList.get(position).restDescription);
        holder.txtStatus.setText(restaurantItemList.get(position).restStatus);
        String imgPath = restaurantItemList.get(position).restImg;
        if(!imgPath.isEmpty() && imgPath!=null)
            Picasso.with(context).load(imgPath).resize(150,90).into(holder.restImgVw);
        else
            Picasso.with(context).load(R.mipmap.ic_launcher).resize(150,90).into(holder.restImgVw);
    }

    @Override
    public int getItemCount() {
        if(restaurantItemList==null) return 0;
        else
        return restaurantItemList.size();
    }

   public void PopulateRestaurantList(List<RestaurantListItem> items)
   {
       restaurantItemList = items;
       notifyDataSetChanged();
   }

   public void SetRecyclerViewOnClickListener(RecyclerViewCLickListener listener)
   {
       recyclerVwClickListener = listener;
   }
}
