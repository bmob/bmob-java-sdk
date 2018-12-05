package cn.bmob.data.bean.op;

import cn.bmob.data.Bmob;
import cn.bmob.data.bean.table.BmobObject;
import cn.bmob.data.bean.table.BmobUser;
import cn.bmob.data.bean.type.BmobDate;
import cn.bmob.data.bean.type.BmobGeoPoint;
import cn.bmob.data.bean.type.BmobPointer;
import cn.bmob.data.callback.object.CountListener;
import cn.bmob.data.callback.object.GetListener;
import cn.bmob.data.callback.object.GetsListener;
import cn.bmob.data.utils.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.oracle.javafx.jmx.json.JSONException;
import retrofit2.Call;

import java.util.*;
import java.util.regex.Pattern;

import static cn.bmob.data.utils.Utils.request;

public class BmobQuery {


    /**
     * 查询条件
     */
    private JsonObject where = new JsonObject();
    /**
     * 查询指定列
     */
    private String keys;
    /**
     * 查询限制条数
     */
    private Integer limit;
    /**
     * 查询跳过条数
     */
    private Integer skip;
    /**
     * 查询结果排序规则
     */
    private String order;
    /**
     * 查询结果包含信息
     */
    private String include;
    /**
     *
     */
    private Integer count;
    /**
     *
     */
    private Boolean groupcount;
    /**
     *
     */
    private String groupby;
    /**
     *
     */
    private String sum;
    /**
     *
     */
    private String average;
    /**
     *
     */
    private String max;
    /**
     *
     */
    private String min;
    /**
     *
     */
    private String having;


    private Integer getCount() {
        return count;
    }

    private void setCount(Integer count) {
        this.count = count;
    }

    private Boolean getGroupcount() {
        return groupcount;
    }

    public void setGroupcount(Boolean groupcount) {
        this.groupcount = groupcount;
    }

    private String getGroupby() {
        return groupby;
    }

    public void setGroupby(String groupby) {
        this.groupby = groupby;
    }

    private String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    private String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    private String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    private String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    private String getHaving() {
        return having;
    }

    public void setHaving(String having) {
        this.having = having;
    }


    private JsonObject getWhere() {
        return where;
    }

    public void setWhere(JsonObject where) {
        this.where = where;
    }


    private String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    private Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    private Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    private String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    private String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    /**
     * 添加查询条件：例（score < ?）
     *
     * @param key   字段名称
     * @param value 条件值
     * @return 返回当前BmobQuery对象
     */
    public BmobQuery addWhereLessThan(String key, Object value) {
        //less than
        addCondition(key, "$lt", value);
        return this;
    }

    /**
     * 添加查询条件：例（score <= ?）
     *
     * @param key   字段名称
     * @param value 条件值
     * @return 返回当前BmobQuery对象
     */
    public BmobQuery addWhereLessThanOrEqualTo(String key, Object value) {
        //less than equal
        addCondition(key, "$lte", value);
        return this;
    }

    /**
     * 添加查询条件：例（score > ?）
     *
     * @param key   字段名称
     * @param value 条件值
     * @return 返回当前BmobQuery对象
     */
    public BmobQuery addWhereGreaterThan(String key, Object value) {
        //greater than
        addCondition(key, "$gt", value);
        return this;
    }

    /**
     * 添加查询条件：例（score >= ?）
     *
     * @param key   字段名称
     * @param value 条件值
     * @return 返回当前BmobQuery对象
     */
    public BmobQuery addWhereGreaterThanOrEqualTo(String key, Object value) {
        //greater than equal
        addCondition(key, "$gte", value);
        return this;
    }

    /**
     * 添加查询条件：例 （name == ?）
     *
     * @param key   字段名称
     * @param value 条件值
     * @return 返回当前BmobQuery对象
     */
    public BmobQuery addWhereEqualTo(String key, Object value) {
        if (value instanceof BmobPointer) {
            //where查询条件支持关联关系查询
            try {
                JsonObject obj = new JsonObject();
                Utils.add2Object(obj, "object", value);
                addCondition(key, null, obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            addCondition(key, null, value);
        }
        return this;
    }

    /**
     * 添加查询条件：例（name != ?）
     *
     * @param key   字段名称
     * @param value 条件值
     * @return 返回当前BmobQuery对象
     */
    public BmobQuery addWhereNotEqualTo(String key, Object value) {
        //not equal
        addCondition(key, "$ne", value);
        return this;
    }

    /**
     * 查询数据列为数组Array类型的值中包含有x,x,x的对象
     *
     * @param key    字段名称
     * @param values 条件值
     * @return 返回当前BmobQuery对象
     */
    public BmobQuery addWhereContainsAll(String key, Collection<?> values) {
        JsonArray array = new JsonArray();
        for (Object object : values) {
            Utils.add2Array(array, object);
        }
        addCondition(key, "$all", array);
        return this;
    }

    /**
     * 查询某字段的值包含在XX范围内的数据
     *
     * @param key    字段名称
     * @param values 条件值
     * @return 返回当前BmobQuery对象
     */
    public BmobQuery addWhereContainedIn(String key, Collection<? extends Object> values) {
        JsonArray array = new JsonArray();
        for (Iterator<? extends Object> localIterator = values.iterator(); localIterator
                .hasNext(); ) {
            Object val = localIterator.next();
            Utils.add2Array(array, val);
        }
        addCondition(key, "$in", array);
        return this;
    }

    /**
     * 查询某字段的值不包含在XX范围内的数据
     *
     * @param key    字段名称
     * @param values 条件值
     * @return 返回当前BmobQuery对象
     */
    public BmobQuery addWhereNotContainedIn(String key, Collection<? extends Object> values) {
        JsonArray array = new JsonArray();
        for (Iterator<? extends Object> localIterator = values.iterator(); localIterator
                .hasNext(); ) {
            Object val = localIterator.next();
            Utils.add2Array(array, val);
        }
        addCondition(key, "$nin", array);
        return this;
    }

    /**
     * regex为正则表达式字符串，key为字段名。查询匹配该正则表达式规则的字段
     *
     * @param key   字段名
     * @param regex 正则表达式规则
     * @return 返回当前BmobQuery对象
     */
    public BmobQuery addWhereMatches(String key, String regex) {
        addCondition(key, "$regex", regex);
        return this;
    }

    /**
     * 查询包含XX字符串的值
     *
     * @param key   列名
     * @param value 包含的字符串
     * @return 返回当前BmobQuery对象
     */
    public BmobQuery addWhereContains(String key, String value) {
        String regex = Pattern.quote(value);
        addWhereMatches(key, regex);
        return this;
    }

    /**
     * 查询以XX字符串开头的值
     *
     * @param key    列名
     * @param prefix 开始字符串
     * @return 返回当前BmobQuery对象
     */
    public BmobQuery addWhereStartsWith(String key, String prefix) {
        String regex = "^" + Pattern.quote(prefix);
        addWhereMatches(key, regex);
        return this;
    }

    /**
     * 查询以XX字符串结尾的值
     *
     * @param key    列名
     * @param suffix 结束字符串
     * @return 返回当前BmobQuery对象
     */
    public BmobQuery addWhereEndsWith(String key, String suffix) {
        String regex = Pattern.quote(suffix) + "$";
        addWhereMatches(key, regex);
        return this;
    }

    /**
     * 查询最接近用户地点的数据 注意：该方法最好结合setLimit一起使用
     *
     * @param key   字段名称
     * @param point 原点
     * @return 返回当前BmobQuery对象
     */
    public BmobQuery addWhereNear(String key, BmobGeoPoint point) {
        addCondition(key, "$nearSphere", point);
        return this;
    }

    /**
     * 查询多少英里之内的信息
     *
     * @param key         字段名称
     * @param point       原点
     * @param maxDistance 范围(单位：英里)
     * @return 返回当前BmobQuery对象
     */
    public BmobQuery addWhereWithinMiles(String key, BmobGeoPoint point, double maxDistance) {
        addGeoPoint(key, "$maxDistanceInMiles", point, maxDistance);
        return this;
    }

    /**
     * 查询多少公里之内的信息
     *
     * @param key         字段名称
     * @param point       原点
     * @param maxDistance 范围(单位：公里)
     * @return 返回当前BmobQuery对象
     */
    public BmobQuery addWhereWithinKilometers(String key, BmobGeoPoint point, double maxDistance) {
        addGeoPoint(key, "$maxDistanceInKilometers", point, maxDistance);
        return this;
    }

    /**
     * 查询一个圆形范围内的信息
     *
     * @param key         字段名称
     * @param point       原点
     * @param maxDistance 范围(单位：公里)
     * @return 返回当前BmobQuery对象
     */
    public BmobQuery addWhereWithinRadians(String key, BmobGeoPoint point, double maxDistance) {
        addGeoPoint(key, "$maxDistanceInKilometers", point, maxDistance);
        return this;
    }

    private void addGeoPoint(String key, String condition, BmobGeoPoint point, double maxDistance) {
        addCondition(key, "$nearSphere", point);
        addCondition(key, condition, maxDistance);
    }


    /**
     * 查询一个矩形范围内的信息
     *
     * @param key       字段名称
     * @param southwest 矩形左下角的坐标点
     * @param northeast 矩形右上角的坐标点
     * @return 返回当前BmobQuery对象
     */
    public BmobQuery addWhereWithinGeoBox(String key, BmobGeoPoint southwest, BmobGeoPoint northeast) {
        ArrayList<BmobGeoPoint> array = new ArrayList<BmobGeoPoint>();
        array.add(southwest);
        array.add(northeast);
        addCondition(key, "$within", array);
        return this;
    }

    /**
     * 查询存在指定字段的数据
     *
     * @param key 字段名称
     * @return 返回当前BmobQuery对象
     */
    public BmobQuery addWhereExists(String key) {
        addCondition(key, "$exists", true);
        return this;
    }

    /**
     * 查询不存在指定字段的数据
     *
     * @param key 字段名称
     * @return 返回当前BmobQuery对象
     */
    public BmobQuery addWhereDoesNotExists(String key) {
        addCondition(key, "$exists", false);
        return this;
    }

    /**
     * 例：查询某帖子的所有评论
     * 加关联标记
     *
     * @param key     注意这里的key是帖子表中的Relation类型字段'comment'
     * @param pointer 指向帖子表的BmobPointer对象
     * @return 返回当前BmobQuery对象
     */
    public BmobQuery addWhereRelatedTo(String key, BmobPointer pointer) {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("key", key);
            Utils.add2Object(obj, "object", pointer);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        addCondition("$relatedTo", null, obj);
        return this;
    }

    /**
     * 查询的对象中的某个列符合另一个查询
     *
     * @param key        列名
     * @param className  该列对应的数据表名
     * @param innerQuery 针对该列的另一个查询
     * @return 返回当前BmobQuery对象
     */
    public BmobQuery addWhereMatchesQuery(String key, String className, BmobQuery innerQuery) {
        JsonObject obj = new JsonObject();
        obj.add("where", innerQuery.getWhere());
        obj.addProperty("className", className);
        addCondition(key, "$inQuery", obj);
        return this;
    }

    /**
     * 查询的对象中的某个列不符合另一个查询
     *
     * @param key        列名
     * @param className  该列对应的数据表名
     * @param innerQuery 针对该列的另一个查询
     * @return 返回当前BmobQuery对象
     */
    public BmobQuery addWhereDoesNotMatchQuery(String key, String className, BmobQuery innerQuery) {
        JsonObject obj = new JsonObject();
        obj.add("where", innerQuery.getWhere());
        obj.addProperty("className", className);
        addCondition(key, "$notInQuery", obj);
        return this;
    }

    /**
     * 指定查询某列的数据，指定多列时用（,）号分隔
     *
     * @param keys 指定的数据列
     * @return 返回当前BmobQuery对象
     */
    public BmobQuery addQueryKeys(String keys) {
        this.keys = keys;
        return this;
    }

    /**
     * 限制查询的结果数量
     *
     * @param newLimit 结果数量
     */
    public BmobQuery setLimit(int newLimit) {
        this.limit = newLimit;
        return this;
    }

    /**
     * 跳过第多少条数据，分页时用到，获取下一页数据
     *
     * @param newSkip
     */
    public BmobQuery setSkip(int newSkip) {
        this.skip = newSkip;
        return this;
    }


    /**
     * 排序(例:"score"或"-score")
     *
     * @param order 排序字段
     * @return 返回当前BmobQuery对象
     */
    public BmobQuery order(String order) {
        this.order = order;
        return this;
    }

    /**
     * 关联查询，该方法用在字段为Pointer类型时（例："post"）
     *
     * @param fieldName 列名
     */
    public BmobQuery include(String fieldName) {
        this.include = fieldName;
        return this;
    }

    /**
     * 复合查询条件or
     *
     * @param where
     * @return 返回当前BmobQuery对象
     */
    public BmobQuery or(List<BmobQuery> where) {
        JsonArray ja = new JsonArray();
        for (BmobQuery bmobQuery : where) {
            ja.add(bmobQuery.getWhere());
        }
        addCondition("$or", null, ja);
        return this;
    }

    /**
     * 复合查询条件and
     *
     * @param where
     * @return 返回当前BmobQuery对象
     */
    public BmobQuery and(List<BmobQuery> where) {
        JsonArray ja = new JsonArray();
        for (BmobQuery bmobQuery : where) {
            ja.add(bmobQuery.getWhere());
        }
        addCondition("$and", null, ja);
        return this;
    }


    public BmobQuery() {
    }


    /**
     * 组装where查询条件
     *
     * @param key
     * @param condition
     * @param value
     * @return void
     * @throws
     * @method addCondition
     */
    public void addCondition(String key, String condition, Object value) {
        if (Utils.isStringEmpty(condition)) {
            // equal to/or/and/related to
            if (value instanceof BmobUser) {
                BmobUser bo = (BmobUser) value;
                JsonObject obj = new JsonObject();
                obj.addProperty("__type", "Pointer");
                obj.addProperty("objectId", bo.getObjectId());
                obj.addProperty("className", "_User");
                this.where.add(key, obj);
            } else if (value instanceof BmobObject) {
                BmobObject bo = (BmobObject) value;
                JsonObject obj = new JsonObject();
                obj.addProperty("__type", "Pointer");
                obj.addProperty("objectId", bo.getObjectId());
                obj.addProperty("className", bo.getClass().getSimpleName());
                this.where.add(key, obj);
            } else {
                Utils.add2Object(where, key, value);
            }
        } else {// 这里包含大于、小于、大于等于、小于等于等操作
            if (value instanceof BmobGeoPoint) {
                Utils.add2Object((JsonObject) value, "object", value);
            } else if (value instanceof BmobUser) {
                BmobUser bo = (BmobUser) value;
                JsonObject obj = new JsonObject();
                obj.addProperty("__type", "Pointer");
                obj.addProperty("objectId", bo.getObjectId());
                obj.addProperty("className", "_User");
                value = obj;
            } else if (value instanceof BmobObject) {
                BmobObject bo = (BmobObject) value;
                JsonObject obj = new JsonObject();
                obj.addProperty("__type", "Pointer");
                obj.addProperty("objectId", bo.getObjectId());
                obj.addProperty("className", bo.getClass().getSimpleName());
                value = obj;
            } else if (value instanceof BmobDate) {
                Utils.add2Object((JsonObject) value, "object", value);
            } else if (value instanceof ArrayList) {
                // 地理位置查询
                @SuppressWarnings("unchecked")
                ArrayList<BmobGeoPoint> list = (ArrayList<BmobGeoPoint>) value;
                JsonArray arr = new JsonArray();
                JsonObject geoPoint = new JsonObject();
                geoPoint.addProperty("__type", "GeoPoint");
                geoPoint.addProperty("longitude", list.get(0).getLongitude());
                geoPoint.addProperty("latitude", list.get(0).getLatitude());

                JsonObject geoPoint1 = new JsonObject();
                geoPoint1.addProperty("__type", "GeoPoint");
                geoPoint1.addProperty("longitude", list.get(1).getLongitude());
                geoPoint1.addProperty("latitude", list.get(1).getLatitude());
                arr.add(geoPoint);
                arr.add(geoPoint1);
                JsonObject obj = new JsonObject();
                obj.add("$box", arr);
                value = obj;
            }
            JsonObject where = null;
            if (this.where.has(key)) {
                // 复合查询的话，从中先取出内部查询的where
                Object existingValue = this.where.get(key);
                if (existingValue instanceof JsonObject) {
                    where = (JsonObject) existingValue;
                }
            }
            if (where == null) {
                where = new JsonObject();
            }
            Utils.add2Object(where, condition, value);
            this.where.add(key, where);
        }
    }


    /**
     * 获取一条数据
     *
     * @param objectId
     * @param getListener
     * @param <T>
     */
    public <T> void getObject(String objectId, final GetListener<T> getListener) {
        Map<String, Object> map = buildMap();
        Call<JsonObject> call = Bmob.getInstance().api().get(Utils.getClassFromBmobCallback(getListener).getSimpleName(), objectId, map);
        request(call, getListener);
    }

    /**
     * 获取多条数据
     *
     * @param getsListener
     * @param <T>
     */
    public <T> void getObjects(GetsListener<T> getsListener) {
        Map<String, Object> map = buildMap();

        Call<JsonObject> call = Bmob.getInstance().api().getObjects(Utils.getClassFromBmobCallback(getsListener).getSimpleName(), map);
        request(call, getsListener);
    }


    /**
     * @param countListener
     * @param <T>
     */
    public <T> void getCount(CountListener<T> countListener) {
        setCount(1);
        setLimit(0);
        Map<String, Object> map = buildMap();
        Call<JsonObject> call = Bmob.getInstance().api().getObjects(Utils.getClassFromBmobCallback(countListener).getSimpleName(), map);
        request(call, countListener);
    }


    /**
     * 构建查询条件
     *
     * @return
     */
    private Map<String, Object> buildMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        //查询条件
        if (getWhere().size() > 0) {
            map.put("where", getWhere());
        }
        //对结果进行处理
        if (getLimit() != null) {
            map.put("limit", getLimit());
        }
        if (getSkip() != null) {
            map.put("skip", getSkip());
        }
        if (!Utils.isStringEmpty(getOrder())) {
            map.put("order", getOrder());
        }
        if (!Utils.isStringEmpty(getInclude())) {
            map.put("include", getInclude());
        }
        if (getCount() != null) {
            map.put("count", getCount());
        }
        if (!Utils.isStringEmpty(getKeys())) {
            map.put("keys", getKeys());
        }
        if (!Utils.isStringEmpty(getGroupby())) {
            map.put("groupby", getGroupby());
        }
        if (getGroupcount() != null) {
            map.put("groupcount", getGroupcount());
        }
        if (!Utils.isStringEmpty(getSum())) {
            map.put("sum", getSum());
        }
        if (!Utils.isStringEmpty(getAverage())) {
            map.put("average", getAverage());
        }
        if (!Utils.isStringEmpty(getMax())) {
            map.put("max", getMax());
        }
        if (!Utils.isStringEmpty(getMin())) {
            map.put("min", getMin());
        }
        if (!Utils.isStringEmpty(getHaving())) {
            map.put("having", getHaving());
        }
        return map;
    }


}
