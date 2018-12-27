package utils {
import flash.display.Sprite;

import mx.controls.Alert;
import mx.events.CloseEvent;

public class AlertShow {
    public static function operateConfirm(msg:String, msgTitle:String, dispObj:Object, callFunctionForOK:Function, callFunctionForCancel:Function = null):void {

        Alert.okLabel = "确定";
        Alert.cancelLabel = "取消";
        Alert.show(msg, msgTitle, Alert.OK | Alert.CANCEL, dispObj as Sprite, myClick, null, Alert.CANCEL);

        function myClick(evt:CloseEvent):void {
            if (evt.detail == Alert.OK) {   //点了确认按钮
                if (callFunctionForOK) {
                    callFunctionForOK.call();
                }
            } else {
                if (callFunctionForCancel) {
                    callFunctionForCancel.call();
                }
            }
        }
    }

}
}