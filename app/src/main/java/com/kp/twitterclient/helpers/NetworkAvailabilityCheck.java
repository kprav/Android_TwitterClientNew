package com.kp.twitterclient.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

public class NetworkAvailabilityCheck {

    // Check if network is available
    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting())
            return true;
        else
            return false;
    }

    // Show dialog that network is not available
    public static void showNetworkUnavailableDialog(Activity activity) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle("No Internet Access!");
        alertDialog.setMessage("Please check your connection and try again.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    // Show Toast Message that network is not available
    public static void showNetworkUnavailableToast(Activity activity) {
        Toast.makeText(activity, "No Internet! Please check your connection.", Toast.LENGTH_SHORT).show();
    }

    // Show SnackBar Message that network is not available
    public static void showNetworkUnavailableSnackBar(View view) {
        Snackbar.make(view, "No Internet! Please check your connection.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }

}
