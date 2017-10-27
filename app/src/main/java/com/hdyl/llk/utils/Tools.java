package com.hdyl.llk.utils;

import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import com.hdyl.baselib.base.App;
import com.hdyl.mine.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class Tools {
    // public static void changBG(View view) {
    // int res[] = { R.drawable.bg1, R.drawable.b3, R.drawable.b4 };
    // Random random = new Random();
    // int raa = random.nextInt(res.length);
    // view.setBackgroundResource(res[raa]);
    // }

    /**
     * 设置textView drawable
     *
     * @param textView  控件
     * @param resID     资源ID
     * @param direction 方向 0-左，1-上 2-右 3下
     */
    public static void setTextViewDrawable(TextView textView, int resID, int direction) {
        Drawable rightDrawable = textView.getResources().getDrawable(resID);
        rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
        switch (direction) {
            case 0:
                textView.setCompoundDrawables(rightDrawable, null, null, null);
                break;
            case 1:
                textView.setCompoundDrawables(null, rightDrawable, null, null);
                break;
            case 2:
                textView.setCompoundDrawables(null, null, rightDrawable, null);
                break;
            default:
                textView.setCompoundDrawables(null, null, null, rightDrawable);
                break;
        }
    }

    public static String getSDCardPath() {
        if (hasSdcard()) {
            return Environment.getExternalStorageDirectory().getPath();
        }
        return null;
    }

    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public static String createFile(String dirName) {
        if (Tools.hasSdcard()) {
            File pictureFileDir = new File(Tools.getSDCardPath() + File.separator + dirName);
            if (!pictureFileDir.exists()) {
                pictureFileDir.mkdirs();
            }
            return pictureFileDir.getPath();
        }
        return null;
    }


    public static void writeFileByLines(String fileName, String content) {
        try {
            File file = new File(createFile(App.getContext().getPackageName()) + File.separator + fileName);
            if (!file.exists())
                file.createNewFile();
            FileOutputStream out = new FileOutputStream(file, false); // 如果追加方式用true
            out.write(content.getBytes("utf-8"));// 注意需要转换对应的字符集
            out.close();
            Log.e("aa", "文件写成功");
        } catch (IOException ex) {
            Log.e("aa", "文件写错误" + ex.getStackTrace());
        }
    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static String readFileByLines(String fileName) {
        File file = new File(createFile("鲜花连连看") + File.separator + fileName);
        if (!file.exists())
            return null;
        BufferedReader reader = null;
        StringBuffer sbBuffer = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // int line = 1;
            // 一次读入一行，直到读入null为文件结束

            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                // System.out.println("line " + line + ": " + tempString);
                // line++;
                sbBuffer.append(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return sbBuffer.substring(0);
    }

//	public static String readTxtFile(String filePath) {
//		try {
//			String encoding = "GBK";
//			File file = new File(filePath);
//			if (file.isFile() && file.exists()) { // 判断文件是否存在
//				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
//				BufferedReader bufferedReader = new BufferedReader(read);
//				String lineTxt = null;
//				StringBuilder stringBuilder = new StringBuilder();
//				while ((lineTxt = bufferedReader.readLine()) != null) {
//					stringBuilder.append(lineTxt);
//				}
//				read.close();
//				return stringBuilder.substring(0);
//			} else {
//				System.out.println("找不到指定的文件");
//			}
//		} catch (Exception e) {
//			System.out.println("读取文件内容出错");
//			e.printStackTrace();
//		}
//		return "";
//	}
}
