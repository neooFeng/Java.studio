package fengfei.studio.algorithm.stringmatch;

class KMP {
    /**
     * Knuth, Morris, Pratt三人发明的算法，思想是利用好前缀中可能存在的首尾公共字符串，一次向后移动多个字符，时间复杂度O(m+n)
     * Example: text: ababacdab, target: ababae. 则ababa是好前缀，aba是首尾公共字符串
     *
     * 适用场景：模式串可以构造出有效的next数组的场景
     *
     * @param text  目标字符串
     * @param target    模式串
     * @return  模式串在目标串中首次匹配的位置，没有匹配返回-1
     */
    static int find(String text, String target){
        int[] next = getNexts(target);

        int j = 0;
        for (int i=0; i<text.length(); i++){
            while (j>0 && text.charAt(i) != target.charAt(j)){   // 计算时间复杂度主要看while循环体的执行次数
                j = next[j-1] + 1;
            }

            if (text.charAt(i) == target.charAt(j)){
                j++;
            }

            if (j == target.length()){
                return i-j+1;
            }
        }

        return -1;
    }

    /**
     * 计算模式串失效数组
     * example1: ababcd
     * example2: abcabeabcabcd
     *
     * @param target 模式串
     */
    private static int[] getNexts(String target) {
        int maxSubStringLength = target.length()-1;
        int[] next = new int[maxSubStringLength];

        next[0] = -1;

        int k = -1;
        for (int i=1; i<maxSubStringLength; i++){
            while (k != -1 && target.charAt(i) != target.charAt(k+1)){
                k = next[k];
            }

            if (target.charAt(i) == target.charAt(k+1)){
                k++;
            }

            next[i] = k;
        }

        return next;
    }
}
