package com.ly.atest;

import com.ly.enums.CommonCode;
import com.ly.enums.InstrumentStateEnum;
import com.ly.service.InstrumentService;
import com.ly.vo.form.CommonVo;
import com.ly.vo.query.InstrumentQueryVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author lw
 * @version 1.0
 * @description com.ly.atest
 * @date 2018/8/1 14:27
 */
public class InstrumentServiceImplTest extends MyTest {
    @Autowired
    InstrumentService instrumentService;
    @Test
    public void testListPage() {
        InstrumentQueryVo queryVo = new InstrumentQueryVo();
        queryVo.setNumber( 0 );
        queryVo.setSize( 999 );
        /*queryVo.setCategroyId( 8L );*/
        /*queryVo.setCode( "00" );
        queryVo.setState(  "空闲");*/
        queryVo.setName( "仪器" );
        System.out.println( instrumentService.listPage( queryVo ) );
    }
    @Test
    public void updateInstrumentState() {
        instrumentService.updateInstrumentState( 3L, InstrumentStateEnum.USED.name() );
    }

    @Test
    public void findInstrument() {
        System.out.println( instrumentService.findInstrument( 3L ) );
    }

    @Test
    public void saveInstrument() {
    }

    @Test
    public void updateInstrument() {
    }

    @Test
    public void del() {
    }
}