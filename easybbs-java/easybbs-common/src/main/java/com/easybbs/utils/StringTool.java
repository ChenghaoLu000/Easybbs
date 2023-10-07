package com.easybbs.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class StringTool {
    public static final String getRandomNum(Integer n){
        return RandomStringUtils.random(n, false, true);
    }
    public static void main(String[] args){
        System.out.println(getRandomNum(10));
    }
}
