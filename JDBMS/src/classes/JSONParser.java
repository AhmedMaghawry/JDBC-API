package classes;

import java.io.File;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

import classes.SqlParser.sqlFactoryImp;

import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;

import interfaces.IsDate;
import interfaces.IsFloat;
import interfaces.IsInteger;
import interfaces.IsString;
import interfaces.WriterInterface;

public class JSONParser implements WriterInterface{
    
    private String DBDirExtended = null;
    private static String dBDir;
    private static JSONParser reference = null;
    
    private JSONParser(){
        String operatingSystem = System.getProperty("os.name");
        operatingSystem = operatingSystem.substring(0, 3);
        if(operatingSystem.equals("Win")) {
            DBDirExtended = dBDir + File.separator;
        } else {
            DBDirExtended = dBDir + File.separator;
        }
    }

    //The ProtoccolBuffer code here
    @Override
    public TabelImp read(final String TableName) throws Exception {
        ArrayList<DBNode> fields = new ArrayList<>();
        File inputFile = new File(DBDirExtended + TableName + ".json");
        FileReader inputStream = new FileReader(inputFile);
    	org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
        Object obj = parser.parse(inputStream);
		JSONObject root = (JSONObject) obj;
        JSONArray table = (JSONArray) root.get(TableName);
        for(int i=0;i<table.size();i++){
              JSONObject node = (JSONObject) table.get(i);
              String name = (String) node.get("Name");
              String dbNodeType =(String) node.get("Type");
              DBNode x = createColmn(dbNodeType, name);
              JSONArray col=(JSONArray) node.get("Columns");
              for(int j=0;j<col.size();j++){
                  Object val = convertToType(col.get(j).toString());
                  x.getColumn().add(val);
              }
             fields.add(x);
        }
        inputStream.close();
        return new TabelImp(TableName, fields);
    }

    private DBNode createColmn(String type, String readName) {
        DBNode element = null;
        if (type.equals("string")) {
            element = new StringNode(readName);
        } else if ((type).equals("integer")) {
            element = new IntegerNode(readName);
        } else if ((type).equals("float")) {
            element = new FloatNode(readName);
        } else if ((type).equals("date")) {
            element = new DateNode(readName);
        }
        return element;
    }
    
    @Override
    public void write(final TabelImp Table) throws Exception {
        File file = new File(DBDirExtended+Table.GetTableName()+".json");
        file.delete();
        JSONObject obj = new JSONObject();
        JSONArray table = new JSONArray();
        ArrayList<DBNode> s = Table.getTable();
        for(int i=0;i<s.size();i++){
            JSONObject DBnode = new JSONObject();
            DBnode.put("Name",s.get(i).getName());
            DBnode.put("Type",getType(s,i));         
            JSONArray columns = new JSONArray();
            for(int j=0;j<s.get(i).getColumn().size();j++){
                if(s.get(i).getColumn().get(j) != null) {
                if (s.get(i).getColumn().get(j) instanceof Date) {
                    DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
                    columns.add(format.format(s.get(i).getColumn().get(j)));
                }else {
                    columns.add(s.get(i).getColumn().get(j));
                }
                } else {
                    columns.add("null");
                }
            }
            DBnode.put("Columns",columns);
            table.add(DBnode);
        }
        obj.put(Table.GetTableName(), table);
        FileWriter fos = new FileWriter(DBDirExtended + Table.GetTableName() + ".json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        fos.write(gson.toJson(obj));
        new HeadImp(sqlFactoryImp.head.Dir,sqlFactoryImp.head.formatType).makeDtd(Table.GetTableName(), DBDirExtended);
        fos.close();
    }

    private String getType(ArrayList<DBNode> s, int i) {
        String t = null;
        if (s.get(i) instanceof IsInteger) {
            t = "integer";
        } else if (s.get(i) instanceof IsString) {
            t = "string";
        } else if (s.get(i) instanceof IsFloat) {
            t = "float";
        } else if (s.get(i) instanceof IsDate) {
            t = "date";
        }
        return t;
    }
    
    private void setDirectory(final String Directory){
        this.dBDir = Directory;
        String operatingSystem = System.getProperty("os.name");
        operatingSystem = operatingSystem.substring(0, 3);
        if(operatingSystem.equals("Win")) {
            DBDirExtended = dBDir + File.separator;
        } else {
            DBDirExtended = dBDir + File.separator;
        }
    }
    
    public static JSONParser getInstance(final String dbDir){

        if (reference != null){
            reference.setDirectory(dbDir);
        }
        reference = new JSONParser();
        reference.setDirectory(dbDir);
        return reference;
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