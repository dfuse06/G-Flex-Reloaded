package com.dfuse.gflexreloaded;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static com.dfuse.gflexreloaded.root_tools.findBinary;

/**
 * Created by dfuse on 3/11/17.
 */

public class Flash extends Fragment {

    String tagname = "Flash";
    String dir = Environment.getExternalStorageDirectory() + "/Reloaded";

    Button switcher;
    TextView busybox;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.flash, null);
        switcher = (Button) view.findViewById(R.id.button3);


        switcher.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (isRooted() == true ){
                    startInstall();

                }
                else {
                   noBusybox();

                }


            }

        });

        return view;
    }

    void install() {

        String cmd_recovery = "busybox dd if=" + dir + "/recovery.img" + "  of=/dev/block/platform/msm_sdcc.1/by-name/recovery";

        root_tools.execute(cmd_recovery);

        Log.i(tagname, "Flash");
    }

    private static boolean isRooted() {
        return findBinary("busybox");

        }

    private void noBusybox(){
        new AlertDialog.Builder(getActivity())
                .setCancelable(false)
                .setTitle("No Busybox")
                .setMessage("You can't read and you're a idiot!!! Go install busybox and try again.")

                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse("http://www.reactiongifs.com/r/idjut.gif"));
                        startActivity(intent);
                    }
                }).show();
    }


    private void startInstall() {
        final ProgressDialog RingProgressDialog = new ProgressDialog(getActivity());
        RingProgressDialog.setTitle("Please Wait");
        RingProgressDialog.setMessage("Installing");
        RingProgressDialog.setCancelable(false);
        RingProgressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //this is the runnable stuff for the progress bar
                    install();
                } catch (Exception e) {
                    Log.e("Installer", "something went wrong");

                }
                RingProgressDialog.dismiss();

            }
        }).start();

    }


}