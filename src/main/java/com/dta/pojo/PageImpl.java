package com.dta.pojo;

import com.github.pagehelper.Page;

import java.util.List;

public class PageImpl<T> extends Page<T> {
    private List<T> result;
    private int pageNum;
    private int pageSize;
    private long total;

    public PageImpl(List<T> result, int pageNum, int pageSize, long total) {
        this.result = result;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
    }

    @Override
    public List<T> getResult() {
        return result;
    }

    @Override
    public int getPageNum() {
        return pageNum;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public long getTotal() {
        return total;
    }
}