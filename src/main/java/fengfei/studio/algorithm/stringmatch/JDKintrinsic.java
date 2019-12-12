package fengfei.studio.algorithm.stringmatch;

public class JDKintrinsic {
    public static void main(String[] args) {
        String text = "Library/Java/JavaVirtualMachingent:/Applications/Intelli";
        String target = "Applications";

        long c = System.currentTimeMillis();
        for (int i=0; i<1000000000;i++) {
            findByCodeSameAsJDK(text, target);
            //findByJDK(text, target);
        }
        System.out.println(System.currentTimeMillis() - c);
    }


    public static int findByJDK(String text, String target) {
        return text.indexOf(target);
    }

    public static int findByCodeSameAsJDK(String text, String target) {
        return indexOf2(text.toCharArray(), 0, text.length(),
                target.toCharArray(), 0, target.length(), 0);
    }

    static int indexOf2(char[] source, int sourceOffset, int sourceCount,
                        char[] target, int targetOffset, int targetCount,
                        int fromIndex) {
        if (fromIndex >= sourceCount) {
            return (targetCount == 0 ? sourceCount : -1);
        }
        if (fromIndex < 0) {
            fromIndex = 0;
        }
        if (targetCount == 0) {
            return fromIndex;
        }

        char first = target[targetOffset];
        int max = sourceOffset + (sourceCount - targetCount);

        for (int i = sourceOffset + fromIndex; i <= max; i++) {
            /* Look for first character. */
            if (source[i] != first) {
                while (++i <= max && source[i] != first);
            }

            /* Found first character, now look at the rest of v2 */
            if (i <= max) {
                int j = i + 1;
                int end = j + targetCount - 1;
                for (int k = targetOffset + 1; j < end && source[j]
                        == target[k]; j++, k++);

                if (j == end) {
                    /* Found whole string. */
                    return i - sourceOffset;
                }
            }
        }
        return -1;
    }
}
