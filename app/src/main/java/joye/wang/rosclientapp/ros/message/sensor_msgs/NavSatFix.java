package joye.wang.rosclientapp.ros.message.sensor_msgs;

import joye.wang.rosclientapp.ros.message.std_msgs.Header;
import lombok.Data;

/**
 * http://docs.ros.org/en/api/sensor_msgs/html/msg/NavSatFix.html
 */
@Data
public class NavSatFix {
    public static final String MSG_TYPE = "sensor_msgs/NavSatFix";

    Header header;
    NavSatStatus status;
    double latitude;
    double longitude;
    double altitude;
    double[] position_covariance;
    int COVARIANCE_TYPE_UNKNOWN = 0;
    int COVARIANCE_TYPE_APPROXIMATED = 1;
    int COVARIANCE_TYPE_DIAGONAL_KNOWN = 2;
    int COVARIANCE_TYPE_KNOWN = 3;
    int position_covariance_type;
}
