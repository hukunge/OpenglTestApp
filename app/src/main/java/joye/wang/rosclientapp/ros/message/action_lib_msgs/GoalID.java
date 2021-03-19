package joye.wang.rosclientapp.ros.message.action_lib_msgs;

import joye.wang.rosclientapp.ros.message.RosTime;
import lombok.Data;

@Data
public class GoalID {
    RosTime stamp;
    String id;
}
