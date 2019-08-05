package fengfei.studio.algorithm.stringmatch;

import java.util.HashMap;
import java.util.Map;

class BoyerMoore {
    /**
     * Boyer、Moore一起发明的算法，思想是每次对比时计算好后缀和坏字符，一次向后移动多个字符，最佳时间复杂度O(n/m)，实际情况比较复杂，大概O(3～5n)
     *
     * 适用场景：目标串很大，模式串较长且字符重复率不高的场景（比如搜索一句中文话）
     *
     * @param text  目标字符串
     * @param target    模式串
     * @return  模式串在目标串中首次匹配的位置，没有匹配返回-1
     */
    static int find(String text, String target){
        // 预先构建坏字符、好后缀查询表，减少查询阶段执行时间
        Map<Character, Integer> badWordMap = generateBadCharMap(target);
        /*int badCharArrLength = 256;
        int[] badCharArr = new int[badCharArrLength];
        generateBadCharArr(target, badCharArr, badCharArrLength);*/

        int textLength = text.length();
        int targetLength = target.length();
        /*int[] suffix = new int[targetLength];
        boolean[] prefix = new boolean[targetLength];
        generateGoodSuffixArr(target, suffix, prefix);*/

        int step;
        int baseTextIndex=0;
        for (; baseTextIndex <= textLength-targetLength; baseTextIndex += step){
            int targetIndex = targetLength-1;
            while(targetIndex >= 0 && target.charAt(targetIndex) == text.charAt(baseTextIndex + targetIndex)){
                targetIndex--;
            }

            if (targetIndex < 0){   // found
                return baseTextIndex;
            }

            char badChar = text.charAt(baseTextIndex + targetIndex);
            // find if there is a char in target equals to text.charAt(i+j)
            int foundIndex = badWordMap.containsKey(badChar) ? badWordMap.get(badChar) : -1;
            step = Math.max(targetIndex - foundIndex, 1);

            // if exists good suffix
            /*int goodSuffixLength = targetLength-1 - targetIndex;
            if (goodSuffixLength == 0) {
                baseTextIndex += forwardStep1;
            }else{
                int forwardStep2 = 0;
                if (suffix[goodSuffixLength] > -1){
                    forwardStep2 = targetLength - goodSuffixLength - suffix[goodSuffixLength];
                }else{
                    int commonPrefixLength = goodSuffixLength-1;
                    while (commonPrefixLength > 0){
                        if (prefix[commonPrefixLength] == true){
                            break;
                        }
                        commonPrefixLength--;
                    }
                    forwardStep2 = targetLength - commonPrefixLength;
                }

                baseTextIndex += Math.max(forwardStep1, forwardStep2);
            }*/
        }

        return -1;
    }

    private static void generateGoodSuffixArr(String target, int[] suffix, boolean[] prefix){
        for (int i=0; i<target.length(); i++){
            suffix[i] = -1;
        }

        int lastTargetIndex = target.length()-1;
        for (int i=0; i<lastTargetIndex; i++){
            int index=i;
            int goodSuffixLength = 0;
            while (index>=0 && target.charAt(index) == target.charAt(lastTargetIndex - goodSuffixLength)){
                index--;
                goodSuffixLength++;
                suffix[goodSuffixLength] = index + 1;  // 公共子串在target[0,len-1]中的下标
            }

            if (index == -1){    // 如果公共子串是从target第一个字符开始，说明公共子串是前缀公共子串
                prefix[goodSuffixLength] = true;
            }
        }
    }

    private static Map<Character, Integer> generateBadCharMap(String target){
        HashMap<Character, Integer> result = new HashMap<>();

        for(int i=0; i<target.length(); i++){
            result.put(target.charAt(i), i);
        }

        return result;
    }
}
