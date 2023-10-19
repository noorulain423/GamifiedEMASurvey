package com.example.gamifiedsurvey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


class MyAdapterLocationList extends FirebaseRecyclerAdapter <Locations, MyAdapterLocationList.MyViewHolder2> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyAdapterLocationList(@NonNull FirebaseRecyclerOptions<Locations> options) {
        super(options);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount( );
    }

    @Override
    protected void onBindViewHolder(@NonNull MyAdapterLocationList.MyViewHolder2 holder,int position,@NonNull Locations earlierMoments) {
        holder.address.setText(earlierMoments.getAddress());
        holder.steps.setText(earlierMoments.getSteps());
        holder.mobile_usage.setText(earlierMoments.getMobile_usage());

    }




    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.locationlist,parent,false);
        return new MyViewHolder2(view);

    }


    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        TextView address;
        TextView steps;
        TextView mobile_usage;
        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.txtloc);
            steps = itemView.findViewById(R.id.txtsteps);
            mobile_usage = itemView.findViewById(R.id.txtusage);

        }
    }
}
