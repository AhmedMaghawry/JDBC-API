package classes;

import interfaces.WriterInterface;

public class FileSystemFactory {

    private static FileSystemFactory reference = null;

    private FileSystemFactory(){

    }

    public WriterInterface fileBuilder(final String formatType,final String dbDir){

        WriterInterface Writer = null ;
        Print("Build   "+dbDir);
        if (formatType.equals("xml")){
            Writer = XMLParser.getInstance(dbDir);
        } else if (formatType.equals("json")) {
            Writer = JSONParser.getInstance(dbDir);
        } else {
        //  Writer = JSONWriter.getInstance(dbDir);
        }
        return Writer;
    }

    private void Print(String dbDir) {
        //System.out.println(dbDir);
    }

    public static FileSystemFactory  getInstance(){

        if (reference == null){
            reference = new FileSystemFactory();
        }

        return reference;

    }
}