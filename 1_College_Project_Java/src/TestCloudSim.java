import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.Log;
import java.util.Calendar;

public class TestCloudSim {
    public static void main(String[] args) {
        Log.printLine("Starting CloudSim...");
        try {
            int num_user = 1;
            Calendar calendar = Calendar.getInstance();
            boolean trace_flag = false;
            CloudSim.init(num_user, calendar, trace_flag);
            Log.printLine("CloudSim initialized successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            Log.printLine("CloudSim initialization failed.");
        }
    }
}
