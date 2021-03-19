package joye.wang.rosclientapp.ros.message.geometry_msgs;

import lombok.Data;

/**
 * http://docs.ros.org/en/api/geometry_msgs/html/msg/Twist.html
 */
@Data
public class Twist {
    public static final String MSG_TYPE = "geometry_msgs/Twist";
    Vector3 linear;
    Vector3 angular;
}
