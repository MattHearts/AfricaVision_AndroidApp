package com.avision_amc.africavisionapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class PhoneUtils {
    //Performs the dial action with the dial code given
    public static void makePhoneCall(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);
    }
}
