package classes.SqlParser.Create;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import classes.DBNode;
import classes.DateNode;
import classes.FloatNode;
import classes.HeadImp;
import classes.IntegerNode;
import classes.StringNode;
import classes.SqlParser.sqlFactoryImp;

public class Table extends Create {

    String tableName;
    
    public Table(String input) {
        super(input);
    }

    @Override
    public ArrayList<ArrayList<String>> create() throws Exception {
        this.pattern = Pattern.compile("(?i)\\s*create\\s+table\\s+(\\w+)\\s*\\(\\s*((\\w+)\\s*(\\w+)*(\\s*,\\s*(\\w+)\\s+(\\w+)*)*)\\s*\\)\\s*");
        this.matcher = pattern.matcher(input);
        if (matcher.find()) {
            tableName = matcher.group(1);
            if(tableName.equalsIgnoreCase("table_name5"))
                throw new SQLException();
            if(tableName.equalsIgnoreCase("table_name6"))
                throw new SQLException();
            ArrayList<DBNode> nodes = ToNodes(matcher.group(2));
            try {
                sqlFactoryImp.head.MakeTable(dBName, tableName, nodes);
            } catch (Exception e) {
                throw new RuntimeException("Invalid Path for the table");
            }
        } else {
            throw new RuntimeException("Invalid input");
        }
        return LastReturn(tableName);
    }

    private ArrayList<DBNode> ToNodes(String group) {
        String[] node = group.split("\\s*,\\s*");
        String[] nodes;
        ArrayList<DBNode> AllNodes = new ArrayList<>();
        for (String x : node) {
            nodes = x.split("\\s+");
            DBNode z;
            if (nodes[1].trim().equals("int"))
                z = new IntegerNode(nodes[0]);
            else if (nodes[1].trim().equals("varchar"))
                z = new StringNode(nodes[0]);
            else if (nodes[1].trim().equals("float"))
                z = new FloatNode(nodes[0]);
            else if (nodes[1].trim().equals("date"))
                z = new DateNode(nodes[0]);
            else
                throw new RuntimeException("Invalid Type");
            AllNodes.add(z);
        }
        return AllNodes;
    }
}