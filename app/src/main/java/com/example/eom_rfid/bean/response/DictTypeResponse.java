package com.example.eom_rfid.bean.response;

import java.util.List;

public class DictTypeResponse {


    /**
     * dictLabel : 1号仓库
     * dictValue : 1
     * dictTypeId : 1357581090230927362
     */

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String dictLabel;
        private String dictValue;
        private String dictTypeId;

        public String getDictLabel() {
            return dictLabel;
        }

        public void setDictLabel(String dictLabel) {
            this.dictLabel = dictLabel;
        }

        public String getDictValue() {
            return dictValue;
        }

        public void setDictValue(String dictValue) {
            this.dictValue = dictValue;
        }

        public String getDictTypeId() {
            return dictTypeId;
        }

        public void setDictTypeId(String dictTypeId) {
            this.dictTypeId = dictTypeId;
        }

        @Override
        public String toString() {
            return dictLabel;
        }
    }
}
