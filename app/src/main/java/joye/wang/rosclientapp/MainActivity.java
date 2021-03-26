package joye.wang.rosclientapp;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ColorTriangleRender render;
    private MapRender mapRender;
    private GLSurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        surfaceView = new GLSurfaceView(this);
        render = new ColorTriangleRender(this);
        mapRender = new MapRender(this);
        surfaceView.setRenderer(mapRender);
        setContentView(surfaceView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mapRender.onTouchEvent(event)) {
            surfaceView.requestRender();
            return true;
        }
        return super.onTouchEvent(event);
    }
}