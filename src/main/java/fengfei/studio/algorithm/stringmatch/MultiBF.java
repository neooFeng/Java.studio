package fengfei.studio.algorithm.stringmatch;

class MultiBF {
    /**
     * 暴力查找目标串包含的第一个模式串
     *
     * @param text 目标串
     * @return 目标串中存在的第一个模式串
     */
    public static String getFirstMatch(String text, String[] targets){
        for (String target : targets){
            //int index = text.indexOf(target);
            int index = BoyerMoore.find(text, target);
            if (index > -1){
                return target;
            }
        }

        return null;
    }
}
