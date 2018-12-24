package com.github.izerui;

import com.github.izerui.pojo.Product;
import com.github.izerui.pojo.ScanItem;
import com.github.izerui.pojo.ScanResult;
import com.github.izerui.service.AmazonService;
import com.google.common.collect.Lists;
import flex.messaging.io.amf.ASObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ScanRunTests {


    String cookie = "_forum_session=U0pZN2k5K3dHaTRRdEhrVGZYY2Z6OUdIMlRJakl5MFg2ZnNtNEoyU2hRdDV2eWtKbGkzVytpelNxM2hTakJpSytIaitQV0thRWdjZVVqRnBpVCtLTTlZRjFkKzEzUmxoa1Bid29iS01WeWhLT1lPUW9Lei9GejhxaTh1NElSR2k4bDJGVXpXRERFcDg2SEI0aGFmRGpRMGMzUU9ZMUJIN2JBdmI0V3FJV3VpNWZXYjR1MXpFS3VpSXJIdUV6THIrMTU0NzRsTmhuVSsyNnlRME4yL0lHc2RWM1VRVng3TnpnM1hXSFNZeXpOS3hhMlJqQUhkbEgrNWhvTzlHa2Q3YUdhckl4dG82OXZDVEc2SUdZUi9mc0ZmbjJnSjlrZjBMUlBWTGRuVWhJa2NuUHc0QzZ2VGhrUmpRdGphOHhIYmctLVZNVCtSMzU3WkhrYnlER3pIRjdJTEE9PQ%3D%3D--6767b24512640bd942710b2d6e94c910d678e52e; aws-priv=eyJ2IjoxLCJldSI6MCwic3QiOjB9; aws-target-static-id=1544087064628-250653; aws-target-data=%7B%22support%22%3A%221%22%7D; aws-target-visitor-id=1544087064632-645744.22_6; aws-ubid-main=357-4337217-2402437; aws-session-id=144-5359137-4713315; aws-session-id-time=2174807369l; sess-aws-at-main=\"uR34rJuuTDdHe5S1oSmCy02F4k1AL4v3LzY4WDhC/yY=\"; regStatus=registering; aws-business-metrics-last-visit=1544087418397; session-id=144-9379928-7954021; ubid-main=132-2084586-1122908; session-id-time=2082787201l; skin=noskin; x-wl-uid=1o79qffiMJHEGFjhMZpyQ8GoYm0zGkriqrzqr05GX2/AhEI/oMTgmpsWNc5uB3YZyt6nPHux7SyNhL9uip4KnSCQw4ggYilW4mYnZ0Mn99TQNeul9QVsPmXEyhr1x/GPSNt8oa8FWzMY=; s_sq=%5B%5BB%5D%5D; ld=NSGoogle; s_pers=%20s_fid%3D4F0FA1A3ED7DB871-0C11CC04E0499B3F%7C1703059462457%3B%20s_dl%3D1%7C1545294862460%3B%20gpv_page%3DTransparency%7C1545294862466%3B%20s_ev15%3D%255B%255B%2527Google%2527%252C%25271545293062477%2527%255D%255D%7C1703059462477%3B; s_fid=0ECD8E0D2E9ADBCD-096C0EDFF7B22CCF; c_m=undefinedwww.google.comSearch%20Engine; s_sess=%20c_m%3Dwww.google.comNatural%2520Search%3B%20s_ppvl%3DTransparency%252C28%252C28%252C730%252C1440%252C730%252C1440%252C900%252C2%252CL%3B%20s_ppv%3DTransparency%252C28%252C28%252C730%252C1440%252C730%252C1440%252C900%252C2%252CL%3B%20s_cc%3Dtrue%3B%20s_sq%3D%3B%20ev1%3Dn/a%3B; s_cc=true; s_vnum=1977292094333%26vn%3D2; s_ppv=44; JSESSIONID=AA27320F219AA561C2403DF638D38735; aws-userInfo=%7B%22arn%22%3A%22arn%3Aaws%3Aiam%3A%3A029982410244%3Aroot%22%2C%22alias%22%3A%22%22%2C%22username%22%3A%22%22%2C%22keybase%22%3A%22%22%2C%22issuer%22%3A%22https%3A%2F%2Fwww.amazon.com%2Fap%2Fsignin%22%7D; __utma=194891197.1499154040.1544087371.1544087371.1545364589.2; __utmc=194891197; __utmz=194891197.1545364589.2.2.utmccn=(referral)|utmcsr=console.aws.amazon.com|utmcct=/console/home|utmcmd=referral; aws-session-token=\"oFQKhnNRyLdTW1IMyLj9ZWV8v1l3YPi/pvljE+dus9PvwwH+S6LuRhLbC7HKBMBxe7jV0cxZD3RbxaGu/gYKo+Dz0vEIRBSZnGiF+wcrgYKcu/H0UatSybZprdTzKZi6AP9VRT2ZxJeq3dH0GKMYRpt7zF6QKAxJlLBCxEMONg4Mu974axkPaA4usc5xU4HILSTBqK9isZlM7LmOcrZetRlH5JniSNgTnth2mseJu70=\"; lc-main=en_US; csm-hit=tb:2NQPNVX7BMZFY24Y2792+s-2NQPNVX7BMZFY24Y2792|1545378609078&t:1545378609078&adb:adblk_no; s_dslv=1545385229337; s_vn=1575623065092%26vn%3D3; s_nr=1545385229342-Repeat; session-token=\"GwPQCZUn1ZMhdefJOirXkZ+u62DG+P7tWDgZC925UoCq6WXaQUwaUbDiIrJ0OusDn/MpIIpeZjxIcVw6f4kccWDyKWtoMx44kvfLlSG7potE1FwDXpaWYVsf7ORuAyBILzJ6qAgFQVzt48SVL2rZvQJf8y9+EjNcw7HMi8hmxR2I3v1m8VlVFG75F8shlgFfgZl0458EHKY4XI78ubUduEgCFgc5WeoJ51qkhAlnofo=\"; x-main=\"3JPsT@kkA4shRSc3etU3zzHJN88ApyZb7TpnIF3J4Eyr49uJx4rrtMcvDkChmcKB\"; at-main=Atza|IwEBIF9oMjPejWl3WHl2smtVG5J6OLYCjFOTfXcUv-BAxuUu0lFsOVl9A37WkVKAevkUPBs7ICCGO0Y3supP8hBtNXpN2QTu16JlH2kWd3vDPkdUbTqeOtSYSGkfjVdldmdBAVkXVGOC5gNkDfdMCzj9mW3Jb0R-LO5WkwfAfR9TFoVZDMhmjjvglWz9FesvRouu89xlzIM8ij1n3xUsfmqcGWVT9ObV0PpB6h5fkD9bDlTTJ0lAPQedKUFsquESEmZ9t6GAjz15rzsOMWkqgCT4kcrcCqNfZq6UIUkXpRGdzjUPAo-u_HHnv-yjHwgNYSQXq4cHmXFRV6Uxrh5SVUUd-kBTJTov42z2KJjPwnCka4rAsh1Swd2MHbV0Fg6AwvXE4z4DFZjcKoYsg-gDALlvriqw; sess-at-main=\"yWzd9rtZXZ1S91cb75Hvf+R97j5f2W30ZEXRVpVBJDU=\"; sst-main=Sst1|PQFXdHzO0MJcQO5G6SPiJB6LCxkFudstWzOkNtcch7n55s7wVx4sBw4uJ3jGZ6ATfokfhUOQKibGIV03HbQUHLm5NBG5fXzOw6o8_Fzg1MJbfDIpa7sHZNv-jpc-AHpjbV_SLriMQfm3AfrEg58shCFNbtlIBfFJz6MnwkVX8-EMphtnay3fkT0xw5bZDakIJ-57R-gk8olGE1lfVXonJg08YJrZG1YKn0i9GoDCNgDMSqU9i1-CJpfr1M4hag3-QUjOcBlByx8rgDr7BSgoWuNeuzaromsvEb9s8Y38lTYhYGdRE4WcotMtGQjhUsxUMJwKCSIm_xDZSwKrmvCGO-__1A; csm-hit=tb:SE8EKQSXEHKJ491DSB6T+s-VAR6EYZY1WNPYV4APN4N|1545540352656&t:1545538629893&adb:adblk_no";

    @Autowired
    AmazonService amazonService;

    @Test
    public void testProductionList() throws IOException {
        Product product = amazonService.getProductlist(cookie, "20848467081651");
        System.out.println(product.getTitle());
    }

    @Test
    public void testSelectItems() throws IOException {

        ScanItem item = new ScanItem();
        item.setItemId("AZ:XDSAKHQXJ5AOBIBPTMKBMS9ZGE");
        item.setCaseItemId("ZA:GES5FR6OANGPBM38F");
        item.setParent(false);
        item.setRunId("SndbmDShSkStCTeIPE1NWA");
        item.setActive(false);
        item.setTempCaseToken("empty");

        ScanResult scanResult = amazonService.scanItems(cookie, false, Lists.newArrayList(item));
        System.out.println(scanResult);
    }

    @Test
    public void cancelRun(){

    }
}
