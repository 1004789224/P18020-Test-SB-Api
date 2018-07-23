package com.ly.atest;

import com.ly.WebapiApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zhongwei on 28/03/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebapiApplication.class)
public class MyTest {
    @Before
    void init() {
        System.out.println( "=====测试开始======" );
    }

    @After
    void end() {
        System.out.println( "======测试结束======" );
    }
}
