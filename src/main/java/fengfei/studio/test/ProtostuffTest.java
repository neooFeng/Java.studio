package fengfei.studio.test;

import com.carrotsearch.sizeof.RamUsageEstimator;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeEnv;
import io.protostuff.runtime.RuntimeSchema;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProtostuffTest {
    public static void main(String[] args) {
        List<TestObject> schoolMaps = new ArrayList<>(10);
        for (int i = 0; i< 10; i++){
            schoolMaps.add(new TestObject(500, 600));
        }

        System.out.println(RamUsageEstimator.sizeOf(schoolMaps));

        double countedFinalScore = 0;
        final String[] countedFinalScoreLevel = new String[1];

        Schema<TestObject> s = RuntimeSchema.createFrom(TestObject.class, RuntimeEnv.ID_STRATEGY);
        LinkedBuffer buffer = LinkedBuffer.allocate(1024 * 1024);
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ProtostuffIOUtil.writeListTo(b, schoolMaps, s, buffer);

            System.out.println(b.toByteArray().length);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            buffer.clear();
        }
    }


    public static class TestObject {
        private String name;

        private Integer lowGrade;

        private Integer highGrade;

        public TestObject(int lowGrade, int highGrade){
            this.lowGrade = lowGrade;
            this.highGrade = highGrade;
        }

        public Integer getLowGrade() {
            return lowGrade;
        }

        public void setLowGrade(Integer lowGrade) {
            this.lowGrade = lowGrade;
        }

        public Integer getHighGrade() {
            return highGrade;
        }

        public void setHighGrade(Integer highGrade) {
            this.highGrade = highGrade;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
