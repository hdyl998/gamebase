package com.hdyl.pintu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdyl.baselib.utils.bufferknife.MyBindView;
import com.hdyl.baselib.utils.bufferknife.MyBufferKnifeUtils;
import com.hdyl.mine.R;
import com.hdyl.pintu.common.BaseActivity;
import com.hdyl.pintu.common.Constants;
import com.hdyl.pintu.save.SaveData;

public class MainPintuActivity extends BaseActivity {

    GameView15 gameView15;
    ImageView imageView;
    TextView tvCurrentTextView;
    int arr[][];
    CheckBox checkBox;

    TextView textViewScore;

    View viewMenu, viewTools;

    @Override
    public void onBackPressed() {

        boolean isIn = false;
        if (viewTools.getVisibility() == View.VISIBLE) {
            viewTools.setVisibility(View.GONE);
            isIn = true;
        }
        if (viewMenu.getVisibility() == View.VISIBLE) {
            viewMenu.setVisibility(View.GONE);
            isIn = true;
        }
        if (isIn == false)
            super.onBackPressed();
    }

    /**
     * 隐藏菜单
     */
    public void hideMenu() {
        if (viewTools.getVisibility() == View.VISIBLE) {
            viewTools.setVisibility(View.GONE);
        }
        if (viewMenu.getVisibility() == View.VISIBLE) {
            viewMenu.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(int arg0) {
        switch (arg0) {
            case R.id.imageViewExit:
                finish();
                break;
            case R.id.imageViewNewStart:
                gameView15.newGame(false);
                viewMenu.setVisibility(View.GONE);
                break;
            case R.id.imageViewTop:
            case R.id.iv_top:
                Intent intent = new Intent(this, DetailActivity.class);
                startActivity(intent);
                viewMenu.setVisibility(View.GONE);
                break;
            case R.id.imageViewClass:
                gameView15.newGame(true);
                viewMenu.setVisibility(View.GONE);
                break;
            case R.id.imageViewExchange:
                gameView15.changeMode();
                changeMode();
                viewTools.setVisibility(View.GONE);
                break;
            case R.id.imageView1:
            case R.id.imageViewChoosePhoto:// 选择照片
                Intent intent2 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent2, RESULT1);
                viewTools.setVisibility(View.GONE);
                break;
            case R.id.imageViewHelp:// 帮助
                Uri uri = Uri.parse(Constants.URLHELP_STRING);
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);
                viewMenu.setVisibility(View.GONE);
                break;
            case R.id.imageViewAbout:// 关于
                ConirmDialog conirmDialog = new ConirmDialog(this, getResources().getString(R.string.app_note), null, null);
                conirmDialog.show();
                viewMenu.setVisibility(View.GONE);
                break;
            case R.id.iv_menu:// 菜单
                if (viewMenu.getVisibility() == View.VISIBLE) {
                    viewMenu.setVisibility(View.GONE);

                } else {
                    Animation animation2 = AnimationUtils.loadAnimation(getApplication(), R.anim.slide_in_from_bottom);
                    viewMenu.startAnimation(animation2);
                    viewTools.setVisibility(View.GONE);
                    viewMenu.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.iv_toos:// 工具
                if (viewTools.getVisibility() == View.VISIBLE) {
                    viewTools.setVisibility(View.GONE);

                } else {
                    Animation animation2 = AnimationUtils.loadAnimation(getApplication(), R.anim.slide_in_from_bottom);
                    viewTools.startAnimation(animation2);
                    viewTools.setVisibility(View.VISIBLE);
                    viewMenu.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_current_mode:
                if ((Integer) tvCurrentTextView.getTag() == 0) {
                    onClick(R.id.imageViewClass);
                } else {
                    onClick(R.id.imageViewNewStart);
                }
                break;

            case R.id.ll_main:
                hideMenu();
                break;
        }
    }

    public final static int RESULT1 = 003;
    public final static int CROP_BIG_PICTURE = 004;

    public void changeMode() {
        if (saveData.isNummode)
            imageView.setImageResource(R.drawable.ic_aim_num);
        else {
            imageView.setImageBitmap(Constants.curPicBitmap);
        }
    }

    Handler handler = new Handler();

    int curTime = 0;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            curTime++;
            tvTime.setText(curTime + "");
            handler.postDelayed(runnable, 1000);
        }
    };


    public void startTime() {
        stopTime();
        curTime = 0;
        tvTime.setText(curTime + "");
        handler.postDelayed(runnable, 1000);
    }

    public void setTvTimeText(String text){
        tvTime.setText(text);
    }

    public void stopTime() {
        handler.removeCallbacks(runnable);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTime();
    }

    public void setText(String id) {
        textViewScore.setText(id);
    }

    public void setMode(boolean isRandom) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        int highsocre = databaseHelper.selectStepHighScore(!isRandom);

        textViewBestTextView.setText("步数最佳:" + highsocre);

        tvTimeBest.setText("时间最佳:" + databaseHelper.selectTimeHighScore(!isRandom));

        if (isRandom == false) {
            tvCurrentTextView.setText("随机");
            Drawable rightDrawable = getResources().getDrawable(R.drawable.ic_random);
            rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
            tvCurrentTextView.setCompoundDrawables(null, rightDrawable, null, null);
            tvCurrentTextView.setTag(0);
        } else {
            Drawable rightDrawable = getResources().getDrawable(R.drawable.ic_jd);
            rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
            tvCurrentTextView.setCompoundDrawables(null, rightDrawable, null, null);
            tvCurrentTextView.setText("经典");
            tvCurrentTextView.setTag(1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            if (requestCode == RESULT1 && data != null) {
                Uri selectedImage = data.getData();

                if (selectedImage == null) {
                    ConirmDialog dialog = new ConirmDialog(this, "获得相册图片失败！", null, null);
                    dialog.show();
                    return;
                }

                cropImageUri(selectedImage, 800, 800, CROP_BIG_PICTURE);
            } else if (requestCode == CROP_BIG_PICTURE) {
                Uri selectedImage = data.getData();

                if (selectedImage == null) {
                    ConirmDialog dialog = new ConirmDialog(this, "裁剪图片失败！", null, null);
                    dialog.show();
                    return;
                }

                // String[] filePathColumn = { MediaStore.Images.Media.DATA };
                //
                // Cursor cursor = getContentResolver().query(selectedImage,
                // filePathColumn, null, null, null);
                // cursor.moveToFirst();
                //
                // int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                // String picturePath = cursor.getString(columnIndex);
                // cursor.close();
                // BitmapFactory.Options options = new BitmapFactory.Options();
                // Bitmap defaultBitmap = BitmapFactory.decodeFile(picturePath,
                // options);
                Bitmap bitmap = Tools.getBitmapFromUri(this, selectedImage);
                if (bitmap == null)
                    return;
                imageView.setImageBitmap(bitmap);
                Constants.loadBitmaps(bitmap);
                saveData.uriImage = selectedImage.toString();
                Log.e("my", saveData.uriImage);
                saveData.isNummode = false;
                gameView15.invalidate();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, requestCode);

    }

    @Override
    protected void onPause() {
        SaveData.saveSetting();
        super.onPause();
    }

    SaveData saveData;

    TextView textViewBestTextView;


    @MyBindView(R.id.tvTime)
    TextView tvTime;

    @MyBindView(R.id.tvTimeBest)
    TextView tvTimeBest;

    @Override
    protected void initData() {
        setContentView(R.layout.activity_main_pintu);

        MyBufferKnifeUtils.inject(this);


        gameView15 = (GameView15) findViewById(R.id.gameView1);
        int ids[] = {R.id.iv_menu, R.id.iv_toos, R.id.iv_top, R.id.ll_main,

                R.id.imageViewAbout, R.id.imageViewChoosePhoto, R.id.imageViewClass, R.id.imageViewExchange, R.id.imageViewExit, R.id.imageViewHelp, R.id.imageViewNewStart, R.id.imageViewNummark, R.id.imageViewTop};
        for (int i = 0; i < ids.length; i++)
            findViewById(ids[i]).setOnClickListener(this);

        textViewBestTextView = (TextView) findViewById(R.id.textViewHighScore);

        viewMenu = findViewById(R.id.ll_menu);
        viewTools = findViewById(R.id.ll_menu_tool);
        checkBox = (CheckBox) findViewById(R.id.imageViewNummark);
        textViewScore = (TextView) findViewById(R.id.textViewCurrentScore);
        imageView = (ImageView) findViewById(R.id.imageView1);
        imageView.setOnClickListener(this);
        tvCurrentTextView = (TextView) findViewById(R.id.tv_current_mode);
        tvCurrentTextView.setOnClickListener(this);
        saveData = SaveData.getInstance();
        checkBox.setChecked(saveData.isShowNum);
        checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                saveData.isShowNum = arg1;
                gameView15.invalidate();
                viewTools.setVisibility(View.GONE);
            }
        });
        changeMode();
    }
}
