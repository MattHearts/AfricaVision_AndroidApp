package com.avision_amc.africavisionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class VoteActivity extends AppCompatActivity {


    private ListView contestantsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        //Initializes the ListView
        contestantsListView = findViewById(R.id.contestantsListView);
        //Creates a list of contestants
        List<Contestant> contestants = new ArrayList<>();
        contestants.add(new Contestant("Nigeria", R.drawable.nigeria_flag_heart, "African Queen by 2Face Idibia", "01"));
        contestants.add(new Contestant("Kenya", R.drawable.kenya_flag_heart, "Malaika by Fadhili Williams", "02"));
        contestants.add(new Contestant("South Africa", R.drawable.south_africa_flag_heart, "Nomakhanjani’ by Brenda Fassie", "03"));
        contestants.add(new Contestant("Cote d'Ivoir", R.drawable.cote_d_ivoir_flag_heart, "Premier Gaou by Magic System", "04"));
        contestants.add(new Contestant("Ethiopia", R.drawable.ethiopia_flag_heart, "Mezez Alew’by Aster Aweke", "05"));
        contestants.add(new Contestant("Democratic Republic of the Congo", R.drawable.democratic_republic_of_the_congo_flag_heart, "Coupe Bibamba by Awilo Longomba", "06"));
        contestants.add(new Contestant("Benin", R.drawable.benin_flag_heart, "Wombo Lombo by Angelique Kidjo", "07"));
        contestants.add(new Contestant("Ghana", R.drawable.ghana_flag_heart, "Once a Slave’ by Project Monkz, feat. Maulana", "08"));
        contestants.add(new Contestant("Cameroon", R.drawable.cameroon_flag_heart, "Zamina Mina (Zangalewa) by Golden Sounds", "09"));
        contestants.add(new Contestant("Tunisia", R.drawable.tunisia_flag_heart, "Ena by Balti", "10"));
        contestants.add(new Contestant("Madagascar", R.drawable.madagascar_flag_heart, "Goustaro na horevo by King Julian", "11"));
        contestants.add(new Contestant("Touareg Group", R.drawable.touareg_flag_heart, "Tarhanine Tegla by Afous D'afous", "12"));

        //Creates the adapter
        ContestantAdapter adapter = new ContestantAdapter(this, contestants);

        //Sets the adapter to the ListView
        contestantsListView.setAdapter(adapter);
    }
}