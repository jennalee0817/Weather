package com.geappliances.test.sunny.search;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.geappliances.test.sunny.MainActivity;
import com.geappliances.test.sunny.R;
import com.geappliances.test.sunny.common.Dlog;
import com.geappliances.test.sunny.common.Util;
import com.geappliances.test.sunny.data.WeatherData;
import com.geappliances.test.sunny.db.common.CityItem;
import com.geappliances.test.sunny.internal.ComponentHolder;
import com.geappliances.test.sunny.search.city.CityListAdapter;
import com.geappliances.test.sunny.search.listener.CitylistClickListener;
import com.geappliances.test.sunny.search.listener.EditTextFocusListener;
import com.geappliances.test.sunny.search.listener.KeyboardVisibilityListener;
import com.geappliances.test.sunny.search.listener.OnBottomReachedListener;
import com.geappliances.test.sunny.search.listener.SearchAdd;
import com.geappliances.test.sunny.search.listener.WeatherlistClickListener;

import java.util.ArrayList;

import static com.geappliances.test.sunny.common.Util.setKeyboardVisibilityListener;

public class SearchAddActivity extends AppCompatActivity implements EditTextFocusListener, KeyboardVisibilityListener, CitylistClickListener, WeatherlistClickListener, SearchAdd.View {

    private ProgressBar progressBar;
    private RecyclerView searchAddRecyclerView;
    private RecyclerView cityListRecyclerView;
    private RecyclerView.LayoutManager myWeatherListLayoutManager;
    private RecyclerView.LayoutManager cityListLayoutManager;

    private ArrayList<WeatherData> myWeatherList;  // my current weather list
    private ArrayList<CityItem> searchCityList; //result of searched city list

    private SearchAddListAdapter addListAdapter;
    private CityListAdapter cityListAdapter;
    private SearchEditText editTextSearch;

    private SearchAddPresenter searchAddPresenter;
    private Paint p = new Paint(); //background color for delete
    private Toast toast = null;


    private boolean cityListmore = true;
    private int offset = 0;
    private int page = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_add);

        overridePendingTransition(R.animator.trans_left_in, R.animator.trans_left_out);

        progressBar = (ProgressBar) findViewById(R.id.progress_circular);
        searchAddPresenter = new SearchAddPresenter(this, this);

        editTextSearch = (SearchEditText) findViewById(R.id.edittext_search);
        editTextSearch.setListener(this);
        setKeyboardVisibilityListener(this, this);


//        searchAddList View Setting
        searchAddRecyclerView = findViewById(R.id.list_currentWeather);
        searchAddRecyclerView.setHasFixedSize(true);
        searchAddRecyclerView.addItemDecoration(new ListItemDecoration(20));
        myWeatherListLayoutManager = new LinearLayoutManager(this);
        searchAddRecyclerView.setLayoutManager(myWeatherListLayoutManager);

        myWeatherList = ComponentHolder.getInstance().getMyWeatherDbHelper().fetchAllContent();

        addListAdapter = new SearchAddListAdapter(this, myWeatherList, this);
        searchAddRecyclerView.setAdapter(addListAdapter);
        new ItemTouchHelper(weatherListTouchCallback).attachToRecyclerView(searchAddRecyclerView);


//      cityList View Setting

        cityListRecyclerView = findViewById(R.id.list_citylist);
        cityListRecyclerView.setHasFixedSize(true);
        cityListRecyclerView.addItemDecoration(new ListItemDecoration(5));
        cityListLayoutManager = new LinearLayoutManager(this);
        cityListRecyclerView.setLayoutManager(cityListLayoutManager);
        cityListRecyclerView.setVisibility(View.GONE);
        searchCityList = new ArrayList<>();

        cityListAdapter = new CityListAdapter(searchCityList, this);
        cityListRecyclerView.setAdapter(cityListAdapter);

        cityListAdapter.setOnBottomReachedListener(new OnBottomReachedListener() {
            @Override
            public void onBottomReached(int position) {
                if (cityListmore) {
                    cityListRecyclerView.stopScroll();
                    page++;
                    offset = page * Util.SIZE_LIMIT_CITYLIST - 1;
                    String textSearch = String.valueOf(editTextSearch.getText());
                    ArrayList<CityItem> items = ComponentHolder.getInstance().getCoppiedDbHelper().findCityList(textSearch, Util.SIZE_LIMIT_CITYLIST, offset);
                    if (items.size() > 0) {
                        searchCityList.addAll(items);
                        Handler handler = new Handler();
                        final Runnable r = new Runnable() {
                            public void run() {
                                cityListAdapter.changeDataList(searchCityList);
                            }
                        };
                        handler.post(r);

                    }

                } else {
                    return;
                }


            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.animator.trans_right_in, R.animator.trans_right_out);
    }


    // get EditText Focus and set the view
    @Override
    public void focusOn() {
        Dlog.d("테스트", "focusOn");
        searchAddRecyclerView.animate().translationY(searchAddRecyclerView.getHeight()).setDuration(500);
        cityListRecyclerView.animate().alpha(1).setDuration(500).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                cityListRecyclerView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    public void focusOut() {
        Dlog.d("테스트", "focusOff");

        searchAddRecyclerView.animate().translationY(0).setDuration(500);
        cityListRecyclerView.animate().alpha(0).setDuration(500).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                cityListRecyclerView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


    }

    @Override
    public void searchCity(ArrayList<CityItem> items) {
        if (items.size() < 20) {
            cityListmore = false;
        } else {
            cityListmore = true;
        }
        offset = 0;
        page = 1;
        searchCityList = items;
        cityListAdapter.changeDataList(items);


    }

    @Override
    public void onKeyboardVisibilityChanged(boolean keyboardVisible) {
        if (keyboardVisible) {
        } else {
            editTextSearch.clearFocus();
        }
    }


    //    CityListItemClickListener
    @Override
    public void onClick(View v, CityItem item) {
        if (myWeatherList.size() > 15) {
            String text = "You can't add more than 15";
            if (toast != null) {
                toast.setText(text);
            } else {
                toast = Toast.makeText(getApplicationContext(), "You can't add more than 15", Toast.LENGTH_LONG);
                toast.show();
            }
            return;
        }
        searchAddPresenter.searchWeather(item.getCityId());
        progressBar.setVisibility(View.VISIBLE);
//        Toast.makeText(this,"아이템 클릭!!" + item.getCityName() + ", " + item.getCityId(),Toast.LENGTH_LONG).show();
    }


    //    WeatherListItemClickListener
    @Override
    public void onClick(View v, int position) {
//        Toast.makeText(this,"아이템 클릭!!" + position,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("setPosition", position);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        finish();
    }

    @Override
    public void successGettingList(WeatherData data) {
        ComponentHolder.getInstance().getMyWeatherDbHelper().removeExist(data.getCityId());
        ComponentHolder.getInstance().getMyWeatherDbHelper().insert(data);
        myWeatherList.clear();
        myWeatherList = ComponentHolder.getInstance().getMyWeatherDbHelper().fetchAllContent();
        addListAdapter.changeDataList(myWeatherList);
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
            editTextSearch.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editTextSearch.getWindowToken(), 0);
        }
    }
    @Override
    public void showErrorGettingList(String errorMsg) {
        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG);
        finish();
    }

    private ItemTouchHelper.SimpleCallback weatherListTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            int position = viewHolder.getAdapterPosition();

            if (position == 0) {
                if (toast != null) {
                    toast.setText("You can not delete this list");
                } else {
                    toast = Toast.makeText(getApplicationContext(), "You can not delete this list", Toast.LENGTH_SHORT);
                    toast.show();

                }
                return;
            }
            addListAdapter.removePosition(position);

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            Bitmap icon;
            WeatherListViewHolder weatherListViewHolder = (WeatherListViewHolder) viewHolder;

            if (viewHolder.getAdapterPosition() == 0) {
                return;
            }
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                View itemView = viewHolder.itemView;
                float height = (float) itemView.getBottom() - (float) itemView.getTop();
                float width = height / 3;

                p.setColor(ContextCompat.getColor(getApplicationContext(), R.color.delete_red));
                RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                c.drawRect(background, p);
                icon = getBitmapFromVectorDrawable(getApplicationContext(), R.drawable.ic_delete);
                RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                c.drawBitmap(icon, null, icon_dest, p);

            }

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

    };

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);

        assert drawable != null;
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }


}
