package sk3m3l1io.duisburg.memogame.view.score;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import sk3m3l1io.duisburg.memogame.R;
import sk3m3l1io.duisburg.memogame.model.pojos.Player;
import sk3m3l1io.duisburg.memogame.model.pojos.ScoreData;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {
    private Player user;
    private List<ScoreData> scores;
    private final Context context;
    private ScoreView.OnScoreDetailsClickListener itemClickListener;

    public ScoreAdapter(Context context) {
        this.context = context;
    }

    public void setScores(List<ScoreData> scores) {
        this.scores = scores;
    }

    public void setUser(Player user) {
        this.user = user;
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater i = LayoutInflater.from(context);
        View v = i.inflate(R.layout.item_score, parent, false);
        return new ScoreViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder vh, int i) {
        vh.clearCachedViews();
        vh.bind(scores.get(i));
    }

    @Override
    public int getItemCount() {
        if (scores != null && scores.size() > 0) {
            return scores.size();
        }

        return 0;
    }

    public void setOnScoreDetailsClickListener(ScoreView.OnScoreDetailsClickListener listener) {
        itemClickListener = listener;
    }

    class ScoreViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, rank, points;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                if (itemClickListener != null)
                    itemClickListener.onScoreDetailsClicked(getAdapterPosition());
            });
            name = itemView.findViewById(R.id.name);
            rank = itemView.findViewById(R.id.rank);
            points = itemView.findViewById(R.id.points);
            image = itemView.findViewById(R.id.image);
        }

        void bind(ScoreData s) {
            name.setText(s.getPlayer().getName());
            rank.setText("" + (getAdapterPosition() + 1));
            points.setText("" + s.getTotalPoints());

            if (isUser(s.getPlayer()))
                updateUserUi();

            if (s.getPlayer().getPhotoUrl() != null)
                loadImage(s.getPlayer().getPhotoUrl());
        }

        void clearCachedViews() {
            Glide.with(image).clear(image);
        }

        private boolean isUser(Player p) {
            return user != null && user.getId().equals(p.getId());
        }

        private void updateUserUi() {
            MaterialCardView card = (MaterialCardView) itemView;
            int color = context.getResources().getColor(R.color.secondaryColor100);
            card.setCardBackgroundColor(color);
            name.setTypeface(Typeface.DEFAULT_BOLD);
        }

        private void loadImage(String url) {
            Glide.with(image).load(url).placeholder(R.drawable.user_male).into(image);
        }
    }
}
