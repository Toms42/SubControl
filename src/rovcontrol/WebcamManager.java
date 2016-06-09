package rovcontrol;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamException;
import com.github.sarxos.webcam.WebcamResolution;


public class WebcamManager
{
	
	public static volatile Webcam webcam;
	//IMediaWriter writer;
	File file;
	Thread r;
	
	volatile boolean recording = false;
	
	
	public void initWebcam()
	{
		try 
		{
			file = new File(Config.VIDEO_PATH);
			
			webcam = Webcam.getDefault();
			webcam.setViewSize(Config.RESOLUTION);
			Webcam.setAutoOpenMode(true); // WLS
			//writer = ToolFactory.makeWriter(file.getName());
		}
		catch (WebcamException e)
		{
			System.out.println("error initializing camera");
		}
	}
	
	public void startRecording()
	{
		recording = true;
		//record();
	}
	
	public void stopRecording()
	{
		System.out.println("Video recorded in file: " + file.getAbsolutePath());
		recording = false;
		
		// writer.close();
		
	}
	/*
	private void record()
	{
		// Codec was CODEC_ID_H264 -- WLS
		writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_WMV2, Config.RESOLUTION.width, Config.RESOLUTION.height);
		System.out.println("calling start " + Thread.currentThread().toString());
		r = new Thread()
		{
			int frameCount = 0;
			int i=0;
			long startTime = System.currentTimeMillis();
			public void run()
			{
				System.out.println("inside start " + Thread.currentThread().toString());
				while(recording)
				{
					if(i>20)
						i=0;
					// System.out.println("camera " + webcam.toString());
					BufferedImage rawimage = webcam.getImage(); // Note to Tom: The issue is that the webcam cannot be watched if it is "already disposed" by the JVM.
					// System.out.println("rawnull " + (rawimage==null));
					int imagekind = BufferedImage.TYPE_3BYTE_BGR;
					BufferedImage image = ConverterFactory.convertToType(rawimage, imagekind);
					IConverter converter = ConverterFactory.createConverter(image, IPixelFormat.Type.YUV420P);
		
					IVideoPicture frame = converter.toPicture(image, (System.currentTimeMillis() - startTime) * 1000);
					frame.setKeyFrame(i==0);
					// frame.setQuality(0);
					
					try {
						Thread.sleep(00);  // allow camera to initialize WLS
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					writer.encodeVideo(0, frame);
					
					// 25 FPS
					try {
						Thread.sleep(60);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					i++;
					frameCount++;
					//System.out.println("frame " + frameCount);
				}
				System.out.println("stopped recording at " + frameCount + "frames!");
				writer.close(); // WLS
			}
		};
		
		r.start();
		System.out.println("started recording!...");
	}
*/
}
