package joye.wang.rosclientapp;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class BufferUtil {
    public static FloatBuffer allocateFloatBuffer(float[] array) {
        //先初始化buffer，数组的长度*4，因为一个float占4个字节
        ByteBuffer bb = ByteBuffer.allocateDirect(array.length * 4);
        //以本机字节顺序来修改此缓冲区的字节顺序
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer = bb.asFloatBuffer();
        //将给定float[]数据从当前位置开始，依次写入此缓冲区
        floatBuffer.put(array);
        //设置此缓冲区的位置。如果标记已定义并且大于新的位置，则要丢弃该标记。
        floatBuffer.position(0);
        return floatBuffer;
    }
}
