package bl.beans;

import org.mongodb.morphia.annotations.Transient;

/**
 * Created by limin.llm on 2014/9/13.
 * 支持文件和图片得到控件，暂时不改变类名称，避免改动太多
 */
public class ImageInfoBean {
    private String name;
    private String fileName;
    private long size;
    private String fileType = "image"; //image or binary，为了兼容上传文件和图片

    @Transient
    private ProductBean product;

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }
}
