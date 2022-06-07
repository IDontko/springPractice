package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gaoyang
 * create on 2022/5/25
 */

public class TestLog {
    private static final Logger log = LoggerFactory.getLogger(TestLog.class);
    public static void main(String[] args) {
        log.debug("{}","test");
    }
}
