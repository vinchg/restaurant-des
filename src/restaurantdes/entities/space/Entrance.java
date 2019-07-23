package restaurantdes.entities.space;

import restaurantdes.entities.actors.Host;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vince on 12/10/2017.
 */
public class Entrance {
    final int MAX_CAPACITY;
    List<Host> host_list = new ArrayList<>();

    public Entrance(int capacity) {
        MAX_CAPACITY = capacity;
    }

    public boolean addHost(Host input_host) {
        return host_list.add(input_host);
    }

    public boolean removeHost(Host input_host) {
        return host_list.remove(input_host);
    }

    public Host getHost() {
        if (host_list.isEmpty())
            return null;
        else
            return host_list.get(0);
    }

    public Host getAndRemoveHost() {
        Host h = getHost();
        removeHost(h);
        return h;
    }
}
