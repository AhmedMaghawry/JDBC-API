package classes.SqlParser.Insert;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import classes.FileSystemFactory;
import classes.HeadImp;
import classes.TabelImp;
import classes.XMLParser;
import classes.SqlParser.Statements;
import interfaces.WriterInterface;

public abstract class Insert extends Statements {

    Pattern pattern;
    Matcher matcher;
    String input;
    
    public Insert(String input) {
        this.input = input;
    }
    
    public abstract ArrayList<ArrayList<String>> insert() throws Exception;

    protected String[] divideCommaVal(String group) {
        String x = group.replaceAll("\\s*'\\s*", "");
        return x.split("\\s*,\\s*");
    }

    protected String[] divideCommaColm(String group) {
        return group.split("(\\s*),\\s*");
    }
    
    protected boolean checkInsert(String tableName, String firstValue, String[] restValues)
            throws Exception {
        TabelImp x = parser.read(tableName);
        // Check Name
        if (x.getTable().size() == 0)
            return false;
        if (!(restValues.length == 1 && restValues[0].equals(""))) {
            if (x.getTable().size() != restValues.length)
                return false;
            // Check Type
            for (int i = 1; i < x.GetTableSize(); i++) {
                // the Eroeeeeeeeeeeeerrrrrrrrrrrrrrrrrrrrrrr is
                // Hereeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
                /*
                 * if (isString(restValues[i]) == x.getTable().get(i).getType())
                 * return false;
                 */
            }
        }
        // the Eroeeeeeeeeeeeerrrrrrrrrrrrrrrrrrrrrrr is
        // Hereeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
        /*
         * if (isString(firstValue) == x.getTable().get(0).getType()) return
         * false;
         */
        return true;
    }
    
    public String firstValue(String x) {
        if (x.startsWith("'")){
            String y;
            y = x.substring(1, x.length()-1);
            return y;
        }
        return x;
    }
}
