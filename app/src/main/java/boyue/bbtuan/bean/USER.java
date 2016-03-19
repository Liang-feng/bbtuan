package boyue.bbtuan.bean;


public class USER {
    public static String numberPhone="15078303413";
    public static Boolean isCheckPhone=false;
    public static Boolean isCheckPhoneNext=false;
    public static Boolean isForgetPwd=false;
    public static double latitude=0.0;
    public static double lontitude=0.0;
    public static String addr="";
    public static String city="";
    public static String province="";
    public static String changeCity="";
    public static String getProvince() {
        return province;
    }

    public static void setProvince(String province) {
        USER.province = province;
    }

    public static String getChangeCity() {
        return changeCity;
    }

    public static void setChangeCity(String changeCity) {
        USER.changeCity = changeCity;
    }

    public static String getCity() {
        return city;
    }

    public static void setCity(String city) {
        USER.city = city;
    }

    public static String locDescribe="";
    public static int screenWidth=0;
    public static int screenHeight=0;
    public static float scalePx2Dp=0;
    public static String getLocDescribe() {
        return locDescribe;
    }

    public static void setLocDescribe(String locDescribe) {
        USER.locDescribe = locDescribe;
    }

    public static String getAddr() {
        return addr;
    }

    public static void setAddr(String addr) {
        USER.addr = addr;
    }

    public static double getLontitude() {
        return lontitude;
    }

    public static void setLontitude(double lontitude) {
        USER.lontitude = lontitude;
    }

    public static double getLatitude() {
        return latitude;

    }

    public static void setLatitude(double latitude) {
        USER.latitude = latitude;
    }


    public static String IMAGEPATH="/BoYue/boyuehead.png";
    public Boolean getIsCheckPhone() {
        return isCheckPhone;
    }

    public void setIsCheckPhone(Boolean isCheckPhone) {
        this.isCheckPhone = isCheckPhone;
    }

    public static void setNumberPhone(String numberPhone) {
        USER.numberPhone = numberPhone;
    }

    public static String getNumberPhone() {
        return numberPhone;
    }
}
