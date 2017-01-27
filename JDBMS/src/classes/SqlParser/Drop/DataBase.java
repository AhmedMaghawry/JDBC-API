package classes.SqlParser.Drop;

import java.util.ArrayList;
import java.util.regex.Pattern;

import classes.HeadImp;
import classes.SqlParser.sqlFactoryImp;

public class DataBase extends Drop {

    public DataBase(String input) {
        super(input);
    }

    @Override
    public ArrayList<ArrayList<String>> drop() throws Exception {
        pattern = Pattern.compile("(?i)\\s*drop\\s*database\\s*(\\w+)\\s*");
        matcher = pattern.matcher(input);
        if (matcher.find()) {
            String DBName = matcher.group(1);
            if (DBName != null) {
                try {
                    sqlFactoryImp.head.DeleteDB(DBName);
                } catch (Exception e) {
                    throw new RuntimeException("Data base not found");
                }
            }
        } else 
            throw new RuntimeException("Invalid Query for Drop DataBase");
        return null;
    }

}
