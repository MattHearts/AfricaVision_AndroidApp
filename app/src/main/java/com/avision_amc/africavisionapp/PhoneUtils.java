package com.avision_amc.africavisionapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

public class PhoneUtils {

    private static String phoneNumber;
    private static String phoneNumberSMS;
    private static final String TELEPHONE_NUM_START = "003069000000";

    //Performs the dial action with the dial code given
    public static void makePhoneCall(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);
    }
    //Initiates a text message
    public static void sendTextMessage(Context context, String phoneNumber, String SMSmessage) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + phoneNumber));
        intent.putExtra("sms_body", SMSmessage);
        context.startActivity(intent);
    }

    //Performs the vote action for the specific contestant and gives a choice between Call and SMS
    static void VoteChoice(Context context, String callID) {

        phoneNumber = TELEPHONE_NUM_START + callID;
        phoneNumberSMS = TELEPHONE_NUM_START + "00";

        String SMSmessage = "AFRICAVISION " + callID;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Vote");
        builder.setMessage("Phone Call or SMS?");
        builder.setNegativeButton("Call", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                makePhoneCall(context, phoneNumber);
            }
        });

        builder.setPositiveButton("SMS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendTextMessage(context, phoneNumberSMS, SMSmessage);
            }
        });
        builder.show();
    }
}
