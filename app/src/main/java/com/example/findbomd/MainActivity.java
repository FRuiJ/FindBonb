package com.example.findbomd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.style.LineHeightSpan;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<FindBondBean> mDatas;
    private GridView gridView;
    private FindBondAdapter<FindBondBean> findBondAdapter;
    private TextView textView;

    private int[][] code; // 地图
    private Random random = new Random();

    private int new_line = 9; // 初始值——行
    private int new_list = 2; // 列
    private int[] bx = {0, 0, 1, 1, 1, -1, -1, -1};
    private int[] by = {1, -1, 0, 1, -1, 0, 1, -1};
    private final int length_of_side = 25 + 2; // 格子的边长加上间距

    private int in_num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatas = new ArrayList<>();
        UpdateMap();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "你点击了" + position, Toast.LENGTH_SHORT).show();
                in_num = position;
                textView = (TextView) view.findViewById(R.id.item_block_iv_picture);
                textView.setBackgroundResource(R.mipmap.square3);
            }
        });
    }

    private void UpdateMap() {
        /*重置地图函数：*/
        // 绘制游戏布局
        gridView = findViewById(R.id.main_gv_matrix);
        gridView.setNumColumns(new_list);
        gridView.setColumnWidth(length_of_side*new_list);

        // 随机生成炸弹
        code = new int[new_line][new_list];
        int bombNum = new_line; // 炸弹数量
        // 生成地图； -1为炸弹
        while (bombNum > 0) {
            int sub = random.nextInt(new_list * new_line);
            int line = sub / new_list;
            int list = sub % new_list;
            if (code[line][list] != -1) {
                code[line][list] = -1;
                for (int i = 0; i < bx.length; i++) {
                    int zx = line + bx[i];
                    int zy = list + by[i];
                    if (zx >= 0 && zx < new_line && zy >= 0 && zy < new_list) { // 不越界
                        if (code[zx][zy] != -1) {
                            code[zx][zy]++;
                            bombNum--;
                        }
                    }
                }
            }
        }
        // 填充数据
        FindBondBean findBondBean;
        for (int i = 0; i < new_list * new_line; i++) {
            findBondBean = new FindBondBean();
            findBondBean.setText(code[i / new_list][i % new_list]);
            findBondBean.setBomb(false);
            findBondBean.setFlag(false);
            findBondBean.setOpen(false);
            mDatas.add(findBondBean);
        }
//        Drawable drawable = new
        findBondAdapter = new FindBondAdapter<>(R.layout.item_block, mDatas, this,
                new FindBondAdapter.CallBack<FindBondBean>() {
                    @Override
                    public void item(FindBondAdapter.ViewHolder viewHolder, FindBondBean data, int position) {
                        TextView textView = viewHolder.getTextView(R.id.item_block_iv_picture);
                        if (position == 0)
                            textView.setBackgroundResource(R.mipmap.square1);

                    }
                });
        gridView.setAdapter(findBondAdapter);
    }

}