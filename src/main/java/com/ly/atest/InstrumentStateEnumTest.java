package com.ly.atest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ly.enums.InstrumentStateEnum;
import com.ly.helper.AppException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author lw
 * @version 1.0
 * @description com.ly.atest
 * @date 2018/7/31 15:02
 */
public class InstrumentStateEnumTest extends MyTest {

    @Test
    public void valueof() throws AppException {
        System.out.println( InstrumentStateEnum.valueof( 2 ) );
    }

    @Test
    public void toStringTest() throws JsonProcessingException {
        System.out.println( InstrumentStateEnum.UNUSED.toJson() );
    }
}