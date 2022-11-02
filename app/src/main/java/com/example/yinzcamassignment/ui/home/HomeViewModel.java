package com.example.yinzcamassignment.ui.home;

import android.util.Log;

import com.example.yinzcamassignment.data.ScheduleBean;
import com.example.yinzcamassignment.data.TeamBean;
import com.example.yinzcamassignment.tools.Repository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final String TEAM_SCHEDULE_URL = "http://files.yinzcam.com.s3.amazonaws.com/iOS/interviews/ScheduleExercise/schedule.json";
    private Repository repository;
    private TeamBean curTeam;
    private MutableLiveData<HashMap<String, List<ScheduleBean>>> scheduleHashMap = new MutableLiveData<>();
    public enum SCHEDULE_TYPES {
        SCHEDULED("S"), FINAL("F"), BYE("B");
        String type;
        SCHEDULE_TYPES(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public String getFull(String type) {
            if (FINAL.getType().equals(type)) {
                return "Final";
            }
            return "";
        }
    }

    public HomeViewModel() {
        this.repository = Repository.getInstance();
    }

    public MutableLiveData<JsonObject> getTeamScheduleData() {
        repository.callApi(TEAM_SCHEDULE_URL);
        return repository.getResponse();
    }

    public MutableLiveData<HashMap<String, List<ScheduleBean>>> getScheduleHashMap() {
        return scheduleHashMap;
    }

    public void setScheduleHashMap(HashMap<String, List<ScheduleBean>> scheduleHashMap) {
        this.scheduleHashMap.setValue(scheduleHashMap);
    }

    public TeamBean getCurTeam() {
        return curTeam;
    }

    public void setCurTeam(TeamBean curTeam) {
        this.curTeam = curTeam;
    }

    /*
        parse json data and store it
         */
    public void parseScheduleData(JsonObject data) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        curTeam = new Gson().fromJson(data.get("Team"), TeamBean.class);

        JsonArray gameSections = data.getAsJsonArray("GameSection");
        HashMap<String, List<ScheduleBean>> scheduleMap = new HashMap<>();
        for (int i = 0; i < gameSections.size(); i ++) {
            JsonObject gameSection = gameSections.get(i).getAsJsonObject();
            JsonArray games = gameSection.getAsJsonArray("Game");
            String heading = gameSection.get("Heading").getAsString();
            ArrayList<ScheduleBean> arrayList = new ArrayList<>();
            for (int j = games.size()-1; j >= 0; j--) {
                JsonElement game = games.get(j);
                ScheduleBean scheduleBean = new Gson().fromJson(game, ScheduleBean.class);
                JsonObject date =  game.getAsJsonObject().getAsJsonObject("Date");
                if (date != null) {
                    String timestamp = date.get("Timestamp").getAsString();
                    try {
                        Date time = sdf.parse(timestamp);
                        scheduleBean.setTimestamp(time.getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                arrayList.add(scheduleBean);
            }
            scheduleMap.put(heading, arrayList);
        }
        setScheduleHashMap(scheduleMap);
    }

    /*
    transfer parsed data to array item for displaying on the listview
     */
    public ArrayList<Object> getListItems(HashMap<String, List<ScheduleBean>> hashMap) {
        ArrayList<Object> listItems = new ArrayList<>();
        for (String key : hashMap.keySet()) {
            listItems.add(key);
            List<ScheduleBean> beanList = hashMap.get(key);
            for (int i = 0; i < beanList.size(); i++) {
                listItems.add(beanList.get(i));
            }
        }
        return listItems;
    }

}