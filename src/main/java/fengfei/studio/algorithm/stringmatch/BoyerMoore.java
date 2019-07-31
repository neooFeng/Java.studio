package fengfei.studio.algorithm.stringmatch;

import java.util.HashMap;
import java.util.Map;

class BoyerMoore {
    /**
     * Boyer、Moore一起发明的算法，思想是每次对比时计算好后缀和坏字符，一次向后移动多个字符，最佳时间复杂度O(n/m)，实际情况比较复杂，大概O(3～5n)
     * @param text  目标字符串
     * @param target    模式串
     * @return  模式串在目标串中首次匹配的位置，没有匹配返回-1
     */
    static int find(String text, String target){
        int textLength = text.length();
        int targetLength = target.length();

        Map<Character, Integer> badWordMap = generateBadCharMap(target, targetLength);

        int i=0;
        while (i < textLength-targetLength){
            int j = targetLength-1;
            while(j>0 && target.charAt(j) == text.charAt(i+j)){
                j--;
            }

            if (j==0){ // found
                return i;
            }else{
                char badChar = text.charAt(i+j);
                // find if there is a char in target equals to text.charAt(i+j)
                int xi = badWordMap.containsKey(badChar) ? badWordMap.get(badChar) : -1;
                for (int n=targetLength-2; n>0; n--){
                    if (target.charAt(n) == badChar){
                        xi = n;
                        break;
                    }
                }
                i += j-xi;
            }
        }

        return -1;
    }

    private static Map<Character, Integer> generateBadCharMap(String target, int targetLen){
        Map<Character, Integer> result = new HashMap<>();
        for(int i=0; i<targetLen; i++){
            result.put(target.charAt(i), i);
        }

        return result;
    }
}
