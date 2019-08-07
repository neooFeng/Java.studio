package fengfei.studio.algorithm.stringmatch;

import org.junit.Test;

public class MultiPatternMatchTest {
    final int loopCount = 10;
    final int textLength = 100000;
    final int patternCount = 10000;

    @Test
    public void MultiBF(){
        String[] sources = TextGenerator.genChinese(loopCount, textLength);
        String[] targets = TextGenerator.getTargetPatterns(patternCount);

        long start = System.currentTimeMillis();
        for (String source : sources){
            MultiBF.getFirstMatch(source, targets);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void testTrie(){
        String[] sources = TextGenerator.genChinese(loopCount, textLength);
        String[] targets = TextGenerator.getTargetPatterns(patternCount);

        long start = System.currentTimeMillis();
        for (String source : sources){
            Trie.getFirstMatch(source, targets);
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}
