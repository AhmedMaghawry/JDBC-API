package classes;

import java.util.ArrayList;

public class DrawTable {

    String[] Colms;
    ArrayList<ArrayList<String>> Rows;

    public DrawTable(String[] Colms, ArrayList<ArrayList<String>> Rows) {
        this.Colms = Colms;
        this.Rows = Rows;
    }

    public void DrawLine() {
        for (int j = 0; j < Colms.length; j++) {
            System.out.print("+");
            for (int y = 0; y < Colms[j].length(); y++) {
                System.out.print("-");
            }
            for (int k = 0; k < 15 - Colms[j].length(); k++) {
                System.out.print("-");
            }
        }
        System.out.print("+");
        System.out.println("");
    }

    public void Title() {
        for (int j = 0; j < Colms.length; j++) {
            System.out.print("|");
            System.out.print(Colms[j]);
            for (int k = 0; k < 15 - Colms[j].length(); k++) {
                System.out.print(" ");
            }
        }
        System.out.print("|");
        System.out.println();
    }
    
    public void Columns() {
        for (int p = 0; p < Rows.get(0).size(); p++) {
            for (int j = 0; j < Rows.size(); j++) {
                System.out.print("|");
                System.out.print((Rows.get(j).get(p) != null)?Rows.get(j).get(p) : "null");
                for (int k = 0; k < 15 - ((Rows.get(j).get(p) != null)?Rows.get(j).get(p) : "null").length(); k++) {
                    System.out.print(" ");
                }
            }
            System.out.print("|");
            System.out.println();
        }
    }
}
