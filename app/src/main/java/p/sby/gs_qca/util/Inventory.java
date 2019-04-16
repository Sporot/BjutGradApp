package p.sby.gs_qca.util;

/**
 * 清单
 * <p>
 * Created by DavidChen on 2018/5/23.
 */

public class Inventory {
    private String itemDesc;
    private String itemCode;
    private String date;
    private String id;
    private String experts;
    private String filepath;
    private String type;

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id=id;
    }

    public String getExperts() {
        return experts;
    }

    public void setExperts(String experts) {
        this.experts = experts;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
