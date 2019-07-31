package fengfei.studio.algorithm.stringmatch;

public class BruteForce {
    /**
     * 暴力查找，时间复杂度O(m*n)
     * @param text  目标字符串
     * @param target    模式串
     * @return  模式串在目标串中首次匹配的位置，没有匹配返回-1
     */
    public static int find(String text, String target){
        for (int i=0; i<text.length(); i++){
            char firstTargetChar = target.charAt(0);
            if (text.charAt(i) == firstTargetChar){
                int j = 1;
                while (j < target.length() && text.charAt(i+j) == target.charAt(j)){
                    j++;
                }
                if (j == target.length()){
                    return i;
                }
            }
        }

        return -1;
    }
}
