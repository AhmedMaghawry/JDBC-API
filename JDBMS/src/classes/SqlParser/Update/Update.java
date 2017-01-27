package classes.SqlParser.Update;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import classes.SqlParser.Statements;

public abstract class Update extends Statements {

    Pattern pattern;
    Matcher matcher;
    String input;
    
    public Update(String input) {
        this.input = input;
    }
    
    public abstract ArrayList<ArrayList<String>> update() throws Exception;
    
    protected String DummyCheck(String group) {
        String x = group;
        Matcher m = Pattern.compile("=\\s*(\\w+)").matcher(x);
        while (m.find()) {
            x = x.replace(m.group(1), "'" + m.group(1) + "'");
        }
        return x;
    }
    
    protected String[] SplitUpdate(String group) {
        return group.split("(?:\\s*=\\s*|\\s*,\\s*)");
    }
}
