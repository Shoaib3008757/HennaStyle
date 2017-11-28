package hafeztelicom.hennastyles;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class ViewOneMehndiDesign extends AppCompatActivity {

    TouchImageView touchImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_one_mehndi_design);
        initiate();
        setImage();
    }

    public void initiate()
    {
        //viewFullImage = (ImageView) findViewById(R.id.viewfullimage);
        touchImageView = (TouchImageView) findViewById(R.id.viewfullimage);

//        RotateAnimation rotateAnimation = new RotateAnimation(2010.0f, 0.0f,
//                Animation.RELATIVE_TO_PARENT, 0.5f,
//                Animation.RELATIVE_TO_PARENT, 0.5f);
//
//        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
//                Animation.RELATIVE_TO_PARENT, 0.5f,
//                Animation.RELATIVE_TO_PARENT, 0.5f);
//
//
//        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
//
//        AnimationSet animationSet = new AnimationSet(true);
//        animationSet.addAnimation(rotateAnimation);
//        animationSet.addAnimation(scaleAnimation);
//        animationSet.addAnimation(alphaAnimation);
//        animationSet.setDuration(3999);
//        animationSet.setInterpolator(new OvershootInterpolator());
//        touchImageView.startAnimation(animationSet);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(ViewOneMehndiDesign.this ,R.color.colorPrimaryDark)));
    }

    public void setImage()
    {

        Bundle viewImage1 =  getIntent().getExtras();
        Bundle viewImage2 =  getIntent().getExtras();
        Bundle viewImage3 =  getIntent().getExtras();
        Bundle viewImage4 =  getIntent().getExtras();
        Bundle viewImage5 =  getIntent().getExtras();
        Bundle viewImage6 =  getIntent().getExtras();
        Bundle viewImage7 =  getIntent().getExtras();
        Bundle viewImage8 =  getIntent().getExtras();
        Bundle viewImage9 =  getIntent().getExtras();
        Bundle viewImage10 = getIntent().getExtras();


        if (viewImage1!=null) {
            if(viewImage1.containsKey("image1")) {
                touchImageView.setImageResource(R.drawable.mehndi1);
            }
        }
        if (viewImage2!=null) {
            if(viewImage2.containsKey("image2")) {
                touchImageView.setImageResource(R.drawable.mehndi2);
            }
        }
        if (viewImage3!=null) {
            if(viewImage3.containsKey("image3")) {
                touchImageView.setImageResource(R.drawable.mehndi3);
            }
        }
        if (viewImage4!=null) {
            if(viewImage4.containsKey("image4")) {
                touchImageView.setImageResource(R.drawable.mehndi4);
            }
        }
        if (viewImage5!=null) {
            if(viewImage5.containsKey("image5")) {
                touchImageView.setImageResource(R.drawable.mehndi5);
            }
        }
        if (viewImage6!=null) {
            if(viewImage6.containsKey("image6")) {
                touchImageView.setImageResource(R.drawable.mehndi6);
            }
        }
        if (viewImage7!=null) {
            if(viewImage7.containsKey("image7")) {
                touchImageView.setImageResource(R.drawable.mehndi7);
            }
        }
        if (viewImage8!=null) {
            if(viewImage8.containsKey("image8")) {
                touchImageView.setImageResource(R.drawable.mehndi8);
            }
        }
        if (viewImage9!=null) {
            if(viewImage9.containsKey("image9")) {
                touchImageView.setImageResource(R.drawable.mehndi9);
            }
        }
        if (viewImage10!=null) {
            if(viewImage10.containsKey("image10")) {
                touchImageView.setImageResource(R.drawable.mehndi10);
            }
        }
    }
}
