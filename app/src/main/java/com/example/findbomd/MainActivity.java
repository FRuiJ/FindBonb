package com.example.findbomd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class MainActivity extends AppCompatActivity {

    private List<FindBondBean> mDatas;
    private GridView gridView;
    private FindBondAdapter<FindBondBean> findBondAdapter;
    private TextView textView;

    private int[][] code; // 地图
    private int[][] open; // 标记
    private Random random = new Random();

    private int new_line = 9; // 初始值——行
    private int new_list = 5; // 列
    private int[] bx = {0, 0, 1, 1, 1, -1, -1, -1};
    private int[] by = {1, -1, 0, 1, -1, 0, 1, -1};
    private final int length_of_side = 25 + 2; // 格子的边长加上间距
    private Queue<Integer> queue;

    private int in_num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UpdateMap();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (code[position / new_list][position % new_list] == -1)
                    Toast.makeText(MainActivity.this, "游戏失败", Toast.LENGTH_SHORT).show();
                Bfs(position);

            }
        });
    }

    private void Bfs(int position) {
        queue = new LinkedList<>();
        open[position / new_list][position % new_list] = 1;
        queue.add(position);
        while (!queue.isEmpty()) {
            int top = queue.poll();
            if (code[top / new_list][top % new_list] == 0) {
                for (int i = 0; i < bx.length; i++) {
                    int zx = top / new_list + bx[i];
                    int zy = top % new_list + by[i];
                    if (zx >= 0 && zx < new_line && zy >= 0 && zy < new_list && open[zx][zy] == 0) {
                        open[zx][zy] = 1;
                        queue.add(zx * new_list + zy);
                    }
                }
            }
        }
        // 广搜素结束， 重新绑定
        findBondAdapter = new FindBondAdapter<>(R.layout.item_block, mDatas, this,
                new FindBondAdapter.CallBack<FindBondBean>() {
                    @Override
                    public void item(FindBondAdapter.ViewHolder viewHolder, FindBondBean data, int position) {
                        if (open[position / new_list][position % new_list] == 1) { // 当被翻开就刷新图片
                            TextView textView = viewHolder.getTextView(R.id.item_block_iv_picture);
                            switch (code[position / new_list][position % new_list]) {
                                case -1:
                                    textView.setBackgroundResource(R.drawable.ic_bomb_3);
                                    break;
                                case 0:
                                    textView.setBackgroundResource(R.drawable.ic_launcher_foreground);
                                    break;
                                case 1:
                                    textView.setBackgroundResource(R.drawable.square10);
                                    break;
                                case 2:
                                    textView.setBackgroundResource(R.drawable.square3);
                                    break;
                                case 3:
                                    textView.setBackgroundResource(R.drawable.square4);
                                    break;
                                case 4:
                                    textView.setBackgroundResource(R.drawable.square5);
                                    break;
                                case 5:
                                    textView.setBackgroundResource(R.drawable.square6);
                                    break;
                                case 6:
                                    textView.setBackgroundResource(R.drawable.square7);
                                    break;
                                case 7:
                                    textView.setBackgroundResource(R.drawable.square8);
                                    break;
                                case 8:
                                    textView.setBackgroundResource(R.drawable.square9);
                                    break;
                            }
                        }
                    }
                });
        gridView.setAdapter(findBondAdapter);
    }
//    private void bfs(int position) {
//        queue_l = new LinkedList<>();
//        queue_h = new LinkedList<>();
//        int l = position % new_list;
//        int h = position / new_list;
//        queue_l.add(l);
//
//        while (!queue_h.isEmpty()) {
//            int hh = queue_h.poll();
//            int ll = queue_l.poll();
//            FindBondBean findBondBean = mDatas.get(hh * new_list + ll);
//            if (findBondBean.getText() == 0) { // 为0搜索
//                for (int i = 0; i < bx.length; i++) {
//                    int zx = hh + bx[i];
//                    int zy = ll + by[i];
//                    if (zx >= 0 && zx < new_line && zy >= 0 && zy < new_list) { // 不越界
//                        findBondBean = mDatas.get(zx * new_list + zy);
//                        if (findBondBean.getText() != -1 && findBondBean.isOpen() == false) { // 非雷加入队列,和没打开的
//                            findBondBean.setOpen(true);
//                            queue_h.add(zx);
//                            queue_l.add(zy);
//                            Rebind(findBondBean.getText(), zx * new_list + zy);
//                        }
//                    }
//                }
//            }
//        }
//    }

//    private void Rebind(int text, int id) {
//        findBondAdapter = new FindBondAdapter<>(R.layout.item_block, mDatas, this,
//                new FindBondAdapter.CallBack<FindBondBean>() {
//                    @Override
//                    public void item(FindBondAdapter.ViewHolder viewHolder, FindBondBean data, int position) {
//                        TextView textView = viewHolder.getTextView(R.id.item_block_iv_picture);
//                        if (position == id)
//                            switch (text) {
//                                case -1:
//                                    textView.setBackgroundResource(R.drawable.ic_bomb_3);
//                                    break;
//                                case 0:
//                                    textView.setBackgroundResource(R.drawable.ic_launcher_foreground);
//                                    break;
//                                case 1:
//                                    textView.setBackgroundResource(R.drawable.square10);
//                                    break;
//                                case 2:
//                                    textView.setBackgroundResource(R.drawable.square3);
//                                    break;
//                                case 3:
//                                    textView.setBackgroundResource(R.drawable.square4);
//                                    break;
//                                case 4:
//                                    textView.setBackgroundResource(R.drawable.square5);
//                                    break;
//                                case 5:
//                                    textView.setBackgroundResource(R.drawable.square6);
//                                    break;
//                                case 6:
//                                    textView.setBackgroundResource(R.drawable.square7);
//                                    break;
//                                case 7:
//                                    textView.setBackgroundResource(R.drawable.square8);
//                                    break;
//                                case 8:
//                                    textView.setBackgroundResource(R.drawable.square9);
//                                    break;
//                            }
//
//                    }
//                });
//        gridView.setAdapter(findBondAdapter);
//    }

    private void UpdateMap() {
        /*重置地图函数：*/
        // 绘制游戏布局
        gridView = findViewById(R.id.main_gv_matrix);
        gridView.setNumColumns(new_list);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) gridView.getLayoutParams();
        layoutParams.width = new_list * 55 *2;
//        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        gridView.setLayoutParams(layoutParams);
        open = new int[new_line][new_list]; // 重新生成标记地图
        // 随机生成炸弹
        code = new int[new_line][new_list];
        int bombNum = (new_line + new_list) / 2; // 炸弹数量
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
                        }
                    }
                }
                bombNum--;
            }
        }
        // 填充数据
        mDatas = new ArrayList<>();
        FindBondBean findBondBean;
        for (int i = 0; i < new_list * new_line; i++) {
            findBondBean = new FindBondBean();
            findBondBean.setText(code[i / new_list][i % new_list]);
            findBondBean.setFlag(false);
            mDatas.add(findBondBean);
        }
//        Drawable drawable = new
        findBondAdapter = new FindBondAdapter<>(R.layout.item_block, mDatas, this,
                new FindBondAdapter.CallBack<FindBondBean>() {
                    @Override
                    public void item(FindBondAdapter.ViewHolder viewHolder, FindBondBean data, int position) {
//                        TextView textView = viewHolder.getTextView(R.id.item_block_iv_picture);
//                        textView.setBackgroundResource(R.drawable.square1);

                    }
                });
        gridView.setAdapter(findBondAdapter);
    }


//    private void OpenSquare(View view, int text) {
//        textView = (TextView) view.findViewById(R.id.item_block_iv_picture);
//        switch (text) {
//            case -1:
//                textView.setBackgroundResource(R.drawable.ic_bomb_3);
//                break;
//            case 0:
//                textView.setBackgroundResource(R.drawable.ic_launcher_foreground);
//                break;
//            case 1:
//                textView.setBackgroundResource(R.drawable.square10);
//                break;
//            case 2:
//                textView.setBackgroundResource(R.drawable.square3);
//                break;
//            case 3:
//                textView.setBackgroundResource(R.drawable.square4);
//                break;
//            case 4:
//                textView.setBackgroundResource(R.drawable.square5);
//                break;
//            case 5:
//                textView.setBackgroundResource(R.drawable.square6);
//                break;
//            case 6:
//                textView.setBackgroundResource(R.drawable.square7);
//                break;
//            case 7:
//                textView.setBackgroundResource(R.drawable.square8);
//                break;
//            case 8:
//                textView.setBackgroundResource(R.drawable.square9);
//                break;
//        }
//
//    }

}