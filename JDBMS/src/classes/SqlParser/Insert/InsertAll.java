package classes.SqlParser.Insert;

import java.util.ArrayList;
import java.util.regex.Pattern;

import classes.HeadImp;
import classes.SqlParser.sqlFactoryImp;

public class InsertAll extends Insert {

    String tableName;
    
    public InsertAll(String input) {
        super(input);
    }

    @Override
    public ArrayList<ArrayList<String>> insert() throws Exception {
        pattern = Pattern.compile(
                "(?i)\\s*insert\\s+into\\s+(\\w+)(\\s*\\(\\s*(\\w+)((\\s*,\\s*(\\w+))*)\\s*\\))?\\s*values\\s*\\(\\s*((?:'*(\\w+(\\s*\\w+)*)'*|-?\\d+|-?\\d+.\\d+|'?\\d{4}-\\d{2}-\\d{2}'?))((\\s*,\\s*(?:'*(\\w+(\\s*\\w+)*)'*|(-?\\d+)|-?\\d+.\\d+|'?\\d{4}-\\d{2}-\\d{2}'?))*)\\s*\\)\\s*;*");
        matcher = pattern.matcher(input);
        if (matcher.find()) {
            tableName = matcher.group(1);
            String firstValue = firstValue(matcher.group(7));//LOGMEONTOCseD#19PLEASE
            String[] xx = { "" };
            String[] restValues;
            if (matcher.group(10) != null) {
                restValues = divideCommaVal(matcher.group(10));
            } else {
                restValues = xx;
            }
            if (checkInsert(tableName, firstValue, restValues)) {
                insertIntoTable(tableName, firstValue, restValues);
            } else
                throw new RuntimeException("Invalid Parameters for Insert");
            
            return LastReturn(tableName);
        } else
            throw new RuntimeException("Invalid Query for Insert");
    }

    private void insertIntoTable(String tableName, String firstValue, String[] restValues) throws Exception {
        ArrayList<Object> ob = new ArrayList<>();
        ob.add(convertToType(firstValue));
        if (!(restValues.length == 1 && restValues[0].equals(""))) {
            for (int i = 1; i < restValues.length; i++) {
                ob.add(convertToType(restValues[i]));
            }
        }
        sqlFactoryImp.head.insertIntoTable(ob, dBName, tableName);
    }
}
