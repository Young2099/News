package com.demo.panguso.mvp_mode.mvp.presenter.impl;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.app.App;
import com.demo.panguso.mvp_mode.listener.RequestCallBack;
import com.demo.panguso.mvp_mode.mvp.bean.PhotoDetaiView;
import com.demo.panguso.mvp_mode.mvp.interactor.impl.PhotoDetailInteractorImpl;
import com.demo.panguso.mvp_mode.mvp.presenter.PhotoDetailPresenter;
import com.demo.panguso.mvp_mode.mvp.presenter.base.BasePresenterImpl;
import com.demo.panguso.mvp_mode.utils.PhotoRequestType;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

/**
 * Created by ${yangfang} on 2016/11/10.
 */

public class PhotoDetailPresenterImpl extends BasePresenterImpl<PhotoDetaiView, Uri> implements PhotoDetailPresenter, RequestCallBack<Uri> {
    private PhotoDetailInteractorImpl mPhotoDetailInteractor;
    private Activity mActivity;
    private int mRequestType = -1;

    @Inject
    public PhotoDetailPresenterImpl(PhotoDetailInteractorImpl photoDetailPresenter, Activity activity) {
        mPhotoDetailInteractor = photoDetailPresenter;
        mActivity = activity;
    }


    @Override
    public void success(Uri data) {
        super.success(data);
        switch (mRequestType) {
            case PhotoRequestType.TYPE_SHARE:
                share(data);
                break;
            case PhotoRequestType.TYPE_SAVE:
                showSavePathMsg(data);
                break;
            case PhotoRequestType.TYPE_SET_WALLPAPER:
                setWallPaper(data);
                break;
        }
    }

    private void showSavePathMsg(Uri uri) {
        mView.showErrorMsg(mActivity.getString(R.string.picture_alerady_save_to, uri.getPath()));
    }

    private void share(Uri uri) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/jpeg");
        mActivity.startActivity(Intent.createChooser(intent, App.getAppContext().getString(R.string.share)));
    }

    @Override
    public void handlePicture(String imageUrl, @PhotoRequestType.PhotoRequestTypeChecker int type) {
        mRequestType = type;
        mPhotoDetailInteractor.saveImageAndGetImageUri(this, imageUrl);
    }

    /**
     * 设置壁纸
     *
     * @param imageUrl
     */
    public void setWallPaper(Uri imageUrl) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(mActivity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            File wallpaperFile = new File(imageUrl.getPath());
            Uri contentUri = getImageContentUri(mActivity, wallpaperFile.getAbsolutePath());
            mActivity.startActivity(wallpaperManager.getCropAndSetWallpaperIntent(contentUri));
        } else {
            try {
                wallpaperManager.setStream(mActivity.getContentResolver().openInputStream(imageUrl));
                mView.showErrorMsg(App.getAppContext().getString(R.string.set_waller_success));
            } catch (IOException e) {
                e.printStackTrace();
                mView.showErrorMsg(e.getMessage());
            }
        }
    }

    private Uri getImageContentUri(Context context, String absolutePath) {
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                , new String[]{MediaStore.Images.Media._ID}
                , MediaStore.Images.Media.DATA + "=?"
                , new String[]{absolutePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            Log.e("PhotoDetailPresenter:", "cursor:" + cursor.getCount());
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Integer.toString(id));
        } else if (!absolutePath.isEmpty()) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, absolutePath);
            return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } else {
            return null;
        }
    }

    @Override
    public void onDestory() {
        super.onDestory();
        mView = null;
    }
}
