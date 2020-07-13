package com.installerinfotech.pdfreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.itextpdf.text.pdf.PdfReader;
import com.shockwave.pdfium.PdfPasswordException;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;

public class ViewPDFFiles extends AppCompatActivity {

    PDFView pdfView;
    String path = "";
    String url = "";
    private InterstitialAd mInterstitialAd;
    String password = "";
    private NativeAppInstallAd intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_p_d_f_files);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        MobileAds.initialize(this,"ca-app-pub-6981204629834441~5150394602");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.fullscreen_ad));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });
        pdfView = (PDFView) findViewById(R.id.pdfView);
        path = getIntent().getStringExtra("PATH");

        password = getIntent().getStringExtra("Pass");

        displayPDF();

    }

    private void displayPDF() {

    File file = new File(path);
    String fileName = file.getName();
        TextView tv_fileName = (TextView)findViewById(R.id.tv_filename);
        tv_fileName.setText(fileName);
    if(password!=null && password.length()>0)
    {
        try {
            pdfView.fromFile(file)
                    .enableSwipe(true) // allows to block changing pages using swipe
                    .defaultPage(0)
                    .enableAnnotationRendering(true) // render annotations (such as comments, colors or forms)
                    .password(password)
                    .scrollHandle(new DefaultScrollHandle(this))
                    // improve rendering a little bit on low-res screens
                    // spacing between pages in dp. To define spacing color, set view background
                    .onError(new OnErrorListener() {
    @Override
    public void onError(Throwable t) {
        String msg = t.getMessage();
    }
})
                    .load();
//            String url = intent.getExtras().getString("userurl");
//            Intent Main = new Intent(this, ViewPDFFiles.class);
//            Main.putExtra("userurl", url);
//            startActivity(Main);
        }
        catch (Exception e)
        {

        }

    }
    else {
        pdfView.fromFile(file)
                .enableSwipe(true) // allows to block changing pages using swipe
                .defaultPage(0)
                .enableAnnotationRendering(true) // render annotations (such as comments, colors or forms)
                .scrollHandle(new DefaultScrollHandle(this))
                // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .load();
//        String url = intent.getExtras().getString("userurl");
//        Intent Main = new Intent(this, ViewPDFFiles.class);
//        Main.putExtra("userurl", url);
//        startActivity(Main);
        
    }
    }
    

    @Override
    public void onBackPressed() {
        if(mInterstitialAd!= null && mInterstitialAd.isLoaded())
            mInterstitialAd.show();
        super.onBackPressed();
    }
}



