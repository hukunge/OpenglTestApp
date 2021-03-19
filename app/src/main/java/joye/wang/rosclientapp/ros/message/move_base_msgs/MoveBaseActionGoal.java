package joye.wang.rosclientapp.ros.message.move_base_msgs;

import joye.wang.rosclientapp.ros.message.action_lib_msgs.GoalID;
import joye.wang.rosclientapp.ros.message.std_msgs.Header;
import lombok.Data;

@Data
public class MoveBaseActionGoal {
    Header header;
    GoalID goal_id;
    MoveBaseGoal goal;
}
