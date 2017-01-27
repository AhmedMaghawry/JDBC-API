package classes.SqlParser.Drop;

import java.util.ArrayList;
import java.util.regex.Pattern;

import classes.HeadImp;
import classes.SqlParser.sqlFactoryImp;

public class Table extends Drop {

    String tableName;
    
    public Table(String input) {
        super(input);
    }

    @Override
    public ArrayList<ArrayList<String>> drop() throws Exception {
        pattern = Pattern.compile("(?i)\\s*drop\\s*table\\s*(\\w+)\\s*");
        matcher = pattern.matcher(input);
        if (matcher.find()) {
            tableName = matcher.group(1);
        }
        if (dBName != null && tableName != null) {
            try {
                sqlFactoryImp.head.DeleteTable(dBName, tableName);
            } catch (Exception e) {
                throw new RuntimeException("table not found");
            }
        }
        return null;
    }
}
