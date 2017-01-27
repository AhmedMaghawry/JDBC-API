package classes.SqlParser.Delete;

import java.util.ArrayList;
import java.util.regex.Pattern;

import classes.HeadImp;
import classes.SqlParser.sqlFactoryImp;

public class DeleteAll extends Delete {

    String tableName;

    public DeleteAll(String input) {
        super(input);
    }

    @Override
    public ArrayList<ArrayList<String>> delete() throws Exception {
        this.pattern = Pattern.compile("(?i)\\s*delete\\s+\\*?\\s*from\\s+(\\w+)\\s*;?");
        this.matcher = pattern.matcher(input);
        if (matcher.find()) {
            tableName = matcher.group(1);
            sqlFactoryImp.head.deleteAll(dBName, tableName);
            return LastReturn(tableName);
        } else
            throw new RuntimeException("Invalid Query for Delete all statement");
    }
}
