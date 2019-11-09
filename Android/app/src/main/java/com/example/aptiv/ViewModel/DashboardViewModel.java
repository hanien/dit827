package com.example.aptiv.ViewModel;

import com.example.aptiv.Model.Classe.Profile;
import com.example.aptiv.Model.Helpers.DataReaderWriter;
import com.example.aptiv.View.MainActivity;

import java.util.ArrayList;

public class DashboardViewModel extends BaseViewModel  {
    private MainActivity activity;
    public ArrayList<Profile> ProfileList;

    public DashboardViewModel(MainActivity activity) {
        super(activity);
        this.activity = activity;

        init();
    }

    public void init(){
        ArrayList<Profile> Profiles = new ArrayList<>();
        Profiles.add(new Profile("ReadMode","20","20","20","20","20","20","20","20","20","20","20"));
        Profiles.add(new Profile("Relaxation","30","30","30","30","30","30","30","30","30","30","30"));
        DataReaderWriter.getInstance(activity).WriteObjectToFile(Profiles);

        ProfileList = DataReaderWriter.getInstance(activity).ReadObjectFromFile();

        System.out.println(ProfileList);
    }

}
