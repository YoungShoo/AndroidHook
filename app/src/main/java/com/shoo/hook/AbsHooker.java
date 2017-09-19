package com.shoo.hook;

import java.lang.reflect.InvocationHandler;

/**
 * Created by Shoo on 17-9-18.
 */

public abstract class AbsHooker {

    protected static DefaultInvocationHandler sInvocationHandler;

    public static void addHandler(InvocationHandler handler) {
        if (sInvocationHandler != null) {
            sInvocationHandler.addHandler(handler);
        }
    }

    public static void removeHandler(InvocationHandler handler) {
        if (sInvocationHandler != null) {
            sInvocationHandler.removeHandler(handler);
        }
    }
}
