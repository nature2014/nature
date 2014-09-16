package actions.upload;

import bl.beans.ImageInfoBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.bson.types.ObjectId;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * Created by limin.llm on 2014/9/13.
 * 接口API:https://github.com/blueimp/jQuery-File-Upload/wiki/Setup
 */
public class UploadMultipleImageAction extends HttpServlet {

    private final static Logger LOG = LoggerFactory.getLogger(UploadMultipleImageAction.class);
    private static File FILEPATH = null;
    private static String contextPath = null;

    static {
        LOG.debug("初始化图片路径");
        String catalinaHome = System.getProperty("catalina.home");
        FILEPATH = new File(catalinaHome + File.separator + "upload");
        if (!FILEPATH.exists()) {
            //确保目录存在
            FILEPATH.mkdir();
        }
        LOG.debug("初始化应用服务器上下文地址");
        contextPath = ServletActionContext.getServletContext().getContextPath();
    }

    private File[] images; //上传的文件
    private String[] imagesFileName; //文件名称
    private String[] imagesContentType; //文件类型

    //获取文件参数
    private String getfile;
    //删除文件
    private String delfile;
    //获取缩略图
    private String getthumb;

    private int targetSize;

    public int getTargetSize() {
        return targetSize;
    }

    public void setTargetSize(int targetSize) {
        this.targetSize = targetSize;
    }

    public File[] getImages() {
        return images;
    }

    public void setImages(File[] images) {
        this.images = images;
    }

    public String[] getImagesFileName() {
        return imagesFileName;
    }

    public void setImagesFileName(String[] imagesFileName) {
        this.imagesFileName = imagesFileName;
    }

    public String[] getImagesContentType() {
        return imagesContentType;
    }

    public void setImagesContentType(String[] imagesContentType) {
        this.imagesContentType = imagesContentType;
    }

    public String getGetfile() {
        return getfile;
    }

    public void setGetfile(String getfile) {
        this.getfile = getfile;
    }

    public String getDelfile() {
        return delfile;
    }

    public void setDelfile(String delfile) {
        this.delfile = delfile;
    }

    public String getGetthumb() {
        return getthumb;
    }

    public void setGetthumb(String getthumb) {
        this.getthumb = getthumb;
    }

    public String getImage() {
        try {
            HttpServletResponse response = ServletActionContext.getResponse();

            if (StringUtils.isNotEmpty(this.getfile)) {
                File file = new File(FILEPATH, this.getfile);
                if (file.exists()) {
                    int bytes = 0;
                    response.setContentType(getMimeType(file));
                    response.setContentLength((int) file.length());
                    response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");

                    FileInputStream from = new FileInputStream(file);
                    //对拷文件流
                    IOUtils.copy(from, response.getOutputStream());
                    response.getOutputStream().close();
                }
            } else if (StringUtils.isNotEmpty(delfile)) {
                File file = new File(FILEPATH, this.delfile);
                if (file.exists()) {
                    file.delete();
                }
                JSONObject outPutJson = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                JSONObject deleteInfo = new JSONObject();
                deleteInfo.put(this.delfile, true);
                jsonArray.add(deleteInfo);
                outPutJson.put("files", jsonArray);
                response.getWriter().write(outPutJson.toString());
                response.getWriter().close();

            } else if (StringUtils.isNotEmpty(getthumb)) {
                File file = new File(FILEPATH, this.getthumb);
                if (file.exists()) {
                    String mimetype = getMimeType(file);
                    if (mimetype.endsWith("jpg") || mimetype.endsWith("png") || mimetype.endsWith("jpeg") || mimetype.endsWith("gif")) {
                        BufferedImage im = ImageIO.read(file);
                        if (im != null) {
                            //缩略图
                            BufferedImage thumb = null;
                            if (targetSize == 0) {
                                thumb = Scalr.resize(im, 120);
                            } else {
                                thumb = Scalr.resize(im, targetSize);
                            }
                            ByteArrayOutputStream os = new ByteArrayOutputStream();
                            if (mimetype.endsWith("png")) {
                                ImageIO.write(thumb, "PNG", os);
                                response.setContentType("image/png");
                            } else if (mimetype.endsWith("jpg")) {
                                ImageIO.write(thumb, "jpg", os);
                                response.setContentType("image/jpg");
                            } else if (mimetype.endsWith("jpeg")) {
                                ImageIO.write(thumb, "jpeg", os);
                                response.setContentType("image/jpeg");
                            } else {
                                ImageIO.write(thumb, "GIF", os);
                                response.setContentType("image/gif");
                            }
                            ServletOutputStream srvos = response.getOutputStream();
                            response.setContentLength(os.size());
                            response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
                            os.writeTo(srvos);
                            srvos.flush();
                            srvos.close();
                            os.close();
                        }
                    } else {
                        //此为普通二进制文件
                        response.setContentType("application/octef-stream");
                        response.setContentLength((int) file.length());
                        FileInputStream from = new FileInputStream(file);
                        //对拷文件流
                        IOUtils.copy(from, response.getOutputStream());
                        response.getOutputStream().close();
                        from.close();
                    }
                }
            }

        } catch (Exception e) {
            LOG.error("this exception [{}]", e.getMessage());
        }
        return null;
    }

    public String uploadImage() {

        PrintWriter writer = null;
        JSONObject outPutJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            writer = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            for (int i = 0; i < imagesFileName.length; i++) {
                String uuid = ObjectId.get().toString();
                String fileName = uuid + imagesFileName[i];
                File file = new File(FILEPATH, fileName);
                //临时目录上传文件
                File temp = this.images[i];
                //文件流对copy
                FileInputStream from = new FileInputStream(temp);
                FileOutputStream to = new FileOutputStream(file);
                IOUtils.copy(from, to);
                IOUtils.closeQuietly(to);
                IOUtils.closeQuietly(from);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("fileName", fileName);
                jsonObject.put("name", imagesFileName[i]);
                jsonObject.put("size", file.length());

                jsonObject.put("deleteType", "GET");
                jsonObject.put("url", contextPath + "/upload/getImage.action?getfile=" + fileName);
                jsonObject.put("deleteUrl", contextPath + "upload/getImage.action?delfile=" + fileName);

                String mimetype = getMimeType(file);
                if (mimetype.endsWith("jpg") || mimetype.endsWith("png") || mimetype.endsWith("jpeg") || mimetype.endsWith("gif")) {
                    //图片格式
                    jsonObject.put("fileType", "image");
                    jsonObject.put("thumbnailUrl", contextPath + "/upload/getImage.action?getthumb=" + fileName);

                } else {
                    //区分文件
                    jsonObject.put("fileType", "binary");
                    //换成文件的图标
                    jsonObject.put("thumbnailUrl", contextPath + "/jslib/flatlab/img/404_icon.png");
                }
                jsonArray.add(jsonObject);
            }
            outPutJson.put("files", jsonArray);
        } catch (Exception e) {
            LOG.error("this exception [{}]", e.getMessage());
        } finally {
            writer.write(outPutJson.toString());
            writer.close();
        }
        return null;
    }

    private String getMimeType(File file) {
        String mimetype = "";
        if (file.exists()) {
            String suffix = getSuffix(file.getName());
            if (suffix.equalsIgnoreCase("png")) {
                mimetype = "image/png";
            } else if (suffix.equalsIgnoreCase("jpg")) {
                mimetype = "image/jpg";
            } else if (suffix.equalsIgnoreCase("jpeg")) {
                mimetype = "image/jpeg";
            } else {
                javax.activation.MimetypesFileTypeMap mtMap = new javax.activation.MimetypesFileTypeMap();
                mimetype = mtMap.getContentType(file);
            }
        }
        LOG.debug("mimetype: " + mimetype);
        return mimetype;
    }


    private String getSuffix(String filename) {
        String suffix = "";
        int pos = filename.lastIndexOf('.');
        if (pos > 0 && pos < filename.length() - 1) {
            suffix = filename.substring(pos + 1);
        }
        LOG.debug(suffix);
        return suffix;
    }

    public static String jsonFromImageInfo(List<ImageInfoBean> imageInfoBeanList) {
        JSONObject outPutJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if (imageInfoBeanList != null) {
            for (ImageInfoBean infoBean : imageInfoBeanList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("fileName", infoBean.getFileName());
                jsonObject.put("name", infoBean.getName());
                jsonObject.put("size", infoBean.getSize());
                jsonObject.put("fileType", infoBean.getFileType());
                jsonObject.put("url", contextPath + "/upload/getImage.action?getfile=" + infoBean.getFileName());
                jsonObject.put("deleteUrl", contextPath + "/upload/getImage.action?delfile=" + infoBean.getFileName());
                String fileType = infoBean.getFileType();
                if (fileType.equals("image")) {
                    //图片格式
                    jsonObject.put("thumbnailUrl", contextPath + "/upload/getImage.action?getthumb=" + infoBean.getFileName());

                } else {
                    //换成文件的图标
                    jsonObject.put("thumbnailUrl", contextPath + "/jslib/flatlab/img/404_icon.png");
                }
                jsonObject.put("deleteType", "GET");
                jsonArray.add(jsonObject);
            }
            outPutJson.put("files", jsonArray);
        } else {
            outPutJson.put("files", jsonArray);
        }
        return outPutJson.toString();
    }
}
