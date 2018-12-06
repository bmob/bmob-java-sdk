package cn.bmob.data.bean.type;

import cn.bmob.data.bean.table.BmobRole;
import cn.bmob.data.bean.table.BmobUser;
import cn.bmob.data.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class BmobACL  {


    private Map<String, Object> acl = new HashMap<>();

    private static final String READ = "read";
    private static final String WRITE = "write";

    public BmobACL(){}

    public Map<String, Object> getAcl() {
        return acl;
    }

    public void setAcl(Map<String, Object> acl) {
        this.acl = acl;
    }

    private void setAccess(String accessType, String userId, boolean allowed){
        if(acl.containsKey(userId)){
            Map<String, Object> m = (Map<String, Object>) acl.get(userId);
            m.put(accessType, allowed);
            acl.put(userId, m);
        }else {
            Map<String, Object> access = new HashMap<String, Object>();
            access.put(accessType, allowed);
            acl.put(userId, access);
        }
    }

    /**
     * 设置指定的用户是否允许读取该对象
     * @param userId 用户objectId
     * @param allowed 是否可读
     */
    public void setReadAccess(String userId, boolean allowed){
        if(Utils.isStringEmpty(userId)){
            throw new IllegalArgumentException(
                    "cannot setReadAccess for null userId");
        }else if(allowed){
            setAccess(READ, userId, allowed);
        }

    }

    /**
     * 设置指定的用户是否允许读取该对象
     * @param user 用户
     * @param allowed 是否可读
     */
    public void setReadAccess(BmobUser user, boolean allowed){
        if(user == null){
            throw new IllegalArgumentException(
                    "cannot setReadAccess for null user");
        }else if(Utils.isStringEmpty(user.getObjectId())){
            throw new IllegalArgumentException(
                    "cannot setReadAccess for null userId");
        }else  if(allowed){
            setAccess(READ, user.getObjectId(), allowed);
        }
    }

    /**
     * 设置指定的用户是否允许修改该对象
     * @param userId 用户objectId
     * @param allowed 是否可写
     */
    public void setWriteAccess(String userId, boolean allowed){
        if(Utils.isStringEmpty(userId)){
            throw new IllegalArgumentException(
                    "cannot setReadAccess for null userId");
        }else if(allowed){
            setAccess(WRITE, userId, allowed);
        }
    }

    /**
     * 设置指定的用户是否允许修改该对象
     * @param user 用户
     * @param allowed 是否可写
     */
    public void setWriteAccess(BmobUser user, boolean allowed){
        if(user == null){
            throw new IllegalArgumentException(
                    "cannot setReadAccess for null user");
        }else if(Utils.isStringEmpty(user.getObjectId())){
            throw new IllegalArgumentException(
                    "cannot setReadAccess for null userId");
        }else if(allowed){
            setAccess(WRITE, user.getObjectId(), allowed);
        }
    }

    /**
     * 设置指定角色是否允许读取该对象
     * @param roleName 角色名称
     * @param allowed 是否可读
     */
    public void setRoleReadAccess(String roleName, boolean allowed){
        if(Utils.isStringEmpty(roleName)){
            throw new IllegalArgumentException(
                    "cannot setReadAccess for null roleName");
        }else if(allowed){
            setAccess(READ, "role:"+roleName, allowed);
        }
    }

    /**
     * 设置指定角色是否允许读取该对象
     * @param role 角色
     * @param allowed 是否可读
     */
    public void setRoleReadAccess(BmobRole role, boolean allowed){
        if(role == null){
            throw new IllegalArgumentException(
                    "cannot setReadAccess for null role");
        }else if(Utils.isStringEmpty(role.getName())){
            throw new IllegalArgumentException(
                    "cannot setReadAccess for null roleName");
        }else if(allowed){
            setAccess(READ, "role:"+role.getName(), allowed);
        }
    }

    /**
     * 设置指定角色是否允许修改该对象
     * @param roleName 角色名称
     * @param allowed 是否可写
     */
    public void setRoleWriteAccess(String roleName, boolean allowed){
        if(Utils.isStringEmpty(roleName)){
            throw new IllegalArgumentException(
                    "cannot setReadAccess for null roleName");
        }else if(allowed){
            setAccess(WRITE, "role:"+roleName, allowed);
        }
    }

    /**
     * 设置指定角色是否允许修改该对象
     * @param role 角色
     * @param allowed 是否可写
     */
    public void setRoleWriteAccess(BmobRole role, boolean allowed){
        if(role == null){
            throw new IllegalArgumentException(
                    "cannot setReadAccess for null role");
        }else if(Utils.isStringEmpty(role.getName())){
            throw new IllegalArgumentException(
                    "cannot setReadAccess for null roleName");
        }else if(allowed){
            setAccess(WRITE, "role:"+role.getName(), allowed);
        }
    }

    /**
     * 设置所有人是否允许读取该对象
     * @param allowed 是否可读
     */
    public void setPublicReadAccess(boolean allowed){
        if(allowed){
            setReadAccess("*", allowed);
        }
    }

    /**
     * 设置所有人是否允许修改该对象
     * @param allowed 是否可写
     */
    public void setPublicWriteAccess(boolean allowed) {
        if(allowed){
            setWriteAccess("*", allowed);
        }
    }
}
