package vo {
import mx.collections.ArrayCollection;

[RemoteClass(alias="com.github.izerui.pojo.Product")]
public class Product {
    public var upc:String;
    public var asin:String;
    public var title:String;
    public var revision:Number;
    public var productContainerConfigs:ArrayCollection;
    public var attributes:Attributes;
    public var weblabEnabled:Boolean;
}
}
