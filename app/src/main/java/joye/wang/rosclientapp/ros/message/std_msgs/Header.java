package joye.wang.rosclientapp.ros.message.std_msgs;

import joye.wang.rosclientapp.ros.message.RosTime;
import lombok.Data;

@Data
public class Header {
    int seq;
    RosTime stamp;
    String frame_id;
}
