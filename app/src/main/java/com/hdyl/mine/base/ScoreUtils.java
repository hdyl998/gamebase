package com.hdyl.mine.base;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class ScoreUtils {


	public static void getToScore(Context context){
		try {
			Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
			Intent intent2 = new Intent(Intent.ACTION_VIEW, uri);
			intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent2);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(context, "Couldn't launch the market !", Toast.LENGTH_SHORT).show();
		}
	}
}
