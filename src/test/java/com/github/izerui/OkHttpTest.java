package com.github.izerui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.izerui.support.JMap;
import okhttp3.*;
import org.junit.Test;

import java.io.IOException;

public class OkHttpTest {

    String cookie = "aws-priv=eyJ2IjoxLCJldSI6MCwic3QiOjB9; aws-target-static-id=1544087064628-250653; aws-target-data=%7B%22support%22%3A%221%22%7D; aws-target-visitor-id=1544087064632-645744.22_6; aws-ubid-main=357-4337217-2402437; aws-session-id=144-5359137-4713315; aws-session-id-time=2174807369l; sess-aws-at-main=\"uR34rJuuTDdHe5S1oSmCy02F4k1AL4v3LzY4WDhC/yY=\"; regStatus=registering; aws-business-metrics-last-visit=1544087418397; session-id=144-9379928-7954021; ubid-main=132-2084586-1122908; session-id-time=2082787201l; skin=noskin; x-wl-uid=1o79qffiMJHEGFjhMZpyQ8GoYm0zGkriqrzqr05GX2/AhEI/oMTgmpsWNc5uB3YZyt6nPHux7SyNhL9uip4KnSCQw4ggYilW4mYnZ0Mn99TQNeul9QVsPmXEyhr1x/GPSNt8oa8FWzMY=; s_sq=%5B%5BB%5D%5D; s_pers=%20s_fid%3D4F0FA1A3ED7DB871-0C11CC04E0499B3F%7C1703059462457%3B%20s_dl%3D1%7C1545294862460%3B%20gpv_page%3DTransparency%7C1545294862466%3B%20s_ev15%3D%255B%255B%2527Google%2527%252C%25271545293062477%2527%255D%255D%7C1703059462477%3B; s_fid=0ECD8E0D2E9ADBCD-096C0EDFF7B22CCF; c_m=undefinedwww.google.comSearch%20Engine; s_sess=%20c_m%3Dwww.google.comNatural%2520Search%3B%20s_ppvl%3DTransparency%252C28%252C28%252C730%252C1440%252C730%252C1440%252C900%252C2%252CL%3B%20s_ppv%3DTransparency%252C28%252C28%252C730%252C1440%252C730%252C1440%252C900%252C2%252CL%3B%20s_cc%3Dtrue%3B%20s_sq%3D%3B%20ev1%3Dn/a%3B; s_cc=true; s_vnum=1977292094333%26vn%3D2; s_ppv=44; JSESSIONID=AA27320F219AA561C2403DF638D38735; s_vn=1575623065092%26vn%3D2; aws-userInfo=%7B%22arn%22%3A%22arn%3Aaws%3Aiam%3A%3A029982410244%3Aroot%22%2C%22alias%22%3A%22%22%2C%22username%22%3A%22%22%2C%22keybase%22%3A%22%22%2C%22issuer%22%3A%22https%3A%2F%2Fwww.amazon.com%2Fap%2Fsignin%22%7D; __utma=194891197.1499154040.1544087371.1544087371.1545364589.2; __utmc=194891197; __utmz=194891197.1545364589.2.2.utmccn=(referral)|utmcsr=console.aws.amazon.com|utmcct=/console/home|utmcmd=referral; aws-session-token=\"oFQKhnNRyLdTW1IMyLj9ZWV8v1l3YPi/pvljE+dus9PvwwH+S6LuRhLbC7HKBMBxe7jV0cxZD3RbxaGu/gYKo+Dz0vEIRBSZnGiF+wcrgYKcu/H0UatSybZprdTzKZi6AP9VRT2ZxJeq3dH0GKMYRpt7zF6QKAxJlLBCxEMONg4Mu974axkPaA4usc5xU4HILSTBqK9isZlM7LmOcrZetRlH5JniSNgTnth2mseJu70=\"; s_dslv=1545364618974; s_nr=1545364618978-Repeat; lc-main=en_US; session-token=\"+no+Wz/8jcaWiT7P0YfEYaXWvOiAwoFmuZJmMpFyYx6dtXc0SIyPhgnnVkyKftj3Ufn1sImPSXgCGwMOOYjowCnJyxelX83qnP3OlLk2Ey+2kmfbZvNJdYXcU4nSTdZlzhzamIwENgxNIZtFVUrpIc50HL1V3e/lCir59xWErVt9LnkPQ2OFz8+uhVlmWdFo/4USWa7PfWeZ495urkU14by97nFHBVu0Cvht+XpexUY=\"; x-main=\"HIMXJ?Ri6y7YWn4rwaszFNNqLEJXd3bQgF@?f@EZhF2@od1b2GGYaJAonFjo@rp?\"; at-main=Atza|IwEBIMdVQkq6My1aIdZdEmdVnujQRFK6Yl7pbjEDuH4fnpq9mjbTRsCmAUBTC7mPdK6QoifTmIYpsuQIDCSJwxCBdW50fm0PWJdBqijRQ6m3ddjrIKLh2CIfO8eX_0jFpSZcMNyITSQOu-Nbsp6zG5rKrJJEIJadZrCawEGc4TDFDSbBxeLhnCwkZbP5zYdNKMsvSngMxKCDM9Wg0wxL_e8uGNcVWpDzXLph3u22hqYEhNZ3tCB7E5QECvdSXLsM763xYUpSjZ4al5pAaQeyolN-3MoCBPNK_hwxuZ3CueyyAc07H07g2X8XzjZ5CNcdnd7yl0OLj5eSmSTtIUWwjKEJRHRMgnd5VX1nEAjWLCAD-LnX-QzbYRJlFPsRs0iqbGfD3aeNM5N7YxnL677DFNQS3VBP; sess-at-main=\"Ae18cNqhTZtX0gmKdoX2kdW66713WX7efczhgbdE+o8=\"; sst-main=Sst1|PQFTtZ6iPyJcDexHG8zK02TjC-EQTp8CIh4HXGCYGFtX-mvbq90zN1yQy2XRQSsu9SIPL-ix096VWGSxnpsNOVW2CmtZboUaEfexqkmRTCroN8e2pR2wmYJ6ecbQ0Freh4CnNj0huP-Avt-bH-EzVN4meayCxFnTd0ylgPEN3_ODaEqWvU61YIY6FOg6e2SCgkZTxkNwVPiwAG9UUg8vtGpg26zbfFOeJjA_kJx6F_oIhb8BPAl1mMt8TOxOKMwqc5ZizT9q8cAwwaXr0yT8ukuFxENYBvo0plwmA4YslhKFa7WQahl2MT4InKIqaOyOJTzLhNtEZGLs6u_X8HBlQnShXw; csm-hit=tb:BW6PFGKDRNRXRQY64V22+s-0PJ58VPD58M5QW20KZXP|1545382973470&t:1545381881522&adb:adblk_no";

    MediaType JSON_TYPE = MediaType.parse("application/json;charset=utf-8");

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void teset() throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://www.amazon.com/authenticity/getproductlist")
                .header("cookie", cookie)
                .put(
                        RequestBody.create(JSON_TYPE, JMap.create("gtin", "20848467081651").writeToBytes(objectMapper))
                ).build();
        Response response = okHttpClient.newCall(request).execute();

        System.out.println(response.body().string());

    }
}
