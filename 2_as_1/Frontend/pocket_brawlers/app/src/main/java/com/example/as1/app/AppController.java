package com.example.as1.app;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.as1.utils.LruBitmapCache;

/**
 * The AppController class is a singleton class that manages the RequestQueue and ImageLoader for
 * making network requests and loading images in an Android application using Volley library.
 */
public class AppController extends Application {

	public static final String TAG = AppController.class
			.getSimpleName();

	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;

	private static AppController mInstance;

	/**
	 * This function is called when the application is created and sets the instance of the application.
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
	}

	/**
	 * This function returns an instance of the AppController class and ensures that only one instance can
	 * be accessed at a time.
	 *
	 * @return The method is returning an instance of the AppController class. The keyword "synchronized"
	 * ensures that only one thread can access this method at a time, preventing any potential concurrency
	 * issues.
	 */
	public static synchronized AppController getInstance() {
		return mInstance;
	}

	/**
	 * This function returns a RequestQueue object and creates a new one if it doesn't exist.
	 *
	 * @return The method `getRequestQueue()` returns an instance of `RequestQueue`.
	 */
	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	/**
	 * This function returns an instance of the ImageLoader class with a LruBitmapCache.
	 *
	 * @return The `getImageLoader()` method is returning an instance of the `ImageLoader` class.
	 */
	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(this.mRequestQueue,
					new LruBitmapCache());
		}
		return this.mImageLoader;
	}

	/**
	 * This function adds a request to the request queue with a specified tag or a default tag if none is
	 * provided.
	 *
	 * @param req The request to be added to the request queue. It can be of any type, as it is a generic
	 * method.
	 * @param tag The tag is a string identifier for the request. It is used to identify the request in
	 * case it needs to be canceled or removed from the request queue. If the tag is empty, the default
	 * tag (defined as a constant variable in the class) will be used.
	 */
	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	/**
	 * This function adds a request to the request queue and assigns a tag to it.
	 *
	 * @param req req is an instance of the Request class, which is a generic class that represents a
	 * network request. It can be used to make various types of network requests such as GET, POST, PUT,
	 * DELETE, etc. The type parameter T represents the type of the response expected from the server.
	 */
	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	/**
	 * This function cancels all pending requests with a specific tag in the request queue.
	 *
	 * @param tag The tag parameter is an object that is used to identify a group of requests that should
	 * be cancelled. When a request is added to the request queue, a tag can be assigned to it. Later, if
	 * we want to cancel all requests with a specific tag, we can call this method with that tag
	 */
	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}
}
