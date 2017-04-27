package UI;
// this class is interface because multiple implementation. to write abstract validate method

public interface FieldValidator{
    public boolean validate(Field field, Form form);
}
