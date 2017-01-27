package classes.SqlParser;

import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import classes.*;
import classes.SqlParser.Alter.*;
import classes.SqlParser.Create.Create;
import classes.SqlParser.Create.DataBase;
import classes.SqlParser.Create.Table;
import classes.SqlParser.Delete.*;
import classes.SqlParser.Drop.Drop;
import classes.SqlParser.Insert.Insert;
import classes.SqlParser.Insert.InsertAll;
import classes.SqlParser.Insert.InsertRow;
import classes.SqlParser.Select.*;
import classes.SqlParser.Update.*;
import interfaces.*;

public class sqlFactoryImp {

    private String Input;
    public static String DBName;
    private String TableName;
    public static HeadImp head;
    int changed = 0;
    String[] columnsSelected;
    ArrayList<ArrayList<String>> ret;
    
    public sqlFactoryImp (String path, String saveType) {
        head = new HeadImp(path, saveType);
    }
    public ArrayList<ArrayList<String>> execute(String Input) throws Exception {
        this.Input = Input;
        return Distributor();
    }

    public ArrayList<ArrayList<String>> Distributor() throws Exception {
        Pattern sele = Pattern.compile(
                "(?i)\\s*((?:select|create\\s+table|create\\s+database|drop\\s+database|drop\\s+table|delete|update|insert\\s+into|use|alter\\s+table))");
        Matcher matcher = sele.matcher(Input.trim().replaceAll(" +", " "));
        if (matcher.find()) {
            Statements statements = null;
            if (matcher.group(1).toLowerCase().equals("select"))
                return SelectMachine();
            else if (matcher.group(1).toLowerCase().equals("create table")) {
                statements = new Table(Input);
                return ((Create) statements).create();
            }
            else if (matcher.group(1).toLowerCase().equals("create database")){
                statements = new DataBase(Input);
                return ((Create) statements).create();
            }
            else if (matcher.group(1).toLowerCase().equals("drop table")){
                statements = new classes.SqlParser.Drop.Table(Input);
                return ((Drop) statements).drop();
            }
            else if (matcher.group(1).toLowerCase().equals("drop database")){
                statements = new classes.SqlParser.Drop.DataBase(Input);
                return ((Drop) statements).drop();
            }
            else if (matcher.group(1).toLowerCase().equals("delete")){
                return DeleteMachine(statements);
            }
            else if (matcher.group(1).toLowerCase().equals("update")){
                return UpdateMachine(statements);
            }
            else if (matcher.group(1).toLowerCase().equals("insert into"))
                return InsertMachine();
            else if (matcher.group(1).toLowerCase().equals("use"))
                return useMachine() ;
            else if (matcher.group(1).toLowerCase().equals("alter table")){
                return AlterMachine();
            }
            else
                throw new RuntimeException("InValid Query");
        } else {
            throw new RuntimeException("InValid Query");
        }
    }

    private ArrayList<ArrayList<String>> useMachine() {
        Pattern use = Pattern.compile("(?i)\\s*use\\s+(\\w+);*");
        Matcher matcher = use.matcher(Input);
        if (matcher.find()) {
            DBName = matcher.group(1);
        }
        return null;
    }
    
    public ArrayList<ArrayList<String>> AlterMachine() throws Exception {
        Pattern alterPattern = Pattern.compile("(?i)\\s*alter\\s+table\\s+(\\w+)\\s+(?:add\\s+(?:column\\s+|(\\w+))\\s+(\\w+)|drop\\s+column\\s+(\\w+))\\s*;*");
        Matcher alterMatcher = alterPattern.matcher(Input);
        if(alterMatcher.find()){
            TableName = alterMatcher.group(1);
            if(alterMatcher.group(4)==null){
                Statements statement = new AddColumn(Input);
                return ((Alter)statement).alter();
            }else {
                Statements statement = new DropColumn(Input);
                return ((Alter)statement).alter();
            }
        }
        else {
            throw new RuntimeException("Invalid Query for Alter");
        }
    }


    public ArrayList<ArrayList<String>> SelectMachine() throws Exception {
        Pattern selecCol = Pattern.compile(
                "(?i)\\s*select\\s+((?:\\w+|\\*|Distinct))(?:\\s+\\w+|)(\\s*,\\s*(\\w+))*\\s+from\\s+(\\w+)(\\s+where\\s+(\\w+)\\s*(\\W)\\s*(?:'(\\w+(\\s*\\w+)*)'|(\\d+)|(\\d+.\\d+|'?\\d{4}-\\d{2}-\\d{2}'?)))?\\s*(order\\s+by\\s+((\\w+)(\\s*,\\s*(\\w+))*)\\s*((?:asc|desc))?)?;?");
        Matcher matcherCol = selecCol.matcher(Input);
        Statements statements;
        if (matcherCol.find()) {
            TableName = matcherCol.group(4);
            String Columns = matcherCol.group(1);
            String[] colms = divideComma(Columns);
            if (matcherCol.group(5) != null) {
                if (colms.length == 1 && colms[0].equals("*")) {
                    statements = new SelectAllWithWhere(Input);
                    ret = ((Select) statements).select();
                    columnsSelected = ((Select) statements).getColumms();
                    return ret;
                }else if (Columns.contains("Distinct") || Columns.contains("distinct")) { 
                    statements = new SelectDistinctWithWhere(Input);
                    ret = ((Select) statements).select();
                    columnsSelected = ((Select) statements).getColumms();
                    return ret;
                }else {
                    statements = new SelectColumnsWithWhere(Input);
                    ret = ((Select) statements).select();
                    columnsSelected = ((Select) statements).getColumms();
                    return ret;
                }
            } else {
                if (colms.length == 1 && colms[0].equals("*")) {
                    statements = new SelectAllWithoutWhere(Input);
                    ret = ((Select) statements).select();
                    return ret;
                } else if (Columns.contains("Distinct") || Columns.contains("distinct")) { 
                    statements = new SelectDistinct(Input);
                    ret = ((Select) statements).select();
                    columnsSelected = ((Select) statements).getColumms();
                    return ret;
                } else {
                    statements = new SelectColumnsWithoutWhere(Input);
                    ret = ((Select) statements).select();
                    columnsSelected = ((Select) statements).getColumms();
                    return ret;
                }
            }
        } else
            throw new RuntimeException("Invalid Query for Select");
    }

    private String[] divideComma(String columns) {
        // TODO Auto-generated method stub
        columns = columns.replaceAll("\\s*,\\s*", ",");
        return columns.split(",");
    }

    public ArrayList<ArrayList<String>> DeleteMachine(Statements statements) throws Exception {
        // TODO Auto-generated method stub
        Pattern DeleteAll = Pattern.compile("(?i)\\s*delete\\s+\\*?\\s*from\\s+(\\w+)\\s*;?");
        Matcher matcherAll = DeleteAll.matcher(Input);
        Pattern DeleteRow = Pattern.compile(
                "(?i)\\s*delete\\s+from\\s+(\\w+)\\s+where\\s+(\\w+)\\s*(\\W)\\s*(?:'(\\w+(\\s*\\w+)*)'|(\\d+)|(\\d+.\\d+)|'?(\\d+-\\d+-\\d+)'?|'?\\d{4}-\\d{2}-\\d{2}'?)\\s*;*");
        Matcher matcherRow = DeleteRow.matcher(Input);
        if (matcherRow.find()) {
            TableName = matcherRow.group(1);
            statements = new DeleteWithWhere(Input);
            ArrayList<ArrayList<String>> beforeDelete = (statements).LastReturn(TableName);
            ArrayList<ArrayList<String>> afterDelete =  ((Delete) statements).delete() ; 
            changed = (beforeDelete.get(0).size()-afterDelete.get(0).size());
            return afterDelete;
        } else if (matcherAll.find()) {
            TableName = matcherAll.group(1);
            statements = new DeleteAll(Input);
            ArrayList<ArrayList<String>> beforeDelete = ((Delete) statements).LastReturn(TableName);
            ArrayList<ArrayList<String>> afterDelete =  ((Delete) statements).delete() ; 
            changed = (beforeDelete.get(0).size()-afterDelete.get(0).size());
            return afterDelete;
            } else {
            throw new RuntimeException("Invalid Input");
        }
    }

    public ArrayList<ArrayList<String>> UpdateMachine(Statements statements) throws Exception {
        // TODO Auto-generated method stub
        Pattern Update = Pattern.compile(
                "(?i)\\s*update\\s+(\\w+)\\s+set\\s+((\\w+)\\s*=\\s*(?:'([\\w\\s]+)'|'?\\d{4}-\\d{2}-\\d{2}'?|\\d+.\\d+|(\\d+))(\\s*,\\s*(\\w+)\\s*=\\s*(?:'([\\w\\s]+)'|'?\\d{4}-\\d{2}-\\d{2}'?|\\d+.\\d+|(\\d+)))*)(\\s+where\\s+(\\w+)\\s*(\\W)\\s*((?:'([\\w\\s]+)'|'?\\d{4}-\\d{2}-\\d{2}'?|\\d+.\\d+|(\\d+)))?)?\\s*;*");
        Matcher Updatem = Update.matcher(Input);
        if (Updatem.find()) {
            TableName = Updatem.group(1);
            if (Updatem.group(10) != null) {
                // There is Where
                statements = new UpdateWithWhere(Input);
                ArrayList<ArrayList<String>> xx=((Update) statements).update();
                changed = (((UpdateWithWhere) statements).getUpdateSize());
                return xx;
            } else {
                // There isn't where
                statements = new UpdateAll(Input);
                ArrayList<ArrayList<String>> ans = statements.LastReturn(TableName);
                changed = (ans.get(0).size());
                return ((Update) statements).update();
            }
        } else {
            throw new RuntimeException("Invalid Input");
        }
    }

    public ArrayList<ArrayList<String>> InsertMachine() throws Exception {
        // TODO Auto-generated method stub
        Pattern FullInsert = Pattern.compile(
                "(?i)\\s*insert\\s+into\\s+(\\w+)(\\s*\\(\\s*(\\w+)((\\s*,\\s*(\\w+))*)\\s*\\))?\\s*values\\s*\\(\\s*((?:'*(\\w+(\\s*\\w+)*)'*|-?\\d+|-?\\d+.\\d+|'?\\d{4}-\\d{2}-\\d{2}'?))((\\s*,\\s*(?:'*(\\w+(\\s*\\w+)*)'*|(-?\\d+)|-?\\d+.\\d+|'?\\d{4}-\\d{2}-\\d{2}'?))*)\\s*\\)\\s*;*");
        Matcher FullInsertm = FullInsert.matcher(Input);
        if (FullInsertm.find()) {
            //DBName = FullInsertm.group(1);
            TableName = FullInsertm.group(1);
            if (FullInsertm.group(2) != (null)) {
                Statements statements = new InsertRow(Input);
                ArrayList<ArrayList<String>> ans = ((Insert) statements).insert();
                changed = 1;
                return (ans);
            } else {
                Statements statements = new InsertAll(Input);
                ArrayList<ArrayList<String>> ans = ((Insert) statements).insert();
                changed = 1;
                return (ans);
                }
        } else {
            throw new RuntimeException("Invalid Input");
        }
    }

    public String[] Colms() throws Exception {
        Print(DBName);
        Print(TableName);
        WriterInterface parser = FileSystemFactory.getInstance().fileBuilder(head.formatType,head.Dir+DBName);
        TabelImp Table = parser.read(TableName);
        ArrayList<DBNode> x = Table.getTable();
        Print(x.toString());
        String[] Names = new String[x.size()];
        int i = 0;
        for (DBNode y : x) {
            Names[i] = y.getName();
            i++;
        }
        return Names;
    }
    
    public void Print(String x) {
        //System.out.println(x);
    }

    public TabelImp getTable() throws Exception {
        WriterInterface fileWriter = FileSystemFactory.getInstance().fileBuilder(head.formatType, head.Dir+DBName);
        TabelImp tableToUse = fileWriter.read(TableName);
        ArrayList<ArrayList<String>> rows = new ArrayList<>(ret);
        String[] colmnsNames = tableToUse.getCoulmnNames();
        if(colmnsNames.length == rows.size()){
            columnsSelected = colmnsNames;
        }
        ArrayList<DBNode> columns = convertToDBNodes(rows, colmnsNames, tableToUse);
        return new TabelImp(TableName, columns);
    }
    
    private ArrayList<DBNode> convertToDBNodes(ArrayList<ArrayList<String>> rows, String[] colmnsNames,
            TabelImp tableToUse) {
        ArrayList<DBNode> result = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            LinkedList<Object> row = new LinkedList<>();
            for (int j = 0; j < rows.get(0).size(); j++) {
                row.add(convertToType(rows.get(i).get(j)));
            }
            DBNode res = null;
            if(!row.isEmpty()) {
            if(row.get(0) instanceof Integer) {
                res = new IntegerNode(columnsSelected[i]);
                res.setColumn(row);
            } else if (row.get(0) instanceof String) {
                res = new StringNode(columnsSelected[i]);
                res.setColumn(row);
            } else if (row.get(0) instanceof Float) {
                res = new FloatNode(columnsSelected[i]);
                res.setColumn(row);
            } else if (row.get(0) instanceof Date) {
                res = new DateNode(columnsSelected[i]);
                res.setColumn(row);
            }
            }
            result.add(res);
        }
        return result;
    }

    public int getChangedSize() {
        return changed;
    }
    
    protected Object convertToType(String value) {
        Object val = null;
        try {
            val = value;
        } catch (Exception e) {}
        try {
            val = Float.parseFloat(value);
        } catch (Exception e) {}
        try {
            val = Integer.parseInt(value);
        } catch (Exception e) {}
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
            Date TTT = new Date(formatter.parse(value).getTime());
            val = TTT;
        } catch (Exception e) {}
        return val;
    }

}
// insert into Finall.Ezz values ('Dodo');
// delete from Final.Test where Name='Ahmed';
// insert into Final.Test2 (Name, ID) values ('Ahmed','5');
// Select * from Final.Test where Name='Ahmede';
// update Final.Test2 set ID=9 where Name='MAr';