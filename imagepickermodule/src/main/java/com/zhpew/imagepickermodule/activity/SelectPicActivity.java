package com.zhpew.imagepickermodule.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zhpew.imagepickermodule.R;
import com.zhpew.imagepickermodule.adapter.PicAdapter;
import com.zhpew.imagepickermodule.bean.PicItem;
import com.zhpew.imagepickermodule.loader.PicLoader;
import com.zhpew.imagepickermodule.utils.ThreadPoolHelper;
import com.zhpew.imagepickermodule.utils.Utils;

import java.util.ArrayList;

public class SelectPicActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String SELECTED_PIC = "SELECTED_PIC";

    private RecyclerView mList;
    private PicAdapter mAdapter;
    private ArrayList<PicItem> mData = new ArrayList<>(2000);

    private static final long ONE_HOUR = 60 * 60;
    private static final long ONE_DAY = 24 * ONE_HOUR;

    private static final int REQUEST_PERMISSION = 10086;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_picture);
        mList = findViewById(R.id.list);
        mAdapter = new PicAdapter();
        mAdapter.setCount(getIntent().getIntExtra("count", Integer.MAX_VALUE));
        int needPermission = ContextCompat.checkSelfPermission(SelectPicActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (needPermission == PackageManager.PERMISSION_GRANTED) {
            LoaderManager.getInstance(this).initLoader(0, null, this);
        } else {
            ActivityCompat.requestPermissions(SelectPicActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
        }
        findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<PicItem> resultList = new ArrayList<>();
                for (PicItem i : mData) {
                    if (i.isSelected()) {
                        resultList.add(i);
                    }
                }
                Intent intent = new Intent();
                intent.putExtra(SELECTED_PIC, resultList);
                setResult(getIntent().getIntExtra("code", -1), intent);
                finish();
            }
        });
    }

    private void initData() {
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mData.get(position).getType() == PicItem.TYPE_DATE) {
                    return 3;
                } else {
                    return 1;
                }

            }
        });
        mAdapter.setData(mData);
        mList.setLayoutManager(manager);
        mList.setAdapter(mAdapter);

        ThreadPoolHelper.getInstance().RunOnIoThread(new Runnable() {
            @Override
            public void run() {
                for (PicItem item : mData) {
                    item.setHasLocationInfo(Utils.imageHasLocationInfo(item.getUrl()));
                }
                mList.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new PicLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        long lastTime = -1;
        //处理一下数据
        while (data.moveToNext()) {
            @SuppressLint("Range") String url = data.getString(data.getColumnIndex(MediaStore.MediaColumns.DATA));
            @SuppressLint("Range") long date = data.getLong(data.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
            @SuppressLint("Range") String type = data.getString(data.getColumnIndex(MediaStore.Images.Media.MIME_TYPE));
            if (lastTime == -1) {
                if (date % ONE_DAY > 16 * ONE_HOUR) {
                    //第二天了
                    lastTime = date - date % ONE_DAY + 16 * ONE_HOUR;
                } else {
                    lastTime = date - date % ONE_DAY - 8 * ONE_HOUR;
                }
                PicItem item = new PicItem(null, lastTime, "date");
                mData.add(item);
            } else {
                if ((lastTime - date) >= ONE_DAY) {
                    if (date % ONE_DAY > 16 * ONE_HOUR) {
                        //第二天了
                        lastTime = date - date % ONE_DAY + 16 * ONE_HOUR;
                    } else {
                        lastTime = date - date % ONE_DAY - 8 * ONE_HOUR;
                    }
                    PicItem item = new PicItem(null, lastTime, "date");
                    mData.add(item);
                }
            }
            PicItem item = new PicItem(url, date, type);
            mData.add(item);
        }
        initData();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "请去设置打开读取设备存储权限", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "读取设备相册需要此权限，请打开此权限", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else {
                LoaderManager.getInstance(this).initLoader(0, null, this);
            }
        }
    }
}
