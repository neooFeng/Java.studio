package fengfei.studio.algorithm.stringmatch;

class BruteForce {
    /**
     * 暴力查找，时间复杂度O(m*n)，实际使用时间复杂度接近O(n)
     *
     * 适用场景：规模较小的搜索（目标串百万字符以内）
     *
     * @param text  目标字符串
     * @param target    模式串
     * @return  模式串在目标串中首次匹配的位置，没有匹配返回-1
     */
    static int find(String text, String target){
        int targetLength = target.length();
        char firstTargetChar = target.charAt(0);
        int end = text.length() - targetLength;
        for (int i=0; i<end; i++){
            // optimize with processor pipeline
            /*
            if (text.charAt(i) != firstTargetChar) {
                // continue;
                while (++i < end && text.charAt(i) != firstTargetChar);
            }*/

            if (i < end && text.charAt(i) == firstTargetChar){
                int j = i + 1;
                int k = 1;

                while (j < targetLength && j < end && text.charAt(j) == target.charAt(k)){
                    j++;
                    k++;
                }
                if (j == targetLength){
                    return i;
                }
            }
        }

        return -1;
    }
}
