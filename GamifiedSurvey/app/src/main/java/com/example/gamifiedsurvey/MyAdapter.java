package com.example.gamifiedsurvey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class MyAdapter extends FirebaseRecyclerAdapter<LeaderBoard, MyAdapter.MyViewHolder> {
int i = 0;

     @Override
     public int getItemCount() {
         return super.getItemCount( );
     }

     /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyAdapter(@NonNull FirebaseRecyclerOptions<LeaderBoard> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder,int position,@NonNull LeaderBoard leaderBoard) {
        holder.Name.setText(leaderBoard.getName());
        holder.Scores.setText(Integer.toString((int) leaderBoard.getScores()));
//        holder.Rank.setText((int) position);

        //holder.Rank.setText(Integer.toString(getItemCount()-i));
        holder.Rank.setText(Integer.toString( getItemCount()- position));
        //i++;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.lblist_layout,parent,false);
        return new MyViewHolder(view);

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name;
        TextView Scores;
        TextView Rank;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.NameTxt);
            Scores = itemView.findViewById(R.id.ScoreTxt);
            Rank = itemView.findViewById(R.id.rankTxt);


        }
    }
}
