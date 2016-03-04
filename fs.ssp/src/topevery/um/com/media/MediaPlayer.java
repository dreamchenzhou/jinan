package topevery.um.com.media;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import topevery.android.framework.utils.TextUtils;
import topevery.um.com.utils.PathUtils;
import android.content.Context;
import android.media.AudioManager;

public class MediaPlayer implements android.media.MediaPlayer.OnErrorListener
{
	private android.media.MediaPlayer mp = new android.media.MediaPlayer();
	private FileInputStream mFIS;
	private FileDescriptor mFD;

	private static MediaPlayer instance = null;

	private MediaPlayer()
	{

	}

	public static MediaPlayer getInstance()
	{
		if (instance == null)
		{
			synchronized (MediaPlayer.class)
			{
				if (instance == null)
				{
					instance = new MediaPlayer();
				}
			}
		}
		return instance;
	}

	public void onCreate(Context mContext)
	{
		AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
		audioManager.setMode(AudioManager.MODE_NORMAL);
		if (audioManager.isSpeakerphoneOn())
		{
			audioManager.setSpeakerphoneOn(true);
		}
		else
		{
			audioManager.setSpeakerphoneOn(false);
		}
		mp.setAudioStreamType(AudioManager.STREAM_RING);
		mp.setOnErrorListener(this);
	}

	@Override
	public boolean onError(android.media.MediaPlayer mp, int what, int extra)
	{
		return false;
	}

	public void play(File file)
	{
		try
		{
			mp.stop();
			mp.reset();
			mFIS = new FileInputStream(file);
			mFD = mFIS.getFD();
			mp.setDataSource(mFD);
			mp.prepare();
			mp.start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (mFIS != null)
				{
					mFIS.close();
					mFIS = null;
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void setOnCompletionListener(android.media.MediaPlayer.OnCompletionListener listener)
	{
		mp.setOnCompletionListener(listener);
	}

	public void reset()
	{
		mp.reset();
	}

	public void release()
	{
		mp.release();
	}

	public void stop()
	{
		mp.stop();
	}

	public void start()
	{
		mp.start();
	}

	public void prepare()
	{
		try
		{
			mp.prepare();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void pause()
	{
		mp.pause();
	}

	public void isPlaying()
	{
		mp.isPlaying();
	}

	/** 删除录音文件，超过一定天数的文件 */
	public void clearRecords()
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				doClearRecords();
			}
		}).start();
	}

	/** 删除录音文件，超过一定天数的文件 */
	private void doClearRecords()
	{
		long nowModified = new Date().getTime();
		long days = 7 * 24 * 60 * 60 * 1000;
		try
		{
			String dir = PathUtils.getRecordsPath();
			if (!TextUtils.isEmpty(dir))
			{
				File[] files = new File(dir).listFiles();
				if (files != null && files.length > 0)
				{
					for (File file : files)
					{
						if (nowModified - file.lastModified() >= days)
						{
							PathUtils.delete(file);
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
