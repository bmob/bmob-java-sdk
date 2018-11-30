package cn.bmob.data.bean;

public class BmobQuery<T> {


    /**
     * 表名
     */
    private String _c_;

    /**
     *
     */
    public BmobQuery() {
        this(null);
    }

    /**
     * @param _c_
     */
    public BmobQuery(String _c_) {
        if (_c_ != null) {
            this._c_ = _c_;
        }
    }










}

