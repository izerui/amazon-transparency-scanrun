package vo {
[RemoteClass(alias="com.github.izerui.entity.ScanBatch")]
public class ScanBatch {

    public var id:String;
    public var productTitle:String;//商品
    public var asin:String;//ASIN
    public var manufacturerLot:String;//批次编号
    public var parentUpc:String; //包装箱 UPC/GTIN
    public var manufacturerReference:String; //备注
    public var batchId:String;
    public var runId:String;
    public var unitsPerCase:Number; //一个包装内单位数量
    public var expectedCaseCount:Number;//预期包装数量
    public var expectedCount:Number;//预期单位数量
    public var unitLabelRegExPattern:String; // 单位正则
    public var caseLabelRegExPattern:String; // 包装正则
    public var tempCaseToken:String; //
    public var vendorCode:String;

    public var beginTime:Date;
    public var endTime:Date;

    public var caseCount:Number;
    public var unitCount:Number;

    public var upc:String;

    public var unitStringId:String;

    public var caseStringId:String;

    public var internalId:String;

    public var gtin:String;

    public var userCode:String;

}
}
