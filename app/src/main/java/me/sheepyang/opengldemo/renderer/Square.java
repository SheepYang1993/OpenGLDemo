package me.sheepyang.opengldemo.renderer;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.view.View;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 彩色等腰直角三角形
 * Created by SheepYang on 2017-09-22.
 */

public class Square extends Shape {
    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "uniform mat4 vMatrix;" +
                    "varying vec4 vColor;" +
                    "attribute vec4 aColor;" +
                    "void main() {" +
                    "  gl_Position = vMatrix * vPosition;" +
                    "  vColor = aColor;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "varying vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private int mProgram;
    private float triangleCoords[] = {
            -0.5f, 0.5f, 0.0f, // top left
            -0.5f, -0.5f, 0.0f, // bottom left
            0.5f, -0.5f, 0.0f, // bottom right
            0.5f, -0.5f, 0.0f, // bottom right
            0.5f, 0.5f, 0.0f, // bottom right
            -0.5f, 0.5f, 0.0f, // bottom right
    };
    //设置颜色,顺序是rgba
    float color[] = {
            1f, 0.913f, 0.27f, 1f,// top
            0.568f, 1f, 0.8f, 1f,// bottom left
            0.945f, 0.215f, 0.462f, 1f,// bottom right
            1f, 0.913f, 0.27f, 1f,// top
            0.568f, 1f, 0.8f, 1f,// bottom left
            0.945f, 0.215f, 0.462f, 1f,// bottom right
    };
    private FloatBuffer vertexBuffer;
    private int mPositionHandle;
    private int mColorHandle;
    private int COORDS_PER_VERTEX = 3;

    private float[] mViewMatrix = new float[16];
    private float[] mProjectMatrix = new float[16];
    private float[] mMVPMatrix = new float[16];
    //顶点个数
    private int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    //顶点之间的偏移量
    private int vertexStride = COORDS_PER_VERTEX * 4; // 每个顶点四个字节
    private int mMatrixHandler;
    private FloatBuffer colorBuffer;
    private int mDrawType;
    private short index[] = {
            0, 1, 2, 3, 4,5
    };
    private ShortBuffer indexBuffer;

    public Square(View mView) {
        super(mView);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        //将背景设置为灰色
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        //申请底层空间
        ByteBuffer bb = ByteBuffer.allocateDirect(
                triangleCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());//设置字节顺序，其中ByteOrder.nativeOrder()是获取本机字节顺序
        vertexBuffer = bb.asFloatBuffer();//将坐标数据转换为FloatBuffer，用以传入给OpenGL ES程序
        vertexBuffer.put(triangleCoords);
        vertexBuffer.position(0);//设置缓冲区起始位置

        ByteBuffer cc = ByteBuffer.allocateDirect(index.length * 2);
        cc.order(ByteOrder.nativeOrder());
        indexBuffer = cc.asShortBuffer();
        indexBuffer.put(index);
        indexBuffer.position(0);

        ByteBuffer dd = ByteBuffer.allocateDirect(
                color.length * 4);
        dd.order(ByteOrder.nativeOrder());
        colorBuffer = dd.asFloatBuffer();
        colorBuffer.put(color);
        colorBuffer.position(0);

        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        //创建一个空的OpenGLES程序
        mProgram = GLES20.glCreateProgram();
        //将顶点着色器加入到程序
        GLES20.glAttachShader(mProgram, vertexShader);
        //将片元着色器加入到程序中
        GLES20.glAttachShader(mProgram, fragmentShader);
        //连接到着色器程序
        GLES20.glLinkProgram(mProgram);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        //计算宽高比
        float ratio = (float) width / height;
        //设置透视投影
        Matrix.frustumM(mProjectMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        //设置相机位置
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 7.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        //计算变换矩阵
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectMatrix, 0, mViewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);//重绘背景色
        //将程序加入到OpenGLES2.0环境
        GLES20.glUseProgram(mProgram);
        //获取变换矩阵vMatrix成员句柄
        mMatrixHandler = GLES20.glGetUniformLocation(mProgram, "vMatrix");
        //指定vMatrix的值
        GLES20.glUniformMatrix4fv(mMatrixHandler, 1, false, mMVPMatrix, 0);
        //获取顶点着色器的vPosition成员句柄
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        //启用三角形顶点的句柄
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        //准备三角形的坐标数据
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);
//        //获取片元着色器的vColor成员的句柄
//        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
//        //设置绘制三角形的颜色
//        GLES20.glUniform4fv(mColorHandle, 1, color, 0);


        // 获取片元着色器的vColor成员的句柄
        mColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");
        //设置绘制三角形的颜色
        GLES20.glEnableVertexAttribArray(mColorHandle);
        GLES20.glVertexAttribPointer(mColorHandle, 4,
                GLES20.GL_FLOAT, false,
                0, colorBuffer);

        /*
        * GL_POINTS：把每一个顶点作为一个点进行处理，顶点n即定义了点n，共绘制N个点
        * GL_LINES：把每一个顶点作为一个独立的线段，顶点2n－1和2n之间共定义了n条线段，总共绘制N/2条线段
        * GL_LINE_STRIP：绘制从第一个顶点到最后一个顶点依次相连的一组线段，第n和n+1个顶点定义了线段n，总共绘制n－1条线段
        * GL_LINE_LOOP：绘制从第一个顶点到最后一个顶点依次相连的一组线段，然后最后一个顶点和第一个顶点相连，第n和n+1个顶点定义了线段n，总共绘制n条线段
        * GL_TRIANGLES：把每个顶点作为一个独立的三角形，顶点3n－2、3n－1和3n定义了第n个三角形，总共绘制N/3个三角形
        * GL_TRIANGLE_STRIP：绘制一组相连的三角形，对于奇数n，顶点n、n+1和n+2定义了第n个三角形；对于偶数n，顶点n+1、n和n+2定义了第n个三角形，总共绘制N-2个三角形
        * GL_TRIANGLE_FAN：绘制一组相连的三角形，三角形是由第一个顶点及其后给定的顶点确定，顶点1、n+1和n+2定义了第n个三角形，总共绘制N-2个三角形
        * GL_QUADS：绘制由四个顶点组成的一组单独的四边形。顶点4n－3、4n－2、4n－1和4n定义了第n个四边形。总共绘制N/4个四边形
        * GL_QUAD_STRIP：绘制一组相连的四边形。每个四边形是由一对顶点及其后给定的一对顶点共同确定的。顶点2n－1、2n、2n+2和2n+1定义了第n个四边形，总共绘制N/2-1个四边形
        * GL_POLYGON：绘制一个凸多边形。顶点1到n定义了这个多边形。
        */
        if (mDrawType == 0) {
            //绘制正方形
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        } else {
            //索引法绘制正方形
            GLES20.glDrawElements(GLES20.GL_TRIANGLE_STRIP, index.length, GLES20.GL_UNSIGNED_SHORT, indexBuffer);
        }

        //禁止顶点数组的句柄
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

    public void changeDrawType() {
        if (mDrawType == 0) {
            mDrawType = 1;
        } else {
            mDrawType = 0;
        }
    }

    public int getDrawType() {
        return mDrawType;
    }
}
