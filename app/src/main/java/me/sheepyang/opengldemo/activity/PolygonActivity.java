package me.sheepyang.opengldemo.activity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.sheepyang.opengldemo.R;
import me.sheepyang.opengldemo.renderer.Square;

/**
 * 画一个正多边形及圆形
 */
public class PolygonActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.gl_view)
    GLSurfaceView mGlView;
    private Square mSquare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("正多边形及圆形");
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
                        Toast.makeText(this, "顶点法绘制正方形", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(this, "索引法绘制正方形", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            default:
                break;
        }
    }
}
