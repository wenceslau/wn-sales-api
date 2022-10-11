package com.wnsales.service.impl;

import java.lang.reflect.Field;
import java.util.List;

public class _DefaultService {

    protected void addPropertyNullToIgnore(Object instanceClass, List<String> ignore) {
        Class cls = instanceClass.getClass();
        Field[] fields = cls.getDeclaredFields();
        for(Field field : fields){
            Object object = null;
            try {
                String mthName = field.getName();
                mthName = mthName.substring(0,1).toUpperCase() + mthName.substring(1,mthName.length());
                object = cls.getMethod("get"+mthName).invoke(instanceClass);
            } catch (Exception e) {	}

            if (object == null){
                ignore.add(field.getName());
            }
        }
    }
}
