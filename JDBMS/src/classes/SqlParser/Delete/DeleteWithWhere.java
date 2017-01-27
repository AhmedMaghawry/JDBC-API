package classes.SqlParser.Delete;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import classes.HeadImp;
import classes.SqlParser.sqlFactoryImp;

public class DeleteWithWhere extends Delete {
    
    String tableName;
    
    public DeleteWithWhere(String input) {
        super(input);
    }

    @Override
    public ArrayList<ArrayList<String>> delete() throws Exception {
        pattern = Pattern.compile(
                "(?i)\\s*delete\\s+from\\s+(\\w+)\\s+where\\s+(\\w+)\\s*(\\W)\\s*(?:'(\\w+(\\s*\\w+)*)'|(\\d+)|(\\d+.\\d+)|'?(\\d+-\\d+-\\d+)'?|'?\\d{2}-\\d{2}-\\d{4}'?)\\s*;?");
        matcher = pattern.matcher(input);
        if (matcher.find()) {
            tableName = matcher.group(1);
            String colmnName = matcher.group(2);
            Print(colmnName);
            String condition = matcher.group(3);
            Print(condition);
            String element = (matcher.group(6) != null) ? matcher.group(6) : (matcher.group(4) != null) ? matcher.group(4) : (matcher.group(7) != null) ? matcher.group(7) : matcher.group(8);
            Print(element);
            DeleteCondetion(getIndexiesForWhere(tableName, colmnName, condition, element), condition);
            return LastReturn(tableName);
        } else {
            throw new RuntimeException("Invalid Query for Delete with Condition");
        }
    }

    private void DeleteCondetion(ArrayList<Integer> rowindex, String condition) throws Exception {
        // TODO Auto-generated method stub
        if (rowindex.size() == 0)
            throw new RuntimeException("There is No Rows");
        if (condition.equals("="))
            for (int i = 0; i < rowindex.size(); i++) {
                sqlFactoryImp.head.deleteFromTable(rowindex.get(i) - i, dBName, tableName);
            }
        else if (condition.equals(">")) {
            for (int i = 0; i < rowindex.size(); i++) {
                sqlFactoryImp.head.deleteFromTable(rowindex.get(i) - i, dBName, tableName);
            }
        } else if (condition.equals("<")) {
            for (int i = 0; i < rowindex.size(); i++) {
                sqlFactoryImp.head.deleteFromTable(rowindex.get(i) - i, dBName, tableName);
            }
        } else {
            throw new RuntimeException("Invalid Operator");
        }
    }
    
    public void Print(String x) {
        System.out.println(x);
    }
}
