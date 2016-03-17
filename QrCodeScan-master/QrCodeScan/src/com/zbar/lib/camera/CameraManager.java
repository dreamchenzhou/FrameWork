package com.zbar.lib.camera;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * 
 * 描述: 相机管理
 */
public final class CameraManager
{
	private static final String TAG = CameraManager.class.getSimpleName();

	private static CameraManager cameraManager;

	static final int SDK_INT;
	static
	{
		int sdkInt;
		try
		{
			sdkInt = android.os.Build.VERSION.SDK_INT;
		}
		catch (NumberFormatException nfe)
		{
			sdkInt = 10000;
		}
		SDK_INT = sdkInt;
	}

	private final CameraConfigurationManager configManager;
	private Camera camera;
	private boolean initialized;
	private boolean previewing;
	private final boolean useOneShotPreviewCallback;
	private final PreviewCallback previewCallback;
	private final AutoFocusCallback autoFocusCallback;
	private Parameters parameter;

	public static void init(Context context)
	{
		// if (cameraManager == null)
		{
			cameraManager = new CameraManager(context);
		}
	}

	public static CameraManager get()
	{
		return cameraManager;
	}

	private CameraManager(Context context)
	{
		this.configManager = new CameraConfigurationManager(context);

		useOneShotPreviewCallback = SDK_INT > 3;
		previewCallback = new PreviewCallback(configManager, useOneShotPreviewCallback);
		autoFocusCallback = new AutoFocusCallback();
	}

	public void openDriver(SurfaceHolder holder) throws IOException
	{
		if (camera == null)
		{
			camera = Camera.open();
			if (camera == null)
			{
				throw new IOException();
			}
			camera.setPreviewDisplay(holder);

			if (!initialized)
			{
				initialized = true;
				configManager.initFromCameraParameters(camera);
			}
			configManager.setDesiredCameraParameters(camera);
			FlashlightManager.enableFlashlight();
			// FlashlightManager.turnLightOn(camera);
		}
	}

	public Point getCameraResolution()
	{
		return configManager.getCameraResolution();
	}

	public void closeDriver()
	{
		if (camera != null)
		{
			FlashlightManager.disableFlashlight();
			// FlashlightManager.turnLightOff(camera);
			camera.release();
			camera = null;
		}
	}

	public void startPreview()
	{
		if (camera != null && !previewing)
		{
			camera.startPreview();
			previewing = true;
		}
	}

	public void stopPreview()
	{
		if (camera != null && previewing)
		{
			if (!useOneShotPreviewCallback)
			{
				camera.setPreviewCallback(null);
			}
			camera.stopPreview();
			previewCallback.setHandler(null, 0);
			autoFocusCallback.setHandler(null, 0);
			previewing = false;
		}
	}

	public void requestPreviewFrame(Handler handler, int message)
	{
		if (camera != null && previewing)
		{
			previewCallback.setHandler(handler, message);
			if (useOneShotPreviewCallback)
			{
				camera.setOneShotPreviewCallback(previewCallback);
			}
			else
			{
				camera.setPreviewCallback(previewCallback);
			}
		}
	}

	public void requestAutoFocus(Handler handler, int message)
	{
		if (camera != null && previewing)
		{
			autoFocusCallback.setHandler(handler, message);
			camera.autoFocus(autoFocusCallback);
		}
	}

	public void openLight()
	{
		if (camera != null)
		{
			parameter = camera.getParameters();
			parameter.setFlashMode(Parameters.FLASH_MODE_TORCH);
			camera.setParameters(parameter);
		}
	}

	public void offLight()
	{
		if (camera != null)
		{
			parameter = camera.getParameters();
			parameter.setFlashMode(Parameters.FLASH_MODE_OFF);
			camera.setParameters(parameter);
		}
	}

	public static boolean isSupportedFlashModes(Camera mCamera)
	{
		boolean result = true;

		if (mCamera != null)
		{
			Parameters parameters = mCamera.getParameters();
			if (parameters == null)
			{
				result = false;
			}
			List<String> flashModes = parameters.getSupportedFlashModes();
			if (flashModes == null || flashModes.size() == 0)
			{
				result = false;
			}

			// String flashMode = parameters.getFlashMode();
		}
		return result;
	}

	/**
	 * 通过设置Camera打开闪光灯
	 * 
	 * @param mCamera
	 */
	public static void turnLightOn(Camera mCamera)
	{
		if (mCamera == null)
		{
			return;
		}
		Parameters parameters = mCamera.getParameters();
		if (parameters == null)
		{
			return;
		}
		List<String> flashModes = parameters.getSupportedFlashModes();
		// Check if camera flash exists
		if (flashModes == null)
		{
			// Use the screen as a flashlight (next best thing)
			return;
		}
		String flashMode = parameters.getFlashMode();
		Log.i(TAG, "Flash mode: " + flashMode);
		Log.i(TAG, "Flash modes: " + flashModes);
		if (!Parameters.FLASH_MODE_TORCH.equals(flashMode))
		{
			// Turn on the flash
			if (flashModes.contains(Parameters.FLASH_MODE_TORCH))
			{
				parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
				mCamera.setParameters(parameters);
			}
			else
			{
			}
		}
	}

	/**
	 * 通过设置Camera关闭闪光灯
	 * 
	 * @param mCamera
	 */
	public static void turnLightOff(Camera mCamera)
	{
		if (mCamera == null)
		{
			return;
		}
		Parameters parameters = mCamera.getParameters();
		if (parameters == null)
		{
			return;
		}
		List<String> flashModes = parameters.getSupportedFlashModes();
		String flashMode = parameters.getFlashMode();
		// Check if camera flash exists
		if (flashModes == null)
		{
			return;
		}
		Log.i(TAG, "Flash mode: " + flashMode);
		Log.i(TAG, "Flash modes: " + flashModes);
		if (!Parameters.FLASH_MODE_OFF.equals(flashMode))
		{
			// Turn off the flash
			if (flashModes.contains(Parameters.FLASH_MODE_OFF))
			{
				parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
				mCamera.setParameters(parameters);
			}
			else
			{
				Log.e(TAG, "FLASH_MODE_OFF not supported");
			}
		}
	}
}
