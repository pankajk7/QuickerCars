package com.pankaj.quicker;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import android.app.Application;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class ApplicationQuicker extends Application {
     
    private static ApplicationQuicker sInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
     
    public static ApplicationQuicker getInstance(){
        return sInstance;
    }
 
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
         
        mRequestQueue = Volley.newRequestQueue(this);
        mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {
             
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
     
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }

			@Override
			public void putBitmap(String url, Bitmap bitmap) {
				mCache.put(url, bitmap);
			}
			
        });
    }
     
    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }
  
    public ImageLoader getImageLoader(){
        return mImageLoader;
    }   
 
}
         