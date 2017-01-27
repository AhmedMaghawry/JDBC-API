package classes.SqlParser.Insert;

import java.util.ArrayList;
import java.util.regex.Pattern;

import classes.FileSystemFactory;
import classes.HeadImp;
import classes.TabelImp;
import classes.XMLParser;
import classes.SqlParser.sqlFactoryImp;
import interfaces.WriterInterface;

public class InsertRow extends Insert {

    String tableName;

    public InsertRow(String input) {
        super(input);
    }

    @Override
    public ArrayList<ArrayList<String>> insert() throws Exception {
        pattern = Pattern.compile(
                "(?i)\\s*insert\\s+into\\s+(\\w+)(\\s*\\(\\s*(\\w+)((\\s*,\\s*(\\w+))*)\\s*\\))?\\s*values\\s*\\(\\s*((?:'*(\\w+(\\s*\\w+)*)'*|-?\\d+|-?\\d+.\\d+|'?\\d{4}-\\d{2}-\\d{2}'?))((\\s*,\\s*(?:'*(\\w+(\\s*\\w+)*)'*|(-?\\d+)|-?\\d+.\\d+|'?\\d{4}-\\d{2}-\\d{2}'?))*)\\s*\\)\\s*;*");
        matcher = pattern.matcher(input);
        if (matcher.find()) {
            tableName = matcher.group(1);
            String firstColm = matcher.group(3);
            String[] restColm = divideCommaColm(matcher.group(4));
            String firstValue = firstValue(matcher.group(7));
            String[] restValues;
            String[] xx = { "" };
            if (matcher.group(10) != null) {
                restValues = divideCommaVal(matcher.group(10));
            } else {
                restValues = xx;
            }
            if (restColm.length != restValues.length)
                throw new RuntimeException("Invalid parameters");
            insertIntoTable(tableName, firstColm, restColm, firstValue, restValues);
            return LastReturn(tableName);
        } else
            throw new RuntimeException("Invalid Query for Insert");
    }

    private void insertIntoTable(String tableName, String firstColm, String[] restColm, String firstValue,
            String[] restValues) throws Exception {
        TabelImp y = parser.read(tableName);
        ArrayList<Object> ob = new ArrayList<>();
        for (int i = 0; i < y.GetTableSize(); i++) {
            ob.add(null);
        }
        ob.set(y.getIndex(firstColm), convertToType(firstValue));
        for (int i = 1; i < restColm.length; i++) {
            ob.set(y.getIndex(restColm[i]), convertToType(restValues[i]));
        }
        sqlFactoryImp.head.insertIntoTable(ob, dBName, tableName);
    }
}
