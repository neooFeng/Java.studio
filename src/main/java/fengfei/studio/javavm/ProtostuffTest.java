package fengfei.studio.javavm;

import com.carrotsearch.sizeof.RamUsageEstimator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import fengfei.studio.javavm.data.BigList;
import fengfei.studio.javavm.data.BigObject;
import io.netty.util.CharsetUtil;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeEnv;
import io.protostuff.runtime.RuntimeSchema;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ProtostuffTest {
    public static void main(String[] args) {
        BigList to = new BigList(100);

        System.out.println(to.toJson());
        System.out.println(to.toJson().getBytes(CharsetUtil.UTF_8).length);

        Schema<BigList> s = RuntimeSchema.createFrom(BigList.class, RuntimeEnv.ID_STRATEGY);
        LinkedBuffer buffer = LinkedBuffer.allocate(1024 * 1024);
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ProtostuffIOUtil.writeTo(b, to, s, buffer);
            System.out.println(b.toByteArray().length);
            b.reset();

            b = new ByteArrayOutputStream();
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(b);
            gzipOutputStream.write(to.toJson().getBytes(CharsetUtil.UTF_8));
            gzipOutputStream.flush();
            gzipOutputStream.finish();
            System.out.println(b.toByteArray().length);

            GZIPInputStream inputStream = new GZIPInputStream(new ByteArrayInputStream(b.toByteArray()));

            ByteArrayOutputStream outByte = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int n;
            while ( (n = inputStream.read(bytes, 0, bytes.length)) > 0){
                outByte.write(bytes, 0, n);
            }

            System.out.println(outByte.toByteArray().length);

            String str = new String(outByte.toByteArray(), StandardCharsets.UTF_8);
            System.out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            buffer.clear();
        }
    }
}
