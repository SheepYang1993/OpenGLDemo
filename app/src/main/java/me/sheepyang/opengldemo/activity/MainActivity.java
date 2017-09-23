package me.sheepyang.opengldemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.sheepyang.opengldemo.R;
import me.sheepyang.opengldemo.activity.triangle.Triangle1Activity;
import me.sheepyang.opengldemo.activity.triangle.Triangle2Activity;
import me.sheepyang.opengldemo.activity.triangle.Triangle3Activity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private List<MenuBean> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDataList.add(new MenuBean("直角三角形", Triangle1Activity.class));
        mDataList.add(new MenuBean("等腰直角三角形", Triangle2Activity.class));
        mDataList.add(new MenuBean("彩色等腰直角三角形", Triangle3Activity.class));
        mDataList.add(new MenuBean("正方形", SquareActivity.class));
        mDataList.add(new MenuBean("正多边形及圆形", PolygonActivity.class));
        mDataList.add(new MenuBean("画一个三角形", Triangle1Activity.class));
        mDataList.add(new MenuBean("画一个三角形", Triangle1Activity.class));
        mDataList.add(new MenuBean("画一个三角形", Triangle1Activity.class));
        mDataList.add(new MenuBean("画一个三角形", Triangle1Activity.class));
        mDataList.add(new MenuBean("画一个三角形", Triangle1Activity.class));
        mDataList.add(new MenuBean("画一个三角形", Triangle1Activity.class));
        mDataList.add(new MenuBean("画一个三角形", Triangle1Activity.class));
        mDataList.add(new MenuBean("画一个三角形", Triangle1Activity.class));
        mDataList.add(new MenuBean("画一个三角形", Triangle1Activity.class));
        mRecyclerView.setAdapter(new MyAdapter(mDataList));
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private List<MenuBean> mData;

        public MyAdapter(List<MenuBean> dataList) {
            this.mData = dataList;
        }

        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(MainActivity.this)
                    .inflate(R.layout.item_main, parent, false));
        }

        @Override
        public void onBindViewHolder(MyAdapter.MyViewHolder holder, final int position) {
            holder.mBtnName.setText(mData.get(position).getName());
            holder.mBtnName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, mData.get(position).getClazz()));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            Button mBtnName;

            MyViewHolder(View itemView) {
                super(itemView);
                mBtnName = itemView.findViewById(R.id.btn_name);
            }
        }
    }

    private class MenuBean {
        private String name;
        private Class clazz;

        public MenuBean(String name, Class clazz) {
            this.name = name;
            this.clazz = clazz;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Class getClazz() {
            return clazz;
        }

        public void setClazz(Class clazz) {
            this.clazz = clazz;
        }
    }
}
