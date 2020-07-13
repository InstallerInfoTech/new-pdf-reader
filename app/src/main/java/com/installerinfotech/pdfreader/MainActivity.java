package com.installerinfotech.pdfreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.shape.ShapeType;
import co.mobiwise.materialintro.view.MaterialIntroView;
import hotchemi.android.rate.AppRate;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.Formatter;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.Constants;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.itextpdf.text.exceptions.BadPasswordException;
import com.itextpdf.text.pdf.PdfReader;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView clrSearch;
    public static ArrayList<File> fileList = new ArrayList<>();
    private List<File> filelistfiltered;
    PDFAdapter obj_adapter;
    public static int REQUEST_PERMISSION = 1;
    boolean bolean_permission;
    File dir;
    private Toolbar toolbar;
    private EditText search;
    private AdView mAdView;
    //    private View.OnClickListener onClickListener = null;
    ListView swipeMenuListView;
//    ArrayList<Uri> fileUris = new ArrayList<Uri>();
    int backpress = 0;
    SwipeRefreshLayout swipeRefreshLayout;
    private boolean isPassVisible= false;
    private Bitmap.CompressFormat imagetype = Bitmap.CompressFormat.JPEG;
    private Handler handler;
    LinearLayout shareLinerLayout, deleteLinearLayout, renameLinearLayout;
    TextView option;
    FloatingActionButton floatingActionButton;
    //SearchView searchView;




    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onRestart() {
        super.onRestart();
        backpress = 0;
      //  getfile(dir);
        //SortFiles();
       // obj_adapter = new PDFAdapter(fileList);
        //swipeMenuListView.setAdapter(obj_adapter);
//ActionMenuItemView view = (ActionMenuItemView)findViewById(R.id.searchBtn);
//view.getItemData().collapseActionView();
//        if(searchView!=null) {
//            String query = searchView.getQuery().toString();
//            if (obj_adapter != null)
//                obj_adapter.getFilter().filter(query);
//        }
        if(passwordDialog!=null && passwordDialog.isShowing())
            passwordDialog.hide();
//        search.setText("");
        //SearchView searchView = (SearchView)findViewById(R.id.searchBtn);
        InputMethodManager imm = (InputMethodManager) getSystemService(MainActivity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        search.setText("");
        search.clearFocus();
        toolbar.setVisibility(View.GONE);
        clrSearch.setVisibility(View.GONE);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        floatingActionButton = (FloatingActionButton) findViewById(R.id.refreshBtn);
//        button1 = menu.findItem(R.id.searchBtn);
//        button1 = (Button) findViewById(R.id.searchBtn);
//        button2 = findViewById(R.id.optionmenu);
//        new MaterialIntroView.Builder(MainActivity.this)
//                .enableDotAnimation(false)
//                .enableIcon(false)
//                .setFocusGravity(FocusGravity.CENTER)
//                .setFocusType(Focus.NORMAL)
//                .setDelayMillis(1000)
//                .setIdempotent(true)
//                .enableFadeAnimation(true)
//                .performClick(true)
//                .setInfoText("Hi There! Click this button to refresh your list.")
//                .setShape(ShapeType.CIRCLE)
//                .setTarget(floatingActionButton)
//                .setUsageId("intro_card") //THIS SHOULD BE UNIQUE ID
//                .setMaskColor(getResources().getColor(R.color.colorShowcase))
//                .show();

//        new MaterialIntroView.Builder(this)
//                .enableDotAnimation(false)
//                .enableIcon(false)
//                .setFocusGravity(FocusGravity.CENTER)
//                .setFocusType(Focus.NORMAL)
//                .setDelayMillis(1000)
//                .setIdempotent(true)
//                .enableFadeAnimation(true)
//                .performClick(true)
//                .setInfoText("Hi There! Click this button to search your PDFs.")
//                .setShape(ShapeType.CIRCLE)
//                .setTarget(button1)
//                .setUsageId("intro_card2") //THIS SHOULD BE UNIQUE ID
//                .setMaskColor(getResources().getColor(R.color.colorShowcase))
//                .show();
//
//        new MaterialIntroView.Builder(this)
//                .enableDotAnimation(false)
//                .enableIcon(false)
//                .setFocusGravity(FocusGravity.CENTER)
//                .setFocusType(Focus.NORMAL)
//                .setDelayMillis(1000)
//                .setIdempotent(true)
//                .enableFadeAnimation(true)
//                .performClick(true)
//                .setInfoText("Hi There! Click this button to sort your PDFs.")
//                .setShape(ShapeType.CIRCLE)
//                .setTarget(button2)
//                .setUsageId("intro_card3") //THIS SHOULD BE UNIQUE ID
//                .setMaskColor(getResources().getColor(R.color.colorShowcase))
//                .show();

        InputMethodManager imm = (InputMethodManager) getSystemService(MainActivity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);


       // option = findViewById(R.id.opt_menu);
        toolbar = findViewById(R.id.toolbar4);
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
//        if (firstStart){
//            showDialog();
//
//        }


        AppRate.with(this)
                .setInstallDays(1)
                .setLaunchTimes(3)
                .setRemindInterval(3)
                .monitor();

        AppRate.showRateDialogIfMeetsConditions(this);
       //AppRate.with(this).showRateDialog(this);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


//

//        final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
//        View mview = getLayoutInflater().inflate(R.layout.rate_us, null);
//
//        Button rate = (Button) mview.findViewById(R.id.button);
//
//        alert.setView(mview);
//
//        final AlertDialog alertDialog = alert.create();
//        alertDialog.setCancelable(false);
//
//        rate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });



//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                search.setText("");
//                Toast.makeText(MainActivity.this, "Refreshing List...", Toast.LENGTH_SHORT).show();
//                refreshList();
//            }
//        });

        swipeMenuListView = (ListView) findViewById(R.id.listView_pdf);

//        swipeMenuListView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//                int lastItem = firstVisibleItem + visibleItemCount;
//                if (lastItem == totalItemCount) {
//
////                    floatingActionButton.setVisibility(View.INVISIBLE);
//                }else {
////                    floatingActionButton.setVisibility(View.VISIBLE);
//                }
//            }
//        });
       // MenuItem menuItem = (MenuItem)findViewById(R.id.demoa);
        //menuItem.setIcon(getResources().getDrawable(R.drawable.ic_sort));
        //imageView = findViewById(R.id.option);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.mygradient));
            actionBar.setTitle(Html.fromHtml("<font color='#000'>PDF Reader </font>"));


        }
//
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("\n" +
                "ca-app-pub-6981204629834441/3837312934");
//// TODO: Add adView to your view hierarchy.
//
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener(){
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



        //lv_pdf = (ListView)findViewById(R.id.listView_pdf);
        search = (EditText)findViewById(R.id.search);
        dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());




        permission_fn();


        swipeMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), ViewPDFFiles.class);
                String path = filelistfiltered.get(position).getPath();
                try {
                    PdfReader reader = new PdfReader(path);
                    intent.putExtra("PATH", filelistfiltered.get(position).getPath());
                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(search.getApplicationWindowToken(),0);
                    startActivity(intent);
                } catch (BadPasswordException e) {
                    openPasswordDialog(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        clrSearch = findViewById(R.id.clear_search);
        clrSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setText("");
                clrSearch.setVisibility(View.GONE);

            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String ss = search.getText().toString().trim();

                if (search.getText().length() > 0)
                {
                    clrSearch.setVisibility(View.VISIBLE);
                }
                else if (search.getText().length() == 0)
                {
                    clrSearch.setVisibility(View.GONE);
                }

                if (obj_adapter!=null)
                    obj_adapter.getFilter().filter(ss);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                getfile(dir);
//                SortFiles();
//                obj_adapter = new PDFAdapter(fileList);
//                swipeMenuListView.setAdapter(obj_adapter);
                refreshList();
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                 //create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0x00, 0x66,
                        0xff)));
                // set item width
                openItem.setWidth(200);
                // set item title
                openItem.setIcon(R.drawable.new26);
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                 //create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(200);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }

        };
//        swipeMenuListView.setMenuCreator(creator);
//        final AlertDialog[] alertDialog = new AlertDialog[1];
//        swipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
//                switch (index) {
//                    case 0:
//                        File path = new File(filelistfiltered.get(position).getAbsolutePath());
//                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//                        Uri fileUri = Uri.parse(String.valueOf(path));
//                        Uri uri = FileProvider.getUriForFile(getApplicationContext(), "com.pdfreader",path);
//                        sharingIntent.setType("pdf/*");
//                        //Uri uri = FileProvider.getUriForFile(,getApplicationContext().getPackageName()+".provider", path);
//                        sharingIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
//                        startActivity(Intent.createChooser(sharingIntent, "Share file using"));
//                        break;
//                    case 1:
//                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                        builder.setMessage("Are you sure you want to delete ?")
//                                .setCancelable(false)
//                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        File file = new File(filelistfiltered.get(position).getAbsolutePath());
//                                        file.delete();
//                                        final File pos = filelistfiltered.get(position);
//                                        String currentFilePath = filelistfiltered.get(position).getAbsolutePath();
//                                        filelistfiltered.remove(pos);
//                                        int fileListPos = -1;
//                                        for(int i = 0; i < fileList.size(); i++){
//                                            if(fileList.get(i).getAbsolutePath() == currentFilePath)
//                                            {
//                                                fileListPos = i;
//                                                break;
//                                            }
//                                        }
//                                        if(fileListPos !=-1)
//                                        {
//                                            fileList.remove(fileListPos);
//                                        }
//                                        obj_adapter.notifyDataSetChanged();
//                                        Snackbar.make(MainActivity.this.getWindow().getDecorView().getRootView(), " PDF Deleted", Snackbar.LENGTH_LONG).show();
//                                        alertDialog[0].hide();
//                                    }
//                                })
//                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        alertDialog[0].hide();
//                                    }
//                                });
//                        alertDialog[0] = builder.create();
//                        alertDialog[0].show();
//
//                        break;
//                }
//                // false : close the menu; true : not close the menu
//                return false;
//            }
//        });


    }

    public void refreshList(){

        Toast.makeText(MainActivity.this, "Refreshing", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                backpress = 0;
                getfile(dir);
                SortFiles();
                obj_adapter = new PDFAdapter(fileList);
                swipeMenuListView.setAdapter(obj_adapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);

        backpress = 0;
//        getfile(dir);
//        SortFiles();
//        obj_adapter = new PDFAdapter(fileList);
//        swipeMenuListView.setAdapter(obj_adapter);
//        if(passwordDialog!=null && passwordDialog.isShowing())
//            passwordDialog.hide();
        toolbar.setVisibility(View.GONE);
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(search.getApplicationWindowToken(),0);
    }

//    private void showDialog() {
//        new AlertDialog.Builder(this)
//                .setTitle("Welcome")
//                .setMessage("Explore your PDF files here...")
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                })
//                .create().show();
//        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putBoolean("firstStart", false);
//        editor.apply();
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuxml1,menu);
//        MenuItem menuItem = menu.findItem(R.id.searchBtn);
//        searchView = (SearchView) menuItem.getActionView();
//
//        searchView.setQueryHint("Search Here...");
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if (obj_adapter!=null)
//                    obj_adapter.getFilter().filter(newText);
//                return  true;
//            }
//        });
        return super.onCreateOptionsMenu(menu);
    }
    int sort = 2131361956;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        sort = id;
        switch (id){
            case R.id.id2:
                backpress = 0;
                Collections.sort(fileList, new nameAscending());
                obj_adapter.notifyDataSetChanged();
                break;
            case R.id.id3:
                backpress = 0;
                Collections.sort(fileList, new nameDescending());
                obj_adapter.notifyDataSetChanged();
                break;
            case R.id.id4:
                backpress = 0;
                Collections.sort(fileList, new dateAscending());
                obj_adapter.notifyDataSetChanged();
                break;
            case R.id.id5:
                backpress = 0;
                Collections.sort(fileList, new dateDescending());
                obj_adapter.notifyDataSetChanged();
                break;
            case R.id.id6:
                backpress = 0;
                Collections.sort(fileList, new sizeAscending());
                obj_adapter.notifyDataSetChanged();
                break;
            case R.id.id7:
                backpress = 0;
                Collections.sort(fileList, new sizeDescending());
                obj_adapter.notifyDataSetChanged();
                break;
            case R.id.searchBtn:
                if (toolbar.getVisibility() == toolbar.VISIBLE)
                {
                    backpress = 0;
                    toolbar.setVisibility(View.GONE);
                    search.clearFocus();
                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(search.getApplicationWindowToken(),0);

                }
                else {
                    backpress = 0;
                    toolbar.setVisibility(View.VISIBLE);
                    search.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                }


                break;


        }
        return super.onOptionsItemSelected(item);
    }

    private void SortFiles()
    {
        switch (sort){
            case R.id.id2:
                Collections.sort(fileList, new nameAscending());
                break;
            case R.id.id3:
                Collections.sort(fileList, new nameDescending());
                break;
            case R.id.id4:
                Collections.sort(fileList, new dateAscending());
                break;
            case R.id.id5:
                Collections.sort(fileList, new dateDescending());
                break;
            case R.id.id6:
                Collections.sort(fileList, new sizeAscending());
                break;
            case R.id.id7:
                Collections.sort(fileList, new sizeDescending());
                break;
        }
    }





    private class nameAscending implements Comparator<File> {

        @Override
        public int compare(File lhs, File rhs) {
            return lhs.getName().toLowerCase().compareTo(rhs.getName().toLowerCase());
        }
    }

    private class nameDescending implements Comparator<File>{

        @Override
        public int compare(File lhs, File rhs) {
            return rhs.getName().toLowerCase().compareTo(lhs.getName().toLowerCase());
        }
    }

    private class dateAscending implements Comparator<File>{

        @Override
        public int compare(File lhs, File rhs) {
            long k = lhs.lastModified() - rhs.lastModified();
            if (k>0) return 1;
            else if (k == 0) return 0;
            else return -1;
        }
    }

    private class dateDescending implements Comparator<File>{

        @Override
        public int compare(File lhs, File rhs) {
            long k = rhs.lastModified() - lhs.lastModified();
            if (k>0) return 1;
            else if (k == 0) return 0;
            else return -1;
        }
    }

    private class sizeAscending implements Comparator<File>{

        @Override
        public int compare(File lhs, File rhs) { return(lhs.length()> rhs.length() ?1 : -1);}
    }


    private class sizeDescending implements Comparator<File>{

        @Override
        public int compare(File lhs, File rhs) { return(lhs.length()> rhs.length() ?-1 : 1);}

    }

    public void onBackPressed() {
         backpress = (backpress + 1);
//        Toast.makeText(getApplicationContext(), " Press Back again to Exit ", Toast.LENGTH_SHORT).show();

        if (backpress == 1) {
            search.clearFocus();
            search.setText("");
            toolbar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Press Back again to Exit", Toast.LENGTH_SHORT).show();
        } else if (backpress > 1) {
            this.finish();

        }else {
            super.onBackPressed();
        }

    }
private Dialog passwordDialog;
    public void openPasswordDialog(final String path) {
        passwordDialog = new Dialog(MainActivity.this, R.style.Theme_Transparent);
        passwordDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        passwordDialog.setContentView(R.layout.rename);
        passwordDialog.setCancelable(true);
        passwordDialog.setCanceledOnTouchOutside(true);
        ImageView seePassword = (ImageView) passwordDialog.findViewById(R.id.see_pass);
        final EditText renameEditText = (EditText) passwordDialog.findViewById(R.id.create_directory);
        renameEditText.requestFocus();
        TextView cancel = (TextView) passwordDialog.findViewById(R.id.cancel_btn);
        TextView okTextView = (TextView) passwordDialog.findViewById(R.id.save_btn);
        renameEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(255)});
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if ( passwordDialog.isShowing()) passwordDialog.dismiss();
            }
        });
        final ImageView finalSeePassword = seePassword;
        seePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPassVisible = !isPassVisible;
                if (!isPassVisible) {
                    renameEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    finalSeePassword.setImageResource(R.drawable.hide_password);
                } else {
                    renameEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    finalSeePassword.setImageResource(R.drawable.see_password);
                }
                renameEditText.setSelection(renameEditText.getText().length());
            }
        });
        isPassVisible = false;
        renameEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        seePassword.setImageResource(R.drawable.hide_password);
        renameEditText.setText("");
        okTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = renameEditText.getText().toString().trim();
                if (password.length() > 0)
                {
                    try {

                  //      PdfReader reader = new PdfReader(path);
                        File file = new File(path);
                        // This is the PdfRenderer we use to render the PDF.
                        PDFView pdfView = findViewById(R.id.pdfView);
                        pdfView.fromFile(file)
                                .enableSwipe(true) // allows to block changing pages using swipe
                                .defaultPage(0)
                                .enableAnnotationRendering(true) // render annotations (such as comments, colors or forms)
                                .password(password)
                                .onLoad(new OnLoadCompleteListener() {
                                    @Override
                                    public void loadComplete(int nbPages) {
                                        Intent intent = new Intent(getApplicationContext(), ViewPDFFiles.class);
                                        intent.putExtra("PATH", path);
                                        intent.putExtra("Pass",renameEditText.getText().toString().trim());
                                        startActivity(intent);
                                        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                                        inputMethodManager.hideSoftInputFromWindow(renameEditText.getApplicationWindowToken(),0);

                                    }
                                })
                                // improve rendering a little bit on low-res screens
                                // spacing between pages in dp. To define spacing color, set view background
                                .onError(new OnErrorListener() {
                                    @Override
                                    public void onError(Throwable t) {
                                        String msg = t.getMessage();
                                        Toast.makeText(getApplicationContext(), "Invalid Password. Please enter correct password", Toast.LENGTH_LONG).show();

                                    }
                                }).load();


                    }
//                    catch (BadPasswordException e)
//                    {
//                        Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_LONG).show();
//                    }
                    catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_LONG).show();
                    }

                }
                else Toast.makeText(getApplicationContext(), "Please provide password", Toast.LENGTH_LONG).show();


            }
        });
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!passwordDialog.isShowing()) passwordDialog.show();
    }












    private void permission_fn() {

        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) ||
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
        ){

            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE))){
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_PERMISSION);
            }else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_PERMISSION);

            }

        }else {
            bolean_permission = true;
            getfile(dir);
            SortFiles();
            obj_adapter = new PDFAdapter(fileList);
            swipeMenuListView.setAdapter(obj_adapter);

        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION){

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                bolean_permission = true;
                getfile(dir);
                SortFiles();
                obj_adapter = new PDFAdapter( fileList);
                swipeMenuListView.setAdapter(obj_adapter);

            }

            else {
                Toast.makeText(this, "Please Allow the Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }



    public ArrayList<File> getfile(File dir){

        File listFile[] = dir.listFiles();
        if (listFile!=null && listFile.length>0){

            for (int i=0; i<listFile.length;i++){

                if (listFile[i].isDirectory()){

                    getfile(listFile[i]);
                }
                else {

                    boolean booleanpdf = false;
                    if (listFile[i].getName().toLowerCase().endsWith(".pdf")){

                        for (int j = 0;j<fileList.size();j++){

                            if (fileList.get(j).getName().equals(listFile[i].getName())){

                                booleanpdf = true;

                            }

                            else {


                            }

                        }

                        if (booleanpdf){
                            booleanpdf = false;
                        }
                        else {
                            fileList.add(listFile[i]);
                        }
                    }

                }
            }
        }

        return fileList;
    }
    public class PDFAdapter extends BaseAdapter implements Filterable {

        Context context;
        ViewHolder viewHolder;
        ArrayList<File> al_pdf;


        public PDFAdapter( ArrayList<File> al_pdf) {


            this.context = context;
            this.al_pdf = al_pdf;
            filelistfiltered = al_pdf;
        }



        @Override
        public int getCount() {
            return filelistfiltered.size();
        }

        @Override
        public Object getItem(int position)
        {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            if (filelistfiltered.size() > 0) {
                return filelistfiltered.size();
            } else {
                return 1;
            }
        }
        public void RefreshFilelist()
        {
              getfile(dir);
            SortFiles();
             obj_adapter = new PDFAdapter(fileList);
            swipeMenuListView.setAdapter(obj_adapter);
        }
        @Override
        public View getView(final int position, View view, final ViewGroup parent) {


            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.adapter_pdf, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.tv_filename = (TextView) view.findViewById(R.id.tv_name);
                viewHolder.filepath = (TextView) view.findViewById(R.id.tv_path);
                viewHolder.filedate = (TextView) view.findViewById(R.id.tv_date);
                viewHolder.filesize = (TextView) view.findViewById(R.id.tv_size);
                option = (TextView)view.findViewById(R.id.opt_menu);
                option.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        backpress = 0;
                        v = getLayoutInflater().inflate(R.layout.bottom_sheet, parent, false);
                        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(search.getApplicationWindowToken(),0);
                        final BottomSheetDialog bottomSheetDialog= new BottomSheetDialog(MainActivity.this, R.style.BottomSheet);
                        View bottomSheet =  LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet, (LinearLayout)findViewById(R.id.bottom_sheet_linear));
                        viewHolder = new ViewHolder();
                        viewHolder.tv_filename = (TextView) bottomSheet.findViewById(R.id.tv_nam_option);
                        viewHolder.tv_filename.setText(filelistfiltered.get(position).getName());
                        bottomSheet.findViewById(R.id.shareLinearLayout).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                File path = new File(filelistfiltered.get(position).getAbsolutePath());
                                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                Uri myUri = FileProvider.getUriForFile(MainActivity.this,getApplicationContext().getPackageName()+".provider",path);

                                sharingIntent.setDataAndType(myUri, "*/*");
                                sharingIntent.putExtra(Intent.EXTRA_STREAM,myUri);
                                sharingIntent.putExtra(Intent.EXTRA_SUBJECT,"Shared using PDF Reader");
                                sharingIntent.putExtra(Intent.EXTRA_TEXT, "Download PDF READER: https://play.google.com/store/apps/details?id=com.installerinfotech.pdfreader");
//                                Uri fileUri = Uri.parse(String.valueOf(path));
//                                Uri uri = FileProvider.getUriForFile(getApplicationContext(), "com.pdfreader",path);
//                                sharingIntent.setType("pdf/*");
//                                //Uri uri = FileProvider.getUriForFile(,getApplicationContext().getPackageName()+".provider", path);
//                                sharingIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
                                startActivity(sharingIntent.createChooser(sharingIntent, "Share file using"));
                                bottomSheetDialog.dismiss();

                            }
                        });

                        bottomSheet.findViewById(R.id.deleteLinearLayout).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final AlertDialog[] alertDialog = new AlertDialog[1];
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("Are you sure you want to delete ?")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                File file = new File(filelistfiltered.get(position).getAbsolutePath());
                                                file.delete();
                                                final File pos = filelistfiltered.get(position);
                                                String currentFilePath = filelistfiltered.get(position).getAbsolutePath();
                                                filelistfiltered.remove(pos);
                                                int fileListPos = -1;
                                                for(int i = 0; i < fileList.size(); i++){
                                                    if(fileList.get(i).getAbsolutePath() == currentFilePath)
                                                    {
                                                        fileListPos = i;
                                                        break;
                                                    }
                                                }
                                                if(fileListPos !=-1)
                                                {
                                                    fileList.remove(fileListPos);
                                                }
                                                obj_adapter.notifyDataSetChanged();
                                                Snackbar.make(MainActivity.this.getWindow().getDecorView().getRootView(), " PDF Deleted", Snackbar.LENGTH_LONG).show();
                                                alertDialog[0].hide();
                                            }
                                        })
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                alertDialog[0].hide();
                                            }
                                        });
                                alertDialog[0] = builder.create();
                                alertDialog[0].show();
                                bottomSheetDialog.dismiss();

                            }
                        });

                        bottomSheet.findViewById(R.id.renameLinearLayout).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final AlertDialog.Builder renameDialog =
                                        new AlertDialog.Builder(MainActivity.this);
                                renameDialog.setTitle("Rename to:");
                                renameDialog.setMessage("Enter your file name only..");
                                final File file = new File(filelistfiltered.get(position).getAbsolutePath());
                                final EditText input = new EditText(MainActivity.this);
                                final String renamePath = String.valueOf(file);
                                final String filename =file.getName().substring(0,file.getName().lastIndexOf("."));
                                //input.setText(renamePath.substring(renamePath.lastIndexOf("/")));
                                input.setText(filename);

                                input.setInputType(InputType.TYPE_CLASS_TEXT);
                                input.requestFocus();
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                                input.selectAll();
                                renameDialog.setView(input);
                                final boolean[] isInputError = {false};
                                renameDialog.setPositiveButton("Rename", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        String s = new File(renamePath).getParent()+ "/" + input.getText()+ ".pdf";
                                        boolean isExists = false;
                                        for(int i=0;i<fileList.size();i++)
                                        {
                                            String filename = fileList.get(i).getAbsolutePath();
                                            //String name1 = filename.substring(filename.lastIndexOf('/') + 1);
                                            //String nameOnly = name1.substring(0,name1.lastIndexOf('.'));

                                            if(filename.trim().toLowerCase().equals(s.trim().toLowerCase()) && i!=position)
                                            {
                                                isExists = true;
                                                break;
                                            }
                                        }
                                        if(isExists)
                                        {
                                            Toast.makeText(MainActivity.this, "File name already exists. Please enter another name", Toast.LENGTH_SHORT).show();
                                            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                            inputMethodManager.hideSoftInputFromWindow(input.getApplicationWindowToken(), 0);
                                            bottomSheetDialog.dismiss();
                                        }
                                        else
                                        {
                                            File newFile = new File(s);
                                            //new File(renamePath).renameTo(newFile);


                                            filelistfiltered.get(position).renameTo(newFile);
                                            filelistfiltered.set(position, newFile);
                                            search.setText(input.getText());
                                            Toast.makeText(MainActivity.this, "Renamed Successfully", Toast.LENGTH_SHORT).show();
                                            //refreshrename();
                                            RefreshFilelist();
                                            filelistfiltered.remove(file);
                                            obj_adapter.notifyDataSetChanged();
                                            RefreshFilelist();
                                            //swipeMenuListView.setSelection(position);
                                            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                            inputMethodManager.hideSoftInputFromWindow(input.getApplicationWindowToken(), 0);
                                            bottomSheetDialog.dismiss();
                                        }
                                    }
                                });
                                renameDialog.show();

                            }
                        });

                        bottomSheetDialog.setContentView(bottomSheet);
                        bottomSheetDialog.show();

                    }
                });
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();

            }
            String filedate = new SimpleDateFormat("dd MMM, yyyy h:mm a", Locale.ENGLISH).format(new Date(filelistfiltered.get(position).lastModified()));
            viewHolder.tv_filename.setText(filelistfiltered.get(position).getName());
            viewHolder.filepath.setText(filelistfiltered.get(position).getPath());
            viewHolder.filedate.setText("" + filedate);
            viewHolder.filesize.setText(Formatter.formatShortFileSize(MainActivity.this , filelistfiltered.get(position).length()));

            return view;

        }

//        public void refreshrename(){
////            getfile(dir);
////            SortFiles();
////            obj_adapter = new PDFAdapter(fileList);
////            swipeMenuListView.setAdapter(obj_adapter);
//            if(passwordDialog!=null && passwordDialog.isShowing())
//                passwordDialog.hide();
//            renamePdf();
//        }

//        public void renamePdf(){
//            getfile(dir);
//            SortFiles();
//            obj_adapter = new PDFAdapter(fileList);
//            swipeMenuListView.setAdapter(obj_adapter);
//            //refreshList();
//            String ss = search.getText().toString().trim();
//            if (obj_adapter!=null)
//                obj_adapter.getFilter().filter(ss);
//        }

        public class ViewHolder {

            TextView tv_filename, filepath,filedate,filesize, tv_filename_option;


        }
        @Override
        public Filter getFilter(){
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    String charString = constraint.toString();
                    if (charString.isEmpty())
                        filelistfiltered = al_pdf;
                    else {
                        List<File> filteredList = new ArrayList<>();
                        for (File row:al_pdf){
                            if (row.getName().toLowerCase().contains(charString.toLowerCase()))
                                filteredList.add(row);
                        }
                        filelistfiltered= filteredList;

                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = filelistfiltered;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    filelistfiltered = (ArrayList<File>) results.values;
                    notifyDataSetChanged();

                }
            };
        }
    }

}
