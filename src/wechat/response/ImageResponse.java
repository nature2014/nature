package wechat.response;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.io.xml.DomDriver;
import wechat.BaseMessage;
import wechat.utils.Constants;

import java.util.List;

/**
 * Created by wangronghua on 14-3-11.
 */
public class ImageResponse extends BaseMessage {
    private int ArticleCount = 0;
    @XStreamAlias("Articles")
    private Articles Articles = new Articles();

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public Articles getArticles() {
        return Articles;
    }

    public void setArticles(Articles articles) {
        this.Articles = articles;
    }

    public void setMediaId(String mediaId) {
        try {
            XStream myImageContent = new XStream(new DomDriver());
            myImageContent.processAnnotations(Articles.class);
            Articles articles = (Articles) myImageContent.fromXML(mediaId);
            this.Articles = articles;
            setArticleCount(this.Articles.getItem().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ImageResponse() {
        this.setMsgType(Constants.MSG_TYPE_NEWS);
    }

    public String toXml() {
        xstream.alias("xml", this.getClass());
        xstream.processAnnotations(Articles.class);
        return xstream.toXML(this);
    }

    public static void main(String[] args) {
        String mediaId = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><Articles>\n" +
                "<item>\n" +
                "<Title><![CDATA[title1]]></Title> \n" +
                "<Description><![CDATA[description1]]></Description>\n" +
                "<PicUrl><![CDATA[picurl]]></PicUrl>\n" +
                "<Url><![CDATA[url]]></Url>\n" +
                "</item></Articles>";
        XStream xStream1 = new XStream(new DomDriver());
        xStream1.processAnnotations(Articles.class);
        Articles articles = (Articles) xStream1.fromXML(mediaId);
        System.out.println(articles.item.size());
    }
}

@XStreamAlias("Articles")
class Articles {
    @XStreamImplicit(itemFieldName = "item")
    List<Item> item;

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }
}

@XStreamAlias("item")
class Item {
    private String Title;
    private String Description;
    private String PicUrl;
    private String Url;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}