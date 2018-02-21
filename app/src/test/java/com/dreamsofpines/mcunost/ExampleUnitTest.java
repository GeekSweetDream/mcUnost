package com.dreamsofpines.mcunost;

import android.util.Base64;

import org.junit.Test;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.util.List;

import static com.yandex.metrica.impl.q.a.i;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.util.Base64Utils.encode;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        String str = "manager:ut8afs";
        assertThat(Base64.encode(str.getBytes(),Base64.NO_WRAP).toString(),is("bWFuYWdlcjp1dDhhZnM="));
    }
}