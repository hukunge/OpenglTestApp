package joye.wang.rosclientapp.ros.message.geometry_msgs;

import joye.wang.rosclientapp.ros.message.std_msgs.Header;
import lombok.Data;

@Data
public class PoseStamped {
    public static final String MSG_TYPE = "geometry_msgs/PoseStamped";

    private Header header;

    private Pose pose;
}
