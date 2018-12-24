# SourceMark v2 / 亚马逊透明计划Transparency服务代理
---- 

## put getproductlist: https://www.amazon.com/authenticity/getproductlist
---
### request:
```json
{
  "gtin": "20848467081651"
}
```
### response:
```json
{"data":{"upc":"848467081657","asin":"B07HJGNVSH","title":"InvisibleShield-Glass Curve Elite-Apple-Watch (40mm)-Series 4-FG","revision":1,"productContainerConfigs":[{"internalId":"ZAGA91","gtin":"848467081657","unitsPerCase":5,"unitLabelRegExPattern":"^AZ:[A-KM-UW-Z13-9]{26}$","caseLabelRegExPattern":"^ZA:[A-Z0-9]{17}$","parentGtin":"10848467081654","childScanFirst":false,"unitStringId":"Gift box","caseStringId":"Inner","trackingCases":true},{"internalId":"ZAGA92","gtin":"10848467081654","unitsPerCase":10,"unitLabelRegExPattern":"^ZA:[A-Z0-9]{17}$","caseLabelRegExPattern":"^ZA:[A-Z0-9]{17}$","parentGtin":"20848467081651","childScanFirst":false,"unitStringId":"Inner","caseStringId":"Master","trackingCases":true}],"attributes":{"locations":[{"city":null,"state":null,"country":"CHINA"}],"manufacturerName":null,"bestByDateRequired":false},"weblabEnabled":false},"requestId":"WN1G1FK26HTPZYW0C4G6","errorCode":null,"successful":true}
```

## post scanitems: https://www.amazon.com/authenticity/scanitems
---
### request:
```json
{
  "scanItemRequestList": [
    {
      "itemId": "ZA:TBFUBRUOQFGETJ4N9", // 条码内容
      "caseItemId": "", // 包装箱的id
      "parent": true, //true：包装箱  false：单元
      "runId": "39A98xfKT8u7BQEvmi-R8A",
      "active": true, // true：同步  false：伪同步
      "tempCaseToken": "empty" //默认值，具体还不清楚
    },
    {
      "itemId": "AZ:FCU4D7ACYJEI7BHRKOD3MK8NNY",
      "caseItemId": "ZA:TBFUBRUOQFGETJ4N9", // 包装箱的id
      "parent": false,
      "runId": "39A98xfKT8u7BQEvmi-R8A",
      "active": true,
      "tempCaseToken": "empty"
    },
    {
      "itemId": "AZ:8E6R6QJUU5DGXIN6EHNGEMBITU",
      "caseItemId": "ZA:TBFUBRUOQFGETJ4N9",
      "parent": false,
      "runId": "39A98xfKT8u7BQEvmi-R8A",
      "active": true,
      "tempCaseToken": "empty"
    },
    {
      "itemId": "AZ:SW5ZJ7I5NRE68JN61G1ZQXP4N4",
      "caseItemId": "ZA:TBFUBRUOQFGETJ4N9",
      "parent": false,
      "runId": "39A98xfKT8u7BQEvmi-R8A",
      "active": true,
      "tempCaseToken": "empty"
    },
    {
      "itemId": "AZ:4N83UQSJPZG4NIFGPB3O8F7GA4",
      "caseItemId": "ZA:TBFUBRUOQFGETJ4N9",
      "parent": false,
      "runId": "39A98xfKT8u7BQEvmi-R8A",
      "active": true,
      "tempCaseToken": "empty"
    },
    {
      "itemId": "AZ:8QG1DORQQRFNNJ1A7MBA6ZA38E",
      "caseItemId": "ZA:TBFUBRUOQFGETJ4N9",
      "parent": false,
      "runId": "39A98xfKT8u7BQEvmi-R8A",
      "active": true,
      "tempCaseToken": "empty"
    }
  ]
}
```

## response:

```
{
  "data": {
    "errorCount": 0,
    "scanItemResultList": [
      {
        "requestStatus": "SUCCEEDED",
        "scanItemId": "ZA:TBFUBRUOQFGETJ4N9",
        "failureReason": null
      },
      {
        "requestStatus": "SUCCEEDED",
        "scanItemId": "AZ:FCU4D7ACYJEI7BHRKOD3MK8NNY",
        "failureReason": null
      },
      {
        "requestStatus": "SUCCEEDED",
        "scanItemId": "AZ:8E6R6QJUU5DGXIN6EHNGEMBITU",
        "failureReason": null
      },
      {
        "requestStatus": "SUCCEEDED",
        "scanItemId": "AZ:SW5ZJ7I5NRE68JN61G1ZQXP4N4",
        "failureReason": null
      },
      {
        "requestStatus": "SUCCEEDED",
        "scanItemId": "AZ:4N83UQSJPZG4NIFGPB3O8F7GA4",
        "failureReason": null
      },
      {
        "requestStatus": "SUCCEEDED",
        "scanItemId": "AZ:8QG1DORQQRFNNJ1A7MBA6ZA38E",
        "failureReason": null
      }
    ]
  },
  "requestId": "RY9BY39RCXPRFFGNDFEG",
  "errorCode": null,
  "successful": true
}
```

## put completescanrun: https://www.amazon.com/authenticity/completescanrun
---
### request:
```json
{
  "runId":"39A98xfKT8u7BQEvmi-R8A"
}
```
### response:
```json
{
  "data": null,
  "requestId": "383F5RJZ39MDK3A8MXJJ",
  "errorCode": null,
  "successful": true
}
```
> 批次 W811KC 运行中的数据已提交到 Transparency 系统