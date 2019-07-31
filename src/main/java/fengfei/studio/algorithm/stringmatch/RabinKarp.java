package fengfei.studio.algorithm.stringmatch;

public class RabinKarp {
    /**
     * Rabin、Karp一起发明的算法，思想是利用hash对比模式串与主串的所有可能子串，时间复杂度O(n)，极端hash冲突时退化为O(m*n)
     * @param text
     * @param target
     * @return
     */
    private final static int[] ratio = new int[30];
    static {
        ratio[0] = 1;
        int i=1;
        while (i < ratio.length){
            ratio[i] = ratio[i-1] * 31;
            i++;
        }
    }

    public static int find(String text, String target){
        // 为主串的所有可能子串计算hash，hash算法的效率是关键，不然又退化会O(m*n)
        int targetLength = target.length();
        int textLength = text.length();
        int[] h = new int[textLength-targetLength+1];
        for (int i=0; i<=textLength-targetLength; i++) {
            if (i == 0){
                int j=0;
                while (j<targetLength){
                    h[i] += text.charAt(i+j) * ratio[targetLength-j-1];
                    j++;
                }
            }else{
                h[i] = (h[i-1] - text.charAt(i-1) * ratio[targetLength-1]) * 31 + text.charAt(i+targetLength-1);
            }
            //h[i] = text.substring(i, i+targetLength).hashCode();
        }

        // 查询
        int targetHash = target.hashCode();
        for (int index=0; index<h.length; index++){
            if (h[index] == targetHash){
                return index;
            }
        }

        return -1;
    }
}
