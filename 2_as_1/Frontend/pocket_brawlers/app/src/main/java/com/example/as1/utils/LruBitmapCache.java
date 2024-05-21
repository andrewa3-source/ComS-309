package com.example.as1.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

/**
 * The LruBitmapCache class is a cache for storing Bitmap images in memory using LRU (Least Recently
 * Used) algorithm.
 */
public class LruBitmapCache extends LruCache<String, Bitmap> implements ImageCache {
	/**
	 * This function returns the default size of an LRU cache based on the maximum memory available to the
	 * Java runtime environment.
	 *
	 * @return The method is returning an integer value which represents the default size of an LRU (Least
	 * Recently Used) cache. This size is calculated based on the maximum memory available to the
	 * application at runtime, and is set to be one eighth of that value.
	// This is a constructor for the LruBitmapCache class that calls another constructor with a default
	// cache size obtained from the `getDefaultLruCacheSize()` method. It allows the creation of a new
	// instance of the LruBitmapCache class with a default cache size.
	 */
	public static int getDefaultLruCacheSize() {
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		final int cacheSize = maxMemory / 8;

		return cacheSize;
	}

	public LruBitmapCache() {
		this(getDefaultLruCacheSize());
	}

	public LruBitmapCache(int sizeInKiloBytes) {
		super(sizeInKiloBytes);
	}

	/**
	 * This function calculates the size of a Bitmap object in kilobytes based on its row bytes and
	 * height.
	 *
	 * @param key The key is a unique identifier for the Bitmap object in the cache. It is usually a
	 * String that represents the URL or file path of the image.
	 * @param value The "value" parameter in this method refers to a Bitmap object, which is an image that
	 * has been loaded into memory. The method calculates the size of this Bitmap object in kilobytes (KB)
	 * by multiplying the number of bytes per row by the height of the image and then dividing by
	 * @return The method `sizeOf` is returning the size of the input `Bitmap` object in kilobytes (KB).
	 * It calculates the size by multiplying the number of bytes per row of the bitmap with its height and
	 * then dividing the result by 1024 to convert it to KB.
	 */
	@Override
	protected int sizeOf(String key, Bitmap value) {
		return value.getRowBytes() * value.getHeight() / 1024;
	}

	/**
	 * This function returns a Bitmap object for a given URL.
	 *
	 * @param url The URL of the image that needs to be fetched as a Bitmap.
	 * @return A Bitmap object is being returned.
	 */
	@Override
	public Bitmap getBitmap(String url) {
		return get(url);
	}

	/**
	 * This function puts a bitmap into a cache with a specified URL key.
	 *
	 * @param url A string representing the URL of the image to be cached.
	 * @param bitmap A Bitmap is a representation of an image in Android. It is a type of object that
	 * contains the pixel data of an image and can be used to display the image on the screen or
	 * manipulate it in various ways. In the given code, the bitmap parameter is the image that needs to
	 * be stored in
	 */
	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		put(url, bitmap);
	}
}