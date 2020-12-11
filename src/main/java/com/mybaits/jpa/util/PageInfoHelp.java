package com.mybaits.jpa.util;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * Created by admin on 2017-03-27 0027.
 */
public class PageInfoHelp implements Serializable {
    public static final int DEFAULT_PAGE_SIZE = 15;
    public static final int MAX_PAGE_SIZE = 200;
    public static final int FIRST_PAGE = 1;

    @TableField(exist = false)
    private Integer pageNum;
    @TableField(exist = false)
    private Integer pageSize;

    public static Integer setDefaultNum(Integer pageNum) {
        if (pageNum== null) {
            return FIRST_PAGE;
        }
        return pageNum;
    }
    public static Integer setDefaultSize(Integer pageSize) {
        if (pageSize== null) {
            return DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    /**
     * 获取分页起始下标
     * @param pageNum
     * @param pageSize
     * @return
     */
    public static int getStartIndex(int pageNum, int pageSize) {
        return (pageNum -1) * pageSize;
    }

    /**
     * 获取分页结束下标
     * @param pageNum
     * @param pageSize
     * @return
     */
    public static int getEndIndex(int pageNum, int pageSize) {
        return getStartIndex(pageNum, pageSize) + pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        if (pageNum != null && pageSize != null) {
            return (pageNum - 1) * getPageSize();
        }
        return null;
    }

}
