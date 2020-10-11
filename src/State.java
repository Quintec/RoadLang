import java.util.*;
public class State {
    private HashMap<String, Object> vars;
    public State() {
        vars = new HashMap<String, Object>();
    }
    public void setVariableValue(String name, Object val) {
        vars.put(name, val);
    }
    public Object getVariableValue(String name) {
        return vars.get(name);
    }
}