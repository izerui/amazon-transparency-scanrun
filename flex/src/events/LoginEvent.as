package events {
import flash.events.Event;

public class LoginEvent extends Event {

    public function LoginEvent() {
        super("login", bubbles, cancelable);
    }


}
}
