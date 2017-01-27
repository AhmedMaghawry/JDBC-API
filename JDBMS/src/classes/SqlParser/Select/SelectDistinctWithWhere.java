package classes.SqlParser.Select;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class SelectDistinctWithWhere extends Select {

    String tableName;

    public SelectDistinctWithWhere(String input) {
        super(input);
    }

    @Override
    public ArrayList<ArrayList<String>> select() throws Exception {
        pattern = Pattern.compile("(?i)\\s*select\\s+Distinct\\s+(\\w+(\\s*,\\s*\\w+)*)\\s+from\\s+(\\w+)\\s*\\s+where\\s+(\\w+)\\s*(\\W)\\s*(?:'(\\w+(\\s*\\w+)*)'|(\\d+)|(\\d+.\\d+|'?\\d{4}-\\d{2}-\\d{2}'?));?");
        matcher = pattern.matcher(input);
        if (matcher.find()) {
            tableName = matcher.group(3);
            String colms = matcher.group(1);
            SelectColumnsWithWhere x = new SelectColumnsWithWhere(matcher.group(0).replaceAll("(?i)distinct",""));
            x.select();
            ArrayList<ArrayList<String>> columns = x.getSelected();
            ArrayList<ArrayList<String>> columnsEdited = ellimenate(columns);
            System.out.println("The Result of the Distinct");
            drawTable(divideComma(colms), columnsEdited);
            System.out.println();
            setColumms(divideComma(colms));
            return columnsEdited;
        } else {
            throw new RuntimeException("Invalid Query for Select");
        }
    }
}
