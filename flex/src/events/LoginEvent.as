package events {
import flash.events.Event;

import vo.ScanBatch;

public class LoginEvent extends Event {

    public var scanBath:ScanBatch;

    public function LoginEvent(scanBath:ScanBatch) {
        super("login", bubbles, cancelable);
        this.scanBath = scanBath
    }


}
}
