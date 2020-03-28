package sk3m3l1io.kalymnos.memogame.view.score;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import sk3m3l1io.kalymnos.memogame.R;
import sk3m3l1io.kalymnos.memogame.pojos.Player;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {
    private static final int TYPE_NUMERIC = 1313;
    private static final int TYPE_MEDAL = 1314;

    private final Context context;
    private int[] scores;
    private Player[] players;
    private Player user;

    public ScoreAdapter(Context context) {
        this.context = context;
    }

    public void setScores(int[] scores) {
        this.scores = scores;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_MEDAL) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_player_score_medal, parent, false);
            return new MedalViewHolder(v);
        } else {
            View v = LayoutInflater.from(context).inflate(R.layout.item_player_score_numeric, parent, false);
            return new NumericViewHolder(v);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == 1 || position == 2) {
            return TYPE_MEDAL;
        }

        return TYPE_NUMERIC;
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder vh, int i) {
        Player p = players[i];
        int s = scores[i];
        vh.bind(s, p);
    }

    @Override
    public int getItemCount() {
        if (scores != null && players != null)
            if (scores.length > 0 && players.length == scores.length)
                return scores.length;

        return 0;
    }

    public void setUser(Player user) {
        this.user = user;
    }

    abstract class ScoreViewHolder extends RecyclerView.ViewHolder {
        TextView name, score;
        ImageView image;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.user_name);
            score = itemView.findViewById(R.id.user_score);
            image = itemView.findViewById(R.id.user_image);
        }

        void bind(int s, Player p) {
            name.setText(p.getName());
            boolean isUser = p.getId().equals(user.getId());
            if (isUser){
                name.setTextColor(context.getResources().getColor(R.color.symbolMatchColor));
                name.setTypeface(Typeface.DEFAULT_BOLD);
            }
            score.setText("" + s);
            if (p.getPhotoUrl() != null) {
                Picasso.get()
                        .load(p.getPhotoUrl())
                        .placeholder(R.drawable.user_48px)
                        .error(R.drawable.ic_error_outline_black_48dp)
                        .into(image);
            }
        }
    }

    class NumericViewHolder extends ScoreViewHolder {
        TextView position;

        public NumericViewHolder(@NonNull View itemView) {
            super(itemView);
            position = itemView.findViewById(R.id.user_position);
        }

        @Override
        void bind(int s, Player p) {
            super.bind(s, p);
            position.setText("" + (getAdapterPosition() + 1));
        }
    }

    class MedalViewHolder extends ScoreViewHolder {
        ImageView medal;

        public MedalViewHolder(@NonNull View itemView) {
            super(itemView);
            medal = itemView.findViewById(R.id.medal);
        }

        @Override
        void bind(int s, Player p) {
            super.bind(s, p);
            if (getAdapterPosition() == 0)
                medal.setImageResource(R.drawable.medal_first_place_48px);
            if (getAdapterPosition() == 1)
                medal.setImageResource(R.drawable.medal_second_place_48px);
            if (getAdapterPosition() == 2)
                medal.setImageResource(R.drawable.medal_third_place_48px);
        }
    }
}
