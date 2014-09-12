package webapps;

/**
 * Common constants used in web applications.
 */
public interface WebappsConstants {

    /** Constants for the various servlet scopes. */
    public static final String SCOPE_APPLICATION = "Application";
    public static final String SCOPE_SESSION = "Session";
    public static final String SCOPE_REQUEST = "Request";
    public static final String UNVERIFIED_VOLUNTEER_KEY = "unVerifiedVolunnteer";
    public static final String UNINTERVIEWED_VOLUNTEER_KEY = "unInterviewedVolunnteer";

    /** Tokens for storing items in the servlet context. */
    public static final String CTX_TOKEN_WEBAPP_SESSION = "WEBAPP_SESSION_CONTEXT";
    public static final String CTX_TOKEN_WEBAPP_TIMEDOUTSESSION = "WEBAPP_TIMEDOUT_SESSION";
    public static final String CTX_TOKEN_ERROR_MSG_REQUEST = "ERROR_MSG";
    public static final String CTX_TOKEN_WARNING_MSG_REQUEST = "WARNING_MSG";
    public static final String CTX_TOKEN_STATUS_MSG_REQUEST = "STATUS_MSG";
    public static final String CTX_TOKEN_ERROR_MSG_SESSION = "ERROR_MSG_SESSION";
    public static final String CTX_TOKEN_WARNING_MSG_SESSION = "WARNING_MSG_SESSION";
    public static final String CTX_TOKEN_STATUS_MSG_SESSION = "STATUS_MSG_SESSION";
    public static final String CTX_TOKEN_WARNING_STATUS_MSG_SESSION = "WARNING_STATUS_MSG_SESSION";
    
    public final static String LOGIN_USER_SESSION_ID = "sessionUser";
    public final static String USER_DB_FLAG = "USER_DB_FLAG";
    public final static String LOGIN_BACKEND_USER_SESSION_ID = "backendSessionUser";
    
    public final static String ID_PREFIX_KEY = "id_prefix";
}
