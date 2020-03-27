package sk3m3l1io.kalymnos.memogame.view.score;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sk3m3l1io.kalymnos.memogame.R;
import sk3m3l1io.kalymnos.memogame.pojos.Player;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {
    private static final int TYPE_NUMERIC = 1313;
    private static final int TYPE_MEDAL = 1314;

    private final Context context;
    private List<Player> players;

    public ScoreAdapter(Context context) {
        this.context = context;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_MEDAL) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_player_score_medal, parent, false);
            return new MedalViewHolder(v);
        } else {
            View v = LayoutInflater.from(context).inflate(R.layout.item_player_score, parent, false);
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

    int deleteme = 0;

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder vh, int pos) {
        Player p = players.get(pos);
        vh.name.setText(p.getName());
        vh.score.setText("" + p.getScore());

        if (getItemViewType(pos) == TYPE_MEDAL) {
            MedalViewHolder m = (MedalViewHolder) vh;
            if (pos == 0) m.medal.setImageResource(R.drawable.medal_first_place_48px);
            if (pos == 1) m.medal.setImageResource(R.drawable.medal_second_place_48px);
            if (pos == 2) m.medal.setImageResource(R.drawable.medal_third_place_48px);
        } else {
            ((NumericViewHolder) vh).position.setText("" + (pos + 1));
        }

    }

    @Override
    public int getItemCount() {
        if (players != null && players.size() > 0)
            return players.size();

        return 0;
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
    }

    class NumericViewHolder extends ScoreViewHolder {
        TextView position;

        public NumericViewHolder(@NonNull View itemView) {
            super(itemView);
            position = itemView.findViewById(R.id.user_position);
        }
    }

    class MedalViewHolder extends ScoreViewHolder {
        ImageView medal;

        public MedalViewHolder(@NonNull View itemView) {
            super(itemView);
            medal = itemView.findViewById(R.id.medal);
        }
    }
}
