package com.dreamsofpines.mcunost.data.network.api;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Created by ThePupsick on 17.10.2017.
 */

public class Constans {

    public static class ACCOUNT{
        public static final int BAN = 0;
        public static final int ACCESS = 1;
    }

    public static class ROLE{
        public static final int USER = 1;
        public static final int MANAGER = 2;
    }

    public static class CITY{
        public static final int INT_SAINT_PETERBURG= 1;
        public static final int INT_OTHER_CITY = 3;
        public static final int INT_MOSCOW = 2;
    }
    public static class AUTH{
        public static final int LOG_IN= 1;
        public static final int LOG_OUT = 0;
    }

    public static class URL{
//        private static final String HOST = "http://vfproject.ru:49173/";
        private static final String HOST = "http://192.168.0.30:8080/";

        public static class TOUR{
            private static final String TAG_TOUR = "tour/";
            public static final String GET_CATEGORY_TOUR = HOST + TAG_TOUR + "category/get";
            public static final String GET_PACK_EXCUR = HOST + TAG_TOUR +"package/{region}/{category}";
            public static final String GET_HOTEL = HOST + TAG_TOUR +"hotel/{idCity}";
            public static final String GET_EXCURSION = HOST+TAG_TOUR+"excursion/{idCity}";
            public static final String GET_CITY = HOST + TAG_TOUR +"city/get";


        }

        public static class PHONE_ORDER{
            private static final String TAG_ORDER = "call/";
            public static final String BOOOK_CALL = HOST + TAG_ORDER +"add";
        }
        public static class USER {
            private static final String TAG_USER = "users/";
            public static final String ADD_NEW_USER = HOST + TAG_USER +"add";
            public static final String UPDATE_USER_INFO = HOST + TAG_USER +"settings/updateInfo";
            public static final String GET_USER_BY_LOGIN = HOST + TAG_USER +"get/userlogin/{login}";
            public static final String SET_TOKEN_USER = HOST+"register";
        }

        public static class ORDER {
            private static final String TAG_ORDER = "order/";
            public static final String GET_ALL_ORDER_USER_BY_ID = HOST+ TAG_ORDER +"getAllOrderUser/id/{id}";
            public static final String GET_ALL_ORDER_USER_BY_LOGIN = HOST+ TAG_ORDER +"getAllOrderUser/login/{login}";
            public static final String ADD_NEW_ORDER =  HOST + TAG_ORDER + "add";
            public static final String GET_USER_BY_LOGIN =  HOST + TAG_ORDER + "getUserByLogin/{login}";

        }

        public static class CALCULATOR{
            private static final String TAG_CALC = "calculator/";
            public static final String CALCULATE = HOST+TAG_CALC+"calculate";
        }

        public static class MESSAGE{
            private static final String TAG_MESSAGE = "chat/";
            public static final String GET_MESSAGES = HOST+TAG_MESSAGE+"get/{idcust}/{order}";
            public static final String GET_DIALOG = HOST+TAG_MESSAGE+"get/{id}";
            public static final String SET_WAS_READ = HOST+TAG_MESSAGE+"setwasread/{id}";
        }
        public static class DOWNLOAD{
            private static final String TAG_DOWNLOAD="download/";
            public static final String GET_IMG=HOST+TAG_DOWNLOAD+"img/";
            public static final String GET_DOC=HOST+TAG_DOWNLOAD+"doc/{name}";

        }


    }

}
