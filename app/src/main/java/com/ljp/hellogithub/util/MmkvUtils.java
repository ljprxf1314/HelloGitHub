package com.ljp.hellogithub.util;

import com.tencent.mmkv.MMKV;

/**
 * Created by lijipei on 2019/8/8.
 * 腾讯MMKV存储框架
 */

public class MmkvUtils {

    private static final String LOCATION = "location";

    private static MMKV kv = MMKV.defaultMMKV();

    public static MmkvUtils getInstance(){
        return Holder.mMmkvUtils;
    }

    private static class Holder{
        public static MmkvUtils mMmkvUtils = new MmkvUtils();
    }

    public static void saveLocation(int location){
        kv.encode(LOCATION,location);
    }

    public static int readLocation(){
        return kv.decodeInt(LOCATION);
    }


}
