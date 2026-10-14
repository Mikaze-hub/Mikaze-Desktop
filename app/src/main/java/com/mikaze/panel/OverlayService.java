package com.mikaze.panel;
import android.app.Service;import android.content.Intent;import android.graphics.PixelFormat;import android.graphics.Color;import android.graphics.drawable.GradientDrawable;import android.os.Build;import android.os.IBinder;import android.view.*;import android.widget.*;
public class OverlayService extends Service{
 WindowManager wm;WindowManager.LayoutParams lp;LinearLayout root,body,nav,content;String page="License";int Y=0xffffd600,BG=0xff0a0c0f,P=0xff14171c;
 int dp(int n){return (int)(n*getResources().getDisplayMetrics().density+.5f);}
 GradientDrawable bg(int c){GradientDrawable d=new GradientDrawable();d.setColor(c);d.setCornerRadius(dp(8));d.setStroke(dp(1),0xff39330f);return d;}
 TextView txt(String s,int size,int c){TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(c);t.setPadding(dp(8),dp(8),dp(8),dp(8));return t;}
 LinearLayout col(){LinearLayout l=new LinearLayout(this);l.setOrientation(1);return l;}
 public void onCreate(){super.onCreate();wm=(WindowManager)getSystemService(WINDOW_SERVICE);root=col();root.setBackground(bg(BG));
 LinearLayout title=new LinearLayout(this);title.setGravity(Gravity.CENTER_VERTICAL);title.setBackgroundColor(P);TextView name=txt("⚡ MIKAZE | DESKTOP PANEL",14,Y);title.addView(name,new LinearLayout.LayoutParams(0,-2,1));TextView min=txt("−",22,Color.WHITE),close=txt("×",22,0xffff6259);title.addView(min);title.addView(close);root.addView(title);
 body=new LinearLayout(this);nav=col();nav.setPadding(dp(4),dp(4),dp(4),dp(4));nav.setBackgroundColor(0xff0d0f12);body.addView(nav,new LinearLayout.LayoutParams(dp(92),dp(390)));
 ScrollView sc=new ScrollView(this);content=col();content.setPadding(dp(8),dp(8),dp(8),dp(8));sc.addView(content);body.addView(sc,new LinearLayout.LayoutParams(0,dp(390),1));root.addView(body);
 root.addView(txt("UI DEMO • GAME NOT CONNECTED",10,0xff8b95a3));
 int type=Build.VERSION.SDK_INT>=26?WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY:WindowManager.LayoutParams.TYPE_PHONE;
 lp=new WindowManager.LayoutParams(dp(340),dp(440),type,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,PixelFormat.TRANSLUCENT);lp.gravity=Gravity.TOP|Gravity.CENTER_HORIZONTAL;lp.y=dp(60);
 title.setOnTouchListener(new View.OnTouchListener(){float x,y;int sx,sy;public boolean onTouch(View v,MotionEvent e){if(e.getAction()==0){x=e.getRawX();y=e.getRawY();sx=lp.x;sy=lp.y;return true;}if(e.getAction()==2){lp.x=sx+(int)(e.getRawX()-x);lp.y=sy+(int)(e.getRawY()-y);wm.updateViewLayout(root,lp);return true;}return true;}});
 min.setOnClickListener(v->{body.setVisibility(body.getVisibility()==View.GONE?View.VISIBLE:View.GONE);});close.setOnClickListener(v->stopSelf());wm.addView(root,lp);makeNav();show("License");}
 void makeNav(){
    nav.removeAllViews();
    String[] items = {
        "License", "Camera", "Visual", "ESP Config",
        "Player", "Teleport", "Vehicle", "Farm"
    };

    for (String item : items) {
        final boolean active = item.equals(page);
        TextView b = txt(
            (active ? "▌  " : "   ") + item,
            11,
            active ? Y : 0xffb9c0cc
        );
        b.setGravity(Gravity.CENTER_VERTICAL);
        b.setMinHeight(dp(44));
        b.setBackground(bg(active ? 0xff292306 : 0xff0b0d10));

        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(-1, dp(44));
        params.bottomMargin = dp(5);
        nav.addView(b, params);

        b.setOnClickListener(v -> {
            page = item;
            makeNav();
            show(item);
        });
    }
}
 void head(String s){
    TextView title = txt(s.toUpperCase(), 18, Y);
    title.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
    title.setPadding(dp(4), dp(6), dp(4), dp(3));
    content.addView(title);

    TextView subtitle = txt(
        "MIKAZE  /  CONTROL CENTER",
        10,
        0xff929baa
    );
    subtitle.setPadding(dp(4), 0, dp(4), dp(9));
    content.addView(subtitle);

    View line = new View(this);
    line.setBackgroundColor(0xff514300);
    LinearLayout.LayoutParams lineParams =
        new LinearLayout.LayoutParams(-1, dp(1));
    lineParams.bottomMargin = dp(10);
    content.addView(line, lineParams);
}
 void toggle(String s){
    final boolean[] enabled = {false};

    LinearLayout row = new LinearLayout(this);
    row.setOrientation(LinearLayout.HORIZONTAL);
    row.setGravity(Gravity.CENTER_VERTICAL);
    row.setPadding(dp(10), dp(5), dp(8), dp(5));
    row.setBackground(bg(P));

    TextView label = txt(s, 12, 0xffeeeeee);
    label.setGravity(Gravity.CENTER_VERTICAL);
    row.addView(label, new LinearLayout.LayoutParams(0, dp(42), 1));

    TextView state = txt("OFF", 10, 0xffaab0bb);
    state.setGravity(Gravity.CENTER);
    state.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
    state.setBackground(bg(0xff252830));
    row.addView(state, new LinearLayout.LayoutParams(dp(52), dp(30)));

    Runnable refresh = () -> {
        state.setText(enabled[0] ? "ON" : "OFF");
        state.setTextColor(enabled[0] ? 0xff090909 : 0xffaab0bb);
        state.setBackground(bg(enabled[0] ? Y : 0xff252830));
        row.setBackground(bg(enabled[0] ? 0xff211c05 : P));
    };

    refresh.run();

    LinearLayout.LayoutParams params =
        new LinearLayout.LayoutParams(-1, dp(50));
    params.bottomMargin = dp(7);
    content.addView(row, params);

    row.setOnClickListener(v -> {
        enabled[0] = !enabled[0];
        refresh.run();
    });
}
 void action(String s){TextView b=txt("▶  "+s,12,Y);b.setBackground(bg(P));content.addView(b);b.setOnClickListener(v->Toast.makeText(this,s+" • demo saja",0).show());}
 void show(String s){content.removeAllViews();head(s);switch(s){
 case "License": info("Vendor","MIKAZE HUB");info("Access Key","MZK-••••-••••-8F21");info("Durasi Dipilih","30 Hari");info("Sisa Hari","--");info("Versi APK","1.0.0");break;
 case "Camera":toggle("AimBot");toggle("AimVisible");slider("Aim FOV",180,90);slider("Aim Distance",500,150);break;
 case "Visual":toggle("ESP Line");toggle("ESP Box");toggle("ESP Skeleton");toggle("ESP AimBot");toggle("ESP FOV");break;
 case "ESP Config":colors("Line Color");colors("Skeleton Color");colors("Box Color");action("Line Origin: Top / Center / Bottom");slider("ESP Stroke",10,2);break;
 case "Player":toggle("Infinity Status / HP Unlimited");toggle("Stamina Infinity");break;
 case "Teleport":action("SET FIX POSITION");action("TELEPORT TO FIX");info("TELEPORT TO MAP","Koordinat tujuan: X / Y / Z");action("TELEPORT TO MAP");break;
 case "Vehicle":toggle("Unlock Vehicle");toggle("Car Power");toggle("Auto Drive");toggle("God Mode Vehicle");toggle("Anti Bike Fall");slider("Speed Drive",300,100);break;
 case "Farm":toggle("Auto Tambang");toggle("Auto Tani");toggle("Auto Pizza");toggle("Auto Bus");toggle("Auto Truck");toggle("Auto Reconnect");toggle("Crash Off");toggle("Matikan Tabrakan");break;
 }}
 void info(String a,String b){LinearLayout c=col();c.setPadding(dp(4),dp(4),dp(4),dp(4));c.setBackground(bg(P));c.addView(txt(a,10,0xff8e98a7));c.addView(txt(b,13,Color.WHITE));LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.bottomMargin=dp(5);content.addView(c,p);}
 void slider(String s,int max,int start){content.addView(txt(s,12,Color.WHITE));SeekBar b=new SeekBar(this);b.setMax(max);b.setProgress(start);b.setProgressTintList(android.content.res.ColorStateList.valueOf(Y));content.addView(b);}
 void colors(String s){content.addView(txt(s,12,Color.WHITE));LinearLayout r=new LinearLayout(this);int[] cs={0xffffd600,0xffff453a,0xff00e676,0xff2196f3,0xffffffff,0xffd500f9};for(int c:cs){TextView b=txt("●",18,c);r.addView(b,new LinearLayout.LayoutParams(0,dp(40),1));b.setOnClickListener(v->Toast.makeText(this,s+" dipilih (demo)",0).show());}content.addView(r);}
 public int onStartCommand(Intent i,int f,int id){return START_NOT_STICKY;}public void onDestroy(){if(root!=null)wm.removeView(root);super.onDestroy();}public IBinder onBind(Intent i){return null;}
}
