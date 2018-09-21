package department.common.observer;

import department.common.observer.event.Event;

public interface Observer {
    Object[] doEvent(Event event);
}
