package joye.wang.rosclientapp.ros.message.geometry_msgs;

import lombok.Data;

/**
 * http://docs.ros.org/en/api/geometry_msgs/html/msg/Pose.html
 */
@Data
public class Pose {

    public static final String MSG_TYPE = "geometry_msgs/Pose";

    // 位置
    public Point position;
    // 旋转
    public Quaternion orientation;

}
