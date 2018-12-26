package com.github.izerui;

import com.github.izerui.dao.ScanItemDao;
import com.github.izerui.pojo.ScanItemRequest;
import com.github.izerui.pojo.ScanResult;
import com.github.izerui.service.AmazonService;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ScanRunTests {


    String cookie = "session-id=141-7671517-1693851; session-id-time=2082787201l; ubid-main=131-7502192-8224200; csm-hit=tb:KKEC9HD0YWD3DSZZTCCQ+s-KKEC9HD0YWD3DSZZTCCQ|1545636391891&t:1545636391892&adb:adblk_no; session-token=\"0+QWIl9d2eswcGKnlP7NXyeziv1g5fzvH/U+zj21963i1mgPDxN9GEa73UZqBseX7rVkbqiuX3Bf+UrDd1vEyYzC3RTPhf2FvNJxQvvLDvLUcr+5ubBPD2iJTG3chKBQ8gLdzBstYM4mb8HWuGaTf7Wl4fzbAgLq3GU76Qsp6g1Q4EGHPORljO+NUDl1T/iyNx6Kwhnx1x7A9szvvqZNvVZzNVRPVMq4pLPWmkgq15U=\"; x-main=\"m4usINpxN8JKrNJzCUueiz33cZGdW5dRkeN@bcMBrW8pqu9WpDZM7Uz4vxLUKLay\"; at-main=Atza|IwEBIKGTiI8Pzvx6k2FaQG84BaEO0DJU-q1dBOxkXYp4Gun1rw96Jn4kwySCL7-rN2rj54kkclPbHu__6uk8UtLzUaOlFCXEH7aQra0JSVilFzxUHUG2MsNKe84jRi-Ot08ccPEHuUAXZuyfqFIt4s08cKm4PQ27fXwBGeaW6ZPySZG6Jfb30L6PXANrxRBvbL09HlYbHKLRmjb641N_tWIu8PH9KvJvoFhKaKpGim3kf2U5aXrkAvSyJu_yROkAvxgA2RCn1M_qZygjdcNslpHKBx9tKJ0eegptUfpS3X7vjgxx7vfFigU5nf_tIENpX4YM3d4N6DZk9kXtIrxg1aitkD_-pDWEUXFv_04ZBQoYZ8OGXUxTXc14xg3y9Fgs0tZoS3vky3_dS0ryyLesh7GT_4IH; sess-at-main=\"tuLOeTWAEg/aVjrKSAiCzIpq/JbVp3WRYqN7cJaoKsU=\"; sst-main=Sst1|PQF0FKKtfNFeEoP-OQ-vXRf1C74TMTPOjLeF6h8sTMdVIRT3M5aI_NPA2JZPcBM9tftR0ES7BKhOCPMdB2K4m53XhWTnm_GANXpGkqWP3vJISP-h8Bs7jMLqTjKV-qg0fS4HWhNMXoPF5V1mrVzpm80kisVFyP5KJ4ruNBKax9Ol3bOFb4EuJbvrcyuggdHZ3Xfi-_8PedxepB_vu3YqkfAnsnbEl9idVh5xJeHOUjpLC6UxTSpHcQGhLrcCUAoub1kSXbnFuN0pN4Bnsa5YI9ajJwtuKg_-hBmm4dNRHuvawKaxQrOuYcWm6J-FkhQSgVeFnT3vdjiSftpXMZS8hvtF2g";

    @Autowired
    AmazonService amazonService;
    @Autowired
    ScanItemDao scanItemDao;


//    @Test
//    public void cycleData() {
//        for (int i = 0; i < 10000; i++) {
//            ScanCase scanItem = new ScanCase();
//            scanItem.setManufacturerLot("W811KC");
//            scanItem.setParentUpc("10848467081654");
//            scanItem.setProductTitle("InvisibleShield-Glass Curve Elite-Apple-Watch (40mm)-Series 4-FG");
//            scanItem.setParent(true);
//            scanItem.setItemId("itemId-" + i);
//            scanItem.setActive(false);
//            scanItem.setCaseItemId("");
//            scanItem.setAsin("B07HJGNVSH");
//            scanItem.setParent(true);
//            scanItem.setRunId("runId");
//            scanItem.setTempCaseToken("empty");
//            scanItem.setStatus("unDone");
//            scanItem.setScanTime(System.currentTimeMillis());
//            scanItemDao.save(scanItem);
//
//            for (int j = 0; j < 5; j++) {
//                ScanCase scanItemJ = new ScanCase();
//                scanItemJ.setManufacturerLot("W811KC");
//                scanItemJ.setParentUpc("10848467081654");
//                scanItemJ.setProductTitle("InvisibleShield-Glass Curve Elite-Apple-Watch (40mm)-Series 4-FG");
//                scanItemJ.setParent(false);
//                scanItemJ.setAsin("B07HJGNVSH");
//                scanItemJ.setItemId("itemId-" + i + "-" + j);
//                scanItemJ.setActive(false);
//                scanItemJ.setCaseItemId(scanItem.getItemId());
//                scanItemJ.setParent(false);
//                scanItemJ.setRunId("runId");
//                scanItemJ.setTempCaseToken("empty");
//                scanItemJ.setStatus("unDone");
//                scanItemJ.setScanTime(System.currentTimeMillis());
//                scanItemDao.save(scanItemJ);
//            }
//        }
//    }

    @Test
    public void testSelectItems() throws IOException {

        ScanItemRequest item = new ScanItemRequest();
        item.setItemId("AZ:XDSAKHQXJ5AOBIBPTMKBMS9ZGE");
        item.setCaseItemId("ZA:GES5FR6OANGPBM38F");
        item.setParent(false);
        item.setRunId("SndbmDShSkStCTeIPE1NWA");
        item.setActive(false);
        item.setTempCaseToken("empty");

        ScanResult scanResult = amazonService.scanItems(cookie, Lists.newArrayList(item));
        System.out.println(scanResult);
    }

    @Test
    public void cancelRun() throws IOException {

        amazonService.cancelScanRun(cookie, "2WB-mJnoQ6SioXdfxKMmQw");

    }

    @Test
    public void tests() throws IOException {
        Map<String, Object> map = amazonService.getRunInfo(cookie);
//        Document jsoup = Jsoup.parse(html);
//        System.out.println(jsoup);
    }
}
