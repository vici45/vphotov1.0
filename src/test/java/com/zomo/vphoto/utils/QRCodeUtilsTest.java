package com.zomo.vphoto.utils;

import com.zomo.vphoto.ApplicationTests;
import org.junit.Test;

import static org.junit.Assert.*;

public class QRCodeUtilsTest extends ApplicationTests {
    @Test
    public void test1() throws Exception {
        QRCodeUtils.encode("http://www.baidu.com","logo.png","qr",true);
    }

}