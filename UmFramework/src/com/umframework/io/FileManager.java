package com.umframework.io;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import android.text.TextUtils;

/**
 * 文件管理者
 * 
 * @author martin.zheng
 * 
 */
public class FileManager
{
	private FileManager()
	{

	}

	public static boolean isDirectory(File file)
	{
		if (file != null && file.exists())
		{
			return file.isDirectory();
		}
		return false;
	}

	public static boolean isDirectory(String path)
	{
		if (!TextUtils.isEmpty(path))
		{
			return isDirectory(new File(path));
		}
		return false;
	}

	public static File[] listFiles(File file)
	{
		if (file != null && file.exists() && file.isDirectory())
		{
			return file.listFiles();
		}
		return null;
	}

	public static File[] listFiles(File file, FileFilter fileFilter)
	{
		if (file != null && file.exists() && file.isDirectory())
		{
			return file.listFiles(fileFilter);
		}
		return null;
	}

	public static File[] listFiles(File file, FilenameFilter filenameFilter)
	{
		if (file != null && file.exists() && file.isDirectory())
		{
			return file.listFiles(filenameFilter);
		}
		return null;
	}

	public static File[] listFiles(String path)
	{
		if (!TextUtils.isEmpty(path))
		{
			return listFiles(new File(path));
		}
		return null;
	}

	public static File[] listFiles(String path, FileFilter fileFilter)
	{
		if (!TextUtils.isEmpty(path))
		{
			return listFiles(new File(path), fileFilter);
		}
		return null;
	}

	public static File[] listFiles(String path, FilenameFilter filenameFilter)
	{
		if (!TextUtils.isEmpty(path))
		{
			return listFiles(new File(path), filenameFilter);
		}
		return null;
	}

	public static boolean mkdir(String path)
	{
		if (!TextUtils.isEmpty(path))
		{
			return mkdir(new File(path));
		}
		return false;
	}

	/**
	 * 合并路径，失败返回null
	 * 
	 * @param pathBegin
	 * @param pathEnd
	 * @return
	 */
	public static String mkdir(String pathBegin, String pathEnd)
	{
		String path = null;
		boolean result = false;
		if (!TextUtils.isEmpty(pathBegin) && !TextUtils.isEmpty(pathEnd))
		{
			path = pathBegin + File.separator + pathEnd;
			result = mkdir(new File(path));
		}
		return result ? path : null;
	}

	public static boolean mkdir(File file)
	{
		if (file != null && !file.exists())
		{
			return file.mkdir();
		}
		return false;
	}

	public static boolean mkdirs(String path)
	{
		if (!TextUtils.isEmpty(path))
		{
			return mkdirs(new File(path));
		}
		return false;
	}

	/**
	 * 合并目录，失败返回null
	 * 
	 * @param pathBegin
	 * @param pathEnd
	 * @return
	 */
	public static String mkdirs(String pathBegin, String pathEnd)
	{
		String path = null;
		boolean result = false;
		if (!TextUtils.isEmpty(pathBegin) && !TextUtils.isEmpty(pathEnd))
		{
			path = pathBegin + File.separator + pathEnd;
			result = mkdirs(new File(path));
		}
		return result ? path : null;
	}

	public static boolean mkdirs(File file)
	{
		if (file != null && !file.exists())
		{
			return file.mkdirs();
		}
		return false;
	}

	public static boolean exists(String path)
	{
		if (!TextUtils.isEmpty(path))
		{
			return exists(new File(path));
		}
		return false;
	}

	public static boolean exists(File file)
	{
		if (file != null && file.exists())
		{
			return file.exists();
		}
		return false;
	}

	public static boolean delete(String path)
	{
		if (!TextUtils.isEmpty(path))
		{
			return delete(new File(path));
		}
		return false;
	}

	public static boolean delete(File file)
	{
		boolean result = false;
		try
		{
			if (file != null && file.exists())
			{
				result = file.delete();
			}
		}
		catch (Exception e)
		{
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	public static void clear(String path)
	{
		if (!TextUtils.isEmpty(path))
		{
			clear(new File(path));
		}
	}

	public static void clear(File file)
	{
		if (file != null && file.exists())
		{
			if (file.isFile())
			{
				delete(file);
			}
			else
			{
				File[] files = file.listFiles();
				if (files != null && files.length > 0)
				{
					for (File f : files)
					{
						clear(f);
					}
				}
			}
		}
	}

	public static void gzip(String pathIn, String pathOut) throws Exception
	{
		gzip(new File(pathIn), new File(pathOut));
	}

	public static void gzip(File fileIn, File fileOut) throws Exception
	{
		// 打开需压缩文件作为文件输入流
		FileInputStream fin = new FileInputStream(fileIn);
		// 建立压缩文件输出流
		FileOutputStream fout = new FileOutputStream(fileOut);
		// 建立gzip压缩输出流
		GZIPOutputStream gzout = new GZIPOutputStream(fout);
		// 设定读入缓冲区尺寸
		byte[] buf = new byte[1024];
		int num;
		while ((num = fin.read(buf)) != -1)
		{
			gzout.write(buf, 0, num);
		}
		gzout.close();
		fout.close();
		fin.close();
	}

	public static void unGzip(String pathIn, String pathOut) throws Exception
	{
		unGzip(new File(pathIn), new File(pathOut));
	}

	public static void unGzip(File fileIn, File fileOut) throws Exception
	{
		// 建立grip压缩文件输入流
		FileInputStream fin = new FileInputStream(fileIn);
		// 建立gzip解压工作流
		GZIPInputStream gzin = new GZIPInputStream(fin);
		// 建立解压文件输出流
		FileOutputStream fout = new FileOutputStream(fileOut);
		byte[] buf = new byte[1024];
		int num;
		while ((num = gzin.read(buf, 0, buf.length)) != -1)
		{
			fout.write(buf, 0, num);
		}

		gzin.close();
		fout.close();
		fin.close();
	}

	public static boolean copy(String fromPath, String toPath)
	{
		return copy(new File(fromPath), new File(toPath));
	}

	public static boolean copy(File fromFile, File toFile)
	{
		boolean result = false;

		try
		{
			FileInputStream fosfrom = new FileInputStream(fromFile);
			java.io.FileOutputStream fosto = new FileOutputStream(toFile);
			byte bt[] = new byte[1024];
			int c;
			while ((c = fosfrom.read(bt)) > 0)
			{
				fosto.write(bt, 0, c); // 将内容写到新文件当中
			}
			// 关闭数据流
			fosfrom.close();
			fosto.close();
			result = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = false;
		}
		return result;
	}
}
