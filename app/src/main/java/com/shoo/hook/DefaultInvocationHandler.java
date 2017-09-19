package com.shoo.hook;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shoo on 17-9-11.
 */

public class DefaultInvocationHandler implements InvocationHandler {

    private Object mOriginObj;
    private List<InvocationHandler> mHandlers;

    public DefaultInvocationHandler(Object originObj) {
        mOriginObj = originObj;
        mHandlers = new ArrayList<>();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        for (InvocationHandler handler : mHandlers) {
            result = handler.invoke(mOriginObj, method, args);
            if (result != null) {
                return result;
            }
        }

        return method.invoke(mOriginObj, args);
    }

    public void addHandler(InvocationHandler handler) {
        if (mHandlers.contains(handler)) {
            mHandlers.remove(handler);
        }
        mHandlers.add(0, handler);
    }

    public void removeHandler(InvocationHandler handler) {
        mHandlers.remove(handler);
    }
}
