package joye.wang.rosclientapp.ros.message.sensor_msgs;

import joye.wang.rosclientapp.ros.message.std_msgs.Header;
import lombok.Data;

/**
 * http://docs.ros.org/en/api/sensor_msgs/html/msg/CompressedImage.html
 */
@Data
public class CompressedImage {
    public static final String TYPE = "sensor_msgs/CompressedImage";

    Header header;
    String format;
    byte[] data;
}
