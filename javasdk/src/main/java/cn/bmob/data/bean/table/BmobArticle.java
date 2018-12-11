package cn.bmob.data.bean.table;

public class BmobArticle extends BmobObject {


    public BmobArticle() {
        super("_Article");
    }

    /**
     * 生成的图文消息
     */
    private String url;


    /**
     * 图文消息类型
     */
    private String type;


    /**
     * 图文消息的标题
     */
    private String title;


    /**
     * m_id
     */
    private String m_id;


    /**
     * 图文消息注释
     */
    private String desc;


    /**
     * 封面
     */
    private String cover;


    /**
     * 图文消息内容
     */
    private String content;


    /**
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return
     */
    public String getType() {
        return type;
    }


    /**
     * @return
     */
    public String getTitle() {
        return title;
    }


    /**
     * @return
     */
    public String getM_id() {
        return m_id;
    }


    /**
     * @return
     */
    public String getDesc() {
        return desc;
    }


    /**
     * @return
     */
    public String getCover() {
        return cover;
    }


    /**
     * @return
     */
    public String getContent() {
        return content;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
