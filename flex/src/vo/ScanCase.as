package vo {
[RemoteClass(alias="com.github.izerui.pojo.ScanCase")]
public class ScanCase {

    public var id:Number;
    public var batchId:String; //扫描批次id
    public var itemId:String;//条码内容
    public var scanTime:Number;//扫描时间
    public var status:String;//同步状态 unDone Done
    public var count:Number;//包装下的数量
}
}
