package com.example.eom_rfid.bean.response;

import java.util.List;

/**
 * Description:
 * Author:bwang
 * Date:2021/1/15 14:35
 */
public class TaskResponse {


    /**
     * total : 1
     * list : [{"id":"1364747606876041217","type":"0","name":"yangggtest","desc":"yangggtest","location":"江苏省苏州市姑苏区金阊街道创元婚庆文化创意产业园苏州婚庆创意产业园","start":"2021-02-27 00:00:00","end":"2021-03-25 00:00:00","status":1,"enableInHospital":0,"latitude":31.309551,"longitude":120.614706,"content":"{\"children\":[{\"children\":[],\"members\":[{\"userId\":\"1067246875800000001\"}],\"id\":\"a5bf6acd-fd5e-42a5-8304-44b495adfb57\",\"label\":\"111\",\"nodeType\":\"custom\"},{\"children\":[],\"members\":[{\"userId\":\"1349553561505640449\"}],\"id\":\"9571cf6a-e663-4e8f-8d4e-5787fc4f5f45\",\"label\":\"儿科\",\"nodeType\":\"office\",\"value\":\"2\"},{\"children\":[{\"children\":[],\"members\":[],\"id\":\"51ce01f6-66b0-4f95-84d8-91fac14453f5\",\"label\":\"心胸内科\",\"nodeType\":\"office\",\"value\":\"4\"},{\"children\":[],\"members\":[],\"id\":\"39e74da7-56be-475c-856d-4165dfb291ea\",\"label\":\"肾内科\",\"nodeType\":\"office\",\"value\":\"5\"}],\"members\":[],\"id\":\"e81dc19f-3e18-4bca-87cf-de7092e26876\",\"label\":\"77\",\"nodeType\":\"custom\"}],\"members\":[{\"userId\":\"1335770490621956097\"}],\"id\":0,\"label\":\"队长\",\"nodeType\":\"root\"}","daysBetween":null,"totalPatientAmount":"1","memberCount":null,"layoutData":null,"trainContent":null,"taskDate":null,"leaderIds":"1335770490621956097","enableInformAll":0,"informTime":"0","isNotified":1,"idStr":null}]
     */

    private int total;
    /**
     * id : 1364747606876041217
     * type : 0
     * name : yangggtest
     * desc : yangggtest
     * location : 江苏省苏州市姑苏区金阊街道创元婚庆文化创意产业园苏州婚庆创意产业园
     * start : 2021-02-27 00:00:00
     * end : 2021-03-25 00:00:00
     * status : 1
     * enableInHospital : 0
     * latitude : 31.309551
     * longitude : 120.614706
     * content : {"children":[{"children":[],"members":[{"userId":"1067246875800000001"}],"id":"a5bf6acd-fd5e-42a5-8304-44b495adfb57","label":"111","nodeType":"custom"},{"children":[],"members":[{"userId":"1349553561505640449"}],"id":"9571cf6a-e663-4e8f-8d4e-5787fc4f5f45","label":"儿科","nodeType":"office","value":"2"},{"children":[{"children":[],"members":[],"id":"51ce01f6-66b0-4f95-84d8-91fac14453f5","label":"心胸内科","nodeType":"office","value":"4"},{"children":[],"members":[],"id":"39e74da7-56be-475c-856d-4165dfb291ea","label":"肾内科","nodeType":"office","value":"5"}],"members":[],"id":"e81dc19f-3e18-4bca-87cf-de7092e26876","label":"77","nodeType":"custom"}],"members":[{"userId":"1335770490621956097"}],"id":0,"label":"队长","nodeType":"root"}
     * daysBetween : null
     * totalPatientAmount : 1
     * memberCount : null
     * layoutData : null
     * trainContent : null
     * taskDate : null
     * leaderIds : 1335770490621956097
     * enableInformAll : 0
     * informTime : 0
     * isNotified : 1
     * idStr : null
     */

    private List<ListBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String id;
        private String type;
        private String name;
        private String desc;
        private String location;
        private String start;
        private String end;
        private int status;
        private int enableInHospital;
        private double latitude;
        private double longitude;
        private String content;
        private Object daysBetween;
        private String totalPatientAmount;
        private Object memberCount;
        private Object layoutData;
        private Object trainContent;
        private Object taskDate;
        private String leaderIds;
        private int enableInformAll;
        private String informTime;
        private int isNotified;
        private Object idStr;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getEnableInHospital() {
            return enableInHospital;
        }

        public void setEnableInHospital(int enableInHospital) {
            this.enableInHospital = enableInHospital;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getDaysBetween() {
            return daysBetween;
        }

        public void setDaysBetween(Object daysBetween) {
            this.daysBetween = daysBetween;
        }

        public String getTotalPatientAmount() {
            return totalPatientAmount;
        }

        public void setTotalPatientAmount(String totalPatientAmount) {
            this.totalPatientAmount = totalPatientAmount;
        }

        public Object getMemberCount() {
            return memberCount;
        }

        public void setMemberCount(Object memberCount) {
            this.memberCount = memberCount;
        }

        public Object getLayoutData() {
            return layoutData;
        }

        public void setLayoutData(Object layoutData) {
            this.layoutData = layoutData;
        }

        public Object getTrainContent() {
            return trainContent;
        }

        public void setTrainContent(Object trainContent) {
            this.trainContent = trainContent;
        }

        public Object getTaskDate() {
            return taskDate;
        }

        public void setTaskDate(Object taskDate) {
            this.taskDate = taskDate;
        }

        public String getLeaderIds() {
            return leaderIds;
        }

        public void setLeaderIds(String leaderIds) {
            this.leaderIds = leaderIds;
        }

        public int getEnableInformAll() {
            return enableInformAll;
        }

        public void setEnableInformAll(int enableInformAll) {
            this.enableInformAll = enableInformAll;
        }

        public String getInformTime() {
            return informTime;
        }

        public void setInformTime(String informTime) {
            this.informTime = informTime;
        }

        public int getIsNotified() {
            return isNotified;
        }

        public void setIsNotified(int isNotified) {
            this.isNotified = isNotified;
        }

        public Object getIdStr() {
            return idStr;
        }

        public void setIdStr(Object idStr) {
            this.idStr = idStr;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
