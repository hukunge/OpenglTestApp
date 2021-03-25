package joye.wang.rosclientapp;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ColorTriangleRender render;
    private GLSurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        surfaceView = new GLSurfaceView(this);
        render = new ColorTriangleRender(this);
        surfaceView.setRenderer(render);
        setContentView(surfaceView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (render.onTouchEvent(event)) {
            surfaceView.requestRender();
            return true;
        }
        return super.onTouchEvent(event);
    }
}