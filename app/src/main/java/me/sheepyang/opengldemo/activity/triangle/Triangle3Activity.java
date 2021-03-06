package me.sheepyang.opengldemo.activity.triangle;

import android.opengl.GLSurfaceView;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.sheepyang.opengldemo.R;
import me.sheepyang.opengldemo.activity.BaseActivity;
import me.sheepyang.opengldemo.renderer.triangle.Triangle3;

/**
 * 画一个彩色等腰直角三角形
 */
public class Triangle3Activity extends BaseActivity {
    @BindView(R.id.gl_view)
    GLSurfaceView mGlView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triangle3);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("彩色等腰直角三角形");
        mGlView.setEGLContextClientVersion(2);//创建一个OpenGL ES 2.0 context
        mGlView.setRenderer(new Triangle3(mGlView));
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
}
