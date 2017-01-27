package classes;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import interfaces.HeadIF;
import interfaces.IsDate;
import interfaces.IsFloat;
import interfaces.IsInteger;
import interfaces.IsString;
import interfaces.WriterInterface;

public class HeadImp implements HeadIF {

    public String Dir;
    private int operatingSystemNum;
    public static String formatType;
    int updateSize = 0;

    public HeadImp(String path, String saveType) {
        String operatingSystem = System.getProperty("os.name");
        operatingSystem = operatingSystem.substring(0, 3);
        if (operatingSystem.equals("Win")) {
            operatingSystemNum = 1;
            Dir = path + File.separator;
        } else {
            operatingSystemNum = 2;
            Dir = path + File.separator;
        }
        formatType = saveType.toLowerCase();
    }

    @Override
    public void MakeDB(final String Name) {
        File x = new File(Dir + Name);
        x.mkdir();

    }

    @Override
    public void DeleteDB(final String Name) {
        File file = new File(Dir + Name);
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory())
                    file.delete();
                else
                    f.delete();
            }
        }
        file.delete();
    }

    @Override
    public void MakeTable(final String Db, final String tableName, final ArrayList<DBNode> s) throws Exception {
        String DirExtended = Dir + Db + File.separator;
        File f = new File(DirExtended+tableName+"."+formatType);
        if(f.exists()){
            throw new RuntimeException();
        }
        WriterInterface fileWriter = FileSystemFactory.getInstance().fileBuilder(formatType, DirExtended);
        TabelImp emptyTable = new TabelImp(tableName, s);
        fileWriter.write(emptyTable);
    }

    @Override
    public void makeDtd(final String tabelName, final String path) {
        try {
            PrintWriter writer = new PrintWriter(path + tabelName + ".dtd", "UTF-8");
            writer.println("<!ELEMENT " + tabelName + " (DBNode+)>");
            writer.println("<!ELEMENT DBNode (Name, Type, Column)>");
            writer.println("<!ELEMENT Name (#PCDATA)>");
            writer.println("<!ELEMENT Type (#PCDATA)>");
            writer.println("<!ELEMENT Column (Row+)>");
            writer.println("<!ELEMENT Row (#PCDATA)>");
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void DeleteTable(final String Db, final String Name) {
        String DirExtended = null;
        switch (operatingSystemNum) {
            case 1:
                DirExtended = Dir + Db + File.separator;
                break;
            case 2:
                DirExtended = Dir + Db + File.separator;
        }
        File file = new File(DirExtended + Name + "."+formatType);
        //File file2 = new File(DirExtended + Name + ".dtd");
        //File file3 = new File(DirExtended + Name + ".json");
        if(!file.exists())
            throw new RuntimeException();
        if(!file.delete())
            throw new RuntimeException();
    }

    @Override
    public void insertIntoTable(final ArrayList<Object> x, final int index, final String dBase, final String tableName)
            throws Exception {
        WriterInterface aa = FileSystemFactory.getInstance().fileBuilder(formatType, Dir + dBase);
        ArrayList<DBNode> fields = (aa.read(tableName)).getTable();
        if (x.size() != fields.size() || fields.get(0).getColumn().size() < index) {
            throw new RuntimeException("Invalid Input");
        }
        int i = 0;
        for (i = 0; i < x.size(); i++) {
            if (x.get(i) != null) {
                if (x.get(i) instanceof String && fields.get(i) instanceof IsString
                        || x.get(i) instanceof Integer && fields.get(i) instanceof IsInteger
                        || x.get(i) instanceof Float && fields.get(i) instanceof IsFloat
                        || x.get(i) instanceof Date && fields.get(i) instanceof IsDate) {
                    fields.get(i).getColumn().add(index, x.get(i));
                } else {
                    throw new RuntimeException("Invalid Input");
                }
            } else {
                fields.get(i).getColumn().add(index, x.get(i));
            }
        }
        TabelImp xxx = new TabelImp(tableName, fields);
        aa.write(xxx);
    }

    @Override
    public void insertIntoTable(final ArrayList<Object> x, final String dBase, final String tableName)
            throws Exception {
        WriterInterface aa = FileSystemFactory.getInstance().fileBuilder(formatType, Dir + dBase);
        Print(x.toString());
        ArrayList<DBNode> fields = (aa.read(tableName)).getTable();
        int i = 0;
        for (i = 0; i < x.size(); i++) {
            if (x.get(i) != null) {
                if (x.get(i) instanceof String && fields.get(i) instanceof IsString
                        || x.get(i) instanceof Integer && fields.get(i) instanceof IsInteger
                        || x.get(i) instanceof Float && fields.get(i) instanceof IsFloat
                        || x.get(i) instanceof Date && fields.get(i) instanceof IsDate) {
                    fields.get(i).getColumn().add(x.get(i));
                } else {
                    throw new RuntimeException("Invalid Input");
                }
            } else {
                fields.get(i).getColumn().add(x.get(i));
            }
        }
        TabelImp xxx = new TabelImp(tableName, fields);
        aa.write(xxx);
    }

    @Override
    public void deleteFromTable(final int z, final String dBase, final String tableName) throws Exception {
        WriterInterface aa = FileSystemFactory.getInstance().fileBuilder(formatType, Dir + dBase);
        ArrayList<DBNode> fields = (aa.read(tableName)).getTable();
        int limits = fields.get(0).getColumn().size();
        if (fields.size() > 0) {
            int i = 0;
            for (i = 0; i < fields.size(); i++) {
                if (limits >= z) {
                    fields.get(i).getColumn().remove(z);
                } else {
                    throw new RuntimeException("List has no such element");
                }
            }
            TabelImp xxx = new TabelImp(tableName, fields);
            aa.write(xxx);
        } else {
            throw new RuntimeException("List is Empty");
        }
    }

    @Override
    public void deleteAll(final String dBase, final String tableName) throws Exception {
        WriterInterface xmlParser = FileSystemFactory.getInstance().fileBuilder(formatType, Dir + dBase);
        ArrayList<DBNode> fields = (xmlParser.read(tableName)).getTable();
        for (int i = 0; i < fields.size(); i++) {
            fields.get(i).getColumn().clear();
        }
        TabelImp rewrite = new TabelImp(tableName, fields);
        xmlParser.write(rewrite);
    }

    TabelImp table2;

    private void validateElement(final String target) {
        if (!table2.isIn(target)) {
            throw new RuntimeException("Invalid input");
        }
    }

    @Override
    public void modify(final String Db, final String Name, final ArrayList<ArrayList<String>> wantedCol,
            final ArrayList<Object> conditions) throws Exception {
        WriterInterface parser = FileSystemFactory.getInstance().fileBuilder(formatType, Dir + Db);
        table2 = parser.read(Name);
        for (int i = 0; i < wantedCol.size(); i++) {
            validateElement(wantedCol.get(i).get(0));
        }
        if (conditions == null) {
            for (int i = 0; i < table2.getNumberOfRows(); i++) {
                for (int j = 0; j < wantedCol.size(); j++) {
                    table2.SetinRow(i, wantedCol.get(j).get(0), wantedCol.get(j).get(1));
                }
            }
        } else {
            validateElement(conditions.get(0).toString());
            System.out.println(conditions.toString());
            for (int i = 0; i < table2.getNumberOfRows(); i++) {
                String tmpString = table2.getRow(i).get(table2.getIndex(String.valueOf(conditions.get(0)).trim()));
                if (conditions.get(1).equals("=")) {
                    if (tmpString.equals(conditions.get(2).toString())) {
                        applyModifiation(wantedCol, i);
                        updateSize++;
                    }
                } else if (conditions.get(2) instanceof Integer
                        && table2.getTable().get(table2.getIndex(conditions.get(0).toString())) instanceof IsInteger) {
                    if (conditions.get(1).equals(">")) {
                        if (Integer.parseInt(tmpString) > Integer.parseInt(conditions.get(2).toString())) {
                            applyModifiation(wantedCol, i);
                            updateSize++;
                        }
                    } else if (conditions.get(1).equals("<")) {
                        if (Integer.parseInt(tmpString) < Integer.parseInt(conditions.get(2).toString())) {
                            applyModifiation(wantedCol, i);
                            updateSize++;
                        }
                    } else
                        throw new RuntimeException("Invalid operation");
                } else if (conditions.get(2) instanceof String
                        && table2.getTable().get(table2.getIndex(conditions.get(0).toString())) instanceof IsString) {
                    if (conditions.get(1).equals(">")) {
                        if (tmpString.compareTo(conditions.get(2).toString()) > 0) {
                            applyModifiation(wantedCol, i);
                            updateSize++;
                        }
                    } else if (conditions.get(1).equals("<")) {
                        if (tmpString.compareTo(conditions.get(2).toString()) < 0) {
                            applyModifiation(wantedCol, i);
                            updateSize++;
                        }
                    } else
                        throw new RuntimeException("Invalid operation");
                } else if (conditions.get(2) instanceof Float
                        && table2.getTable().get(table2.getIndex(conditions.get(0).toString())) instanceof IsFloat) {
                    if (conditions.get(1).equals(">")) {
                        if (Float.parseFloat(tmpString) > Float.parseFloat(conditions.get(2).toString())) {
                            applyModifiation(wantedCol, i);
                            updateSize++;
                        }
                    } else if (conditions.get(1).equals("<")) {
                        if (Float.parseFloat(tmpString) < Float.parseFloat(conditions.get(2).toString())) {
                            applyModifiation(wantedCol, i);
                            updateSize++;
                        }
                    } else
                        throw new RuntimeException("Invalid operation");
                } else if (conditions.get(2) instanceof Date
                        && table2.getTable().get(table2.getIndex(conditions.get(0).toString())) instanceof IsDate) {
                    DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
                    if (conditions.get(1).equals(">")) {
                        Date xx = formatter.parse(tmpString);
                        java.sql.Date comp = new java.sql.Date(xx.getTime());
                        if (comp.after((java.sql.Date)conditions.get(2))) {
                            applyModifiation(wantedCol, i);
                            updateSize++;
                        }
                    } else if (conditions.get(1).equals("<")) {
                        Date xx = formatter.parse(tmpString);
                        java.sql.Date comp = new java.sql.Date(xx.getTime());
                        if (comp.after((java.sql.Date)conditions.get(2))) {
                            applyModifiation(wantedCol, i);
                            updateSize++;
                        }
                    } else
                        throw new RuntimeException("Invalid operation");
                } else
                    throw new RuntimeException("Invalid Types");
            }
        }
        parser.write(table2);
    }

    private void applyModifiation(final ArrayList<ArrayList<String>> wantedCol, final int rowIndex) {
        for (int i = 0; i < wantedCol.size(); i++) {
            table2.SetinRow(rowIndex, wantedCol.get(i).get(0), wantedCol.get(i).get(1));
        }
    }

    @Override
    public ArrayList<DBNode> select(final String tableName, final String dBase, final ArrayList<String> wanted)
            throws Exception {
        WriterInterface parser = FileSystemFactory.getInstance().fileBuilder(formatType, Dir + dBase);
        ArrayList<DBNode> fields = (parser.read(tableName)).getTable();
        TabelImp table = new TabelImp(tableName, fields);
        ArrayList<DBNode> tmp = new ArrayList<>();
        for (String element : wanted) {
            if (!table.isIn(element)) {
                return null;
            }
            tmp.add(table.getFromTable(table.getIndex(element)));
        }
        return tmp;
    }

    public void Print(Object object) {
        //System.out.println(object);
    }
    
    public int getUpdateSize() {
        return updateSize;
    }
}