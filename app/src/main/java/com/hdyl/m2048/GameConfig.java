package com.hdyl.m2048;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.IntDef;

import com.hdyl.baselib.base.App;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2017/4/23.
 */

public class GameConfig {


    @GameStringMode
    public int showMode=GAME_MODE_ZHONGWENNUM;
    public boolean isSound=false;//是否有音效
    public boolean isAnim=true;//是否有动画
    public int saveType=0;//存档类型 1,2,3三个存档
    public int gamePlayType=0;//游戏玩法类型
    /***
     * 0 4X4普通类型
     * 1 3X3普通类型     增大数字模式，（2^9=512  2048会X4   则最小数字是 8 、16）
     * 2 5X5普通类型
     * 3 2048++  增加0的数字*（这个比较难，后期考虑要不要）
     * 3 限时模式（如限时1分钟根据最高分进行排行，最大数字进行排行）
     * 4 目标数字模式（比如只要最大数字是2048即胜利，得分排行，时间排行榜）
     * 5 目标得分模式（如只要得分为10000分数即可胜利，时间排行榜）
     * 6 残局模式（关卡设计）  先设计一些关卡
     * 7 赢在起跑线模式（满屏2048，满屏4096，满屏。。。）
     * 8 不规则类型面板模式 （3x4模式 3X5模式4X5模式2X4模式2X5模式）
     * 只对普通模式有效果：成就系统  收集2048奖章   1 firstblood  2secondboold 5个10个20个50个100个200个500个1000个
     * 只对普通模式有效果：1不使用撤销功能玩到2048 1次，5次，10次，20次，50次100次，(取名从不后悔模式)
     * 2使用1次，5次，10次，20次，50次100次，200次500次1000次撤销功能
     *
     */




    private static  GameConfig singlelen;

    private final static String KEY_MODE="1";

    public static GameConfig getInstance() {
        if(singlelen==null){
            singlelen=new GameConfig();
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(App.getContext());
            @GameStringMode
            int gameStringMode=settings.getInt(KEY_MODE,GAME_MODE_DEFAULT);
            singlelen.setShowMode(gameStringMode);
        }
        return singlelen;
    }

    public void save(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(KEY_MODE,showMode);
        editor.commit();
    }



    @IntDef({GAME_MODE_DEFAULT, GAME_MODE_DYNASTY,GAME_MODE_TIANGAN,GAME_MODE_DIZHI, GAME_MODE_12_ANIMALS, GAME_MODE_ZHONGWENNUM, GAME_MODE_CHAR})
    @Retention(RetentionPolicy.SOURCE)
    public  @interface GameStringMode {}
    public final static int GAME_MODE_DEFAULT=0;
    public final static int GAME_MODE_DYNASTY=1;
    public final static int GAME_MODE_TIANGAN=2;
    public final static int GAME_MODE_DIZHI=3;
    public final static int GAME_MODE_CHAR =4;
    public final static int GAME_MODE_12_ANIMALS =5;
    public final static int GAME_MODE_ZHONGWENNUM =6;


    public String getModeName(){
        String str=null;
        switch (showMode){
            case GAME_MODE_DEFAULT:
                str="经典模式";
                break;
            case GAME_MODE_DIZHI:
                str="地支";
                break;
            case GAME_MODE_DYNASTY:
                str="朝代";
                break;
            case GAME_MODE_12_ANIMALS:
                str="生肖";
                break;
            case GAME_MODE_TIANGAN:
                str="天干";
                break;
            case GAME_MODE_CHAR:
                str="字母";
                break;
            case GAME_MODE_ZHONGWENNUM:
                str="大写数字";
                break;
        }
        return str;
    }

    public void exChangeMode(){
        if(showMode==GAME_MODE_ZHONGWENNUM){
            showMode=GAME_MODE_DEFAULT;
        }
        else {
            showMode++;
        }
        save();
    }

    public int getShowMode() {
        return showMode;
    }
    public boolean isDefaultMode(){
        return showMode==GAME_MODE_DEFAULT;
    }

    public void setShowMode(@GameStringMode int showMode) {
        this.showMode = showMode;
    }
    public String getStringShow(Cell cell){
        String str=null;
        switch (showMode){
            case GAME_MODE_DEFAULT:
                str= cell.getValue()+"";
                break;
            case GAME_MODE_DYNASTY:
                str= getArrString(cell,strsDinasties);
                break;
            case GAME_MODE_TIANGAN:
                str=getArrString(cell,strsTiangan);
                break;

            case GAME_MODE_DIZHI:
                str= getArrString(cell,strsDizhi);
                break;
            case GAME_MODE_CHAR:
                str= getArrString(cell, strsChar);
                break;
            case GAME_MODE_ZHONGWENNUM:
                str= getArrString(cell, strsZhongwenNum);
                break;
            case GAME_MODE_12_ANIMALS:
                str= getArrString(cell, strsNoText);
                break;
        }
        return str;
    }

    public String getArrString(Cell cell, String strs[]){
        int index= (int) (Math.log(cell.getValue())/ Math.log(2))-1;
        if(index<0||index>=strs.length){
            return "...";
        }
        else {
            return  strs[index];
        }
    }

    final  String[]strsDinasties={"夏","商","周","秦","汉","三国","晋","隋","唐","宋","元","明","清","新中国"};
    final  String[]strsTiangan={"甲","乙","丙","丁","戊","己","庚","辛","壬","癸","甲子","乙丑","丙寅","丁卯"};
    final  String[]strsDizhi={"子","丑","寅","卯","辰","巳","午","未","申","酉","戌","亥","甲子","乙丑","丙寅","丁卯"};
    final  String[] strsChar ={"A","B","C","D","E","F","G","H","I","J","X","Y","Z"};
    final String[] strsZhongwenNum="壹,贰,叁,肆,伍,陆,柒,捌,玖,拾,佰,仟,萭,亿".split(",");
    final String[] strsNoText ={"鼠","牛","虎","兔","龙","蛇","马","羊","猴","鸡","狗","猪"};
}
