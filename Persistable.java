
package CSKAirlines;

import java.io.Serializable;

public interface Persistable extends Serializable {

    public boolean isDirty(); //persistable object has been modified since last time we save to DB
    public void save(); //save object to DB
}
