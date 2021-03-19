package joye.wang.rosclientapp.ros.message.geometry_msgs;

import lombok.Data;

@Data
public class Quaternion {
    // cos(angle/2)
    public double w;
    public double x;
    public double y;
    // sin(angle/2)
    public double z;
}
