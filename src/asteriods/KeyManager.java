package asteriods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Represents a set of keys and whether they are pressed down or not
 */
public class KeyManager {
    
    HashMap<String, Boolean> map;
    
    public KeyManager(String[] keys) {
        this.map = new HashMap<>();
        
        for (String key : keys) {
            this.map.put(key, false);
        }
    }
    
    public boolean getKeyPressed(String key) {
        return this.map.get(key);
    }
    
    public String[] getKeysPressed() {
        ArrayList<String> keysPressed = new ArrayList<>();
        Iterator it = this.map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if ((Boolean) pair.getValue()) {
                keysPressed.add("" + pair.getKey());
            }
        }
        return keysPressed.toArray(new String[keysPressed.size()]);
    }
    
    public void setKey(String key, boolean value) {
        this.map.put(key, value);
    }
    
    @Override
    public String toString() {
        String s = "";
        Iterator it = this.map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            s += pair.getKey() + ": " + pair.getValue() + "\n";
            //it.remove(); // avoids a ConcurrentModificationException
        }
        return s;
    }
}
