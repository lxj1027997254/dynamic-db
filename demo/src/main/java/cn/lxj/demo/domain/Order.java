package cn.lxj.demo.domain;

/**
 * Created by LXJ on 2021-03-09.
 */
public class Order {
   private Integer userId;
   private String name;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
