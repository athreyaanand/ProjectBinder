package com.notey.projectbinder;

        import android.app.Activity;
        import android.content.res.AssetManager;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.SurfaceView;
        import android.view.WindowManager;
        import android.widget.TextView;

        import org.opencv.android.BaseLoaderCallback;
        import org.opencv.android.CameraBridgeViewBase;
        import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
        import org.opencv.android.LoaderCallbackInterface;
        import org.opencv.android.OpenCVLoader;
        import org.opencv.android.Utils;
        import org.opencv.core.Core;
        import org.opencv.core.DMatch;
        import org.opencv.core.Mat;
        import org.opencv.core.MatOfByte;
        import org.opencv.core.MatOfDMatch;
        import org.opencv.core.MatOfKeyPoint;
        import org.opencv.core.MatOfPoint;
        import org.opencv.core.MatOfPoint2f;
        import org.opencv.core.Scalar;
        import org.opencv.features2d.DescriptorExtractor;
        import org.opencv.features2d.DescriptorMatcher;
        import org.opencv.features2d.FeatureDetector;
        import org.opencv.features2d.Features2d;
        import org.opencv.imgproc.Imgproc;

        import java.io.IOException;
        import java.io.InputStream;
        import java.util.ArrayList;
        import java.util.LinkedList;
        import java.util.List;


public class SandboxActivity extends Activity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG = "OCVSample::Activity";
    private int w, h;
    private CameraBridgeViewBase mOpenCvCameraView;
    TextView tvName;
    Scalar RED = new Scalar(255, 0, 0);
    Scalar GREEN = new Scalar(0, 255, 0);
    FeatureDetector detector;
    Mat descriptors2,descriptors1;
    Mat img1;
    MatOfKeyPoint keypoints1,keypoints2;

    static {
        if (!OpenCVLoader.initDebug())
            Log.d("ERROR", "Unable to load OpenCV");
        else
            Log.d("SUCCESS", "OpenCV loaded");
    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                    try {
                        initializeOpenCVDependencies();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    private void initializeOpenCVDependencies() throws IOException {
        mOpenCvCameraView.enableView();
        detector = FeatureDetector.create(FeatureDetector.ORB);

    }


    public SandboxActivity() {

        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_sandbox);
        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.tutorial1_activity_java_surface_view);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
        //tvName = (TextView) findViewById(R.id.text1);

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {
        w = width;
        h = height;
    }

    public void onCameraViewStopped() {
    }

    public Mat recognize(Mat aInputFrame) {
        Mat grayScale = new Mat();
        Imgproc.cvtColor(aInputFrame, grayScale, Imgproc.COLOR_RGB2GRAY);
        Scalar lowerThreshold = new Scalar ( 200, 200, 200 );
        Scalar upperThreshold = new Scalar ( 255, 255, 255 );
        Mat maskMat = new Mat();
        Core.inRange ( grayScale, lowerThreshold , upperThreshold, maskMat );
        Mat dilatedMat = new Mat();
        Imgproc.dilate ( maskMat, dilatedMat, new Mat() );
        ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours ( dilatedMat, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE );
        MatOfPoint[] contours_poly = new MatOfPoint[contours.size()];
        for ( int i=0; i < contours.size(); i++ ) {
            Imgproc.approxPolyDP((MatOfPoint2f) contours.get(i), (MatOfPoint2f) contours_poly[i], 3, true );
            Imgproc.drawContours (aInputFrame, contours, i, new Scalar (0, 255, 0), 5);
        }
        double epsilon = 0.1*
        Imgproc.boundingRect(contours);
        Imgproc.contourArea(dilatedMat);

        return dilatedMat;
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        return recognize(inputFrame.rgba());

    }
}
