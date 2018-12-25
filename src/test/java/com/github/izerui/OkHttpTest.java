package com.github.izerui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.izerui.support.JMap;
import okhttp3.*;
import org.junit.Test;

import java.io.IOException;

public class OkHttpTest {

    String cookie = "session-id=137-1201648-7750258; session-id-time=2082787201l; ubid-main=133-6900056-9905745; session-token=\"9IQgZBrOqLvBP8EYYfhja2YJFpLSJbAEW3Sygki7TU/wZ7/xICvFPKHnoR05fC6C3AOFqVyXDtWWTsDnW0ZMwmDVm0fdbcuAWA4eb2WxbTgLjldF6oyHCnpzOi47756XqSJf6PyVe3CAipU3wVdfym9RQidg1ZDtBR1pa//lhd+efmKgI1nqcsG6gndnJnI5qOwofwkGsMPa21DlKYrWR9FcUieJYt6yLI4wkGgxffo=\"; x-main=8qYhJ56mupcls8iQn96RCxyC4bLSS6kfdKG2qJ5tPdLP5xHsveGyfx9HdRaOSYZF; at-main=Atza|IwEBILBwNzM-J5PZC0XUXund8sIFAITKpP5sZGAG5HxX3db1Nq5Wt83X1APi-fROCuB3LsciTo1qYIpHUGwegOyITQ1BiOD_V5FGJM0E0_1N2B-NANkYysZEQgBuFQhLhSnYTd1EYOr3bb7RCtuHZx2NxgOhGcwRN4qHuyI-Y5MO4fObqrZrsGIg0uselOAjR1_hQr0BNZisHQpSkuTFUW4IxGJ8Dh_da4BnFtqWxtfeFUAymsZ-wqUsVOBbOy0uNLQ86-zC1_Xuxukxe5tu7_BEwhnatLCwlXP0Yzm99yA1TtEYqLMYaDrUICsrd7_bpyZruAh-9tNwI7oWs9dVf96ivn8E6igD2rejx3CPxhATpoERjEM58x-OymIMeaqYjUejfcPLoDg9mGGubCjBONvFjkSi; sess-at-main=\"Kmfxua90fquBGJEnBO0xsbqip03i23QrMXMlfmF/DzA=\"; sst-main=Sst1|PQGauLNicl6nVnYVNHGuFsjpC6fw7bIk9MBHLBG_Mt4v5AkRQTn6Aa94DXGVN6ERCBxIFUMkoy0O-zV5cIb5RKauId1TecKxHhKyUjI0rXZMpvPF69NDYJ7O5jTUikd0aFKf19KS900zqJeZ3FTMeRJCjg_pNUF2PX_RIVH7CW2l1cAQk66NsqOMMoF79Ryi6OQWFM3VttYziZ49TItDgpNpQe9c3dLjIpZ-9558JNPxc6ISVeTxdkNpE0CZ1vQ8x201vaToTb-22ZTAmbhEEen3cQ1geycF4_QA3uV8wa3UL9-F0-8DfCt2fuiYPWECXx71ouvWJ9VGLMZdOeoL08AOvg; csm-hit=tb:TQ2X84PQR03CE9V4EF41+s-VK4BM5CXWHJS34AJ2R7H|1545635278200&t:1545634161125&adb:adblk_no";

    MediaType JSON_TYPE = MediaType.parse("application/json;charset=utf-8");

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void teset() throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://www.amazon.com/authenticity/scanrun/v2?")
                .header("cookie", cookie)
                .get().build();
        Response response = okHttpClient.newCall(request).execute();

        System.out.println(response.body().string());

    }
}
