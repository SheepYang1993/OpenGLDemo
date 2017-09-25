package me.sheepyang.opengldemo.activity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.sheepyang.opengldemo.R;
import me.sheepyang.opengldemo.renderer.Square;

/**
 * 画一个正方形
 */
public class SquareActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.gl_view)
    GLSurfaceView mGlView;
    @BindView(R.id.tv_type)
    TextView mTvType;
    private Square mSquare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("正方形");
        mGlView.setEGLContextClientVersion(2);//创建一个OpenGL ES 2.0 context
        mSquare = new Square(mGlView);
        mGlView.setRenderer(mSquare);
        mGlView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);//脏模式，这样可以有效降低cpu负载
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGlView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGlView.onPause();
    }


    @Override
    @OnClick({R.id.btn_change})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_change:
                mSquare.changeDrawType();
                mGlView.requestRender();
                switch (mSquare.getDrawType()) {
                    case 0:
                        mTvType.setText("当前：顶点法绘制");
                        break;
                    case 1:
                        mTvType.setText("当前：索引法绘制");
                        break;
                }
                break;
            default:
                break;
        }
    }
}
