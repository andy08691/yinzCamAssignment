package com.example.yinzcamassignment.ui.home;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.yinzcamassignment.R;
import com.example.yinzcamassignment.data.ScheduleBean;
import com.example.yinzcamassignment.data.TeamBean;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Object> mDataList;
    private Context mContext;
    private TeamBean mTeam;
    private SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM dd");
    private SimpleDateFormat sdf2 = new SimpleDateFormat("H:mm a");



    public ItemAdapter(TeamBean teamBean, List<Object> mDataList, Context mContext) {
        this.mDataList = mDataList;
        this.mContext = mContext;
        this.mTeam = teamBean;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View inflator = LayoutInflater.from(mContext).inflate(R.layout.item_heading_layout, parent, false);
            return new HeaderHolder(inflator);
        } else if (viewType == 2) { // show item view
            View inflator = LayoutInflater.from(mContext).inflate(R.layout.item_bye_layout, parent, false);
            return new ByeHolder(inflator);
        } else {
            View inflator = LayoutInflater.from(mContext).inflate(R.layout.item_score_layout, parent, false);
            return new ScoreHolder(inflator);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object item = mDataList.get(position);
        if (holder.getItemViewType() == 1) {
            HeaderHolder headerHolder = (HeaderHolder) holder;
            headerHolder.txt_heading.setText((String) item);
        } else if (holder.getItemViewType() == 2) {

        } else {
            ScheduleBean scheduleBean = (ScheduleBean) item;
            ScoreHolder scoreHolder = (ScoreHolder) holder;
            scoreHolder.txt_team_name.setText(mTeam.getName());
            scoreHolder.txt_opponent_name.setText(scheduleBean.getOpponent().getName());
            if (HomeViewModel.SCHEDULE_TYPES.SCHEDULED.getType().equals(scheduleBean.getType())) {
                scoreHolder.txt_team_record.setVisibility(View.VISIBLE);
                scoreHolder.txt_opponent_record.setVisibility(View.VISIBLE);
                scoreHolder.txt_home_score.setVisibility(View.GONE);
                scoreHolder.txt_away_score.setVisibility(View.GONE);
                scoreHolder.txt_team_record.setText(mTeam.getRecord());
                scoreHolder.txt_opponent_record.setText(scheduleBean.getOpponent().getRecord());
                scoreHolder.txt_time.setText(sdf2.format(new Date(scheduleBean.getTimestamp())));
            } else {
                scoreHolder.txt_home_score.setVisibility(View.VISIBLE);
                scoreHolder.txt_away_score.setVisibility(View.VISIBLE);
                scoreHolder.txt_team_record.setVisibility(View.GONE);
                scoreHolder.txt_opponent_record.setVisibility(View.GONE);
                scoreHolder.txt_home_score.setText(scheduleBean.getHomeScore());
                scoreHolder.txt_away_score.setText(scheduleBean.getAwayScore());
                scoreHolder.txt_time.setText(HomeViewModel.SCHEDULE_TYPES.FINAL.getFull(scheduleBean.getType()));
            }
            scoreHolder.txt_date.setText(sdf.format(new Date(scheduleBean.getTimestamp())));
            scoreHolder.txt_week.setText(scheduleBean.getWeek());

            String team_logo_url = String.format("http://yc-app-resources.s3.amazonaws.com/nfl/logos/nfl_%s_light.png", mTeam.getTriCode().toLowerCase());
            String opponent_logo_url = String.format("http://yc-app-resources.s3.amazonaws.com/nfl/logos/nfl_%s_light.png", scheduleBean.getOpponent().getTriCode().toLowerCase());
            Picasso.get()
                    .load(team_logo_url)
                    .into(scoreHolder.img_team_logo);
            Picasso.get()
                    .load(opponent_logo_url)
                    .into(scoreHolder.img_opponent_logo);
//            URL team_url = null;
//            URL opponent_url = null;
//            try {
//                team_url = new URL(team_logo_url);
//                opponent_url = new URL(opponent_logo_url);
//                scoreHolder.img_team_logo.setImageBitmap(BitmapFactory.decodeStream(team_url.openConnection().getInputStream()));
//                scoreHolder.img_team_logo.setImageBitmap(BitmapFactory.decodeStream(team_url.openConnection().getInputStream()));
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        }

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mDataList.get(position);
        if (ScheduleBean.class.isInstance(item)) {
            ScheduleBean scheduleBean = (ScheduleBean) item;
            if (HomeViewModel.SCHEDULE_TYPES.BYE.getType().equals(scheduleBean.getType())) {
                return 2;
            } else {
                return 3;
            }
        } else {
            return 1;
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        TextView txt_heading;

        public HeaderHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txt_heading = itemView.findViewById(R.id.text_heading);
        }
    }

    class ByeHolder extends RecyclerView.ViewHolder {
        public ByeHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }

    class ScoreHolder extends RecyclerView.ViewHolder {
        TextView txt_team_name, txt_home_score, txt_opponent_name, txt_away_score, txt_team_record, txt_opponent_record, txt_date, txt_time, txt_week, txt_other;
        ImageView img_team_logo, img_opponent_logo;
        public ScoreHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txt_team_name = itemView.findViewById(R.id.team_name);
            txt_home_score = itemView.findViewById(R.id.home_score);
            txt_opponent_name = itemView.findViewById(R.id.opponent_name);
            txt_away_score = itemView.findViewById(R.id.away_score);
            txt_team_record = itemView.findViewById(R.id.team_record);
            txt_opponent_record = itemView.findViewById(R.id.opponent_record);
            txt_date = itemView.findViewById(R.id.date);
            txt_time = itemView.findViewById(R.id.time);
            txt_week = itemView.findViewById(R.id.week);
            txt_other = itemView.findViewById(R.id.other);
            img_team_logo = itemView.findViewById(R.id.img_team_logo);
            img_opponent_logo = itemView.findViewById(R.id.img_opponent_logo);
        }
    }




}
