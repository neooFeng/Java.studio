package fengfei.studio.algorithm.stringmatch;

import java.util.HashMap;
import java.util.Map;

/**
 * 又称字典树，是一种多模式字符串匹配算法。
 * 思想是利用多个模式串的公共前缀，构造字典树，匹配时遍历对比主串字符与字典树根节点是否匹配，匹配则继续对比下一个主串字符和可选叶子节点；不匹配在主串index+1
 * 时间复杂度O(n*m)，m为字典树高度
 */
class Trie {
    private static final String END_FLAG="end";
    private static Map<Object,Object> sensitiveWordsMap = new HashMap<>();

    /**
     * 查找目标串中的第一个模式串
     *
     * @param text 目标串
     * @return 目标串中存在的第一个模式串
     */
    public static String getFirstMatch(String text, String[] targets){
        if (sensitiveWordsMap == null || sensitiveWordsMap.isEmpty()){
            initSensitiveWordsMap(targets);
        }

        for(int i=0; i<text.length(); i++){
            int length = getMatchedLength(text, i);
            if (length > 0){
                return text.substring(i, i+length);
            }
        }
        return null;
    }

    /**
     * 构造模式串字典树(trie)
     * @param targets 模式串列表
     */
    private static void initSensitiveWordsMap(String[] targets){
        sensitiveWordsMap = new HashMap<>(targets.length);

        Map<Object,Object> currentMap;
        Map<Object,Object> subMap;
        for (String currentWord : targets){
            currentMap = sensitiveWordsMap;
            for(int i=0; i < currentWord.length(); i++){
                char c = currentWord.charAt(i);
                subMap = (Map<Object, Object>) currentMap.get(c);
                if(subMap == null){
                    subMap = new HashMap<>();
                    currentMap.put(c, subMap);
                    currentMap = subMap;
                }else {
                    currentMap = subMap;
                }
                if(i == currentWord.length() - 1){
                    currentMap.put(END_FLAG, null);
                }
            }
        }
    }

    private static int getMatchedLength(String text, int startIndex){
        char currentChar;
        Map<Object, Object> currentMap = sensitiveWordsMap;
        int wordLength = 0;
        boolean endFlag = false;
        for(int i=startIndex; i<text.length(); i++){
            currentChar = text.charAt(i);
            Map<Object,Object> subMap = (Map<Object,Object>) currentMap.get(currentChar);
            if(subMap == null){
                break;
            }else {
                wordLength++;
                if(subMap.containsKey(END_FLAG)){
                    endFlag = true;
                    break;
                }else {
                    currentMap = subMap;
                }
            }
        }
        if(!endFlag) {
            wordLength = 0;
        }
        return wordLength;
    }
}
