package boyue.bbtuan.db;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;
import java.util.List;

public class SharedPreferenceDb {
    private Context context;
    public SharedPreferenceDb(Context context) {
        this.context=context;
    }

    //签到
    public int getSigninCount(){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        return sp.getInt(DbConstants.KEY_USER_SIGNIN_COUNT, 0);
    }
    public void setSigninCount(int count){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(DbConstants.KEY_USER_SIGNIN_COUNT, count).commit();
    }
    public int getSigninScore(){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        return sp.getInt(DbConstants.KEY_USER_SIGNIN_SCORE, 1);
    }
    public void setSigninScore(int score){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(DbConstants.KEY_USER_SIGNIN_SCORE, score).commit();
    }
    public String getSigninDates(){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        return sp.getString(DbConstants.KEY_USER_SIGNIN_DATES, "");
    }
    public void setSigninDates(String jsonDates){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(DbConstants.KEY_USER_SIGNIN_DATES, jsonDates).commit();
    }

    //用户所在城市
    public String getUserCity(){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        return sp.getString(DbConstants.KEY_USER_CITY, "");
    }
    public void setUserCity(String userCity){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(DbConstants.KEY_USER_CITY, userCity).commit();
    }
    //用户昵称
    public String getName(){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        return sp.getString(DbConstants.KEY_USER_NAME, "");
    }
    public void setName(String name){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(DbConstants.KEY_USER_NAME, name).commit();
    }
    //用户年龄
    public String getAge(){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        return sp.getString(DbConstants.KEY_USER_AGE, "");
    }
    public void setAge(String age){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(DbConstants.KEY_USER_AGE, age).commit();
    }
    //用户头像
    public String getPhoneNum(){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        return sp.getString(DbConstants.KEY_USER_PHONENUM, "");
    }
    public void setPhoneNum(String phoneNum){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(DbConstants.KEY_USER_PHONENUM, phoneNum).commit();
    }
    //用户所在学校
    public String getNameSchool(){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        return sp.getString(DbConstants.KEY_USER_NAMESCHOOL, "");
    }
    public void setNameSchool(String nameSchool){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(DbConstants.KEY_USER_NAMESCHOOL, nameSchool).commit();
    }
    //用户所在学校学院
    public String getNameCollege(){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        return sp.getString(DbConstants.KEY_USER_NAMECOLLEGE, "");
    }
    public void setNameCollege(String nameCollege){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(DbConstants.KEY_USER_NAMECOLLEGE, nameCollege).commit();
    }
    //用户所在学校专业
    public String getNameMajor(){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        return sp.getString(DbConstants.KEY_USER_NAMEMAJOR, "");
    }
    public void setNameMajor(String nameMajor){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(DbConstants.KEY_USER_NAMEMAJOR, nameMajor).commit();
    }
    //用户学习标签
    public String getNameLabel(){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        return sp.getString(DbConstants.KEY_USER_NAMELABEL, "");
    }
    public void setNameLabel(String nameLabel){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(DbConstants.KEY_USER_NAMELABEL, nameLabel).commit();
    }
    //用户个人签名
    public String getNameSignature(){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        return sp.getString(DbConstants.KEY_USER_NAMESIGNATURE, "");
    }
    public void setNameSignature(String nameSignature){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(DbConstants.KEY_USER_NAMESIGNATURE, nameSignature).commit();
    }
    //用户最喜欢的书籍
    public String getNameBooks(){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        return sp.getString(DbConstants.KEY_USER_NAMEBOOKS, "");
    }
    public void setNameBooks(String nameBooks){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(DbConstants.KEY_USER_NAMEBOOKS, nameBooks).commit();
    }
    //用户最喜欢的影视
    public String getNameVideos(){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        return sp.getString(DbConstants.KEY_USER_NAMEVIDEOS, "");
    }
    public void setNameVideos(String nameVideos){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(DbConstants.KEY_USER_NAMEVIDEOS, nameVideos).commit();
    }
    //用户个人说明
    public String getNameIllustrate(){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        return sp.getString(DbConstants.KEY_USER_NAMEILLUSTRATE, "");
    }
    public void setNameIllustrate(String nameIllustrate){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(DbConstants.KEY_USER_NAMEILLUSTRATE, nameIllustrate).commit();
    }
    //用户性别
    public String getSex(){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        return sp.getString(DbConstants.KEY_USER_SEX, "");
    }
    public void setSex(String sex){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(DbConstants.KEY_USER_SEX, sex).commit();
    }
    //用户头像
    public String getImgPath(){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        return sp.getString(DbConstants.KEY_USER_IMAPATH, "");
    }
    public void setImgPath(String imgPath){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_USER_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(DbConstants.KEY_USER_IMAPATH, imgPath).commit();
    }
    //更新软件信息:版本号
    public String getDeviseOldVersion(){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_DEVISE_VERSION, Context.MODE_PRIVATE);
        return sp.getString(DbConstants.KEY_DEVISE_OLDVERSION, "");
    }
    public void setDeviseOldVersion(String deviseOldVersion){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_DEVISE_VERSION, Context.MODE_PRIVATE);
        sp.edit().putString(DbConstants.KEY_DEVISE_OLDVERSION,deviseOldVersion).commit();
    }
    public String getDeviseNeglectVersion(){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_DEVISE_VERSION, Context.MODE_PRIVATE);
        return sp.getString(DbConstants.KEY_DEVISE_NEGLECTVERSION, "");
    }
    public void setDeviseNeglectVersion(String deviseNeglectVersion){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_DEVISE_VERSION, Context.MODE_PRIVATE);
        sp.edit().putString(DbConstants.KEY_DEVISE_NEGLECTVERSION,deviseNeglectVersion).commit();
    }
    public String getDeviseNewVersion(){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_DEVISE_VERSION, Context.MODE_PRIVATE);
        return sp.getString(DbConstants.KEY_DEVISE_NEWVERSION, "");
    }
    public void setDeviseNewVersion(String deviseNewVersion){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_DEVISE_VERSION, Context.MODE_PRIVATE);
        sp.edit().putString(DbConstants.KEY_DEVISE_NEWVERSION,deviseNewVersion).commit();
    }
    //版本更新时间
    public String getDeviseNewVertime(){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_DEVISE_VERSION, Context.MODE_PRIVATE);
        return sp.getString(DbConstants.KEY_DEVISE_NEWVERTIME, "");
    }
    public void setDeviseNewVertime(String deviseNewVertime){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_DEVISE_VERSION, Context.MODE_PRIVATE);
        sp.edit().putString(DbConstants.KEY_DEVISE_NEWVERTIME,deviseNewVertime).commit();
    }
    //版本更新大小
    public String getDeviseNewVercap(){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_DEVISE_VERSION, Context.MODE_PRIVATE);
        return sp.getString(DbConstants.KEY_DEVISE_NEWVERCAP, "");
    }
    public void setDeviseNewVercap(String deviseNewVercap){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_DEVISE_VERSION, Context.MODE_PRIVATE);
        sp.edit().putString(DbConstants.KEY_DEVISE_NEWVERCAP,deviseNewVercap).commit();
    }
    //版本更新内容
    public String getDeviseNewVermessage(){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_DEVISE_VERSION, Context.MODE_PRIVATE);
        return sp.getString(DbConstants.KEY_DEVISE_NEWVERMESSAGE, "");
    }
    public void setDeviseNewVermessage(String deviseNewVermessage){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_DEVISE_VERSION, Context.MODE_PRIVATE);
        sp.edit().putString(DbConstants.KEY_DEVISE_NEWVERMESSAGE,deviseNewVermessage).commit();
    }
    //版本软件路径
    public String getDeviseNewVerapkpath(){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_DEVISE_VERSION, Context.MODE_PRIVATE);
        return sp.getString(DbConstants.KEY_DEVISE_NEWVERAPKPATH, "");
    }
    public void setDeviseNewVerapkpath(String deviseNewVerapkpath){
        SharedPreferences sp=context.getSharedPreferences(DbConstants.DB_DEVISE_VERSION, Context.MODE_PRIVATE);
        sp.edit().putString(DbConstants.KEY_DEVISE_NEWVERAPKPATH,deviseNewVerapkpath).commit();
    }
}
