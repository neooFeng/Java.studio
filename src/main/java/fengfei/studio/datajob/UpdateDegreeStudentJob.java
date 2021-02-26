package fengfei.studio.datajob;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class UpdateDegreeStudentJob {

    public static void main(String[] args) {
        String filePath = "C:\\Users\\fengfei\\Downloads\\21级助考账号删除.xlsx";

        String studentids = "34079,34081,34082,34083,34084,34085,34086,34087,34088,34089,34090,34091,34092,34093,34094,34095,34096,34097,34098,34099,34100,34101,34102,34103,34104,34105,34106,34107,34108,34109,34110,34111,34112,34113,34114,34115,34116,34117,34118,34119,34120,34121,34122,34123,34124,34125,34126,34127,34128,34129,34130,34131,34132,34133,34134,34135,34136,34137,34138,34139,34140,34141,34142,34143,34144,34145,34146,34147,34148,34149,34150,34151,34152,34153,34154,34155,34156,34157,34158,34159,34160,34161,34162,34163,34164,34165,34166,34167,34168,34169,34170,34171,34172,34173,34174,34175,34176,34177,34178,34179,34180,34181,34182,34183,34184,34185,34186,34187,34188,34189,34190,34191,34192,34193,34194,34195,34196,34197,34198,34199,34200,34201,34202,34203,34204,34205,34206,34207,34208,34209,34210,34211,34212,34213,34214,34215,34216,34217,34218,34219,34220,34221,34222,34223,34224,34225,34226,34227,34228,34229,34231,34232,34233,34234,34235,34236,34237,34238,34239,34240,34241,34242,34243,34244,34245,34246,34247,34248,34249,34250,34251,34252,34253,34254,34255,34256,34257,34258,34259,34260,34261,34262,34263,34264,34265,34266,34267,34268,34269,34270,34271,34272,34273,34274,34275,34276,34277,34278,34279,34280,34281,34282,34283,34284,34285,34286,34287,34288,34289,34290,34291,34292,34293,34294,34295,34296,34297,34298,34299,34300,34301,34302,34303,34304,34305,34306,34307,34308,34309,34310,34311,34312,34313,34314,34315,34316,34317,34318,34319,34320,34321,34322,34323,34324,34325,34326,34327,34328,34329,34330,34331,34332,34333,34334,34335,34336,34337,34338,34339,34340,34341,34342,34343,34344,34345,34346,34347,34348,34349,34350,34351,34352,34353,34354,34355,34356,34357,34358,34359,34360,34361,34362,34363,34364,34365,34366,34367,34368,34369,34370,34371,34372,34373,34374,34375,34376,34377,34378,34379,34380,34381,34382,34383,34384,34385,34386,34387,34388,34389,34390,34391,34392,34393,34395,34396,34397,34398,34399,34400,34401,34402,34403,34404,34405,34406,34407,34408,34409,34410,34411,34412,34413,34414,34415,34416,34417,34418,34419,34420,34421,34422,34423,34424,34425,34426,34427,34428,34429,34430,34431,34432,34433,34434,34435,34436,34437,34438,34439,34440,34441,34442,34443,34492,34493,34494,34495,34496,34497,34498,34499,34500,34501,34502,34503,34504,34505,34506,34507,34508,34509,34510,34511,34512,34513,34514,34515,34516,34517,34518,34519,34520,34521,34522,34523,34524,34525,34526,34527,34528,34529,34530,34531,34532,34533,34534,34535,34536,34537,34538,34539,34540,34541,34542,34543,34544,34545,34546,34547,34548,34549,34550,34551,34552,34553,34554,34555,34556,34557,34558,34559,34560,34561,34562,34563,34564,34565,34566,34567,34568,34569,34570,34571,34572,34573,34574,34575,34576,34577,34578,34579,34580,34581,34582,34583,34584,34585,34586,34587,34588,34589,34590,34591,34592,34593,34623,34624,34625,34626,34627,34628,34629,34630,34631,34632,34633,34634,34635,34636,34637,34638,34639,34640,34641,34642,34643,34654,34655,34656,34657,34658,34659,34660,34661,34662,34663,34664,34665,34666,34667,34668,34669,34670,34671,34672,34673,34674,34675,34676,34677,34678,34679,34680,34681,34682,34683,34684,34685,34686,34687,34688,34689,34690,34691,34692,34693,34694,34695,34696,34697,34698,34699,34700,34701,34702,34703,34704,34705,34706,34707,34708,34709,34710,34711,34712,34713,34714,34717,34718,34719,34721,34722,34723,34724,34725,34726,34727,34728,34729,34730,34736,34737,34738,34739,34740,34741,34742,34743,34744,34745,34746,34747,34748,34749,34750,34751,34752,34753,34754,34755,34756,34757,34758,34759,34760,34761,34762,34763,34764,34765,34766,34767,34768,34769,34770,34771,34772,34773,34774,34775,34776,34777,34778,34779,34780,34781,34782,34783,34784,34785,34786,34787,34788,34789,34790,34791,34792,34793,34802,34803,34804,34805,34806,34807,34808,34809,34810,34811,34812,34813,34814,34815,34816,34817,34818,34819,34820,34821,34822,34823,34824,34825,34834,34835,34836,34837,34838,34839,34840,34841,34842,34843,34844,34845,34846,34847,34848,34849,34852,34853,34854,34855,34856,34857,34858,34861,34862,34863,34864,34865,34866,34867,34868,34869,34870,34871,34872,34873,34874,34875,34876,34877,34878,34879,34880,34881,34882,34883,34884,34885,34886,34887,34889,34890,34891,34892,34893,34894,34895,34896,34897,34898,34899,34900,34901,34902,34903,34904,34905,34906,34907,34908,34909,34910,34911,34912,34913,34914,34915,34916,34917,34918,34919,34920,34921,34922,34923,34924,34925,34926,34927,34928,34929,34930,34931,34932,34933,34934,34935,34936,34937,34938,34939,34940,34941,34942,34943,34944,34945,34946,34947,34948,34988,34989,34990,34991,34992,34993,34994,34995,34996,34997,34998,34999,35003,35004";

        int schoolId = 4069;

       // List<Student> studentList = parseExcel(filePath);


        // updateGlobalUser(studentList);

        String[] studentIds = studentids.split(",");
        deleteSchoolUser(schoolId, studentIds);
    }

    private static void deleteSchoolUser(int schoolId, String[] studentList){
        JdbcTemplate schoolTemplate = DBUtil.getSchoolTemplate(schoolId);

        StringBuilder sb = new StringBuilder();
        for(String studentId : studentList){
            String sql = "call student_delete(" + studentId + ");\n";
            sb.append(sql);
        }

        System.out.println(sb);
    }

    private static Map<String, Integer> getFromSchool() {
        Map<String, Integer> resultMap = new HashMap<>();

        JdbcTemplate schoolTemplate = DBUtil.getSchoolTemplate(4027);
        List<Map<String, Object>> studentMapList = schoolTemplate.queryForList("SELECT candidate_number, global_user_id FROM student;");
        for (Map<String, Object> studentMap : studentMapList) {
            resultMap.put(studentMap.get("candidate_number").toString(), Integer.valueOf(studentMap.get("global_user_id").toString()));
        }

        return resultMap;
    }

    private static List<Student> parseExcel(String filePath) {
        List<Student> students = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream(new File(filePath));

            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);

            XSSFSheet sheet = xssfWorkbook.getSheetAt(1);
            Iterator<Row> rowIterator = sheet.rowIterator();

            while (rowIterator.hasNext()){
                Row row = rowIterator.next();
                if(row.getRowNum() < 1){
                    continue;
                }

                Student student = new Student();

                Cell cell0 = row.getCell(0);
                cell0.setCellType(CellType.STRING);
                student.setCandidateNumber(cell0.getStringCellValue());

                student.setGender(row.getCell(3).getStringCellValue());

                Cell cell4 = row.getCell(4);
                cell4.setCellType(CellType.STRING);
                String dateStr = cell4.getStringCellValue();

                int year = Integer.parseInt(dateStr.substring(0, 4));
                int month = Integer.parseInt(dateStr.substring(5,6));
                int day = Integer.parseInt(dateStr.substring(7,8));
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.set(year, month, day);
                student.setBirthday(calendar.getTime());

                Cell cell5 = row.getCell(5);
                cell5.setCellType(CellType.STRING);
                student.setIdentityCard(cell5.getStringCellValue());

                student.setPolitics(row.getCell(6).getStringCellValue());
                student.setNationnality(row.getCell(7).getStringCellValue());

                students.add(student);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return students;
    }

    private static class Student {
        private int id;
        private String candidateNumber;
        private int globalUserId;
        private String globalUsername;
        private String identityCard;
        private Date birthday;
        private String gender;
        private String politics;
        private String nationnality;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCandidateNumber() {
            return candidateNumber;
        }

        public void setCandidateNumber(String candidateNumber) {
            this.candidateNumber = candidateNumber;
        }

        public int getGlobalUserId() {
            return globalUserId;
        }

        public void setGlobalUserId(int globalUserId) {
            this.globalUserId = globalUserId;
        }

        public String getGlobalUsername() {
            return globalUsername;
        }

        public void setGlobalUsername(String globalUsername) {
            this.globalUsername = globalUsername;
        }

        public String getIdentityCard() {
            return identityCard;
        }

        public void setIdentityCard(String identityCard) {
            this.identityCard = identityCard;
        }

        public Date getBirthday() {
            return birthday;
        }

        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getPolitics() {
            return politics;
        }

        public void setPolitics(String politics) {
            this.politics = politics;
        }

        public String getNationnality() {
            return nationnality;
        }

        public void setNationnality(String nationnality) {
            this.nationnality = nationnality;
        }
    }
}
