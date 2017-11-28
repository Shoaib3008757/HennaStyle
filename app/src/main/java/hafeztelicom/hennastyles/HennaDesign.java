package hafeztelicom.hennastyles;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import hafeztelicom.hennastyles.util.IabBroadcastReceiver;
import hafeztelicom.hennastyles.util.IabHelper;
import hafeztelicom.hennastyles.util.IabResult;
import hafeztelicom.hennastyles.util.Inventory;
import hafeztelicom.hennastyles.util.Purchase;

public class HennaDesign extends AppCompatActivity implements IabBroadcastReceiver.IabBroadcastListener{

    ImageView mehndiImage1 , mehndiImage2 , mehndiImage3, mehndiImage4, mehndiImage5, mehndiImage6, mehndiImage7
            , mehndiImage8, mehndiImage9, mehndiImage10;
    Bitmap image1 , image2 , image3 , image4 , image5 , image6 , image7 , image8 , image9 , image10;
    Bitmap blurredBitmap4 , blurredBitmap5  , blurredBitmap6 , blurredBitmap7 , blurredBitmap8 , blurredBitmap9 , blurredBitmap10;
    int boughtValue1 , boughtValue2   , boughtValue3 , boughtValue4 , boughtValue5
            , boughtValue6 , boughtValue7  , boughtValue8  , boughtValue9  , boughtValue10;
    private static final float BLUR_RADIUS = 20f;


    int mehndinumber = 0;

    IabHelper mHelper;
    String devPayLoad = "";
    private static final int IAPCODE = 10001;
    static final int RC_REQUEST = 10001;
    boolean mIsPremium = false;
    boolean mSubscribedToInfiniteGas = false;
    IabBroadcastReceiver mBroadcastReceiver;
    SharedPreferences sharedPreferences , spOnlyOnceRun;
    static final String SKU_mehndi1 = "mehndi1";
    static final String SKU_mehndi2 = "mehndi2";
    static final String SKU_mehndi3 = "mehndi3";
    static final String SKU_mehndi4 = "mehndi4";
    static final String SKU_mehndi5 = "mehndi5";
    static final String SKU_mehndi6 = "mehndi6";
    static final String SKU_mehndi7 = "mehndi7";

    static final String TAG = "HennaDesign";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_henna_design);



        initiate();
        setImageRoundCorner();
        imageClickListner();
        keyInitialization();
        startUpLab();
        blurImages();
        loadData();

    }

    public void initiate()
    {
        mehndiImage1 = (ImageView) findViewById(R.id.mehndiimage1);
        mehndiImage2 = (ImageView) findViewById(R.id.mehndiimage2);
        mehndiImage3 = (ImageView) findViewById(R.id.mehndiimage3);
        mehndiImage4 = (ImageView) findViewById(R.id.mehndiimage4);
        mehndiImage5 = (ImageView) findViewById(R.id.mehndiimage5);
        mehndiImage6 = (ImageView) findViewById(R.id.mehndiimage6);
        mehndiImage7 = (ImageView) findViewById(R.id.mehndiimage7);
        mehndiImage8 = (ImageView) findViewById(R.id.mehndiimage8);
        mehndiImage9 = (ImageView) findViewById(R.id.mehndiimage9);
        mehndiImage10 = (ImageView) findViewById(R.id.mehndiimage10);

    }


    public void setImageRoundCorner()
    {

         image1 =  BitmapFactory.decodeResource(getResources(), R.drawable.mehndi1);
         image2 =  BitmapFactory.decodeResource(getResources(), R.drawable.mehndi2);
         image3 =  BitmapFactory.decodeResource(getResources(), R.drawable.mehndi3);
         image4 =  BitmapFactory.decodeResource(getResources(), R.drawable.mehndi4);
         image5 =  BitmapFactory.decodeResource(getResources(), R.drawable.mehndi5);
         image6 =  BitmapFactory.decodeResource(getResources(), R.drawable.mehndi6);
         image7 =  BitmapFactory.decodeResource(getResources(), R.drawable.mehndi7);
         image8 =  BitmapFactory.decodeResource(getResources(), R.drawable.mehndi8);
         image9 =  BitmapFactory.decodeResource(getResources(), R.drawable.mehndi9);
         image10 =  BitmapFactory.decodeResource(getResources(), R.drawable.mehndi10);

         mehndiImage1.setImageBitmap(image1);
         mehndiImage2.setImageBitmap(image2);
         mehndiImage3.setImageBitmap(image3);
         mehndiImage4.setImageBitmap(image4);
         mehndiImage5.setImageBitmap(image5);
         mehndiImage6.setImageBitmap(image6);
         mehndiImage7.setImageBitmap(image7);
         mehndiImage8.setImageBitmap(image8);
         mehndiImage9.setImageBitmap(image9);
         mehndiImage10.setImageBitmap(image10);

//         roundImage1(image1);
//         roundImage2(image2);
//         roundImage3(image3);
//         roundImage4(image4);
//         roundImage5(image5);
//         roundImage6(image6);
//         roundImage7(image7);
//         roundImage8(image8);
//         roundImage9(image9);
//         roundImage10(image10);



    }


    public Bitmap blur(Bitmap image) {
        if (null == image) return null;

        Bitmap outputBitmap = Bitmap.createBitmap(image);
        final RenderScript renderScript = RenderScript.create(this);
        Allocation tmpIn = Allocation.createFromBitmap(renderScript, image);
        Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);

        //Intrinsic Gausian blur filter
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }

    public void blurImages()
    {
        blurredBitmap4 = blur(image4);
        blurredBitmap5 = blur(image5);
        blurredBitmap6 = blur(image6);
        blurredBitmap7 = blur(image7);
        blurredBitmap8 = blur(image8);
        blurredBitmap9 = blur(image9);
        blurredBitmap10 = blur(image10);

        mehndiImage4.setImageBitmap(blurredBitmap4);
        mehndiImage5.setImageBitmap(blurredBitmap5);
        mehndiImage6.setImageBitmap(blurredBitmap6);
        mehndiImage7.setImageBitmap(blurredBitmap7);
        mehndiImage8.setImageBitmap(blurredBitmap8);
        mehndiImage9.setImageBitmap(blurredBitmap9);
        mehndiImage10.setImageBitmap(blurredBitmap10);


    }
    public void roundImage1(Bitmap mbitmap)
    {
        Bitmap imageRounded = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas = new Canvas(imageRounded);
        Paint mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 10, 10, mpaint);// Round Image Corner 100 100 100 100
        mehndiImage1.setImageBitmap(imageRounded);

    }

    public void roundImage2(Bitmap mbitmap)
    {
        Bitmap imageRounded = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas = new Canvas(imageRounded);
        Paint mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 10, 10, mpaint);// Round Image Corner 100 100 100 100
        mehndiImage2.setImageBitmap(imageRounded);

    }

    public void roundImage3(Bitmap mbitmap)
    {
        Bitmap imageRounded = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas = new Canvas(imageRounded);
        Paint mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 10, 10, mpaint);// Round Image Corner 100 100 100 100
        mehndiImage3.setImageBitmap(imageRounded);

    }


    public void roundImage4(Bitmap mbitmap)
    {
        Bitmap imageRounded = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas = new Canvas(imageRounded);
        Paint mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 10, 10, mpaint);// Round Image Corner 100 100 100 100
        mehndiImage4.setImageBitmap(imageRounded);

    }


    public void roundImage5(Bitmap mbitmap)
    {
        Bitmap imageRounded = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas = new Canvas(imageRounded);
        Paint mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 10, 10, mpaint);// Round Image Corner 100 100 100 100
        mehndiImage5.setImageBitmap(imageRounded);

    }


    public void roundImage6(Bitmap mbitmap)
    {
        Bitmap imageRounded = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas = new Canvas(imageRounded);
        Paint mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 10, 10, mpaint);// Round Image Corner 100 100 100 100
        mehndiImage6.setImageBitmap(imageRounded);

    }


    public void roundImage7(Bitmap mbitmap)
    {
        Bitmap imageRounded = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas = new Canvas(imageRounded);
        Paint mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 10, 10, mpaint);// Round Image Corner 100 100 100 100
        mehndiImage7.setImageBitmap(imageRounded);

    }



    public void roundImage8(Bitmap mbitmap)
    {
        Bitmap imageRounded = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas = new Canvas(imageRounded);
        Paint mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 10, 10, mpaint);// Round Image Corner 100 100 100 100
        mehndiImage8.setImageBitmap(imageRounded);

    }



    public void roundImage9(Bitmap mbitmap)
    {
        Bitmap imageRounded = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas = new Canvas(imageRounded);
        Paint mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 10, 10, mpaint);// Round Image Corner 100 100 100 100
        mehndiImage9.setImageBitmap(imageRounded);

    }



    public void roundImage10(Bitmap mbitmap)
    {
        Bitmap imageRounded = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas = new Canvas(imageRounded);
        Paint mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 10, 10, mpaint);// Round Image Corner 100 100 100 100
        mehndiImage10.setImageBitmap(imageRounded);

    }

    public void imageClickListner()
    {
        mehndiImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HennaDesign.this , ViewOneMehndiDesign.class);
                Bundle imageBundle = new Bundle();
                imageBundle.putString("image1", "1");
                intent.putExtras(imageBundle);
                startActivity(intent);

            }
        });

        mehndiImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HennaDesign.this , ViewOneMehndiDesign.class);
                Bundle imageBundle = new Bundle();
                imageBundle.putString("image2", "2");
                intent.putExtras(imageBundle);
                startActivity(intent);

            }
        });

        mehndiImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HennaDesign.this , ViewOneMehndiDesign.class);
                Bundle imageBundle = new Bundle();
                imageBundle.putString("image3", "3");
                intent.putExtras(imageBundle);
                startActivity(intent);


            }
        });


        mehndiImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();


                if(boughtValue1 == 1)
                {
                Intent intent = new Intent(HennaDesign.this , ViewOneMehndiDesign.class);
                Bundle imageBundle = new Bundle();
                imageBundle.putString("image4", "4");
                intent.putExtras(imageBundle);
                startActivity(intent);
                }
                else {
                    mehndinumber = 1;
                    buyImageDialog();

                }



            }
        });


        mehndiImage5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();

                if(boughtValue2 == 2)
                {
                    Intent intent = new Intent(HennaDesign.this , ViewOneMehndiDesign.class);
                    Bundle imageBundle = new Bundle();
                    imageBundle.putString("image5", "5");
                    intent.putExtras(imageBundle);
                    startActivity(intent);
                }
                else {
                    mehndinumber = 2;
                    buyImageDialog();
                }
            }
        });


        mehndiImage6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadData();


                if(boughtValue3 == 3)
                {
                    Intent intent = new Intent(HennaDesign.this , ViewOneMehndiDesign.class);
                    Bundle imageBundle = new Bundle();
                    imageBundle.putString("image6", "6");
                    intent.putExtras(imageBundle);
                    startActivity(intent);
                }
                else {
                    mehndinumber = 3;
                    buyImageDialog();


                }

            }
        });



        mehndiImage7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadData();


                if(boughtValue4 == 4)
                {
                    Intent intent = new Intent(HennaDesign.this , ViewOneMehndiDesign.class);
                    Bundle imageBundle = new Bundle();
                    imageBundle.putString("image7", "7");
                    intent.putExtras(imageBundle);
                    startActivity(intent);
                }
                else {
                    mehndinumber = 4;
                    buyImageDialog();


                }

            }
        });


        mehndiImage8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadData();

                if(boughtValue5 == 5)
                {
                    Intent intent = new Intent(HennaDesign.this , ViewOneMehndiDesign.class);
                    Bundle imageBundle = new Bundle();
                    imageBundle.putString("image8", "8");
                    intent.putExtras(imageBundle);
                    startActivity(intent);
                }
                else {
                    mehndinumber = 5;
                    buyImageDialog();



                }


            }
        });


        mehndiImage9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//
                loadData();


                if(boughtValue6 == 6)
                {

                Intent intent = new Intent(HennaDesign.this , ViewOneMehndiDesign.class);
                Bundle imageBundle = new Bundle();
                imageBundle.putString("image9", "9");
                intent.putExtras(imageBundle);
                startActivity(intent);
                }
                else {
                    mehndinumber = 6;
                    buyImageDialog();


                }


            }
        });


        mehndiImage10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                loadData();

                if(boughtValue7 == 7)
                {
                    Intent intent = new Intent(HennaDesign.this , ViewOneMehndiDesign.class);
                    Bundle imageBundle = new Bundle();
                    imageBundle.putString("image10", "10");
                    intent.putExtras(imageBundle);
                    startActivity(intent);
                }
                else {
                    mehndinumber = 7;
                    buyImageDialog();


                }


            }
        });

    }

    public void buyImageDialog()
    {
        final Dialog dialog = new Dialog(HennaDesign.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.buyimagedialog);
        dialog.setTitle("Add Clause");

        Button buy_button =  (Button) dialog.findViewById(R.id.buy_button);
        Button later_button = (Button) dialog.findViewById(R.id.later_button);


        buy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(mehndinumber == 1)
                {
                    try {
                        dialog.dismiss();
                        buyMehndi1();
                        //onSuccessShirtBought();
                        Log.d("tag" ,"onSuccessShirtBought on button");
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

                if(mehndinumber == 2)
                {
                    try {
                        dialog.dismiss();
                        buyMehndi2();
                        //onSuccessShirtBought();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                if(mehndinumber == 3)
                {
                    try {
                        dialog.dismiss();
                        buyMehndi3();
                        //onSuccessShirtBought();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                if(mehndinumber == 4)
                {
                    try {
                        dialog.dismiss();
                        buyMehndi4();
                        //onSuccessShirtBought();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                if(mehndinumber == 5)
                {
                    try {
                        dialog.dismiss();
                        buyMehndi5();
                        //onSuccessShirtBought();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                if(mehndinumber == 6)
                {
                    try {
                        dialog.dismiss();
                        buyMehndi6();
                        //onSuccessShirtBought();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                if(mehndinumber == 7)
                {
                    try {
                        dialog.dismiss();
                        buyMehndi7();
                        //onSuccessShirtBought();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }


            }
        });

        later_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();

    }




    //hiding views when bought invertory successfull
    public void onSuccessShirtBought(){
        sharedPreferences = getSharedPreferences("FORSELLMEHNDI", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //start
        if (mehndinumber==1){
            //additing values to sharepreferences
            editor.putInt("MEHNDI1", 1);//int value 1 is for stargoggle

            mehndiImage4.setImageResource(R.drawable.mehndi4);
            Log.d("tag" , "mehndinumber4");
            //mehndiImage4.setVisibility(View.GONE);
        }//end
//start
        if (mehndinumber==2){
            //additing values to sharepreferences
            editor.putInt("MEHNDI2", 2);//int value 1 is for stargoggle

            mehndiImage5.setImageResource(R.drawable.mehndi5);
            Log.d("tag" , "mehndinumber5");

            //Shirt2.setVisibility(View.GONE);
        }//end
//start
        if (mehndinumber==3){
            //additing values to sharepreferences
            editor.putInt("MEHNDI3", 3);//int value 1 is for stargoggle
            //Shirt3.setVisibility(View.GONE);

            mehndiImage6.setImageResource(R.drawable.mehndi6);
            Log.d("tag" , "mehndinumber6");
        }//end
//start
        if (mehndinumber==4){
            //additing values to sharepreferences
            editor.putInt("MEHNDI4", 4);//int value 1 is for stargoggle
            //Shirt4.setVisibility(View.GONE);
            mehndiImage7.setImageResource(R.drawable.mehndi7);
            Log.d("tag" , "mehndinumber7");
        }//end
//start
        if (mehndinumber==5){
            //additing values to sharepreferences
            editor.putInt("MEHNDI5", 5);//int value 1 is for stargoggle
            //Shirt5.setVisibility(View.GONE);

            mehndiImage8.setImageResource(R.drawable.mehndi8);
            Log.d("tag" , "mehndinumber8");
        }//end
//start
        if (mehndinumber==6){
            //additing values to sharepreferences
            editor.putInt("MEHNDI6", 6);//int value 1 is for stargoggle
            //Shirt6.setVisibility(View.GONE);

            mehndiImage9.setImageResource(R.drawable.mehndi9);
            Log.d("tag" , "mehndinumber9");
        }//end
//start
        if (mehndinumber==7){
            //additing values to sharepreferences
            editor.putInt("MEHNDI7", 7);//int value 1 is for stargoggle
            //Shirt7.setVisibility(View.GONE);

            mehndiImage10.setImageResource(R.drawable.mehndi10);
            Log.d("tag" , "mehndinumber10");
        }//end
//start



        editor.commit();



    }//end of on Successfully Mehndi bought

    public void loadData(){
        sharedPreferences = getSharedPreferences("FORSELLMEHNDI", 0);

        if(sharedPreferences!=null) {
             boughtValue1 = sharedPreferences.getInt("MEHNDI1", 0);//defualt values is zero for all
             boughtValue2 = sharedPreferences.getInt("MEHNDI2", 0);
             boughtValue3 = sharedPreferences.getInt("MEHNDI3", 0);
             boughtValue4 = sharedPreferences.getInt("MEHNDI4", 0);
             boughtValue5 = sharedPreferences.getInt("MEHNDI5", 0);
             boughtValue6 = sharedPreferences.getInt("MEHNDI6", 0);
             boughtValue7 = sharedPreferences.getInt("MEHNDI7", 0);

            Log.d("tag" , "sharedPreferences 1 "+boughtValue1 );

            if (boughtValue1==1){
                mehndiImage4.setImageResource(R.drawable.mehndi4);
            }
            if (boughtValue2==2){
                mehndiImage5.setImageResource(R.drawable.mehndi5);

            }
            if (boughtValue3==3){
                mehndiImage6.setImageResource(R.drawable.mehndi6);

            }
            if (boughtValue4==4){
                mehndiImage7.setImageResource(R.drawable.mehndi7);

            }
            if (boughtValue5==5){
                mehndiImage8.setImageResource(R.drawable.mehndi8);

            }
            if (boughtValue6==6){
                mehndiImage9.setImageResource(R.drawable.mehndi9);

            }
            if (boughtValue7==7){
                mehndiImage10.setImageResource(R.drawable.mehndi10);

            }



        }


    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == IAPCODE )
        {
            android.util.Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
            if (mHelper == null) return;

            // Pass on the activity result to the helper for handling
            if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {




                super.onActivityResult(requestCode, resultCode, data);
            }
            else {
                android.util.Log.d(TAG, "onActivityResult handled by IABUtil.");
            }

        }



    }

    public void  keyInitialization(){
        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkKFGVC+SsJzzbSphEYe7q/dNQFo3xrTiqXvH8uj/gDrTM7tDmIWSPPjyON+eueb3zq+gfZDliBqKkFWy8CvS2J4XC6qd04qU/MYR+Gs3tQWoRqJnFqjukHRPuUtNFSXPqUxs8enXVOPswNOk38LfQmrmXKn4jyHA2fkX8SMdKALHr1MJoorG+LAWg/h8QZlEx3m4837mXachxClNmrpEgHDdWAuBpPU0rzRsQ/FfZdzpohZcuLGCEm39u7MIOQaCk07eFj4K/1Q1GmTj1vcfpUusilQCJzY2FtZzUeI8gkyin5zOMCyWQjEboUY+l2bbTOflorIme7xHF1ziBfW9awIDAQAB";

        // Some sanity checks to see if the developer (that's you!) really followed the
        // instructions to run this sample (don't put these checks on your app!)
        if (base64EncodedPublicKey.contains("CONSTRUCT_YOUR")) {
            throw new RuntimeException("Please put your app's public key in MainActivity.java. See README.");
        }
        if (getPackageName().startsWith("com.example")) {
            throw new RuntimeException("Please change the sample's package name! See README.");
        }

        // Create the helper, passing it our context and the public key to verify signatures with
        android.util.Log.d(TAG, "Creating IAB helper.");
        mHelper = new IabHelper(this, base64EncodedPublicKey);

        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(true);



    }//end of keyInitialization

    public void startUpLab(){
        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        android.util.Log.d(TAG, "Starting setup.");

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                android.util.Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    complain("Problem setting up in-app billing: " + result);
                    return;
                }

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null) return;

                mBroadcastReceiver = new IabBroadcastReceiver(HennaDesign.this);
                IntentFilter broadcastFilter = new IntentFilter(IabBroadcastReceiver.ACTION);
                registerReceiver(mBroadcastReceiver, broadcastFilter);

                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                android.util.Log.d(TAG, "Setup successful. Querying inventory.");
                try {
                    mHelper.queryInventoryAsync(mGotInventoryListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    complain("Error querying inventory. Another async operation in progress.");
                }
            }
        });


    }//end of startup lab

    @Override
    protected void onDestroy() {
        super.onDestroy();

        inAppBilling_onDestroy();

    }

    private static final char[] payloadSymbols = new char[36];

    static {
        for (int idx = 0; idx < 10; ++idx)
            payloadSymbols[idx] = (char) ('0' + idx);
        for (int idx = 10; idx < 36; ++idx)
            payloadSymbols[idx] = (char) ('a' + idx - 10);
    }


    public void inAppBilling_onDestroy()
    {

        // very important:
        if (mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver);
        }
        if (mHelper != null) try {
            mHelper.dispose();
            mHelper.disposeWhenFinished();
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
        mHelper = null;
    }

    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            android.util.Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            if (result.isFailure()) {
                complain("Error purchasing: " + result);
                setWaitScreen(false);
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                complain("Error purchasing. Authenticity verification failed.");
                setWaitScreen(false);
                return;
            }

            android.util.Log.d(TAG, "Purchase successful.");

            //*********************************** Method for mehndi *******************
            //***************mehndi 1*****************
            if (mehndinumber == 1) {
                if (purchase.getSku().equals(SKU_mehndi1)) {
                    // bought 1/4 tank of gas. So consume it.
                    android.util.Log.d(TAG, "Purchase is Star Goggle. Starting Goggle consumption.");
                    try {
                        mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming Goggle. Another async operation in progress.");
                        setWaitScreen(false);
                        return;
                    }
                }
            }//end of mehndi 1

            //***************mehndi 2*****************
            if (mehndinumber == 2) {
                if (purchase.getSku().equals(SKU_mehndi2)) {
                    // bought 1/4 tank of gas. So consume it.
                    android.util.Log.d(TAG, "Purchase is Star Goggle. Starting Goggle consumption.");
                    try {
                        mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming Goggle. Another async operation in progress.");
                        setWaitScreen(false);
                        return;
                    }
                }
            }//end of mehndi 2

            //***************mehndi 3*****************
            if (mehndinumber == 3) {
                if (purchase.getSku().equals(SKU_mehndi3)) {
                    // bought 1/4 tank of gas. So consume it.
                    android.util.Log.d(TAG, "Purchase is Star Goggle. Starting Goggle consumption.");
                    try {
                        mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming Goggle. Another async operation in progress.");
                        setWaitScreen(false);
                        return;
                    }
                }
            }//end of mehndi 3

            //***************mobile 4*****************
            if (mehndinumber == 4) {
                if (purchase.getSku().equals(SKU_mehndi4)) {
                    // bought 1/4 tank of gas. So consume it.
                    android.util.Log.d(TAG, "Purchase is Star Goggle. Starting Goggle consumption.");
                    try {
                        mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming Goggle. Another async operation in progress.");
                        setWaitScreen(false);
                        return;
                    }
                }
            }//end of mehndi 4

            //***************mobile 5*****************
            if (mehndinumber == 5) {
                if (purchase.getSku().equals(SKU_mehndi5)) {
                    // bought 1/4 tank of gas. So consume it.
                    android.util.Log.d(TAG, "Purchase is Star Goggle. Starting Goggle consumption.");
                    try {
                        mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming Goggle. Another async operation in progress.");
                        setWaitScreen(false);
                        return;
                    }
                }
            }//end of mehndi 5

            //***************mehndi 6*****************
            if (mehndinumber == 6) {
                if (purchase.getSku().equals(SKU_mehndi6)) {
                    // bought 1/4 tank of gas. So consume it.
                    android.util.Log.d(TAG, "Purchase is Star Goggle. Starting Goggle consumption.");
                    try {
                        mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming Goggle. Another async operation in progress.");
                        setWaitScreen(false);
                        return;
                    }
                }
            }//end of mehndi 6

            //***************mehndi 7*****************
            if (mehndinumber == 7) {
                if (purchase.getSku().equals(SKU_mehndi7)) {
                    // bought 1/4 tank of gas. So consume it.
                    android.util.Log.d(TAG, "Purchase is Star Goggle. Starting Goggle consumption.");
                    try {
                        mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming Goggle. Another async operation in progress.");
                        setWaitScreen(false);
                        return;
                    }
                }
            }//end of mehndi 7


            //********************************************* Ranglerz Team **********************************

        }
    };



    @Override
    public void receivedBroadcast() {
// Received a broadcast notification that the inventory of items has changed
        android.util.Log.d(TAG, "Received broadcast notification. Querying inventory.");
        try {
            mHelper.queryInventoryAsync(mGotInventoryListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            android.util.Log.e(TAG, "Error querying inventory. Another async operation in progress.");
        }
    }

    public class RandomString {

       /*
        * static { for (int idx = 0; idx < 10; ++idx) symbols[idx] = (char)
        * ('0' + idx); for (int idx = 10; idx < 36; ++idx) symbols[idx] =
        * (char) ('a' + idx - 10); }
        */


        private final Random random = new Random();

        private final char[] buf;

        public RandomString(int length) {
            if (length < 1)
                throw new IllegalArgumentException("length < 1: " + length);
            buf = new char[length];
        }

        public String nextString() {
            for (int idx = 0; idx < buf.length; ++idx)
                buf[idx] = payloadSymbols[random.nextInt(payloadSymbols.length)];
            return new String(buf);
        }

    }

    public final class SessionIdentifierGenerator {

        private SecureRandom random = new SecureRandom();

        public String nextSessionId() {
            return new BigInteger(130, random).toString(32);
        }

    }


    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            android.util.Log.d(TAG, "Query inventory finished.");

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
                complain("Failed to query inventory: " + result);
                return;
            }

            android.util.Log.d(TAG, "Query inventory was successful.");

            // Check for gas delivery -- if we own gas, we should fill up the tank immediately

            //************************************** for mehndi Perchase ********************************

            //************for mehndi 1 perchase*********************
            if (mehndinumber==1){
                Purchase gasPurchase = inventory.getPurchase(SKU_mehndi1);
                android.util.Log.d(TAG, "Inventory Purchase " + gasPurchase);

                if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
                    android.util.Log.d(TAG, "We have gas. Consuming it.");
                    try {
                        mHelper.consumeAsync(inventory.getPurchase(SKU_mehndi1), mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming gas. Another async operation in progress.");
                    }
                    return;
                }
            }//end for mehndi 1 perchase

            //************for mehndi 2 perchase*********************
            if (mehndinumber==2){
                Purchase gasPurchase = inventory.getPurchase(SKU_mehndi2);
                android.util.Log.d(TAG, "Inventory Purchase " + gasPurchase);

                if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
                    android.util.Log.d(TAG, "We have gas. Consuming it.");
                    try {
                        mHelper.consumeAsync(inventory.getPurchase(SKU_mehndi2), mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming gas. Another async operation in progress.");
                    }
                    return;
                }
            }//end for mehndi 2 perchase

            //************for mehndi 3 perchase*********************
            if (mehndinumber==3){
                Purchase gasPurchase = inventory.getPurchase(SKU_mehndi3);
                android.util.Log.d(TAG, "Inventory Purchase " + gasPurchase);

                if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
                    android.util.Log.d(TAG, "We have gas. Consuming it.");
                    try {
                        mHelper.consumeAsync(inventory.getPurchase(SKU_mehndi3), mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming gas. Another async operation in progress.");
                    }
                    return;
                }
            }//end for mehndi 3 perchase

//************for mehndi 4 perchase*********************
            if (mehndinumber==4){
                Purchase gasPurchase = inventory.getPurchase(SKU_mehndi4);
                android.util.Log.d(TAG, "Inventory Purchase " + gasPurchase);

                if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
                    android.util.Log.d(TAG, "We have gas. Consuming it.");
                    try {
                        mHelper.consumeAsync(inventory.getPurchase(SKU_mehndi4), mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming gas. Another async operation in progress.");
                    }
                    return;
                }
            }//end for mehndi 4 perchase

            //************for Mobile 5 perchase*********************
            if (mehndinumber==5){
                Purchase gasPurchase = inventory.getPurchase(SKU_mehndi5);
                android.util.Log.d(TAG, "Inventory Purchase " + gasPurchase);

                if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
                    android.util.Log.d(TAG, "We have gas. Consuming it.");
                    try {
                        mHelper.consumeAsync(inventory.getPurchase(SKU_mehndi5), mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming gas. Another async operation in progress.");
                    }
                    return;
                }
            }//end for mehndi 5 perchase

            //************for mehndi 6 perchase*********************
            if (mehndinumber==6){
                Purchase gasPurchase = inventory.getPurchase(SKU_mehndi6);
                android.util.Log.d(TAG, "Inventory Purchase " + gasPurchase);

                if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
                    android.util.Log.d(TAG, "We have gas. Consuming it.");
                    try {
                        mHelper.consumeAsync(inventory.getPurchase(SKU_mehndi6), mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming gas. Another async operation in progress.");
                    }
                    return;
                }
            }//end for mehndi 6 perchase

            //************for mehndi 7 perchase*********************
            if (mehndinumber==7){
                Purchase gasPurchase = inventory.getPurchase(SKU_mehndi7);
                android.util.Log.d(TAG, "Inventory Purchase " + gasPurchase);

                if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
                    android.util.Log.d(TAG, "We have gas. Consuming it.");
                    try {
                        mHelper.consumeAsync(inventory.getPurchase(SKU_mehndi7), mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming gas. Another async operation in progress.");
                    }
                    return;
                }
            }//end for mehndi 7 perchase




            setWaitScreen(false);
            android.util.Log.d(TAG, "Initial inventory query finished; enabling main UI.");
        }
    };

    // Called when consumption is complete
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            android.util.Log.d(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            // We know this is the "gas" sku because it's the only one we consume,
            // so we don't check which sku was consumed. If you have more than one
            // sku, you probably should check...
            if (result.isSuccess()) {
                // successfully consumed, so we apply the effects of the item in our
                // game world's logic, which in our case means filling the gas tank a bit
                android.util.Log.d(TAG, "Consumption successful. Provisioning.");
                //mTank = mTank == TANK_MAX ? TANK_MAX : mTank + 1;
                //saveData();
                alert("You have successfully bought Shirt Phone");

                onSuccessShirtBought();
//                DialogForCustomerInfo();



                //alert("You filled 1/4 tank. Your tank is now " + String.valueOf(mTank) + "/4 full!");
            }
            else {
                complain("Error while consuming: " + result);
            }
            //updateUi();

            setWaitScreen(false);
            android.util.Log.d(TAG, "End consumption flow.");
        }
    };


    void complain(String message) {
        android.util.Log.e(TAG, "**** TrivialDrive Error: " + message);
        alert("Error: " + message);
    }


    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        android.util.Log.d(TAG, "Showing alert dialog: " + message);
        bld.setCancelable(false);
        bld.create().show();
    }


    //************************************ Starts for methods for buy mehndi x ************************************

    //buy mehndi 1
    public void buyMehndi1(){
        if (mSubscribedToInfiniteGas) {
            complain("No need! You're subscribed to infinite gas. Isn't that awesome?");
            return;
        }


        // launch the gas purchase UI flow.
        // We will be notified of completion via mPurchaseFinishedListener
        setWaitScreen(true);
        android.util.Log.d(TAG, "Launching purchase flow for gas.");

        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = "";
        android.util.Log.e("testTag", "Buy Call");
        RandomString randomString = new RandomString(36);
        devPayLoad = randomString.nextString();
        payload = devPayLoad;

        try {
            mHelper.launchPurchaseFlow(this, SKU_mehndi1, RC_REQUEST,
                    mPurchaseFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress.");
            setWaitScreen(false);
        }
    }//end of mehndi1


    //buy mehndi 2
    public void buyMehndi2(){
        if (mSubscribedToInfiniteGas) {
            complain("No need! You're subscribed to infinite gas. Isn't that awesome?");
            return;
        }


        // launch the gas purchase UI flow.
        // We will be notified of completion via mPurchaseFinishedListener
        setWaitScreen(true);
        android.util.Log.d(TAG, "Launching purchase flow for gas.");

        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = "";
        android.util.Log.e("testTag", "Buy Call");
        RandomString randomString = new RandomString(36);
        devPayLoad = randomString.nextString();
        payload = devPayLoad;

        try {
            mHelper.launchPurchaseFlow(this, SKU_mehndi2, RC_REQUEST,
                    mPurchaseFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress.");
            setWaitScreen(false);
        }
    }//end of mehndi2

    //buy mehndi 3
    public void buyMehndi3(){
        if (mSubscribedToInfiniteGas) {
            complain("No need! You're subscribed to infinite gas. Isn't that awesome?");
            return;
        }


        // launch the gas purchase UI flow.
        // We will be notified of completion via mPurchaseFinishedListener
        setWaitScreen(true);
        android.util.Log.d(TAG, "Launching purchase flow for gas.");

        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = "";
        android.util.Log.e("testTag", "Buy Call");
        RandomString randomString = new RandomString(36);
        devPayLoad = randomString.nextString();
        payload = devPayLoad;

        try {
            mHelper.launchPurchaseFlow(this, SKU_mehndi3, RC_REQUEST,
                    mPurchaseFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress.");
            setWaitScreen(false);
        }
    }//end of mehndi3

    //buy mehndi 4
    public void buyMehndi4(){
        if (mSubscribedToInfiniteGas) {
            complain("No need! You're subscribed to infinite gas. Isn't that awesome?");
            return;
        }


        // launch the gas purchase UI flow.
        // We will be notified of completion via mPurchaseFinishedListener
        setWaitScreen(true);
        android.util.Log.d(TAG, "Launching purchase flow for gas.");

        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = "";
        android.util.Log.e("testTag", "Buy Call");
        RandomString randomString = new RandomString(36);
        devPayLoad = randomString.nextString();
        payload = devPayLoad;

        try {
            mHelper.launchPurchaseFlow(this, SKU_mehndi4, RC_REQUEST,
                    mPurchaseFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress.");
            setWaitScreen(false);
        }
    }//end of mehndi4

    //buy mehndi 5
    public void buyMehndi5(){
        if (mSubscribedToInfiniteGas) {
            complain("No need! You're subscribed to infinite gas. Isn't that awesome?");
            return;
        }


        // launch the gas purchase UI flow.
        // We will be notified of completion via mPurchaseFinishedListener
        setWaitScreen(true);
        android.util.Log.d(TAG, "Launching purchase flow for gas.");

        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = "";
        android.util.Log.e("testTag", "Buy Call");
        RandomString randomString = new RandomString(36);
        devPayLoad = randomString.nextString();
        payload = devPayLoad;

        try {
            mHelper.launchPurchaseFlow(this, SKU_mehndi5, RC_REQUEST,
                    mPurchaseFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress.");
            setWaitScreen(false);
        }
    }//end of mehndi5

    //buy mehndi 6
    public void buyMehndi6(){
        if (mSubscribedToInfiniteGas) {
            complain("No need! You're subscribed to infinite gas. Isn't that awesome?");
            return;
        }


        // launch the gas purchase UI flow.
        // We will be notified of completion via mPurchaseFinishedListener
        setWaitScreen(true);
        android.util.Log.d(TAG, "Launching purchase flow for gas.");

        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = "";
        android.util.Log.e("testTag", "Buy Call");
        RandomString randomString = new RandomString(36);
        devPayLoad = randomString.nextString();
        payload = devPayLoad;

        try {
            mHelper.launchPurchaseFlow(this, SKU_mehndi6, RC_REQUEST,
                    mPurchaseFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress.");
            setWaitScreen(false);
        }
    }//end of mehndi6

    //buy mehndi 7
    public void buyMehndi7(){
        if (mSubscribedToInfiniteGas) {
            complain("No need! You're subscribed to infinite gas. Isn't that awesome?");
            return;
        }


        // launch the gas purchase UI flow.
        // We will be notified of completion via mPurchaseFinishedListener
        setWaitScreen(true);
        android.util.Log.d(TAG, "Launching purchase flow for gas.");

        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = "";
        android.util.Log.e("testTag", "Buy Call");
        RandomString randomString = new RandomString(36);
        devPayLoad = randomString.nextString();
        payload = devPayLoad;

        try {
            mHelper.launchPurchaseFlow(this, SKU_mehndi7, RC_REQUEST,
                    mPurchaseFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress.");
            setWaitScreen(false);
        }
    }//end of mehndi7




    // Enables or disables the "please wait" screen.
    void setWaitScreen(boolean set) {

        Toast.makeText(getApplicationContext(), "Please Wait...", Toast.LENGTH_SHORT).show();

		/*findViewById(R.id.screen_main).setVisibility(set ? View.GONE : View.VISIBLE);
		findViewById(R.id.screen_wait).setVisibility(set ? View.VISIBLE : View.GONE);*/
    }

    /** Verifies the developer payload of a purchase. */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();

        /*
         * TODO: verify that the developer payload of the purchase is correct. It will be
         * the same one that you sent when initiating the purchase.
         *
         * WARNING: Locally generating a random string when starting a purchase and
         * verifying it here might seem like a good approach, but this will fail in the
         * case where the user purchases an item on one device and then uses your app on
         * a different device, because on the other device you will not have access to the
         * random string you originally generated.
         *
         * So a good developer payload has these characteristics:
         *
         * 1. If two different users purchase an item, the payload is different between them,
         *    so that one user's purchase can't be replayed to another user.
         *
         * 2. The payload must be such that you can verify it even when the app wasn't the
         *    one who initiated the purchase flow (so that items purchased by the user on
         *    one device work on other devices owned by the user).
         *
         * Using your own server to store and verify developer payloads across app
         * installations is recommended.
         */

        return true;
    }



}
