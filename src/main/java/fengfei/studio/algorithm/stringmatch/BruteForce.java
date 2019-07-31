package fengfei.studio.algorithm.stringmatch;

class BruteForce {
    /**
     * 暴力查找，时间复杂度O(m*n)，实际使用时间复杂度接近O(n)
     * @param text  目标字符串
     * @param target    模式串
     * @return  模式串在目标串中首次匹配的位置，没有匹配返回-1
     */
    static int find(String text, String target){
        int end = text.length() - target.length();
        for (int i=0; i<end; i++){
            char firstTargetChar = target.charAt(0);
            if (text.charAt(i) == firstTargetChar){
                int j = 1;

                while (j < target.length() && i<end && text.charAt(i+j) == target.charAt(j)){
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
