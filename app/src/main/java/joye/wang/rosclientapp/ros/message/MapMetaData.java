package joye.wang.rosclientapp.ros.message;

import joye.wang.rosclientapp.ros.message.geometry_msgs.Pose;
import lombok.Data;

/**
 * http://docs.ros.org/en/api/nav_msgs/html/msg/MapMetaData.html
 */
@Data
public class MapMetaData {
    public RosTime map_load_time;
    // 每个像素点代表几米，width*resolution = 地图实际宽度(米)
    public float resolution;
    public int width;
    public int height;
    public Pose origin;
}
