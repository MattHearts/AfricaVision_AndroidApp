package com.avision_amc.africavisionapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.List;

public class ContestantAdapter extends ArrayAdapter<Contestant> {

    private Context context;
    private List<Contestant> contestants;

    private String phoneNumber;

    private static final String TELEPHONE_NUM_START= "003069000000";

    public ContestantAdapter(Context context, List<Contestant> contestants) {
        super(context, 0, contestants);
        this.context = context;
        this.contestants = contestants;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.contestant_row, parent, false);

            holder = new ViewHolder();
            holder.callIDView = convertView.findViewById(R.id.callIDTextView);
            holder.flagImageView = convertView.findViewById(R.id.flagImageView);
            holder.countryNameTextView = convertView.findViewById(R.id.countryNameTextView);
            holder.songNameTextView = convertView.findViewById(R.id.songNameTextView);
            holder.voteButton = convertView.findViewById(R.id.voteButton);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Contestant contestant = contestants.get(position);

        // Set the call ID
        holder.callIDView.setText(String.format("+%s", contestant.getCallID()));

        // Set the country name
        holder.countryNameTextView.setText(contestant.getCountryName());

        // Set the song name
        holder.songNameTextView.setText(contestant.getSongName());

        // Set the flag image (replace with appropriate resource ID)
        holder.flagImageView.setImageResource(contestant.getFlagResId());

        // Set the Vote button functionality if needed
        holder.voteButton.setOnClickListener(view-> {

                // Performs the vote button action here
            handleVoteButtonClick(contestant.getCallID());

        });

        return convertView;
    }

    private void handleVoteButtonClick(String callID) {
        // Perform the vote button action for the specific contestant
        // You can implement the desired logic here
        // Prepare the phone number by appending the callID
        phoneNumber = TELEPHONE_NUM_START + callID;


            makePhoneCall(phoneNumber);
        }


    private void makePhoneCall(String phoneNumber) {
        // Create the intent to initiate the phone call
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);
    }



    private static class ViewHolder {

        TextView callIDView;
        ImageView flagImageView;
        TextView countryNameTextView;
        TextView songNameTextView;
        Button voteButton;
    }
}
