package fengfei.studio.algorithm.stringmatch;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MatchTest {
    final int loopCount = 100;
    final int textLength = 1000000;
    final String target = "驼绍经贯奏春帮珍玻毒型挂封持项垮挎城挠政赴赵挡挺括拴拾挑指";

    @Test
    public void testBFPerformance(){
        String[] sources = TextGenerator.genChinese(loopCount, textLength);

        long start = System.currentTimeMillis();
        for (int i=0; i<loopCount; i++){
            BruteForce.find(sources[i], target);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void testRKPerformance() {
        RabinKarp.find("为了初始化一些static数据", "为了初");

        String[] sources = TextGenerator.genChinese(loopCount, textLength);

        long start = System.currentTimeMillis();
        for (int i=0; i<loopCount; i++){
            RabinKarp.find(sources[i], target);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void testKMPPerformance(){
        String[] sources = TextGenerator.genChinese(loopCount, textLength);

        long start = System.currentTimeMillis();
        for (int i=0; i<loopCount; i++){
            KMP.find(sources[i], target);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void testBMPerformance(){
        String[] sources = TextGenerator.genChinese(loopCount, textLength);

        long start = System.currentTimeMillis();
        for (int i=0; i<loopCount; i++){
            BoyerMoore.find(sources[i], target);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void testJDKPerformance() {
        String[] sources = TextGenerator.genChinese(loopCount, textLength);

        long start = System.currentTimeMillis();
        for (int i = 0; i < loopCount; i++) {
            sources[i].contains(target);
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}
