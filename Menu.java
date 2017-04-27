package UI;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    String title;
    ArrayList<String> options = new ArrayList<String>();
    ArrayList<String> items = new ArrayList<String>();
    ArrayList<MenuAction> actions = new ArrayList<MenuAction>();
//    final static Scanner sc = new Scanner(System.in);
    public Menu(String title) {
        this.title = title;
    }
    public Menu add(String option, String item, MenuAction action) {
        options.add(option);
        items.add(item);
        actions.add(action);
        return this;
    }
    public String display() {
        System.out.println("\n\t\t"+title);
        String optionString = "";
        for(int i = 0; i < options.size(); i++) {
            System.out.println("\t"+options.get(i)+")\t"+items.get(i));
            optionString += options.get(i);
        }
        System.out.print("Enter your selection: ");
        Scanner sc = new Scanner(System.in); //.useDelimiter("");
        String input;
        while(optionString.indexOf(input = sc.next()) == -1) {
            sc.nextLine();
            System.out.print(input + " is invalid option, Enter your selection: ");
        }
        return input;
    }
    public void run() {
        String input = display();
        MenuAction action = actions.get(options.indexOf(input));
        if(action != null)
            action.execute(input);
    }
   
}
