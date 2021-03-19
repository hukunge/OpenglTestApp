package joye.wang.rosclientapp.ros.message.sensor_msgs;

import joye.wang.rosclientapp.ros.message.std_msgs.Header;

/**
 * http://docs.ros.org/en/api/sensor_msgs/html/msg/LaserScan.html
 */
public class LaserScan {
    private Header header;
    private float angle_min;
    private float angle_max;
    private float angle_increment;
    private float time_increment;
    private float scan_time;
    private float range_min;
    private float range_max;
    private float[] ranges;
    private float[] intensities;
}
