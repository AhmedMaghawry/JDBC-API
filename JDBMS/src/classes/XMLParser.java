package classes;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.sql.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import classes.SqlParser.sqlFactoryImp;
import interfaces.IsDate;
import interfaces.IsFloat;
import interfaces.IsInteger;
import interfaces.IsString;
import interfaces.WriterInterface;
import interfaces.XmlFactoryIF;

public class XMLParser implements XmlFactoryIF,WriterInterface {

    private static String dBDir;
    org.w3c.dom.Document doc;
    private ArrayList<DBNode> fields;
    private String dBDirExtended = null;
    private static XMLParser reference = null;

    private XMLParser() {
        String operatingSystem = System.getProperty("os.name");
        operatingSystem = operatingSystem.substring(0, 3);
        if (operatingSystem.equals("Win")) {
            dBDirExtended = dBDir + File.separator;
        } else {
            dBDirExtended = dBDir + File.separator;
        }
    }

    @Override
    public TabelImp read(final String tableName) throws Exception {
        fields = new ArrayList<>();
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Print(dBDirExtended);
        File inputFile = new File(dBDirExtended + tableName + ".xml");
        InputStream inputStream = new FileInputStream(inputFile);
        org.w3c.dom.Document doc = builder.parse(dBDirExtended + tableName + ".xml");
        NodeList rootElement = doc.getElementsByTagName(tableName);
        NodeList tableFields = rootElement.item(0).getChildNodes();
        for (int i = 1; i < tableFields.getLength(); i = i + 2) {
            NodeList currentNode = tableFields.item(i).getChildNodes();
            LinkedList<Object> readColumn = new LinkedList<>();
            org.w3c.dom.Node name = currentNode.item(1);
            org.w3c.dom.Node type = currentNode.item(3);
            readColumn = readColms(currentNode.item(5).getChildNodes(), type, readColumn);
            DBNode element = createColmn(type, name.getTextContent());
            element.setColumn(readColumn);
            fields.add(element);
        }
        inputStream.close();
        Print(fields.toString());
        return new TabelImp(tableName, fields);
    }

    private void Print(String dBDirExtended2) {
        //System.out.println("XMl Parser   "+dBDirExtended2);
    }

    private DBNode createColmn(Node type, String readName) {
        DBNode element = null;
        if ((type.getTextContent()).equals("string")) {
            element = new StringNode(readName);
        } else if ((type.getTextContent()).equals("integer")) {
            element = new IntegerNode(readName);
        } else if ((type.getTextContent()).equals("float")) {
            element = new FloatNode(readName);
        } else if ((type.getTextContent()).equals("date")) {
            element = new DateNode(readName);
        }
        return element;
    }

    private LinkedList<Object> readColms(NodeList rows, Node type, LinkedList<Object> readColumn) throws Exception, ParseException {
        for (int j = 1; j < rows.getLength(); j = j + 2) {
            org.w3c.dom.Node row = rows.item(j);
            if ((type.getTextContent()).equals("string")) {
                readColumn.add(row.getTextContent());
            } else if ((type.getTextContent()).equals("integer")) {
                if (!row.getTextContent().equals("null"))
                    readColumn.add(Integer.parseInt(row.getTextContent()));
                else
                    readColumn.add(null);
            } else if ((type.getTextContent()).equals("float")) {
                if (!row.getTextContent().equals("null"))
                    readColumn.add(Float.parseFloat(row.getTextContent()));
                else
                    readColumn.add(null);
            } else if ((type.getTextContent()).equals("date")) {
                if (!row.getTextContent().equals("null")){
                    DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
                    Date date = new Date(formatter.parse(row.getTextContent()).getTime());
                    readColumn.add(date);
                }else
                    readColumn.add(null);
            }
        }
        return readColumn;
    }

    @Override
    public void write(final TabelImp table) throws Exception {
        Print("Rwirte    "+dBDirExtended);
        File file = new File(dBDirExtended + table.GetTableName() + ".xml");
        file.delete();
        DocumentBuilderFactory docFact = DocumentBuilderFactory.newInstance();
        DocumentBuilder build = docFact.newDocumentBuilder();
        doc = build.newDocument();
        DOMImplementation domImpl = doc.getImplementation();
        DocumentType doctype = domImpl.createDocumentType(table.GetTableName(), "SYSTEM",
                table.GetTableName() + ".dtd");
        doc.appendChild(doctype);
        Element root = doc.createElement(table.GetTableName());
        doc.appendChild(root);
        ArrayList<DBNode> colmns = table.getTable();
        createNodes(colmns, root);
        rotineProtocol(table, doctype);
    }

    private void createNodes(ArrayList<DBNode> colmns, Element root) {
        for (int i = 0; i < colmns.size(); i++) {
            Element dBnode = doc.createElement("DBNode");
            root.appendChild(dBnode);
            Element name = doc.createElement("Name");
            name.appendChild(doc.createTextNode(colmns.get(i).getName()));
            Element type = doc.createElement("Type");
            type.appendChild(doc.createTextNode(getType(colmns, i)));
            Element column = doc.createElement("Column");
            for (int j = 0; j < colmns.get(i).getColumn().size(); j++) {
                Element row = doc.createElement("Row");
                if (colmns.get(i).getColumn().get(j) instanceof Date) {
                    DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
                    row.appendChild(doc.createTextNode(format.format(colmns.get(i).getColumn().get(j))));
                }else {
                    row.appendChild(doc.createTextNode(String.valueOf(colmns.get(i).getColumn().get(j))));
                }
                column.appendChild(row);
            }
            dBnode.appendChild(name);
            dBnode.appendChild(type);
            dBnode.appendChild(column);
        }
    }

    private void rotineProtocol(TabelImp table, DocumentType doctype) throws Exception {
        TransformerFactory tranFactory = TransformerFactory.newInstance();
        Transformer aTransformer;
        aTransformer = tranFactory.newTransformer();
        aTransformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
        aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
        aTransformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
        DOMSource source = new DOMSource(doc);
        FileWriter fos = new FileWriter(dBDirExtended+table.GetTableName()+".xml");
        StreamResult result = new StreamResult(fos);
        aTransformer.transform(source, result);
        new HeadImp(sqlFactoryImp.head.Dir,sqlFactoryImp.head.formatType).makeDtd(table.GetTableName(), dBDirExtended);
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
    
    private void setDirectory(final String dBDir){
        this.dBDir = dBDir ;
        String operatingSystem = System.getProperty("os.name");
        operatingSystem = operatingSystem.substring(0, 3);
        if (operatingSystem.equals("Win")) {
            dBDirExtended = dBDir + File.separator;
        } else {
            dBDirExtended = dBDir + File.separator;
        }
    }
    
    public static XMLParser getInstance(final String dbDir){

        if (reference != null){
            reference.setDirectory(dbDir);
        }
        reference = new XMLParser();
        reference.setDirectory(dbDir);
        return reference;
    }
}