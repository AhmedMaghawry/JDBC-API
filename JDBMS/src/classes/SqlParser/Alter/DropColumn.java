package classes.SqlParser.Alter;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import classes.DBNode;
import classes.FileSystemFactory;
import classes.TabelImp;
import interfaces.WriterInterface;

public class DropColumn extends Alter {
    String tableName, columnName;

    public DropColumn(String input) {
        super(input);
        // TODO Auto-generated constructor stub
    }

    @Override
    public ArrayList<ArrayList<String>> alter() throws Exception {
        // TODO Auto-generated method stub
        Pattern alterPattern = Pattern.compile(
                "(?i)\\s*alter\\s+table\\s+(\\w+)\\s+drop\\s+column\\s+(\\w+)\\s*");
        Matcher alterMatcher = alterPattern.matcher(input);
        if (alterMatcher.find()) {
            tableName = alterMatcher.group(1);
            columnName = alterMatcher.group(2);
              TabelImp table = parser.read(tableName);
            if(columnExist(table ,columnName)){
                ArrayList<DBNode>columns = deleteNode(table,columnName);
                TabelImp updated = updateNewTable(columns);
                parser.write(updated);
                return LastReturn(tableName);
            }
            else 
                throw new RuntimeException("Column name not found");
        } else
            throw new RuntimeException("Invalid Query for Alter");
    }

    private boolean columnExist(TabelImp table,String columnName) throws Exception {
        for (int i = 0; i < table.getTable().size(); i++) {
            if (table.getTable().get(i).getName().equals(columnName))
                return true;
        }
        return false;
    }

    private ArrayList<DBNode> deleteNode( TabelImp table,String columnName) throws Exception {
        for (int i = 0; i < table.getTable().size(); i++) {
            if (table.getTable().get(i).getName().equals(columnName) ) {
                table.getTable().remove(i);
            }
        }
        return table.getTable();
    }
    private TabelImp updateNewTable(ArrayList<DBNode>all){
        TabelImp newTable = new TabelImp(tableName, all);
        return newTable;
    }
}