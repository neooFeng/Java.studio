package fengfei.studio.algorithm.stringmatch;

import org.junit.Test;

import java.lang.management.BufferPoolMXBean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MatchTest {
    final int loopCount = 100;
    final int textLength = 1000000;
    final String target = "厂七卜人入八九几儿了力乃刀又三于干亏士工土才寸下大丈与万上小口巾山千乞川亿个勺久凡及夕丸么广亡门义之尸弓己已子卫也女飞刃习叉马乡丰王井开夫天无元专云扎艺木五支厅不太犬区历尤友匹车巨牙屯比互切瓦止少日中冈贝内水见午牛手毛气升长仁什片仆化仇币仍仅斤爪反介父从今凶分乏公仓月氏勿欠风丹匀乌凤勾文";

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
