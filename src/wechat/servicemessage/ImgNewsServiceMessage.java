package wechat.servicemessage;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.io.xml.DomDriver;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by pli on 14-06-07.
 * 支持图文消息
 */
public class ImgNewsServiceMessage implements ServiceMessage {

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getMsgtype() {
        return msgtype;
    }

    private String touser;
    private String msgtype = "news";
    private JSONObject news = new JSONObject();

    public JSONObject getNews() {
        return news;
    }

    public void setNews(JSONObject news) {
        this.news = news;
    }

    public ImgNewsServiceMessage(String touser, String content) {
        this.touser = touser;
        XStream myImageContent = new XStream(new DomDriver());
        myImageContent.processAnnotations(Articles.class);
        Articles articles = (Articles) myImageContent.fromXML(content);
        JSONArray jsonArray = new JSONArray();
        List<Item> items = articles.getItem();
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            JSONObject article = new JSONObject();
            article.put("title", item.getTitle());
            article.put("description", item.getDescription());
            article.put("url", item.getUrl());
            article.put("picurl", item.getPicUrl());
            jsonArray.add(article);
        }
        news.put("articles", jsonArray);
    }

    public static void main(String[] args) {
        ServiceMessage message = new ImgNewsServiceMessage("ada", "<Articles> <item> <Title><![CDATA[霞珍集团]]></Title>  <Description><![CDATA[安徽霞珍集团创办于1986年10月，是安徽第一家从事水禽养殖、羽绒制品加工出口的外向型企业，是中国羽绒行业的开拓者和领军型的集团化企业。]]></Description> <PicUrl><![CDATA[http://dzr.cyznj.com/upload/getImage.action?getfile=55739b910cf21dc8846338c8test.jpg]]></PicUrl> <Url><![CDATA[http://dzr.cyznj.com/pages/wechat/mainPage.jsp]]></Url> </item> </Articles>");
        String x = JSONObject.fromObject(message).toString();
        System.out.print(x);
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