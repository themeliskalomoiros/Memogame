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
        vh.bind(scores.get(i));
    }

    @Override
    public int getItemCount() {
        if (scores != null && scores.size() > 0) {
            return scores.size();
        }

        return 0;
    }

    class ScoreViewHolder extends RecyclerView.ViewHolder {
        TextView name, rank, points;
        ImageView image, medal, leftBadge, rightBadge;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            rank = itemView.findViewById(R.id.rank);
            points = itemView.findViewById(R.id.points);
            image = itemView.findViewById(R.id.image);
            medal = itemView.findViewById(R.id.medal);
            leftBadge = itemView.findViewById(R.id.badge_left);
            rightBadge = itemView.findViewById(R.id.badge_right);
        }

        void bind(ScoreData s) {
            name.setText(s.getPlayer().getName());
            rank.setText("" + (getAdapterPosition() + 1));
            points.setText("" + s.getTotalPoints());

            if (isUser(s.getPlayer()))
                updateUserUi();

            if (s.getPlayer().getPhotoUrl() != null)
                loadImage(s.getPlayer().getPhotoUrl());

            if (isMedalWinner())
                updateMedalUi();

            if (s.isTimeCompleted())
                leftBadge.setVisibility(View.VISIBLE);

            if (s.isSurvivalCompleted())
                rightBadge.setVisibility(View.VISIBLE);
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
            Glide.with(context).load(url).placeholder(R.drawable.user_male).into(image);
        }

        private boolean isMedalWinner() {
            int pos = getAdapterPosition();
            return pos == 0 || pos == 1 || pos == 2;
        }

        private void updateMedalUi() {
            if (getAdapterPosition() == 0) {
                medal.setImageResource(R.drawable.medal_first_place);
            } else if (getAdapterPosition() == 1) {
                medal.setImageResource(R.drawable.medal_second_place);
            } else if (getAdapterPosition() == 2) {
                medal.setImageResource(R.drawable.medal_third_place);
            }

            medal.setVisibility(View.VISIBLE);
        }
    }
}
