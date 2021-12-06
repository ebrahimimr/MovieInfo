package ir.mreonline.movieinfo;

import static androidx.core.content.ContextCompat.startActivity;
import static androidx.core.content.ContextCompat.startForegroundService;

import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import ir.mreonline.movieinfo.models.MovieSearch;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.viewHolder> {

    MovieSearch moviesearch;
    Integer mode;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    RecyclerAdapter(MovieSearch V,Integer M) {
        moviesearch = V;
        mode= M;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_movie_item, parent, false);
        viewHolder holder = new viewHolder(v);
        //holder.container.setOnClickListener(new View.OnClickListener() {

            //@Override
            //public void onClick(View v) {
                //mOnItemClickListener.onItemClick(v, holder.getAdapterPosition());
            //}
        //});


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        try {

            holder.txtTitle.setText("Title : "+ moviesearch.getSearch().get(position).getTitle());
            holder.txtType.setText("Type : "+ moviesearch.getSearch().get(position).getType());
            holder.txtImdbID.setText("ImdbID : "+ moviesearch.getSearch().get(position).getImdbID());
            holder.txtYear.setText("Year : "+ moviesearch.getSearch().get(position).getYear());
            Glide.with(holder.itemView).load(moviesearch.getSearch().get(position).getPoster().toString()).into(holder.imgPoster);

            holder.crdView.setOnClickListener(new View.OnClickListener() {
                Intent intent = new Intent(holder.container.getContext(), MovieDetailActivity.class);
                @Override
                public void onClick(View v) {

                    Integer pos = holder.getAdapterPosition();
                    intent.putExtra("imdbID",moviesearch.getSearch().get(pos).getImdbID());
                    intent.putExtra("mode",mode);
                    startActivity(holder.container.getContext(),intent,null);
                }
            });
        } catch (Exception ex) {
        }

    }


    @Override
    public int getItemCount() {
        Integer size = 0;
        try {
            size = moviesearch.getSearch().size();
        } catch (Exception ex) {
        }

        return size;
    }

    class viewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtYear;
        TextView txtImdbID;
        TextView txtType;
        ImageView imgPoster;
        public View container;
        CardView crdView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtYear = itemView.findViewById(R.id.txtYear);
            txtImdbID = itemView.findViewById(R.id.txtImdbID);
            txtType = itemView.findViewById(R.id.txtType);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            crdView = itemView.findViewById(R.id.crdView);
            container = itemView;
        }
    }
}

