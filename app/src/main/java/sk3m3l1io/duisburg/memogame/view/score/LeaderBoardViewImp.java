package sk3m3l1io.duisburg.memogame.view.score;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import sk3m3l1io.duisburg.memogame.R;
import sk3m3l1io.duisburg.memogame.controller.stats.ArcadeScorePage;
import sk3m3l1io.duisburg.memogame.controller.stats.RandomScorePage;
import sk3m3l1io.duisburg.memogame.controller.stats.ScoresFragment;
import sk3m3l1io.duisburg.memogame.pojos.Player;

public class LeaderBoardViewImp implements LeaderBoardView {
    private View root;
    private TextView title;
    private ProgressBar progressBar;

    private ViewPager pager;
    private MyPagerAdapter adapter;

    public LeaderBoardViewImp(LayoutInflater inflater, ViewGroup container, FragmentManager manager) {
        root = inflater.inflate(R.layout.activity_leaderboard, container, false);
        progressBar = root.findViewById(R.id.progressbar);
        title = root.findViewById(R.id.title);
        adapter = new MyPagerAdapter(manager);
        pager = root.findViewById(R.id.pager);
        pager.setAdapter(adapter);
    }

    @Override
    public View getRootView() {
        return root;
    }

    @Override
    public void setTitle(String t) {
        title.setText(t);
    }

    @Override
    public void setTitle(int res) {
        title.setText(res);
    }

    @Override
    public void setScorePageChangeListener(PageChangeListener listener) {
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    listener.onSurvivalModePage();
                } else {
                    listener.onTimeModePage();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void showLoadingIndicator() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setUser(Player p) {
        adapter.setUser(p);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private Player user;

        public MyPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            this.user = user;
        }

        public void setUser(Player user) {
            this.user = user;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            ScoresFragment f;
            if (position == 0) {
                f = new RandomScorePage();
            } else {
                f = new ArcadeScorePage();
            }

            f.setUser(user);
            return f;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
