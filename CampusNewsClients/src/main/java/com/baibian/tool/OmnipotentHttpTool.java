package com.baibian.tool;

import android.os.IInterface;

import com.baibian.bean.jsonbean.ConcernedDebateTopic;
import com.baibian.bean.jsonbean.ConcernedThesis;
import com.baibian.bean.jsonbean.ConcernedUser;
import com.baibian.bean.jsonbean.ForumsDetailsPage;
import com.baibian.bean.jsonbean.ForumsInitialPage;
import com.baibian.bean.jsonbean.InformationFans;
import com.baibian.bean.jsonbean.PerfectPage;
import com.baibian.bean.jsonbean.UserInformationDetail;
import com.baibian.information.Objection;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

/**
 * 方法能够被客户端程序自由调用，实现对后台数据的修改获取等功能
 * TODO 目前之考虑了字符串类型数据
 * Created by 邹特强 on 2017/8/15.
 *
 * @author 邹特强
 */

public class OmnipotentHttpTool {
    //TODO 具体值待定
    private final static String BASE_URL = "http://112.74.107.202";//基础的固定的url
    /**
     * 论阁初始页GET方法类型常量
     */
    private final static int FORUMS_INITIAL_PAGE_GET = 0;
    /**
     * 对应url的path
      */
    private final static String FORUMS_INITIAL_PAGE_GET_PATH="";
    /**
     * 论阁初始页POST——ADD添加数据方法类型常量
     */
    private final static int FORUMS_INITIAL_PAGE_POST_ADD=1;
    /**
     * 对应url的path
     */
    private final static String FORUMS_INITIAL_PAGE_POST_ADD_PATH="";
    /**
     * 论阁初始页POST-DELETE删除数据方法类型常量
     */
    private final static int FORUMS_INITIAL_PAGE_DELETE=2;
    /**
     * 对应url的path
     */
    private final static String FORUMS_INITIAL_PAGE_DELETE_PATH="";
    /**
     * 论阁初始页POST-CHANGE给编数据方法类型常量
     */
    private final static int FORUMS_INITIAL_PAGE_CHANGE=3;
    /**
     * 对应url的path
     */
    private final static String FORUMS_INITIAL_PAGE_CHANGE_PATH="";
    /**
     * 论点以及辩题详情GET方法类型常量
     */
    private final static int FORUMS_DETAILS_PAGE_GET = 4;
    /**
     * 对应url的path
     */
    private final static String FORUMS_DETAILS_PAGE_GET_PATH="";
    /**
     * 论点及辩题详情POST-ADD方法类型常量
     */
    private final static int FORUMS_DETAILS_PAGE_ADD=5;
    /**
     * 对应url的path
     */
    private final static String FORUMS_DETAILS_PAGE_ADD_PATH="";
    /**
     * 论点及辩题详情POST-DELETE方法类型常量
     */
    private final static int FORUMS_DETAILS_PAGE_DElETE=6;
    /**
     * 对应url的path
     */
    private final static String FORUMS_DETAILS_PAGE_DElETE_PATH="";
    /**
     * 论点及辩题详情POST-CHNAGE方法类型常量
     */
    private final static int FORUMS_DETAILS_PAGE_CHANGE=7;
    /**
     * 对应url的path
     */
    private final static String FORUMS_DETAILS_PAGE_CHANGE_PATH="";
    /**
     * 个人中心个人信息GET方法类型常量
     */
    private final static int USER_CENTER_USERINFORMATION_GET=8;
    /**
     * 对应url的path
     */
    private final static String USER_CENTER_USERINFORMATION_GET_PATH="";
    /**
     * 个人中心粉丝信息GET方法类型常量
     */
    private final static int USER_CENTER_FANSINFORMATION_GET=9;
    /**
     * 对应url的path
     */
    private final static String USER_CENTER_FANSINFORMATION_GET_PATH="";
    /**
     * 个人中心用户关注辩题GET方法类型常量
      */
    private final static int USER_CENTER_CONCERNED_DEBATE_TOPIC_GET=10;
    /**
     * 对应url的path
     */
    private final static String USER_CENTER_CONCERNED_DEBATE_TOPIC_GET_PATH="";
    /**
     * 个人中心用户关注论点GET方法类型常量
     */
    private final static int USER_CENTER_CONCERNED_THESIS_GET=11;
    /**
     * 对应url的path
     */
    private final static String USER_CENTER_CONCERNED_THESIS_GET_PATH="";
    /**
     * 个人中心用户关注的人GET方法类型常量
     */
    private final static int USER_CENTER_CONCERNED_USER_GET=12;
    /**
     *对应url的path
     */
    private final static String USER_CENTER_CONCERNED_USER_GET_PATH="";
    /**
     * 个人中心POST-ADD方法类型常量
     */
    private final static int USER_CENTER_USERINFORMATION_POST_ADD=13;
    /**
     * 对应url的path
     */
    private final static String USER_CENTER_USERINFORMATION_POST_ADD_PATH="";
    /**
     * 个人中心粉丝信息POST-CHANGE方法类型常量
     */
    private final static int USER_CENTER_FANSINFORMATION_POST_CHANGE=14;
    /**
     * 对应url的path
     */
    private final static String USER_CENTER_FANSINFORMATION_POST_CHANGE_PATH="";
    /**
     * 个人中心用户关注的人POST-CHANGE方法类型常量
     */
    private final static int USER_CENTER_CONCERNEDUSER_POST_CHANGE=15;
    /**
     * 对应url的path
     */
    private final static String USER_CENTER_CONCERNEDUSER_POST_CHANGE_PATH="";
    /**
     * 发起完善界面提交完善版本时：完善理由+完善后的论点+对应形式的原版本，同时完善理由加入异议列表POST-ADD方法类型常量
     */
    private final static int PERFECTPAGE_POST_ADD=16;
    /**
     * 对应的url
     */
    private final static String PERFECTPAGE_POST_ADD_PATH="";
    /**
     * 发起完善界面POST-CHANGE方法类型常量
     */
    private final static int PERFECTPAGE_POST_CHANGE=17;
    /**
     * 对应的url
     */
    private final static String PERFECTPAGE_POST_CHANGE_PATH="";
    /**
     * 发起完善界面POST-DELETE方法类型常量
     */
    private final static int PERFECTPAGE_POST_DELETE=18;
    /**
     * 对应的url
     */
    private final static String PERFECTPAGE_POST_DELETE_PATH="";
    /**
     * 发起完善界面获取完善版本和原始版本GET方法类型常量
     */
    private final static int PERFECTPAGE_GET_1=19;
    /**
     * 对应的url
     */
    private final static String PERFECTPAGE_GET_1_PATH="";
    /**
     * 发起完善界面获取已有异议列表GET方法类型常量
     */
    private final static int PERFECTPAGE_GET_2=20;
    /**
     * 对应的url
     */
    private final static String PERFECTPAGE_GET_2_PATH="";
    /**
     * JSON数据类型
     */
    private final static MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    /**
     * 从后端获取数据
     *
     * @param path 数据所在url
     * @return 返回json格式的数据
     */
    public static String getJsonDatas(String path) {
        final String[] jsonString = {"error"};//很神奇的一件事，被定义为final的数组，元素是可变的！
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        Request request = new Request.Builder().url("http://47.94.89.241:8080/lundaoweb2/debateby").get().build();
         try{
         response=okHttpClient.newCall(request).execute();
             jsonString[0]=response.body().string();}
         catch (IOException e){
             e.printStackTrace();
             System.out.println("error");
         }
        System.out.println("Success" + jsonString[0]);
        return jsonString[0];//返回String格式的json数据，此时还未解析
    }

    /**
     * 向后端发送数据
     *
     * @param path 所发送数据类型的存储地址
     * @param json 解析过后的json格式的数据
     * @return 返回json格式的数据
     */
    public static boolean postJsonDatas(String path, String json) {
        final boolean result[] = {false};
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(BASE_URL + path).post(RequestBody.create(JSON, json)).build();
        /**
         * 回调方法，检测是否发送成功
         */
          try {
              okHttpClient.newCall(request).execute();
              result[0]=true;
          }catch (IOException e){
              result[0]=false;
              e.printStackTrace();
          }
        System.out.println(result[0]);
        return result[0];
    }


    public static List<? extends Object> getDatas(int type) {
        String path = null;
        /**
         *
         * 根据不同类型数据，在工具类里选择不同的path，获取数据,暂时这样决定，减少调用端的麻烦
         */
        switch (type) {
            case FORUMS_INITIAL_PAGE_GET:
                break;
            case FORUMS_DETAILS_PAGE_GET:
                break;
            case USER_CENTER_USERINFORMATION_GET:
                break;
            case USER_CENTER_FANSINFORMATION_GET:
                break;
            case USER_CENTER_CONCERNED_DEBATE_TOPIC_GET:
                break;
            case USER_CENTER_CONCERNED_THESIS_GET:
                break;
            case USER_CENTER_CONCERNED_USER_GET:
                break;
            case PERFECTPAGE_GET_1:
                break;
            case PERFECTPAGE_GET_2:
                break;
            default:
        }
        List<ForumsInitialPage> forumsInitialPages;//论阁初始页javabean类
        List<ForumsDetailsPage> forumsDetailsPages;//辩题详情javabean类
        List<UserInformationDetail> userInformationDetails;//用户个人信息javabean类
        List<InformationFans> informationFanses;//粉丝信息javabean类
        List<ConcernedDebateTopic> concernedDebateTopics;//用户关注的辩题
        List<ConcernedThesis> concernedThesises;//用户关注的论点
        List<ConcernedUser> concernedUsers;//用户关注的人
        List<PerfectPage> perfectPages;//完善论点页面
        List<Objection> objections;//异议列表
        String jsonDatas;
        jsonDatas = getJsonDatas(path);
        /**
         * 检测是否数据获取成功
         */
        if(jsonDatas.equals("error")){
            return null;
        }
        System.out.println("解析结果" + jsonDatas);
        Gson gson = new Gson();
        /**
         * 根据不同的type解析为不同的list
         */
        switch (type) {
            /**
             * 论阁初始页数据，解析为对应的javaBean类
             */
            case FORUMS_INITIAL_PAGE_GET:
                forumsInitialPages = gson.fromJson(jsonDatas, new TypeToken<List<ForumsInitialPage>>() {
                }.getType());
                return forumsInitialPages;
            case FORUMS_DETAILS_PAGE_GET:
                informationFanses=gson.fromJson(jsonDatas,new TypeToken<List<InformationFans>>(){}.getType());
                return informationFanses;
            case USER_CENTER_FANSINFORMATION_GET:
                informationFanses=gson.fromJson(jsonDatas,new TypeToken<List<InformationFans>>(){}.getType());
                return informationFanses;
            case USER_CENTER_USERINFORMATION_GET:
                userInformationDetails=gson.fromJson(jsonDatas,new TypeToken<List<UserInformationDetail>>(){}.getType());
                return userInformationDetails;
            case USER_CENTER_CONCERNED_DEBATE_TOPIC_GET:
                concernedDebateTopics=gson.fromJson(jsonDatas,new TypeToken<List<ConcernedDebateTopic>>(){}.getType());
                return concernedDebateTopics;
            case USER_CENTER_CONCERNED_THESIS_GET:
                concernedThesises=gson.fromJson(jsonDatas,new TypeToken<List<ConcernedThesis>>(){}.getType());
                return concernedThesises;
            case USER_CENTER_CONCERNED_USER_GET:
                concernedUsers=gson.fromJson(jsonDatas,new TypeToken<List<ConcernedUser>>(){}.getType());
                return concernedUsers;
            case PERFECTPAGE_GET_1:
                perfectPages=gson.fromJson(jsonDatas,new TypeToken<List<PerfectPage>>(){}.getType());
                return perfectPages;
            case PERFECTPAGE_GET_2:
                objections=gson.fromJson(jsonDatas,new TypeToken<List<Objection>>(){}.getType());
                return objections;
            default:
                return null;
        }
    }

    //TODO 下面三个方法重复代码很多，之所以分开写，是为了减少耦合性

    /**
     * 增加某个数据
     * @param type 增加数据的类型
     * @param datas 增加的数据
     * @return 返回增加是否成功
     */
    public static boolean addDatas(int type, Map<String, String> datas) {
        String path = null;
        boolean result = false;
        switch (type) {
            case FORUMS_INITIAL_PAGE_POST_ADD:
                break;
            case FORUMS_DETAILS_PAGE_ADD:
                break;
            case USER_CENTER_USERINFORMATION_POST_ADD:
                break;
            case PERFECTPAGE_POST_ADD:
                break;
        }
        /**
         * 将map数据转化为json
         */
        Gson gson = new Gson();
        String jsonDatas = gson.toJson(datas);
        result = postJsonDatas(path, jsonDatas);
        return result;
    }

    /**
     * 删除数据
     * @param type 删除数据的类型
     * @param datas 删除的数据信息
     * @return 返回删除是否成功
     */
    public static boolean deleteDatas(int type, Map<String, String> datas) {
        String path = null;
        boolean result = false;
        switch (type) {
            case FORUMS_INITIAL_PAGE_DELETE:
                break;
            case FORUMS_DETAILS_PAGE_DElETE:
                break;
            case PERFECTPAGE_POST_DELETE:
                break;
        }
        Gson gson = new Gson();
        String jsonDatas = gson.toJson(datas);
        result = postJsonDatas(path, jsonDatas);
        return result;
    }

    /**
     * 改变某个数据
     * @param type 改变的数据的类型
     * @param datas 改变的数据信息
     * @return 返回改变的结果
     */
    public static boolean changeDatas(int type, Map<String, String> datas) {
        String path = null;
        boolean result = false;
        switch (type) {
            case FORUMS_INITIAL_PAGE_CHANGE:
                break;
            case FORUMS_DETAILS_PAGE_CHANGE:
                break;
            case USER_CENTER_FANSINFORMATION_POST_CHANGE:
                break;
            case USER_CENTER_CONCERNEDUSER_POST_CHANGE:
                break;
            case PERFECTPAGE_POST_CHANGE:
                break;

        }
        Gson gson = new Gson();
        String jsonDatas = gson.toJson(datas);
        result = postJsonDatas(path, jsonDatas);
        return result;
    }

}
