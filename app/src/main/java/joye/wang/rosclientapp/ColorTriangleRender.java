package joye.wang.rosclientapp;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class ColorTriangleRender implements GLSurfaceView.Renderer {

    /*private float[] vertexPoints = new float[]{
            0.0f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f
    };*/
    private float[] vertexPoints = new float[]{
            0.0f, 50f, 0.0f,
            -50f, -50f, 0.0f,
            50f, -50f, 0.0f
    };

    private float[] mColor = new float[]{
            1, 1, 0, 1,
            0, 1, 1, 1,
            1, 0, 1, 1
    };
    private FloatBuffer triangleBuffer, colorBuffer;
    private GestureDetector translateGestureDetector;
    private ScaleGestureDetector scaleGestureDetector;

    public ColorTriangleRender(Context context) {
        triangleBuffer = ByteBuffer.allocateDirect(vertexPoints.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        triangleBuffer.put(vertexPoints);
        triangleBuffer.position(0);

        colorBuffer = ByteBuffer.allocateDirect(mColor.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        colorBuffer.put(mColor);
        colorBuffer.position(0);

        translateGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            // 必须返回true，否则所有后续操作都会被忽略
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                // translate(distanceX, distanceY);
                x = e1.getX() - e2.getX();
                y = e1.getY() - e2.getY();
                return super.onScroll(e1, e2, distanceX, distanceY);
            }
        });
        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                scale = detector.getScaleFactor();
                return super.onScale(detector);
            }
        });
    }

    public boolean onTouchEvent(MotionEvent event) {
        translateGestureDetector.onTouchEvent(event);
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }

    private float x, y;
    private float scale;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 设置白色为清屏
        gl.glClearColor(1, 1, 1, 1);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        float ratio = (float) width / height;
        // 设置OpenGL场景的大小,(0,0)表示窗口内部视口的左下角，(w,h)指定了视口的大小
        gl.glViewport(0, 0, width, height);
        // 设置投影矩阵
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(-width / 2f, width / 2f, -height / 2f, height / 2f, -10000f, 10000f);
        // 设置视口的大小
        //gl.glFrustumf(-1, 1, -1, 1, 1, 2);
        //以下两句声明，以后所有的变换都是针对模型(即我们绘制的图形)
        gl.glMatrixMode(5888);
        //gl.glEnable(3042);
        //gl.glBlendFunc(770, 771);
        //gl.glDisable(2929);
        //gl.glLoadIdentity();
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        if (x > 10)
            x = 10f;
        if (y > 10)
            y = 10f;
        gl.glTranslatef(x / 100f, y / 100f, -1.5f);
        if (scale > 10) scale = 10;
        if (scale == 0) scale = 1;
        gl.glScalef(scale, scale, 0);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, triangleBuffer);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);

        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glFinish();
    }
}
