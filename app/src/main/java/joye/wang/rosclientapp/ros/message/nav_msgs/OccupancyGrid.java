package joye.wang.rosclientapp.ros.message.nav_msgs;

import joye.wang.rosclientapp.ros.message.MapMetaData;
import joye.wang.rosclientapp.ros.message.std_msgs.Header;
import lombok.Data;

/**
 * http://docs.ros.org/en/api/nav_msgs/html/msg/OccupancyGrid.html
 */
@Data
public class OccupancyGrid {
    public static final String MSG_TYPE = "nav_msgs/OccupancyGrid";

    public int[] data;
    public Header header;
    public MapMetaData info;
}
