package me.sheepyang.opengldemo;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.sheepyang.opengldemo.render.Triangle2;

/**
 * 画一个等腰直角三角形
 */
public class Triangle2Activity extends AppCompatActivity {
    @BindView(R.id.gl_view)
    GLSurfaceView mGlView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triangle);
        ButterKnife.bind(this);
        mGlView.setEGLContextClientVersion(2);//创建一个OpenGL ES 2.0 context
        mGlView.setRenderer(new Triangle2(mGlView));
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
