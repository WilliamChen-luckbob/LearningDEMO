package com.wwstation.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 常量值
 *
 * @author william
 * @description
 * @Date: 2021-02-17 11:56
 */
@Getter
@AllArgsConstructor
public class Constants {
    /**
     * 鉴权部分
     */
    public static final String ACCESS_TOKEN = "token";
    public static final String AUTH_MODIFYING_RESOURCE_LOCK_KEY="LOCK:ResourceIsModifying";
    public static final String AUTH_LOCK_KEY = "LOCK:Auth:%s";
    public static final String AUTH_RSA_TOKEN_PAIR_KEY = "RsaTokenPair:%s";
    public static final String AUTH_USER_ROOT_KEY = "auth:user:%s:*";
    public static final String AUTH_USER_SESSIONPOOL_KEY = "auth:user:sessionPool:%s";
    public static final String AUTH_USER_RESOURCELIST_KEY = "auth:user:%s:resource";
    public static final String AUTH_ALL_RESOURCES_KEY = "basicInfo:resources";
    public static final String AUTH_ALL_FUZZY_RESOURCES_KEY = "basicInfo:fuzzyResources";

    public static final String AUTH_OLD_TOKEN_KEY = "token:%s";
    public static final String AUTH_OLD_USERINFO_KEY = "userData:%s";
    public static final String AUTH_OLD_USER_RESOURCE_KEY = "user:%s:resource";
    public static final String AUTH_OLD_RESOURCE_KEY = "resourceData:%s";
    /**
     * 任务部分
     */
    public static final String TASK_DATA_PATH = "TASK:%s:data";
    public static final String TASK_LOCK_PATH = "LOCK:Task:%s";
    public static final String TASK_FIRST_TASK = "codeStart";
    public static final String TASK_LAST_TASK = "codeEndAndLog";
    public static final String TASK_DOLPHIN_SESSION_PATH = "COOKIES:dolphinScheduler";

    /**
     * 网关统计部分
     */
    public static final String ATTRIBUTE_COMPANY_ID = "companyId";
    public static final String ATTRIBUTE_USER_NAME = "username";
    public static final String ATTRIBUTE_USER_ID = "userId";
    public static final String ATTRIBUTE_PASSED = "passed";
    public static final String ATTRIBUTE_FAILED_REASON = "failedReason";
    public static final String ATTRIBUTE_TPS_START_TIME = "tpsStartTime";
    public static final String ATTRIBUTE_TPS_END_TIME = "tpsEndTime";
    public static final String ATTRIBUTE_GREY_RELEASE_LB_KEY = "greyReleaseTag";
    public static final String ATTRIBUTE_GREY_RELEASE_LB_PASS_HEADER = "grey-release-header";
    public static final String ATTRIBUTE_GREY_RELEASE_LB_STABLE = "Stable";
    public static final String ATTRIBUTE_GREY_RELEASE_LB_TEST = "Test";

    public static final String GATEWAY_FAILED_REASON_NOT_FOUND = "NOT_FOUND";
    public static final String GATEWAY_FAILED_REASON_UNAUTHORIZED = "UNAUTHORIZED";
    public static final String GATEWAY_FAILED_REASON_JWT_UNAUTHORIZED = "JWT_UNAUTHORIZED";
    //网关统计查询需要的一些基础数据在缓存时用的key
    public static final String GATEWAY_STATISTICS_BASIC_CONDITIONS_KEY="statistics:gateway:api:basic_condition";
    //网关统计查询TPS表
    @Deprecated
    public static final String GATEWAY_STATISTICS_TPS_STATUS_KEY="statistics:gateway:api:tps";
    public static final String GATEWAY_STATISTICS_TPS_GRAPH_KEY="statistics:gateway:api:tps:graph:%s";
    public static final String GATEWAY_STATISTICS_TPS_OVERVIEW_KEY="statistics:gateway:api:tps:overview:%s";
    /**
     * 消息队列部分
     */
    public static final String TAG_GATEWAY_COUNT = "urlCount";
    public static final String TAG_TASK_LOG = "taskLog";
    public static final String BROADCAST_ACK_NUM="web_message_center:status:broadcast_ack_num:%s";

    /**
     * 站内信部分
     */
    public static final String UUID_GEN_LOCK_KEY = "LOCK:rocket_mq:get_temp_id:instant_msg";//获取临时UUID时加分布式锁时的标识ID
    public static final String USER_SOCKET_LOGIN_UUID_GEN_LOCK_KEY = "LOCK:rocket_mq:get_temp_id:user_socket_login:%s";//用户获取socket时创建uuid的锁
    public static final String INSTANT_MSG_TEMP_DATA = "web_message:temp_data:producing";//web-message站内信在消息队列发送处理时的临时数据(hash)
    public static final String USER_SOCKET_SESSION_BASIC_INFO = "web_message:user:%s:%s:%s:basic_info";//web-message站内信模块用户socket session的登录信息缓存key，第一个参数是用户标识key（公司id_用户名），第二个用户是session，内容为hash
    public static final String USER_REGISTRATION_INFO = "web_message:user:%s:registration:endpoint_list";//用户登录节点
    public static final String USER_MESSAGE_READ_STATUS = "web_message:user:%s:messages";//用户已读状态缓存
    public static final String USER_SESSION_INITIALIZING_LOCK_KEY = "LOCK:web_message:user:%s:initializing";//用户是否有session正在初始化，如有不能查询，需等待
    public static final String USER_CONSUMER_TEMP_STATUS="web_message:temp_data:consuming:%s:%s";//消费成功与否的判定依据表
    public static final String USER_MESSAGE_PERSIST_LOCK_KEY="LOCK:web_message:user:%s:persist_msg:%s";//用户持久化消息分布式锁，只能有一个节点能够对数据持久化



    /**
     * 链路追踪
     */

    //链路ID，每个请求对应的ID全局唯一
    public static final String TRACE_KEY = "traceId";
    // 链路ID下的服务层级ID，每个traceId内部唯一
    public static final String TRACE_OP_KEY = "traceOp";
    public static final String TRACE_PREVIOUS_OP_KEY = "previousTraceOp";

    private String value;
}
