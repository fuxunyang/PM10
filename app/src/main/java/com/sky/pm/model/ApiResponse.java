/*
 * Copyright (C) 2015. Keegan小钢（http://keeganlee.me）
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sky.pm.model;


import com.sky.pm.api.JsonToResponse;
import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;


/**
 * Api响应结果的封装类.
 *
 * @author sky QQ:1136096189
 * @date 15/12/9 下午8:54
 */
@HttpResponse(parser = JsonToResponse.class)
public class ApiResponse<T> implements Serializable {
    private T row;           // 单个对象
    private T rows;       // 数组对象
    private int total;    // 总条数
    private Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public T getRow() {
        return row;
    }

    public void setRow(T row) {
        this.row = row;
    }

    public T getRows() {
        return rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
