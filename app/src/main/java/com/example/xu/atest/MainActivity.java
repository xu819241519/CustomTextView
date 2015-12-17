package com.example.xu.atest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //新闻信息
    private final String newsbody = " <p>　　今年浙江卫视凭《中国好声音》一举做大" +
            "，其巨大的影响力直接波及到了各家卫视“跨年晚会”的战略部署。日前" +
            "，“跨年晚会”概念的鼻祖湖南卫视率先表示“退出跨年烧钱大战”。" +
            "但据湖南卫视内部人士透露，即使如此，今年的湖南跨年晚会也将会掂出“跨年季”这个概念" +
            "，“也就是从12月27日到12月31日，连续五天，我们将相继用《百变大咖秀》、《快乐大本营》" +
            "、《女人如歌》、《天天向上》的特别节目来连续打造这个”季“的概念，直到12月31日的那场晚会。”</p>";
    private MyTextView myTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        myTextView = (MyTextView) findViewById(R.id.mytextview);
        ArrayList<HashMap<String, String>> datas = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> hashMap1 = new HashMap<String, String>();
        hashMap1.put("type", "image");
        hashMap1.put("value", "http://www.taoqao.com/uploads/allimg/111216/1-111216101257.png");
        HashMap<String, String> hashMap2 = new HashMap<String, String>();
        hashMap2.put("type", "text");
        hashMap2.put("value", newsbody);
        HashMap<String, String> hashMap3 = new HashMap<String, String>();
        hashMap3.put("type", "image");
        hashMap3.put("value", "http://www.bz55.com/uploads/allimg/150309/139-150309101A0.jpg");
        datas.add(hashMap1);
        datas.add(hashMap2);
        datas.add(hashMap3);
        myTextView.setContent(datas);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
