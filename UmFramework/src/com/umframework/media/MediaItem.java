package com.umframework.media;

import java.io.File;
import java.io.Serializable;

import android.text.TextUtils;

import com.umframework.io.FileManager;

/**
 * 
 * @author martin.zheng
 * 
 */
@SuppressWarnings("serial")
public class MediaItem implements Serializable
{
	private String id = "";
	private String displayName = "";
	private String path = "";
	private File file = null;
	private MediaTypeEnum mediaType = MediaTypeEnum.nothing;
	private boolean checked = false;
	private String fileUri;

	public MediaItem()
	{
	}

	public MediaItem(String path)
	{
		setPath(path);
	}

	public MediaItem(File File)
	{
		setFile(File);
	}

	public MediaItem(String path, MediaTypeEnum mediaType)
	{
		setPath(path);
		setMediaType(mediaType);
	}

	public MediaItem(File File, MediaTypeEnum mediaType)
	{
		setFile(File);
		setMediaType(mediaType);
	}

	public boolean exists()
	{
		return FileManager.exists(file);
	}

	public boolean delete()
	{
		return FileManager.delete(file);
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
		if (!TextUtils.isEmpty(path))
		{
			this.file = new File(path);
			this.displayName = file.getName();
		}
		else
		{
			this.file = null;
		}
	}

	public File getFile()
	{
		return file;
	}

	public void setFile(File file)
	{
		this.file = file;
		if (file != null)
		{
			this.path = file.getAbsolutePath();
			this.displayName = file.getName();
		}
		else
		{
			this.path = null;
		}
	}

	public MediaTypeEnum getMediaType()
	{
		return mediaType;
	}

	public void setMediaType(MediaTypeEnum mediaType)
	{
		this.mediaType = mediaType;
	}

	public long lastModified()
	{
		if (file != null && file.exists())
		{
			return file.lastModified();
		}
		return 0;
	}

	public boolean moveTo(String toPath)
	{
		return FileManager.copy(this.path, toPath);
	}

	public boolean moveTo(File toFile)
	{
		return FileManager.copy(this.file, toFile);
	}

	public boolean isChecked()
	{
		return checked;
	}

	public void setChecked(boolean checked)
	{
		this.checked = checked;
	}

	public String getFileUri()
	{
		if (TextUtils.isEmpty(fileUri))
		{
			fileUri = "file://" + path;
		}
		return fileUri;
	}
}
