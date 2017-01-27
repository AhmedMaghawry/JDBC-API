package classes.SqlParser.Update;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import classes.HeadImp;
import classes.SqlParser.sqlFactoryImp;

public class UpdateAll extends Update{
    
    String tableName;
    
    public UpdateAll(String input) {
        super(input);
    }

    @Override
    public ArrayList<ArrayList<String>> update() throws Exception {
        pattern = Pattern.compile(
                "(?i)\\s*update\\s+(\\w+)\\s+set\\s+((\\w+)\\s*=\\s*(?:'([\\w\\s]+)'|'?\\d{4}-\\d{2}-\\d{2}'?|\\d+.\\d+|(\\d+))(\\s*,\\s*(\\w+)\\s*=\\s*(?:'([\\w\\s]+)'|'?\\d{4}-\\d{2}-\\d{2}'?|\\d+.\\d+|(\\d+)))*)(\\s+where\\s+(\\w+)\\s*(\\W)\\s*((?:'([\\w\\s]+)'|'?\\d{4}-\\d{2}-\\d{2}'?|\\d+.\\d+|(\\d+)))?)?\\s*;*");
        matcher = pattern.matcher(input);
        ArrayList<String> Colms = new ArrayList<>();
        ArrayList<String> rows = new ArrayList<>();
        if (matcher.find()) {
            tableName = matcher.group(1);
            String[] Spliter = SplitUpdate(DummyCheck(matcher.group(2)));
            for (int i = 0; i < Spliter.length; i += 2) {
                Colms.add(Spliter[i]);
            }
            for (int i = 1; i < Spliter.length; i += 2) {
                rows.add(Spliter[i].replaceAll("'", ""));
            }
            UpdateIt(Colms, rows);
            return LastReturn(tableName);
        } else
            throw new RuntimeException("Invalid Query for Update All");
    }
    
    private void UpdateIt(ArrayList<String> colms, ArrayList<String> rows)
            throws Exception {
        ArrayList<ArrayList<String>> modifications = new ArrayList<>();
        for (int i = 0; i < colms.size(); i++) {
            ArrayList<String> node = new ArrayList<>();
            node.add(colms.get(i));
            node.add(rows.get(i));
            modifications.add(node);
        }
        sqlFactoryImp.head.modify(dBName, tableName, modifications, null);
    }
}
