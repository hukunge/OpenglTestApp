package joye.wang.rosclientapp.ros.message.move_base_msgs;

import joye.wang.rosclientapp.ros.message.geometry_msgs.PoseStamped;
import lombok.Data;

@Data
public class MoveBaseGoal {
    private PoseStamped target_pose;
}
