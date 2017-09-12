package com.shoo.hook.pms;

import android.content.Context;
import android.util.Log;

/**
 * Created by Shoo on 17-9-11.
 */

public class TPmsHooker {

    private static final String TAG = "THookPackageInfo";

    public static void test(Context context) {
        /*
        THookPackageInfo: before hook: signature = 308201dd30820146020101300d06092a864886f70d010105050030373116301406035504030c0d416e64726f69642044656275673110300e060355040a0c07416e64726f6964310b3009060355040613025553301e170d3137303930353131333033335a170d3437303832393131333033335a30373116301406035504030c0d416e64726f69642044656275673110300e060355040a0c07416e64726f6964310b300906035504061302555330819f300d06092a864886f70d010101050003818d0030818902818100ce1407932ae9fcf5c66582a79e9d725310c38b70362882b35d048fdae59463171757714877554f6d42e961ac3733e4464ca6e675f4d8481d02a19ef1b8b4b829e9e5e05ceee18af09a3c4e0e551c2f035faed9974fa8faad45c5cf1687f6d2fa485418d5d79f431a0723fb6d085347dc40aacf58698d011b89fe4f8493af08290203010001300d06092a864886f70d010105050003818100574e85ff14e7289a0bf8e0bd5ccbc600aafec6a14588eaea06343334df68e0bafe4a30f17dca3562f96870f39fc60c587455ecb81de82df23307152bdd3d670712fd4ec11adf0d3df2ba62a05f915559eeff0244c305c3558fbe0d72fbe59abdab4e1f568f4c648407c364cbadde41f95d2611546031baab7d417e7e439c8e4c
        THookPackageInfo: after hook: signature = abcd
         */

        String signature = PackageUtils.getSignature(context);
        Log.d(TAG, "before hook: signature = " + signature);

        PmsHooker.hookPackageInfo("abcd", 12345);

        signature = PackageUtils.getSignature(context);
        Log.d(TAG, "after hook: signature = " + signature);
    }

}
