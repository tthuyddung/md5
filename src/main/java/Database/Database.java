//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    public Database() {
    }

    public static Connection connectDb() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/admin", "root", "");
            return connect;
        } catch (Exception var1) {
            Exception e = var1;
            e.printStackTrace();
            return null;
        }
    }
}
