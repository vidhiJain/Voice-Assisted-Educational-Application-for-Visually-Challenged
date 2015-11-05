package org.arise.enums;

/**
 * Created by Arpit Phanda on 3/11/2015.
 */
public enum CourseRequestType {
    TYPE("type"),
    ALL("all"),
    CURRENT("current"),
    COMPLETED("completed");

    private String keyValue;

    CourseRequestType(String keyValue){
        this.keyValue = keyValue;
    }

    @Override
    public String toString() {
        return this.keyValue;
    }
}
