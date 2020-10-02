package fengfei.studio.datajob;

public class SchoolMap {
    private String dgSchema;
    private String centerName;
    private String gzSchema;
    private SchoolMapType type;

    public SchoolMap(SchoolMapType type, String dgSchema, String centerName, String gzSchema) {
        this.type = type;
        this.dgSchema = dgSchema;
        this.gzSchema = gzSchema;
        this.centerName = centerName;
    }

    public String getDgSchema() {
        return dgSchema;
    }

    public void setDgSchema(String dgSchema) {
        this.dgSchema = dgSchema;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getGzSchema() {
        return gzSchema;
    }

    public void setGzSchema(String gzSchema) {
        this.gzSchema = gzSchema;
    }

    public SchoolMapType getType() {
        return type;
    }

    public void setType(SchoolMapType type) {
        this.type = type;
    }

    public enum SchoolMapType {
        College, Academy, Class
    }

}

