package com.example.xu.atest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.text.Html;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyTextView extends LinearLayout {

    private static android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public synchronized void handleMessage(Message msg) {
            HashMap<String, Object> hashMap = (HashMap<String, Object>) msg.obj;
            ImageView imageView = (ImageView) hashMap.get("imageView");
            Drawable drawable = (Drawable) hashMap.get("drawable");
            LayoutParams params = new LayoutParams(msg.arg1, msg.arg2);
            params.gravity = Gravity.CENTER_HORIZONTAL;
            imageView.setLayoutParams(params);
            imageView.setImageDrawable(drawable);
        }
    };
    //上下文对象
    private Context mContext;
    //声明TypedArray引用
    private TypedArray mTypeArray;
    //布局参数
    private LayoutParams mParams;
    //图片关键字
    private String IMAGE = "image";
    private int mPaddingLeft = 10;
    private int mPaddingRight = 10;
    //文字关键字
    private String TEXT = "text";
    // 线程池
    private ExecutorService mExecutor;

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setOrientation(LinearLayout.VERTICAL);
        mTypeArray = mContext.obtainStyledAttributes(attrs, R.styleable.myTextView);
        mPaddingLeft = mTypeArray.getDimensionPixelOffset(R.styleable.myTextView_paddingleft, mPaddingLeft);
        mPaddingRight = mTypeArray.getDimensionPixelOffset(R.styleable.myTextView_paddingright, mPaddingRight);
        setPadding(mPaddingLeft, 0, mPaddingRight, 0);
        mExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    /**
     * 设置视图内容，首先清空之前所有的内容，然后添加要添加的内容
     *
     * @param datas
     */
    public void setContent(ArrayList<HashMap<String, String>> datas) {
        removeAllViews();
        addContent(datas);
    }

    /**
     * 添加视图内容
     *
     * @param datas
     */
    public void addContent(ArrayList<HashMap<String, String>> datas) {
        for (HashMap<String, String> hashMap : datas) {
            String type = hashMap.get("type");
            if (type.equals(IMAGE)) {
                int imageWidth = mTypeArray.getDimensionPixelOffset(R.styleable.myTextView_image_width, 100);
                int imageHeight = mTypeArray.getDimensionPixelOffset(R.styleable.myTextView_image_height, 100);
                ImageView imageView = new ImageView(mContext);
                mParams = new LayoutParams(imageWidth, imageHeight);
                mParams.gravity = Gravity.CENTER_HORIZONTAL;
                imageView.setLayoutParams(mParams);
                imageView.setImageResource(R.drawable.cache);

                addView(imageView);
                mExecutor.execute(new DownloadPicThread(imageView, hashMap.get("value")));
            } else if (type.equals(TEXT)) {
                float textSize = mTypeArray.getDimensionPixelSize(R.styleable.myTextView_textSize, 16);
                int textColor = mTypeArray.getColor(R.styleable.myTextView_textColor, 0xff000000);
                TextView textView = new TextView(mContext);
                textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
                textView.setText(Html.fromHtml(hashMap.get("value")));
                textView.setTextColor(textColor);
                textView.setTextSize(textSize);
                addView(textView);
            }
        }
    }

    private class DownloadPicThread implements Runnable {
        //图片控件
        private ImageView mImageView;
        //图片网址
        private String Url;

        public DownloadPicThread(ImageView imageView, String url) {
            super();
            mImageView = imageView;
            Url = url;
        }

        @Override
        public void run() {
            Drawable drawable = null;
            InputStream inputStream = null;
            int imageWidth = 0;
            int imageHeight = 0;
            try {
                System.out.println(Url);
                inputStream = new URL(Url).openStream();
                drawable = Drawable.createFromStream(inputStream, "image");
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageHeight = drawable.getIntrinsicHeight();
                imageWidth = drawable.getIntrinsicWidth();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //SystemClock.sleep(2000);
            Message msg = mHandler.obtainMessage();
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("imageView", mImageView);
            hashMap.put("drawable", drawable);
            msg.obj = hashMap;
            msg.arg1 = imageWidth;
            msg.arg2 = imageHeight;

            synchronized (mHandler) {
                mHandler.sendMessage(msg);
            }

        }
    }

}
