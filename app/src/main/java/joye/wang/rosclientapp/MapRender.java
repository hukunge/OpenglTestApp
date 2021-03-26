package joye.wang.rosclientapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.opengl.GLES31;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import joye.wang.rosclientapp.ros.message.nav_msgs.OccupancyGrid;

public class MapRender implements GLSurfaceView.Renderer {

    // 顶点坐标
    private final FloatBuffer surfaceVertices;
    // 纹理坐标
    private final FloatBuffer textureVertices;
    private Context context;

    private Bitmap bitmap;


    private float x, y;
    private float scale;

    private GestureDetector translateGestureDetector;
    private ScaleGestureDetector scaleGestureDetector;

    public MapRender(Context context) {
        this.context = context;
        surfaceVertices = BufferUtil.allocateFloatBuffer(new float[]{
                0.0f, 0.0f,  // Bottom left
                1, 0.0f,  // Bottom right
                0.0f, 1.0f,  // Top left
                1.0f, 1.0f,  // Top right
        });
        textureVertices = BufferUtil.allocateFloatBuffer(new float[]{
                0.0f, 0.0f, // Bottom left
                1000.0f, 0.0f, // Bottom right
                0.0f, 1000.0f, // Top left
                1000.0f, 1000.0f, // Top right
        });
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
        // read json data from file
        String mapData = readData(context, "map.json");
        // convert json to java object
        OccupancyGrid map = JSONObject.parseObject(mapData, OccupancyGrid.class);
        initMap(map);
    }


    public boolean onTouchEvent(MotionEvent event) {
        translateGestureDetector.onTouchEvent(event);
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }

    public static final int[] gradient = getGradient();

    // color array
    private static int[] getGradient() {
        int[] grad = new int[102];

        for (int i = 0; i <= 101; i++) {
            int color;

            if (i == 101) {
                color = Color.argb(128, 0, 0, 0);
            } else {
                color = Color.argb((int) (255 / 100f * (100 - i)), 0, 0, 0);
            }

            grad[i] = color;
        }

        return grad;
    }

    // generate bitmap from data
    private void initMap(OccupancyGrid occupancyGrid) {
        int height = occupancyGrid.info.height;
        int width = occupancyGrid.info.width;

        Bitmap newMap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);

        int[] pixels;
        int bytePixel;

        int curr = 0;
        for (int y = 0; y < height; y++) {
            pixels = new int[width];

            for (int x = 0; x < width; x++) {
                // Pixels are ARGB packed ints.
                bytePixel = occupancyGrid.data[curr++];

                if (bytePixel == -1) {
                    // black color
                    pixels[x] = gradient[101];
                } else {
                    // gray color
                    pixels[x] = gradient[bytePixel];
                }
            }

            newMap.setPixels(pixels, 0, width, 0, y, width, 1);
        }
        bitmap = newMap;
    }

    /**
     * 从assets文件中读取字符串
     */
    public String readData(Context context, String filename) {
        AssetManager assetManager = context.getAssets();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open(filename)));
            String line = reader.readLine();
            StringBuilder data = new StringBuilder();
            while (line != null) {
                data.append(line);
                line = reader.readLine();
            }
            return data.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(-width / 2f, width / 2f, -height / 2f, height / 2f, -10000f, 10000f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glTranslatef(x, y, -1.5f);
        if (scale == 0) scale = 1;
        gl.glScalef(scale , scale, 0);
        // 开启纹理
        GLES31.glEnable(GLES31.GL_TEXTURE_2D);
        final int[] textureIds = new int[1];
        //创建一个纹理对象
        GLES31.glGenTextures(1, textureIds, 0);

        // 绑定纹理到OpenGL
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D, textureIds[0]);

        //设置默认的纹理过滤参数
        GLES31.glTexParameterf(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_MIN_FILTER, GLES31.GL_NEAREST);
        GLES31.glTexParameterf(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_MAG_FILTER, GLES31.GL_NEAREST);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_BASE_LEVEL, 0);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D, GLES31.GL_TEXTURE_MAX_LEVEL, 0);
        // 加载bitmap到纹理中
        GLUtils.texImage2D(GLES31.GL_TEXTURE_2D, 0, bitmap, 0);

        // vertex
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, surfaceVertices);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureVertices);

        GLES31.glDrawArrays(GLES31.GL_TRIANGLE_STRIP, 0, 4);
    }
}
