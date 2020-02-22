package fengfei.studio.test;

import org.apache.commons.collections.CollectionUtils;

import java.util.*;

public class Tmp {
    public static void main(String[] args) {
        Map<Integer, List<Integer>> schoolLives = new HashMap<>();
        schoolLives.putAll(getPXSchoolLives());
        //schoolLives.putAll(getGZSchoolLives());

        int count = 0;
        for (Integer schoolId : schoolLives.keySet()){
            System.out.println(schoolId);
            System.out.println(schoolLives.get(schoolId));
            count += schoolLives.get(schoolId).size();
        }

        System.out.println(count);
    }

    private static Map<Integer, List<Integer>> getGZSchoolLives() {
        Map<Integer, List<Integer>> schoolLives = new HashMap<>();

        List<Integer> gzSchoolIds = DBUtil.getGlobalTemplate().queryForList("select s.id FROM school s " +
                "inner join school_category sc on s.category_id = sc.id " +
                "where sc.type =6 and s.is_group = 0;", Integer.class);

        for (Integer schoolId : gzSchoolIds) {
            List<Integer> commonLiveIds = null;
            try{
                commonLiveIds = DBUtil.getSchoolTemplate(schoolId).queryForList("SELECT common_live_id FROM live_lesson where status = 3", Integer.class);
            }catch (Exception e){
                e.printStackTrace();
            }

            if (CollectionUtils.isNotEmpty(commonLiveIds)){
                schoolLives.put(schoolId, commonLiveIds);
            }
        }

        return schoolLives;
    }

    private static Map<Integer, List<Integer>> getPXSchoolLives() {
        Map<Integer, List<Integer>> schoolLives = new HashMap<>();

        List<Integer> pxSchoolIds = DBUtil.getGlobalTemplate().queryForList("select s.id FROM school s " +
                "inner join school_category sc on s.category_id = sc.id " +
                "where sc.type = 0;", Integer.class);

        for (Integer schoolId : pxSchoolIds) {
            List<Integer> commonLiveIds = null;
            try {
                commonLiveIds = DBUtil.getSchoolTemplate(schoolId).queryForList("SELECT common_live_id FROM live_class where type = 2 and status = 4", Integer.class);
            }catch (Exception e){
                e.printStackTrace();
            }

            if (CollectionUtils.isNotEmpty(commonLiveIds)){
                schoolLives.put(schoolId, commonLiveIds);
            }
        }

        return schoolLives;
    }
}
