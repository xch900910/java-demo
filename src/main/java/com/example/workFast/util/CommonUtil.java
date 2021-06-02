package com.example.workFast.util;

import com.google.common.base.Joiner;

import java.util.Map;
import java.util.TreeMap;

import static com.example.workFast.Constants.*;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-05-19 15:46
 **/
public class CommonUtil {

    /**
     * 字典排序算法
     *
     * @param strArr 待排序数组
     */
    public static void sort(String[] strArr) {
        for (int i = 0; i < strArr.length - 1; i++) {
            for (int j = i + 1; j < strArr.length; j++) {
                if (strArr[j].compareTo(strArr[i]) < 0) {
                    String temp = strArr[i];
                    strArr[i] = strArr[j];
                    strArr[j] = temp;
                }
            }
        }
    }

    /**
     * 根据私钥加签
     *
     * @param appKey
     * @param privateKey
     * @return
     */
    public static Map<String, Object> geneApiSign(String appKey, String privateKey) {
        Map<String, Object> singMap = new TreeMap<>();
        singMap.put(APP_KEY_NAME, appKey);
        singMap.put(TIME_STAMP, System.currentTimeMillis());
        singMap.put(SIGN_TYPE, SIGN_TYPE_VALUE);
        singMap.put(CHAR_SET, CHAR_SET_VALUE);
        String join = Joiner.on("&").withKeyValueSeparator("=").join(singMap);
        String sign = null;
        try {
            byte[] bytes = SignUtil.sign256(join, SignUtil.getPrivateKey(privateKey));
            sign = SignUtil.encodeBase64(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        singMap.put(SIGN, sign);
        return singMap;
    }

    public static void main(String[] args) {
        System.out.println(geneApiSign("test", "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCcEC+BjuK0/bsgSJWwDz5DLlWp1goZ0hqM2LjDtlCVYLzA4+n+dvE+fYV3BDmrvK8nSllcYbtJvQab6BZq2mwBqXV5muLpX73U+VdrhsrZ/ksAwPQbGVFW8uadDjM4NUwofjkSO9Yc7zOfobxK2Ixr5hxkadATvBi0O2YzRJMzFWwg4VBhgnV7zqfOrtSpEh4azODlsUFQbcpejnDs22pXSHUBTL7L/fSF15d/Op30wXaIGBQWxUNphlY9mxb+XrdnItl1gTkIYqBvY1ggWGgTdGmoyWRC7RUF9I7foGaR+bxt6ImFQZ7ofY0Uh7PbYg+fwNbYWCgO1BYTsso4ZbEjAgMBAAECggEANMu49pB7G5W2524Gzb/qZVnYexNA6YlNmBZ9MyCLVG2cjXjNIVvlsylNrHzOcc3sR1N8tacOcBy1jqrOYqmv3kQQzOncF+1e0BzmOScpmuPPRdsA+eXvwYV9BObX5w0Iza4d6pA5mCJICZnylDN8FciMFGBDl1mL1fU+Vtu9HIt6/SSP2NHBXVYf3tCPqFPIK4cckiEy+q9eI/QczUn+ImgPhmCORk7e278bytfXYJgd5KI42uJmQ2qSyrE5gsBcrPl2M4oYuMWXKWiIn4d4MCmmrXovO+iCC0EVhgNMTxBmHI0z/J0C7BVKzkz9PFMruiAyULNYiZuxru5KzUcHiQKBgQDoMSFBOjdwzS37sMznqzNuaA6RfxHQpGPUmrE+G3YQTYYnK3xUnY0By2kiM4zigHphZXfwuOLhZKnJvlLGAAkkOaCW0sLDafCepZpj2wME1UZxcoHTquoy0gKrkIdJD0c+8CvzZWkS6qCtGqGjwzbcYbqFbcEbwiaxx4cZbpm5hQKBgQCsELuIdTPgcqMnB/k+lNNCz2AxS8qH5MNvcIpvaPKVw59jS5mnN+hiaVn9yekUTOXjmmimqX0VLatM03rvNJrWj1d6Yfy0SK9d0JPxfMSM2X60kgrGdSqjn30SyZwYqxc1lFRrDsHFTtYzxk+O32OsjyP559/tco8f7zKWRUoshwKBgQCTvx9wCAFbL9RAwcHA8LlrWZ9bicXmQ5Sapkx+Pq8jtJDWv97/YLlnGO4vKypMvoAhFZX6gUstLQa1Lvwwrbkjad/72OTBv9myuaHCRIxpOOT4L2rZK3qXF/dHAFJ1dNla/SyaVj9EIDRZsgaE1CVGmpE7chlc9m/01AftcOk0mQKBgC01PECPIrEvDYTcyHTT32nnWIu7MRxhU6P8+rJo7YvB2SfaniVcQBdTPMy8dxvZVHFniWV4lJ+y3xYRXnRo6uHCyaYjBqVLj5/jhGtnb7DeGDdTQ7d+41QYPGFqTTQCB9gQ/9kdCHZRz1/NE+wi72pQNZCchq45cjdevnjS/bkTAoGAQvQ3d4kShnuH8h3+gVElCV54Aacjkh07kpY5W7FjRyOH+SwtKi2WZ271v/W7ZXflkqjxmuQWedSq+VkXKORB0oMDIbZTR9ewUIOcDGmKOUnTp5fvsKFHsYYyXaoRqXUCVXbDQeXA9YZ5/ghVUniFTjIWMRDu17k97t+/o85OSt0="));
//        Map<String, Object> singMap = new TreeMap<>();
//        singMap.put(APP_KEY_NAME, "appKey");
//        singMap.put(TIME_STAMP, System.currentTimeMillis());
//        singMap.put("SIGN_TYPE", SIGN_TYPE_VALUE);
//        singMap.put(CHAR_SET, CHAR_SET_VALUE);
//        singMap.putIfAbsent("SIGN_TYPE", "dddd");
//        System.out.println(singMap);
//        String join = Joiner.on("&").withKeyValueSeparator("=").join(singMap);
//        System.out.println(join);
    }

}
