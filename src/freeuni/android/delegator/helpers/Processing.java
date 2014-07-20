package freeuni.android.delegator.helpers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.Formatter;
import android.util.Log;

/**
 * This class is helper class for others. there are needed some code for many of other classes.
 * To use DRY, here will be some common methods. 
 * Copied from Messenger Project. (c) me
 * @author Admin
 *
 */
public class Processing {
	
	private static final String LOG_TAG="Processing";

	/**
	 * Get the local IP address
	 * @return
	 */
	public static String getLocalIpAddress() {
	    try {
	        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
	            NetworkInterface intf = en.nextElement();
	            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
	                InetAddress inetAddress = enumIpAddr.nextElement();
	                if (!inetAddress.isLoopbackAddress()) {
	                	String ip = Formatter.formatIpAddress(inetAddress.hashCode());
	                    return ip;//inetAddress.getHostAddress().toString();
	                }
	            }
	        }
	    } catch (SocketException ex) {
	        Log.e(LOG_TAG, ex.toString());
	    }
	    return null;
	}
	
	/**
	 * Returns byte array from bitmap 
	 * @param bmp Bitmap object
	 * @return returns byte[] 
	 */
	public static byte[] bitmapToByteArray(Bitmap bmp){
		if(bmp==null)
			return null;
		byte[] image = null;
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
		image = stream.toByteArray();
		try {
			stream.close();
		} catch (IOException e) {
			Log.i(LOG_TAG, "stream not closed");
			e.printStackTrace();
		}
		return image;
	}
	
	
	/**
	 * Calculating inSampleSize; powered by google: http://developer.android.com/training/displaying-bitmaps/load-bitmap.html
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

        final int halfHeight = height / 2;
        final int halfWidth = width / 2;

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while ((halfHeight / inSampleSize) > reqHeight
                && (halfWidth / inSampleSize) > reqWidth) {
            inSampleSize *= 2;
        }
    }

    return inSampleSize;
}
	
	
	/**
	 * Byte array to bitmap
	 * @param image
	 */
	public static Bitmap byteArrayToBitmap(byte[] image, int dimensionH, int dimensionW){
		if(image==null)
			return null;
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = Processing.calculateInSampleSize(options, dimensionW, dimensionH);
		return BitmapFactory.decodeByteArray(image, 0, image.length, options);
	}
}
