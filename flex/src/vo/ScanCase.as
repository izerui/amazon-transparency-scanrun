package vo {
[RemoteClass(alias="com.github.izerui.entity.ScanCase")]
public class ScanCase {

    public var id:Number;
    public var batchId:String; //扫描批次id
    public var itemId:String;//条码内容
    public var scanTime:Number;//扫描时间
    public var submited:Boolean; //提交状态
    public var requestStatus:String;//同步状态
    public var failureReason:String;//失败原因
    public var count:Number;//包装下的数量
}
}
