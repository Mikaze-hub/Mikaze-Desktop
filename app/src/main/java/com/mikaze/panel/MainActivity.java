package com.mikaze.panel;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.*;
public class MainActivity extends Activity {
 public void onCreate(Bundle b){super.onCreate(b); getWindow().setStatusBarColor(0xff08090b);getWindow().setNavigationBarColor(0xff08090b);
 LinearLayout l=new LinearLayout(this);l.setOrientation(1);l.setGravity(Gravity.CENTER_VERTICAL);l.setPadding(48,32,48,32);l.setBackgroundColor(0xff08090b);
 TextView t=new TextView(this);t.setText("⚡ MIKAZE");t.setTextColor(0xffffd600);t.setTextSize(30);t.setTypeface(null,1);l.addView(t);
 TextView d=new TextView(this);d.setText("DESKTOP FLOATING PANEL\n\nPanel bergaya PC. Fitur game belum terhubung ke Rio Rise.");d.setTextColor(0xffb8c0cc);d.setTextSize(16);l.addView(d);
 Button x=new Button(this);x.setText("BUKA FLOATING PANEL");l.addView(x);x.setOnClickListener(v->{if(Build.VERSION.SDK_INT>=23&&!Settings.canDrawOverlays(this)){startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,Uri.parse("package:"+getPackageName())));}else{startService(new Intent(this,OverlayService.class));finish();}});setContentView(l);}
}
