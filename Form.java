package UI;

import java.util.ArrayList;
import java.util.Scanner;
//UI form 
public class Form {
    String title;

    ArrayList<Field> fields = new ArrayList<Field>();//info you are asking from user is filed
    FormAction action;
    boolean defaultPreviousValues = false;
    public Form(String title, FormAction action) {
        this.title = title;
        this.action = action;
    }
    public Form(String title, FormAction action, boolean defaultPreviousValues) {
        this.title = title;
        this.action = action;
        this.defaultPreviousValues = defaultPreviousValues;
    }

    public Form addField(String name, String label) {
        Field f = new Field(name,label);
        return addField(f);
    }
    public Form addField(String name, String label, String value) {
        Field f = new Field(name,label,value);
        return addField(f);
    }
    public Form addField(String name, String label, FieldValidator validator) {
        Field f = new Field(name,label, validator);
        return addField(f);
    }
    public Form addField(Field field) {
        fields.add(field);
        return this;
    }
    public String getFieldValue(String name) {
        for(Field f : fields)
            if(f.name.equals(name))
                return f.value;
        return null;
    }
    public boolean display() {
        System.out.println("\n\t\t"+title);
        Scanner sc = new Scanner(System.in);
        for(int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            if(defaultPreviousValues)
            		System.out.print("\n\t"+field.label+(field.value == null ? "" : ": "+field.value) + ": ");
            else
        			System.out.print("\n\t"+field.label + ": ");
            String ln = sc.nextLine();
            if(!defaultPreviousValues || ln.trim().length() > 0) { // user entered some thing
        			field.value = ln;
            }
            //field.value = sc.nextLine();
            while(!field.validator.validate(field, this)) {
                System.out.print("\n\t"+field.label+": ");
                ln = sc.nextLine();
                if(!defaultPreviousValues || ln.trim().length() > 0) { // user entered some thing
                		field.value = ln;
                }
//                field.value = sc.nextLine();
            }
        }
        // allows user to submit the form or cancel
        Form that = this;
        Menu formMenu = new Menu("")
            .add("s","Submit", new MenuAction() { public void execute(String s) { action.submit(that);}} )
            .add("c", "Cancel", new MenuAction() { public void execute(String s) { action.submit(that);}} );
        String option = formMenu.display();
        // execute action 
        if(option.equals("s"))
            return action.submit(this);
        else
            return action.cancel(this);
    }
    public String toString() {
        String fieldStr = "";
        for(Field f: fields)
            fieldStr += "  "+f.toString();
        return "Form["+title + "Fields:[ " + fieldStr +"] ]";
    }
}
