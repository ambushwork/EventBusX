package com.netatmo.ylu.eventbus_annotation;

public interface SubscriberInfo {

    Class<?> getSubscriberClass();

    SubscriberMethod[] getSubscriberMethods();
}
