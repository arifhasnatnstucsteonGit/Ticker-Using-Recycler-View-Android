package com.example.bs058.myapplication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AutoScroll extends AppCompatActivity {


    @BindView(R.id.rec_scroll_stock)
    RecyclerView rec_scroll_stock;

    List<StockListModel> stockListModels = new ArrayList<>();
    //private ScrollStockAdapter scrollStockAdapter;
    private ScrollCustomAdapter scrollStockAdapter;
    StockListModel model = new StockListModel();

    //new count added
    int scrollCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_scroll);
        ButterKnife.bind(this);

        //Dummy Value
        model.setAskerName("Ali Ahmed");
        model.setBidderName("Tanzim");
        model.setId("abc");
        model.setType("BUY");
        model.setIsSyncedWithServer(true);
        model.setTransactionTime("12/12/2016");
        model.setShortName("ABC");
        model.setShareQuantity(10);

        //add to the list
        stockListModels.add(model);
        stockListModels.add(model);
        stockListModels.add(model);
        stockListModels.add(model);
        stockListModels.add(model);
        stockListModels.add(model);
        stockListModels.add(model);
        stockListModels.add(model);
        stockListModels.add(model);
        stockListModels.add(model);
        stockListModels.add(model);
        stockListModels.add(model);
        stockListModels.add(model);
        stockListModels.add(model);
        stockListModels.add(model);
        stockListModels.add(model);
        stockListModels.add(model);
        stockListModels.add(model);
        stockListModels.add(model);
        stockListModels.add(model);
    }



    @Override
    protected void onResume() {
        super.onResume();
        scrollStockAdapter = new ScrollCustomAdapter(this,stockListModels) {
            @Override
            public void load() {
                stockListModels.addAll(stockListModels);
            }
        };

        LinearLayoutManager layoutManager = new LinearLayoutManager(AutoScroll.this) {

            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(AutoScroll.this) {
                    private static final float SPEED = 2000f;// Change this value (default=25f)
                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return SPEED / displayMetrics.densityDpi;
                    }
                };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }
        };
        autoScrollAnother();
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rec_scroll_stock.setLayoutManager(layoutManager);
        rec_scroll_stock.setHasFixedSize(true);
        rec_scroll_stock.setItemViewCacheSize(10);
        rec_scroll_stock.setDrawingCacheEnabled(true);
        rec_scroll_stock.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        rec_scroll_stock.setAdapter(scrollStockAdapter);
    }

    //old auto scroll
    public void autoScroll(){
        final int speedScroll = 1000;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                if(count == scrollStockAdapter.getItemCount())
                    count =0;
                if(count < scrollStockAdapter.getItemCount()){
                    rec_scroll_stock.smoothScrollToPosition(++count);
                    handler.postDelayed(this,speedScroll);
                }
            }
        };
        handler.postDelayed(runnable,speedScroll);
    }

    //new auto scroll
    public void autoScrollAnother() {
        scrollCount = 0;
        final int speedScroll = 1200;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(scrollCount == scrollStockAdapter.getItemCount()){
                    scrollStockAdapter.load();
                    scrollStockAdapter.notifyDataSetChanged();
                }
                rec_scroll_stock.smoothScrollToPosition((scrollCount++));
                handler.postDelayed(this, speedScroll);
            }
        };
        handler.postDelayed(runnable, speedScroll);
    }
}
