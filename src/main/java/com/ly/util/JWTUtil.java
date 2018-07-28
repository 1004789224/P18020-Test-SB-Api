package com.ly.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author lw
 * @version 1.0
 * @description com.ly.util
 * @date 2018/7/27 10:55
 */
@Component
public class JwtUtil {
    @Value("${jwt.expiresHour}")
    private int expiresHour;
    @Value(("${jwt.key}"))
    private String key;
    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private static Logger log = LoggerFactory.getLogger( JwtUtil.class );
    public Object getOneObjectFromJsonSrt(String jsonStr, Class valueType) throws IOException {
        log.debug( "key:"+key );
        Claims claims = Jwts.parser().setSigningKey( key ).parseClaimsJws( jsonStr ).getBody();
        Date expiresDate = claims.getExpiration();
        Date now = new Date();
        if (expiresDate.before( now )) {
            return null;
        }
        String subject = claims.getSubject();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue( subject,valueType );
    }


    public String getJsonStrFormObject( Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Calendar calendar = Calendar.getInstance();
        //设置过期时间 自己写
        calendar.add( Calendar.HOUR, expiresHour );
        Claims claims = Jwts.claims();
        claims.put( key, object );
        return Jwts.builder()
                .signWith( signatureAlgorithm, key )
                .setSubject( objectMapper.writeValueAsString( object ) )
                .setExpiration( calendar.getTime() )
                .compact();

    }
}
