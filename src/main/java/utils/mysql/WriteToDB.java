package utils.mysql;

import java.util.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class WriteToDB {
    private String url = "jdbc:mysql://127.0.0.1:3306/selenium_tests";
    private String user = "root";
    private String password = "welcome";

    public void addResultstoDB(String sessionID,Date startTime, Date endTime,String os,String os_version,String browser,String browser_version,String isException) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            long diffInSeconds  = endTime.getTime() - startTime.getTime();
            long duration = TimeUnit.MILLISECONDS.toSeconds(diffInSeconds);
            java.text.SimpleDateFormat sdf =
                    new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String startDate = sdf.format(startTime);
            String endDate = sdf.format(endTime);

            String sql = "INSERT INTO RegressionTests values (NULL, '"+sessionID+"','"+startDate+"','"+endDate+"','"+duration+"','"+os+"','"+os_version+"','"+browser+"','"+browser_version+"','"+isException+"',CURRENT_TIMESTAMP)";
            PreparedStatement statement = conn.prepareStatement(sql);

            int row = statement.executeUpdate();
            if (row > 0) {
                System.out.println("Test results updated for sessionID: "+sessionID);
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
